package otc.symboltable;

import java.util.LinkedList;

import otc.node.ADateListItemTypedList;
import otc.node.ADaysListItemTypedList;
import otc.node.AExpandableListItemTypedList;
import otc.node.AFemaleListItemTypedList;
import otc.node.AGroupDefinitions;
import otc.node.AMaleListItemTypedList;
import otc.node.AMonthsListItemTypedList;
import otc.node.ANumberListItemTypedList;
import otc.node.APostalcodeListItemTypedList;
import otc.node.AStringListItemTypedList;
import otc.node.ATypedName;
import otc.node.AYearListItemTypedList;
import otc.node.PTypedList;


/**
 * Group symbol object. 
 * @author vikramsundaram
 */
public class GroupSymbol extends Symbol 
{ 
	private String name;
	private LinkedList<TypedListValue> values; 
	
	/**
	 * Constructor
	 * @param The node from the tree. 
	 */
	public GroupSymbol(AGroupDefinitions node)
	{
		constructSymbolFromNode(node); 
	}
	
	/**
	 * Constructs the given input node into a Symbol. 
	 * @param node
	 */
	private void constructSymbolFromNode(AGroupDefinitions node)
	{
		// Gets the name
		this.name = ((ATypedName)node.getTypedName()).getTIdentifier().getText();
		
		// Sets the type 
		setType(convertToType((ATypedName)node.getTypedName()));
		
		// Sets the Object Type.
		setObjectType(ObjectType.Group);
		
		// Sets the linenumber
		ATypedName ln = (ATypedName) node.getTypedName(); 
		int linenumber = ln.getTIdentifier().getLine(); 
		this.setLineNumber(linenumber);
		
		// Get the list of values. 
		this.values = Symbol.convertToTypeList(node.getTypedList()); 
		
		// We want to remove any duplicates. 
		this.values = Symbol.removeDuplicates(values);
	}
	
	public String toString()
	{
		return "group " + getType() + " " + this.name + " = " + values; 
	}

	/**
	 * Gets the name. 
	 */
	public String getName()
	{
		return this.name; 
	}
	
	/**
	 * Gets the values
	 */
	public LinkedList<TypedListValue> getGroupValues()
	{
		return this.values; 
	}
}
