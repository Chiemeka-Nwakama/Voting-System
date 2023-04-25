
import src.*;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;

import src.PO;
import src.PO_Ballot;
import src.PO_Candidate;

public class ElectionTestPO {

    PO po_single;
    PO po_multiple;
    String path = "C:\\Users\\justi\\Desktop\\repo-Team3\\Project2\\testing"; // need to edit for your machine

    @Before
    public void setUp() {

        // C:\\Users\\chiem\\OneDrive - Marshall Public
        // Schools\\Desktop\\repo-Team3\\CSCI 5801 Election System\\testCPL.txt
        // "C:\\Users\\Sidney\\OneDrive\\Desktop\\UMN Classes\\New
        // folder\\repo-Team3\\CSCI 5801 Election System>"
        try {

            File file_1 = new File(path + "\\POfile1.csv");
            File[] file_single = new File[1];
            file_single[0] = file_1;
            File[] file_multiple = new File[3];
            File file_2 = new File(path + "\\POfile2.csv");
            File file_3 = new File(path + "\\POfile3.csv");
            file_multiple[0] = file_1;
            file_multiple[1] = file_2;
            file_multiple[2] = file_3;

            po_single = new PO(file_single);
            po_multiple = new PO(file_multiple);
        } catch (IOException e) {
            System.out.println("Error File not found!");
        }
    }

    @Test

    public void testPopulateCandidate() { // test the populate candidates that is used in the constructor to see if it
                                          // populated the data correctly

        String expectedCandiates = "Pike Foster Deutsch Borg Jones Smith ";

        int i = 0;
        PO_Candidate[] candidates_m = po_multiple.getCandidates();
        PO_Candidate[] candidates_s = po_single.getCandidates();
        String actualCandiates_s = "";
        for (PO_Candidate candidate : candidates_s) {
            actualCandiates_s = actualCandiates_s + candidate.getName() + " ";

        }
        assertEquals("Testing to see if reads in the right candidates (single file): ", expectedCandiates,
                actualCandiates_s);

        String actualCandiates_m = "";
        for (PO_Candidate candidate : candidates_m) {
            actualCandiates_m = actualCandiates_m + candidate.getName() + " ";

        }

        assertEquals("Testing to see if reads in the right candidates (single file): ", expectedCandiates,
                actualCandiates_m);

    }

    @Test

    // potential bug, only passes test when ran individual not with the rest of the
    // tests

    public void testPopulateBallots() {
        // test the populate ballots that is used in the constructor to see if it
        // populated the data correctly

        String[] expectedBallots_s = { "Ballot 0: 1,,,,,", "Ballot 1: 1,,,,,", "Ballot 2: ,1,,,,", "Ballot 3: ,,,,1,",
                "Ballot 4: ,,,,,1", "Ballot 5: ,,,1,,", "Ballot 6: ,,,1,,", "Ballot 7: 1,,,,,", "Ballot 8: ,1,,,," };
        String[] expectedBallots_m = { "Ballot 0: 1,,,,,", "Ballot 1: 1,,,,,", "Ballot 2: ,1,,,,", "Ballot 3: ,,,,1,",
                "Ballot 4: ,,,,,1", "Ballot 5: ,,,1,,", "Ballot 6: ,,,1,,", "Ballot 7: 1,,,,,", "Ballot 8: ,1,,,,",
                "Ballot 9: 1,,,,,", "Ballot 10: ,1,,,," };

        ArrayList<PO_Ballot> ballots_s = new ArrayList<>();
        ballots_s = po_single.getBallots();

        ArrayList<PO_Ballot> ballots_m = new ArrayList<>();
        ballots_m = po_multiple.getBallots();

        for (int i = 0; i < expectedBallots_s.length; i++) {
            assertEquals("Testing to see if getting ballots (single file): ", expectedBallots_s[i],
                    ballots_s.get(i).toString());

        }

        for (int i = 0; i < expectedBallots_m.length; i++) {
            assertEquals("Testing to see if getting ballots (multiple files): ", expectedBallots_m[i],
                    ballots_m.get(i).toString());
        }

    }

    @Test

    public void testgetNumCandidates() { // test to see if num parties get works
        String expectedNum = "6";
        String actualNum_s = po_single.getNumCandidates() + "";
        String actualNum_m = po_multiple.getNumCandidates() + "";

        assertEquals("Testing getter (single file): ", expectedNum, actualNum_s);
        assertEquals("Testing getter (multiple files): ", expectedNum, actualNum_m);

    }

    @Test

    public void testgetBallots() { // test to see if num parties get works
        String[] expectedBallots_s = { "Ballot 0: 1,,,,,", "Ballot 1: 1,,,,,", "Ballot 2: ,1,,,,", "Ballot 3: ,,,,1,",
                "Ballot 4: ,,,,,1", "Ballot 5: ,,,1,,", "Ballot 6: ,,,1,,", "Ballot 7: 1,,,,,", "Ballot 8: ,1,,,," };
        String[] expectedBallots_m = { "Ballot 0: 1,,,,,", "Ballot 1: 1,,,,,", "Ballot 2: ,1,,,,", "Ballot 3: ,,,,1,",
                "Ballot 4: ,,,,,1", "Ballot 5: ,,,1,,", "Ballot 6: ,,,1,,", "Ballot 7: 1,,,,,", "Ballot 8: ,1,,,,",
                "Ballot 9: 1,,,,,", "Ballot 10: ,1,,,," };

        ArrayList<PO_Ballot> ballots_s = new ArrayList<>();
        ballots_s = po_single.getBallots();

        ArrayList<PO_Ballot> ballots_m = new ArrayList<>();
        ballots_m = po_multiple.getBallots();

        for (int i = 0; i < expectedBallots_s.length; i++) {
            assertEquals("Testing to see if getting ballots (single file): ", expectedBallots_s[i],
                    ballots_s.get(i).toString());

        }

        for (int i = 0; i < expectedBallots_m.length; i++) {
            assertEquals("Testing to see if getting ballots (multiple files): ", expectedBallots_m[i],
                    ballots_m.get(i).toString());
        }

    }

    @Test

    public void testAssignBallots() { // test to see if ballots are assigned correctly to candidates via how many
                                      // votes they have
        // Single File
        String[] expectedVotes_s = { "3", "2", "0", "2", "1", "1" };

        po_single.assignBallots();
        PO_Candidate[] candidates_s = po_single.getCandidates();
        for (int i = 0; i < po_single.getNumCandidates(); i++) {

            assertEquals("Testing to see if assigning ballots (single file): ", expectedVotes_s[i],
                    candidates_s[i].getVotes() + "");

            assertEquals("Testing to see if assigning ballots (single file): ", expectedVotes_s[i],
                    candidates_s[i].getVotes() + "");

        }

        // Multiple Files
        String[] expectedVotes_m = { "4", "3", "0", "2", "1", "1" };

        po_multiple.assignBallots();
        PO_Candidate[] candidates_m = po_multiple.getCandidates();
        for (int i = 0; i < po_multiple.getNumCandidates(); i++) {

            assertEquals("Testing to see if assigning ballots (single file): ", expectedVotes_m[i],
                    candidates_m[i].getVotes() + "");

        }

    }

    @Test

    public void sortCandidates() { // test to see if ballots are assigned correctly to candidates via how many
                                   // votes they have
        // Single File

        po_single.assignBallots();

        PO_Candidate temp[] = po_single.getCandidates();// candidates list since they will be reordered by their
                                                        // remainders in descending order later

        po_single.sortCandidates(temp);

        assertEquals("Testing to see if duplicates are correctly found in a row", "Pike", temp[0].getName());

    }

    @Test

    public void testDuplicates() { // test to see if duplicates works
        int expected = 1;
        po_single.assignBallots();
        int actual = po_single.duplicates(0);

        assertEquals("Testing to see if duplicates are correctly found in a row", expected, actual);

        expected = 1;
        actual = po_single.duplicates(2);

        assertEquals("Testing to see if duplicates are correctly found in a row ", expected, actual);
        expected = 2;
        actual = po_single.duplicates(4);

        assertEquals("Testing to see if duplicates are correctly found in a row", expected, actual);

    }

    @Test

    public void testGetNameandParty() {
        PO_Candidate candidate = new PO_Candidate("Obama", "D");
        String expected = "Obama D";
        String actual = candidate.getName() + " " + candidate.getParty();

        assertEquals("Test name and party getter", expected, actual);

    }

    @Test
    public void testselectWinnerPool() {

        PO potest = null;
        String expected = "fair around 33-33-33";
        String actual = "";

        int count = 0; // how many times extra seat is given to one of the two parties
        for (int i = 0; i < 300; i++) {
            try {
                File file = new File(path + "\\testPO_Pool.csv");
                File[] test = new File[1];
                test[0] = file;
                potest = new PO(test);
            } catch (IOException e) {
                System.out.println("Error File not found!"); // potest.distributeSeats(); // has an error
                break;
            }
            potest.assignBallots();
            potest.selectWinner();

            PO_Candidate winner = potest.getWinner();
            PO_Candidate[] candidates = potest.getCandidates();

            if (candidates[0].getName().equals(winner.getName())) { // if the winner matches the first candidate
                count++;
            }
        }

        double percent = count / 300.0;

        if (percent >= .30 && percent <= .36) { // sees if it falls within .33 with a margin of error of .03 +/-
            actual = "fair around 33-33-33";

        } else {
            actual = "not fair";

        }

        assertEquals("testing the fairness of pool select for candidates", expected, actual);

    }

    @Test
    public void testselectWinnerCoinToss() {

        PO potest = null;
        String expected = "fair around 50-50";
        String actual = "";

        int count = 0; // how many times extra seat is given to one of the two parties
        for (int i = 0; i < 1000; i++) {
            try {
                File file = new File(path + "\\testPO_Cointoss.csv");
                File[] test = new File[1];
                test[0] = file;
                potest = new PO(test);
            } catch (IOException e) {
                System.out.println("Error File not found!"); // potest.distributeSeats(); // has an error
                break;
            }
            potest.assignBallots();
            potest.selectWinner();

            PO_Candidate winner = potest.getWinner();
            PO_Candidate[] candidates = potest.getCandidates();

            if (candidates[0].getName().equals(winner.getName())) { // if the winner matches the first candidate
                count++;
            }
        }

        double percent = count / 1000.0;

        if (percent >= .475 && percent <= .525) { // sees if it falls within .5 with a margin of error of .025 +/-
            actual = "fair around 50-50";

        } else {
            actual = "not fair";

        }

        assertEquals("testing the fairness of pool select for candidates", expected, actual);

    }

    @Test
    public void testAddVote() {
        PO_Candidate candidate = new PO_Candidate("Bob", "Democrats");
        String expected = "1";
        candidate.addVote();
        String actual = candidate.getVotes() + "";

        assertEquals("Test candidate add seats/getter", expected, actual);

    }

    @Test
    public void selectWinner() { // test to see if correct winner is selected

        // Single File
        String expected_winner = "Pike D";

        po_single.assignBallots();
        po_single.selectWinner();

        assertEquals("Testing to see if correct winner is selected", expected_winner,
                po_single.getWinner().getName() + " " + po_single.getWinner().getParty());

        // Multiple Files
        po_multiple.assignBallots();
        po_multiple.selectWinner();

        assertEquals("Testing to see if correct winner is selected", expected_winner,
                po_multiple.getWinner().getName() + " " + po_multiple.getWinner().getParty());

    }

    @Test
    public void testMultipleFiles() throws FileNotFoundException {
        String[] fileNames = { path + "\\POfile1.csv", path + "\\POfile2.csv", path + "\\POfile3.csv" };
        File[] files = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            files[i] = new File(fileNames[i]);

        }
        PO po = new PO(files);

        // java.util.NoSuchElementException: No line found

        String expected = fileNames[0] + " " + fileNames[1] + " " + fileNames[2];

        String actual = po.getFiles();

        assertEquals("Testing bringing in multiple files", expected, actual);
    }
}
