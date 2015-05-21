package otc.drivers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.node.AForeachComputation;
import otc.node.AForeachSequenceComputation;
import otc.node.AForeachSequenceSetComputation;
import otc.node.AForeachSetComputation;
import otc.node.AForeachTableComputation;
import otc.node.AForeachTableSetComputation;
import otc.node.AGroupDefinitions;
import otc.node.AHeader;
import otc.node.ATypedName;
import otc.node.Node;
import otc.node.PTypedName;

/**
 * This file expands the OncoTime program, constructs the Symbol Table along the way. 
 * The symbol table assigns new names to all the variables, dependenent on scope etc. 
 * @author vikramsundaram
 */
public class OncoCleaner extends DepthFirstAdapter 
{
	// Keeps track of the number of variables with the same name. 
	private HashMap<String, Integer> nameCount = new HashMap<String, Integer>(); 
	
	
	/**
	 * Constructor
	 */
	public OncoCleaner()
	{
		
	}
	
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
			ast.apply(new OncoCleaner());
		}
	}
	
	/**
	 * This function either creates a new entry in name count or simply augments the count. s
	 */
	public void incrementNameCount(String name)
	{
		// First we see if the name exists already.
		// Adding it if not. 
		if(!nameCount.containsKey(name))
		{
			nameCount.put(name, 0); 
		}
		else
		{
			// We increment the counter if it's already there. 
			nameCount.put(name, nameCount.get(name) + 1); 
		}
	}
	
	/**
	 * Augments the name. 
	 * @param name
	 * @return
	 */
	public String augmentedName(String name)
	{
		return name + "_" + nameCount.get(name); 
	}
	
	
	@Override 
	public void caseAGroupDefinitions(AGroupDefinitions node)
	{
		// Update the name. 
		ATypedName typedName = (ATypedName) node.getTypedName();
		String name = typedName.getTIdentifier().getText(); 
		
		// Increment the name count. 
		incrementNameCount(name); 
		
		// Now we get the value and augment the name. 
		typedName.getTIdentifier().setText(augmentedName(name));
		
		System.out.println("Augmented Group Name: " + typedName.getTIdentifier().getText()); 
	}
	
	@Override
	public void caseAHeader(AHeader node)
	{	
		// We need to update the parameters.
		for(PTypedName typedName : node.getParameters())
		{
			// Increment the Name count. 
			String name = ((ATypedName)typedName).getTIdentifier().getText();
			incrementNameCount(name); 
			
			// Now we want to update the name. 
			((ATypedName)typedName).getTIdentifier().setText(augmentedName(name));
			
			System.out.println("Augmented Param Name: " + ((ATypedName)typedName).getTIdentifier().getText()); 
		}
	}
	
	@Override
	public void caseAForeachComputation(AForeachComputation node)
	{
		// We need to update the Name. 
		String name = node.getTIdentifier().getText(); 
		incrementNameCount(name); 
		node.getTIdentifier().setText(augmentedName(name));
		System.out.println("Augmented Foreach Iterator Name: " + node.getTIdentifier().getText()); 
	}
	
	@Override
	public void caseAForeachSetComputation(AForeachSetComputation node)
	{
		// We need to update the Name. 
		String name = node.getTIdentifier().getText(); 
		incrementNameCount(name); 
		node.getTIdentifier().setText(augmentedName(name));
		System.out.println("Augmented Foreach Set Iterator Name: " + node.getTIdentifier().getText()); 
	}
	
	@Override
	public void caseAForeachTableComputation(AForeachTableComputation node)
	{
		// We need to update the Name. 
		String name = node.getIterator().getText(); 
		incrementNameCount(name); 
		node.getIterator().setText(augmentedName(name));
		System.out.println("Augmented Foreach Table Iterator Name: " + node.getIterator().getText()); 
	}
	
	@Override
	public void caseAForeachTableSetComputation(AForeachTableSetComputation node)
	{
		// We need to update the Name. 
		String name = node.getIterator().getText(); 
		incrementNameCount(name); 
		node.getIterator().setText(augmentedName(name));
		System.out.println("Augmented Foreach Table Set Iterator Name: " + node.getIterator().getText()); 
	}
	
	@Override
	public void caseAForeachSequenceComputation(AForeachSequenceComputation node)
	{
		// We need to update the Name. 
		String name = node.getTIdentifier().getText(); 
		incrementNameCount(name); 
		node.getTIdentifier().setText(augmentedName(name));
		System.out.println("Augmented Foreach Sequence Iterator Name: " + node.getTIdentifier().getText());
	}
	
	@Override
	public void caseAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
	{
		// We need to update the Name. 
		String name = node.getTIdentifier().getText(); 
		incrementNameCount(name); 
		node.getTIdentifier().setText(augmentedName(name));
		System.out.println("Augmented Foreach Sequence Set Iterator Name: " + node.getTIdentifier().getText());
	}
	
	
}
