import org.w3c.dom.NameList;

public class CPL_Candidate {
    private int seats;
    private String name;
    private String partyName;

      /**
   * This Constructor creates a CPL_Candidate taking in a name and partyName
   * @param name name of the candidate
   * @param partyName name of the party the candidate belongs to
   */
    public CPL_Candidate(String name, String partyName){
        this.name = name;
        this.partyName = partyName;
        seats = 0;

    }

       /**
   * This method gets the seats that a candidate has and returns it
   * @param void
   * @return the seats that a candidate has
   */

    public int getSeats(){
       return seats;
    }

           /**
   * This method add the seats to candiate
   * @return void
   */

   public void addSeat(){
     seats++;
 }




     /**
   * This method gets the name of the candidate and returns 
   * @param void
   * @return the name of the candidate
   */
    
    public String getName(){
        return name;
     }

    

    
} 
