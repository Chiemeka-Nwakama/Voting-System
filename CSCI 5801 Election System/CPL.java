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
        try {  
        Scanner sc = new Scanner(file);
        sc.nextLine(); //ignores first line since it was read already in the Election Class
        numParties =  sc.nextInt(); //reads in the 2nd line - number of parties
        sc.nextLine(); // skip the rest of the line containing the numParties
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

        sc.close();

    }
    catch(IOException e){
        System.out.println("Error: File could not be opened"); 

    }
}
    
    void run(File file){

        System.out.print("Num Parties:");
        System.out.println(numParties);
        System.out.println();
        System.out.print("num Candidates: ");
        System.out.println(numCandidates);
        System.out.println();
        System.out.print("Num Ballots: ");
        System.out.println(numBallots);
        System.out.println();
        System.out.print("Total Seats: ");
        System.out.println(totalSeats);
        System.out.println();
        System.out.println();
        System.out.println("Parties: ");

        for(int i=0; i < numParties; i++){ //creates parties 
            System.out.println(parties[i].getName());
        }



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

    /*
    public int coinToss(){

    }
    public int poolselect(){

    }
    */
    
    public void assignBallots(){

    }
    public void displayResults(){

    }
   
    public void clearParties(Party[] parties){

    }
    public void clearBallots(CPL_Ballot[] ballots){

    }

    
}
