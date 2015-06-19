package otc.symboltable;

import java.util.HashMap;

import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.node.PTypedList;
import otc.symboltable.Symbol.Type;

public class DoctorSymbol extends Symbol 
{

	private static String DoctorSymbolName;
	private HashMap<String, FilterItemSymbol> docFilters = new HashMap<String, FilterItemSymbol>();
	
	public static String getDoctorSymbolName(){return DoctorSymbolName;}
	
	public DoctorSymbol()
	{
		// The name of the population symbol. 
		DoctorSymbolName = "DoctorFilter"; 
		
		this.objectType = ObjectType.Filter; 
		this.type = Type.DoctorFilter;
		
		// Construct the basic values.
		constructDoctorFilters();
	}
	
	private void constructDoctorFilters()
	{
		FilterItemSymbol ids = new FilterItemSymbol(Type.ID);
		docFilters.put(Type.ID.toString(), ids); 
	}
	
	public String toString()
	{
		return "Doctors are \n \t" + getFilterValues(); 
	}
	
	private String getFilterValues()
	{
		String returnString = ""; 
		String newLineTab = "\n\t"; 
		
		for(String s : docFilters.keySet())
		{
			returnString += docFilters.get(s) + newLineTab; 
		}
		
		return returnString + "\n";
	}

	public void addFilterValues(Type t, PTypedList typedList) 
	{
		FilterItemSymbol symbol = docFilters.get(t.toString()); 
		
		// Not a valid type
		if(symbol == null)
		{
			MyError.error(OncoUtilities.getFileName(), ErrorMessages.invalidFilter(t, "Doctor "), "Doctor Filter");
			return; 
		}
		
		symbol.addValues(typedList); 
		
	}

	public HashMap<String, FilterItemSymbol> getDoctorFilters() {
		return docFilters; 
	}
	
}
