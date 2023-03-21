public class Party {
    private CPL_Candidate[] candidates;
    private CPL_Ballot[] partyBallots;
    private int totalVotes;
    private int remainderVotes;
    private int seats;
    private String name;
// test hello
    public Party(String name){
        this.name = name;
        


    }
    public void populateCandidates(String[] candidateNames, int numCandidates){
        candidates = new CPL_Candidate[numCandidates]; // intitalizes candidate list
        for(int i = 0; i < numCandidates; i++){ //creates candidates inaccordance to how many there are

        
        candidates[i] = new CPL_Candidate(candidateNames[i], name); //populates each candidate one by one
        

    }
}

public void initilizeBallotCapacity(int totalBallots){
    partyBallots = new CPL_Ballot[totalBallots]; // makes the ballot array as big as there are ballots in the election
}
    public void clearCandidates(){

    }
    public CPL_Candidate[] getCandidates(){

    }
    public void clearBallots(){

    }
    public CPL_Ballot[] getBallots(){
            return partyBallots;
    }
    public String getName(){
            return name;
    }
    public void addVote(){

    }
    public int getVote(){

    }
    public void addSeats(){

    }
    public int getSeats(){

    }
    public int getRemainderVotes(){

    }
    public void setRemainderVotes(int votes){
        
    }

    
}
