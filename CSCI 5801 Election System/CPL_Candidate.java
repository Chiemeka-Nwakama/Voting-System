import org.w3c.dom.NameList;

public class CPL_Candidate {
    private int seats;
    private String name;
    private String partyName;
    public CPL_Candidate(String name, String partyName){
        this.name = name;
        this.partyName = partyName;
        seats = 0;

    }


    public int getSeats(){
       return seats;
    }

    
    public String getName(){
        return name;
     }

    

    
} 
 // @casey526