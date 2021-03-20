# Treasure_Hunt
This is a small game that I made in a Java class in school.


## Compiling
I used JRE version 1.8 (8) with JDK 8<br />

To compile:<br />
javac *.java<br />

## Running
The main file is called Adventure and you need to specify a map .txt file to create the map.
Make sure to have MapPics folder, map1items.txt (this file should not be edited) and the map.txt in the same directory as the java files

To run:<br />
java Adventure map1.txt<br />
![GitHub Logo](GameEx1.PNG)
![GitHub Logo](ProgramScreenshot.PNG)

There isn't an objective in the game. It is to wonder around and search for supplies.

## Customizing a map file
A map file can be customized with an n x m matrix of characters.<br />
For the characters, use the ones already provided at the bottom. This is the example map1.txt:<br />
![GitHub Logo](MapEx.PNG)

When saving a game, the file type must have .dat extension<br />

commands:<br />
go east (moves left)<br />
go west (moves right)<br />
go north (moves up)<br />
go south (moves down)<br />
take <item name>// takes an item<br />
drop <item name>// drops an item
