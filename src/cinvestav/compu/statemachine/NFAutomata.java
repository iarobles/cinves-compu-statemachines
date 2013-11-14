package cinvestav.compu.statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFAutomata implements Automata {

    private List<List<Set<Integer>>> transitionMatrix;

    private List<String> alphabet;

    private List<String> states;

    private Integer initialState;

    private Set<Integer> finalStates;

    public NFAutomata(String[] alphabet, Integer[][][] transitionMatrix, Integer initialState, Integer[] finalStates) {
        this.initialState = initialState;
        this.alphabet = Arrays.asList(alphabet);
        this.transitionMatrix = buildTransitionMatrix(transitionMatrix);
        this.finalStates = new HashSet<Integer>(Arrays.asList(finalStates));
    }

    public NFAutomata(List<String> alphabet2, List<String> states, List<List<Set<Integer>>> transitionMatrix2, Integer initialState2, Set<Integer> finalStates2) {
        this(alphabet2, transitionMatrix2, initialState2, finalStates2);
        this.states = states;
    }

    public NFAutomata(List<String> alphabet2, List<List<Set<Integer>>> transitionMatrix2, Integer initialState2, Set<Integer> finalStates2) {
        this.alphabet = alphabet2;
        this.transitionMatrix = transitionMatrix2;
        this.initialState = initialState2;
        this.finalStates = finalStates2;
    }

    public NFAutomata(String[] alphabet2, String[] states2, Integer[][][] transitionMatrix2, Integer initialState2, Integer[] finalStates2) {
        this(alphabet2, transitionMatrix2, initialState2, finalStates2);
        this.states = Arrays.asList(states2);
    }

    private List<List<Set<Integer>>> buildTransitionMatrix(Integer[][][] transitionMatrix) {
        List<List<Set<Integer>>> theTransitionMatrix;

        theTransitionMatrix = new ArrayList<List<Set<Integer>>>();
        int rows = transitionMatrix.length;
        int columns = transitionMatrix[0].length;

        for (int row = 0; row < rows; row++) {

            List<Set<Integer>> rowOfStates = new ArrayList<Set<Integer>>();
            theTransitionMatrix.add(rowOfStates);
            for (int column = 0; column < columns; column++) {

                Set<Integer> statesByStateAndSymbol = new HashSet<Integer>(Arrays.asList(transitionMatrix[row][column]));
                rowOfStates.add(statesByStateAndSymbol);
            }
        }

        return theTransitionMatrix;
    }

    public Set<Integer> delta(int state, String symbol) {
        Set<Integer> states = null;

        if (symbol == null || symbol.length() == 0) {
            states = new HashSet<Integer>();
            //delt(q,eps) = q
            states.add(state);
        } else {
            
            int symbolIndex = this.alphabet.indexOf(symbol);
            if (symbolIndex == -1) System.out.println("NOT VALID SYMBOL:" + symbol);
            states = this.transitionMatrix.get(state).get(symbolIndex);
        }

        return states;
    }

    public Set<Integer> delta(Set<Integer> states, String symbol) {
        Set<Integer> unionOfStates;

        unionOfStates = new HashSet<Integer>();
        for (Integer theState : states) {
            unionOfStates.addAll(delta(theState, symbol));
        }

        //System.out.println("union of states are:" + unionOfStates);
        return unionOfStates;
    }

    public Set<Integer> delta(Integer state, String[] inputSymbols) {
        Set<Integer> states;

        states = new HashSet<Integer>();
        states.add(state);

        for (String symbol : inputSymbols) {
            states = delta(states, symbol);
        }

        return states;
    }

    @Override
    public boolean isAcceptable(String inputString, String delimiter) {
        return isAcceptable(this.initialState, inputString, delimiter);
    }

    @Override
    public boolean isAcceptable(int initialState, String inputString, String delimiter) {
        if (inputString == null) {
            inputString = "";
        }
        return isAcceptable(initialState, inputString.split(delimiter));
    }

    @Override
    public boolean isAcceptable(int initialState, String[] inputSymbols) {
        Set<Integer> states;
        boolean isValid = false;

        states = delta(initialState, inputSymbols);
        for (Integer aFinalState : this.finalStates) {
            if (states.contains(aFinalState)) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }

    public List<List<Set<Integer>>> getTransitionMatrix() {
        return transitionMatrix;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public Set<Integer> getFinalStates() {
        return finalStates;
    }

    public Integer getInitialState() {
        return initialState;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void setTransitionMatrix(List<List<Set<Integer>>> transitionMatrix) {
        this.transitionMatrix = transitionMatrix;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setInitialState(Integer initialState) {
        this.initialState = initialState;
    }

    public void setFinalStates(Set<Integer> finalStates) {
        this.finalStates = finalStates;
    }

    @Override
    public String toString() {
        StringBuilder builder;

        builder = new StringBuilder();
        builder.append("\n");
        builder.append("alphabet:").append(this.alphabet).append("\n");
        builder.append("states:").append(this.states).append("\n");
        builder.append("initial State:").append(this.initialState).append("\n");
        builder.append("final states:").append(this.finalStates).append("\n");
        builder.append("transition matrix:\n");
        for (List<Set<Integer>> row : this.transitionMatrix) {
            builder.append(row).append("\n");
        }

        builder.append("\n");

        return builder.toString();
    }

}
