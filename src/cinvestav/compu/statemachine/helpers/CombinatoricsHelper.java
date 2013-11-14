package cinvestav.compu.statemachine.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinatoricsHelper {

    public static void main(String args[]) {

        Set<Integer> test = new HashSet<Integer>();
        Set<Integer> test2 = new HashSet<Integer>();

        test.add(3);
        test.add(1);
        test.add(2);

        test2.add(3);
        test2.add(1);
        test2.add(2);

        //System.out.println(test2.equals(test));
    }

    private static boolean nextCombination(Integer comb[], int n, int k) {
        int i = k - 1;
        if (i == -1) { return false;
        //System.out.println("NOT VALID INDEX, n:" + n + ", k:" + k);
        }

        ++comb[i];
        while ((i >= 0) && (comb[i] >= n - k + 1 + i)) {
            --i;
            if (i >= 0) {
                ++comb[i];
            }
        }

        if (comb[0] > n - k) /* Combination (n-k, n-k+1, ..., n) reached */
        return false; /* No more combinations can be generated */

        /* comb now looks like (..., x, n, n, n, ..., n).
        Turn it into (..., x, x + 1, x + 2, ...) */
        for (i = i + 1; i < k; ++i)
            comb[i] = comb[i - 1] + 1;

        return true;
    }

    public static List<Set<Integer>> listCombinationsFixed2(final int n, final int k, final boolean isZeroBased) {
        List<Set<Integer>> combinations = new ArrayList<Set<Integer>>();

        //System.out.println("will get combinations with (n,k)=(" + n + "," + k + ")");

        Integer[] combination = null;

        if (k == 0 || k == n) {
            combination = new Integer[n];
            for (int i = 0; i < n; i++) {
                combination[i] = i;
            }
        } else {
            combination = new Integer[k];
            for (int i = 0; i < k; i++) {
                combination[i] = i;
            }
        }

        if (isZeroBased) {
            combinations.add(new HashSet<Integer>(Arrays.asList(combination)));
        } else {
            Integer[] tempCombination = new Integer[combination.length];

            for (int i = 0; i < combination.length; i++) {
                tempCombination[i] = combination[i] + 1;
            }
            combinations.add(new HashSet<Integer>(Arrays.asList(tempCombination)));
        }

        while (nextCombination(combination, n, k)) {

            if (isZeroBased) {
                combinations.add(new HashSet<Integer>(Arrays.asList(combination)));
            } else {
                Integer[] tempCombination = new Integer[combination.length];

                for (int i = 0; i < combination.length; i++) {
                    tempCombination[i] = combination[i] + 1;
                }
                combinations.add(new HashSet<Integer>(Arrays.asList(tempCombination)));
            }

        }

        return combinations;
    }

    public static List<Set<Integer>> listCombinationsFixed(final int total, final int k, final boolean isZeroBased) {
        List<Set<Integer>> combinations = new ArrayList<Set<Integer>>();
        long totalCombinations = calcTotalCombinations(total, k);
        int offset = 1;
        for (int combinationNumber = 1; combinationNumber <= totalCombinations; combinationNumber++) {
            Set<Integer> combination = new HashSet<Integer>();
            int start = (combinationNumber % total);
            boolean updateOffset = false;

            if (start == 0) {
                start = total;
                updateOffset = true;
            }

            int digit = start;
            for (int j = 1; j <= k; j++) {

                //System.out.println("digit:" + digit + ", offset:" + offset);
                digit = (digit + offset);
                //System.out.println("digit + offset:" + digit);
                digit = digit % total;

                if (digit == 0) {
                    digit = total;
                }
                //System.out.println("digit after modulo operation:" + digit + ", n=" + total);

                if (combination.contains(digit)) {
                    System.out.println("DIGIT:" + digit + " IS IN COMBINATION:" + combination + ", offset:" + offset);
                }

                combination.add(digit);
            }

            if (combination.size() != k) {
                System.out.println("COMBINATION NOT VALID:" + combination + ", k=" + k);
            }

            if (updateOffset) {
                offset = (offset + 1) % (k - 1);
                if (offset == 0) {
                    offset = (k - 1);
                }

                updateOffset = false;
            }

            if (isZeroBased) {
                Set<Integer> combinationZeroBased = new HashSet<Integer>();

                for (Integer aDigit : combination) {
                    aDigit--;
                    combinationZeroBased.add(aDigit);
                }

                combination = combinationZeroBased;
            }

            if (combinations.contains(combination)) {
                System.out.println("COMBINATION:" + combination + " IS REAPEATED!!");
            }

            combinations.add(combination);
        }

        return combinations;
    }

    public static List<Set<Integer>> listCombinations(int n, int k, boolean zeroBased) {
        List<Set<Integer>> combinations = new ArrayList<Set<Integer>>();
        int maxDigit, minDigit, initialOffset = 1, zeroBasedWeigth;
        long totalCombinations = calcTotalCombinations(n, k);
        boolean isZeroBased = zeroBased;
        zeroBased = false;

        if (zeroBased) {
            maxDigit = n - 1;
            minDigit = 0;
            zeroBasedWeigth = 1;
        } else {
            maxDigit = n;
            minDigit = 1;
            zeroBasedWeigth = 0;
        }

        for (int element = 0, firstDigit = minDigit; element < totalCombinations; element++, firstDigit++) {
            Set<Integer> combination = new HashSet<Integer>();

            if (firstDigit > maxDigit) {
                firstDigit = minDigit;
                initialOffset = initialOffset + 1;
            }

            for (int nextDigit = firstDigit, position = k - 1; position >= 0; position--, nextDigit = nextDigit + initialOffset) {

                if (nextDigit > maxDigit) {
                    nextDigit = nextDigit % maxDigit - zeroBasedWeigth;
                }

                combination.add(nextDigit);
            }

            if (isZeroBased) {
                Set<Integer> combinationZeroBased = new HashSet<Integer>();

                for (Integer aDigit : combination) {
                    aDigit--;
                    combinationZeroBased.add(aDigit);
                }

                combination = combinationZeroBased;
            }

            combinations.add(combination);

        }

        return combinations;
    }

    public static long calcTotalCombinations(long n, long k) {
        long numerator = 1, numeratorStartValue, denominator;

        long nk = n - k;
        if (nk > k) {//se elimina nk en denominador, queda k en el denominador
            numeratorStartValue = nk + 1;
            denominator = k;
        } else {//se elimina k en el denominador, queda nk
            numeratorStartValue = k + 1;
            denominator = nk;
        }

        for (; numeratorStartValue <= n; numeratorStartValue++) {
            numerator = numerator * numeratorStartValue;
        }

        denominator = calcTotalPermutations(denominator);

        return numerator / denominator;
    }

    public static long calcTotalPermutations(long n) {

        if (n != 0) {
            return n * calcTotalPermutations(n - 1);
        } else {
            return 1;
        }

    }

    public static List<Set<Integer>> listAllCombinations(int n, boolean zeroBased) {
        List<Set<Integer>> allCombinations;

        allCombinations = new ArrayList<Set<Integer>>();
        for (int k = 0; k < n; k++) {
            allCombinations.addAll(listCombinationsFixed2(n, k, zeroBased));
        }

        return allCombinations;
    }

    public static List<Integer> getDigits(int number) {
        List<Integer> digits;

        digits = new ArrayList<Integer>();
        while (number > 0) {
            digits.add(number % 10);
            number /= 10;
        }

        return digits;
    }
}
