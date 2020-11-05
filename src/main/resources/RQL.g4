grammar RQL;

log: query;

query: ip '- -' '['dateAndTime']' WORD* ;

ip: NUMBER*'.'NUMBER*'.'NUMBER*'.'NUMBER*;
dateAndTime:date':'time;
date: NUMBER'/'NUMBER'/'NUMBER;
time: hour':'minute':'second;


hour:NUMBER;
minute:NUMBER;
second:NUMBER;


NUMBER : [0-9]+;

WORD : [a-zA-Z_]+;

WHITESPACE          : (' ' | '\t' | '.')+ -> skip ;