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
    private File election_file;

    public CPL(File file){
        audit = new CPL_Audit_File(); // intitalzies the audit file
        populateData(file); // intitalizes the values from the read in files
}
    
    void run(File file){
        

        distributeSeat(); // distributes seats to parties

    }

    public int getNumParties(){
        return numParties;

    }

    public int getNumCandidates(){
        return numCandidates;

    }
    public int getTotalSeats(){
        return totalSeats;

    }
    public int getNumBallots(){
        return numBallots;

    }
    
    public void distributeSeat(){


    }

    public void populateData(File file){
        try {  
            Scanner sc = new Scanner(file);
            sc.nextLine(); //ignores first line since it was read already in the Election Class
            numParties =  sc.nextInt(); //reads in the 2nd line - number of parties
            String[] partyNames = sc.nextLine().split(","); // 3rd line -- gets names of the party
            parties = new Party[numParties];
            for(int i=0; i < numParties; i++){ //creates parties 
                partyNames[i] = partyNames[i].trim(); // remove leading space between comma and name : , green party = green party
                parties[i] = new Party(partyNames[i]);
            }
            for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
                String[] Candidate_names =  sc.nextLine().split(",");
                parties[i].populateCandidates(Candidate_names, Candidate_names.length);
                
            }
            totalSeats =  sc.nextInt(); // reads in the total number of seats avaliable
            numBallots =  sc.nextInt(); // reads in the total number of ballots in election
            for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
               
                parties[i].initilizeBallotCapacity(numBallots); //initialize capacity of ballots for each party to be total ballots in election
            }

            while(sc.hasNextLine()){ //reads until the end of file
                

            }
    
    
           
    
}
catch(IOException e){ 
    System.out.println("Invalid File"); // catches error
}
sc.close();
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
