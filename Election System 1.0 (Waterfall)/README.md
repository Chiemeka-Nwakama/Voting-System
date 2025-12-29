# CSCI 5801: The Election System Project (Waterfall)

## Credits

Chiemeka Nwakama

Ted Casey

Phuong Lieu

Sidney Nguyen


## Project Overview
This application models an election system where a file containing information about the election such as number of seats, participating parties, and balots are provided to the system. The system distributes the seats according to the rules of the type of election. An audit file is produced that displays the progression of the election as well as a summary of the election printed to the screen. 

## Set up
### Compiling and Running Election Program

NOTE: There were issues with compiling the program directly in the src folder.

Please compile and run from the Project1 folder as below:

To compile in the project1 folder: javac src/Election.java
To run in the project1 folder: java src/Election.java

If the program is able to be compiled within the src folder:

Ensure the file containing the election information and ballots is in your current directory
To compile, type in your terminal “javac Election.java”
If you wish to provide the election filename by command line, you must do so when you run the program or you will be prompted for the file name
To run the program, type in your terminal “java Election”, along with the filename if you choose to provide the filename this way.

Note: If you wish to use the Election program with a different file, you must recompile and rerun the program with the new file name. There are events within the program that are randomized, so if the user wishes to see different results regarding this randomization, they must recompile and rerun the program.

### Running the Tests

To run the tests, you must modify the path variable string to the path of the files or else the test will not work.

*System testing for both CPL and IR election were tested by simply running a election test file and looking through the audit file to see if the steps and results at each point of both elections were as we expected. The rest of the tests (Unit testing) were done through JUnit testing in ElectionTestCPL.java and IR_ElectionTest.java respectively.
