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
    IR ir_multiple;
    String path = "/home/lieu0009/5081/repo-Team3/Project2/testing/";
    
    @Before
    public void setUp(){    
        try{
            // File[] file = new File[1];
            // file[0] = new File("/home/lieu0009/CSCI/CSCI5801/repo-Team3/Project2/testing/IRtest.csv");
            // ir = new IR(file);
            File file_1 = new File(path + "/IRfile1.csv");
            File[] file_single = new File[1];
            file_single[0] = file_1;
            File[] file_multiple = new File[3];
            File file_2 = new File(path + "/IRfile2.csv");
            File file_3 = new File(path + "/IRfile3.csv");
            file_multiple[0] = file_1;
            file_multiple[1] = file_2;
            file_multiple[2] = file_3;

            ir = new IR(file_single);
            ir_multiple = new IR(file_multiple);
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
    String actualnumCandidates_m = ir_multiple.getNumCandidates() + "";
    String expectedCandidates = "Rosen (D) Kleinberg (R) Chou (I) Royce (L) ";
    String actualCandidtates = "";
    String actualCandidtates_m = "";
    IR_Candidate[] candidates = ir.getCandidates();
    IR_Candidate[] candidates_m = ir_multiple.getCandidates();
    for(IR_Candidate candidate: candidates){
        actualCandidtates = actualCandidtates + candidate.getName() + " ";
    }
    //multiple
    for(IR_Candidate candidate: candidates_m){
        actualCandidtates_m = actualCandidtates_m + candidate.getName() + " ";
    }

    String expectedBallots = "6";
    String actualBallots = ir.getNumBallots() + "";
    //multiple
    String expectedBallots_m = "18";
    String actualBallots_m = ir_multiple.getNumBallots() + "";

    assertEquals("Testing to see if reads in the right number of Candidates", expectednumCandidates, actualnumCandidates);
    assertEquals("Testing to see if reads in the right name of Candidates", expectedCandidates, actualCandidtates);
    assertEquals("Testing to see if reads in the right number of ballots", expectedBallots, actualBallots);
    //multiple
    assertEquals("Testing to see if reads in the right number of Candidates", expectednumCandidates, actualnumCandidates_m);
    assertEquals("Testing to see if reads in the right name of Candidates", expectedCandidates, actualCandidtates_m);
    assertEquals("Testing to see if reads in the right number of ballots", expectedBallots_m, actualBallots_m);

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
    //multiple
    String[] expectedBallots_m = {"[1, 3, 4, 2]", "[1, 0, 2, 0]", "[1, 2, 3, 0]", "[3, 2, 1, 4]", "[0, 0, 1, 2]", "[0, 0, 0, 1]",
                                "[1, 3, 4, 2]", "[1, 3, 2, 0]", "[1, 2, 3, 0]", "[3, 2, 1, 4]", "[0, 0, 1, 2]", "[0, 0, 0, 1]",
                                "[1, 3, 4, 2]", "[1, 0, 2, 0]", "[1, 2, 3, 0]", "[3, 2, 1, 4]", "[0, 0, 0, 1]", "[1, 0, 0, 0]"};
    IR_Ballot[] ballots_m = ir_multiple.getBallots();
    String[] actualBallots_m = new String[18];
    for (int a = 0; a < 18; a++){
        actualBallots_m[a] = Arrays.toString(ballots_m[a].getBallot());
    }

    for(int i = 0; i < expectedBallots_m.length; i++){
        assertEquals("Testing for correct votes stored: ", expectedBallots_m[i], actualBallots_m[i]);
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
    //multiple
    String actualCandidtates_m = "";
    IR_Candidate[] candidates_m = ir_multiple.getCandidates();
    for(IR_Candidate candidate: candidates_m){
        actualCandidtates_m = actualCandidtates_m + candidate.getName() + " ";
    }

    assertEquals("Testing getter: ", expectedCandidates, actualCandidtates_m);

}

@Test
public void testgetNumCandidates(){
    String expectednumCandidates = "4";
    String actualnumCandidates = ir.getNumCandidates() + "";
    
    assertEquals("Testing getter: ", expectednumCandidates, actualnumCandidates);
    //multiple
    String actualnumCandidates_m = ir_multiple.getNumCandidates() + "";
    
    assertEquals("Testing getter: ", expectednumCandidates, actualnumCandidates_m);
}

@Test
public void testgetNumBallots(){
    String expectedBallots = "6";
    String actualBallots = ir.getNumBallots() + "";

    assertEquals("Testing getter: ", expectedBallots, actualBallots);   
    //multiple
    String expectedBallots_m = "18";
    String actualBallots_m = ir_multiple.getNumBallots() + "";

    assertEquals("Testing getter: ", expectedBallots_m, actualBallots_m);
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
    //multiple
    ir_multiple.setElectionStatus();
    IR_Candidate[] rankings_m = ir_multiple.getRankings();
    assertEquals(leadCandidate, rankings_m[0].getName());
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
    //multiple
    ir_multiple.setElectionStatus();
    IR_Candidate[] rankings_m = ir_multiple.getRankings();
    ir_multiple.makeLoser(rankings[3]);
    String actualLoser_m = "No";
    String expectedLoser_m = "Yes";
    if (!rankings_m[3].getStatus()){
        actualLoser_m = "Yes";
    }
    assertEquals (expectedLoser_m, actualLoser_m);
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
    //multiple
    int candidate_m = ir_multiple.coinToss();
    if (candidate_m >= 0 && candidate_m <= 1){
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
    //multiple
    int candidate_m = ir_multiple.poolSelect(3);
    if (candidate_m >= 0 && candidate_m <= 2){
        actualCandidate = true;
    }
    assertEquals(expectedCandidate, actualCandidate);
}

@Test
public void testTable(){
    int [][] expected = {
        {0, 3, 3, 3}, 
        {0, 0, 0, 0}, 
        {0, 2, 2, 2}, 
        {0, 0, 0, 0}, 
        {0, 1, 1, 1}
    };
    ir.run();
    assertEquals(expected, ir.getTable());
}

@Test
public void testExhaustIncomplete(){
    int expected = 0;
    IR_Candidate[] candidates = ir.getCandidates();
    int actual = candidates[3].getVotes();
    assertEquals(expected, actual);
}

}
