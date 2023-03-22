import java.util.Arrays;

public class CPL_Ballot {
    private String ballot[];
    private int partyVote;
    private String partyName;
    private static int count = 0; //keeps track of how many parties are made
    private int id;
 

    public CPL_Ballot(int partyVote, int amountOfParties){
        
        ballot = new String[amountOfParties];
        Arrays.fill(ballot, ","); // fill entire array with 0s
        ballot[partyVote] = "1"; // set vote position to 1
        this.partyVote = partyVote;
        id = count;
        count++;
        


    }

    public int getPartyVote(){
        return partyVote;

    }

    public String representation(){
        return toString();
    }

    public String toString(){
        String b = "";
        for(int i = 0; i < ballot.length; i++) {
            b = b + ballot[i];
        }

        return "Ballot " + id + ": " + b;
    }
    public int getId(){
        return id;
    }

    

}


