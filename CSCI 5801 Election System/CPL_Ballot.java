import java.util.Arrays;

public class CPL_Ballot {
    private int ballot[];
    private int partyVote;
    private String partyName;
    private static int id = 0;
 

    public CPL_Ballot(int partyVote, int amountOfParties){
        ballot = new int[amountOfParties];
        Arrays.fill(ballot, 0); // fill entire array with 0s
        ballot[partyVote] = 1; // set vote position to 1
        this.partyVote = partyVote;
       
        id++;



    }

    public int getPartyVote(){
        return partyVote;

    }

    public int[] representation(){
        return ballot;
    }

    public String toString(){
        return "Ballot " + id;
    }
    public int getId(){
        return id;
    }

    

}


