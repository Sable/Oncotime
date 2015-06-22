package otc.symboltable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import otc.drivers.OncoUtilities;
import otc.node.AFilterList;
import otc.node.Node;
import otc.node.PFilterList;
import otc.node.PType;
import otc.symboltable.Symbol.Type;


/** 
 * A Singleton class that holds all our state information. 
 * This class is used in the OncoCleaning and subsequent phases to control all variable information. 
 * @author vikramsundaram
 *
 */
public class Stage 
{
	// The actual symbol table. Contains references to the Symbols we need throughout the program. 
	private static HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	
	// How many times is variable named 'x' redeclared, used only for groups, essentially. 
	private static HashMap<String, Integer> nameCount = new HashMap<String, Integer>();
	
	// When a group is declared, we want to know from what file it came from. 
	private static HashMap<String, String> groupFileNames = new HashMap<String, String>(); 
	
	// This is the symbol table that grows as we increment our level. 
	private static LinkedList<HashMap<String, Symbol>> computationSymbolTable;
	private static int currentLevel = 0; 
	
	// We keep track of the foreaches to know which variable we are referencing. 
	private static int numForeach = 0; 

	/**
	 * --------------------------------------------------
	 * Utility Methods for Global Symbol Table.  
	 * --------------------------------------------------
	 */
	public static void symbolsPut(String key, Symbol s)
	{
		symbols.put(key, s); 
	}
	
	public static void symbolPutForeachSymbol(String key, Symbol s)
	{
		symbols.put(key + "_"+numForeach, s); 
	}
	
	public static Symbol symbolsGet(String key)
	{		
		return symbols.get(key); 
	}

	public static Symbol symbolsGetForeachSymbol(String key, int foreachLevel)
	{		
		Symbol s = null; 
		
		while(s == null && foreachLevel >= 0)
		{
			s = symbols.get(key + "_" + foreachLevel); 
			foreachLevel--;
		}
		
		return s;  
	}
	
	public static void incrementForeach()
	{
		numForeach++; 
	}
	
	/**
	 * --------------------------------------------------
	 * Utility Methods for groupFileNames 
	 * --------------------------------------------------
	 */
	public static String getFileNameForGroup(String groupName)
	{	
		String name = groupFileNames.get(groupName); 
		
		// If it's null it's in the current file. 
		if(name == null)
			return OncoUtilities.getFileName(); 
		else
			return name; 
	}
	
	public static void setFileNameForGroup(String groupName, String fileName)
	{
		groupFileNames.put(groupName, fileName); 
	}
	
	
	
	
	
	/**
	 * --------------------------------------------------
	 * Utility Methods for computationSymbolTable 
	 * --------------------------------------------------
	 */
	
	/**
	 * Get's the currentLevel of the program. 
	 * @return
	 */
	public static int getCurrentLevel()
	{
		return currentLevel; 
	}
	
	public static void initializeComputationTables()
	{
		computationSymbolTable = new LinkedList<HashMap<String, Symbol>>(); 
		computationSymbolTable.add(currentLevel, new HashMap<String, Symbol>()); 
	}
	
	/**
	 * Checks to see if the Computation Symbol Table contains the key. 
	 * @param key
	 * @return
	 */
	public static boolean computationsContain(String key)
	{
		
		// We want to check every level of the symbol table to see if the variable name has been used. 
		for(int i = 0; i <= currentLevel; i++)
		{
			HashMap<String, Symbol> currentSymbolTable = computationSymbolTable.get(i); 
			if(currentSymbolTable.containsKey(key)) 
			{  
				return true;
			}
		}
		return false; 
	}
	
	/**
	 * Gets the Abstract Symbol for the key. 
	 * @param key
	 * @return
	 */
	public static Symbol computationsGet(String key)
	{
		for(int i = 0; i <= currentLevel; i++)
		{
			HashMap<String, Symbol> currentSymbolTable = computationSymbolTable.get(i); 
			
			if(currentSymbolTable.containsKey(key)) return currentSymbolTable.get(key); 
		}
		// If it's not already there, then simply continue. 
		return null;
		
	}
	
	/**
	 * Adds the key to the current level of the computation symbol table. 
	 * @param key
	 * @param sym
	 */
	public static void computationsPut(String key, Symbol sym)
	{
		HashMap<String, Symbol> currentSymbolTable = computationSymbolTable.get(currentLevel);
		currentSymbolTable.put(key, sym); 
	}
	
	/**
	 * increments the current level, meaning we add a new symbol table. 
	 * @param pFileName
	 */
	public static void incrementCurrentLevel()
	{
		currentLevel++; 
		computationSymbolTable.add(currentLevel, new HashMap<String, Symbol>());
	}
	
	/**
	 * Decrements the current level, meaning we need to remove the last symbol table. s
	 */
	public static void decrementCurrentLevel()
	{
		computationSymbolTable.remove(currentLevel); 
		currentLevel--; 
	}
	
	
	/**
	 * Has the name 'x' already been used?
	 * @param key: The name of the variable
	 * @return 
	 */
	public static boolean nameCountContainsKey(String key)
	{
		return nameCount.containsKey(key); 
	}
	
	/**
	 * Insert the name and the number of times it has been used. 
	 * @param key
	 * @param i
	 */
	private static void nameCountPut(String key, int i)
	{
		nameCount.put(key, i); 
	}
	
	/**
	 * Return the number of times name 'x' has been used. 
	 * @param key
	 * @return
	 */
	public static int nameCountGet(String key)
	{
		return nameCount.get(key); 
	}
	
	/**
	 * Adds the name or increments it's counter if it's already there. 
	 */
	public static void addOrIncrement(String name)
	{
		// First we see if the name exists already.
		// Adding it if not. 
		if(!nameCountContainsKey(name))
		{
			nameCountPut(name, 0); 
		}
		else
		{
			// We increment the counter if it's already there. 
			nameCountPut(name, nameCountGet(name) + 1); 
		}
	}
	
	
	/** 
	 * Returns all the keys of the Group and Filter Symbol table. 
	 */
	public static Set<String> getAllNonComputationKeys()
	{
		return symbols.keySet(); 
	}
	
	/**
	 * Method that simply transforms x to x_count
	 * @param oldName
	 * @return
	 */
	public static String getMostRecentName(String oldName)
	{
		
		return oldName + "_" + nameCount.get(oldName); 
	}
	
	/**
	 * Method that simply transforms x to x_count-1
	 * @param oldName
	 * @return
	 */
	public static String getNextMostRecentName(String oldName)
	{
		
		if(nameCount.get(oldName) == null)
		{
			return oldName + "_" + 0; 
		}
		return oldName + "_" + (nameCount.get(oldName) - 1); 
	}
	
	
	
	/**
	 * Gets the original name without the augmentation. 
	 */
	public static String getNonAugmentedName(String augmentedName)
	{
		if(augmentedName == null) return null; 
		return augmentedName.substring(0, augmentedName.lastIndexOf('_')); 
	}
	
	
	/**
	 * Returns the symbol for the variable with name, 'name' 
	 * @param name
	 * @return
	 */
	public static Symbol getSymbolOriginalName(String name)
	{
		// We need to determine what the most recent name would be. 
		name = getMostRecentName(name);
		
		return symbols.get(name); 
	}
	
	/**
	 * Sometimes we want the last name augmented name. 
	 * @param text
	 */
	public static Symbol getLastSymbolForName(String name) 
	{
		// We need to determine what the next most recent name would be. 
		name = getNextMostRecentName(name);
		
		return symbols.get(name); 
		
	}	
	
	/**
	 * Manages putting symbols into the Symbol Table. 
	 * @param name
	 * @param symbol
	 */
	public static void addSymbol(String name, Symbol symbol)
	{
		// First check to see if the symbol is in the table. 
		if(symbols.get(name) != null)
		{
			// TODO: Actually emit error. 
			System.out.println("ERROR: Value is already there."); 
		}
		else
		{
			symbols.put(name, symbol); 
		}
	}
	
	/**
	 * Gets the symbol from the symbol table.
	 */
	public static Symbol getSymbolForAugmentedName(String name)
	{
		return symbols.get(name); 
	}
	
	/**
	 * Initializes the Stage. 
	 * Sets all the filters to everything. 
	 */
	public static void initStage()
	{
		// First we want to construct our Filter Symbols and initialize them to all. 
		PopulationSymbol pops = new PopulationSymbol(); 
		addSymbol(PopulationSymbol.getPopulationSymbolName(), pops); 
		
		PeriodSymbol pers = new PeriodSymbol(); 
		addSymbol(PeriodSymbol.getPeriodSymbolName(), pers); 
		
		EventsSymbol events = new EventsSymbol(); 
		addSymbol(EventsSymbol.getEventSymbolName(), events); 
		
		DoctorSymbol docs = new DoctorSymbol();
		addSymbol(DoctorSymbol.getDoctorSymbolName(), docs);
	}

	/**
	 * This takes the filter list, and increases our population filters. 
	 * @param filterList
	 * @throws IncorrectFilterValue 
	 */
	public static void addPopulationFilters(LinkedList<PFilterList> filterList) 
	{
		PopulationSymbol pop =  (PopulationSymbol) getSymbolForAugmentedName(PopulationSymbol.getPopulationSymbolName()); 
		
		for(PFilterList filter : filterList) 	
		{
			// We want the abstract alternative
			AFilterList list = (AFilterList) filter;
			
			// First we want to grab the appropriate type. 
			Type t = Symbol.convertToType(list.getType()); 
			
			// This will allow us to grab the appropriate node. 
			pop.addFilterValues(t, list.getTypedList()); 
		}
	}
	
	/**
	 * This takes the filter list, and increases our period filters. 
	 * @param filterList
	 */
	public static void addPeriodFilters(LinkedList<PFilterList> filterList)
	{
		PeriodSymbol per = (PeriodSymbol) getSymbolForAugmentedName(PeriodSymbol.getPeriodSymbolName()); 
		
		for(PFilterList filter : filterList)
		{
			// We want the abstract alternative
			AFilterList list = (AFilterList) filter;
			
			// First we want to grab the appropriate type. 
			Type t = Symbol.convertToType(list.getType()); 
			
			// This will allow us to grab the appropriate node. 
			per.addFilterValues(t, list.getTypedList()); 
			
		}
	}
	
	/**
	 * This takes the filter list, and increases our Events filters. 
	 * @param filterList
	 */
	public static void addEventsFilters(LinkedList<PFilterList> filterList)
	{
		EventsSymbol events = (EventsSymbol) getSymbolForAugmentedName(EventsSymbol.getEventSymbolName()); 
		
		for(PFilterList filter : filterList)
		{
			// We want the abstract alternative
			AFilterList list = (AFilterList) filter;
			
			// First we want to grab the appropriate type. 
			Type t = Symbol.convertToType(list.getType()); 
			
			// This will allow us to grab the appropriate node. 
			events.addFilterValues(t, list.getTypedList()); 
			
		}
	}
	
	/**
	 * This takes the filter list, and increases our period filters. 
	 * @param filterList
	 */
	public static void addDoctorFilters(LinkedList<PFilterList> filterList)
	{
		DoctorSymbol doctors = (DoctorSymbol) getSymbolForAugmentedName(DoctorSymbol.getDoctorSymbolName());
		
		for(PFilterList filter: filterList)
		{
			// We want the abstract alternative. 
			AFilterList list = (AFilterList) filter;
			
			// First we want to grab the appropriate type. 
			Type t = Symbol.convertToType(list.getType()); 
			
			// This will allow us to grab the appropriate node. 
			doctors.addFilterValues(t, list.getTypedList()); 
			
		}
	}

	
}
