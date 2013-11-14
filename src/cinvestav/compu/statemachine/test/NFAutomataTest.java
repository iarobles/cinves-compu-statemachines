package cinvestav.compu.statemachine.test;

import junit.framework.Assert;

import org.junit.Test;

import cinvestav.compu.statemachine.NFAutomata;

public class NFAutomataTest {

    @Test
    public void testAcceptableStrings() {
        Integer[][][] transitionMatrix = { { { 0, 3 }, { 0, 1 } }, { {}, { 2 } }, { { 2 }, { 2 } },
                                          { { 4 }, {} }, { { 4 }, { 4 } } };
        int initialState = 0;
        Integer[] finalStates = { 4, 2 };
        String[] alphabet = { "0", "1" };
        String stringToTest = "0 1 0 0 1", notValidString = "0 1 0", delimiter = " ";

        NFAutomata automata = new NFAutomata(alphabet, transitionMatrix, initialState, finalStates);
        Assert.assertTrue(automata.isAcceptable(initialState, stringToTest, delimiter));
        Assert.assertFalse(automata.isAcceptable(initialState, notValidString, delimiter));

    }

}
