import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
public class CPL {
    private int numParties;
    private int numCandidates;
    private int totalSeats;
    private int numBallots;
    private Party[] parties;
    private CPL_Ballot[] ballots;
    private CPL_Audit_File audit;
    private final int randomConstant = 1000;
    public Scanner sc;
    
      /**
   * This constructor takes in the election file and kicks off the population of data into data types by calling various methods within
   * @param file
   */

    public CPL(File file) throws FileNotFoundException{
        audit = new CPL_Audit_File(); // intitalzies the audit file
        audit.writeToAudit("CPL Election found");
        audit.writeToAudit("Audit file initialized");
        audit.writeToAudit("Populating Data from CPL election file...\n");
        sc = new Scanner(file);
        populateParties(file, sc);
        populateCandidates(file, sc);
        populateBallots(file, sc);
        audit.writeToAudit("Data Succesfully Populated!\n");
        
        sc.close();
    }
      /**
   * This method runs the entire election from start to finish using various methods along the way writes to the audit file
   * @param file
   * @return void
   */
    void run(File file){
        audit.writeToAudit("Start of CPL Election");
        audit.writeToAudit("Assigning Ballots to Parties: ");
        assignBallots(); // distibutes Ballots to the parties associated with the parties voted for
        audit.writeToAudit("\nDistributing Seats to Parties: ");
        distributeSeats(); // distributes seats to parties
        audit.outputAudit(); // output audit file to file named "audit" once the election is completed

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

    /**
   * This method distributes the seats of in the election in two round allocations to the parties based on votes and the quota, ran after assigning ballots
   * @param void
   * @return void
   */

    
    public void distributeSeats(){
        int quota = numBallots / totalSeats; //calculates the quotes to be used to determine seats handed out
        audit.writeToAudit("Calculating Quota: total Votes / total Seats");
        audit.writeToAudit("Quota =  " + quota);
        int seatsRemaining = totalSeats;
        firstRound(seatsRemaining, quota);
        Party temp[] = parties.clone();
        secondRound(seatsRemaining, quota);

        
        parties = temp.clone(); // corrects parties back to orginal order

        for(int i =0; i < numParties; i++) {
            audit.writeToAudit(parties[i].getName() + "Seats: " + parties[i].getSeats());
    
    }
    audit.writeToAudit("Party Seat Distrubution Completed!");
    audit.writeToAudit("Candidate Seat Distrubtion");
    for(int i = 0; i < numParties; i++){ //distrbutes seats to candidates in every party
        parties[i].distributeSeats(audit);
    }
   
}

    public void firstRound(int seatsRemaining, int quota){
        audit.writeToAudit("\nFirst Round of Seat Distrubution:");
        audit.writeToAudit("Seats Remaining: " + seatsRemaining);
        for(int i = 0; i < numParties; i++){
           int seats =  parties[i].getVotes()/quota; //calculates the seats the a part will be granted before remainder
           parties[i].addSeats(seats);
           audit.writeToAudit("Allocating " + seats + " seats to " + parties[i].getName());
           seatsRemaining = seatsRemaining - seats;
           audit.writeToAudit(parties[i].getName() + " Seats: " + seats); // writes to audit how many seats each party was givin in frist round


        }
    }

    public void secondRound(int seatsRemaining, int quota){
        audit.writeToAudit("\nSecond Round of Seat Distrubution:");
        audit.writeToAudit("Seats Remaining: " + seatsRemaining);
        audit.writeToAudit("Votes Remaining: ");
        calculateRemainingVotes(quota); // calculates the remaining votes left to distribute seats
        for(int i = 0; i < numParties; i++) {
            audit.writeToAudit(parties[i].getName() + ": " + parties[i].getRemainderVotes());
        }

        sortParties(parties);

        System.out.println();
        System.out.println();
        System.out.println();
        for(int i = 0; i < numParties; i++) {
            System.out.println(parties[i].getName() + " votes " + parties[i].getRemainderVotes());
        }
        System.out.println();
        System.out.println();
        int b = 0;

        for(int i = 0; i < numParties-1 && seatsRemaining > 0; i++) {
            System.out.println("i: " + i);   
            int dups = duplicates(i); // num duplicates
            System.out.println("Dups: " + dups);
            if(seatsRemaining < dups) {
                if(dups == 2){
                    System.out.println("TIE");
                    // get the random value
                    int coin = coinToss();
       
                    if(coin == 0) {
                        parties[i].addSeats(1);
                                        
                    }else{
                        parties[i+1].addSeats(1);
                    }
 
                }else{
                    System.out.println("Pool");
                    
                }
            }
            i = i + (dups-1); // subtract 1 as the loop increases by 1
            
            b++;
            if(b == 2){
                break;
            }
        }
        System.out.println();
    }

    public int duplicates(int index){
        int numDups = 0;

        for(int i = index; i < numParties-1; i++) {
            if(parties[i].getRemainderVotes() == parties[i+1].getRemainderVotes()) {
                numDups++;
            }else{
                break;
            }
        }
        numDups++;
        return numDups;

    }

    
/**
   * This method calculates the remaining votes that a party has left after the first round allocation via the modulous of votes by the quota to get the remainder
   * @param quota the quota of the election calculated by taking (total votes in election)/(total seats)
   * @return void
   */

    public void calculateRemainingVotes(int quota){
        for(int i = 0; i < numParties; i++) {
            int remaining = parties[i].getVotes() % quota;
            parties[i].setRemainderVotes(remaining);
        }
    }
    /**
   * This method assigns ballots to Parties based on who they voted adding them to vote count of each party as well as giving the ballots to each parties candidates list
   * @param void
   * @return void
   */


    public void assignBallots(){

        for(CPL_Ballot ballot: ballots){
            int partyVote = ballot.getPartyVote();
            parties[partyVote].addVote(ballot);
            audit.writeToAudit("Assigning " +  ballot + " to " + parties[partyVote].getName());
        }
        Arrays.fill(ballots, null); // clears ballots since no longer need since they are in the parties now
        audit.writeToAudit("Ballots Assignment and Vote Count Complete!\n");
        audit.writeToAudit("Results:\n ");
        for(Party party: parties){ //iterates through each party
            audit.writeToAudit(party.getName() + ":");
            int partyVotes = party.getVotes(); //uses total votes to know when to stop looking for ballots since the rest will be null
            audit.writeToAudit("Total Votes: " + partyVotes); // writes total votes to audit
            audit.writeToAudit("Ballots Earned: "); // writes ballots earned to audit
            CPL_Ballot[] partyBallots = party.getBallots();
            for(int i = 0; i < partyVotes; i++){ //iterates through the ballot array in each party after distrubtion
                audit.writeToAudit(partyBallots[i].toString());  // writes the each ballot the party earned to audit
            }
            audit.writeToAudit("");
        }





    }
    /**
   * This method is called in the CPL constructor populating and intilazing the Party objects along with reading in the total number of parties
   * @param file the election file 
   * @param sc the sc already initalized in the CPL constructor
   * @return void
   */

    public void populateParties(File file, Scanner sc) {
        sc.nextLine(); //ignores first line since it was read already in the Election Class
        numParties =  sc.nextInt(); //reads in the 2nd line - number of parties
        audit.writeToAudit("Number of Parties: " + numParties);
        sc.nextLine();
        String[] partyNames = sc.nextLine().split(","); // 3rd line -- gets names of the party
        parties = new Party[numParties];
        audit.writeToAudit("Parties: ");
        for(int i=0; i < numParties; i++){ //creates parties 
            partyNames[i] = partyNames[i].trim(); // remove leading space between comma and name : , green party = green party
            parties[i] = new Party(partyNames[i]);
            audit.writeToAudit("Party " + i  + ": " + parties[i].getName());
        }   
    
}
/**
   * This method is called in the CPL constructor populating and intializing the Candidate list in each party
   * @param file the election file 
   * @param sc the sc already initalized in the CPL constructor
   * @return void
   */


public void populateCandidates(File file, Scanner sc){ //split into poluate parties, candidates, ballot methods  
    audit.writeToAudit("\nCandidates:");
    for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
        String[] Candidate_names =  sc.nextLine().split(",");
        parties[i].populateCandidates(Candidate_names, Candidate_names.length);
        
        audit.writeToAudit(parties[i].getName() + ": "); //writes party name to audit
        String candidateList = "";
        for (CPL_Candidate candidate: parties[i].getCandidates()){ //writes each candidate in each party to audit
            candidateList += candidate.getName() + ", ";

        }
        candidateList = candidateList.substring(0, candidateList.length()-2);
        audit.writeToAudit(candidateList);
        audit.writeToAudit("");

          
    }    

}
    /**
   * This method is called in the CPL constructor populating and intializing the CPL Ballot objects along with reading in the total number of seats to be given
   * @param file the election file 
   * @param sc the sc already initalized in the CPL constructor
   * @return void
   */

public void populateBallots(File file, Scanner sc) {         
    totalSeats =  sc.nextInt(); // reads in the total number of seats avaliable
    audit.writeToAudit("Total Seats: " + totalSeats);
    numBallots =  sc.nextInt(); // reads in the total number of ballots in election
    ballots = new CPL_Ballot[numBallots];
    sc.nextLine();
    audit.writeToAudit("Number of Ballots: " + numBallots);
    
    for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
           
        parties[i].initilizeBallotCapacity(numBallots); //initialize capacity of ballots for each party to be total ballots in election
    }

    audit.writeToAudit("Ballots:");
    for(int i = 0; i < numBallots; i++){
        String ballot = sc.nextLine();
        int partyVote = ballot.indexOf("1"); // gets the pos of the vote
        ballots[i] = new CPL_Ballot(partyVote, numParties); //intializes a new ballot
        audit.writeToAudit(ballots[i].toString()); //prints out the ballots by id and their integer array repesentation         
    }  
    
}
     /**
   * This method cointToss() will determine which party gets a seat in the two way tie
   * @param void 
   * @return A a randomly generate number 0 or 1, 0 for heads, 1 for tails
   */
    public int coinToss(){
        Random randomNum = new Random();
        int result = 0;
        for(int i = 0; i < randomConstant; i++ ){//flips a coin 1000 time prior to gaurantee true randomness opposed to pseudo

           result =  randomNum.nextInt(2);
    }
    result =  randomNum.nextInt(2);
    return result;
}
    public int poolselect(){
        return 0;

    }

      /**
   * This method sorts parties using Insertion sort by remainder of votes
   * @param parties an array of the parties to be sorted
   * @return void
   */
    public void sortParties(Party[] parties) // sorts party by their remainder votes
    {
        for (int j = 1; j < parties.length; j++) {  
            Party key = parties[j];
            int i = j-1;  
            while ( (i > -1) && ( parties[i].getRemainderVotes() < key.getRemainderVotes())) {  
                parties[i+1] = parties[i];  
                i--;  
            }  
            parties[i+1] = key;  
    }
}


    
    public void displayResults(){

    }
   
    public void clearParties(Party[] parties){
        Arrays.fill(parties, null);

    }
    public void clearBallots(CPL_Ballot[] ballots){
        Arrays.fill(ballots, null);
    }

    
}
