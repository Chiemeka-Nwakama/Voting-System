import java.util.Arrays;
import java.util.Random;

public class Party {
    private CPL_Candidate[] candidates;
    private CPL_Ballot[] partyBallots;
    private int totalVotes;
    private int remainderVotes;
    private int seats;
    private String name;
    private static int count = 0;
    private int id;
    private final int randomConstant = 1000;
 

        /**
   * This constructor makes a new party with a give name
   * @param name the name of the party
   */

    public Party(String name){
        this.name = name;
        totalVotes = 0;
        seats = 0;
        remainderVotes = 0;
        id = count;
        count++;

    } 

        /**
   * This method populates the Candidates within each party. called in the CPL constructor
   * @param candidateNames an array of the candidate names
   *  @param numCandidates the number of candidates 
   * @return void
   */


    public void populateCandidates(String[] candidateNames, int numCandidates){
        candidates = new CPL_Candidate[numCandidates]; // intitalizes candidate list
        for(int i = 0; i < numCandidates; i++){ //creates candidates inaccordance to how many there are
            candidates[i] = new CPL_Candidate(candidateNames[i].trim(), name); //populates each candidate one by one
    }
    
}

        /**
   * This method intiazlizes the array that each candidate can contain to the size of the total amount of ballots
   * @param totalBallots the total number of ballots in the election
   * @return void
   */


public void initilizeBallotCapacity(int totalBallots){
    partyBallots = new CPL_Ballot[totalBallots]; // makes the ballot array as big as there are ballots in the election
}
    public void clearCandidates(){
        // MAY NOT BE NECESSARY
        // deference ballots by setting them to null

        candidates = null;

    }

    // WOULD RETURN MEMORY ADDRESS SO I DONT THINK WE EVEN NEED THIS
    public CPL_Candidate[] getCandidates(){

        return candidates;

    }
    public void clearBallots(){
        // MAY NOT BE NECESSARY
        // deference ballots by setting them to null

        partyBallots = null;

    }

    // WOULD RETURN MEMORY ADDRESS SO I DONT THINK WE EVEN NEED THIS

        /**
   * This method returns the candidates a party has
   * @param void 
   * @return Candidates
   */


           /**
   * This method gets the ballots a party has and returns the array
   * @param void
   * @return The ballots the party has been assigned
   */

    public CPL_Ballot[] getBallots(){
        return partyBallots;

    }


              /**
   * This method gets the name of the party and returns it
   * @return name of the party
   */
    public String getName(){
        return name;

    }

    /**
   * This method adds a vote to a party by adding the ballot to the ballot list and incrementing total votes
   * @param ballot the CPL ballot being assigned to the party
   * @return void
   */
    public void addVote(CPL_Ballot ballot){
        partyBallots[totalVotes] = ballot; //gives voter's ballot to party
        totalVotes++; //adds to total votes party has

    }

                  /**
   * This method gets number of votes the party has and returns it
   * @return votes party has 
   */
    public int getVotes(){
        return totalVotes;

    }

                   /**
   * This method adds a given amount of seats to a Parties seat total
   * @param amountOfSeats the amount of seats to be added
   * @return void
   */
    public void addSeats(int amountOfSeats){
        seats = seats + amountOfSeats;

    }

                   /**
   * This method returns the amount of seats a party has
   * @return party seats
   */
    public int getSeats(){

        return seats;
    }

                    /**
   * This method returns the remainder votes a party has
   * @return party remainder votes after first round
   */
    public int getRemainderVotes(){
        return remainderVotes;

    }

                    /**
   * This method sets the remainder votes of the party
   * @param votes the remainder votes
   * @return void
   */
    public void setRemainderVotes(int votes){
        remainderVotes = votes;
        
    }

                    /**
   * This method distributes the Seats to each candidate after a party has been distrbuted their seats
   * @param audit passes the audit object to be written to
   * @return void
   */

    public void distributeSeats(CPL_Audit_File audit){
        int remainingSeats = seats;
        audit.writeToAudit("First Round of Distribution:");
        audit.writeToAudit("Seats Remaining" + remainingSeats);
        for(int i = 0; i < candidates.length && remainingSeats > 0; i++){ // goes in order of candidates to give seats
            candidates[i].addSeat(); // adds seat to candidate
            audit.writeToAudit("Adding seat to " + candidates[i].getName());
            remainingSeats--; //removes a seat
        }
        
        for(CPL_Candidate candidate: candidates){
            audit.writeToAudit(candidate.getName() + "'s Seats: " + candidate.getSeats());
        }
        if(remainingSeats > 0){
            audit.writeToAudit("Second Rounds of Distrubution");
            audit.writeToAudit("Seats Remaining: " + remainingSeats);
            while(remainingSeats > 0){
                
                audit.writeToAudit("Pool Selection:");
                audit.writeToAudit("Participants:");
                for(CPL_Candidate candidate: candidates){
                    audit.writeToAudit(candidate.getName());
                }
                int winner = poolselect(candidates.length); //perfoms pull select to give out seat
               
                candidates[winner].addSeat();
                audit.writeToAudit(candidates[winner].getName() + "has won a seat!");
                audit.writeToAudit("Seat Standings after Pool Selection:");
                for(CPL_Candidate candidate: candidates){
                    audit.writeToAudit(candidate.getName() + "'s Seats: " + candidate.getSeats());
                }
                remainingSeats--;
                audit.writeToAudit(remainingSeats + "Seats Remaining");

            }
        }
        audit.writeToAudit("Candidate Seat Distrubution for " + name + " Completed!\n");


    }

                    /**
   * This method performs a pool select given a number of candidates. Which candidate has the closer random number to another random number wins the seat
   * @param numCandidates the number of candidates being pool selected
   * @return the index of the candidate that won
   */

    public int poolselect(int numCandidates){
        int[] assignedNumbers = new int[numCandidates]; // stores assigned a random number to each candidate
        Random randomNum = new Random();
        boolean winner = false;
        int index = 0;
        while(!winner){ //while no winner of the seat is selected
        winner = true; //sets winner to true

        for(int i = 0; i < randomConstant; i++){ // generates 1000 times to make the random generator truly random


        randomNum.nextInt(randomConstant);
        }
   
        for(int i = 0; i < numCandidates; i++){
            assignedNumbers[i] = randomNum.nextInt(randomConstant); //assigns a random number to each candidate

        }

        int genNum = randomNum.nextInt(randomConstant); // generates a number to see which number assigned to a candidate is the closest

        index = 0;
        int leastDifference = Math.abs(genNum - assignedNumbers[0]);

        for(int i = 1; i < numCandidates && winner; i++){
            int currDifference = Math.abs(genNum - assignedNumbers[i]);
            if(leastDifference == currDifference){ //if there are two candidates with the same assigned rand number, winner is set to false loop is exited and the pool selection is restarted
                winner = false; //sets winner to false since winner has not been selected start over
            }
            else if(leastDifference > currDifference){ // leastDiffeence is greater than the curr candidates  assigned number difference
                leastDifference = currDifference; // makes the candidate a candidate to be the new winner
                index = i;

            }
        }
    }
    return index; //returns the index of the winner
    }
    


                /**
   * This method returns the party id
   * @param void
   * @return party id
   */
    public int getId(){
        return id;
    }

    

    
}