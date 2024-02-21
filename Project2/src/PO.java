package src;
import java.util.Scanner;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class PO {
    private int numCandidates;
    private int numBallots;
    private PO_Candidate[] candidates;
    private ArrayList<PO_Ballot> ballots;
    private File[] files;
    private final int randomConstant = 1000;
    private PO_Candidate winner;
    public Scanner sc;
    

  /**
   * This constructor takes in the election file and kicks off the population of data into data types by calling various methods within
   * @param files
   */

   public PO(File[] files) throws FileNotFoundException{
    this.files = files; // initilize file list
    this.ballots = new ArrayList<>(); // initialize ballot arraylist
  
    sc = new Scanner(files[0]);
    sc.nextLine(); // PO line
    
    populateCandidates(files[0], sc);
   
    sc.close();

    for (int i = 0; i < files.length; i++) {
        sc = new Scanner(files[i]);

        // skip header
        for (int j = 0; j < 3; j++) {
           
            sc.nextLine();
        }
       

        populateBallots(files[i], sc);

        sc.close();

    }
    
   
}

   /**
   * This method returns the path names if the files in the format "filepath1 filepath2". This metod is to test multiple files feature
   * @return s String containing pathnames of all files being brought in
   */

   public String getFiles(){
    String s = files[0].toString();
    for(int i = 1; i < files.length; i++){
    s = s + " " + files[i].toString();
    }
    return s;
}
/**
      /**
   * This method runs the entire election from start to finish using various methods along the way writes to the audit file
   * @param file
   * @return void
   */
    public void run(){
       
        assignBallots(); // distibutes Ballots to the parties associated with the parties voted for
       
        selectWinner(); // Selects winner
     
        displayResults(); //displays results since election has been completed

    }

      
     /**
   * This method returns the number of candidates in election
   * @param void 
   * @return A number of candidates
   */

    public int getNumCandidates(){
        return numCandidates;

    }

        /**
   * This method returns the total number of votes/ballots in election
   * @param void 
   * @return A number of seats
   */

    public int getTotalVotes(){
        return numBallots;

    }




 
  /**
   * This method returns the ballots in election
   * @param void 
   * @return ballots
   */

   public ArrayList<PO_Ballot> getBallots(){
    return ballots;

}

        /**
   * This method returns the number of ballots in election
   * @param void 
   * @return A number of ballots
   */

    public int getNumBallots(){
        return numBallots;

        
    }

          /**
   * This method returns the winner of the po election
   * @param void 
   * @return A candidate that won
   */

   public PO_Candidate getWinner(){
    return winner;

}


          /**
   * This method returns the candidates in the election
   * @param void 
   * @return an array of canddiates
   */

   public PO_Candidate[] getCandidates(){
    return candidates;

}



    

    /**
   * This method Picks a winner based on the candidate that has the highest popularity vote. Also handles ties
   * @param void
   * @return void
   */

    
    public void selectWinner(){
    
        PO_Candidate temp[] = candidates.clone(); // candidates list since they will be reordered by their remainders in descending order later
        
         sortCandidates(temp);
         int dups = duplicates(0, temp); // num duplicates
         if(dups == 0){
            winner = temp[0]; // gets winner by checking who has the most votes

         }
         else if(dups == 1){
            
            int coin = coinToss();
          

            if(coin == 0) {
                winner = temp[0]; //first candidate is picked
                                
            }else{
                
                winner = temp[1]; // 2nd canddiates that tied is picked
            }

        }else{
            
            winner = temp[poolselect(dups)]; // pool selects the winner
 
            
        }

        
    }
      
   

    
   
   
    /**
   * This method checks for how Candidates have duplicate amount of votes (based on candidates with the most votes)
   * @param index the index of the candidate that we are checking for duplicate remainder votes
   * @return number of duplicates
   */

    public int duplicates(int index, PO_Candidate[] candidates){
        int numDups = 0;

        for(int i = index; i < numCandidates-1; i++) {
            if(candidates[i].getVotes() == candidates[i+1].getVotes()) {
                numDups++;
            }else{
                break;
            }
        }
    
        return numDups;

    }


    

    /**
   * This method assigns ballots to Parties based on who they voted adding them to vote count of each party as well as giving the ballots to each parties candidates list
   * @param void
   * @return void
   */


    public void assignBallots(){

        
        ballots.forEach((b) -> {
            int candidateVote = b.getCandidateVote();
            candidates[candidateVote].addVote();
            
        });


    }

/**
   * This method is called in the CPL constructor populating and intializing the Candidate list in each party
   * @param file the election file 
   * @param sc the sc already initalized in the CPL constructor
   * @return void
   */


public void populateCandidates(File file, Scanner sc){ 

    
    numCandidates = sc.nextInt();

    
    candidates = new PO_Candidate[numCandidates];
    sc.nextLine(); //goes to next line
    String[] candidate_names =  sc.nextLine().split("]");

    //first candidates parse
    String candidate_and_party = candidate_names[0].substring(1); // removes [braket in the front]
    String[] candidate_info = candidate_and_party.split(","); //splits up the candidate name and party up by ","
    candidates[0] = new PO_Candidate(candidate_info[0], candidate_info[1].substring(1)); //makes new candidate with name and party
    
    for(int i = 1; i < numCandidates; i++){ //reads candidates until all candidates are populated
         candidate_and_party = candidate_names[i].substring(3); // removes [braket in the front]
         candidate_info = candidate_and_party.split(","); //splits up the candidate name and party up by ","
       
         candidates[i] = new PO_Candidate(candidate_info[0], candidate_info[1].substring(1)); //makes new candidate with name and party

     
        }
       
      
          
      

}
    /**
   * This method is called in the CPL constructor populating and intializing the CPL Ballot objects along with reading in the total number of seats to be given
   * @param file the election file 
   * @param sc the sc already initalized in the CPL constructor
   * @return void
   */

public void populateBallots(File file, Scanner sc) {     
    
    int fileBallots =  sc.nextInt(); // reads in the total number of seats avaliable, assumes same for each file

    int id_num = numBallots;    
    numBallots += fileBallots; 
    sc.nextLine();

    for (int i = 0; i < fileBallots; i++) {
        String ballot = sc.nextLine();
        int partyVote = ballot.indexOf("1"); // gets the pos of the vote
       
        PO_Ballot individual_ballot = new PO_Ballot(partyVote, numCandidates, (id_num+i));
        ballots.add(individual_ballot);
    }

}
     /**
   * This method cointToss() will determine which candidate wins in the two way tie
   * @param void 
   * @return A a randomly generate number 0 or 1, 0 for heads, 1 for tails
   */
    public int coinToss(){
        Random randomNum = new Random();
        int result = 0;
        for(int i = 0; i < randomConstant; i++ ){//flips a coin 1000 time prior to gaurantee true randomness opposed to pseudo

           result =  randomNum.nextInt(2);
    }
    result =  randomNum.nextInt(2);
    return result;
}


     /**
   * This method pools parties together assigns a random number, and see who's rand number is closest to another generated random number.
   * @param ties how many parties tied
   * @return The number of the indivdaul in the pooled selection that was the closest to the random number (the winner)
   */

/* SUMMARY: 
candidateTemp stores the duplicate parties only
assignedNumbers should be the same length and stores the assigned random numbers
Two while loops. While there are no more seats to give, for the first round we find a winner based on the random numbers, give it a seat, and remove it. The next round we repeat with the remaining parties until there are no more seats
*/

    public int poolselect(int ties){
        ArrayList<PO_Candidate> candidateTemp = new ArrayList<PO_Candidate>(); // Create an ArrayList object
        ArrayList<Integer> assignedNumbers = new ArrayList<Integer>();
        Random randomNum = new Random(); //create random object where we will produce our random numbers
        boolean winner = false;
        int index = 0;

        // Copy over parties to arraylist
        for(int i = 0; i < ties + 1; i++) {
            candidateTemp.add(candidates[i]);
        }


      
         

           
            while(winner == false) {
                winner = true;

                for(int i = 0; i < randomConstant; i++){ // generates 1000 times to make the random generator truly random
                    randomNum.nextInt(randomConstant);
                }

                // chosen random number
                int genNum = randomNum.nextInt(randomConstant);
            

                // assign the random numbers to the candidate
                for(int i = 0; i < ties + 1; i++){
                    assignedNumbers.add(randomNum.nextInt(randomConstant)); //assigns a random number to each candidate

                }
        
                
                int leastDifference = Math.abs(genNum - assignedNumbers.get(0));
                
                // compare differences until a winner is found
                for(int i = 1; i < ties + 1 && winner; i++){

                    int currDifference = Math.abs(genNum - assignedNumbers.get(i));

                    if(leastDifference == currDifference){ //if there are two candidates with the same assigned rand number, winner is set to false loop is exited and the pool selection is restarted
                        winner = false; //sets winner to false since winner has not been selected start over
                    }
                    else if(leastDifference > currDifference){ // leastDiffeence is greater than the curr candidates  assigned number difference
                        leastDifference = currDifference; // makes the candidate a candidate to be the new winner
                        index = i;
        
                    }

                }
        
            }
            return index; // returns winning candidates index. (Based on temp candidate list)
           
           

   

            


     }


public void sortCandidates(PO_Candidate[] candidates) // sorts candidates by votes
{
    for (int j = 1; j < candidates.length; j++) {  
        PO_Candidate key = candidates[j];
        int i = j-1;  
        while ( (i > -1) && (candidates[i].getVotes() < key.getVotes())) {  
            candidates[i+1] = candidates[i];  
            i--;  
        }  
        candidates[i+1] = key;  
}
}

 
         /**
   * This method prints out all of the important results that the election judge and public would want to see from the election
   * @param void
   * @return void
   */
    public void displayResults(){
        //displays results exactly as they are being print below 
        //we are displaying information about the election such as number of candidates, ballots, party names along with votes recieved and seats earned and caniddate names along with seats they earned
        String results = "";
         
        results += "-----POPULARITY ONLY ELECTION RESULTS AND SUMMARY-----\n\n";
        results += "GENERAL INFORMATION ABOUT THE ELECTION\n";
        results += "WINNER: " + winner.getName() + " " + "("+ winner.getParty() + ")" + "\n";
        results += "Number of Candidates: " + numCandidates + "\n";
        results += "Number of Ballots cast: " + numBallots + "\n";
        results += "Total Number of in Election: " +  "\n";
        results += "Candidate names and Party, number of votes received:" + "\n";
        for(int i = 0; i < numCandidates; i++){
            double votes = candidates[i].getVotes();
            results += candidates[i].getName() + " " + "("+ candidates[i].getParty() + ")" + " \nVotes: " + candidates[i].getVotes() + " Percentage: " + Math.round((votes/numBallots * 100.00) * 100.0) / 100.0 + "%";
        
        
            results += "\n";
        
        }

       

        System.out.println(results);
       

    }


               /**
   * This method clears out the ballots setting all values in array to null
   * @param ballots to clear
   * @return void
   */
    public void clearBallots(PO_Ballot[] ballots){
        Arrays.fill(ballots, null);
    }

    
}
