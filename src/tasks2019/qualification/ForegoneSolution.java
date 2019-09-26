package tasks2019.qualification;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * PROBLEM
 * Someone just won the Code Jam lottery, and we owe them N jamcoins!
 * However, when we tried to print out an oversized check, we encountered a problem. The value of N,
 * which is an integer, includes at least one digit that is a 4... and the 4 key on the keyboard of our oversized check printer is broken.
 * Fortunately, we have a workaround: we will send our winner two checks for positive integer amounts A and B,
 * such that neither A nor B contains any digit that is a 4, and A + B = N. Please help us find any pair of values A and B that satisfy these conditions.
 *
 * Link to the problem and discription of input : https://codingcompetitions.withgoogle.com/codejam/round/0000000000051705/0000000000088231
 */

public class ForegoneSolution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.valueOf(scanner.nextLine());
        for (int k=1;k<=n;k++) {
            BigInteger value = new BigInteger(scanner.nextLine());
            int length = value.toString().length();
            String stringValue = value.toString();
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            for (int i=0;i<length;i++) {
                int digit = Character.getNumericValue(stringValue.charAt(i));
                switch (digit) {
                    case 5: sb1.append(3);
                        sb2.append(2);
                        break;
                    case 1: sb1.append(1);
                        if (sb2.length()!=0) {
                            sb2.append(0);
                        }
                        break;
                    case 0: sb1.append(0);
                        if (sb2.length()!=0) {
                            sb2.append(0);
                        }
                        break;
                    default: sb1.append(digit-1);
                        sb2.append(1);
                        break;
                }
            }
            System.out.println("Case #" + k + ": " + sb1 + " " + sb2);
        }

    }
}
