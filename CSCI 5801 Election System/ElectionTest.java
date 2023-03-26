import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.*;
import org.junit.runners.MethodSorters;
import java.io.PrintWriter;
import java.util.Scanner;


public class ElectionTest {

    CPL cpl;
    @Before
    public void setUp(){

        //C:\\Users\\chiem\\OneDrive - Marshall Public Schools\\Desktop\\repo-Team3\\CSCI 5801 Election System\\test.txt
    try{
        File file = new File("C:\\Users\\Sidney\\OneDrive\\Desktop\\UMN Classes\\New folder\\repo-Team3\\CSCI 5801 Election System>");         cpl = new CPL(file);
    }
    catch(IOException e){ 
        System.out.println("Error File not found!");
    }
    }


  
@Test 
public void testPopulateParties(){ //test the populate parties that is used in the constructor to see if it populated the data correctly
    String expectedPartyNumber = "7";
    String actualPartyNumber = cpl.getNumParties() + "";
    String expectedParties = "Democratic Republican New Wave Reform Green Independent Test ";
    String actualParties = "";
    Party[] parties = cpl.getParties();
    for(Party party: parties){
        actualParties = actualParties + party.getName() + " ";

    }
    assertEquals("Testing to see if reads in the right number of Parties", expectedPartyNumber, actualPartyNumber);
    assertEquals("Testing to see if parties read in match", expectedParties, actualParties);
   

}

@Test 

public void testPopulateCandidate(){ //test the populate candidates that is used in the constructor to see if it populated the data correctly

    String[] expectedCandiates = {"Foster Volz Pike ", "Green Xu Wang ", "Jacks Rosen ", "McClure Berg ", "Zheng Melvin ", "Peters ", "Tester "};
    Party[] parties = cpl.getParties();
    int i = 0;
    for(Party party: parties){
        CPL_Candidate[] candidates = party.getCandidates();
        String actualCandiates = "";
        for(CPL_Candidate candiate: candidates){
            actualCandiates = actualCandiates + candiate.getName() + " ";
        }
        assertEquals("Testing to see if reads in the right candidates for each party", expectedCandiates[i], actualCandiates);
        i++;

    }
   

}

@Test 

//potential bug, only passes test when ran individual not with the rest of the tests

public void testPopulateBallots(){ //test the populate ballots that is used in the constructor to see if it populated the data correctly
    
   String[] expectedBallots = {"Ballot 0: 1,,,,,,", "Ballot 1: ,,,,,,1", "Ballot 2: ,1,,,,,", "Ballot 3: ,,,,1,,", "Ballot 4: ,,,,,1,", "Ballot 5: ,,,1,,,", "Ballot 6: ,,1,,,,", "Ballot 7: 1,,,,,,", "Ballot 8: ,1,,,,,"};
   CPL_Ballot[] ballots = cpl.getBallots();
  
   for(int i = 0; i < expectedBallots.length; i++){

    assertEquals("Testing to see if read in ballots",expectedBallots[i], ballots[i].toString());

   }
   
    String expectedSeats = "6";
    String actualSeats = cpl.getTotalSeats() + "";
    assertEquals("Testing to see if read total number of seats",expectedSeats, actualSeats);




}

@Test 

public void testgetNumParties(){ //test to see if num parties get works

   String expectedNumber = 7 + "";
   String actualNumber = cpl.getNumParties() + "";

   assertEquals("Testing to see if getter works",expectedNumber, actualNumber);



}



@Test 

public void testgetParty(){ //test to see if num parties get works
    String expectedParties = "Democratic Republican New Wave Reform Green Independent Test ";
    String actualParties = "";

    Party[] parties = cpl.getParties();
    for(Party party: parties){
        actualParties = actualParties + party.getName() + " ";

    }

    assertEquals("Testing getter", expectedParties, actualParties);


}


@Test 

public void testgetNumCandidates(){ //test to see if num parties get works
    String expectedNum = "14";
    String actualNum = cpl.getNumCandidates() + "";

    
    assertEquals("Testing getter", expectedNum, actualNum);


}

@Test 

public void testgetTotalSeats(){ //test to see if num parties get works
   
    String expectedSeats = "6";
    String actualSeats = cpl.getTotalSeats() + "";

    assertEquals("Testing getter",expectedSeats, actualSeats);


}

@Test 

public void testgetBallots(){ //test to see if num parties get works
    String[] expectedBallots = {"Ballot 0: 1,,,,,,", "Ballot 1: ,,,,,,1", "Ballot 2: ,1,,,,,", "Ballot 3: ,,,,1,,", "Ballot 4: ,,,,,1,", "Ballot 5: ,,,1,,,", "Ballot 6: ,,1,,,,", "Ballot 7: 1,,,,,,", "Ballot 8: ,1,,,,,"};
    CPL_Ballot[] ballots = cpl.getBallots();
   
    for(int i = 0; i < expectedBallots.length; i++){
 
     assertEquals("Testing to see if read in ballots",expectedBallots[i], ballots[i].toString());
 
    }
}


@Test 

public void testAssignBallots(){ //test to see if ballots are assigned correctly to parties
    String[] expectedBallots = {"Ballot 0: 1,,,,,,", "Ballot 1: ,,,,,,1", "Ballot 2: ,1,,,,,", "Ballot 3: ,,,,1,,", "Ballot 4: ,,,,,1,", "Ballot 5: ,,,1,,,", "Ballot 6: ,,1,,,,", "Ballot 7: 1,,,,,,", "Ballot 8: ,1,,,,,"};
    CPL_Ballot[] ballots = cpl.getBallots();
   
    for(int i = 0; i < expectedBallots.length; i++){
 
     assertEquals("Testing to see if read in ballots",expectedBallots[i], ballots[i].toString());
 
    }

}

}






<<<<<<< HEAD
=======




>>>>>>> 3b021de875944780e41aef0ec2f9c26bc8f49aba
