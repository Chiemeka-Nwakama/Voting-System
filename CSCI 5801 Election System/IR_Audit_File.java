import java.io.*;
public class IR_Audit_File {
    private File auditFile;
    private String result;
    
    /** 
    *This is a constructor initializer for IR audit file
    @param void
    **/
    public IR_Audit_File(){
        this.auditFile = new File("result");
        result = "";
    }

    /** 
    *writeToAudit() method will write the result steps of the IR election to the audit file
    @param thing A string that will store all of the information of each step in IR election
    @return void
    **/
    public void writeToAudit(String thing){
       result = result + thing + "\n";
       System.out.println(thing);
    }

    /** 
    *outputAudit() method is where information write to the file will be output to the actual IR audit file
    @param void
    @return void
    **/
    public void outputAudit(){
        System.out.print(auditFile);
         try{
            FileWriter writer = new FileWriter(auditFile);
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot write IR results to audit file.");
            e.printStackTrace();
        }
        
    }
}
