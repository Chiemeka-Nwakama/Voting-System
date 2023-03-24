public class IR_Ballot {
    private int[] ballot;
    private int currentVote; //holds the ranking of the current vote
    private int currentCandidate; //holds the index of the candadate for the current vote
    private int ballotID;

    /** 
    *@brief This constructor create rankings array to store the ranking of candidates
    @param rankings stores the ranking of the candidates in the elction
    @param ID stores the ID (index in array) for each candidates
    **/
    public IR_Ballot(int[] rankings, int ID){
        ballot = rankings;
        for (int a = 0; a <= ballot.length; a++){ //parse through ballot for first vote
            if (ballot[a] == 1){
                currentCandidate = a; //assign candidate index to currentCandidate
                break;
            }
        }
        currentVote = 1;
        ballotID = ID;
    }

    /** 
    *@brief The method is updating the current voting status.
    It will get called when candidate is eliminated, and update vote to the next rank
    **/
    public void updateCurrentVote(){ //called when candidate is eliminated, updates vote to next rank
        currentVote += 1;
        if (currentVote >= ballot.length){ //check if ballot has any votes left
            currentVote = -1;
            currentCandidate = -1;
        }else{
            for (int a = 0; a < ballot.length; a++){ //parse through ballot for next vote
                if (ballot[a] == currentVote){
                    currentCandidate = a; //assign candidate index to currentCandidate
                    break;
                }else{
                    currentCandidate = -1; //if ballot only didn't rank all candidates
                }
            }
        }
    }

    /** 
    *@brief method return index of currently voted for candidate
    *@return currentCandidate
    **/
    public int getCurrentVote(){return currentCandidate;} //returns index of currently voted for candidate

    /** 
    *@brief The method return current ballotID of the election
    *@return ballotID
    **/
    public int getBallotID(){return ballotID;}

    /** 
    *@brief Get the ballot array from the election
    *@return The ballot array from the election
    **/
    public int[] getBallot(){return ballot;}
}
