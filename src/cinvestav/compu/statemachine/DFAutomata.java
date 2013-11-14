package cinvestav.compu.statemachine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Ismael Ariel
 * @description: Clase base para simular un Automata Finito Deterministico
 * (Estados,Alfabeto, matriz de transiciones, estado inicial, estados finales)
 *
 */
public class DFAutomata implements Automata {

    /**
     * 
     * @param alphabet
     * @param transitionMatrix
     * @param finalStates
     */
    public DFAutomata(List<String> alphabet, int[][] transitionMatrix, int initialState, Integer[] finalStates) {
        this(alphabet.toArray(new String[alphabet.size()]), transitionMatrix, initialState, finalStates);
    }

    public DFAutomata(List<String> alphabet, List<String> states, int[][] transitionMatrix, int initialState, Integer[] finalStates) {
        this(alphabet, transitionMatrix, initialState, finalStates);
        this.states = states;
    }

    public DFAutomata(String[] alphabet, int[][] transitionMatrix, int initialState, Integer[] finalStates) {

        this.initialState = initialState;
        this.setTransitionMatrix(transitionMatrix);
        this.setAlphabet(alphabet);
        this.setFinalStates(new HashSet<Integer>(Arrays.asList(finalStates)));
    }

    public DFAutomata(List<String> alphabet, int[][] transitionMatrix, Integer initialState, List<Integer> finalStates) {
        this(alphabet.toArray(new String[alphabet.size()]), transitionMatrix, initialState, finalStates.toArray(new Integer[finalStates.size()]));
    }

    public DFAutomata(String[] alphabet, Integer[][] transitionMatrix, Integer initialState, Integer[] finalStates) {
        this(alphabet, toPrimitiveArray(transitionMatrix), initialState, finalStates);
    }

    private static int[][] toPrimitiveArray(Integer[][] transitionMatrix) {
        int[][] primitiveTransitionMatrix;

        primitiveTransitionMatrix = new int[transitionMatrix.length][transitionMatrix[0].length];
        for (int row = 0; row < transitionMatrix.length; row++) {
            for (int column = 0; column < transitionMatrix[0].length; column++) {
                primitiveTransitionMatrix[row][column] = transitionMatrix[row][column];
            }
        }

        return primitiveTransitionMatrix;
    }

    // representa la matriz de transiciones(funcion de transicion estadosXAlfabeto), si se tiene
    // el valor [i][j], este representa el estado siguiente de la DFA, si esta
    // se encuentra en el estado qi con entrada aj
    private int[][] transitionMatrix = null;

    private List<String> alphabet = null;

    private List<String> states = null;

    private Integer initialState;

    private Set<Integer> finalStates;

    private List<Set<Integer>> statesPowerSet;

    private Integer biggerSetSizeFromPowerSet;

    public void setTransitionMatrix(int[][] transitionMatrix) {

        if (transitionMatrix == null || transitionMatrix.length == 0) { throw new RuntimeException("La matriz de transicion esta vacia"); }

        int totalColumns = transitionMatrix[0].length;
        for (int row = 0; row < transitionMatrix.length; row++) {
            if (transitionMatrix[row].length != totalColumns) { throw new RuntimeException("El renglon " + row + " de la matriz de transicion es mas grande que el primer renglon"); }
        }

        //total de estados = numero de renglones de la matriz de transicion
        //symbolos del alfabeto = numero de columnas de la matriz de transicion
        this.transitionMatrix = transitionMatrix;
    }

    public int delta(int state, String[] inputSymbols) {
        int nextState = state;
        //para cada simbolo(representacion) de la cadena de entrada, se busca el indice
        //asociado y se usa dicho indice en conjuncion con el estado actual para
        //determinar el siguiente estado
        for (String currentSymbol : inputSymbols) {
            nextState = delta(nextState, currentSymbol);
        }

        return nextState;
    }

    public int delta(int state, String symbol) {
        int nextState;

        if (symbol != null && symbol.length() > 0) {
            int symbolIndex = alphabet.indexOf(symbol);
            if (symbolIndex < 0) {
                System.out.println("NOT VALID SYMBOL(DFA):" + symbol);
            }
            nextState = this.transitionMatrix[state][symbolIndex];
        } else {
            nextState = state;
        }

        if(nextState == -1){
            System.out.println("delta[" + state + "][\"" + symbol + "\"]=" + nextState);
        }
        System.out.println("delta[" + state + "][\"" + symbol + "\"]=" + nextState);
        return nextState;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean usePowerSet) {
        StringBuilder builder;
        int totalStates = this.transitionMatrix.length;
        int totalSymbols = this.transitionMatrix[0].length;
        int lastSymbolIndex = totalSymbols - 1;

        int maximumPadding = (usePowerSet) ? this.biggerSetSizeFromPowerSet * 2 : 0;

        builder = new StringBuilder();
        builder.append("\n");
        if (usePowerSet) {
            builder.append("Estados:").append(statesPowerSetToString(maximumPadding)).append("\n");
        }
        builder.append("Alfabeto:").append(this.alphabet).append("\n");
        builder.append("states:").append(this.states).append("\n");
        builder.append("Estado Inicial:").append(this.initialState).append("\n");
        builder.append("Estados Finales:").append(this.finalStates).append("\n");
        builder.append("Matriz de transicion:\n");

        for (int row = 0; row < totalStates; row++) {

            if (usePowerSet) {
                builder.append("state " + setOfStatesToString(this.statesPowerSet.get(row), maximumPadding) + ": ");
            }

            for (int column = 0; column < totalSymbols; column++) {

                if (usePowerSet) {
                    builder.append(setOfStatesToString(this.statesPowerSet.get(this.transitionMatrix[row][column]), maximumPadding));
                } else {
                    builder.append(this.transitionMatrix[row][column]);
                }

                if (column != lastSymbolIndex) {
                    builder.append(", ");
                }
            }
            builder.append("\n");
        }

        builder.append("\n");

        return builder.toString();
    }

    private String statesPowerSetToString(int maximumPadding) {
        StringBuilder builder;
        boolean isFirstElement = true;

        builder = new StringBuilder();
        builder.append("{ ");

        for (Set<Integer> states : this.statesPowerSet) {
            if (!isFirstElement) {
                builder.append(", ");

            } else {
                isFirstElement = false;
            }
            builder.append(setOfStatesToString(states, maximumPadding));
        }

        builder.append(" }");

        return builder.toString();
    }

    private String setOfStatesToString(Set<Integer> states, int maximumPadding) {
        StringBuilder builder = new StringBuilder();
        int totalPadding = maximumPadding - (states.size() * 2);

        builder.append("[");
        for (Integer stateIndex : states) {
            builder.append("q").append(stateIndex);
        }
        builder.append("]");

        for (int paddingCounter = 0; paddingCounter < totalPadding; paddingCounter++) {
            builder.append(" ");
        }

        return builder.toString();
    }

    public void setStatesPowerSet(List<Set<Integer>> statesPowerSet) {
        this.statesPowerSet = statesPowerSet;
    }

    public List<Set<Integer>> getStatesPowerSet() {
        return statesPowerSet;
    }

    public Integer getBiggerSetSizeFromPowerSet() {
        return biggerSetSizeFromPowerSet;
    }

    public void setBiggerSetSizeFromPowerSet(Integer biggerSetSizeFromPowerSet) {
        this.biggerSetSizeFromPowerSet = biggerSetSizeFromPowerSet;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setInitialState(Integer initialState) {
        this.initialState = initialState;
    }

    public int[][] getTransitionMatrix() {
        return transitionMatrix;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public Integer getInitialState() {
        return initialState;
    }

    public Set<Integer> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<Integer> finalStates) {

        if (finalStates == null) { throw new RuntimeException("el conjunto de estados finales esta vacio"); }

        if (finalStates.size() > this.transitionMatrix.length) { throw new RuntimeException("hay mas estados finales(" + finalStates.size() + ") que los de la matriz de transicion(" + this.transitionMatrix.length + ")"); }

        this.finalStates = finalStates;
    }

    public void setAlphabet(String[] alphabet) {

        if (alphabet == null || alphabet.length == 0) { throw new RuntimeException("el alfabeto esta vacio"); }
        if (alphabet.length != this.transitionMatrix[0].length) { throw new RuntimeException("la matriz de trancision no esta definida para algunos simbolos del alfabeto"); }

        this.alphabet = Arrays.asList(alphabet);
    }

    @Override
    public boolean isAcceptable(String inputString, String delimiter) {

        return isAcceptable(this.initialState, inputString, delimiter);
    }

    @Override
    public boolean isAcceptable(int initialState, String inputString, String delimiter) {

        return isAcceptable(initialState, inputString.split(delimiter));

    }

    @Override
    public boolean isAcceptable(int initialState, String[] inputSymbols) {

        return this.finalStates.contains(delta(initialState, inputSymbols));
    }
}
