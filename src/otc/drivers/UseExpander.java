package otc.drivers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.lexer.Lexer;
import otc.lexer.LexerException;
import otc.node.ADependencies;
import otc.node.AGroupDefinitions;
import otc.node.AGroupfileProgram;
import otc.node.AHeader;
import otc.node.AOncoprogramProgram;
import otc.node.Node;
import otc.node.PDependencies;
import otc.node.PGroupDefinitions;
import otc.node.Start;
import otc.node.TTGroupFile;
import otc.parser.Parser;
import otc.parser.ParserException;


/**
 * This is O(n) in the number of .grp files, I believe. Since we don't traverse the tree other than the Program Node.  
 * @author vikramsundaram
 *
 */
public class UseExpander extends DepthFirstAdapter 
{
	private LinkedList<AGroupDefinitions> includedGroups = new LinkedList<AGroupDefinitions>();
	private String fileLocation = ""; 
	
	/**
	 * 
	 * @param scriptLocation
	 */
	public UseExpander(String scriptLocation)
	{
		this.fileLocation = scriptLocation; 
	}
	
	/**
	 * We simple want to include all our "used" group files. 
	 * @param theProgram
	 */
	public static void expandGroups(LinkedList<ProgramFile> theProgram) 
	{
		Iterator<ProgramFile> iter = theProgram.iterator();
		ProgramFile programFile;
		Node ast;
		
		while (iter.hasNext()) 
		{
			programFile = (ProgramFile)iter.next();
			
			// Store the current files location. 
			programFile.getFilePath(); 
			ast = programFile.getAst(); 
			ast.apply(new UseExpander(programFile.getFilePath()));
		}
	}
	
	/** 
	 * Since we are doing a depth first traversal of our program, we will hit this node last. 
	 * Our groups will already be included, and we can simply add them to our group definitions list. 
	 */
	@Override
	public void caseAOncoprogramProgram(AOncoprogramProgram node)
	{	
		// Grab and store a LinkedList of group definitions. 
		expandUses(((AHeader)node.getHeader()).getUses()); 
		
		for(AGroupDefinitions g : includedGroups)
		{
			// We want to include it first, since uses are done before group declerations. 
			node.getGroupDefinitions().addFirst(g);
		}
	}
	
	/**
	 * Used when we hit a use group node.  
	 */
	public void expandUses(LinkedList<PDependencies> dependencies)
	{	
		for(PDependencies useStatement : dependencies)
		{
			for(TTGroupFile file : ((ADependencies)useStatement).getTGroupFile())
			{
				includeGroupsInFile(file);  
			}	
		}
	}
	

	/**
	 * Takes the name of a file. Determines it's location relative to the .onc file,
	 * 	expands it, and includes the contents as Nodes in includedGroups. 
	 * @param file
	 */
	private void includeGroupsInFile(TTGroupFile file) 
	{
		// We first just want the location of the group file. 
		String absoluteFileLocation = fileLocation + file.getText();
		
		// TODO: We should throw a proper error message if the file does not exist in that location.
		// We now want to find the file, open it, and parse it.
		Parser p;
		try 
		{	
			// Grab the file, and create the tree. 
			p = new Parser (new Lexer(new PushbackReader(new FileReader(absoluteFileLocation),5000)));
			Start tree = p.parse();
			
			// We want to add the new group decleration to our list. 
			for(PGroupDefinitions g : ((AGroupfileProgram) tree.getPProgram()).getGroupDefinitions())
			{	
				includedGroups.addFirst((AGroupDefinitions)g);
			}
			
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (ParserException e) 
		{
			e.printStackTrace();
		} catch (LexerException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	

}
