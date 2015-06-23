package otc.typecheck;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.drivers.ProgramFile;
import otc.node.*; 
import otc.symboltable.Symbol;
import otc.symboltable.Symbol.ObjectType;
import otc.symboltable.Symbol.Type;
import otc.symboltable.ActorSymbol;
import otc.symboltable.DoctorSymbol;
import otc.symboltable.EventsSymbol;
import otc.symboltable.FilterItemSymbol;
import otc.symboltable.GroupSymbol;
import otc.symboltable.PeriodSymbol;
import otc.symboltable.PopulationSymbol;
import otc.symboltable.Stage;
import otc.symboltable.TypedListValue;

/**
 * Type checks the OncoProgram. 
 * @author vikramsundaram
 *
 */
public class TypeChecker extends DepthFirstAdapter
{
	String fileName; 
	
	/*********************************************************************************			
     * 			 				TRAVERSE PROGRAM TREE								 *
     *********************************************************************************/
	public TypeChecker(String pFileName)
	{
		this.fileName = pFileName;
		
		// We initialize the root level of our symbol table. 
		Stage.initializeComputationTables(); 
	}
	
	/**
	 * The visitor method that starts the traversal for the program.  
	 * @param theProgram
	 */
	public static void typeCheck(LinkedList<ProgramFile> theProgram) 
	{ 	
		Iterator<ProgramFile> iter = theProgram.iterator();
		ProgramFile programFile;
		Node ast;
		
		while (iter.hasNext()) 
		{
			programFile = (ProgramFile)iter.next();
			ast = programFile.getAst();   
			ast.apply(new TypeChecker(programFile.getName()));
		}
	}
	
	/*********************************************************************************			
     * 			 				PROGRAM HEADER										 *
     *********************************************************************************/
	@Override
	public void caseAHeader(AHeader node)
	{	
		// We want to make sure the script name matches the name of the file. 
		String name = node.getName().getText(); 
		String nameOfFile = OncoUtilities.getNameOfFileWithoutExtension(fileName);
		int linenumber = node.getName().getLine(); 

		if(!name.equals(nameOfFile))
			MyError.error(fileName, ErrorMessages.invalidFileName(name, nameOfFile), linenumber);
		
	}
	
	
	/*********************************************************************************			
     * 			 				 GROUPS 											 *
     *********************************************************************************/
	@Override
	 public void caseAGroupDefinitions(AGroupDefinitions node)
	 {
		 // We want to grab our internal representation of a node Symbol. 
		 ATypedName typedName = (ATypedName)(node.getTypedName());
		 GroupSymbol sym = (GroupSymbol) Stage.getSymbolForAugmentedName(typedName.getTIdentifier().getText());
		 
		 // We want to get line numbers for any error messages. 
		 int linenumber = typedName.getTIdentifier().getLine(); 
		 
		 // Now we want to make sure the types match up. 
		 Type t = sym.getType();
		 
		 for(TypedListValue val : sym.getGroupValues())
		 {
			 // We want to make sure all our types are correct. 
			 if(val.getType() != t && val.getType() != Type.All)
			 {
				 // If we can't gain the proper type it is an error. 
				 if(!typeInferrable(val, t))
					 MyError.error(this.fileName, ErrorMessages.typeMisMatch(val, t) , linenumber);  
			 }
			 
			 // if it's an event we want to check it out. 
			 if((t == Type.Event) && !typeCheckEvent(val))
				 MyError.error(this.fileName, ErrorMessages.invalidEvent(val.getValue()), linenumber);
		 }
	 }

	/**
	 * We confirm that the names are valid. 
	 */
	private boolean typeCheckEvent(TypedListValue val) 
	{ 	
		// Since these are simple events, we need only check that the names are valid.  
		return EventValidator.isValidEventName(val.getValue());  
		
	}

	/**
	 * Handles misinterpretations by the lexer. 
	 * For example, 10 coupe be an identifier or a month, which is it? 
	 * We assume that if it is used as an identifier it is, and likewise for month. 
	 */
	private boolean typeInferrable(TypedListValue val, Type t) 
	{ 	
		// ID's might have 4 digit numbers or 2 digit numbers 
		if(t == Type.ID)
		{
			// We're fine, it was just a four digit ID. 
			if(val.getType() == Type.Years || val.getType() == Type.Months)
			{
				val.setType(Type.ID);
				return true; 
			}
		}
		
		// Sex groups have values that become male or female. 
		if(t == Type.Sex)
		{
			if(val.getType() == Type.Male || val.getType() == Type.Female)
			{
				return true; 
			}
		}
		
		// Birthyears and Years have the same "Year" type, just pass that along. 
		if(t == Type.Birthyear)
		{
			if(val.getType() == Type.Years)
			{
				val.setType(Type.Birthyear); 
				return true; 
			}
		}
		
		// Event lists might be confused for Diagnosis, we will confirm they are valid events after. 
		if(t == Type.Event)
		{
			if(val.getType() == Type.Diagnosis)
			{
				val.setType(Type.Event);
				return true; 
			}
		}
		
		// There was no lexer type missassignment
		return false; 
	}
	 
	/*********************************************************************************			
     * 			 				 FILTERS 											 *
     *********************************************************************************/
	
	/**
	 * Makes sure that for each filter item, the types match up accordingly. 
	 */
	public boolean validateFilterItem(FilterItemSymbol filterSymbol, String filter)
	{
		
		for(TypedListValue v : filterSymbol.getValues())
		{
			// First we want to validate the types. 
			if(v.getType() != filterSymbol.getType() && v.getType() != Type.All)
			{
				if(!typeInferrable(v, filterSymbol.getType()))
					MyError.error(OncoUtilities.getFileName(), ErrorMessages.typeMisMatch(v, filterSymbol.getType()) , filter);
			}
			
			// Next if it's an event filter, we want to validate the events. 
			if(filterSymbol.getType() == Type.Event)
			{
				if(!typeCheckEvent(v))  
				{
					MyError.error(OncoUtilities.getFileName(), 
							ErrorMessages.invalidEventName(v.getValue()), 
							filter);
				}
				
			}
		}
		return true; 
	}
	
	
	/**
	 * TODO: There is a lot of redundency here. Is there a cleaner solution to this?
	 * Perhaps we do it at a later location. 
	 */
	@Override 
	public void caseAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
	{
		// The symbol contains all the information we need. 
		PopulationSymbol sym = (PopulationSymbol) Stage.getSymbolForAugmentedName(PopulationSymbol.getPopulationSymbolName());
		
		HashMap<String, FilterItemSymbol> filters = sym.getPopulationFilters();
		
		for(String s : filters.keySet())
		{
			FilterItemSymbol filterSymbol = filters.get(s); 
			validateFilterItem(filterSymbol, "Population Filter");
		}
		
	}
	
	@Override 
	public void caseAPeriodFilterFilterDefinitions(APeriodFilterFilterDefinitions node)
	{
		// The symbol contains all the information we need. 
		PeriodSymbol sym = (PeriodSymbol) Stage.getSymbolForAugmentedName(PeriodSymbol.getPeriodSymbolName());
		
		HashMap<String, FilterItemSymbol> filters = sym.getPeriodFilters();
		
		for(String s : filters.keySet())
		{
			FilterItemSymbol filterSymbol = filters.get(s); 
			validateFilterItem(filterSymbol, "Period Filter"); 
		}
	}
	
	@Override 
	public void caseAEventFilterFilterDefinitions(AEventFilterFilterDefinitions node)
	{
		// The symbol contains all the information we need. 
		EventsSymbol sym = (EventsSymbol) Stage.getSymbolForAugmentedName(EventsSymbol.getEventSymbolName());
		
		HashMap<String, FilterItemSymbol> filters = sym.getEventFilters();
		
		for(String s : filters.keySet())
		{
			FilterItemSymbol filterSymbol = filters.get(s); 
			validateFilterItem(filterSymbol, "Events Filter"); 
		}
	}
	
	@Override 
	public void caseADoctorFilterFilterDefinitions(ADoctorFilterFilterDefinitions node)
	{
		// The symbol contains all the information we need. 
		DoctorSymbol sym = (DoctorSymbol) Stage.getSymbolForAugmentedName(DoctorSymbol.getDoctorSymbolName());
		
		HashMap<String, FilterItemSymbol> filters = sym.getDoctorFilters();
		
		for(String s : filters.keySet())
		{
			FilterItemSymbol filterSymbol = filters.get(s); 
			validateFilterItem(filterSymbol, "Doctor Filter"); 
		}
	}
	
	/*********************************************************************************			
     * 			 				 COMPUTATIONS 										 *
     *********************************************************************************/
	
	 @Override
	 public void caseAForeachComputation(AForeachComputation node)
	 {
		// First we want to make sure to increment the current level. 
		Stage.incrementCurrentLevel();
		Stage.incrementForeach();
		
		// We want to first make sure we are not foreaching incorrectly. 
		ActorSymbol actor = new ActorSymbol(node.getActor(), node.getTIdentifier().getText());
		
		// We want to confirm the actor is valid. 
		if(!validateForeachActor(actor))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.actorReused(actor.getObjectType().toString()), 
					node.getTIdentifier().getLine());
		}
		
		 // We need to validate the variable names. 
		if(!validDecleration(actor.getName()))
		{
			MyError.error(OncoUtilities.getFileName(),
					ErrorMessages.invalidRedecleration(actor.getName()),
					node.getTIdentifier().getLine());
		}
		
		// Since the actor is valid, we can add it to the symbol table. 
		Stage.computationsPut(ActorSymbol.actorKey, actor); 
		Stage.computationsPut(actor.getName(), actor);
		Stage.symbolPutForeachSymbol(actor.getName(), actor);
		
		 // Validate the computation inside. 
		node.getComputation().apply(this);
		
		// And then we finally decrement the scope. 
		Stage.decrementCurrentLevel(); 
	
	 }
	 
	 @Override
	 public void caseAForeachSetComputation(AForeachSetComputation node)
	 {
		// First we want to make sure to increment the current level. 
		Stage.incrementCurrentLevel();  
		Stage.incrementForeach();
		
		// We want to first make sure we are not foreaching incorrectly. 
		ActorSymbol actor = new ActorSymbol(node.getActor(), node.getTIdentifier().getText());
		
		// We want to confirm the actor is valid. 
		if(!validateForeachActor(actor))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.actorReused(actor.getObjectType().toString()), 
					node.getTIdentifier().getLine());
		}
		
		 // We need to validate the variable names. 
		if(!validDecleration(actor.getName()))
		{
			MyError.error(OncoUtilities.getFileName(),
					ErrorMessages.invalidRedecleration(actor.getName()),
					node.getTIdentifier().getLine());
		}
		
		// Since the actor is valid, we can add it to the symbol table. 
		Stage.computationsPut(ActorSymbol.actorKey, actor); 
		Stage.computationsPut(actor.getName(), actor); 
		Stage.symbolPutForeachSymbol(actor.getName(), actor);
		
		 // Validate the computation inside. 
		node.getComputationList().apply(this);
		
		// And then we finally decrement the scope. 
		Stage.decrementCurrentLevel(); 
	 }
	 
	 @Override
	 public void caseAForeachTableComputation(AForeachTableComputation node)
	 {
		// First we want to increment the current level. 
		Stage.incrementCurrentLevel(); 
		Stage.incrementForeach();
		 
		// Confirm we are not foreaching incorrectly
		String iterator = node.getIterator().getText();
		String variable = node.getVariable().getText(); 
		
		// The iterator must be a unique name. 
		if(!validDecleration(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getIterator().getLine());
		}
		
		// Validate the table
		Symbol table = Stage.computationsGet(variable); 
		
		if(!(table.getObjectType() == ObjectType.Table))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidObjectType(variable, table.getObjectType(), ObjectType.Table ), 
					node.getVariable().getLine());
		}
		
		// Insert the iterator and variable into the symbol table. 
		Symbol iteratorSymbol = new Symbol();
		iteratorSymbol.setObjectType(ObjectType.Iterator);
		iteratorSymbol.setName(iterator);
		
		Stage.computationsPut(iteratorSymbol.getName(), iteratorSymbol);
		Stage.symbolPutForeachSymbol(iteratorSymbol.getName(), iteratorSymbol);
		 	 
		// Validate the computations.
		node.getComputation().apply(this);
		
		// Decrement the current level. 
		Stage.decrementCurrentLevel();
	 }
	 
	 @Override
	 public void caseAForeachTableSetComputation(AForeachTableSetComputation node)
	 {
		// First we want to increment the current level. 
		Stage.incrementCurrentLevel(); 
		Stage.incrementForeach();
		 
		// Confirm we are not foreaching incorrectly
		String iterator = node.getIterator().getText();
		String variable = node.getVariable().getText(); 
		
		// The iterator must be a unique name. 
		if(!validDecleration(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getIterator().getLine());
		}
		
		// Validate the table
		Symbol table = Stage.computationsGet(variable); 
		
		if(!(table.getObjectType() == ObjectType.Table))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidObjectType(variable, table.getObjectType(), ObjectType.Table ), 
					node.getVariable().getLine());
		}
		
		// Insert the iterator and variable into the symbol table. 
		Symbol iteratorSymbol = new Symbol();
		iteratorSymbol.setObjectType(ObjectType.Iterator);
		iteratorSymbol.setName(iterator);
		
		Stage.computationsPut(iteratorSymbol.getName(), iteratorSymbol);
		Stage.symbolPutForeachSymbol(iteratorSymbol.getName(), iteratorSymbol);
		 	 
		// Validate the computations.
		node.getComputationList().apply(this);
		
		// Decrement the current level. 
		Stage.decrementCurrentLevel();
	 }
	 
	 @Override
	 public void caseAForeachSequenceComputation(AForeachSequenceComputation node)
	 {
		 // First we want to increment the appropriate levels.
		 Stage.incrementForeach();
		 Stage.incrementCurrentLevel();
		 
		 // We want to validate the sequence items. 
		 node.getSequence().apply(this);
		 
		// Confirm we are not foreaching incorrectly
		String iterator = node.getTIdentifier().getText();
			
		// The iterator must be a unique name. 
		if(!validDecleration(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getTIdentifier().getLine());
		}
		
		// Insert the iterator in our symbol table. 
		Symbol iteratorSymbol = new Symbol(); 
		iteratorSymbol.setObjectType(ObjectType.SequenceItem);
		iteratorSymbol.setName(iterator);
	
		Stage.computationsPut(iteratorSymbol.getName(), iteratorSymbol);
		Stage.symbolPutForeachSymbol(iteratorSymbol.getName(), iteratorSymbol);
	
		 // Validate the validate the computations
		node.getComputation().apply(this);
		
		Stage.decrementCurrentLevel(); 
	 }

	@Override
	 public void caseAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
	 {
		// First we want to increment the appropriate levels.
		 Stage.incrementForeach();
		 Stage.incrementCurrentLevel();
		 
		 // We want to validate the sequence items. 
		 node.getSequence().apply(this);
		 
		// Confirm we are not foreaching incorrectly
		String iterator = node.getTIdentifier().getText();
			
		// The iterator must be a unique name. 
		if(!validDecleration(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getTIdentifier().getLine());
		}
		
		// Insert the iterator in our symbol table. 
		Symbol iteratorSymbol = new Symbol(); 
		iteratorSymbol.setObjectType(ObjectType.SequenceItem);
		iteratorSymbol.setName(iterator);
	
		Stage.computationsPut(iteratorSymbol.getName(), iteratorSymbol);
		Stage.symbolPutForeachSymbol(iteratorSymbol.getName(), iteratorSymbol);
	
		 // Validate the validate the computations
		node.getComputationList().apply(this);
		
		Stage.decrementCurrentLevel(); 
	 }
	 
	 @Override
	 public void caseAForeachMemberComputation(AForeachMemberComputation node)
	 {
		 // We want to increment the appropriate levels. 
		 Stage.incrementForeach();
		 Stage.incrementCurrentLevel();
		 
		 // We want to grab the relevant information. 
		 String iterator = node.getName().getText().toString(); 
		 String variable = node.getSequencename().getText().toString(); 
		 
		// The iterator must be a unique name. 
		if(!validDecleration(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getName().getLine());
		}
		
		// Validate the list
		Symbol table = Stage.computationsGet(variable); 
		
		if(!(table.getObjectType() == ObjectType.List))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidObjectType(variable, table.getObjectType(), ObjectType.List ), 
					node.getName().getLine());
		}
		
		// Insert the iterator and variable into the symbol table. 
		Symbol iteratorSymbol = new Symbol();
		iteratorSymbol.setObjectType(ObjectType.SequenceItem);
		iteratorSymbol.setName(iterator);
		
		Stage.computationsPut(iteratorSymbol.getName(), iteratorSymbol);
		Stage.symbolPutForeachSymbol(iteratorSymbol.getName(), iteratorSymbol);
		 	 
		// Validate the computations.
		node.getComputation().apply(this);
		
		// Decrement the current level. 
		Stage.decrementCurrentLevel();
	 }
	 
	 @Override
	 public void caseAForeachMemberSetComputation(AForeachMemberSetComputation node)
	 {
		 // We want to increment the appropriate levels. 
		 Stage.incrementForeach();
		 Stage.incrementCurrentLevel();
		 
		 // We want to grab the relevant information. 
		 String iterator = node.getName().getText().toString(); 
		 String variable = node.getSequencename().getText().toString(); 
		 
		// The iterator must be a unique name. 
		if(!validDecleration(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getName().getLine());
		}
		
		// Validate the list
		Symbol table = Stage.computationsGet(variable); 
		
		if(!(table.getObjectType() == ObjectType.List))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidObjectType(variable, table.getObjectType(), ObjectType.List ), 
					node.getName().getLine());
		}
		
		// Insert the iterator and variable into the symbol table. 
		Symbol iteratorSymbol = new Symbol();
		iteratorSymbol.setObjectType(ObjectType.SequenceItem);
		iteratorSymbol.setName(iterator);
		
		Stage.computationsPut(iteratorSymbol.getName(), iteratorSymbol);
		Stage.symbolPutForeachSymbol(iteratorSymbol.getName(), iteratorSymbol);
		 	 
		// Validate the computations.
		node.getComputationList().apply(this);
		
		// Decrement the current level. 
		Stage.decrementCurrentLevel();
	 }
	 
	 @Override
	 public void caseAPrintComputation(APrintComputation node)
	 {
		 // Printing can either be for Actor Symbols or Sequence Symbol. 
		 // TODO: More involved checks needed. 
		 if(!Stage.computationsContain(node.getTIdentifier().getText()))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.variableNotDefined(node.getTIdentifier().getText()), 
					 node.getTIdentifier().getLine());
		 } 
	 }
	 
	 @Override
	 public void caseAPrintAttrComputation(APrintAttrComputation node)
	 { 
		 // First we need to validate that the variable exists and is of the proper type. 
		 String name 	= node.getTIdentifier().getText(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		 // Does the variable exist?
		 if(!Stage.computationsContain(name))
		 {
			 MyError.error(OncoUtilities.getFileName(),
					 ErrorMessages.variableNotDefined(name),
					 linenumber);
			 return; 
		 }
		 
		 // Now we want to make sure it's an Actor 
		 Symbol actorSymbol = Stage.computationsGet(name); 
		 
		 if(!Symbol.isActor(actorSymbol))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.invalidObjectType(name,ObjectType.Actor, actorSymbol.getObjectType()),
					 linenumber);
			 return; 
		 }
		 
		 // Now we want to confirm that it's contents are valid. 
		 for(PType type : node.getAttrList())
		 {
			 if(!validAttribute(actorSymbol, Symbol.convertToType(type)))
			 {
				 MyError.error(OncoUtilities.getFileName(), 
						 ErrorMessages.invalidActorType(actorSymbol.getObjectType(), Symbol.convertToType(type)), 
						 linenumber);
				 
				 return; 
			 }
		 }
		 
	 }
	 
	 @Override
	 public void caseAPrintTimelineComputation(APrintTimelineComputation node)
	 {
		 // First we want to make sure the variable has been declared and is a Patient. 
		 String name = node.getTIdentifier().getText(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		// Does the variable exist?
		 if(!Stage.computationsContain(name))
		 {
			 MyError.error(OncoUtilities.getFileName(),
					 ErrorMessages.variableNotDefined(name),
					 linenumber);
			 return; 
		 }
		 
		 // Now we confirm its a patient. 
		 Symbol s = Stage.computationsGet(name);
		 
		 if(s.getObjectType() != ObjectType.Patient)
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 	ErrorMessages.invalidTimeLinePrint(name, s.getObjectType(), ObjectType.Patient), 
					 	linenumber);
			 
			 return;
		 }
	 }
	 
	 @Override
	 public void caseAPrintLengthComputation(APrintLengthComputation node)
	 {
		// First we want to make sure the variable has been declared and is a Patient. 
		 String name = node.getTIdentifier().getText(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		// Does the variable exist?
		 if(!Stage.computationsContain(name))
		 {
			 MyError.error(OncoUtilities.getFileName(),
					 ErrorMessages.variableNotDefined(name),
					 linenumber);
			 return; 
		 }
		 
		 // Now we confirm its a patient. 
		 Symbol s = Stage.computationsGet(name);
		 
		 if(s.getObjectType() != ObjectType.Table)
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 	ErrorMessages.invalidLengthOfPrint(name, s.getObjectType(), ObjectType.Table), 
					 	linenumber);
			 
			 return;
		 }
	 }
	 
	 @Override
	 public void caseAPrintTableitemComputation(APrintTableitemComputation node)
	 {
		// Confirm we are not foreaching incorrectly
		String iterator = node.getIterator().getText();
		String variable = node.getVariable().getText(); 
		
		// The iterator must already have been defined. 
		if(!Stage.computationsContain(iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidRedecleration(iterator)
					, node.getIterator().getLine());
		}
		
		// Validate the table and the iterator type. 
		Symbol table = Stage.computationsGet(variable);
		Symbol iter  = Stage.computationsGet(iterator); 
		
		if(!(table.getObjectType() == ObjectType.Table))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidObjectType(variable, table.getObjectType(), ObjectType.Table ), 
					node.getVariable().getLine());
			return; 
		}
		
		if(!(iter.getObjectType() == ObjectType.Iterator))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidObjectType(iterator, iter.getObjectType(), ObjectType.Iterator), 
					node.getIterator().getLine());
			
			return; 
		}
	 }
	 
	 @Override
	 public void caseATableComputation(ATableComputation node)
	 {
		 
		 String name = node.getTIdentifier().getText(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		 // First we want to make sure the name is not already used. 
		 if(Stage.computationsContain(name))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.invalidRedecleration(name), 
					 linenumber);
			 return; 
		 }
		 
		 // Now we want to make sure we are in the top level scope. 
		 if(Stage.getCurrentLevel() != 0)
		 {
			 
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.tableNotDeclaredTopScope(name), 
					 linenumber);
			 
			 return; 
		 }
		 
		 // Now we want to make sure we are counting proper attributes of the actor. 
		 ActorSymbol actor = new ActorSymbol(node.getActor(), node.getTIdentifier().getText());
		 Type type = Symbol.convertToType(node.getType()); 
		 
		 if(!validAttribute(actor, type))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.invalidActorType(actor.getObjectType(), type), 
					 linenumber);
			 
			 return; 
		 }
		 
		 // We now can put in our new symbol into our symbol table. 
		 Symbol table = new Symbol(); 
		 table.setObjectType(ObjectType.Table);
		 table.setName(name);
		 table.setLineNumber(linenumber);
		 table.setType(type);
		 
		 Stage.computationsPut(table.getName(), table);
		 Stage.symbolsPut(table.getName(), table); 
	 }
	 
	 @Override
	 public void caseABarchartComputation(ABarchartComputation node)
	 {
		 // We want to confirm the table exists and is about to be used. 
		 String name = node.getTIdentifier().getText(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		 // First we want to make sure the name is not already used. 
		 if(!Stage.computationsContain(name))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.invalidRedecleration(name), 
					 linenumber);
			 return; 
		 }
		 
		 Symbol table = Stage.computationsGet(name); 
		 
		 // Now we want to confirm that it is of type Barchart. 
		 if(!(table.getObjectType() == ObjectType.Table))
		 {
		 	MyError.error(OncoUtilities.getFileName(), 
		 			ErrorMessages.invalidTableBarchart(name, table.getObjectType(), ObjectType.Table ), 
		 			linenumber);
		 }
	 }
	 
	 @Override
	 public void caseAListComputation(AListComputation node)
	 {
		 // We want to make sure the name has not already been declared.
		 String name = node.getTIdentifier().getText(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		// First we want to make sure the name is not already used. 
		 if(Stage.computationsContain(name))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.invalidRedecleration(name), 
					 linenumber);
			 return; 
		 }
		 
		Symbol list = new Symbol(); 
		list.setObjectType(ObjectType.List);
		list.setLineNumber(linenumber);
		list.setName(name);
		
		Stage.computationsPut(name, list);
		Stage.symbolsPut(name, list);
		 
		 // We want to validate the sequence items. 
		 node.getSequence().apply(this);
		 
	 }
	 
	 
	 @Override
	 public void caseANoparamEventItem(ANoparamEventItem node)
	 {
		 String eventName = node.getTIdentifier().getText().trim(); 
		 int linenumber = node.getTIdentifier().getLine(); 
		 
		 // First we want to make sure that it's a valid event name. 
		 if(!EventValidator.isValidEventName(eventName))
		 {
			 MyError.error(OncoUtilities.getFileName(), 
					 ErrorMessages.invalidEventName(eventName), 
					 linenumber);
		 }
	 }
	 
	 @Override
	 public void caseAParamEventItem(AParamEventItem node)
	 { 
		String eventName = node.getTIdentifier().getText().trim(); 
		int linenumber = node.getTIdentifier().getLine(); 
		
		// First we want to make sure that it's a valid event name. 
		if(!EventValidator.isValidEventName(eventName))
		{
			MyError.error(OncoUtilities.getFileName(), 
					ErrorMessages.invalidEventName(eventName), 
					linenumber);
		}
		 
		 // TODO: We need to deal with parameters, they are triickkkyyy. 
	 }
	 
	 
	 /*********************************************************************************			
	  * 			 				 Utility Methods 								  *
	  *********************************************************************************/
	 
	/**
	 * We validate that the actor is not being used incorrectly. 
	 * I.e. a Doctor nested in a Doctor. 
	 */
	public boolean validateForeachActor(ActorSymbol actor)
	{			
		// We need to make sure out Actor Types don't overlap. 
		for(int i = 0; i < Stage.getCurrentLevel(); i++)
		{
			ActorSymbol s = (ActorSymbol) Stage.computationsGet(ActorSymbol.actorKey); 
			
			if(!(s == null))
				if(s.getObjectType() == actor.getObjectType())
					return false; 
		}
		
		
		return true; 
	}

	/**
	 * Simply checks to make sure that the variable has not been redeclared. If it has, return false. 
	 */
	public boolean validDecleration(String variableName)
	{
		// If the symbol table contains the variable name then it is an invalid name. 
		if(Stage.computationsContain(variableName))
		{
			return false;
		}
		else
		{ 
			return true;
		}
	}
	
	/** 
	 * We need to confirm that the attributes for the actors are valid.  
	 */
	public boolean validAttribute(Symbol actor, Type type)
	{
		if(actor.getObjectType() == ObjectType.Doctor)
		{
			if(type != Type.ID)
			{
				return false; 
			}
		}
		else if(actor.getObjectType() == ObjectType.Patient)
		{
			if(type != Type.Birthyear &&
					type != Type.Sex &&
					type != Type.Diagnosis &&
					type != Type.Postalcode &&
					type != Type.ID)
			{
				return false; 
			}
		}
		else // Diagnosis
		{
			if(type != Type.Diagnosis)
			{
				return false; 
			}
		}
		
		return true; 
	}
}
