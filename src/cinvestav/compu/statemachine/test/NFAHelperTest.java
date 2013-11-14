package cinvestav.compu.statemachine.test;

import junit.framework.Assert;

import org.junit.Test;

import cinvestav.compu.statemachine.DFAutomata;
import cinvestav.compu.statemachine.NFAutomata;
import cinvestav.compu.statemachine.helpers.NFAHelper;

public class NFAHelperTest {

    private DFAutomata buildDfa() {
        int[][] transitionMatrix = { { 2, 1 }, { 3, 0 }, { 0, 3 }, { 1, 2 } };
        int initialState = 0;
        Integer[] finalStates = { 0 };
        String[] alphabet = { "0", "1" };
        String stringToTest = "1 1 0 1 0 1", notValidString = "1 1 0 ", delimiter = " ";

        DFAutomata automata = new DFAutomata(alphabet, transitionMatrix, initialState, finalStates);

        return automata;
    }

    private NFAutomata buildNFA() {
        Integer[][][] transitionMatrix = { { { 0, 3 }, { 0, 1 } }, { {}, { 2 } }, { { 2 }, { 2 } },
                                          { { 4 }, {} }, { { 4 }, { 4 } } };
        int initialState = 0;
        Integer[] finalStates = { 4, 2 };
        String[] alphabet = { "0", "1" };

        NFAutomata automata = new NFAutomata(alphabet, transitionMatrix, initialState, finalStates);

        return automata;
    }

    private NFAutomata buildNFA2() {
        Integer[][][] transitionMatrix = { { { 0, 1 }, { 1 } }, { {}, { 0, 1 } } };
        int initialState = 0;
        Integer[] finalStates = { 1 };
        String[] alphabet = { "0", "1" };
        String[] states = { "est0", "est1" };

        NFAutomata automata = new NFAutomata(alphabet,states, transitionMatrix, initialState, finalStates);

        return automata;
    }

    @Test
    public void testConversion() {
        DFAutomata dfa, dfaCreated;
        NFAutomata nfa;
        String stringToTest = "0 1 0 0 1", notValidString = "0 1 0", delimiter = " ";

        nfa = buildNFA2();
        //nfa = buildNFA();

        //se convierte dfa a nfa
        dfaCreated = NFAHelper.convertToDFA(nfa);
        Assert.assertNotNull(dfaCreated);

        System.out.println("-----------------------------------------------");
        System.out.println(nfa);
        System.out.println(dfaCreated.toString(true));
        System.out.println("-----------------------------------------------");

        Assert.assertTrue(nfa.isAcceptable(stringToTest, delimiter));

        //se prueba conversion
        //System.out.println(isAceptable);

        Assert.assertTrue("la Dfa creada de la nfa no es correcta", dfaCreated.isAcceptable(stringToTest, delimiter));
        Assert.assertFalse("la Dfa creada de la nfa no es correcta", dfaCreated.isAcceptable(notValidString, delimiter));
    }
}
