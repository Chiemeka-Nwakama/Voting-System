package src;
import java.io.*;
import java.util.Arrays;
import java.io.IOException;
public class IR_Audit_File {
    private File auditFile;
    private FileWriter writer;
    private String result;
    
    /** 
    *@brief This is a constructor initializer for IR audit file
    *@param void
    **/
    public IR_Audit_File(){
        auditFile = new File("audit");
        result = "";
        try{
            writer = new FileWriter(auditFile);
        } catch (IOException e) {
            System.out.println("Cannot write IR results to audit file.");
            e.printStackTrace();
        }
    }

    /** 
    *@brief Write the result steps of the IR election to the audit file
    *@param thing A string that will store all of the information of each step in IR election
    **/
    public void writeToAudit(String thing){
       result = result + thing + "\n";
    }

    /** 
    *@brief Write the information on the ballot to audit file
    *@param ballot IR_Ballot of the election
    **/
    public void writeBallot(IR_Ballot ballot){
        result = result + (Integer.toString(ballot.getBallotID()) + ": " + Arrays.toString(ballot.getBallot()) + "\n");
    }

    /** 
    *@brief Write the Candidate votes for each stage in the election
    *@param candidates Each candidates information will be writing in audit file
    **/
    public void writeCandidateBallots(IR_Candidate candidate){
        result = result + (candidate.getName() + ": ");
        result = result + (candidate.getVotes() + " Votes\nBallot(s):\n");
        int[] ballots = candidate.getBallots();
        if (candidate.getVotes() > 0){
            result = result + (Integer.toString(ballots[0]));
        }
        for (int a = 1; a < candidate.getVotes(); a++){
            result = result + ", " + (Integer.toString(ballots[a]));
        }
        result = result + "\n";
    }

    /** 
    *@brief The method is where information write to the file will be output to the actual IR audit file
    *@param void
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
