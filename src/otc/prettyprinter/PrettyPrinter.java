package otc.prettyprinter;

import org.sablecc.sablecc.analysis.DepthFirstAdapter;

import otc.node.*;

public class PrettyPrinter extends DepthFirstAdapter 
{
	public void castTTScript(TTScript node)
	{
		System.out.println(node.toString());
	}
}
