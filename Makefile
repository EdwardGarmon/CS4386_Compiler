JAVA=java
JAVAC=javac
JFLEX=$(JAVA) -jar jflex-1.8.2/lib/jflex-full-1.8.2.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)
TESTDIR=p3tests

default: run

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
				$(JAVAC) -cp $(CP) $*.java

FILE= TypeException.java MySymbol.java ExampleScanner.java parser.java sym.java\
	Memberdecls.java Program.java Fielddecls.java Fielddecl.java\
	Methoddecls.java Methoddecl.java Optionalsemi.java Optionalfinal.java\
	Optionalexpr.java ReturnType.java Type.java Argdecls.java ArgdeclList.java\
	Argdecl.java Stmts.java Stmt.java IfEnd.java Name.java Args.java\
	Readlist.java Printlist.java Binaryop.java Expr.java Printlinelist.java\
	ExampleParserTest.java SymbolTable.java AccessTable.java\
	MethodSymbol.java
				

dump: parserD.java $(FILE:java=class)


run: tests

tests: all
		@for f in $(shell ls ${TESTDIR}); do \
			echo running test for $${f}; \
			$(JAVA) -cp $(CP) ExampleParserTest ${TESTDIR}/$${f} > ${TESTDIR}-output/$${f}-output.txt;\
		done


all: ExampleScanner.java parser.java $(FILE:java=class)

clean:
		rm -f *.class *~ *.bak ExampleScanner.java parser.java sym.java
		rm ./p3tests-output/*.txt
		

ExampleScanner.java: exampleGrammar.jflex
		$(JFLEX) exampleGrammar.jflex

parser.java: exampleTokens.cup
		$(CUP) -interface -progress < exampleTokens.cup

parserD.java: exampleTokens.cup
		$(CUP) -interface -dump < exampleTokens.cup
