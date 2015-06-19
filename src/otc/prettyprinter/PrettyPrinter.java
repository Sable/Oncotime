package otc.prettyprinter;

import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.drivers.OncoCleaner;
import otc.drivers.ProgramFile;
import otc.node.*;
import otc.symboltable.DoctorSymbol;
import otc.symboltable.EventsSymbol;
import otc.symboltable.PeriodSymbol;
import otc.symboltable.PopulationSymbol;
import otc.symboltable.Stage;

public class PrettyPrinter extends DepthFirstAdapter 
{
	private int currentTabLevel = 0; 
	private static String program = ""; 
	
	/**
	 * The visitor method that starts the traversal for the program.  
	 * @param theProgram
	 */
	public static void prettyPrint(LinkedList<ProgramFile> theProgram) 
	{ 	
		Iterator<ProgramFile> iter = theProgram.iterator();
		ProgramFile programFile;
		Node ast;
		
		while (iter.hasNext()) 
		{
			programFile = (ProgramFile)iter.next();
			ast = programFile.getAst();  
			ast.apply(new PrettyPrinter());
		}
		
		System.out.println(program); 
	}
	
	@Override 
	public void caseAHeader(AHeader node)
	{
		 program += "Script Name: " + node.getName().getText() + "\n"; 
		 program += "Parameters: " + node.getParameters() + "\n";
		 
		 // We clean the documentation comment. 
		 program += "Documentation Comment: \n" + node.getScriptComment().getText() +"\n"; 
		 
	}
	
	@Override
	public void caseAGroupDefinitions(AGroupDefinitions node)
	{ 	
		ATypedName t = ((ATypedName)node.getTypedName()); 
		program += Stage.getSymbolForAugmentedName(t.getTIdentifier().getText()) + "\n";   
	}
	
	@Override
	public void caseAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
	{
		program += Stage.getSymbolForAugmentedName(PopulationSymbol.getPopulationSymbolName()); 
		program += Stage.getSymbolForAugmentedName(PeriodSymbol.getPeriodSymbolName());
		program += Stage.getSymbolForAugmentedName(DoctorSymbol.getDoctorSymbolName()); 
		program += Stage.getSymbolForAugmentedName(EventsSymbol.getEventSymbolName()); 
	}
}
