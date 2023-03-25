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

    IR ir;
    @Before
    public void setUp(){

    
    try{
        File file = new File("C:\\Users\\Desktop\\repo-Team3\\CSCI 5801 Election System\\test.txt");
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

public void testPopulateBallots(){ //test the populate ballots that is used in the constructor to see if it populated the data correctly

}


