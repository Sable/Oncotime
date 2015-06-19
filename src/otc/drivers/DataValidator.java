package otc.drivers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

import otc.analysis.DepthFirstAdapter;
import otc.node.Node;
import otc.symboltable.DoctorSymbol;
import otc.symboltable.EventsSymbol;
import otc.symboltable.GroupSymbol;
import otc.symboltable.PeriodSymbol;
import otc.symboltable.PopulationSymbol;
import otc.symboltable.Stage;
import otc.symboltable.Symbol;
import otc.symboltable.Symbol.ObjectType;
import otc.symboltable.TypedListValue;
import otc.typecheck.TypeChecker;

public class DataValidator 
{
	/*********************************************************************************			
     * 			 				TRAVERSE PROGRAM TREE								 *
     *********************************************************************************/
	public DataValidator()
	{
		// The groups and the filters are already stored data, we need to simply analyze them.
		for(String key : Stage.getAllNonComputationKeys())
		{
			validateObject(Stage.getSymbolForAugmentedName(key)); 
		}
	}

	/**
	 * The visitor method that starts the traversal for the program.  
	 * @param theProgram
	 */
	public static void validate(LinkedList<ProgramFile> theProgram) 
	{ 	
		// We currently don't use theProgram, since we are not traversing the tree. 
		DataValidator dv = new DataValidator(); 
	}


/*********************************************************************************			
 * 			 				VALIDATE BASED ON TYPE								 *
 *********************************************************************************/


	/**
	 * This method validates the data for Groups and Filters, based on their type. 
	 * @param symbolForAugmentedName
	 */
	private void validateObject(Symbol symbol) 
	{		
		// Depending on what type it is, we need different traversal methods. 
		if(symbol.getObjectType() == ObjectType.Group)
			validateObjectGroup(symbol);
		if(symbol.getObjectType() == ObjectType.Filter)
			validateObjectFilter(symbol);	
	}
	

	/**
	 * Validates and converts the contents of a group. 
	 * @param symbol
	 */
	private void validateObjectGroup(Symbol symbol) 
	{
		// Now we know it is specifically a group symbol, so we can do smarter operations. 
		GroupSymbol group = (GroupSymbol) symbol; 
		
		// We now simply check the group data.
		for(TypedListValue val : group.getGroupValues())
			validateTypedValue(val, group.getName()); 
	}

	/**
	 * Validates and converts the contents of a filter. 
	 * @param symbol
	 */
	private void validateObjectFilter(Symbol symbol) 
	{
		// Now we know it is specifically a filter symbol, so we can do smarter operations.
		switch(symbol.getType())
		{
			case PeriodFilter:
			{
				PeriodSymbol filter = (PeriodSymbol) symbol;
				
				// For each of the filter types. 
				for(String s : filter.getPeriodFilters().keySet())
					// For each of the values
					for(TypedListValue val : filter.getPeriodFilters().get(s).getValues())
						// Now we simply need to check the data. 
						validateTypedValue(val, symbol.getName()); 
				
				break; 
			}
			
			case PopulationFilter:
			{
				PopulationSymbol filter = (PopulationSymbol) symbol;
				
				// For each of the filter types. 
				for(String s : filter.getPopulationFilters().keySet())
					// For each of the values
					for(TypedListValue val : filter.getPopulationFilters().get(s).getValues())
						// Now we simply need to check the data. 
						validateTypedValue(val, symbol.getName()); 
				
				
				break; 
			}
			
			case EventsFilter:
			{
				EventsSymbol filter = (EventsSymbol) symbol; 
				
				// For each of the filter types. 
				for(String s : filter.getEventFilters().keySet())
					// For each of the values
					for(TypedListValue val : filter.getEventFilters().get(s).getValues())
						// Now we simply need to check the data. 
						validateTypedValue(val,symbol.getName()); 
				
				break; 
			}
			
			case DoctorFilter:
			{
				DoctorSymbol filter = (DoctorSymbol) symbol;
				
				// For each of the filter types. 
				for(String s : filter.getDoctorFilters().keySet())
					// For each of the values
					for(TypedListValue val : filter.getDoctorFilters().get(s).getValues())
						// Now we simply need to check the data. 
						validateTypedValue(val, symbol.getName()); 
				
				break; 
			}
			default:
			{
				break;
			}
		}
	}
	

/*********************************************************************************			
 * 			 				ACTUAL VALIDATION METHODS							 *
 *********************************************************************************/

	/**
	 * Actually takes the value, 
	 * matches it with the appropriate validater,
	 * and issues appropriate warnings. 
	 */
	private void validateTypedValue(TypedListValue val, String name) 
	{		
		switch(val.getType())
		{
			case ID:
			{
				validateTypedValueID(val, name);
				break;
			}
			case Birthyear:
			{
				validateTypedValueBirthyear(val, name);
				break; 
			}
			case Date:
			{
				validateTypedValueDate(val, name);
				break;
			}
			case Days:
				validateTypedValueDay(val, name); 
				break;
			case Diagnosis:
				break;
			case Event:
				break;
			case Female:
				validateTypedValueSex(val, name); 
				break;
			case Male:
				validateTypedValueSex(val, name);
				break;
			case Months:
			{
				validateTypedListValueMonths(val, name); 
				break;
			}
			case Postalcode:
			{
				validateTypedValuePostalcode(val, name); 
				break;
			}
			case Sex:
				validateTypedValueSex(val, name); 
				break;
			case Hours:
			{
				validateTypedValueHours(val, name); 
				break; 
			}
				
			case Years:
				break;
			default:
				break;
		}
	}





	/**
	 * Converts days to the appropriate form for the Database. 
	 * @param val
	 * @param name
	 */
	private void validateTypedValueDay(TypedListValue val, String name) 
	{	
		// We want to convert it a format recognizable by the Database. 
		if(val.getValue().equals("Sun"))
			val.setValue("1");
		if(val.getValue().equals("Mon"))
			val.setValue("2");
		if(val.getValue().equals("Tues"))
			val.setValue("3");
		if(val.getValue().equals("Wed"))
			val.setValue("4");
		if(val.getValue().equals("Thurs"))
			val.setValue("5");
		if(val.getValue().equals("Fri"))
			val.setValue("6");
		if(val.getValue().equals("Sat")) 
			val.setValue("7"); 
	}

	/**
	 * Checks out ID's.
	 */
	private void validateTypedValueID(TypedListValue val, String name) 
	{
		// Patient ID's seems to go from 1 - ~35000, if it's much higher than that, its a warning. 
		int idValue = Integer.parseInt(val.getValue());
		
		// We can change this as new patients are added.
		int currentUpperLimit = 40000; 
		int currentLowerLimit = 1; 
		
		// Throw a warning if it's a unreasonable number. 
		if(idValue > currentUpperLimit || idValue < currentLowerLimit)
		{
			MyError.warning(Stage.getFileNameForGroup(Stage.getNonAugmentedName(name)), 
					ErrorMessages.strangeIDValue(val.getValue()), 
					val.getLineNumber());
		}
		
	}
	
	/**
	 * Checks out Birthyears
	 */
	private void validateTypedValueBirthyear(TypedListValue val, String name) 
	{
		// We want to confirm the birthyear isn't too old or too young. 
		int birthyearValue = Integer.parseInt(val.getValue()); 
		
		int currentLowerLimit = 1900; 
		int currentUpperLimit = Calendar.getInstance().get(Calendar.YEAR);
		
		if(birthyearValue > currentUpperLimit || birthyearValue < currentLowerLimit)
		{
			MyError.warning(Stage.getFileNameForGroup(Stage.getNonAugmentedName(name)), 
					ErrorMessages.outOfRangeBirthyear(val.getValue()), 
					val.getLineNumber());
		}
		
	}
	
	
	/**
	 * Checks out the Dates.
	 */
	private void validateTypedValueDate(TypedListValue val, String name) 
	{		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		try {
			formatter.parse(val.getValue());
		} catch (ParseException e) {
			MyError.warning(Stage.getFileNameForGroup(Stage.getNonAugmentedName(name)),
					ErrorMessages.invalidDate(val.getValue()), val.getLineNumber());
		} 
	}
	
	/**
	 * Checks out the Postalcodes 
	 */
	private void validateTypedValuePostalcode(TypedListValue val, String name) 
	{	
		// If the postal code is too specific then it won't match on any patient. 
		// We make the patient less specific and issue a warning.
		if(val.getValue().length() >= 6)
		{
			MyError.warning(Stage.getFileNameForGroup(Stage.getNonAugmentedName(name)),
					ErrorMessages.postalCodeSpecificity(val.getValue()), val.getLineNumber());
			val.setValue(val.getValue().substring(0, 3)); 
		}
	}
	
	
	/**
	 * Checks out Times. 
	 * @param val
	 * @param name
	 */
	private void validateTypedValueHours(TypedListValue val, String name) 
	{
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		formatter.setLenient(false);
		try
		{
			formatter.parse(val.getValue());
		}
		catch(ParseException e)
		{
			MyError.warning(Stage.getFileNameForGroup(Stage.getNonAugmentedName(name)),
					ErrorMessages.invalidTime(val.getValue()),
					val.getLineNumber());
		}
		
	}

	/**
	 * Checks out the Months. 
	 * @param val
	 * @param name
	 */
	private void validateTypedListValueMonths(TypedListValue val, String name) 
	{		
		DateFormat formatter = new SimpleDateFormat("MM"); 
		formatter.setLenient(false);
		
		try
		{
			formatter.parse(val.getValue()); 
		}
		catch(ParseException e)
		{
			MyError.warning(Stage.getFileNameForGroup(Stage.getNonAugmentedName(name)),
					ErrorMessages.invalidMonth(val.getValue()),
					val.getLineNumber()); 
		}
	}
	
	/**
	 * Checks our Sex. Makes sure they are in Database appropriate format.
	 */
	private void validateTypedValueSex(TypedListValue val, String name) 
	{		
		if(val.getValue().equals("M"))
			val.setValue("Male");
		
		if(val.getValue().equals("F"))
			val.setValue("Female");
	
	}
	
}
