package testing;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.*;

import src.IR;
import src.IR_Ballot;
import src.IR_Candidate;
import src.IR_Make_Test_File;


public class IR_ElectionTest {

    IR ir;
    @Before
    public void setUp(){    
        try{
            File[] file = new File[1];
            file[0] = new File("/home/lieu0009/CSCI/CSCI5801/repo-Team3/Project2/testing/IRtest.csv");
            ir = new IR(file);
        }
        catch(Exception e) { 
            System.out.println("Error File not found!");


        }
    }


@Test
public void makeTestFile(){
    IR_Make_Test_File testFile = new IR_Make_Test_File(1000, 6);
    testFile.makeTestFile();
    testFile.outputTestFile();
} 
    

@Test 
public void testInitialData(){ //test the initial data is used in the constructor to see if it populated the data correctly
    String expectednumCandidates = "4";
    String actualnumCandidates = ir.getNumCandidates() + "";
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
    String[] expectedBallots = {"[1, 3, 4, 2]", "[1, 0, 2, 0]", "[1, 2, 3, 0]", "[3, 2, 1, 4]", "[0, 0, 1, 2]", "[0, 0, 0, 1]"};
    IR_Ballot[] ballots = ir.getBallots();
    String[] actualBallots = new String[6];
    for (int a = 0; a < 6; a++){
        actualBallots[a] = Arrays.toString(ballots[a].getBallot());
    }


    for(int i = 0; i < expectedBallots.length; i++){
        assertEquals("Testing for correct votes stored: ", expectedBallots[i], actualBallots[i]);
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

@Test
public void testSetElectionStatus(){
    ir.setElectionStatus();
    IR_Candidate[] rankings = ir.getRankings();
    String leadCandidate = "Rosen (D)";
    assertEquals(leadCandidate, rankings[0].getName());
}

@Test
public void testMakeLoser(){
    ir.setElectionStatus();
    IR_Candidate[] rankings = ir.getRankings();
    ir.makeLoser(rankings[3]);
    String actualLoser = "No";
    String expectedLoser = "Yes";
    if (!rankings[3].getStatus()){
        actualLoser = "Yes";
    }
    assertEquals (expectedLoser, actualLoser);
}

@Test
public void testCoinToss(){ //should pass about half the time
    int candidate = ir.coinToss();
    boolean expectedCandidate = true;
    boolean actualCandidate = false;
    if (candidate >= 0 && candidate <= 1){
        actualCandidate = true;
    }
    assertEquals(expectedCandidate, actualCandidate);
}

@Test
public void testPoolSelect(){ //should pass about a third of the time
    int candidate = ir.poolSelect(3);
    boolean expectedCandidate = true;
    boolean actualCandidate = false;
    if (candidate >= 0 && candidate <= 2){
        actualCandidate = true;
    }
    assertEquals(expectedCandidate, actualCandidate);
}
@Test 
public void testMultipleFiles(){
    String path ="/home/lieu0009/5081/repo-Team3/Project2/testing/";
    String[] fileNames = {path + "\\IRfile1.csv", path + "\\IRfile2.csv", path + "\\IRfile3.csv"};
    File[] files = new File[fileNames.length];
            for(int i = 0; i < fileNames.length; i++){
                files[i] = new File(fileNames[i]);

            }
    IR multIR = new IR(files);

   
    String expected = fileNames[0] + " " + fileNames[1] + " " + fileNames[2];
    
    String actual =  multIR.getFiles();

    assertEquals("Testing bringing in multiple files", expected, actual);

}

@Test
public void testTable(){
    int [][] expected = {
        {0, 3, 3, 3}, 
        {0, 0, 0, 0}, 
        {0, 2, 2, 2}, 
        {0, 1, 1, 0}, 
        {0, 0, 0, 1}
    };
    ir.run();
    assertEquals(expected, ir.getTable());
}

}
