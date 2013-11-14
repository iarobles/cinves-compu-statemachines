package cinvestav.compu.statemachine.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import cinvestav.compu.statemachine.DFAutomata;
import cinvestav.compu.statemachine.helpers.DFAHelper;

public class DFAHelperTest {

    @Test
    public void testConstructionOfCCode() throws IOException {
        int[][] transitionMatrix = { { 2, 1 }, { 3, 0 }, { 0, 3 }, { 1, 2 } };
        int initialState = 0;
        Integer[] finalStates = { 0 };
        String[] alphabet = { "0", "1" };
        String stringToTest = "1,1,0,1,0,1", notValidString = "1,1,0", delimiter = ",";
        String pathCFile = System.getProperty("user.home") + File.separator + "test.c";

        DFAutomata dfa = new DFAutomata(alphabet, transitionMatrix,initialState, finalStates);
        DFAHelper.buildEquivalentCodeInC(dfa, pathCFile);

    }

}
