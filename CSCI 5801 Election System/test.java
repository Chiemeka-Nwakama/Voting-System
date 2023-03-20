import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class test {
    public static void main(String args[]) throws FileNotFoundException{
        String input;
        Scanner scan = new Scanner(System.in);
        System.out.println("enter file name");
        input = scan.nextLine();
        File file = new File(input);
        Scanner sc = new Scanner(file);
        String[] partyNames = sc.nextLine().split(","); // 3rd line -- gets names of the party
        for(int i = 0; i < partyNames.length; i++) {
            partyNames[i] = partyNames[i].trim();
            System.out.println(partyNames[i]);
        }



        sc.close();
        scan.close();
 
    }
    
}
