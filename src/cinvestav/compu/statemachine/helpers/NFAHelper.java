package cinvestav.compu.statemachine.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cinvestav.compu.statemachine.DFAutomata;
import cinvestav.compu.statemachine.NFAutomata;

public class NFAHelper {

    public static DFAutomata convertToDFA(NFAutomata nfa) {
        DFAutomata dfa;
        int[][] dfaTransitionMatrix;
        List<Set<Integer>> statesPowerSet;
        Set<Integer> dfaFinalStates, dfaInitialStateSet, nfaFinalStates;
        List<String> alphabet;
        int dfaInitialStateIndex;

        //se reserva espacio para los estados finales de la dfa
        nfaFinalStates = nfa.getFinalStates();
        dfaFinalStates = new HashSet<Integer>();

        //el alfabeto de la dfa es el mismo que de la nfa
        alphabet = nfa.getAlphabet();

        //se obtiene el conjunto potencia de los estados de la nfa
        //TODO:refactor for a better optimization!!
        statesPowerSet = CombinatoricsHelper.listAllCombinations(nfa.getTransitionMatrix().size(), true);
        statesPowerSet.add(new HashSet<Integer>());
        //System.out.println("conjunto potencia:" + statesPowerSet);

        //se crea la representacion de estados correspondiente

        List<String> dfaStates = new ArrayList<String>();
        if (nfa.getStates() != null) {
            List<String> nfaStates = nfa.getStates();

            for (Set<Integer> setOfStates : statesPowerSet) {
                StringBuilder builder;
                boolean isFirst = true;

                builder = new StringBuilder();
                //get dfa state from setOfstates
                builder.append("[");
                for (Integer nfaStateIndex : setOfStates) {

                    if (!isFirst) {
                        builder.append(",");
                    } else {
                        isFirst = false;
                    }

                    if (nfaStateIndex == -1) System.out.println("NOT VALID INDEX:-1 in set of states:" + setOfStates);
                    builder.append(nfaStates.get(nfaStateIndex));
                }
                builder.append("]");

                dfaStates.add(builder.toString());
            }
        } else {

        }

        //se reserva espacio para la matrix de transicion de la dfa
        dfaTransitionMatrix = new int[statesPowerSet.size()][alphabet.size()];

        //se crea estado inicial para la dfa
        dfaInitialStateSet = new HashSet<Integer>();
        dfaInitialStateSet.add(nfa.getInitialState());
        dfaInitialStateIndex = statesPowerSet.indexOf(dfaInitialStateSet);

        int dfaOriginalStateIndex = 0;
        //se rellena la matriz de transicion
        for (Set<Integer> aSetOfStates : statesPowerSet) {

            int alphabetIndex = 0;
            for (String symbol : alphabet) {
                Set<Integer> resultStates = nfa.delta(aSetOfStates, symbol);

                int dfaNextStateIndex = statesPowerSet.indexOf(resultStates);
                if (dfaNextStateIndex == -1) {
                    System.out.println("NOT VALID RESULT STATES:" + resultStates);
                }

                dfaTransitionMatrix[dfaOriginalStateIndex][alphabetIndex] = dfaNextStateIndex;

                alphabetIndex++;
            }

            //finalmente buscamos si el estado actual es un estado final para la dfa
            for (Integer aFinalState : nfaFinalStates) {
                if (aSetOfStates.contains(aFinalState)) {
                    //System.out.println("state:" + aSetOfStates + " is final");
                    dfaFinalStates.add(statesPowerSet.indexOf(aSetOfStates));
                    break;
                } else {
                    //System.out.println("state:" + aSetOfStates + " is not final");
                }
            }

            dfaOriginalStateIndex++;
        }

        dfa = new DFAutomata(alphabet, dfaStates, dfaTransitionMatrix, dfaInitialStateIndex, (Integer[])dfaFinalStates.toArray(new Integer[dfaFinalStates.size()]));
        dfa.setStatesPowerSet(statesPowerSet);
        dfa.setBiggerSetSizeFromPowerSet(nfa.getTransitionMatrix().size());
        return dfa;
    }
}
