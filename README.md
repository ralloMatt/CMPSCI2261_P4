# CMPSCI_2261_P4
Jave file reader:
The purpose of this project is to get comfortable with file handling, maps and
byte to hex conversion in java.
In this project you will be using Maps and file-handling to gather information
about a file and then make changes as necessary.
Your program should start by prompting the user for a filename. Your program
should then open this file and read it byte by byte, converting it to uppercase
hexidecimal and displaying it on the screen. Make sure this display is exactly 40
characters wide, taking up as many lines as necessary. To convert a byte to a
hexidecimal string, use the following...
// suppose you had a byte variable b
// btw, that is zero, not the letter O
String.format(“%02X”,b);
While you are doing this, you should be storing in a Map (or multiple Maps) the
frequency of all bytes, byte pairs and byte triples. For example, given this hex
string representing the bytes in a file...
2F 2F 20 54 2F 20
we would see that for the bytes
2F : 3
20 : 2
54 : 1
for byte pairs...
2F2F : 1
2F20 : 2
2054 : 1
542F : 1
for byte triples...
2F2F20 : 1
2F2054 : 1
20542F : 1
542F20 : 1
Then prompt the user for what amount of bytes they wish to see the data on, for
example:
What length of bytes would you like to see(1-3):
Depending on the prompt, display the appropriate data from your map.
Now prompt the user (y/n) if they would like to modify the file by replacing some
byte sequence with another. If so, prompt them for the length of bytes they
would like to modify (1-3). Then take as input the hexidecimal number sequence
as a string and verify it is of the correct length (the target byte sequence). Then
prompt them for another hexidecimal number sequence of the appropriate
length that we will use to replace the original (the replacement byte sequence).
Then open a new file, which is the name of the original file given, with the
extension “.mwh”. Now create a modified original file by copying it byte by byte,
however changing any occurrence of the target byte sequence with the
replacement byte sequence. When this is done, close the file and exit
