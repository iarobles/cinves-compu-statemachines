package cinvestav.compu.statemachine.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cinvestav.compu.statemachine.DFAutomata;

public class DFAHelper {

    public static String getEquivalentCodeInC(DFAutomata dfa, String ltSymbol, String gtSymbol) throws IOException {
        String generatedCode;
        
        generatedCode = CodeTemplateForC.CODE_TEMPLATE.replace(CodeTemplateForC.ALPHABET_SIZE_TOKEN, dfa.getAlphabet().size() + "");        
        generatedCode = generatedCode.replace(CodeTemplateForC.LESSTHAN_SYMBOL, ltSymbol);
        generatedCode = generatedCode.replace(CodeTemplateForC.GREATHERTHAN_SYMBOL, gtSymbol);
        generatedCode = generatedCode.replace(CodeTemplateForC.ALPHABET_TOKEN, collectionToString(dfa.getAlphabet(), "\'", "\'"));
        generatedCode = generatedCode.replace(CodeTemplateForC.INITIAL_STATE_TOKEN, dfa.getInitialState().toString());
        generatedCode = generatedCode.replace(CodeTemplateForC.STATES_SIZE_TOKEN, dfa.getTransitionMatrix().length + "");
        generatedCode = generatedCode.replace(CodeTemplateForC.FINAL_STATES_TOKEN, collectionToString(dfa.getFinalStates()));
        generatedCode = generatedCode.replace(CodeTemplateForC.FINAL_STATES_SIZE_TOKEN, dfa.getFinalStates().size() + "");
        generatedCode = generatedCode.replace(CodeTemplateForC.TRANSITION_MATRIX_TOKEN, matrixToString(dfa.getTransitionMatrix()));

        return generatedCode;
    }

    public static void buildEquivalentCodeInC(DFAutomata dfa, String pathCFile) throws IOException {
        String generatedCode;
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pathCFile)));

        generatedCode = CodeTemplateForC.CODE_TEMPLATE.replace(CodeTemplateForC.ALPHABET_SIZE_TOKEN, dfa.getAlphabet().size() + "");
        generatedCode = generatedCode.replace(CodeTemplateForC.LESSTHAN_SYMBOL, "<");
        generatedCode = generatedCode.replace(CodeTemplateForC.GREATHERTHAN_SYMBOL, ">");
        generatedCode = generatedCode.replace(CodeTemplateForC.ALPHABET_TOKEN, collectionToString(dfa.getAlphabet(), "\'", "\'"));
        generatedCode = generatedCode.replace(CodeTemplateForC.INITIAL_STATE_TOKEN, dfa.getInitialState().toString());
        generatedCode = generatedCode.replace(CodeTemplateForC.STATES_SIZE_TOKEN, dfa.getTransitionMatrix().length + "");
        generatedCode = generatedCode.replace(CodeTemplateForC.FINAL_STATES_TOKEN, collectionToString(dfa.getFinalStates()));
        generatedCode = generatedCode.replace(CodeTemplateForC.FINAL_STATES_SIZE_TOKEN, dfa.getFinalStates().size() + "");
        generatedCode = generatedCode.replace(CodeTemplateForC.TRANSITION_MATRIX_TOKEN, matrixToString(dfa.getTransitionMatrix()));

        writer.write(generatedCode);
        writer.close();

    }

    private static String matrixToString(int[][] transitionMatrix) {
        StringBuilder builder;
        boolean isFirst = true;

        builder = new StringBuilder();
        for (int row = 0; row < transitionMatrix.length; row++) {

            if (isFirst) {
                isFirst = false;
            } else {
                builder.append(",");
            }

            builder.append("{");
            builder.append(arrayToString(transitionMatrix[row]));
            builder.append("}");
        }
        // TODO Auto-generated method stub
        return builder.toString();
    }

    private static String arrayToString(int[] theArray) {
        List<Integer> elements;

        elements = new ArrayList<Integer>();
        for (int element : theArray) {
            elements.add(element);
        }

        return collectionToString(elements);
    }

    private static <T> String collectionToString(Collection<T> collection) {
        return collectionToString(collection, "", "");
    }

    private static <T> String collectionToString(Collection<T> collection, String prefix, String suffix) {
        StringBuilder builder;
        boolean isFirst = true;

        builder = new StringBuilder();
        for (Object element : collection) {
            if (isFirst) {
                isFirst = false;
            } else {
                builder.append(",");
            }
            builder.append(prefix).append(element).append(suffix);
        }

        return builder.toString();
    }

}
