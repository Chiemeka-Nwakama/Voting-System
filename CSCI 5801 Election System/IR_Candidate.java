public class IR_Candidate {
    private IR_Ballot[] ballots;
    private boolean status;
    private int votes;
    private String name;
    private int index;
    private boolean winner;
    private int winningBallots;
    private int curRank;
    public IR_Candidate(String name, int index){
        curRank = 0;
        status = true;
        winner = false;
        this.name = name;
        this.index = index;
        votes = 0; 
    }

    public void addBallot(IR_Ballot ballot){
        ballots[votes] = ballot;
        votes += 1;
        if (votes >= winningBallots){
            winner = true;
        }
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(){
        this.status = false;
    }

    public int getVotes(){
        return votes;
    }

    public void removeVotes(){
        votes = 0;
    }

    public int getRank(){
        return curRank;
    }

    public void setRank(int rank){
        curRank = rank;
    }

    public IR_Ballot[] getBallots(){
        return ballots;
    }

    public boolean getWinner(){
        return winner;
    }

    public void setCandidateBallots(int numBallots){
        ballots = new IR_Ballot[numBallots];
        winningBallots = (int) Math.ceil((double)numBallots / 2); //divide totalBallots by 2, round up if not whole number
        if (numBallots % 2 == 0){
            winningBallots++; //add 1 if whole number, making it half the totalBallots + 1
        }
    }

    
}
