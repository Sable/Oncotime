package otc.codegeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import otc.drivers.MyError;
import otc.drivers.OncoUtilities;


/**
 * Credit to Group 4: Michael Noseworthy, Pascale Gourdeau and Joel Cheverie. 
 * This file reads the ontology.json file, and gets information required for different events. 
 * It also allows the user to create a Python Map of the events and their associated data.
 * 
 * This file also handles grabbing any other boilerplate python functions and code and returning them 
 * 	to be injected into the generated code. 
 * TODO: Think of a better name. 
 * @author vikramsundaram
 */
public class CodeInjector 
{
	// The location of the Ontology.json file. 
	private static String ontologyFileLocation = "Ontology" + File.separator + "ontology.json";
	
	// The location of the dataManipulation.py file. 
	private static String dataManipulationFileLocation = "Ontology" + File.separator + "dataManipulation.py"; 
	
	
	/**
	 * Reads and returns the contents of Ontology.json. 
	 */
	public static String getOntology()
	{
		try
		{
			 StringBuilder sb = new StringBuilder(); 
			 BufferedReader reader = new BufferedReader(new FileReader(ontologyFileLocation));
		     sb.append("\nontology=");
		     String line = null;
		     while ((line = reader.readLine()) != null) {
		         sb.append(line.trim());
		     }
		     
		     return sb.toString(); 
		}
		catch(Exception e)
		{
			
			MyError.fatalError("Code Generation", ontologyFileLocation + " is not a valid file.");
			return null; 
		}
	}
	
	/**
	 * Reads and returns the contents of DataManipulation.py
	 */
	public static String getDataManipulationCode()
	{
		try
		{
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(dataManipulationFileLocation)); 
			String line = null; 
			while((line = reader.readLine()) != null) {
				sb.append(line + "\n"); 
			}
			
			return sb.toString(); 
		}
		catch(Exception e)
		{
			MyError.fatalError("Code Generation", dataManipulationFileLocation + " is not a valid file.");
			return null; 
		}
	}
	
}
