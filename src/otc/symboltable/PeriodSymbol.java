package otc.symboltable;

import java.util.HashMap;

import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.node.PTypedList;
import otc.symboltable.Symbol.Type;

public class PeriodSymbol extends Symbol 
{
	private static String PeriodSymbolName;
	private HashMap<String, FilterItemSymbol> periodFilters = new HashMap<String, FilterItemSymbol>();
	
	public HashMap<String, FilterItemSymbol> getPeriodFilters() {return periodFilters;}
	
	public static String getPeriodSymbolName(){return PeriodSymbolName;}
	
	public PeriodSymbol()
	{
		// The name of the population symbol. 
		this.PeriodSymbolName = "PeriodFilter"; 
		
		this.objectType = ObjectType.Filter; 
		this.type = Type.PeriodFilter;
		
		// Construct the basic values.
		constructPeriodFilters();
	}
	
	private void constructPeriodFilters()
	{
		FilterItemSymbol days = new FilterItemSymbol(Type.Days);
		periodFilters.put(Type.Days.toString(), days); 
	
		FilterItemSymbol months = new FilterItemSymbol(Type.Months);
		periodFilters.put(Type.Months.toString(), months); 
		
		FilterItemSymbol years = new FilterItemSymbol(Type.Years);
		periodFilters.put(Type.Years.toString(), years); 
		
		FilterItemSymbol date = new FilterItemSymbol(Type.Date);
		periodFilters.put(Type.Date.toString(), date); 
		
		FilterItemSymbol time = new FilterItemSymbol(Type.Hours);
		periodFilters.put(Type.Hours.toString(), time);  
	}
	
	public String toString()
	{
		return "Period is \n \t" + getFilterValues(); 
	}
	
	private String getFilterValues()
	{
		String returnString = ""; 
		String newLineTab = "\n\t"; 
		
		for(String s : periodFilters.keySet())
		{
			returnString += periodFilters.get(s) + newLineTab; 
		}
		
		return returnString + "\n";
	}

	public void addFilterValues(Type t, PTypedList typedList) 
	{
		FilterItemSymbol symbol = periodFilters.get(t.toString()); 
		
		// Not a valid type
		if(symbol == null)
		{
			MyError.error(OncoUtilities.getFileName(), ErrorMessages.invalidFilter(t, "Period"), "Period Filter");
			return; 
		}
		
		symbol.addValues(typedList); 
	}

}
