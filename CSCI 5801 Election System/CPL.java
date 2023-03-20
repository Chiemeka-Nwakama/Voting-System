import java.util.Scanner;
import java.io.*;
public class CPL {
    private int numParties;
    private int numCandidates;
    private int totalSeats;
    private int numBallots;
    private int[] Partyballot;
    private Party[] parties;
    private CPL_Ballot[] ballots;
    private CPL_Audit_File audit;

    public CPL(File file){
        audit = new CPL_Audit_File(); // intitalzies the audit file
        try {  
        Scanner sc = new Scanner(file);
        sc.nextLine(); //ignores first line since it was read already in the Election Class
        numParties =  sc.nextInt(); //reads in the 2nd line - number of parties
        String[] partyNames = sc.nextLine().split(","); // 3rd line -- gets names of the party
        parties = new Party[numParties];
        for(int i=0; i < numParties; i++){ //creates parties 
            parties[i] = new Party(partyNames[i]);
        }
        for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
            String[] Candidate_names =  sc.nextLine().split(",");
            parties[i].populateCandidates(Candidate_names, Candidate_names.length);
            

        }

        sc.close();

    }
    catch(IOException e){ 

    }
}
    
    void run(){

    }

    public int getParties(){

    }

    public int getCandidates(){

    }
    public int getTotalSeats(){

    }
    public int getNumBallots(){

    }
    
    public void distributeSeat(){

    }

    
    public int coinToss(){

    }
    public int poolselect(){

    }
    public void assignBallots(){

    }
    public void displayResults(){

    }
   
    public void clearParties(Party[] parties){

    }
    public void clearBallots(CPL_Ballot[] ballots){

    }

    
}
