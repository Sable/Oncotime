package otc.symboltable;

import java.util.HashMap;

import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.node.PTypedList;
import otc.symboltable.Symbol.Type;

public class EventsSymbol extends Symbol 
{
	private static String EventSymbolName;
	private HashMap<String, FilterItemSymbol> eventsFilters = new HashMap<String, FilterItemSymbol>();
	
	public static String getEventSymbolName(){return EventSymbolName;}
	
	public EventsSymbol()
	{
		// The name of the population symbol. 
		this.EventSymbolName = "EventsFilter"; 
		
		this.objectType = ObjectType.Filter; 
		this.type = Type.EventsFilter;
		
		// Construct the basic values.
		constructEventsFilters();
	}
	
	private void constructEventsFilters()
	{
		FilterItemSymbol events = new FilterItemSymbol(Type.Event);
		eventsFilters.put(Type.Event.toString(), events); 
	}
	
	public String toString()
	{
		return "Events are \n \t" + getFilterValues(); 
	}
	
	private String getFilterValues()
	{
		String returnString = ""; 
		String newLineTab = "\n\t"; 
		
		for(String s : eventsFilters.keySet())
		{
			returnString += eventsFilters.get(s) + newLineTab; 
		}
		
		return returnString;
	}

	public void addFilterValues(Type t, PTypedList typedList) 
	{
		FilterItemSymbol symbol = eventsFilters.get(t.toString()); 
		
		
		// Not a valid type
		if(symbol == null)
		{
			MyError.error(OncoUtilities.getFileName(), ErrorMessages.invalidFilter(t, "Events "), "Events Filter");
			return; 
		}
		
		symbol.addValues(typedList); 
		
	}

	public HashMap<String, FilterItemSymbol> getEventFilters() 
	{
		return eventsFilters; 
	}
	
	public String getEventsAsStrings()
	{
		StringBuilder sb = new StringBuilder(); 
	
		// Get all our events. 
		for(String s : eventsFilters.keySet())
		{
			FilterItemSymbol sym = eventsFilters.get(s);  
		
			if(sym.getValues().size() == 0)
			{
				sb.append("\"");
				sb.append("*"); 
				sb.append("\"");
				sb.append(",");
			}
			else
			{
				for(TypedListValue val : sym.getValues())
				{
					sb.append("\""); 
					sb.append(val.getValue()); 
					sb.append("\"");
					sb.append(","); 
				}
			}
		}
		
		sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");  
		
		return sb.toString(); 
		
	}
}
