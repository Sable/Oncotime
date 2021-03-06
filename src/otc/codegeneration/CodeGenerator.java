package otc.codegeneration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import otc.analysis.DepthFirstAdapter;
import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.drivers.ProgramFile;
import otc.node.ADisjunctionSequenceItem;
import otc.node.AForeachComputation;
import otc.node.AForeachMemberComputation;
import otc.node.AForeachMemberSetComputation;
import otc.node.AForeachSequenceComputation;
import otc.node.AForeachSequenceSetComputation;
import otc.node.AForeachSetComputation;
import otc.node.AForeachTableComputation;
import otc.node.AForeachTableSetComputation;
import otc.node.AListComputation;
import otc.node.ANoparamEventItem;
import otc.node.ANotSequenceItem;
import otc.node.AParamEventItem;
import otc.node.APermutationSequenceItem;
import otc.node.APrintAttrComputation;
import otc.node.APrintComputation;
import otc.node.APrintLengthComputation;
import otc.node.APrintTableitemComputation;
import otc.node.APrintTimelineComputation;
import otc.node.ASequence;
import otc.node.ASequenceItem;
import otc.node.ATableComputation;
import otc.node.Node;
import otc.node.PEventItem;
import otc.node.PSequenceItem;
import otc.node.PType;
import otc.symboltable.DoctorSymbol;
import otc.symboltable.EventsSymbol;
import otc.symboltable.FilterItemSymbol;
import otc.symboltable.PeriodSymbol;
import otc.symboltable.PopulationSymbol;
import otc.symboltable.Stage;
import otc.symboltable.Symbol;
import otc.symboltable.Symbol.Type;
import otc.symboltable.TypedListValue;
import otc.typecheck.TypeChecker;

public class CodeGenerator extends DepthFirstAdapter
{
	
	// Will contain and house the generatedCode. 
	private static StringBuilder generatedCode = new StringBuilder();
	
	// Will tell us how many foreaches have occured. For code generation. 
	private static int numberOfForeaches = 0;
	
	// Will tell us if we need to modify the current foreach. 
	private static int level = 0; 
	
	// Will tell us how we need to modify the current foreach. 
	private static Stack<String> parentVariable = new Stack<String>();  
	
	// Useful for different parts of the program. 
	private static String quote = "\""; 
	

	/*********************************************************************************			
     * 			 				TRAVERSE PROGRAM TREE								 *
     *********************************************************************************/
	
	/**
	 * Intitializes and sets up the stage for code generation. 
	 * This method also generates the filter queries, right from the start. 
	 */
	public CodeGenerator()
	{
		generateHeaders(); 
		generateFilters();
		generateBoilerPlate(); 
	}


	/**
	 * The visitor method that starts the traversal for the program.  
	 * @param theProgram
	 */
	public static void generate(LinkedList<ProgramFile> theProgram) 
	{ 	
		Iterator<ProgramFile> iter = theProgram.iterator();
		ProgramFile programFile;
		Node ast;
		
		while (iter.hasNext()) 
		{
			programFile = (ProgramFile)iter.next();
			ast = programFile.getAst();   
			ast.apply(new CodeGenerator());
		}
		
		System.out.println(generatedCode); 
	}
	
	
	/*********************************************************************************			
     * 			 				HEADER AND FITLERS 									 *
     *********************************************************************************/
	
	/**
	 * Generates and includes the necessary dependency files. 
	 */
	private void generateHeaders() 
	{
		// We need MySQLdb to handle the MySQL stuff.
		generatedCode.append("import MySQLdb");
		genCodeNewLine();
		
		// Used for pretty printing tables.
		generatedCode.append("from prettytable import PrettyTable");
		genCodeNewLine();
		
		// This allows us to remotely connect to the db. 
		generatedCode.append("db=MySQLdb.connect(host='127.0.0.1', user='root', passwd='root', db='oncodb')");
		genCodeNewLine(); 
		
		// This is the contents of the Ontology.json file. 
		generatedCode.append(CodeInjector.getOntology());
		genCodeNewLine(); 
		
	}
	
	/**
	 * This method generates and handles code for filters.
	 * This is the method that generates the code that will actually handle DB parsing. 
	 */
	private void generateFilters()
	{
		 // First we want to get a list of all the events. 
		EventsSymbol events = (EventsSymbol) Stage.getSymbolForAugmentedName(EventsSymbol.getEventSymbolName());
		generatedCode.append("events = [");
		generatedCode.append(events.getEventsAsStrings()); 
		generatedCode.append("]");
		genCodeNewLine(); 
		
		// Next we want to generate our filter query string. 
		StringBuilder filters = new StringBuilder();
		filters.append(generatePopulationFilter());
		filters.append(generatePeriodFilter()); 
		filters.append(generateDoctorFilter());
		
		generatedCode.append("filters = \""
				+ filters.toString() + "\""); 
	}
	
	/**
	 * There are many functions that were handwritten in Python. 
	 * This method takes these functions and injects them into the code. 
	 * This allows easier manipulation of how the data is stored and managed. 
	 */
	private void generateBoilerPlate() 
	{
		// This is the data manipulation code.
		// It handles grabbing the data and storing it into our Results object. 
		// Computations can then go ahead and operate on this object. 
		generatedCode.append(CodeInjector.getDataManipulationCode());  
	}

	
	private String generatePopulationFilter() 
	{
		String populationQuery = "";
		
		// The symbol contains all the information we are interested in. 
		PopulationSymbol popSymbol = (PopulationSymbol) Stage.getSymbolForAugmentedName(PopulationSymbol.getPopulationSymbolName());
		
		// We want all the information from the Patient table. 
		String tableNamePatient = "Patient.";
		String tableNameDiagnosis = "Diagnosis."; 
		
		// For each of the Population filter items. 
		for(String filterItem : popSymbol.getPopulationFilters().keySet())
		{
			FilterItemSymbol item =  popSymbol.getPopulationFilters().get(filterItem);
			
			String populationItemQuery = "("; 
			
			// For each of the values in the item. 
			for(int i = 0; i < item.getValues().size(); i++)
			{
				// We want the actual value. 
				TypedListValue val = item.getValues().get(i); 
				
				switch(item.getType())
				{
					case ID:
					{ 
						if(val.getType() == Type.Range)
							populationItemQuery += tableNamePatient + "PatientSerNum >= " + val.getValue() +" and " + tableNamePatient + "PatientSerNum <= " + val.getValue2();
						else
							populationItemQuery += tableNamePatient + "PatientSerNum = " + val.getValue();  	
						
						break;
					}
					case Birthyear:
					{
						if(val.getType() == Type.Range)
							populationItemQuery += tableNamePatient + "DateOfBirth >= " + val.getValue() +" and " + tableNamePatient + "DateOfBirth <= " + val.getValue2();
						else
							populationItemQuery += tableNamePatient + "DateOfBirth = " + val.getValue(); 
						break; 
					}
					case Diagnosis:
					{
						populationItemQuery += tableNameDiagnosis + "Description REGEXP \\\"" + val.getValue() + "\\\"";
						break;
					}
					case Postalcode:
					{
						populationItemQuery += tableNamePatient + "PostalCode REGEXP \\\"" + val.getValue() + "\\\"";
						break; 
					}
					case Sex:
					{
						populationItemQuery += tableNamePatient + "Sex REGEXP \\\"" + val.getValue() + "\\\"";
						break; 
					}
					default:
						break;
				}
				
				// We want to generate the appropriate "or's". 
				if(i != item.getValues().size() - 1)
					populationItemQuery += " or "; 
				else
					populationItemQuery += ") "; 
			}
			
			if(!populationItemQuery.equals("("))
				populationQuery += "and " + populationItemQuery; 
		} 
		return populationQuery;
	}
	
	private String generatePeriodFilter() 
	{
		String periodQuery = "";
		
		// The symbol contains all the information we are interested in. 
		PeriodSymbol perSymbol = (PeriodSymbol) Stage.getSymbolForAugmentedName(PeriodSymbol.getPeriodSymbolName());
		
		// Like group 4 did originally, we use a temporary PERIOD as the table name. This is because the period information
		// 	is dependent on the event. The word PERIOD is replaced by the appropriate table in the get_results() method of the 
		//	generated code. 
		String tableNamePeriod = "PERIOD."; 
		
		for(String s : perSymbol.getPeriodFilters().keySet())
		{
			FilterItemSymbol item = perSymbol.getPeriodFilters().get(s);
			
			String periodItemQuery = "("; 
			
			for(int i = 0; i < item.getValues().size(); i++)
			{
				
				// We want the actual values. 
				TypedListValue val = item.getValues().get(i); 
				
				switch(item.getType())
				{ 
					case Hours:
					{
						if(val.getType() == Type.Range)
						{
							// Start hour. 
							String hour1 = val.getValue().substring(0, 2);
							String hour2 = val.getValue2().substring(0, 2);
							
							periodItemQuery += "(" + "HOUR("+tableNamePeriod+"LastUpdated) >=" +hour1 +
													" and "
												   + "HOUR("+tableNamePeriod+"LastUpdated) <=" +hour2 + ")"; 
						}
						else
						{
							// The start time is easy to get. 
							String startTime = "\\\"" + val.getValue() + ":00" + "\\\""; 
							
							// We need to get the end time. 
							// hours are always HH:MM, so we can get just the hour. 
							String hour = val.getValue().substring(0, 3); 
							String endTime = "\\\"" + hour + "59:59" + "\\\""; 
							
							periodItemQuery += "(" + "TIME("+tableNamePeriod+"LastUpdated) >=" +startTime +
									" and "
								   + "TIME("+tableNamePeriod+"LastUpdated) <=" +endTime + ")";
						}
						 
						break;
					}
					
					case Date:
					{
						if(val.getType() == Type.Range)
						{
							String startDateTime = "\\\"" + val.getValue() + " 00:00:00" + "\\\""; 
							String endDateTime   = "\\\"" + val.getValue2() + " 23:59:59" + "\\\"";
							
							periodItemQuery += "(" + tableNamePeriod + "LastUpdated >= " + startDateTime 
									+ " and " 
									+ tableNamePeriod + "LastUpdated <= " +endDateTime + ")";
						}
						else
						{
							String startDateTime = "\\\"" + val.getValue() + " 00:00:00" + "\\\""; 
							String endDateTime   = "\\\"" + val.getValue2() + " 23:59:59" + "\\\"";
							
							periodItemQuery += "(" + tableNamePeriod + "LastUpdated >= " + startDateTime 
									+ " and " 
									+ tableNamePeriod + "LastUpdated <= " +endDateTime + ")";	
						}
						
						 
					
						break; 
					}
					
					case Months:
					{
						if(val.getType() == Type.Range)
						{
							periodItemQuery += "MONTH(" + tableNamePeriod + "LastUpdated) >= " + val.getValue()
													+ " and "
													+ "MONTH(" + tableNamePeriod + "LastUpdated) <= " + val.getValue2() + " "; 
						}
						else
							periodItemQuery += "MONTH(" + tableNamePeriod + "LastUpdated) = " + val.getValue();  
						break;
					}
					
					case Days:
					{
						if(val.getType() == Type.Range)
						{
							periodItemQuery += "DAYOFWEEK(" + tableNamePeriod + "LastUpdated) >= " + val.getValue()
													+ " and "
													+ "DAYOFWEEK(" + tableNamePeriod + "LastUpdated) <= " + val.getValue2() + " "; 
						}
						else
							periodItemQuery += "DAYOFWEEK(" + tableNamePeriod + "LastUpdated) = " + val.getValue();   
						break; 
					}
					
					case Years:
					{
						if(val.getType() == Type.Range)
						{
							periodItemQuery += "YEAR(" + tableNamePeriod + "LastUpdated) >= " + val.getValue()
													+ " and "
													+ "YEAR(" + tableNamePeriod + "LastUpdated) <= " + val.getValue2() + " "; 
						}
						else
							periodItemQuery += "YEAR(" + tableNamePeriod + "LastUpdated) = " + val.getValue(); 
						break; 
					}
					
					default:
					{
						break;
					}
				}
				
				// We want to generate the appropriate "or's". 
				if(i != item.getValues().size() - 1)
					periodItemQuery += " or "; 
				else
					periodItemQuery += ") "; 
			}
			
			if(!periodItemQuery.equals("("))
				periodQuery += "and " + periodItemQuery;
		}
		
		return periodQuery;
	}

	private String generateDoctorFilter() 
	{
		String doctorQuery = "";
		
		// The symbol contains all the information we are interested in. 
		DoctorSymbol docSymbol = (DoctorSymbol) Stage.getSymbolForAugmentedName(DoctorSymbol.getDoctorSymbolName());
		
		String tableNameDoctor = "Doctor."; 
		
		for(String s : docSymbol.getDoctorFilters().keySet())
		{
			FilterItemSymbol sym = docSymbol.getDoctorFilters().get(s); 
			
			String doctorItemQuery = "("; 
			
			for(int i = 0; i < sym.getValues().size(); i++)
			{
				TypedListValue val = sym.getValues().get(i);
				
				switch(val.getType())
				{
					case ID:
					{
						
						if(val.getType() == Type.Range)
							doctorItemQuery += tableNameDoctor + "DoctorSerNum >= " + val.getValue() +" and " + tableNameDoctor + "DoctorSerNum <= " + val.getValue2();
						else
							doctorItemQuery += tableNameDoctor + "DoctorSerNum = " + val.getValue();   			
						
						break; 
					}
					default:
						break;
				}
				
				// We want to generate the appropriate "or's". 
				if(i != sym.getValues().size() - 1)
					doctorItemQuery += " or "; 
				else
					doctorItemQuery += ") "; 
			}
			
			if(!doctorItemQuery.equals("("))
				doctorQuery += "and " + doctorItemQuery;
		}
		
		return doctorQuery;
	}



	/*********************************************************************************			
     * 			 				HELPER METHODS										 *
     *********************************************************************************/

	/**
	 * We often need to generate Tabs for our Python Program. 
	 * This simple method does just that. 
	 */
	private void codeGenTabs()
	{
		for(int i = 0; i < level; i++) generatedCode.append("\t"); 
	}
	
	/**
	 * Everytime we need a new line we call this method. 
	 */
	private void genCodeNewLine()
	{
		generatedCode.append("\n"); 
	}
	
	/**
	 * Sometimes we'll want to do both. 
	 */
	private void genCodeNewLineWithTabs()
	{
		genCodeNewLine(); 
		codeGenTabs(); 
	}
	
	/**
	 * We often need to put strings into quotes. 
	 */
	private String quotes(String s)
	{
		return (quote + s + quote).trim();
	}


	
	/*********************************************************************************			
     * 			 				COMPUTATIONS										 *
     *********************************************************************************/
	
	
	/***********************************
	 * 			FOREACH				   *
	 ***********************************/
	@Override 
	public void caseAForeachComputation(AForeachComputation node)
	{
		// We need to keep track of the number of foreaches in order to get the appropriate iterator nodes.
		numberOfForeaches++;
		
		String actorName = node.getActor().toString().toUpperCase().trim(); 
		String variableName = node.getTIdentifier().getText().toString().trim();
		String valueName = variableName + "val"; 
		
		// Top Level Foreach
		if(level == 0)
		{
			// Generate the appropriate code.
			generatedCode.append("for " + variableName + ", " + valueName + " in results.get_type(" + quotes(actorName) + ").iteritems():");
			
			// Increment the level, and store the variable name so we can use it in further foreaches. 
			level++;
			parentVariable.push(valueName); 
			
			// So all following computations are printed accordingly.  
			genCodeNewLineWithTabs();
		}
		else
		{
			// Get the variable name we are interested in. 
			String parentVariableName = parentVariable.pop(); 
			
			// Generate the appropriate code. 
			generatedCode.append("for " + variableName + " in " + parentVariableName + "[" + quotes(actorName) + "]: ");
			
			// Increment the level, and store the variable name so we can use it in further foreaches.
			level++; 
			parentVariable.push(variableName);
			
			// So all the following computations are printed accordingly.  
			genCodeNewLineWithTabs(); 
		}
		
		node.getComputation().apply(this);
		level--; 
	}
	
	public void caseAForeachSetComputation(AForeachSetComputation node)
	{
		// We need to keep track of the number of foreaches in order to get the appropriate iterator nodes.
		numberOfForeaches++;
		
		String actorName = node.getActor().toString().toUpperCase().trim(); 
		String variableName = node.getTIdentifier().getText().toString().trim();
		String valueName = variableName + "val"; 
		
		// Top Level Foreach
		if(level == 0)
		{
			// Generate the appropriate code.
			generatedCode.append("for " + variableName + ", " + valueName + " in results.get_type(" + quotes(actorName) + ").iteritems():");
			
			// Increment the level, and store the variable name so we can use it in further foreaches. 
			level++;
			parentVariable.push(valueName); 
			
			// So all following computations are printed accordingly.  
			genCodeNewLineWithTabs();
		}
		else
		{
			// Get the variable name we are interested in. 
			String parentVariableName = parentVariable.pop(); 
			
			// Generate the appropriate code. 
			generatedCode.append("for " + variableName + " in " + parentVariableName + "[" + quotes(actorName) + "]: ");
			
			// Increment the level, and store the variable name so we can use it in further foreaches.
			level++; 
			parentVariable.push(variableName);
			
			// So all the following computations are printed accordingly. 
			genCodeNewLineWithTabs(); 
		}
		
		node.getComputationList().apply(this);
		level--; 
	}
	
	@Override
	public void caseAForeachTableComputation(AForeachTableComputation node)
	{
		// We want to keep track of the number of Foreaches.
		numberOfForeaches++;
		
		// Get the required information out of the node. 
		String tableName = node.getVariable().getText();  
		String iteratorName = node.getIterator().getText(); 
		
		// Generate the appropriate code.
		generatedCode.append("for " + iteratorName +  " in " + tableName + ": ");
		level++;
		genCodeNewLineWithTabs(); 
		
		// Generate the interior computations. 
		node.getComputation().apply(this);
		level--; 
	}
	
	@Override
	public void caseAForeachTableSetComputation(AForeachTableSetComputation node)
	{
		// We want to keep track of the number of Foreaches.
		numberOfForeaches++;
		
		// Get the required information out of the node. 
		String tableName = node.getVariable().getText();  
		String iteratorName = node.getIterator().getText(); 
		
		// Generate the appropriate code.
		generatedCode.append("for " + iteratorName +  " in " + tableName + ": ");
		level++;
		genCodeNewLineWithTabs(); 
		
		// Generate the interior computations. 
		node.getComputationList().apply(this);
		level--; 
	}
	
	@Override 
	public void caseAForeachSequenceComputation(AForeachSequenceComputation node)
	{
		// We want to keep track of the number of foreaches. 
		numberOfForeaches++; 
		
		// We need to get the relevant data from the node. 
		String iteratorName = node.getTIdentifier().getText();
		String sequence = generateSequenceString((ASequence) node.getSequence());
		String valueName = iteratorName + "val"; 
		
		// Generate the appropriate code. 
		generatedCode.append("for " + iteratorName + ", " + valueName + " in findSequences(" + sequence +").iteritems():");
		level++;
		genCodeNewLineWithTabs(); 
		
		// Generate the interior computations. 
		node.getComputation().apply(this);
		level--; 
	}
	
	@Override 
	public void caseAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
	{
		// We want to keep track of the number of foreaches. 
		numberOfForeaches++; 
		
		// We need to get the relevant data from the node. 
		String iteratorName = node.getTIdentifier().getText();
		String sequence = generateSequenceString((ASequence) node.getSequence());
		String valueName = iteratorName + "val"; 
		
		// Generate the appropriate code. 
		generatedCode.append("for " + iteratorName + ", " + valueName + " in findSequences(" + sequence +").iteritems():");
		level++;
		genCodeNewLineWithTabs(); 
		
		// Generate the interior computations. 
		node.getComputationList().apply(this);
		level--; 
	}
	
	@Override 
	public void caseAForeachMemberComputation(AForeachMemberComputation node)
	{
		// We want to keep track of the number of foreaches. 
		numberOfForeaches++; 
		
		// We need to get the relevant data from the node. 
		String iteratorName = node.getName().getText().trim();
		String sequenceName = node.getSequencename().getText().trim();
		String valueName = iteratorName + "val"; 
		
		// Generate the appropriate code. 
		generatedCode.append("for " + iteratorName + ", "  + valueName + " in " + sequenceName + ".iteritems():");
		level++;
		genCodeNewLineWithTabs(); 
		
		// Generate the interior computations. 
		node.getComputation().apply(this);
		level--; 
	}
	
	@Override 
	public void caseAForeachMemberSetComputation(AForeachMemberSetComputation node)
	{
		// We want to keep track of the number of foreaches. 
		numberOfForeaches++; 
		
		// We need to get the relevant data from the node. 
		String iteratorName = node.getName().getText().trim();
		String sequenceName = node.getSequencename().getText().trim();
		String valueName = iteratorName + "val"; 
		
		// Generate the appropriate code. 
		generatedCode.append("for " + iteratorName + ", "  + valueName + " in " + sequenceName + ".iteritems():");
		level++;
		genCodeNewLineWithTabs(); 
		
		// Generate the interior computations. 
		node.getComputationList().apply(this);
		level--; 
	}
	
	/***********************************
	 * 			TABLES				   *
	 ***********************************/
	@Override 
	public void caseATableComputation(ATableComputation node)
	{
		String variableName = node.getTIdentifier().getText().trim(); 
		String actorName = node.getActor().toString().toUpperCase().trim();
		String fieldName = node.getType().toString().toUpperCase().trim();
		
		generatedCode.append(variableName + " = tableDecleration( " + quotes(actorName) + ", " + quotes(fieldName) + ", results)" );  
		genCodeNewLineWithTabs(); 
	}
	
	/***********************************
	 * 			PRINTING			   *
	 ***********************************/
	@Override
	public void caseAPrintComputation(APrintComputation node)
	{
		// If it's a Foreach
		Symbol printSymbol = Stage.symbolsGetForeachSymbol(node.getTIdentifier().toString().trim(), numberOfForeaches);
		
		// Otherwise it's a table
		if(printSymbol == null) printSymbol = Stage.symbolsGet(node.getTIdentifier().toString().trim()); 
		
		switch(printSymbol.getObjectType())
		{
			case Table: 
			{
				generatedCode.append("printTable("+printSymbol.getName()+")");
				genCodeNewLineWithTabs();
				break;
			}
			case Patient:
			{
				generatedCode.append("printActor(results, "+printSymbol.getName()+ ", " + quotes("PATIENT") + ", tabCount=" + level + ")");
				genCodeNewLineWithTabs(); 
				break;
			}
			case Diagnosis:
			{
				generatedCode.append("printActor(results, "+printSymbol.getName()+", " + quotes("DIAGNOSIS") + ", tabCount=" + level + ")");
				genCodeNewLineWithTabs();
				break;
			}
			case Doctor:
			{
				generatedCode.append("printActor(results,  "+printSymbol.getName()+", " + quotes("DOCTOR") + ", tabCount=" + level + ")");
				genCodeNewLineWithTabs();
				break; 
			}
			case SequenceItem:
			{
				generatedCode.append("printSequenceItem(" + printSymbol.getName()+"val" +")"); 
				genCodeNewLineWithTabs(); 
				break;
			}
			default:
			{
				MyError.fatalError("Code Generation", "We are trying to print an object of type: " + printSymbol.getObjectType());
				break; 
			}	
		}
	}
	
	@Override
	public void caseAPrintAttrComputation(APrintAttrComputation node)
	{
		Symbol printSymbol = Stage.symbolsGetForeachSymbol(node.getTIdentifier().toString().trim(), numberOfForeaches);
		
		// We want to get a list of all the attributes. 
		String attributes = "["; 
		for(PType type : node.getAttrList())
		{
			String value = quotes(type.toString().toUpperCase().trim()); 
			attributes += value + ", "; 
		}
		attributes += "]"; 
		
		switch(printSymbol.getObjectType())
		{
			case Patient:
			{
				generatedCode.append("printActorAttributes(results, "+printSymbol.getName()+ ", " + quotes("PATIENT") + ", " + attributes + ", tabCount=" + level + ")");
				genCodeNewLineWithTabs();
				break;
			}
			case Diagnosis:
			{
				generatedCode.append("printActorAttributes(results, "+printSymbol.getName()+ ", " + quotes("DIAGNOSIS") + ", " + attributes + ", tabCount=" + level + ")");
				genCodeNewLineWithTabs();
				break;
			}
			case Doctor:
			{
				generatedCode.append("printActorAttributes(results, "+printSymbol.getName()+ ", " + quotes("DIAGNOSIS") + ", " + attributes + ", tabCount=" + level + ")");
				genCodeNewLineWithTabs();
				break; 
			}
			default:
			{
				MyError.fatalError("Code Generation", "We are trying to print an object of type: " + printSymbol.getObjectType());
				break; 
			}
		}
	}
	
	@Override
	public void caseAPrintTimelineComputation(APrintTimelineComputation node)
	{
		generatedCode.append("printPatientTimeline(results, " + node.getTIdentifier().getText() + ")");
		genCodeNewLineWithTabs();
	}
	
	@Override
	public void caseAPrintLengthComputation(APrintLengthComputation node)
	{
		generatedCode.append("printLength(" + node.getTIdentifier().getText() + ")"); 
		genCodeNewLineWithTabs();
	}
	
	@Override 
	public void caseAPrintTableitemComputation(APrintTableitemComputation node)
	{
		generatedCode.append("printTableItem(" + node.getVariable().toString().trim() + "," + node.getIterator().toString().trim() + ")"); 
		genCodeNewLineWithTabs(); 
	}
	
	/***********************************
	 * 			SEQUENCES			   *
	 ***********************************/
	
	private String getEventName(PEventItem ei)
	{
		if(ei instanceof AParamEventItem)
		{
			return quotes(((AParamEventItem) ei).getTIdentifier().getText().trim());
		}
		else
		{
			return quotes(((ANoparamEventItem) ei).getTIdentifier().getText().trim());
		}
	}
	
	private String generateSequenceString(ASequence s)
	{
		String sequenceString = "["; 
		
		
		for(PSequenceItem item : s.getSequenceItem())
		{
			if(item instanceof ADisjunctionSequenceItem)
			{
				ADisjunctionSequenceItem disItem = (ADisjunctionSequenceItem) item;
				
				String eventItemString = "(" + quotes("Or") + ", ["; 
				
				for(PEventItem eventItem : disItem.getEventItem())
				{
					eventItemString += getEventName(eventItem) + ", "; 
				}
				
				eventItemString += "])"; 
				sequenceString += eventItemString + ", "; 
			}
			
			if(item instanceof ANotSequenceItem)
			{
				ANotSequenceItem notItem = (ANotSequenceItem) item;
				
				String eventItemString = "(" + quotes("Not") + ", ["; 
				
				eventItemString += getEventName(notItem.getEventItem());

				eventItemString += "])"; 
				sequenceString += eventItemString + ", "; 
			}
			
			if(item instanceof APermutationSequenceItem)
			{
				APermutationSequenceItem anyItem = (APermutationSequenceItem) item;  
				
				String eventItemString = "(" + quotes("Any") + ", ["; 
				
				for(PEventItem eventItem : anyItem.getEventItem())
				{
					eventItemString += getEventName(eventItem) + ", "; 
				}
				
				eventItemString += "])"; 
				sequenceString += eventItemString + ", "; 
			}
		}
		
		return sequenceString += " (" + quotes("End") + ", [] )]";
	}
	
	@Override
	public void caseAListComputation(AListComputation node)
	{
		String name = node.getTIdentifier().getText().toString().trim();
		String sequence = generateSequenceString((ASequence)node.getSequence()); 
		
		generatedCode.append(name + " = " + "findSequences(" + sequence + ")");
		genCodeNewLineWithTabs();
	}
	
	
}
