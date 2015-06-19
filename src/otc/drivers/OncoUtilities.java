package otc.drivers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import otc.node.ATypedName;
import otc.symboltable.Symbol.Type;

/** 
 * We want to be able to clean up the script before it is even parsed.  
 * @author vikramsundaram
 */
public class OncoUtilities 
{
	private static String fileExtension = "x";
	private static String extendedFilePath = "";
	private static String fileName = ""; 
	
	public static String getExtendedFilePath(){return extendedFilePath;}
	public static void setFileName(String pFileName) {fileName = pFileName;}
	
	public static String getFileName(){return fileName;}
	
	/**
	 * Cleans up the File path. 
	 * @param name: The file path passed in as input to the compiler. 
	 * @return The cleaned up fil epath that can be used to access other files.
	 */
	public static String getFilePathOf(String name) 
	{
		int index = name.lastIndexOf("/");
		
		if(index <= 0)
			return name;
		else
			return name.substring(0, index+1); 
	}

	/**
	 * Get's the name of the script entered without the file path / script extension.
	 * @param name: The filepath / name of the script: e.g: ../Tests/Valid/oncoScript.onc
	 * @return String: oncoScript
	 * TODO: Recognize different types of file names and extensions. 
	 */
	public static String getNameOfFileWithoutExtension(String name) 
	{
		return name.substring(name.lastIndexOf("/")+1, name.length()-4); 
	}
}
