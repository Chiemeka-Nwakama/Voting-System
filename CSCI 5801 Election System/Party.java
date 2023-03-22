import java.util.Arrays;

public class Party {
    private CPL_Candidate[] candidates;
    private CPL_Ballot[] partyBallots;
    private int totalVotes;
    private int remainderVotes;
    private int seats;
    private String name;
    private static int count = 0;
    private int id;
 

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
    public void addSeats(){
        seats++;

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

    public int getId(){
        return id;
    }

    

    
}
