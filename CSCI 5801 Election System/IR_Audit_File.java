import java.io.*;
import java.util.Arrays;
public class IR_Audit_File {
    private File auditFile;
    private string result;
    
    /** 
    *@brief This is a constructor initializer for IR audit file
    *@param void
    **/
    public IR_Audit_File(){
        this.auditFile = new File();
        result = "";
        FileWriter writer = new FileWriter(auditFile);
    }

    /** 
    *@brief Write the result steps of the IR election to the audit file
    *@param thing A string that will store all of the information of each step in IR election
    *@return void
    **/
    public void writeToAudit(String thing){
       result = result + thing + "\n";
    }

    public void writeBallot(IR_Ballot ballot){
        writer.write("%i: %s\n", ballot.getBallotID(), Arrays.toString(ballot.getBallot()));
    }

    public void writeCandidateBallots(IR_Candidate candidate){
        writer.write("%s: ", candidate.getName());
        int[] ballots = candidate.getBallots();
        for (a = 0; a < IR_Candidate.getVotes; a++){
            writer.write("%i ", ballots[a]);
        }
        writer.write("\n");
    }

    /** 
    *@brief The method is where information write to the file will be output to the actual IR audit file
    *@param void
    *@return void
    **/
    public void outputAudit(){
        System.out.print(auditFile);
         try{
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot write IR results to audit file.");
            e.printStackTrace();
        }
        
    }
}
