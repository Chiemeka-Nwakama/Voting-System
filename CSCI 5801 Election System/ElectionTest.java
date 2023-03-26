import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.*;
import org.junit.runners.MethodSorters;
import java.io.PrintWriter;


public class ElectionTest {
    IR ir;
    @Before
    public void setUp(){    
        try{
            File file = new File("/home/casey526/CSCI/CSCI5801/repo-Team3/CSCI 5801 Election System/IRtest.txt");
            ir = new IR(file);
        }
        catch(Exception e) { 
            System.out.println("Error File not found!");


        }
    }


  
@Test 
public void testInitialData(){ //test the populate parties that is used in the constructor to see if it populated the data correctly
    String expectednumCandidates = "4";
    System.out.println("Test started");
    String actualnumCandidates = ir.getNumCandidates() + "";
    assertEquals("Testing to see if reads in the right number of Candidates", expectednumCandidates, actualnumCandidates);
    String expectedCandidates = "Rosen (D) Kleinberg (R) Chou (I) Royce (L) ";
    String actualCandidtates = "";
    IR_Candidate[] candidates = ir.getCandidates();
    for(IR_Candidate candidate: candidates){
        actualCandidtates = actualCandidtates + candidate.getName() + " ";
    }

    String expectedBallots = "6";
    String actualBallots = ir.getNumBallots() + "";

    assertEquals("Testing to see if reads in the right number of Candidates", expectednumCandidates, actualnumCandidates);
    assertEquals("Testing to see if reads in the right name of Candidates", expectedCandidates, actualCandidtates);
    assertEquals("Testing to see if reads in the right number of ballots", expectedBallots, actualBallots);
   

}

@Test
public void testpopulateData(){
    String[] expectedBallots = {"1,3,4,2", "1,0,2,0", "1,2,3,0", "3,2,1,4", "0,0,1,2", "0,0,0,1"};
    IR_Ballot[] ballots = ir.getBallots();

    for(int i = 0; i < expectedBallots.length; i++){
        assertEquals("Testing for correct votes stored: ", expectedBallots[i], ballots[i].getBallot() + "");
    }

}

@Test
public void testgetCandidates(){
    String expectedCandidates = "Rosen (D) Kleinberg (R) Chou (I) Royce (L) ";
    String actualCandidtates = "";
    IR_Candidate[] candidates = ir.getCandidates();
    for(IR_Candidate candidate: candidates){
        actualCandidtates = actualCandidtates + candidate.getName() + " ";
    }

    assertEquals("Testing getter: ", expectedCandidates, actualCandidtates);

}

@Test
public void testgetNumCandidates(){
    String expectednumCandidates = "4";
    String actualnumCandidates = ir.getNumCandidates() + "";
    
    assertEquals("Testing getter: ", expectednumCandidates, actualnumCandidates);
}

@Test
public void testgetNumBallots(){
    String expectedBallots = "6";
    String actualBallots = ir.getNumBallots() + "";

    assertEquals("Testing getter: ", expectedBallots, actualBallots);   
}

@Test
public void testUpdateCurrentVote(){
    IR_Ballot[] ballots = ir.getBallots();
    ballots[4].updateCurrentVote();
    String expectedVote = "3";
    String actualVote = ballots[4].getCurrentVote() + "";

    assertEquals("Testing setter: ", expectedVote, actualVote); 
}

// @Test
// public void testmakeLoser(){

// }



}