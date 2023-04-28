# CSCI 5801: The Election System Project Version 2.0

## General Team Information
Team #3: 

Ted Casey - casey526

Phuong Lieu - lieu0009

Sidney Nguyen - nguy4257

Chiemeka Nwakama - nwaka013

## Project Overview
This application models an election system where a file containing information about the election such as number of seats, participating parties, and ballots are provided to the system. The system distributes the seats according to the rules of the type of election. An audit file is produced that displays the progression of the election as well as a summary of the election printed to the screen. This application has been updated to include an election by Popularity Only. 

## Set up
### Compiling and Running Election Program

NOTE: There were issues with compiling the program directly in the src folder.

Please compile and run from the Project2 folder as below:

To compile in the project2 folder: javac src/Election.java
To run in the project2 folder: java src/Election.java

If the program is able to be compiled within the src folder:

Selecting Files:
A pop up window will direct you to select your files. You are able to select more than one file at a time by holding down the CTRL button on the keyboard and clicking on each file at the same time.

Note: If you wish to use the Election program with a different file, you must recompile and rerun the program with the new file name. There are events within the program that are randomized, so if the user wishes to see different results regarding this randomization, they must recompile and rerun the program.

### Running the Tests

To run the tests, you must modify the path variable string to the path of the files or else the test will not work.

*System testing for both CPL, IR, and PO elections were tested by simply running a election test file and looking through the audit file to see if the steps and results at each point of both elections were as we expected. The rest of the tests (Unit testing) were done through JUnit testing in ElectionTestCPL.java and IR_ElectionTest.java respectively.

