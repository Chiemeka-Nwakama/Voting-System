public class IR_Candidate {
    private IR_Ballot[] ballots;
    private String name;
    boolean status;
    int votes;
    public IR_Candidate(String name, int totalBallots){
        this.name = name;
        ballots = new IR_Ballot[totalBallots]; //makes ballots capacity = to total balltos since a voter could potentially have all of the votes by the end of the IR algorithm


    }

    /**
    *@param void
    *@return status
     */

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public String getName(){
        return name;
    }

    
}
