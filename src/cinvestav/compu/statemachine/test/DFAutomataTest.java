package cinvestav.compu.statemachine.test;

import junit.framework.Assert;

import org.junit.Test;

import cinvestav.compu.statemachine.DFAutomata;

public class DFAutomataTest {

    @Test
    public void testAcceptableStrings() {
        int[][] transitionMatrix = { { 2, 1 }, { 3, 0 }, { 0, 3 }, { 1, 2 } };
        int initialState = 0;
        Integer[] finalStates = { 0 };
        String[] alphabet = { "0", "1" };
        String stringToTest = "1,1,0,1,0,1", notValidString = "1,1,0", delimiter = ",";

        DFAutomata automata = new DFAutomata(alphabet, transitionMatrix,initialState, finalStates);
        Assert.assertTrue(automata.isAcceptable( stringToTest, delimiter));
        Assert.assertFalse(automata.isAcceptable( notValidString, delimiter));

    }

}
