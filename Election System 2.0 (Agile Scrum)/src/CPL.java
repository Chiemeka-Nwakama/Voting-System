package src;
import java.util.Scanner;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class CPL {
    private int numParties;
    private int numCandidates;
    private int totalSeats;
    private int numBallots;
    private Party[] parties;
    private int seatsRemaining;
    private ArrayList<CPL_Ballot> ballots;
    private File[] files;
    private CPL_Audit_File audit;
    private final int randomConstant = 1000;
    public Scanner sc;
    
    /**
   * This constructor takes in the election file and kicks off the population of data into data types by calling various methods within
   * @param file
   */

    public CPL(File[] files) throws FileNotFoundException{
        this.files = files; // initilize file list
        this.ballots = new ArrayList<>(); // initialize ballot arraylist
        audit = new CPL_Audit_File(); // intitalzies the audit file
        audit.writeToAudit("CPL ELECTION");
        audit.writeToAudit("AUDIT FILE INITIALIZED");
        audit.writeToAudit("POPULATING DATA FROM CPL ELECTION FILE...\n");
        
        sc = new Scanner(files[0]);
        sc.nextLine(); // Skip CPL line
        populateParties(files[0], sc);
        populateCandidates(files[0], sc);
        totalSeats = sc.nextInt(); // assumes total seats is the same for all files, so no need to rescan
        audit.writeToAudit("Total Seats: " + totalSeats);
        sc.close();
   
        audit.writeToAudit("BALLOTS:");
        for (int i = 0; i < files.length; i++) {
            sc = new Scanner(files[i]);

            // skip header
            for (int j = 0; j < numParties + 4; j++) {
                sc.nextLine();
            }

            populateBallots(files[i], sc);

            sc.close();

        }
        

        audit.writeToAudit("DATA SUCCESSFULLY POPULATED!\n");
       
    }

    /**
   * This method returns the path names if the files in the format "filepath1 filepath2". This metod is to test multiple files feature
   * @return s String containing pathnames of all files being brought in
   */

    public String getFiles(){
        String s = files[0].toString();
        for(int i = 1; i < files.length; i++){
        s = s + " " + files[i].toString();
        }
        return s;
    }
    /**
   * This method runs the entire election from start to finish using various methods along the way writes to the audit file
   * @return void
   */
    public void run(){
        audit.writeToAudit("START OF CPL ELECTION\n");
        audit.writeToAudit("ASSIGNING BALLOTS TO PARTIES: ");

        assignBallots(); // distibutes Ballots to the parties associated with the parties voted for
        audit.writeToAudit("\nDISTRIBUTING SEATS TO PARTIES: ");
        distributeSeats(); // distributes seats to parties
        audit.writeToAudit("END OF ELECTION"); // writes that the election is over to audit
        audit.outputAudit(); // output audit file to file named "audit" once the election is completed        
        displayResults(); //displays results since election has been completed

        clearBallots();


  

    }
    
    /**
   * This method returns the number of Parties in election
   * @param void 
   * @return A number of parties
   */

    public int getNumParties(){
        return numParties;

    }
      
     /**
   * This method returns the number of candidates in election
   * @param void 
   * @return A number of candidates
   */

    public int getNumCandidates(){
        return numCandidates;

    }

        /**
   * This method returns the total number of seats in election
   * @param void 
   * @return A number of seats
   */

    public int getTotalSeats(){
        return totalSeats;

    }

     /**
   * This method returns the parties
   * @param void 
   * @return parties
   */

   public Party[] getParties(){
    return parties;

}



  /**
   * This method returns the ballots in election
   * @param void 
   * @return ballots
   */

   public ArrayList<CPL_Ballot> getBallots(){
    return ballots;

}

        /**
   * This method returns the number of ballots in election
   * @param void 
   * @return A number of ballots
   */

    public int getNumBallots(){
        return numBallots;

    }


    

    /**
   * This method distributes the seats of in the election in two round allocations to the parties based on votes and the quota, ran after assigning ballots
   * @param void
   * @return void
   */

    
    public void distributeSeats(){
        double numBallotsDec = numBallots;
        double q = numBallotsDec / totalSeats; //calculates the quotes to be used to determine seats handed out
        int quota = (int) Math.round(q);
        if(quota == 1){
            quota = 2; // quota can never be 1 messes up how many seats
        }
        seatsRemaining = totalSeats; //sets remaining seats to the total seats to give out in the election
        Party temp[] = parties.clone(); // party list since they will be reordered by their remainders in descending order later
        audit.writeToAudit("Calculating Quota: total Votes / total Seats");
        audit.writeToAudit("Quota = " + numBallots + " / " + totalSeats + " = "  + quota + " (rounded)");

        audit.writeToAudit("\nFIRST ROUND OF SEAT DISTRIBUTION:");
        audit.writeToAudit("Seats Remaining: " + seatsRemaining);
        firstRound(quota);

        audit.writeToAudit("\nSECOND ROUND OF SEAT DISTRIBUTION:");
        audit.writeToAudit("Seats Remaining: " + seatsRemaining);
        audit.writeToAudit("Votes Remaining: ");
        calculateRemainingVotes(quota); // calculates the remaining votes left to distribute seats
        for(int i = 0; i < numParties; i++) {
            audit.writeToAudit(parties[i].getName() + ": " + parties[i].getRemainderVotes());
        }
        audit.writeToAudit("");
        secondRound(); // begins the second round which involves giving out the remaining seats based on the remainder

        
        audit.writeToAudit("\nFINAL RESULTS:");
        parties = temp.clone(); // corrects parties back to orginal order
        for(int i =0; i < numParties; i++) {
            audit.writeToAudit(parties[i].getName() + " Seats: " + parties[i].getSeats());
    
        }
        audit.writeToAudit("PARTY SEAT DISTRIBUTION COMPLETED!\n");
        audit.writeToAudit("CANDIDATE SEAT DISTRIBUTION:\n");
        for(int i = 0; i < numParties; i++){ //distrbutes seats to candidates in every party
            parties[i].distributeSeats(audit);
        }
   
}

    /**
   * This method distributes the seats of in the election in the first round allocations to the parties based on votes and the quota, remainders seats are distributed in round 2
   * @param quota the quota of the election used to determine how many seats to give out based on the votes a party got
   * @return void
   */

    public void firstRound(int quota){
        String results = "\nResults:\n";
        for(int i = 0; i < numParties && seatsRemaining > 0; i++){
           int seats =  parties[i].getVotes()/quota; //calculates the seats the a part will be granted before remainder
           parties[i].addSeats(seats);
           audit.writeToAudit("Allocating " + seats + " seats to " + parties[i].getName());
           seatsRemaining = seatsRemaining - seats;

           results += parties[i].getName() + " Seats: " + seats + "\n";

        }

        audit.writeToAudit(results);
    }
   /**
   * This method distributes the seats of in the election in the second round allocations based on remainder on votes  based on who has the highest remainder gets priority for remainder seats
   * @param void
   * @return void
   */
    public void secondRound(){
        sortParties(parties);

        for(int i = 0; i < numParties-1 && seatsRemaining > 0; i++) {
            int dups = duplicates(i); // num duplicates
            if(seatsRemaining < dups) {
                audit.writeToAudit("A tie has occurred");
                if(dups == 2){
                    audit.writeToAudit("Coin Toss between two parties is initiated");
                    int coin = coinToss();
       
                    if(coin == 0) {
                        audit.writeToAudit(parties[i].getName() + " has won a seat!");
                        parties[i].addSeats(1);
                                        
                    }else{
                        audit.writeToAudit(parties[i+1].getName() + " has won a seat!");
                        parties[i+1].addSeats(1);
                    }
                    seatsRemaining--;
 
                }else{
                    audit.writeToAudit("Pool select between 3 or more parties is initiated\n");
                    poolselect(dups, i); // adds what index currently on to winner index to scale with which parties got pooled
         
                    
                }
                i = i + (dups-1); // subtract 1 as the loop increases by 1

            }else{
                audit.writeToAudit(parties[i].getName() + " has won a seat!");
                parties[i].addSeats(1);
            }
            
        }
   
    }





    /**
   * This method checks for how many parties of a give party with a remainder votes has that same amount of remainder votes
   * @param index the index of the party that we are checking for duplicate remainder votes
   * @return void
   */

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

        ballots.forEach((b) -> {
            int partyVote = b.getPartyVote();
            parties[partyVote].addVote(b);
            audit.writeToAudit("Assigning " +  b + " to " + parties[partyVote].getName());        
        });

        audit.writeToAudit("BALLOT ASSIGNMENT AND VOTE COUNT COMPLETE!\n");
        audit.writeToAudit("Results:\n ");
        for(Party party: parties){ //iterates through each party
            audit.writeToAudit(party.getName() + ":");
            int partyVotes = party.getVotes(); //uses total votes to know when to stop looking for ballots since the rest will be null
            audit.writeToAudit("Total Votes: " + partyVotes); // writes total votes to audit
            audit.writeToAudit("Ballots Earned: "); // writes ballots earned to audit
            ArrayList <CPL_Ballot> partyBallots = new ArrayList<>();
            partyBallots = party.getBallots();
            for(int i = 0; i < partyVotes; i++){ //iterates through the ballot array in each party after distrubtion
                audit.writeToAudit(partyBallots.get(i).toString());  // writes the each ballot the party earned to audit
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

    public void populateParties(File file, Scanner sc) throws FileNotFoundException{
        numParties =  sc.nextInt(); //reads in the 2nd line - number of parties
        audit.writeToAudit("Number of Parties: " + numParties + "\n");
        sc.nextLine();
        String[] partyNames = sc.nextLine().split(","); // 3rd line -- gets names of the party
        parties = new Party[numParties];
        audit.writeToAudit("PARTIES: ");
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


public void populateCandidates(File file, Scanner sc) throws FileNotFoundException{ //split into poluate parties, candidates, ballot methods  
    audit.writeToAudit("\nCANDIDATES:");

    for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
        String[] Candidate_names =  sc.nextLine().split(",");
        parties[i].populateCandidates(Candidate_names, Candidate_names.length);
        numCandidates += Candidate_names.length;
        
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
    int fileBallots =  sc.nextInt(); // reads in the total number of seats avaliable, assumes same for each file

    int id_num = numBallots;    
    numBallots += fileBallots; 
    sc.nextLine();

    for (int i = 0; i < fileBallots; i++) {
        String ballot = sc.nextLine();
        int partyVote = ballot.indexOf("1"); // gets the pos of the vote
        CPL_Ballot individual_ballot = new CPL_Ballot(partyVote, numParties, (id_num+i));
        ballots.add(individual_ballot);
        audit.writeToAudit(ballots.get(id_num+i).toString()); //prints out the ballots by id and their integer array repesentation         

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


     /**
   * This method pools parties together assigns a random number, and see who's rand number is closest to another generated random number.
   * @param ties how many parties tied
   * @return The number of the indivdaul in the pooled selection that was the closest to the random number (the winner)
   */

/* SUMMARY: 
partiesTemp stores the duplicate parties only
assignedNumbers should be the same length and stores the assigned random numbers
Two while loops. While there are no more seats to give, for the first round we find a winner based on the random numbers, give it a seat, and remove it. The next round we repeat with the remaining parties until there are no more seats
*/

    public int poolselect(int ties, int begin){
        ArrayList<Party> partiesTemp = new ArrayList<Party>(); // Create an ArrayList object
        ArrayList<Integer> assignedNumbers = new ArrayList<Integer>();
        Random randomNum = new Random(); //create random object where we will produce our random numbers
        boolean winner = false;
        int index = 0;

        // Copy over parties to arraylist
        for(int i = begin; i < (begin + ties); i++) {
            partiesTemp.add(parties[i]);
        }

        audit.writeToAudit("Parties in the pooling:");
        for(int i = 0; i < partiesTemp.size(); i++) {
            audit.writeToAudit(i + ": " + partiesTemp.get(i).getName());
        }

       // while there are seats to give
        while(seatsRemaining > 0) {

           // participating parties for the current round of choosing 1 winner
            audit.writeToAudit("\nParticipating parties in current round of choosing a winner:");
            for(int i = 0; i < partiesTemp.size(); i++) {
                audit.writeToAudit(i + ": " + partiesTemp.get(i).getName());
            }

            audit.writeToAudit("\nSeats remaining: " + seatsRemaining);
            // whie we have not choosen a winner to give 1 seat to based on random numbers
            while(winner == false) {
                winner = true;

                for(int i = 0; i < randomConstant; i++){ // generates 1000 times to make the random generator truly random
                    randomNum.nextInt(randomConstant);
                }

                // chosen random number
                int genNum = randomNum.nextInt(randomConstant);
                audit.writeToAudit("Choosen randomized number: " + genNum);


                audit.writeToAudit("\nGenerating and assigning random numbers to parties:");
                // assign the random numbers to the parties
                for(int i = 0; i < ties; i++){
                    assignedNumbers.add(randomNum.nextInt(randomConstant)); //assigns a random number to each candidate
                    audit.writeToAudit(partiesTemp.get(i).getName() + ": Assigned number = " + assignedNumbers.get(i));
                }
        
                
                int leastDifference = Math.abs(genNum - assignedNumbers.get(0));
                
                // compare differences until a winner is found
                for(int i = 1; i < ties && winner; i++){

                    int currDifference = Math.abs(genNum - assignedNumbers.get(i));

                    if(leastDifference == currDifference){ //if there are two candidates with the same assigned rand number, winner is set to false loop is exited and the pool selection is restarted
                        winner = false; //sets winner to false since winner has not been selected start over
                    }
                    else if(leastDifference > currDifference){ // leastDiffeence is greater than the curr candidates  assigned number difference
                        leastDifference = currDifference; // makes the candidate a candidate to be the new winner
                        index = i;
        
                    }

                }
        
            }
            partiesTemp.get(index).addSeats(1);
            seatsRemaining--;
           
           

            audit.writeToAudit("\nWinner selected");
            audit.writeToAudit(partiesTemp.get(index).getName() + " had the number closest to the choosen randomized number");
            audit.writeToAudit(partiesTemp.get(index).getName() + " won a seat!"); 
            partiesTemp.remove(index);
            assignedNumbers.remove(index);
            index = 0;
            winner = false;
            ties--;

            

        }


       
    return 0; //returns the index of the winner

     }

      /**
   * This method sorts parties using Insertion sort by remainder of votes in descending order
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



    /**
   * This method prints out all of the important results that the election judge and public would want to see from the election
   * @param void
   * @return void
   */
    public void displayResults(){
        //displays results exactly as they are being print below 
        //we are displaying information about the election such as number of candidates, ballots, party names along with votes recieved and seats earned and caniddate names along with seats they earned
        String results = "";
         
        results += "-----CLOSED PARTY LIST ELECTION RESULTS AND SUMMARY-----\n\n";
        results += "GENERAL INFORMATION ABOUT THE ELECTION\n";
        results += "Number of Parties: " + numParties + "\n";
        results += "Number of Candidates: " + numCandidates + "\n";
        results += "Number of Ballots cast: " + numBallots + "\n";
        results += "Total Number of Seats to in Election: " + totalSeats + "\n";
        results += "Party names, number of votes received, and how many seats earned:\n\n"; 
        for(int i = 0; i < numParties; i++){
            results += parties[i].getName() + " \nVotes: " + parties[i].getVotes() + "  Seats: " + parties[i].getSeats();
            CPL_Candidate[] candiates = parties[i].getCandidates();
            results += "\nCandidates name and number of seats received:\n";

        
            for(int j = 0; j < candiates.length; j++){
                results += candiates[j].getName() + " " + "Seats: " + candiates[j].getSeats() + "\n";
            }
            results += "\n";
        
        }

        System.out.println(results);
        audit.writeToAudit("\n" + results);
    

    }

            /**
   * This method clears out the parties setting all vals to null
   * @param parties parties to clear
   * @return void
   */
   
    public void clearParties(Party[] parties){
        Arrays.fill(parties, null);

    }

               /**
   * This method clears out the ballots setting all values in array to null
   * @param ballots to clear
   * @return void
   */
    public void clearBallots(){
        Collections.fill(ballots, null); // clears ballots since no longer need since they are in the parties now
    }

    
}