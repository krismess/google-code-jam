package tasks2019.qualification;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem
 * On the Code Jam team, we enjoy sending each other pangrams, which are phrases that use each letter of the English alphabet at least once.
 * One common example of a pangram is "the quick brown fox jumps over the lazy dog".
 * Sometimes our pangrams contain confidential information — for example, CJ QUIZ: KNOW BEVY OF DP FLUX ALGORITHMS — so we need to keep them secure.
 *
 * We looked through a cryptography textbook for a few minutes, and we learned that it is very hard to factor products of two large prime numbers,
 * so we devised an encryption scheme based on that fact. First, we made some preparations:
 *
 * We chose 26 different prime numbers, none of which is larger than some integer N.
 * We sorted those primes in increasing order. Then, we assigned the smallest prime to the letter A, the second smallest prime to the letter B, and so on.
 * Everyone on the team memorized this list.
 * Now, whenever we want to send a pangram as a message, we first remove all spacing to form a plaintext message.
 * Then we write down the product of the prime for the first letter of the plaintext and the prime for the second letter of the plaintext.
 * Then we write down the product of the primes for the second and third plaintext letters, and so on,
 * ending with the product of the primes for the next-to-last and last plaintext letters. This new list of values is our ciphertext.
 * The number of values is one smaller than the number of characters in the plaintext message.
 *
 * For example, suppose that N = 103 and we chose to use the first 26 odd prime numbers, because we worry that it is too easy to factor even numbers.
 * Then A = 3, B = 5, C = 7, D = 11, and so on, up to Z = 103.
 * Also suppose that we want to encrypt the CJ QUIZ... pangram above, so our plaintext is CJQUIZKNOWBEVYOFDPFLUXALGORITHMS.
 * Then the first value in our ciphertext is 7 (the prime for C) times 31 (the prime for J) = 217; the next value is 1891, and so on, ending with 3053.
 *
 * We will give you a ciphertext message and the value of N that we used.
 * We will not tell you which primes we used, or how to decrypt the ciphertext. Do you think you can recover the plaintext anyway?
 *
 *
 * Link to the problem and discription of input : https://codingcompetitions.withgoogle.com/codejam/round/0000000000051705/000000000008830b
 */

public class Cryptopangrams {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> alphabet = new ArrayList<>();
        for (int i=65;i<91;i++) {
            alphabet.add(Character.toString((char) i));
        }
        int loops = scanner.nextInt();

        for (int i=1;i<=loops;i++) {
            BigInteger n = scanner.nextBigInteger();
            int l = scanner.nextInt();
            List<BigInteger> list = new ArrayList<>();
            for (int j=0;j<l;j++) {
                list.add(scanner.nextBigInteger());
            }
            List<BigInteger> indexAndFactor = twoDifferentNumbers(list);
            BigInteger[] numbers = new BigInteger[l+1];
            numbers[indexAndFactor.get(0).intValue()] = indexAndFactor.get(1);

            fillLeftSide(list,numbers,indexAndFactor.get(0),indexAndFactor.get(1));
            fillRightSide(list,numbers,indexAndFactor.get(0).add(BigInteger.ONE),indexAndFactor.get(1));

            List<BigInteger> temp = Arrays.stream(numbers.clone()).distinct().collect(Collectors.toList());

            temp.sort(Comparator.naturalOrder());
            System.out.println("Case #" + i + ": " + decode(numbers,temp,alphabet));

        }

    }

    public static BigInteger findCommonFactor(List<BigInteger> list) {
        BigInteger a = list.get(0);
        BigInteger b = list.get(1);
        if (b.remainder(a).equals(BigInteger.ZERO)) {
            return a;
        } else {
            List<BigInteger> temp = new ArrayList<>();
            temp.add(0,b.remainder(a));
            temp.add(1, a);
            return findCommonFactor(temp);
        }

    }

    public static List<BigInteger> twoDifferentNumbers(List<BigInteger> list) {
        List<BigInteger> result = new ArrayList<>();
        List<BigInteger> twoNumbers = new ArrayList<>();
        for (int i=0;i<list.size()-1;i++) {
            if (!list.get(i).equals(list.get(i+1))) {
                twoNumbers.add(list.get(i));
                twoNumbers.add(list.get(i+1));
                result.add(BigInteger.valueOf(i));
                result.add(list.get(i).divide(findCommonFactor(twoNumbers)));
                break;
            }
        }
        return result;
    }
    public static void fillLeftSide(List<BigInteger> list, BigInteger[] array, BigInteger index, BigInteger factor) {
        BigInteger tempFactor = factor;
        for (int i=index.intValue()-1;i>=0;i--){
            array[i] = list.get(i).divide(tempFactor);
            tempFactor = array[i];
        }
    }

    public static void fillRightSide(List<BigInteger> list, BigInteger[] array, BigInteger index, BigInteger factor) {
        BigInteger tempFactor = factor;
        for (int i=index.intValue();i<array.length;i++){
            array[i] = list.get(i-1).divide(tempFactor);
            tempFactor = array[i];
        }
    }

    public static String decode(BigInteger[] numbers, List<BigInteger> temp, List<String> alphabet) {
        Map<BigInteger,String> map = new HashMap<>();
        for (int i=0;i<26;i++) {
            map.put(temp.get(i),alphabet.get(i));
        }
        StringBuilder sb = new StringBuilder();
        for (BigInteger i: numbers) {
            sb.append(map.get(i));
        }
        return sb.toString();

    }
}

//TODO: needs refactoring (using too much BigInteger)
