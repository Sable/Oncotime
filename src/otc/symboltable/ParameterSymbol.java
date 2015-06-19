package otc.symboltable;

import otc.node.ATypedName;
import otc.node.PTypedName;

public class ParameterSymbol extends Symbol
{
	private String name;
	public String getName(){return this.name;}
	
	/**
	 * Constructor takes the typed name symbol and converts it. 
	 * @param node
	 */
	public ParameterSymbol(PTypedName node)
	{
		// Sets the actual ObjectType
		setObjectType(ObjectType.Parameter); 
		
		// Sets the actual parameter type. 
		setType(super.convertToType((ATypedName)node));
		
		// Sets the name.
		this.name = ((ATypedName)node).getTIdentifier().getText(); 
	}
}
