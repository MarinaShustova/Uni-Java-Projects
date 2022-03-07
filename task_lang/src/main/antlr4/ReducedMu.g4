grammar ReducedMu;

parse
 : block EOF
 ;

block
 : OBRACE stat* CBRACE
 ;

//while_block
// : OBRACE (stat | break2)* CBRACE
// ;

stat
 : assignment
 | if_stat
 | while_stat
 | log
 | block
 | break2
 ;

assignment
 : ID ASSIGN expr SCOL
 ;

if_stat
 : 'if'  ('(')? expr (')')? trueStatement=block ('else' falseStatement=block)?
 ;

while_stat
 : WHILE expr block
 ;

log
 : LOG expr SCOL
 ;

break2
 : BREAK SCOL
 ;

expr
 : expr op=(MULT | DIV ) expr      #multiplicationExpr
 | expr op=(PLUS | MINUS) expr          #additiveExpr
 | expr op=(LTEQ | GTEQ | LT | GT) expr #relationalExpr
 | expr op=(EQ | NEQ) expr              #equalityExpr
 | atom                                 #atomExpr
 | variableReference                    #varRef
 ;

atom
 : INT            #numberAtom
 | (TRUE | FALSE) #booleanAtom
// | ID             #idAtom
 | STRING         #stringAtom
 | OPAR expr CPAR #parExpr
 ;

variableReference : ID ;

EQ : '==';
NEQ : '!=';
GT : '>';
LT : '<';
GTEQ : '>=';
LTEQ : '<=';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';

SCOL : ';';
ASSIGN : '=';
OPAR : '(';
CPAR : ')';
OBRACE : '{';
CBRACE : '}';

TRUE : 'true';
FALSE : 'false';
IF : 'if';
ELSE : 'else';
WHILE : 'while';
LOG : 'log';
BREAK : 'break';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

INT
 : [0-9]+
 ;

STRING
 : '"' (~["\r\n] | '""')* '"'
 ;

COMMENT
 : '#' ~[\r\n]* -> skip
 ;

SPACE
 : [ \t\r\n] -> skip
 ;
