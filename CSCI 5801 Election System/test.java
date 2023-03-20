import java.util.Arrays;

public class test {
    public static void main(String args[]) {
        int ballot[];
        ballot = new int[5];
        Arrays.fill(ballot, 0);

        for(int i = 0; i < 5; i++) {
            System.out.println(ballot[i]);
        }
    }
    
}
