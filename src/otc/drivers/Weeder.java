package otc.drivers;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.node.*;

public class Weeder extends DepthFirstAdapter 
{
	private static String filename = "";
	private static String filepath = ""; 

	/**
	 * Simple constructor for a Weeder object. 
	 * @param name - The name of the program. 
	 */
	public Weeder(String name)
	{
		filename = name.substring(name.lastIndexOf("/")+1, name.length()-5);
		
	}
	
	/**
	 * The Visitor method that is called in order to weed the program files. 
	 * @param theProgram
	 */
	public static void weed(LinkedList<ProgramFile> theProgram) 
	{
		Iterator<ProgramFile> iter = theProgram.iterator();
		ProgramFile programFile;
		Node ast;
		
		while (iter.hasNext()) 
		{
			programFile = (ProgramFile)iter.next();
			ast = programFile.getAst();
			filepath = programFile.getFilePath(); 
			ast.apply(new Weeder(programFile.getName()));
		}
	}
	
	
	/**
	 * Validates the Program Name. 
	 * Script filename must match the program name. 
	 */
	@Override
	public void caseTTScriptName(TTScriptName node)
	{
		String script = node.getText().toString().trim();
       
        if (!filename.equals(script)) 
            MyError.error(filename, ErrorMessages.scriptNameMismatch, node.getLine());
	}
	
	
	/**
	 * Validates group file names.
	 * TODO: What validations do we do here? 
	 */
	@Override
	public void caseTTGroupFile(TTGroupFile node)
	{
	 
	}
	
    // All non-token nodes
    public void defaultOut(Node node)
    {
		//System.out.println(node.getClass());
    }

    // All tokens
    public void defaultCase(Node node)
    {
    	//System.out.println(node.getClass());
    }
}
