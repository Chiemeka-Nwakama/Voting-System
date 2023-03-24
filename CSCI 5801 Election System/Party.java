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
 

    public Party(String name){
        this.name = name;
        totalVotes = 0;
        seats = 0;
        remainderVotes = 0;
        id = count;
        count++;

    } 

    public void populateCandidates(String[] candidateNames, int numCandidates){
        candidates = new CPL_Candidate[numCandidates]; // intitalizes candidate list
        for(int i = 0; i < numCandidates; i++){ //creates candidates inaccordance to how many there are
            candidates[i] = new CPL_Candidate(candidateNames[i].trim(), name); //populates each candidate one by one
    }
    
}

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

    public CPL_Ballot[] getBallots(){
        return partyBallots;

    }
    public String getName(){
        return name;

    }
    public void addVote(CPL_Ballot ballot){
        partyBallots[totalVotes] = ballot; //gives voter's ballot to party
        totalVotes++; //adds to total votes party has

    }
    public int getVotes(){
        return totalVotes;

    }
    public void addSeats(int amountOfSeats){
        seats = seats + amountOfSeats;

    }
    public int getSeats(){

        return seats;
    }

    public int getRemainderVotes(){
        return remainderVotes;

    }
    public void setRemainderVotes(int votes){
        remainderVotes = votes;
        
    }

    public void distributeSeats(CPL_Audit_File audit){
        int remainingSeats = seats;
        audit.writeToAudit("First Round of Distribution:");
        for(int i = 0; i < candidates.length && remainingSeats > 0; i++){ // goes in order of candidates to give seats
            candidates[i].addSeat(); // adds seat to candidate
            audit.writeToAudit("Adding seat to " + candidates[i].getName());
            remainingSeats--; //removes a seat
        }
        
        for(CPL_Candidate candidate: candidates){
            audit.writeToAudit("Candidates ");
        }
        if(remainingSeats > 0){
            while(remainingSeats > 0){
                


            }
        }


    }

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
    



    public int getId(){
        return id;
    }

    

    
}