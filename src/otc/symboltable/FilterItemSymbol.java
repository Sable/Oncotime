package otc.symboltable;

import java.util.LinkedList;

import otc.node.PTypedList;

public class FilterItemSymbol extends Symbol 
{
	private LinkedList<TypedListValue> filterValues = new LinkedList<TypedListValue>();
	
	public FilterItemSymbol(Type t)
	{
		// Set the type of this FilterItemSymbol. 
		setType(t); 
	}
	
	@Override
	public String toString()
	{
		return getType().toString() + ": " + filterValues; 
	}

	public void addValues(PTypedList typedList) 
	{
		// TODO: Remove duplicates / clean etc. 
		filterValues.addAll(Symbol.convertToTypeList(typedList));
	}
	
	public LinkedList<TypedListValue> getValues()
	{
		return filterValues; 
	}
}
