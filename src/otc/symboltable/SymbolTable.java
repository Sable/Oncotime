package otc.symboltable;


/**
 * The representation of a Symbol Table for our program. 
 * @author vikramsundaram
 *
 */
public class SymbolTable 
{
	// The current depth of our Symbol Table
	private static int currentLevel = 0; 
	
	/**
	 * Increments the current level. 
	 */
	public static void incrementCurrentLevel()
	{
		currentLevel++; 
	}
	
	/**
	 * Decrements the current level. 
	 */
	public static void decrementCurrentLevel()
	{
		currentLevel--; 
	}
}
