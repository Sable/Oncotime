package otc.typecheck;

import java.util.LinkedList;

import otc.symboltable.Symbol.Type;

/**
 * This holds all the information for any event. 
 * We simply create a new instance of this event and add it to our Event Validator.  
 * @author vikramsundaram
 *
 */
public class Event 
{
	// Events can have some, or multiple parameters. 
	// These parameters will get implicitly typed.
	// When constructing a new event, we want to make sure that
	//	we add the types in the right order. 
	private LinkedList<Type> validParameterTypes = new LinkedList<Type>();
	
	// We also have the the name the user enters. 
	private String scriptName; 
	
	// And the name the database expects. 
	private String databaseName; 
	
	/**
	 * We can get the Script Name
	 */
	public String getScriptName()
	{
		return this.scriptName; 
	}
	
	/**
	 * We can get the database name 
	 */
	public String getDatabaseName()
	{
		return this.databaseName; 
	}
	
	/**
	 * Set the Script Name
	 */
	public void setScriptName(String pScriptName)
	{
		this.scriptName = pScriptName; 
	}
	
	/**
	 * Set the database name. 
	 */
	public void setDatabaseName(String pDatabaseName)
	{
		this.databaseName = pDatabaseName; 
	}
	
	/**
	 * We can add a parameter type. 
	 * It gets added last, i.e. we expect the parameters to be entered in the order they appear. 
	 */
	public void addParameterType(Type t)
	{
		this.validParameterTypes.addLast(t);
	}
	
	/**
	 * We can confirm that a parameter is a valid type. 
	 */
	public boolean isValidParameter(Type t, int index)
	{
		// If the index is invalid, then return false. 
		if(this.validParameterTypes.get(index) == null)
		{
			return false; 
		}
		
		// If the types don't matchup return false. 
		if(this.validParameterTypes.get(index) != t)
		{
			return false; 
		}
		
		// Otherwise return true. 
		return true; 
		
	}
}
