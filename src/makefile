# assumes sablecc script is on your PATH

#.PHONY OncoTime

# to compile the code
OncoTime: clean grammar
	javac otc/lexer/*.java otc/parser/*.java otc/node/*.java otc/analysis/*.java otc/drivers/*.java otc/symboltable/*.java  otc/typecheck/*.java otc/codegeneration/*.java otc/prettyprinter/*.java

# to generate the compiler code
grammar: OTC.sablecc
	sablecc OTC.sablecc

check:	
	./check

jar: clean grammar
	javac otc/lexer/*.java otc/parser/*.java otc/node/*.java otc/analysis/*.java otc/drivers/*.java otc/symboltable/*.java  otc/typecheck/*.java otc/codegeneration/*.java otc/prettyprinter/*.java
	jar cmf manifest.txt otc.jar otc
	mv otc.jar ../
	
clean:	
	rm -rf otc/*.class otc/lexer/ otc/parser/ otc/node/ otc/analysis/ otc/driver/*.class otc/symboltable/*.class  otc/typecheck/*.class otc/codegeneration/*.class otc/prettyprinter/*.class
