import java.io.*;
import java.util.Random;
public class IR_Make_Test_File {
    private File testFile;
    private int numBallots;
    private int numCandidates;
    private String candidateNames;
    private FileWriter testWriter; 
    private String toWrite;

    public IR_Make_Test_File(int numBallots, int numCandidates){
        this.numBallots = numBallots;
        this.numCandidates = numCandidates;
        toWrite = "";
        testFile = new File("IR_test");
        try {
            testWriter = new FileWriter(testFile);
        } catch (IOException e) {
            System.out.println("Cannot write IR results to audit file.");
            e.printStackTrace();
        }
        
    }

    public void makeTestFile(){
        int vote;
        int counter;
        Random random = new Random();
        for (int a = 0; a < numBallots; a++){
            int[] rankings = new int[numCandidates];
            counter = 1;
            vote = random.nextInt(numCandidates);
            for (int b = 0; b < numCandidates; b++){
                while (rankings[vote] != 0){
                    vote = random.nextInt(numCandidates);
                }
                rankings[vote] = counter;
                counter++;
            }
            toWrite += rankings[0];
            for (int c = 1; c < numCandidates; c++){
                toWrite += "," + rankings[c];
            }
            toWrite += "\n";
        }
    }

    public void outputTestFile(){
        System.out.println(testFile);
        try {
            testWriter.write(toWrite);
            testWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
