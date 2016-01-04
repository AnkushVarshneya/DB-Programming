Only use .pc files the files without extentions are a sample run through.

Part1.pc: creates DB

Part2.pc: populates DB based on user input

Part3.pc: prints out supplier with its part in a table format

part4.pc: lets user query the DB

How to compile:

1. proc Part<n>.pc
2. cc -c Part<n>.c -I/usr/include/oracle/11.2/client64
3. cc -o Part<n> Part1.o -L/usr/lib/oracle/11.2/client64/lib -lclntsh
4. ./Part<n>

Where <n> is the part number.