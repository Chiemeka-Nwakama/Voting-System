import java.util.Arrays;
import java.util.Scanner;
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

    
    try{
        File file = new File("test.txt");
         cpl = new CPL(file);
    }
    catch(IOException e){ 
        System.out.println("Error File not found!");
    }
    }


  
@Test 
public void testPopulateParties(){ //test the populate parties that is used in the constructor to see if it populated the data correctly
    int expectedPartyNumber =  cpl.getNumParties();
    int actualPartyNumber = 7;
    String expectedParties;
    for()
}

}


