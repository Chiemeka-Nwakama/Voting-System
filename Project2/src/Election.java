package src;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Election{
  
    public static void main(String[] args) throws FileNotFoundException{
            System.out.println("Please select all files you would like to use");
            String election_type;
            File[] files;
            String[] file_names;
    
            // File selection set up
            JFileChooser fileSelection = new JFileChooser();

            // Files only
            fileSelection.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // Specify file types
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV FILES", "csv");
            fileSelection.setFileFilter(filter);

            // Enable multiple selection
            fileSelection.setMultiSelectionEnabled(true);
            
            // Open users default directory
            int approved = fileSelection.showOpenDialog(null);

            // If files selected are approved based on filter and type
            if (approved == fileSelection.APPROVE_OPTION) {
                files = fileSelection.getSelectedFiles();
                file_names = new String[files.length];

                System.out.println("\nYou have selected the following files:");
                for (int i = 0; i < files.length; i++) {
                    System.out.println(files[i].getName());
                    file_names[i] = files[i].getName();
    
                }

                System.out.println();
                Scanner scan = new Scanner(files[0]);
                election_type = scan.nextLine();
                int invalid = 0;

                // Ensure all files are of the same election type
                for (int i = 0; i < file_names.length; i++) {
                    Scanner sc = new Scanner(files[i]);
                    String compare = sc.nextLine();
                    if (!(compare.equals(election_type))) {
                        invalid = 1;
                    }
                    sc.close();
                }

                if (invalid == 0) {
        
                    if (election_type.equals("CPL")){
                        CPL cpl  = new CPL(files);
                        cpl.run();
                        
                    }else if (election_type.equals("IR")){
                        IR ir = new IR(files);
                        ir.run();
                        
                    } else if (election_type.equals("PO")) {
                        PO po = new PO(files);
                        po.run();
                    }
                    

                } else {
                    System.out.println("The files provided have mismatched election types.");
                }

                scan.close();
             
        }

    }

}

   
    


