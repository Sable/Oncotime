package otc.drivers;

import otc.analysis.*;
import otc.lexer.*; 
import otc.parser.*; 
import otc.prettyprinter.PrettyPrinter;
import otc.node.*; 

import java.io.*;

public class Main 
{
	public static void main(String[] args)
	{
		Parser p = new Parser(new Lexer(new PushbackReader(new InputStreamReader(System.in), 1024)));
		
		try {
			Start tree = p.parse();
			System.out.println("Done Parsing"); 
			System.out.println(tree.toString()); 
			
			
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

