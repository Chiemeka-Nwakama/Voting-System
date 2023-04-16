package testing;

import src.*;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.*;

import src.CPL;
import src.CPL_Ballot;
import src.CPL_Candidate;


public class ElectionTestCPL {

    CPL cpl;
    String path = "C:\\Users\\justi\\Desktop\\repo-Team3-Chiemeka\\Project2\\testing"; // need to edit for your machine
    
    @Before
    public void setUp(){

        //C:\\Users\\chiem\\OneDrive - Marshall Public Schools\\Desktop\\repo-Team3\\CSCI 5801 Election System\\testCPL.txt
        //"C:\\Users\\Sidney\\OneDrive\\Desktop\\UMN Classes\\New folder\\repo-Team3\\CSCI 5801 Election System>"
    try{
        
        
       
        File file = new File(path +"\\testCPL.csv");
    
       
        cpl = new CPL(file);
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
    
    assertEquals("Testing to see if reads in the right number of Parties: ",expectedPartyNumber, actualPartyNumber);
    assertEquals("Testing to see if parties read in match: ", expectedParties, actualParties);
   

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
        assertEquals("Testing to see if reads in the right candidates for each party: ", expectedCandiates[i], actualCandiates);
        i++;

    }
   

}

@Test 

//potential bug, only passes test when ran individual not with the rest of the tests

public void testPopulateBallots(){ //test the populate ballots that is used in the constructor to see if it populated the data correctly
    
   String[] expectedBallots = {"Ballot 0: 1,,,,,,", "Ballot 1: ,,,,,,1", "Ballot 2: ,1,,,,,", "Ballot 3: ,,,,1,,", "Ballot 4: ,,,,,1,", "Ballot 5: ,,,1,,,", "Ballot 6: ,,1,,,,", "Ballot 7: 1,,,,,,", "Ballot 8: ,1,,,,,"};
   CPL_Ballot[] ballots = cpl.getBallots();
  
   for(int i = 0; i < expectedBallots.length; i++){

    assertEquals("Testing to see if read in ballots: ",expectedBallots[i], ballots[i].toString());

   }
   
    String expectedSeats = "6";
    String actualSeats = cpl.getTotalSeats() + "";
    assertEquals("Testing to see if read total number of seats: ",expectedSeats, actualSeats);




}

@Test 

public void testgetNumParties(){ //test to see if num parties get works

   String expectedNumber = 7 + "";
   String actualNumber = cpl.getNumParties() + "";

   assertEquals("Testing to see if getter works: ",expectedNumber, actualNumber);



}



@Test 

public void testgetParty(){ //test to see if num parties get works
    String expectedParties = "Democratic Republican New Wave Reform Green Independent Test ";
    String actualParties = "";

    Party[] parties = cpl.getParties();
    for(Party party: parties){
        actualParties = actualParties + party.getName() + " ";

    }

    assertEquals("Testing getter: ", expectedParties, actualParties);


}


@Test 

public void testgetNumCandidates(){ //test to see if num parties get works
    String expectedNum = "14";
    String actualNum = cpl.getNumCandidates() + "";

    
    assertEquals("Testing getter: ", expectedNum, actualNum);


}

@Test 

public void testgetTotalSeats(){ //test to see if num parties get works
   
    String expectedSeats = "6";
    String actualSeats = cpl.getTotalSeats() + "";

    assertEquals("Testing getter: ",expectedSeats, actualSeats);


}

@Test 

public void testgetBallots(){ //test to see if num parties get works
    String[] expectedBallots = {"Ballot 0: 1,,,,,,", "Ballot 1: ,,,,,,1", "Ballot 2: ,1,,,,,", "Ballot 3: ,,,,1,,", "Ballot 4: ,,,,,1,", "Ballot 5: ,,,1,,,", "Ballot 6: ,,1,,,,", "Ballot 7: 1,,,,,,", "Ballot 8: ,1,,,,,"};
    CPL_Ballot[] ballots = cpl.getBallots();
   
    for(int i = 0; i < expectedBallots.length; i++){
 
     assertEquals("Testing to see if read in ballots: ",expectedBallots[i], ballots[i].toString());
 
    }
}


@Test 

public void testAssignBallots(){ //test to see if ballots are assigned correctly to parties
    String[] expectedBallots = {"Ballot 0: 1,,,,,,", "Ballot 1: ,,,,,,1", "Ballot 2: ,1,,,,,", "Ballot 3: ,,,,1,,", "Ballot 4: ,,,,,1,", "Ballot 5: ,,,1,,,", "Ballot 6: ,,1,,,,", "Ballot 7: 1,,,,,,", "Ballot 8: ,1,,,,,"};
    CPL_Ballot[] ballots = cpl.getBallots();
   
    for(int i = 0; i < expectedBallots.length; i++){
 
     assertEquals("Testing to see if read in ballots: ",expectedBallots[i], ballots[i].toString());
 
    }

}

@Test 

public void testdistrubuteSeatsCoinToss(){ //test to see if seats distribute correctly with tie breaker too. Tests the first and second rounds
    CPL cpltest = null;
    String expected = "fair around 50-50";
    String actual = "";
    
    int count = 0; //how many times extra seat is given to one of the two parties
    for(int i = 0; i < 1000; i++){
    try{
        File file = new File(path + "\\testDistrubuteSeatCoinToss.csv");         
        cpltest = new CPL(file);

        //Paths.get("");
      
    }
    
    catch(IOException e){ 
        System.out.println("Error File not found!");   //cpltest.distributeSeats(); // has an error 
    }
    cpltest.assignBallots();
    cpltest.distributeSeats();


    

   
        Party[] parties = cpltest.getParties();
        int seats = parties[0].getSeats();
        System.out.print(seats);
        if(seats == 2){
            count++;
        }

    }
 
    double percent = count/1000.0;
    if(percent >= .475 && percent <=.525){ // sees if it falls within .5 with  a margin of error of .025 +/-
        actual = "fair around 50-50";

    }
    else{
        actual = "not fair";
    }
  
    assertEquals("testing the fairness of coin toss", expected, actual);




    
  
}


@Test 

public void testdistrubuteSeatsPoolSelect(){ //test to see if seats distribute correctly with tie breaker too. Tests the first and second rounds
    CPL cpltest = null;
    String expected = "fair around 33-33-33";
    String actual = "";
    
    int count = 0; //how many times extra seat is given to one of the two parties
    for(int i = 0; i < 500; i++){
    try{
        File file = new File(path + "\\testDistrubuteSeatPool.csv");         
        cpltest = new CPL(file);
      
    }
    
    catch(IOException e){ 
        System.out.println("Error File not found!");   //cpltest.distributeSeats(); // has an error 
    }
    cpltest.assignBallots();
    cpltest.distributeSeats();


    

   
        Party[] parties = cpltest.getParties();
        int seats = parties[0].getSeats();
    
        if(seats == 2){
            count++;
        }

    }
 
    double percent = count/500.0;
    if(percent >= .28 && percent <=.38){ // sees if it falls within .33 with  a margin of error of .05 +/-
        actual = "fair around 33-33-33";

    }
    else{
        actual = "not fair";
    }
  
    assertEquals("testing the fairness of ppol select", expected, actual);




    
  
}

@Test 

public void testDuplicates(){ //test to see if duplicates works
    int expected = 2;
    cpl.assignBallots();
    double qd = cpl.getNumBallots() / (cpl.getTotalSeats() * 1.0);
    qd = Math.round(qd); // rounds quota
    int q = (int) qd;
    cpl.calculateRemainingVotes(q);
    int actual = cpl.duplicates(0);
    
    assertEquals("Testing to see if duplicates are correctly found in a row, index 0-1: ", expected, actual);

    expected = 5;
    actual = cpl.duplicates(2);

    assertEquals("Testing to see if duplicates are correctly found in a row, index 2-6: ", expected, actual);

}

@Test 

public void testCalculateRemainingVotes(){ //test to see if calculating remaining votes works
    int [] arr = {0,0,1,1,1,1,1};
    cpl.assignBallots();
    double qd = cpl.getNumBallots() / (cpl.getTotalSeats() * 1.0);
    qd = Math.round(qd); // rounds quota
    int q = (int) qd;


    cpl.calculateRemainingVotes(q);
    Party [] p = cpl.getParties();

    for(int i = 0; i < arr.length; i++){
 
        assertEquals("Testing to see if remaining votes are calculated correctly: ", arr[i], p[i].getRemainderVotes());
    
       }



}

@Test 

public void testPartyGetName(){
    Party party = new Party("Obama");
    String expected = "Obama";
    String actual = party.getName();

    assertEquals("Test party getter", expected, actual);

}
@Test 
public void testAddSeatParty(){
    Party party = new Party("Obama");
    String expected = "1";
    party.addSeats(1);
    String actual =  party.getSeats()+ "";

    assertEquals("Test party add seats/getter", expected, actual);

}
@Test 
public void testremainderVotes(){
    Party party = new Party("Obama");
    String expected = "1";
    party.addSeats(1);
    party.setRemainderVotes(1);
    String actual =  party.getRemainderVotes()+ "";

    assertEquals("Test party remainder votes setter/getter", expected, actual);

}

@Test 
public void testaddVote(){
    Party party = new Party("Obama");
    String expectedNum = "1";
    String expectedBallot = "Ballot 9: 1,"; // ballot 9 becaause static varaible modified from cpl object made for class
    party.initilizeBallotCapacity(1);
    CPL_Ballot ballot = new CPL_Ballot(0, 2);
    party.addVote(ballot);
    CPL_Ballot[] ballots = party.getBallots();
    String actualNum =  party.getVotes() + "";
    String actualBallot = ballots[0].toString();

    assertEquals("Test party add vote number", expectedNum, actualNum);
    assertEquals("Test party ballot to see if was added", expectedBallot, actualBallot);

}

@Test 
public void testDistributeSeatCandidatePool(){
   

    CPL cpltest = null;
    String expected = "fair around 50-50";
    String actual = "";
    
    int count = 0; //how many times extra seat is given to one of the two parties
    for(int i = 0; i < 1000; i++){
        try{
        File file = new File(path + "\\testDistrubuteSeatPoolCandidates.csv");         
        cpltest = new CPL(file);
      }
    catch(IOException e){ 
        System.out.println("Error File not found!");   //cpltest.distributeSeats(); // has an error 
    }
    cpltest.assignBallots();
    cpltest.distributeSeats();


    

   
        Party[] parties = cpltest.getParties();
        CPL_Candidate[] candidates = parties[0].getCandidates();
        int seats = candidates[0].getSeats();
        
        if(seats == 2){
            count++;
        }
    }
 
    double percent = count/1000.0;
    if(percent >= .475 && percent <=.525){ // sees if it falls within .5 with  a margin of error of .025 +/-
        actual = "fair around 50-50";

    }
    else{
        actual = "not fair";
  
    
    }

    assertEquals("testing the fairness of pool select for candidates", expected, actual);



    
  


}

@Test 
public void testAddSeatCandidate(){
    CPL_Candidate candidate = new CPL_Candidate("Bob", "Democrats");
    String expected = "1";
    candidate.addSeat();
    String actual =  candidate.getSeats()+ "";

    assertEquals("Test candidate add seats/getter", expected, actual);

}

@Test 
public void testgetCandidateName(){
    CPL_Candidate candidate = new CPL_Candidate("Bob", "Democrats");
    String expected = "Bob";
    
    String actual =  candidate.getName();

    assertEquals("Test candidate getName", expected, actual);

}

@Test 
public void testMultipleFiles(){
    String[] fileNames = {path + "\\CPLfile1.csv", path + "\\CPLfile2.csv", path + "\\CPLfile3.csv"};
    File[] files = new File[fileNames.length];
            for(int i = 0; i < fileNames.length; i++){
                files[i] = new File(fileNames[i]);

            }
    CPL cpl = new CPL(files);

   
    String expected = fileNames[0] + " " + fileNames[1] + " " + fileNames[2];
    
    String actual =  cpl.getFiles();

    assertEquals("Testing bringing in multiple files", expected, actual);

}
}






