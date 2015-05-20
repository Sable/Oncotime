package otc.drivers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import otc.analysis.ReversedDepthFirstAdapter;
import otc.node.Node;
import otc.node.Token;

public class Lines extends ReversedDepthFirstAdapter
{
    public static void setLines(LinkedList<ProgramFile> theProgram)
    {
		ProgramFile programFile;
		Iterator<ProgramFile> iter = theProgram.iterator();
		Node ast;
		Lines lineSetter = new Lines();
	
		while (iter.hasNext()) 
		{
		    programFile = (ProgramFile)iter.next();
		    ast = programFile.getAst();
		    ast.apply(lineSetter);
		}
    }

    static public int getLine(Node node)
    {
    	return ((Integer) lines.get(node)).intValue();
    }

    static public int getPos(Node node)
    {
    	return ((Integer) positions.get(node)).intValue();
    }


    static private int last_line = -1;
    static private int last_pos = -1;

    static private final Map<Node, Integer> lines = new HashMap<Node, Integer>();
    static private final Map<Node, Integer> positions = new HashMap<Node, Integer>();

    // All non-token nodes
    public void defaultOut(Node node)
    {
		lines.put(node, new Integer(last_line));
		positions.put(node, new Integer(last_pos));
    }

    // All tokens
    public void defaultCase(Node node)
    {
		Token token = (Token) node;
		last_line = token.getLine();
		last_pos = token.getPos();
		lines.put(node, new Integer(last_line));
		positions.put(node, new Integer(last_pos));
    }

}