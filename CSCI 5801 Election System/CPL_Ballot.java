import java.util.Arrays;

public class CPL_Ballot {
    private int ballot[];
    private int partyVote;
 

    public CPL_Ballot(int partyVote, int amountOfParties){
        ballot = new int[amountOfParties];
        Arrays.fill(ballot, 0); // fill entire array with 0s
        ballot[partyVote] = 1; // set vote position to 1
        this.partyVote = partyVote;

    }

    public int getPartyVote(){
        return partyVote;

    }

}


