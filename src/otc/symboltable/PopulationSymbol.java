package otc.symboltable;

import java.util.HashMap;

import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.node.PTypedList;
import otc.symboltable.Symbol.Type;

/**
 * Represents and stores information for the Population Filter. 
 * @author vikramsundaram
 *
 */
public class PopulationSymbol extends Symbol 
{
	private static String PopulationSymbolName = "PopulationFilter";
	private HashMap<String, FilterItemSymbol> populationFilters = new HashMap<String, FilterItemSymbol>();
	
	public HashMap<String, FilterItemSymbol> getPopulationFilters(){return populationFilters;}
	
	public static String getPopulationSymbolName(){return PopulationSymbolName;}
	
	public PopulationSymbol()
	{ 
		this.objectType = ObjectType.Filter; 
		this.type = Type.PopulationFilter; 
		
		// Construct the basic values.
		constructPopulationFilters();
	}
	
	private void constructPopulationFilters()
	{
		FilterItemSymbol id = new FilterItemSymbol(Type.ID);
		populationFilters.put(Type.ID.toString(), id); 
	
		FilterItemSymbol sex = new FilterItemSymbol(Type.Sex);
		populationFilters.put(Type.Sex.toString(), sex); 
		
		FilterItemSymbol diagnosis = new FilterItemSymbol(Type.Diagnosis);
		populationFilters.put(Type.Diagnosis.toString(), diagnosis); 
		
		FilterItemSymbol birthyear = new FilterItemSymbol(Type.Birthyear);
		populationFilters.put(Type.Birthyear.toString(), birthyear); 
		
		FilterItemSymbol postalcode = new FilterItemSymbol(Type.Postalcode);
		populationFilters.put(Type.Postalcode.toString(), postalcode); 
	}
	
	public String toString()
	{
		String s = "\nPopulation is \n \t";
		s += getFilterValues(); 
		
		return(s);  
	}
	
	private String getFilterValues()
	{
		String returnString = ""; 
		String newLineTab = "\n\t"; 
		
		for(String s : populationFilters.keySet())
		{
			returnString += newLineTab + populationFilters.get(s);  
		}
		
		return returnString + "\n";
	}

	public void addFilterValues(Type t, PTypedList typedList) 
	{
		FilterItemSymbol symbol = populationFilters.get(t.toString());  
		
		// Not a valid type
		if(symbol == null)
		{
			MyError.error(OncoUtilities.getFileName(), ErrorMessages.invalidFilter(t, "Population"), "Population Filter");
			return; 
		}
		
		symbol.addValues(typedList); 
	}
}
