import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class test {
    public static void main(String args[]){
        System.out.println(getArray());

 
 
    }

    public static int[] getArray() {
        int arr[];
        arr = new int[4];

        for(int i = 0; i < arr.length; i++) {
            arr[i] = i;
            System.out.println(arr[i]);
         }

         return arr;

    }
    
}
