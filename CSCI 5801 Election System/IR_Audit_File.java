import java.io.*;
public class IR_Audit_File {
    private File auditFile;
    
    public IR_Audit_File(String filePath){
        this.auditFile = new File(filePath);
    }
    public void writeToAudit(String result){
        try{
            FileWriter writer = new FileWriter(auditFile, true);
            writer.write(result + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot write IR results to audit file.");
        }
        
    }
}
