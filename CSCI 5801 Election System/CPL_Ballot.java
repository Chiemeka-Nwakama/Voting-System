import java.util.Arrays;

public class CPL_Ballot {
    private int ballot[];
    private int partyVote;
 

    public CPL_Ballot(int pos, int amountOfParties){
        ballot = new int[amountOfParties];
        Arrays.fill(ballot, 0); // fill entire array with 0s
        ballot[pos] = 1; // set vote position to 1
        partyVote = pos;

    }

    public int getPartyVote(){
        return partyVote;

    }

}


