package cinvestav.compu.statemachine;


public interface Automata {

    boolean isAcceptable(int initialState, String inputString, String delimiter);

    boolean isAcceptable(int initialState, String[] inputSymbols);

    boolean isAcceptable(String inputString, String delimiter);

}
