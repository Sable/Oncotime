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

/** 
 * We want to be able to clean up the script before it is even parsed.  
 * @author vikramsundaram
 */
public class OncoUtilities 
{
	private static String fileExtension = "x";
	private static String extendedFilePath = "";
	
	public static String getExtendedFilePath(){return extendedFilePath;}
	
	/**
	 * We will take the "use" lists and expand them. 
	 * This way we can continue with a, "complete" OncoScript.
	 * @param filename
	 * @return True if successfully expanded. 
	 * TODO: Currently finds all the .grp files, even in comments. We need to fix this.
	 * TODO: This is also probably quite inefficient. Solution for now, need to fix.
	 * TODO: If the file does not exist, we just throw a typical java filenotfound error.   
	 */
	public static boolean expandGroups(String filename)
	{
		File f = new File(filename);
		String filePath = getFilePathOf(filename); 
		
		try 
		{
			// We now should have a String representation of the File we can do manipulations on. 
			String contents = readFile(filename);	
			
			// Will store all our group files. 
			List<String> groupMatches = new ArrayList<String>(); 
			
			// These are the patterns we hope to match. 
			String groupPattern = "[a-z][a-zA-Z_0-9]*\\b.grp\\b"; 
			String usePattern = "\\buse\\b .*"; 
			
			// Find all the group names.
			Matcher m = Pattern.compile(groupPattern)
				     .matcher(contents);
				 while (m.find()) {
				   groupMatches.add(m.group()); 
				 }
			
			
			// Remove all the use clauses.
			contents = contents.replaceAll(usePattern, "GROUP_LOCATION");
			
			// Get all the group contents. 
			List<String> groupMatchContents = new ArrayList<String>(); 
			
			for(String groupFile : groupMatches)
				groupMatchContents.add(readFile(filePath + groupFile));
			
			String allGroups = "";
			
			for(String groupContent : groupMatchContents)
				allGroups += groupContent; 
			
			// Inject all the group contents.
			contents = contents.replaceFirst("\\bGROUP_LOCATION\\b", allGroups);
			contents = contents.replaceAll("\\bGROUP_LOCATION\\b", ""); 
			
			// Store the file to be manipulated by remainder of script.
			
			// The new files extended filename. 
			extendedFilePath = filename + fileExtension;
			
			PrintWriter pr = new PrintWriter(extendedFilePath, "UTF-8");
			pr.write(contents);
			pr.close(); 

			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
		return true; 
	}
	
	
	/**
	 * Reads the contents of a file into a String. 
	 * @param fileName: The name of the File to be read. 
	 * @return A string that is the contents of the file. 
	 * @throws IOException The file does not exist. 
	 * 
	 * Taken from: http://stackoverflow.com/questions/16027229/reading-from-a-text-file-and-storing-in-a-string
	 */
	public static String readFile(String fileName) throws IOException 
	{
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	
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
