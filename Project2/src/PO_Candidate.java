package src;
import org.w3c.dom.NameList;

public class PO_Candidate {

    private String name;
    private String partyName;
    private int votes;

      /**
   * This Constructor creates a CPL_Candidate taking in a name and partyName
   * @param name name of the candidate
   * @param partyName name of the party the candidate belongs to
   */
    public PO_Candidate(String name, String partyName){
        this.name = name;
        this.partyName = partyName;


    }

       /**
   * This method gets the votes that a candidate has and returns it
   * @param void
   * @return the votes that a candidate has
   */


    public int getVotes(){
       return votes;
    }

           /**
   * This method add the votes to candiate
   * @return void
   */

   public void addVote(){
     votes++;
 }




     /**
   * This method gets the name of the candidate and returns 
   * @param void
   * @return the name of the candidate
   */
    
    public String getName(){
        return name;
     }


        /**
   * This method gets the party intial of the candidate and returns 
   * @param void
   * @return the party initial of the candidate
   */
    
    public String getParty(){
        return partyName;
     }


    

    
} 