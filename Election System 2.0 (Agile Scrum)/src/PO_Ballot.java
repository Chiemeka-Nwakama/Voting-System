package src;
import java.util.Arrays;

public class PO_Ballot {
    private String ballot[];
    private int candidateVote;
    private int id;
 
      /**
   * This Construtor creates a PO_Ballot with a string represenation and id that increases by each time a ballot is created
   * @param partyVote the index of the party the ballot is voting for
   * @param amountOfParties the amount of parties that can be voted for
   */

    public PO_Ballot(int candidateVote, int amountOfCandidates, int id_num){
        
        ballot = new String[amountOfCandidates];
        Arrays.fill(ballot, ","); // fill entire array with 0s
        ballot[candidateVote] = "1"; // set vote position to 1
        this.candidateVote = candidateVote;
        id = id_num;


    }

     /**
   * This method gets the party index that the ballot is voting for
   * @param void
   * @return the party index being voted for
   */

    public int getCandidateVote(){
        return candidateVote;

    }

     /**
   * This method returns a representation of the ballot
   * @param void
   * @return repsentation of the ballot
   */

    public String representation(){
        return toString();
    }

        /**
   * This method returns a representation of the ballot
   * @param void
   * @return repsentation of the ballot
   */

    public String toString(){
        
        String b = "";
        for(int i = 0; i < ballot.length; i++) {
            b = b + ballot[i];
        }

        return "Ballot " + id + ": " + b;
    }

        /**
   * This method gets the id of the ballot so that it can be uniquely identified 
   * @param void
   * @return id of the ballot
   */
    public int getId(){
        return id;
    }

    

}


