import java.io.*;
public class CPL_Audit_File {
    private String audit;
    private File auditFile;
    
    public CPL_Audit_File(){

        auditFile = new File("audit");

    }
    public void writeToAudit(String thing){
        audit = audit + thing + "/n";
        System.out.println(thing);
    }

    public void outputAudit(){
        System.out.print(auditFile);
        try {
            FileWriter myWriter = new FileWriter(auditFile);
            myWriter.write(audit);
            myWriter.close();
            
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
    }

}
}