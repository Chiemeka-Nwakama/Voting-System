import java.util.Scanner;
import java.io.*;
public class Election{
    public static void main(String[] args){
        String fileName;
        boolean valid = false;
        Scanner scanner = new Scanner(System.in); //intializes a scanner object
        Scanner file_scan; // scanner object for files
        File election;

        if(args.length > 1) {
            // command line, right now assumes file name is correct
            fileName = args[1];

        }else{            
            // prompt user
            System.out.print("Enter in the name of the election file. Enter 'Exit' to exit program");
            fileName = scanner.nextLine(); //obtains the file name from user
        }
        
        while(!valid || fileName.equals("Exit")){ // while provided filename is not valid or the user has not exited the loop, keep prompting them
            try {  
                election = new File(fileName);
                file_scan  = new Scanner(election);
                valid = true;
                System.out.print("File name: ");
                System.out.println(fileName);

                String type = file_scan.nextLine(); // scans in the first line of the election file to see what type of election it is
                if (type.equals("CPL")){
                    CPL cpl  = new CPL(election);
                    cpl.run();
                    
                }else{
                    IR ir = new IR(election);
                    ir.run();
                }
                         
            }
                
            catch(IOException e){  //catches error that file cannot be open    
                valid = false;
                System.out.println("Error! invalid File. Try Again");
                 // while provided filename is not valid or the user has not exited the loop, keep prompting them
                System.out.print("Enter in the name of the election file. Enter 'Exit' to exit program ");
                fileName = scanner.nextLine(); //obtains the new file name from user  
            }

            
        } //while
        
    
    scanner.close();
    file_scan.close();
    //election.close(); close file
    
    } //main
} //eof

