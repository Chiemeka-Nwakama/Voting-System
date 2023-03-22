import java.util.Scanner;
import java.io.*;
public class IR {
    private File inputFile;
    private int numCandidates;
    private IR_Candidate[] candidates;
    private int numBallots;
    private IR_Ballot[] ballots;
    private IR_Audit_File audit;

    public IR(File  file){
        inputFile = file;
        //TODO: Create audit file
        

    }
    
    void run(){
        int counter = 0;
        int curBallot = 0;
        String tempBallot[];
        int finalBallot[];
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (counter == 0){
            }else if (counter == 1){ //get numCandidates
                numCandidates = Integer.parseInt(data);
                candidates = new IR_Candidate[numCandidates];
            }else if (counter == 2){ //get candidates
                String tempCandidates[] = data.split(",");
                for (int a = 0; a < numCandidates; a++){
                    candidates[a] = new IR_Candidate(tempCandidates[a]);
                }
            }else if (counter == 3){ //get numBallots
                numBallots = Integer.parseInt(data);
                ballots = new IR_Ballot[numBallots];
                finalBallot = new int[numCandidates];
            }else{ //parse ballots
                tempBallot = data.split(",");
                for (int a = 0; a < numCandidates; a++){
                    finalBallot[a] = Integer.parseInt(tempBallot[a]);
                }
                ballots[curBallot] = new IR_Ballot(finalBallot); //add ballot to ballots array
                for (int a = 0; a < numCandidates; a++){ //add ballot to candidates ballot array
                    if (finalBallot[a] == 1){
                        candidates[a].addBallot(finalBallot); 
                    }
                }
                curBallot++;
            }
            counter++;
        }
    }


    public IR_Candidate[] getCandidates(){
        return candidates;
    }

    public int getNumCandidates(){
        return this.numCandidates;
    }

   
    public int getNumBallots(){
        return this.numBallots;
    }
    
    public void makeLoser(IR_Candidate makeLoser){

    }
    public int coinToss(){

    }
    public int poolselect(){

    }
    public void assignBallots(){

    }
    public void displayResults(){

    }

    public void countVote(IR_Ballot ballot){
        
    }

    public int getRanking(){
        
    }
   
    public void clearCandidates(IR_Candidate[] candidates){
        candidates.clear()
    }
    public void clearBallots(CPL_Ballot[] ballots){
        
    }

    }
}
