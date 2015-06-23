package otc.symboltable;

import java.util.HashMap;
import java.util.LinkedList;

import otc.drivers.ErrorMessages;
import otc.drivers.MyError;
import otc.drivers.OncoUtilities;
import otc.node.AAllListItemTypedList;
import otc.node.ABirthyearType;
import otc.node.ADateListItemTypedList;
import otc.node.ADateType;
import otc.node.ADaysListItemTypedList;
import otc.node.ADaysType;
import otc.node.ADiagnosisType;
import otc.node.AEventType;
import otc.node.AExpandableListItemTypedList;
import otc.node.AFemaleListItemTypedList;
import otc.node.AHourListItemTypedList;
import otc.node.AHourType;
import otc.node.AIdType;
import otc.node.AMaleListItemTypedList;
import otc.node.AMonthsListItemTypedList;
import otc.node.AMonthsType;
import otc.node.ANumberListItemTypedList;
import otc.node.APostalcodeListItemTypedList;
import otc.node.APostalcodeType;
import otc.node.ASexType;
import otc.node.AStringListItemTypedList;
import otc.node.ATypedName;
import otc.node.AYearListItemTypedList;
import otc.node.AYearsType;
import otc.node.PType;
import otc.node.PTypedList;

/**
 * Symbol objects that hold important variable information
 * @author vikramsundaram
 *
 */
public class Symbol 
{
	// These are the various types we can have in an OncoProgram. 
	// TODO: WE NEED TO MAKE IT START AND FINISH. 
	public static enum Type {ID, Sex, Male, Female, 
		Birthyear, Diagnosis, Postalcode, Years, 
		Months, Days, Date, Event, Hours, All,
		PopulationFilter, PeriodFilter, EventsFilter, DoctorFilter};
	
	// These are the various object types that our symbol can be. 
	public static enum ObjectType {Group, Actor, 
		TypedListValue, TypedName, Parameter, Patient, Doctor, 
		Diagnosis, Table, List, Sequence, Iterator, 
		Filter, SequenceItem}
	
	// We store the type of the object. 
	protected ObjectType objectType; 
	public ObjectType getObjectType(){return this.objectType;}
	public void setObjectType(ObjectType t){this.objectType = t;}
	
	// We also store it's oncoType. 
	protected Type type; 
	public Type getType(){return this.type;}
	public void setType(Type t){this.type = t;}
	
	// We also store it's name and linenumber. 
	private String name; 
	public String getName(){return this.name;} 
	public void setName(String pName){this.name = pName;}
	
	private int lineNumber; 
	public int getLineNumber(){return this.lineNumber;} 
	public void setLineNumber(int pLineNumber){this.lineNumber = pLineNumber;} 
	
	
	
	
	/*********************************************************************************			
     * 			 				 VARIOUS UTILITY METHODS 							 *
     *********************************************************************************/
	
	public static Type convertToType(PType node)
	{
		// TODO: This is pretty messy, but I'm not sure a better way to do this. 
		if(node instanceof AIdType)
		{
			return Type.ID; 
		}
		else if(node instanceof ASexType)
		{
			return Type.Sex; 
		}
		else if(node instanceof ABirthyearType)
		{
			return Type.Birthyear;
		}
		else if(node instanceof ADiagnosisType)
		{
			return Type.Diagnosis;
		}
		else if(node instanceof APostalcodeType)
		{
			return Type.Postalcode; 
		}
		else if(node instanceof AYearsType)
		{
			return Type.Years; 
		}
		else if(node instanceof AMonthsType)
		{
			return Type.Months; 
		}
		else if(node instanceof ADaysType)
		{
			return Type.Days; 
		}
		else if(node instanceof ADateType)
		{
			return Type.Date; 
		}
		else if(node instanceof AEventType)
		{
			return Type.Event; 
		}
		else if(node instanceof AHourType)
		{
			return Type.Hours; 
		}
		else
		{ 
			return null; 
		}
	}
	
	/**
	 * Gets the type from a Typed Name.
	 * @param node
	 * @return
	 */
	public static Type convertToType(ATypedName node)
	{
		// TODO: This is pretty messy, but I'm not sure a better way to do this. 
		if(node.getType() instanceof AIdType)
		{
			return Type.ID; 
		}
		else if(node.getType() instanceof ASexType)
		{
			return Type.Sex; 
		}
		else if(node.getType() instanceof ABirthyearType)
		{
			return Type.Birthyear;
		}
		else if(node.getType() instanceof ADiagnosisType)
		{
			return Type.Diagnosis;
		}
		else if(node.getType() instanceof APostalcodeType)
		{
			return Type.Postalcode; 
		}
		else if(node.getType() instanceof AYearsType)
		{
			return Type.Years; 
		}
		else if(node.getType() instanceof AMonthsType)
		{
			return Type.Months; 
		}
		else if(node.getType() instanceof ADaysType)
		{
			return Type.Days; 
		}
		else if(node.getType() instanceof ADateType)
		{
			return Type.Date; 
		}
		else if(node.getType() instanceof AEventType)
		{
			return Type.Event; 
		}
		else if(node.getType() instanceof AHourType)
		{
			return Type.Hours; 
		}
		else
		{
			// TODO: Throw error. 
			return null; 
		}
	}
	
	/**
	 * This takes the list of values, determines their type, their values, and stores them in a list. 
	 * @param typedList
	 * @return the list of values in our format. 
	 * @throws ObjectNotDeclared 
	 */
	protected static LinkedList<TypedListValue> convertToTypeList(PTypedList typedList) 
	{		
		LinkedList<TypedListValue> groupValues = new LinkedList<TypedListValue>(); 
		
		while(typedList != null)
		{		
			// Again, the type conversion stuff is not clean. We need to determine a much cleaner way of handling this.
			if(typedList instanceof ANumberListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((ANumberListItemTypedList) typedList);
				TypedListValue v = new TypedListValue(Type.ID, ((ANumberListItemTypedList) typedList).getTNumber().getText());
				v.setLineNumber(((ANumberListItemTypedList) typedList).getTNumber().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((ANumberListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof AMaleListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((AMaleListItemTypedList) typedList);
				TypedListValue v = new TypedListValue(Type.Male, ((AMaleListItemTypedList) typedList).getTMale().getText());
				v.setLineNumber(((AMaleListItemTypedList) typedList).getTMale().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((AMaleListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof AFemaleListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((AFemaleListItemTypedList) typedList); 
				TypedListValue v = new TypedListValue(Type.Female, ((AFemaleListItemTypedList) typedList).getTFemale().getText()); 
				v.setLineNumber(((AFemaleListItemTypedList) typedList).getTFemale().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((AFemaleListItemTypedList) typedList).getTypedList();
			}
			else if(typedList instanceof APostalcodeListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((APostalcodeListItemTypedList) typedList); 
				TypedListValue v = new TypedListValue(Type.Postalcode, ((APostalcodeListItemTypedList) typedList).getTPostalcode().getText()); 
				v.setLineNumber(((APostalcodeListItemTypedList) typedList).getTPostalcode().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((APostalcodeListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof AYearListItemTypedList)
			{
				// Construct the TypedListValue 
				typedList = ((AYearListItemTypedList) typedList);
				TypedListValue v = new TypedListValue(Type.Years, ((AYearListItemTypedList) typedList).getTYear().getText());
				v.setLineNumber(((AYearListItemTypedList) typedList).getTYear().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((AYearListItemTypedList) typedList).getTypedList(); 
				
			}
			else if(typedList instanceof ADaysListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((ADaysListItemTypedList) typedList);
				TypedListValue v = new TypedListValue(Type.Days, ((ADaysListItemTypedList) typedList).getTDay().getText());
				v.setLineNumber(((ADaysListItemTypedList) typedList).getTDay().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((ADaysListItemTypedList) typedList).getTypedList(); 
				
			}
			else if(typedList instanceof AMonthsListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((AMonthsListItemTypedList) typedList); 
				TypedListValue v = new TypedListValue(Type.Months, ((AMonthsListItemTypedList) typedList).getTMonth().getText());
				v.setLineNumber(((AMonthsListItemTypedList) typedList).getTMonth().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((AMonthsListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof ADateListItemTypedList)
			{
				// Construct the TypedListValue 
				typedList = ((ADateListItemTypedList) typedList); 
				TypedListValue v = new TypedListValue(Type.Date, ((ADateListItemTypedList) typedList).getTDate().getText()); 
				v.setLineNumber(((ADateListItemTypedList) typedList).getTDate().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((ADateListItemTypedList) typedList).getTypedList();  
			}
			else if(typedList instanceof AStringListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((AStringListItemTypedList) typedList);
				TypedListValue v = new TypedListValue(Type.Diagnosis,((AStringListItemTypedList) typedList).getTIdentifier().getText());
				v.setLineNumber(((AStringListItemTypedList) typedList).getTIdentifier().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue 
				typedList = ((AStringListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof AHourListItemTypedList)
			{
				// Construct the TypedListValue 
				typedList = ((AHourListItemTypedList) typedList); 
				TypedListValue v = new TypedListValue(Type.Hours, ((AHourListItemTypedList) typedList).getTHour().getText()); 
				v.setLineNumber(((AHourListItemTypedList) typedList).getTHour().getLine());
				
				// Store it in our list. 
				groupValues.addLast(v);
				
				// Continue
				typedList = ((AHourListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof AExpandableListItemTypedList)
			{
				// Construct the TypedListValue
				typedList = ((AExpandableListItemTypedList) typedList); 
						
				// Normal expandable items
				Symbol s = Stage.getSymbolOriginalName(((AExpandableListItemTypedList) typedList).getTIdentifier().getText());
				
				// Sometimes we have a special case, where the group is calling a previous version of the group. 
				if(s == null)
					s = Stage.getLastSymbolForName(((AExpandableListItemTypedList) typedList).getTIdentifier().getText());
				
				// If the object is not found. 
				if(s == null)
				{  
					// Throw an error 
					MyError.error(OncoUtilities.getFileName(), 
								ErrorMessages.ObjectNotDeclared(((AExpandableListItemTypedList) typedList).getTIdentifier().getText()), 
								((AExpandableListItemTypedList) typedList).getTIdentifier().getLine());
					
				}
				else
				{
					if(s.getObjectType() != ObjectType.Group)
					{
						// TODO: If it is a parameter we need to handle it.  
					}
					else
					{ 
						groupValues.addAll(((GroupSymbol)s).getGroupValues()); 
					}
				}
				
				// Continue
				typedList = ((AExpandableListItemTypedList) typedList).getTypedList(); 
			}
			else if(typedList instanceof AAllListItemTypedList)
			{ 
				
				TypedListValue v = new TypedListValue(Type.All, ((AAllListItemTypedList) typedList).getTStar().getText());
				v.setLineNumber(((AAllListItemTypedList) typedList).getTStar().getLine());
				
				groupValues = new LinkedList<TypedListValue>();
				groupValues.addFirst(v);
				
				return groupValues; 
			}
			else
			{
				//TODO: Throw errror. 
			}
		}
		return groupValues;
	}
	
	
	/** 
	 * Checks to see if the actorSymbol is a valid actor. 
	 * @param actorSymbol
	 * @return
	 */
	public static boolean isActor(Symbol actorSymbol) 
	{
		ObjectType t = actorSymbol.getObjectType(); 
		
		return (t == ObjectType.Doctor || t == ObjectType.Diagnosis || t == ObjectType.Patient); 
		
	}
	
	
	/**
	 * Removes all duplicates from a typedList. 
	 * @param values
	 */
	public static LinkedList<TypedListValue> removeDuplicates(LinkedList<TypedListValue> values) 
	{ 	
		LinkedList<TypedListValue> cleanedValues = new LinkedList<TypedListValue>(); 
		HashMap<String, TypedListValue> dups = new HashMap<String, TypedListValue>(); 
		
		for(TypedListValue v : values)
		{ 
			if(dups.get(v.getValue()) == null)
				dups.put(v.getValue(), v); 
		} 
	
		for(String v : dups.keySet())
			cleanedValues.addLast(dups.get(v));
		
		return cleanedValues; 
	}
	
	
}
