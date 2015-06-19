package otc.drivers;

import otc.symboltable.Symbol.ObjectType;
import otc.symboltable.Symbol.Type;
import otc.symboltable.TypedListValue;

/**
 * All our public error messages
 * @author vikramsundaram
 *
 */
public class ErrorMessages 
{
	public static String scriptNameMismatch = "The name of the script does not match the name of the program.";
	public static String oncoScriptDoesNotExist   = "The script does not exist.";
	public static String typeMisMatch(TypedListValue val, Type t) {
		return "Trying to use value of type: " + val.getType() + " where we were expecting " + t; 
	}
	public static String invalidEvent(String value) {
		return "The event: " + value + " is not a valid event."; 
	}
	public static String invalidFileName(String name, String nameOfFile) {
		return "The scripts name is: "
				+ name + " which does not match the name of the file: " + nameOfFile; 
	}
	public static String fileDoesNotExist(String absoluteFileLocation) {
		return "The file located at: "
				+ absoluteFileLocation + " does not exist."; 
	}
	public static String ObjectNotDeclared(String text) {
		return "The object with name " + text + " was never declared."; 
	}
	public static String invalidFilter(Type type, String filterType) {
		
		return "The filter " + filterType + " does not take type " + type;
	}
	public static String actorReused(String text) {
		
		return "There is already an actor with type " + text + " used in the current scope.";  
	}
	public static String invalidRedecleration(String name) 
	{
		return "There is already a variable declared with the name: " + name; 
	}
	public static String variableNotDefined(String text) {
		return "The variable: " + text + " has not been defined. Check it's spelling please."; 
	}
	public static String tableNotDeclaredTopScope(String tableName) 
	{
		return "You are trying to declare a table with the name: " + tableName + " in something other than the outermost scope."
				+ " Make sure you are not declering a table in a for loop."; 
	}
	public static String invalidActorType(ObjectType actorType, Type type) 
	{
		return "Actors with the type: " + actorType + " do not have the property " + type;
	}
	public static String invalidObjectType(String variable, ObjectType type1,
			ObjectType type2) 
	{
		return "You are trying to use the variable: " + variable + " as a type: " + type1 + " when it has been previously declared as: " +type2; 
	}
	public static String invalidTimeLinePrint(String name,
			ObjectType object, ObjectType actualType) {
		
		return "We can only print the Timeline of Patients, and " + name + " actually has type " + object; 
	}
	public static String invalidLengthOfPrint(String name,
			ObjectType objectType, ObjectType table) {
		
		
		return "We can only print the Length of Tables, and " + name + " actually has type " + objectType; 
	}
	public static String invalidTableBarchart(String name,
			ObjectType objectType, ObjectType table) {
		
		return "We can only present Tables in barchart format, and " + name + " actually has type " + objectType;
	}
	public static String invalidEventName(String eventName) {
		
		return "The event: " + eventName + " is not a valid event name.";
	}
	public static String strangeIDValue(String value) {
		
		return "WARNING: The ID, " + value + ", is out of common range."; 
	}
	public static String outOfRangeBirthyear(String value) {
		return "WARNING: The Birthyear, " + value + ", looks like it might be incorrect.";
	}
	public static String invalidDate(String value) {
		return "WARNING: The date you entered, " + value + ", looks like it might be incorrect." +
					" Dates should be YYYY-MM-DD"; 
	}
	public static String postalCodeSpecificity(String value) {
		
		return "WARNING: Given the structure of the database, the postal code, " +value+", is too specific. We will automatically use the first three digits to improve search results"; 
	}
	public static String invalidTime(String value) {
		
		return "WARNING: The time you entered, " + value + ", looks like it might be incorrect." +	
					" Time should be HH:MM"; 
	}
	public static String invalidMonth(String value) 
	{
		return "WARNING: The month you entered, " + value + ", looks like it might be incorrect." + 
					" Months should be MM"; 
	} 

}
