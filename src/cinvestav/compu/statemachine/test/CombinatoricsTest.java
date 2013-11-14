package cinvestav.compu.statemachine.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import cinvestav.compu.statemachine.helpers.CombinatoricsHelper;

public class CombinatoricsTest {

    @Test
    public void testFail() {
        List<Set<Integer>> combinationsResult, combination5_2_zeroList, combination5_2List;
        Set<Integer> stupidSet = new HashSet<Integer>();

        stupidSet.add(3);
        stupidSet.add(4);
        stupidSet.add(7);
        stupidSet.add(8);
        long totalCombinations = CombinatoricsHelper.calcTotalCombinations(10, 4);
        System.out.println("combinations(10,4):" + totalCombinations);
        combinationsResult = CombinatoricsHelper.listCombinationsFixed2(10, 10, false);
        System.out.println("combinationsResult:" + combinationsResult);

        Assert.assertEquals(totalCombinations, combinationsResult.size());
        System.out.println("combinations:" + totalCombinations + ", combinations.size:" + combinationsResult.size());

        int setToSearchSize = stupidSet.size();
        boolean found = false;
        Set<Integer> combinationFound = null;
        for (Set<Integer> combination : combinationsResult) {
            int coincidencesFound = 0;

            if (combination.size() == setToSearchSize) {
                for (Integer aItem : stupidSet) {

                    if (combination.contains(aItem)) {
                        coincidencesFound++;

                        if (coincidencesFound == setToSearchSize) {
                            found = true;
                            combinationFound = combination;
                            break;
                        }
                    }

                }
            }

            if (found) break;
        }
        System.out.println("combinationFound:" + combinationFound);
        System.out.println("combinations(10,4):" + combinationsResult);
        Assert.assertNotSame("NO SE ENCONTRO A: " + stupidSet + " en conjunto", -1, combinationsResult.indexOf(stupidSet));

        System.out.println(combinationsResult);
    }

    @Test
    public void testCountMethods() {

        Assert.assertEquals(1, CombinatoricsHelper.calcTotalPermutations(0));
        Assert.assertEquals(1, CombinatoricsHelper.calcTotalPermutations(1));
        Assert.assertEquals(24, CombinatoricsHelper.calcTotalPermutations(4));
        Assert.assertEquals(120, CombinatoricsHelper.calcTotalPermutations(5));

        Assert.assertEquals(1, CombinatoricsHelper.calcTotalCombinations(4, 4));
        Assert.assertEquals(4, CombinatoricsHelper.calcTotalCombinations(4, 3));
        Assert.assertEquals(6, CombinatoricsHelper.calcTotalCombinations(4, 2));

        System.out.println(CombinatoricsHelper.getDigits(123));

    }

    private List<Set<Integer>> matrixToListSet(Integer[][] matrix) {
        List<Set<Integer>> matrixList;

        matrixList = new ArrayList<Set<Integer>>();
        for (int row = 0; row < matrix.length; row++) {
            Set<Integer> theSet = new HashSet<Integer>();

            for (int column = 0; column < matrix[0].length; column++) {
                theSet.add(matrix[row][column]);
            }

            matrixList.add(theSet);
        }

        return matrixList;
    }

    @Test
    @Ignore
    public void testListCombinations() {
        Integer[][] combination5_2_zero_based = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 0 },
                                                 { 0, 2 }, { 1, 3 }, { 2, 4 }, { 3, 0 }, { 4, 1 } };

        Integer[][] combination5_2 = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 }, { 5, 1 }, { 1, 3 },
                                      { 2, 4 }, { 3, 5 }, { 4, 1 }, { 5, 2 } };
        List<Set<Integer>> combinationsResult, combination5_2_zeroList, combination5_2List;

        combination5_2_zeroList = matrixToListSet(combination5_2_zero_based);
        combination5_2List = matrixToListSet(combination5_2);

        System.out.println("combinations(5,2):");
        combinationsResult = CombinatoricsHelper.listCombinationsFixed2(5, 2, true);
        System.out.println(combinationsResult);
        Assert.assertEquals(combination5_2_zeroList, combinationsResult);

        System.out.println("combinations(5,2):");
        combinationsResult = CombinatoricsHelper.listCombinationsFixed2(5, 2, false);
        System.out.println(combinationsResult);
        Assert.assertEquals(combination5_2List, combinationsResult);

        combinationsResult = CombinatoricsHelper.listAllCombinations(2, true);

    }
}
