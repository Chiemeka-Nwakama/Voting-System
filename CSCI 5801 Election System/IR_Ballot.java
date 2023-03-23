public class IR_Ballot {
    private int[] ballot;
    private int currentVote; //holds the ranking of the current vote
    private int currentCandidate; //holds the index of the candadate for the current vote
    private int ballotID; //holds index for ballot in IR's ballot array

    public IR_Ballot(int[] rankings, int index){
        ballot = rankings;
        for (int a = 0; a <= ballot.length; a++){ //parse through ballot for first vote
            if (ballot[a] == 1){
                currentCandidate = a; //assign candidate index to currentCandidate
                break;
            }
        }
        currentVote = 1;
        ballotID = index;
    }

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

    public int getCurrentVote(){return currentCandidate;} //returns index of currently voted for candidate

    public int getBallotID(){return ballotID;} 

    
}
