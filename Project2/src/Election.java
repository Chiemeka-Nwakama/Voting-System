package src;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Election{
  
    public static void main(String[] args) throws FileNotFoundException{
        String fileNames;
        
        Scanner scanner = new Scanner(System.in); //intializes a scanner object
        
        String election_type;
        File[] files;
        String[] names;

        if(args.length > 0) {
            // command line, right now assumes file name is correct
           
            files = new File[args.length];
  
            for(int i = 0; i < args.length; i++){
                files[i] = new File(args[i]);
            

            }

        }else{            
            // prompt user
            System.out.println("Enter in the name of the election file(s). (ex: file1.csv file2.csv) (ex: file1.csv)  Enter 'Exit' to exit program");
            fileNames = scanner.nextLine(); //obtains the file name from user
            names = fileNames.split(" ");
            files = new File[names.length];
            for(int i = 0; i < names.length; i++){
                files[i] = new File(names[i]);

            }
        }

        //if last thing types .equals exit then program will terminate and not advance to an election
     
        for(int i = 0; i < files.length && !(files[files.length-1].toString().equals("Exit")); i++){ // while provided filename is not valid or the user has not exited the loop, keep prompting them
            try {  
              
                Scanner file_scan  = new Scanner(files[i]); // validates file exist
               
                file_scan.close();
        
                         
            }
                
            catch(IOException e){  //catches error that file cannot be open    
                i = -1; // resets i
                System.out.println("Error! Atleast one file was not a valid file" );
             // prompt user
            System.out.println("Enter in the name of the election file(s). (ex: file1.csv file2.csv) (ex: file1.csv)  Enter 'Exit' to exit program");
            fileNames = scanner.nextLine(); //obtains the file name from user
            names = fileNames.split(" ");
            files = new File[names.length];
            for(int j = 0; j < names.length; j++){
                files[j] = new File(names[j]);

            }
        }
    }
         
         if(!files[files.length-1].toString().equals("Exit"))  {
         //for file validator
         
        election_type = "";
        try {  
        Scanner file_scan  = new Scanner(files[0]); //obtains the file name from user


        election_type = file_scan.nextLine(); // scans in the first line of the election file to see what type of election it is      
        file_scan.close();
        }
        catch(IOException e){

        }          
        if (election_type.equals("CPL")){
            CPL cpl  = new CPL(files);
            cpl.run();
            
        }else if (election_type.equals("IR")){
            IR ir = new IR(files);
            //
            ir.run();
            
            
        }
    
        // else{
        //     PO po = new PO(files);
        //     po.run();
        // }
        }

   
    
    scanner.close();
    //election.close(); close file
    
    } //main
} //eof
