import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class IR {
    private File inputFile;
    private int numCandidates;
    private IR_Candidate[] candidates;
    private int numBallots;
    private IR_Ballot[] ballots;
    private IR_Audit_File audit;
    private IR_Candidate[] ranking;
    private String result; //use to write each step into the audit file
    private final int randomConstant = 1000;
    private int remainingCandidates;
    private IR_Candidate winner;

    /** 
    *@brief This constructor initialize the audit file and taking input file
    *@param file The input file for IR election
    **/
    /** 
    *@brief This constructor initialize the audit file and taking input file
    *@param file The input file for IR election
    **/
    public IR(File  file){
        inputFile = file;
        audit = new IR_Audit_File();
        audit.writeToAudit("Instant Runoff Voting Election\n");
    }
    
    /** 
    *@brief The method run the entire election from start to finish using various methods
    **/
    /** 
    *@brief The method run the entire election from start to finish using various methods
    **/
    void run(){
        //write to audit file
        populateData(); //add all ballots to ballot array and assign IDs
        assignBallots(); //assign ballot IDs to candidate's ballot array
        remainingCandidates = numCandidates;
        //**need to defind this function?? **/
        while (!getElectionStatus()){ //rank all candidates and check for winner
            int numTied = checkForWinnerTie();
            if (numTied == remainingCandidates){
                if (numTied == 2){
                    winner = ranking[coinToss() - 1]; //declare a winner and end process
                    break;
                }else{
                    makeLoser(ranking[poolSelect(numTied) - 1]); //redistribute losing candidate's votes
                }
            }else{
                numTied = checkForLoserTie();
                if (numTied == 2){
                    makeLoser(ranking[remainingCandidates - coinToss()]);
                }else if (numTied > 2){
                    makeLoser(ranking[remainingCandidates - poolSelect(numTied)]);
                }else{
                    makeLoser(ranking[remainingCandidates - 1]); //eliminate last place candidate and redistribute their votes
                }
            }
        }
        if (winner == null && ranking[0].isWinner()){
            winner = ranking[0];
        }
    }

    /** 
    *@brief This method will scan the given ballot file and read/store the informations to the election
    **/
    /** 
    *@brief This method will scan the given ballot file and read/store the informations to the election
    **/
    public void populateData(){ 
        int counter = 0;
        int curBallot = 0;
        String tempBallot[];
        int finalBallot[];
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (counter == 0){
            }else if (counter == 1){                  //get numCandidates
                numCandidates = Integer.parseInt(data);
                candidates = new IR_Candidate[numCandidates];
            }else if (counter == 2){                    //get candidates
                String tempCandidates[] = data.split(",");
                for (int a = 0; a < numCandidates; a++){
                    candidates[a] = new IR_Candidate(tempCandidates[a], a);
                }
            }else if (counter == 3){                        //get numBallots
                numBallots = Integer.parseInt(data);
                ballots = new IR_Ballot[numBallots];
                for (int a = 0; a < numCandidates; a++){
                    candidates[a].setCandidateBallots(numBallots);
                }
            }else{                                   //parse ballots
                finalBallot = new int[numCandidates];
                tempBallot = data.split(",");
                for (int a = 0; a < numCandidates; a++){
                    finalBallot[a] = Integer.parseInt(tempBallot[a]);
                }
                ballots[curBallot] = new IR_Ballot(finalBallot, curBallot); //add ballot to ballots array
                audit.writeBallot(ballots[curBallot]); //write ballot to audit file 
                curBallot++;
            }
            counter++;
        }
        scanner.close();
    }

    /** 
    *@brief Assign the votes in each ballot for the candidates
    **/
    /** 
    *@brief Assign the votes in each ballot for the candidates
    **/
    public void assignBallots(){
        int currentCandidate = -1;
        for (int a = 0; a < numBallots; a++){
            currentCandidate = ballots[a].getCurrentVote();
            candidates[currentCandidate].addBallot(a);
        }
        for (int a = 0; a < numCandidates; a++){
            audit.writeCandidateBallots(candidates[a]); //write all candidates and their ballots to the audit file
        }
    }

    /** 
    *@brief Get the array of the Candidates
    *@return The IR_Candidate array
    **/    /** 
    *@brief Get the array of the Candidates
    *@return The IR_Candidate array
    **/
    public IR_Candidate[] getCandidates(){
        return this.candidates;
    }

    /** 
    *@brief Get the number of candidates in the election
    *@return The number of canidates
    **/
    /** 
    *@brief Get the number of candidates in the election
    *@return The number of canidates
    **/
    public int getNumCandidates(){
        return this.numCandidates;
    }

    /** 
    *@brief Get the number of ballots in the election
    *@return The total ballots in the election
     /** 
    *@brief Get the number of ballots in the election
    *@return The total ballots in the election
    **/**/
    public int getNumBallots(){
        return this.numBallots;
    }
    
    /** 
    *@brief The method is to make loser candidate with lowest votes
    *@param loser IR_candidate of who will be the loser in the election
    **/
    /** 
    *@brief The method is to make loser candidate with lowest votes
    *@param loser IR_candidate of who will be the loser in the election
    **/
    public void makeLoser(IR_Candidate loser){
        loser.setStatus();
        int curBallot;
        int[] loserBallots = new int[loser.getVotes()];
        loserBallots = loser.getBallots();
        for (int a = 0; a < loser.getVotes(); a++){ //reassign ballots
            curBallot = loserBallots[a];
            ballots[curBallot].updateCurrentVote();
            candidates[ballots[curBallot].getCurrentVote()].addBallot(curBallot); //add ballot to new candidate's array
        }
        loser.removeVotes(); //set loser's vote count to 0
        remainingCandidates--;
    }

    /** 
    *@brief The method check for how many cadidates tie in the election for winner
    *@return The number of tie candidates in the election
    **/
    /** 
    *@brief The method check for how many cadidates tie in the election for winner
    *@return The number of tie candidates in the election
    **/
    public int checkForWinnerTie(){
        int tieCounter = 1; //set tieCounter to 1 if no candidates are tied
        for (int a = 1; a < numCandidates; a++){ //check if there's a tie, start with second candidate
            if (ranking[0].getVotes() == ranking[a].getVotes()){
                tieCounter++; //increament 
            }else{
                break;
            }
        }
        return tieCounter;
    }

    /** 
    *@brief The method check for how many candidates ties in votes 
    *@return The number of loser candidates
    **/
    /** 
    *@brief The method check for how many candidates ties in votes 
    *@return The number of loser candidates
    **/
    public int checkForLoserTie(){
        int tieCounter = 1; //set tieCounter to 1 if no candidates are tied
        for (int a = numCandidates - 2; a >= 0; a--){ //check if there's a tie 
            if (ranking[numCandidates - 1].getVotes() == ranking[a].getVotes()){
                tieCounter++;
            }else{
                break;
            }
        }
        return tieCounter;
    }

    /** 
    *@brief Determine the winning candidate in a tie by coin toss method
    *@return A randomly generate number 0 or 1, 0 for heads, and 1 for tails
    **/
    /** 
    *@brief Determine the winning candidate in a tie by coin toss method
    *@return A randomly generate number 0 or 1, 0 for heads, and 1 for tails
    **/
    public int coinToss(){
        Random randomNum = new Random();
        int result = 0;
        for(int i = 0; i < randomConstant; i++){
            result = randomNum.nextInt(2);
        }
        return result;      

    }

    /** 
    *@brief Tie situation for more than 2 candidates
    Assigned random number to all candidates, and generate a random selection 
    *@return The number of the winning candidate
    **/
    /** 
    *@brief Tie situation for more than 2 candidates
    Assigned random number to all candidates, and generate a random selection 
    *@return The number of the winning candidate
    **/
    public int poolSelect(int numTied){
        Random randomNum = new Random();
        int result = 0;
        for(int i = 0; i < randomConstant; i++){
            result = randomNum.nextInt((numTied - 1) + 1) + 1;
        }
        
        return result;
    }

    /** 
    *@brief Display the result of the lection
    *Also, write the result into the audit file
    **/
    /** 
    *@brief Display the result of the lection
    *Also, write the result into the audit file
    **/
    public void displayResults(){
        System.out.println("-----Instant Runoff Election Results-----");
        System.out.print("***   The winner is: ");
        System.out.println("--Information on the Election--");
        System.out.println("Number of Candidates: " + numCandidates);
        System.out.println("Number of Ballots cast: " + numBallots);
        System.out.println("Candidates name and number of votes received:");
        for(int i = 0; i < numCandidates; i++){
            System.out.println("  " + candidates[i] + "  " + ballots[i].getCurrentVote());
        }

        result = "-----Instant Runoff Election Results-----\n" +
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

    /** 
    *@brief Set the election status for all the candidates from highest to lowest
    *@return True for candidate with highest ranking is sorted in right position
    **/
    /** 
    *@brief Set the election status for all the candidates from highest to lowest
    *@return True for candidate with highest ranking is sorted in right position
    **/
    public boolean setElectionStatus(){ //rank all candidates highest to lowest, find winner if there is one
        IR_Candidate[] curRanking = new IR_Candidate[numCandidates];
        curRanking = candidates;
        for (int i = 0; i < numCandidates; i++){ //insertion sort modified from geeksforgeeks
            IR_Candidate key = ranking[i];
            int j = i - 1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && (ranking[j].getVotes()) > key.getVotes()) {
                ranking[j + 1] = ranking[j];
                j = j - 1;
            }
            ranking[j + 1] = key;
        }
        if (curRanking[0].isWinner()){
            return true;
        }else{
            return false;
        }
    }
   
    /** 
    *@brief Clean the IR_Candidate array
    *@param candidates The array that stores all candidates in the election
    **/
    public void clearCandidates(IR_Candidate[] candidates){
        Arrays.fill(candidates, null);
    }

    /** 
    *@brief Clean the IR_Ballot array
    *@param ballots The array that stores all ballots in the election
    **/
    public void clearBallots(IR_Ballot[] ballots){
        Arrays.fill(ballots, null);
    }
}



