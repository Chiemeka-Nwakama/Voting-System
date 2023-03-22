import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
public class CPL {
    private int numParties;
    private int numCandidates;
    private int totalSeats;
    private int numBallots;
    private int[] Partyballot;
    private Party[] parties;
    private CPL_Ballot[] ballots;
    private CPL_Audit_File audit;
    private File election_file;

    public CPL(File file){
        audit.writeToAudit("CPL Election found");
        audit = new CPL_Audit_File(); // intitalzies the audit file
        audit.writeToAudit("Audit file intialized");
        audit.writeToAudit("Populating Data from CPL election file...");
        populateData(file); // intitalizes the values from the read in files
}
    
    void run(){
        audit.writeToAudit("Start of CPL Election");
        audit.writeToAudit("Assigning Ballots to Parties: ");
        assignBallots(); // distibutes Ballots to the parties associated with the parties voted for
        audit.writeToAudit("Distributing Seats to Parties: ");
         distributeSeats(); // distributes seats to parties

    }

    public int getNumParties(){
        return numParties;

    }

    public int getNumCandidates(){
        return numCandidates;

    }
    public int getTotalSeats(){
        return totalSeats;

    }
    public int getNumBallots(){
        return numBallots;

    }
    
    public void distributeSeats(){
        int quota = numBallots / totalSeats; //calculates the quotes to be used to determine seats handed out
        audit.writeToAudit("Calculating Quota: total Votes / total Seats");
        audit.writeToAudit("Quota =  " + quota);
        int seatsRemaining = totalSeats;
        audit.writeToAudit("Seats Remaining: " + seatsRemaining);



    }

    public void assignBallots(){

        for(CPL_Ballot ballot: ballots){
            int partyVote = ballot.getPartyVote();
            parties[partyVote].addVote(ballot);
            audit.writeToAudit("Assigning " +  ballot + "to " + parties[partyVote]);
        }
        Arrays.fill(ballots, null); // clears ballots since no longer need since they are in the parties now
        audit.writeToAudit("Ballots Assignment and Vote Count Complete");
        audit.writeToAudit("Results: ");
        for(Party party: parties){ //iterates through each party
            audit.writeToAudit(party.getName() + ":");
            int partyVotes = party.getVotes(); //uses total votes to know when to stop looking for ballots since the rest will be null
            audit.writeToAudit(party.getName() + "Total Votes: " + partyVotes); // writes total votes to audit
            audit.writeToAudit(party.getName() + "Ballots Earned: "); // writes ballots earned to audit
            CPL_Ballot[] partyBallots = party.getBallots();
            for(int i = 0; i < partyVotes; i++){ //iterates through the ballot array in each party after distrubtion
                audit.writeToAudit(partyBallots[i].toString());  // writes the each ballot the party earned to audit
            }
        }





    }

    public void populateData(File file){
        try {  
            Scanner sc = new Scanner(file);
            sc.nextLine(); //ignores first line since it was read already in the Election Class
            numParties =  sc.nextInt(); //reads in the 2nd line - number of parties
            audit.writeToAudit("Number of Parties: " + numParties);
            String[] partyNames = sc.nextLine().split(","); // 3rd line -- gets names of the party
            parties = new Party[numParties];
            for(int i=0; i < numParties; i++){ //creates parties 
                partyNames[i] = partyNames[i].trim(); // remove leading space between comma and name : , green party = green party
                parties[i] = new Party(partyNames[i]);
                audit.writeToAudit("Parties: ");
                audit.writeToAudit("Party " + i  + ": " + parties[i].getName());
            }
            for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
                String[] Candidate_names =  sc.nextLine().split(",");
                parties[i].populateCandidates(Candidate_names, Candidate_names.length);
                audit.writeToAudit(parties[i].getName()); //writes party name to audit
                for (CPL_Candidate candidate: parties[i].getCandidates()){ //writes each candidate in each party to audit
                    audit.writeToAudit(candidate.getName());

                }
            
                
            }
            totalSeats =  sc.nextInt(); // reads in the total number of seats avaliable
            audit.writeToAudit("Total Seats: " + totalSeats);
            numBallots =  sc.nextInt(); // reads in the total number of ballots in election
            audit.writeToAudit("Number of Ballots: " + numBallots);
            for(int i = 0; i < numParties; i++){ //reads candidates until all parties are populated
               
                parties[i].initilizeBallotCapacity(numBallots); //initialize capacity of ballots for each party to be total ballots in election
            }

            audit.writeToAudit("Ballots:");
           
            for(int i = 0; i < numBallots; i++){

                String ballot = sc.nextLine();
                int partyVote = ballot.indexOf("1"); // gets the pos of the vote
               
                    ballots[i] = new CPL_Ballot(partyVote, numParties); //intializes a new ballot
                    audit.writeToAudit("Ballot " + ballots[i].getId() + ": " + ballots[i].representation()); //prints out the ballots by id and their integer array repesentation
                    
                }  
                sc.close();
                audit.writeToAudit("Data Succesfully Populated!");
           
}
catch(IOException e){ 
    audit.writeToAudit("Data Population Failed ---> Invalid File!");

    
}

    }

    
    public int coinToss(){

    }
    public int poolselect(){

    }

    public void sortParties(Party[] parties) // sorts party by their remainder votes
    {
        for (int j = 1; j < parties.length; j++) {  
            Party key = parties[j];
            int i = j-1;  
            while ( (i > -1) && ( parties[i].getRemainderVotes() > key.getRemainderVotes()  ) ) {  
                parties[i+1] = parties[i];  
                i--;  
            }  
            parties[i+1] = key;  
    }
}


    
    public void displayResults(){

    }
   
    public void clearParties(Party[] parties){

    }
    public void clearBallots(CPL_Ballot[] ballots){

    }

    
}