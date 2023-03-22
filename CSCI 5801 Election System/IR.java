import java.util.Scanner;
import java.io.*;
public class IR {
    private File inputFile;
    private int numCandidates;
    private IR_Candidate[] candidates;
    private int numBallots;
    private IR_Ballot[] ballots;
    private IR_Audit_File audit;
    private IR_Candidate[] ranking;

    public IR(File  file){
        inputFile = file;
        //TODO: Create audit file
        audit = new IR_Audit_File("IR_auditFile.txt");
        

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
                    candidates[a] = new IR_Candidate(tempCandidates[a], a);
                }
            }else if (counter == 3){ //get numBallots
                numBallots = Integer.parseInt(data);
                ballots = new IR_Ballot[numBallots];
                for (int a = 0; a < numCandidates; a++){
                    candidates[a].setCandidateBallots(numBallots);
                }
            }else{ //parse ballots
                finalBallot = new int[numCandidates];
                tempBallot = data.split(",");
                for (int a = 0; a < numCandidates; a++){
                    finalBallot[a] = Integer.parseInt(tempBallot[a]);
                }
                ballots[curBallot] = new IR_Ballot(finalBallot); //add ballot to ballots array
                for (int a = 0; a < numCandidates; a++){ //add ballot to candidates ballot array
                    if (finalBallot[a] == 1){
                        candidates[a].addBallot(new IR_Ballot(finalBallot)); 
                    }
                }
                curBallot++;
            }
            counter++;
        }
        
    }


    public IR_Candidate[] getCandidates(){
        return this.candidates;
    }

    public int getNumCandidates(){
        return this.numCandidates;
    }

   
    public int getNumBallots(){
        return this.numBallots;
    }
    
    public void makeLoser(IR_Candidate loser){
        loser.setStatus();
        IR_Ballot curBallot;
        IR_Ballot[] loserBallots = new IR_Ballot[loser.getVotes()];
        loserBallots = loser.getBallots();
        for (int a = 0; a < loser.getVotes(); a++){ //reassign ballots
            curBallot = loserBallots[a];
            curBallot.updateCurrentVote();
            candidates[curBallot.getCurrentVote()].addBallot(curBallot); //add ballot to new candidate's array
        }
        loser.removeVotes(); //set loser's vote count to 0
    }

    public int coinToss(){

    }

    public int poolselect(){

    }

    public void displayResults(){
        System.out.println("-----Instant Runoff Election Results-----");
        System.out.print("***   The winner is: ");
        //getElectionStatus();
        System.out.println("--Information on the Election--");
        System.out.println("Number of Candidates: " + numCandidates);
        System.out.println("Number of Ballots cast: " + numBallots);
        System.out.println("Candidates name and number of votes received:");
        for(int i = 0; i < numCandidates; i++){
            System.out.println("  " + candidates[i] + "  " + ballots[i].getCurrentVote());
        }

        String result = "-----Instant Runoff Election Results-----\n" +
                        "***The winner is: \n" + 
                        "--Information on the Election--\n" +
                        "Number of Candidates: " + numCandidates + "\n" +
                        "Number of Ballots cast: " + numBallots + "\n" +
                        "Candidates name and number of votes received:\n";
        for(int i = 0; i < numCandidates; i++){
            result += "  " + candidates[i] + "  " + ballots[i].getCurrentVote() + "\n";
        }
        audit.writeToAudit(result);

    }

    public void countVote(IR_Ballot ballot){
        
    }

    public IR_Candidate[] getElectionStatus(){ //rank all candidates highest to lowest, find winner if there is one
        ranking = new IR_Candidate[numCandidates];
        for (int a = 0; a < numCandidates; a++){
            
        }
        return ranking;
    }
   
    public void clearCandidates(IR_Candidate[] candidates){
        for (int i = 0; i < candidates.length; i++){
            candidates[i] = null;
        }
    }
    public void clearBallots(CPL_Ballot[] ballots){
        for(int i = 0; i < ballots.length; i++){
            ballots[i] = null;
        }
    }
}



