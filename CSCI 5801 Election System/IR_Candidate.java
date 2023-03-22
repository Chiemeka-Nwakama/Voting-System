public class IR_Candidate {
    private IR_Ballot[] ballots;
    private boolean status;
    private int votes;
    private String name;
    public IR_Candidate(String name){
        status = true;
        this.name = name;
        votes = 0;
        ballots = new IR_Ballot[null]; //determine how large to make arrays, maybe as large as all ballots, but very memory heavy
    }

    public void addBallot(IR_Ballot ballot){
        ballots[votes] = ballot;
        votes += 1;
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

    public IR_Ballot[] getBallots(){
        return ballots;
    }

    
}
