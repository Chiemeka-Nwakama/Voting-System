import java.io.*;
public class CPL_Audit_File {
    private String audit;
    private File auditFile;
          /**
   * This constructor intializes the audit file as well as the String that things written to before outputAudit is called
   * @param void
   */
    public CPL_Audit_File(){
        auditFile = new File("audit");
        audit = "";

    }
          /**
   * This method will "write" to the audit file adding text to our audit String so that it is ready to be output to audit file at theend of the election
   * @param thing the thing we are writing to the audit file
   * @return void
   */
    public void writeToAudit(String thing){
        audit = audit + thing + "\n";
        System.out.println(thing);
    }

          /**
   * This method will out put what we have written to the audit string to the actual audit file using filewriter
   * @param void 
   * @return void
   */
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