package otc.drivers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.node.ABarchartComputation;
import otc.node.ADoctorFilterFilterDefinitions;
import otc.node.AEventFilterFilterDefinitions;
import otc.node.AFilterList;
import otc.node.AForeachComputation;
import otc.node.AForeachMemberComputation;
import otc.node.AForeachMemberSetComputation;
import otc.node.AForeachSequenceComputation;
import otc.node.AForeachSequenceSetComputation;
import otc.node.AForeachSetComputation;
import otc.node.AForeachTableComputation;
import otc.node.AForeachTableSetComputation;
import otc.node.AGroupDefinitions;
import otc.node.AHeader;
import otc.node.AListComputation;
import otc.node.APeriodFilterFilterDefinitions;
import otc.node.APopulationFilterFilterDefinitions;
import otc.node.APrintAttrComputation;
import otc.node.APrintComputation;
import otc.node.APrintLengthComputation;
import otc.node.APrintTableitemComputation;
import otc.node.APrintTimelineComputation;
import otc.node.ATableComputation;
import otc.node.ATypedName;
import otc.node.Node;
import otc.node.PFilterList;
import otc.node.PTypedName;
import otc.symboltable.Symbol;
import otc.symboltable.GroupSymbol;
import otc.symboltable.ParameterSymbol;
import otc.symboltable.Stage;

/**
 * This file expands the OncoTime program, constructs the Symbol Table along the way. 
 * The symbol table assigns new names to all the variables, dependenent on scope etc. 
 * @author vikramsundaram
 */
public class OncoCleaner extends DepthFirstAdapter 
{	
	/**
	 * The visitor method that starts the traversal for the program.  
	 * @param theProgram
	 */
	public static void clean(LinkedList<ProgramFile> theProgram) 
	{ 	
		Iterator<ProgramFile> iter = theProgram.iterator();
		ProgramFile programFile;
		Node ast;
		
		while (iter.hasNext()) 
		{
			programFile = (ProgramFile)iter.next();
			ast = programFile.getAst(); 
			
			// Set the current FileName 
			OncoUtilities.setFileName(OncoUtilities.getNameOfFileWithoutExtension(programFile.getName()));
			
			ast.apply(new OncoCleaner());
		}
	}
	
	/**
	 * ----------------------------------------------------------
	 * The actual recursive iteration through the tree. 
	 * ----------------------------------------------------------
	 */
	
	/**
	 * These are all the Definitions nodes. 
	 * Here we handle expansion of group nodes. 
	 * All the 'used' group nodes are already expanded. 
	 */
	@Override 
	public void caseAGroupDefinitions(AGroupDefinitions node)
	{
		// Update the name. 
		ATypedName typedName = (ATypedName) node.getTypedName();
		String name = typedName.getTIdentifier().getText(); 
		
		// Increment the name count. 
		Stage.addOrIncrement(name); 
		
		// Now we get the value and augment the name. 
		typedName.getTIdentifier().setText(Stage.getMostRecentName(name));
		
		// We now store the symbol in the table. 
		GroupSymbol symbol = new GroupSymbol(node); 
		Stage.addSymbol(symbol.getName(), symbol);
	}
	
	/**
	 * Here we handle the parameters.
	 * TODO: We can (should) throw an error message if a parameter is reused. 
	 */
	@Override
	public void caseAHeader(AHeader node)
	{	
		// We need to update the parameters.
		for(PTypedName typedName : node.getParameters())
		{
			// Increment the Name count. 
			String name = ((ATypedName)typedName).getTIdentifier().getText();
			Stage.addOrIncrement(name); 
			
			// Now we want to update the name. 
			((ATypedName)typedName).getTIdentifier().setText(Stage.getMostRecentName(name));
			
			// Now we want to store the symbol in the table. 
			ParameterSymbol symbol = new ParameterSymbol(typedName);
			Stage.addSymbol(symbol.getName(), symbol);
		}
	}
	
	/**
	 * We expand all our filters here. 
	 * The stage and the actual filter objects handle the information. 
	 */
	@Override 
	public void caseAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
	{
		// Add the values in this filter definitions to our construction of values.
		Stage.addPopulationFilters(node.getFilterList()); 
	}
	
	@Override 
	public void caseAPeriodFilterFilterDefinitions(APeriodFilterFilterDefinitions node)
	{
		// Add the values in this filter definitions to our construction of values. 
		Stage.addPeriodFilters(node.getFilterList());
	}
	
	@Override 
	public void caseAEventFilterFilterDefinitions(AEventFilterFilterDefinitions node)
	{
		// Add the values in this filter definitions to our construction of values. 
		Stage.addEventsFilters(node.getFilterList());
	}
	
	@Override 
	public void caseADoctorFilterFilterDefinitions(ADoctorFilterFilterDefinitions node)
	{
		// Add the values in this filter definitions to our construction of values. 
		Stage.addDoctorFilters(node.getFilterList());
	}
}
