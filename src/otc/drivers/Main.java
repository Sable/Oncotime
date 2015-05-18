package otc.drivers;

import otc.analysis.*;
import otc.lexer.*; 
import otc.parser.*; 
import otc.prettyprinter.PrettyPrinter;
import otc.node.*; 

import java.io.*;
import java.util.Iterator;

public class Main 
{
	public static void main(String[] args)
	{
		
		try {
			System.setIn(new FileInputStream("TestFiles/Valid/test1.onc"));
		} catch (FileNotFoundException e1) {
			System.out.println(System.getProperty("user.dir")); 
			e1.printStackTrace();
		}
		
		Parser p = new Parser(new Lexer(new PushbackReader(new InputStreamReader(System.in), 1024)));
		
		try {
			Start tree = p.parse(); 
			
			System.out.println(tree);			
		} catch (ParserException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (LexerException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} 
	}
}

