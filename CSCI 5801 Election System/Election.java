import java.util.Scanner;
import java.io.*;
import java.io.File;

public class Election{
    public static void main(String[] args){
        String fileName;
        boolean valid = false;
        Scanner scanner = new Scanner(System.in); //intializes a scanner object
        File election;

        if(args.length > 1) {
            // command line, right now assumes file name is correct
            fileName = args[1];

        }else{            
            // prompt user
            System.out.println("Enter in the name of the election file. Enter 'Exit' to exit program");
            fileName = scanner.nextLine(); //obtains the file name from user
        }

        while(valid || !(fileName.equals("Exit"))){ // while provided filename is not valid or the user has not exited the loop, keep prompting them

            try {  
                election = new File(fileName);
                Scanner file_scan  = new Scanner(election);
                String type = file_scan.nextLine(); // scans in the first line of the election file to see what type of election it is                
                if (type.equals("CPL")){
                    CPL cpl  = new CPL(election);
                    cpl.run(election);
                    
                }else{
                    
                    IR ir = new IR(election);
                    ir.run(election);
                    
                }
                
                file_scan.close();
                valid = true;
                break;

                         
            }
                
            catch(IOException e){  //catches error that file cannot be open    
                valid = false;
                System.out.println("Error! invalid File. Try Again");
                 // while provided filename is not valid or the user has not exited the loop, keep prompting them
                System.out.println("Enter in the name of the election file. Enter 'Exit' to exit program ");
                fileName = scanner.nextLine(); //obtains the new file name from user  
            }
            
            
        } //while
        
    
    scanner.close();
    //election.close(); close file
    
    } //main
} //eof
