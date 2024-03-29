package src;
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
    private Scanner scanner;
    private int[][] tableData;
    private int[] exhaustedPile;
    private int exhaustedIndex;

    /** 
    *@brief This constructor initialize the audit file and taking input file
    *@param file The input file for IR election
    **/
    public IR(File  file) throws FileNotFoundException{
        inputFile = file;
        audit = new IR_Audit_File();
        audit.writeToAudit("INSTANT RUNOFF (IR) VOTING ELECTION");
        audit.writeToAudit("AUDIT FILE INITIALIZED");
        audit.writeToAudit("POPULATING DATA FROM IR ELECTION FILE...\n");
        audit.writeToAudit("Ballots:");
        scanner = new Scanner(inputFile);
        initializeData();
        populateData(); //add all ballots to ballot array and assign IDs
        audit.writeToAudit("\nInitial Candidate Votes:");
        assignBallots(); //assign ballot IDs to candidate's ballot array
        scanner.close();
    }
    
    
    /** 
    *@brief The method run the entire election from start to finish using various methods
    **/
    public void run(){
        int rounds = 1;
        //write to audit file
        while (!setElectionStatus() || remainingCandidates != 1){ //rank all candidates and check for winner
            int numTied = checkForWinnerTie();
            if (numTied == remainingCandidates){
                if (numTied == 2){
                    winner = ranking[coinToss()]; //declare a winner and end process
                    break;
                }else{
                    makeLoser(ranking[poolSelect(numTied)]); //redistribute losing candidate's votes
                }
            numTied = checkForLoserTie();
            }else if (numTied < remainingCandidates){
                if (numTied == 2){
                    makeLoser(ranking[(remainingCandidates - 1) - coinToss()]);
                }else if (numTied > 2){
                    makeLoser(ranking[(remainingCandidates - 1) - poolSelect(numTied)]);
                }else{
                    makeLoser(ranking[remainingCandidates - 1]); //eliminate last place candidate and redistribute their votes
                }
            }
            audit.writeToAudit("\nRound " + rounds + ":");
            updateTable(rounds);
            rounds++;
        }
        if (winner == null){
            winner = ranking[0];
        }

        displayResults();
        printTable(rounds - 1);
        audit.writeToAudit("DATA SUCCESSFULLY POPULATED.\n");
        audit.outputAudit();
    }

    
    /** 
    *@brief This method will scan the given ballot file and read/store the informations to the election
    **/
    public void initializeData(){       
        scanner.nextLine();   //skip 1st line
        numCandidates = scanner.nextInt();
        remainingCandidates = numCandidates; //initialize remainingCanidates
        tableData = new int[numCandidates + 1][numCandidates]; //initiallize table
        scanner.nextLine();  //get numCandidates
        String[] candidateNames = scanner.nextLine().split(", ");  //get candidates
        candidates = new IR_Candidate[numCandidates]; 
        ranking = new IR_Candidate[numCandidates]; 
        for (int a = 0; a < numCandidates; a++){
            candidates[a] = new IR_Candidate(candidateNames[a], a); //add candidates to candidate array
            ranking[a] = candidates[a];
        }                      
        numBallots = scanner.nextInt(); //get numBallots
        scanner.nextLine(); //move to next line
        ballots = new IR_Ballot[numBallots];   //initialize ballot array
        exhaustedPile = new int[numBallots]; //initialize exhausted pile
        exhaustedIndex = 0;
        for (int a = 0; a < numCandidates; a++){
            candidates[a].setCandidateBallots(numBallots);   //initialize candidate ballot arrays
        }     
    } 

    /**
    @brief This method will read in the ballots data and store the votes with corresponding candidate 
    **/
    public void populateData(){
        int curBallot = 0;
        String[] tempBallot;
        int[] finalBallot;
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();                                  //parse ballots
            finalBallot = new int[numCandidates];
            tempBallot = data.split(",");
            for (int a = 0; a < tempBallot.length; a++){
                if (!tempBallot[a].equals("")){
                    finalBallot[a] = Integer.parseInt(tempBallot[a]); //add vote to vote array
                }
            }
            ballots[curBallot] = new IR_Ballot(finalBallot, curBallot); //add ballot to ballots array
            audit.writeBallot(ballots[curBallot]); //write ballot to audit file 
            curBallot++;
            }
    }

   
    /** 
    *@brief Assign the votes in each ballot for the candidates
    **/
    public void assignBallots(){
        int currentCandidate = -1;
        for (int a = 0; a < numBallots; a++){
            currentCandidate = ballots[a].getCurrentVote();
            if (currentCandidate != -1){
                candidates[currentCandidate].addBallot(a);
            }    
        }
        //for (int a = 0; a < numCandidates; a++){
           // audit.writeCandidateBallots(candidates[a]); //write all candidates and their ballots to the audit file
        //}
    }

    /** 
    *@brief Get the array of the Candidates
    *@return The IR_Candidate array
    **/
    public IR_Candidate[] getCandidates(){
        return candidates;
    }

    /** 
    *@brief Get the number of candidates in the election
    *@return The number of canidates
    **/
    public int getNumCandidates(){
        return numCandidates;
    }

    /** 
    *@brief Get the number of ballots in the election
    *@return The total ballots in the election
    **/
    public int getNumBallots(){
        return numBallots;
    }

    /**
     * @brief Get ballot array
     * @return ballot array
     */
    public IR_Ballot[] getBallots(){return ballots;}

    /**
     * @brief Get candidate 
     * @return
     */
    public IR_Candidate[] getRankings(){return ranking;}
    
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
            while (ballots[curBallot].getCurrentVote() != -1 && !candidates[ballots[curBallot].getCurrentVote()].getStatus()){ 
                ballots[curBallot].updateCurrentVote();
            }
            if (ballots[curBallot].getCurrentVote() != -1){
                candidates[ballots[curBallot].getCurrentVote()].addBallot(curBallot); //add ballot to new candidate's array
            }else{
                exhaustedPile[exhaustedIndex] = ballots[curBallot].getBallotID();
                exhaustedIndex++;
            }
        }
        if (ranking[remainingCandidates - 1].getCandidateID() != loser.getCandidateID()){
            IR_Candidate tempCandidate = ranking[remainingCandidates - 1];
            ranking[remainingCandidates - 1] = loser;
            ranking[loser.getRank()] = tempCandidate;
        }
        loser.removeVotes(); //set loser's vote count to 0
        remainingCandidates--;
    }

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
    public int checkForLoserTie(){
        int tieCounter = 1; //set tieCounter to 1 if no candidates are tied
        for (int a = remainingCandidates - 2; a >= 0; a--){ //check if there's a tie 
            if (ranking[remainingCandidates - 1].getVotes() == ranking[a].getVotes()){
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
    public int poolSelect(int numTied){
        Random randomNum = new Random();
        int result = 0;
        for(int i = 0; i < randomConstant; i++){
            result = randomNum.nextInt(numTied);
        }
        return result;
    }

    /** 
    *@brief Display the result of the lection
    *Also, write the result into the audit file
    **/
    public void displayResults(){
        System.out.println("-----Instant Runoff Election Results-----");
        System.out.println("Information on the Election");       
        System.out.println("Number of Candidates: " + numCandidates);
        System.out.println("Number of Ballots cast: " + numBallots);
        System.out.println("***   The winner is: " + winner.getName() + "   ***");
        

        result = "-----Instant Runoff Election Results-----\n" +
                        "--Information on the Election--\n" +
                        "Number of Candidates: " + numCandidates + "\n" +
                        "Number of Ballots cast: " + numBallots + "\n" +
                        "***The winner is: " + winner.getName() + "***\n";                       
        audit.writeToAudit(result);
    }

    /** 
    *@brief Set the election status for all the candidates from highest to lowest
    *@return True for candidate with highest ranking is sorted in right position
    **/
    public boolean setElectionStatus(){ //rank all candidates highest to lowest, find winner if there is one
        for (int i = 0; i < numCandidates; i++){ //insertion sort modified from geeksforgeeks
            IR_Candidate key = ranking[i];
            int j = i - 1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position behind
               of their current position */
            while (j >= 0 && (ranking[j].getVotes()) < key.getVotes()) {
                ranking[j + 1] = ranking[j];
                j = j - 1;
            }
            ranking[j + 1] = key;
        }
        for (int a = 0; a < numCandidates; a++){
            ranking[a].setRank(a);
            audit.writeCandidateBallots(ranking[a]);
        }
        if (ranking[0].isWinner()){
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

    public void updateTable(int round){
        for (int a = 0; a < numCandidates; a++){
            tableData[a][round] = candidates[a].getVotes();
        }
        tableData[numCandidates][round] = exhaustedIndex;
    }

    public void printTable(int round){
        System.out.print("-------------------"); //format top of table
        for (int a = 0; a < round; a ++){
            System.out.print("----------------");
        }
        System.out.print("\n");

        System.out.format("|   Candidates    |"); //format rounds header
        for (int a = 1; a <= round; a++){
            System.out.format("    Round %d    |", a);
        }
        System.out.print("\n");

        System.out.format("|Candidates(Party)|"); //format vote count header
        for (int a = 1; a <= round; a++){
            System.out.format(" Votes | Delta |");
        }
        System.out.print("\n");

        for (int a = 0; a < numCandidates; a++){  //format vote counts and deltas
            System.out.format("| %16s|", ranking[a].getName());
            for (int b = 1; b <= round; b++){
                System.out.format(" %6d| %+6d|", tableData[ranking[a].getCandidateID()][b], 
                (tableData[ranking[a].getCandidateID()][b] - tableData[ranking[a].getCandidateID()][b -1]));
            }
            System.out.print("\n");
        }

        System.out.format("|Exhausted Pile|");
        for (int a = 1; a < round; a++){
            System.out.format(" %6d| %+6d|", tableData[numCandidates][a], (tableData[numCandidates][a] - tableData[numCandidates][a - 1]));
        }
        System.out.print("\n");

        System.out.print("-------------------");
        for (int a = 0; a < round; a ++){
            System.out.print("----------------");
        }
        System.out.print("\n");
    }
}



