package otc.drivers;

import otc.analysis.*;
import otc.codegeneration.CodeGenerator;
import otc.lexer.*; 
import otc.parser.*; 
import otc.prettyprinter.PrettyPrinter;
import otc.symboltable.Stage;
import otc.typecheck.EventValidator;
import otc.typecheck.TypeChecker;
import otc.node.*; 

import java.io.*;
import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.LinkedList;

//import com.sun.tools.javac.jvm.ClassFile;
//import com.sun.tools.javac.util.List;

public class Main 
{
	public static void main(String[] argv)
	{
		
		/*
		 * Important components. 
		 */
		Start tree = null; 
		Parser p; 
		Iterator filesIterator; 
		String file; 
		LinkedList<ProgramFile> theProgram = new LinkedList<ProgramFile>();
		
		/*
		 * Process the Command Line arguments
		 */
		filesIterator = processCmdLine(argv).iterator();
		if (MyError.debug) MyError.debugln("Building syntax tree:");
		
		
		/*
		 * For each file. 
		 */
		while (filesIterator.hasNext()) 
		{
			file = (String)filesIterator.next();
			
			if (MyError.debug)
				MyError.debug(" - Parsing "+file+"...");

			try 
			{ 
				/* lexical-analyze and parse the input */
				p = new Parser (new Lexer(new PushbackReader(new FileReader(file),5000)));
				tree = p.parse();
				
				if (MyError.debug) MyError.debugln(" done");
				/* lump classfile together into 'the program' */
				theProgram.add(new ProgramFile(file, tree));
				
				
			/* handle all exceptions */
			} catch(IOException e) {
				System.err.println(" - IOException: cannot parse input file: "+ e.getMessage());
				System.exit(1);
			} catch(LexerException e) {
				System.out.println(file+": lexical error: "+e.getMessage());
				System.exit(1);
			} catch(ParserException e) {
				System.out.println(file+": syntax error: "+e.getMessage());
				System.exit(1);
			}
		} // end loop 
		
		/* Initialize our Stage */
		Stage.initStage(); 
		
		/* Line Numbers */ 
		if (MyError.debug) MyError.debug("Adding line numbers to nodes...");
		Lines.setLines(theProgram);
		if (MyError.debug) MyError.debugln(" done");
		MyError.noErrors();
		
		/* Inclusion of 'used' .grp files */ 
		if(MyError.debug) MyError.debug("Including .grp files..."); 
		UseExpander.expandGroups(theProgram); 
		if(MyError.debug) MyError.debugln(" done");
		MyError.noErrors(); 
		
		/* Symbol Table creation, Expansion of <>, Filter cleanup*/
		if(MyError.debug) MyError.debug("Creating the Symbol Table and expanding and filters..."); 
		SymbolTableInitializer.initialize(theProgram); 
		if(MyError.debug) MyError.debugln(" done");
		MyError.noErrors(); 
		
		/* Type Checking */ 
		if(MyError.debug) MyError.debug("Typechecking the program...");
		EventValidator.init(); 
		TypeChecker.typeCheck(theProgram);
		if(MyError.debug) MyError.debugln(" done");
		MyError.noErrors();
		
		/* Pretty Print */ 
//		if(MyError.debug) MyError.debugln("Pretty printing ..."); 
//		PrettyPrinter.prettyPrint(theProgram);
//		if(MyError.debug) MyError.debugln(" done");
//		MyError.noErrors(); 
		
		/* Code Generation */ 
		if(MyError.debug) MyError.debug("Validating the data...");
		DataValidator.validate(theProgram); 
		CodeGenerator.generate(theProgram); 
		if(MyError.debug) MyError.debugln(" done"); 
		MyError.noErrors(); 
		
	}

	/**
	 * Processes options and extracts the actual arguments (filenames). 
	 * @param args - The arguments to the program. 
	 * @return 
	 */
	private static LinkedList<String> processCmdLine(String[] args) {
		String arg;
		LinkedList<String> files = new LinkedList<String>();

		for (int i = 0; i < args.length; i++) {
			arg = args[i].intern(); // optimization: can use '==' for .equals
			if ((arg == "-v") || (arg == "--verbose"))
				MyError.debug = true;
			else if ((arg == "-h") || (arg == "--help"))  {
				System.out.println("Usage: joos [OPTION]... [FILE]...");
				System.out.println("Options:");
				System.out.println("  -v, --verbose            show progress");
				System.out.println("  -P, --pretty-print       (debug) print ast in a parsable form and exit");
				System.out.println("  -S, --sym-pretty-print   (debug) print symbol table and exit");
				System.out.println("  -h, --help               display this help and exit");
				System.out.println();
				System.out.println("Input files should have extension '.java' for regular classes");
				System.out.println("and '.joos' for external class declarations.");
				System.exit(0);
			}
			else if ((arg == "-P") || (arg == "--pretty-print"))
				MyError.pretty_print = true;
			else if ((arg == "-S") || (arg == "--sym-pretty-print"))
				MyError.sym_pretty_print = true;
			else if (arg.startsWith("-")) {
				System.err.println("Unrecognized option: "+arg);
				System.exit(-1);
			}
			else
				files.add(arg);
		}

		return files;
	
	}
}



