public class IR_Candidate {
    private int[] ballots;
    private boolean status;
    private int votes;
    private String name;
    private int ID;
    private boolean winner;
    private int winningBallots;
    private int curRank;

    /** 
    *@brief This constructor initial the name and candidate ID in 
    *@param name the name of the canidates in the election
    *@param candidateID store the ID(index) in array for each candidates
    **/
    public IR_Candidate(String name, int candidateID){
        curRank = 0;
        status = true;
        winner = false;
        this.name = name;
        ID = candidateID;
        votes = 0; 
    }

    /** 
    *@brief addBallot() method is to add new ballot into the voting system
    *@param ballotIndex the index of the ballot to be added
    **/
    public void addBallot(int ballotIndex){
        ballots[votes] = ballotIndex;
        votes += 1;
        if (votes >= winningBallots){
            winner = true;
        }
    }

    /** 
    *@brief Get the status of the candidate
    *@return The status of the candidate
    **/
    public boolean getStatus(){
        return status;
    }

    /** 
    *@brief Set the status of the candidate 
    **/
    public void setStatus(){
        this.status = false;
    }

    /** 
    *@brief Get the votes for the candidate 
    *@return The votes of the candidate
    **/
    public int getVotes(){
        return votes;
    }

    /** 
    *@brief Remove votes from the candidate
    **/
    public void removeVotes(){
        votes = 0;
    }

    /** 
    *@brief Get the ranking of the candidate in the election
    *@return The current ranking of the candidate
    **/
    public int getRank(){
        return curRank;
    }

    /** 
    *@brief Set the ranking of the candidate in the election
    *@param rank Candidate's current ranking
    **/
    public void setRank(int rank){
        curRank = rank;
    }

    /** 
    *@brief Get arrays that contain all of the ballots in the election
    *@return The ballots array for the election
    **/
    public int[] getBallots(){
        return ballots;
    }

    /** 
    *@brief See which candidates is the winner of the election
    *@return The winner of the IR electioin
    **/
    public boolean isWinner(){
        return winner;
    }

    /** 
    *@brief Get the name of the candidate in the election
    *@return The name of the candidate
    **/
    public String getName(){return name;} 

    /**
     * @brief Get the candidate ID number
     * @return the candidate's ID number
     */
    public int getCandidateID(){return ID;}

    /** 
    *@brief Set the array for each candidate to hold their votes for the ballots
    *@param numBallots The total number of ballots in that election
    **/
    public void setCandidateBallots(int numBallots){
        ballots = new int[numBallots];
        winningBallots = (int) Math.ceil((double)numBallots / 2); //divide totalBallots by 2, round up if not whole number
        if (numBallots % 2 == 0){
            winningBallots++; //add 1 if whole number, making it half the totalBallots + 1
        }
    }

    
}
