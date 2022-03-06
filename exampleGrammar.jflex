/*-***
 * This grammar is defined for the example grammar defined in the
 *project part 1 instructions
 */

/*
 * NOTE: make sure that the java cup runtime file is in the same directory as this file
 * it is also alright if the runtime location is added to to the classpath, but just
 * putting in the same file is far easier.
 */
import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%line
%column
%unicode
%class ExampleScanner
/*
 * NOTE: the above name ExampleLexer, will have to be changed here if
 * you chose to rename the lexer object.
 */
 
%{

/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */
Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}

/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */
Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}

%}


/*-*
 * PATTERN DEFINITIONS:
 */


slash			    = \\
letter        = [A-Za-z]
digit         = [0-9]
id   	      = {letter}({letter}|{digit})*
intlit	      = {digit}+
charlit       = \'([^'\\]|\\.)\'
inlinecomment = {slash}{slash}.*\n
blockcomments   = {slash}\*
blockcommente   = \*{slash}
commentbody		= ([^\*]|(\*+[^\\]))
blockcomment    = {blockcomments}{commentbody}*?{blockcommente}
whitespace    = [ \n\t\r]
stringlit     = \"([^\"\\]|\\.)*\"
floatlit      = {digit}+\.{digit}+


%%
/**
 * LEXICAL RULES:
 */
class              { return newSym(sym.CLASS, "class"); }
print		       { return newSym(sym.PRINT, "print"); }
printline          { return newSym(sym.PRINTLN, "println");}
"/"                { return newSym(sym.DIV, "/");}
"+"                { return newSym(sym.PLUS, "+"); }
"-"                { return newSym(sym.SUB, "-"); }
"<"                { return newSym( sym.LT, "<"); }
">"                { return newSym (sym.GT, ">"); }
"<="               { return newSym (sym.LTE, "<=");}
">="               { return newSym  (sym.GTE, ">=");}
"=="               { return newSym (sym.EQUAL, "==");}
"<>"               { return newSym (sym.NOTEQUAL, "<>");}
"||"               { return newSym (sym.OR, "||");}
"&&"               { return newSym (sym.AND, "&&"); }
"*"                { return newSym(sym.TIMES, "*"); }
"="                { return newSym(sym.ASSMNT, "="); }
";"                { return newSym(sym.SEMI, ";"); }
"["                { return newSym(sym.OPENSQUARE, "["); }
"]"                { return newSym(sym.CLOSESQUARE, "]"); }
":"                { return newSym(sym.COLON, ":"); }
"?"                { return newSym(sym.QUESTION, "?"); } 
var		           { return newSym(sym.VAR, "var"); }
int                { return newSym(sym.INT, "int");}
char               { return newSym(sym.CHAR, "char");}
bool               { return newSym(sym.BOOL, "bool");}
float              { return newSym(sym.FLOAT, "float");}
void               { return newSym(sym.VOID, "void");}
final              { return newSym(sym.FINAL, "final");}
if                 { return newSym (sym.IF, "if");}
"("                  { return newSym (sym.OPEN, "(");}
")"                  { return newSym (sym.CLOSE, ")");}
read               { return newSym (sym.READ, "read");}
return             { return newSym (sym.RETURN, "return");}
"++"                 { return newSym(sym.INCREMENT, "++");}
"--"                 { return newSym(sym.DECREMENT, "--");}
"{"                { return newSym(sym.OPENCURL, "{");}
"}"                 { return newSym(sym.CLOSECURL, "}");}
else               { return newSym(sym.ELSE, "else");}
","                  { return newSym(sym.COMMA, ",");}
"~"                  { return newSym(sym.TILDA , "~");}
while               { return newSym(sym.WHILE , "while");}
true                {return newSym(sym.TRUE, "true");}
false               {return newSym(sym.TRUE, "false");}
{floatlit}          { return newSym(sym.FLOATLIT, yytext());}
{stringlit}         {return newSym(sym.STRINGLIT, yytext());}
{charlit}          { return newSym(sym.CHARLIT, yytext());}
{id}               { return newSym(sym.ID, yytext());}
{intlit}           { return newSym(sym.INTLIT, yytext());}
{inlinecomment}    { /* For this stand-alone lexer, print out comments. */}
{blockcomment} {/* For this stand-alone lexer, print out comments. */}
{whitespace}       { /* Ignore whitespace. */ }
.                  { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yychar); } 