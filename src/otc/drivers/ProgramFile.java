package otc.drivers;

import otc.node.Node;




public class ProgramFile 
{
	private Node ast;
	private String name;
	private String filepath; 

  /**
   * Defines a Program with a specific name and an AST tree.
   */
	public ProgramFile(String name, Node ast) 
	{
		this.name = name;
		this.ast = ast;
		this.filepath = OncoUtilities.getFilePathOf(name); 
	}
	
	public Node getAst() { return ast; }
	public String getName() { return name; }
	public String getFilePath() { return filepath;}
}