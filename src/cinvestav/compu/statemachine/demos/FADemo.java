package cinvestav.compu.statemachine.demos;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cinvestav.compu.statemachine.DFAutomata;
import cinvestav.compu.statemachine.NFAutomata;
import cinvestav.compu.statemachine.helpers.DFAHelper;
import cinvestav.compu.statemachine.helpers.NFAHelper;

public class FADemo {

    private static final String OPTION_DFA_TO_C_CODE = "dfa";

    private static final String OPTION_NFA_TO_DFA = "nfac";
    

    public static void main(String args[]) throws IOException {
        Console console = System.console();
        String option = (args != null && args.length>0)?args[0]:OPTION_DFA_TO_C_CODE;
                       
        if (option.equalsIgnoreCase(OPTION_DFA_TO_C_CODE)) {
            showDFA(console);
        } else if (option.equalsIgnoreCase(OPTION_NFA_TO_DFA)) {
            showNFAConversion(console);
        } else {
            console.printf("\nOpcion NO valida, escriba:\n java -jar cinvestav.compu.statemachine.jar dfa\n");
            console.printf("Si se desea crear una DFA y obtener su codigo en C equivalente\n");
            console.printf("o Escriba\n: java -jar cinvestav.compu.statemachine.jar nfac\n");
            console.printf("Si se desea crear una NFA y obtener su DFA equivalente\n\n");
        }

    }

    private static Integer stringToInteger(String selectedOption, boolean exceptionIfNotValid) {
        Integer theNumber;

        try {
            theNumber = Integer.parseInt(selectedOption);
        } catch (Exception ex) {

            if (exceptionIfNotValid) {
                throw new RuntimeException(ex);
            } else {
                theNumber = null;
            }

        }

        return theNumber;
    }

    public static void showDFA(Console console) throws IOException {
        Integer totalStates, initialState;
        int[][] transitionMatrix;
        List<String> alphabet;
        List<Integer> finalStates;
        boolean exitNow = true;

        console.printf("----------------- Construir una DFA y generar el codigo equivalente en C ----------------- \n\n");

        alphabet = askForAlphabet(console);

        //se define matriz de transicion
        totalStates = stringToInteger(console.readLine("Introduzca el total de estados:"), false);
        console.printf("\nIntroduzca EL SUBINDICE del estado que corresponde a los estados y simbolos que se le pidan.");
        console.printf("Ejemplo, si su DFA tiene el alfabeto {a,b} y se le pide delta[qo,a] y a este transicion le corresponde q2,");
        console.printf("usted debe introducir SOLO \"2\" (sin las comillas)\n\n");
        transitionMatrix = new int[totalStates][alphabet.size()];
        for (int stateIndex = 0; stateIndex < totalStates; stateIndex++) {

            for (int symbolIndex = 0; symbolIndex < alphabet.size(); symbolIndex++) {

                String stateAsString = console.readLine("delta(q" + stateIndex + ", " + alphabet.get(symbolIndex) + ")= q");
                transitionMatrix[stateIndex][symbolIndex] = stringToInteger(stateAsString, false);
            }
        }

        console.printf("\n");
        initialState = askForInitialState(console, totalStates);
        finalStates = askForFinalStates(console, totalStates);

        DFAutomata dfa = new DFAutomata(alphabet, transitionMatrix, initialState, finalStates);

        console.printf("La DFA generada es:\n" + dfa);
        String pathCFile = console.readLine("Introduzca la ruta a guardar el archivo equivalente en C (incluyase el nombre del archivo):\n");
        DFAHelper.buildEquivalentCodeInC(dfa, pathCFile);
        console.printf("Se ha generado el codigo equivalente en C y se ha guardado en:" + pathCFile);

        /*
        while (exitNow) {
            console.printf("\nIntroduzca cadena para evaluar si es aceptable para la DFA(SEPARAR con comas cada simbolo):\n");
            console.printf("Recuerdese que el alfabeto es:" + dfa.getAlphabet() + "\n");
            String stringToTest = console.readLine("Cadena a evaluar:");
            stringToTest = stringToTest.replace(" ", "");
            boolean isAceptable = dfa.isAcceptable(stringToTest, ",");
            console.printf("la cadena:" + stringToTest + " " + (isAceptable ? "" : "NO") + " ES aceptable para la DFA \n");

            //console.printf(" \n--------------------\n" + dfa);
        }*/
    }

    public static void showNFAConversion(Console console) {
        Integer totalStates, initialState;
        List<List<Set<Integer>>> transitionMatrix;
        List<String> alphabet;
        List<Integer> finalStates;
        boolean exitNow = true;

        console.printf("\n\n----------------- Construir una NFA y convertirla a DFA ----------------- \n\n");

        alphabet = askForAlphabet(console);

        //se define matriz de transicion

        totalStates = stringToInteger(console.readLine("Introduzca el total de estados de la NFA:"), false);
        console.printf("\nIntroduzca LOS SUBINDICES de los estados separados por comas para cada estado y simbolo que se le pida");
        console.printf("Ejemplo, si su DFA tiene el alfabeto {a,b} y se le pide delta[qo,a] y a este transicion le corresponde {q2,q1}");
        console.printf("usted debe introducir SOLO \"2,1\" (sin las comillas)\n\n");
        console.printf("Use ENTER para indicar conjuntos vacios\n\n");
        transitionMatrix = new ArrayList<List<Set<Integer>>>();

        for (int stateIndex = 0; stateIndex < totalStates; stateIndex++) {
            List<Set<Integer>> rowTransitionMatrix;

            rowTransitionMatrix = new ArrayList<Set<Integer>>();
            for (int symbolIndex = 0; symbolIndex < alphabet.size(); symbolIndex++) {
                Set<Integer> statesByTransition;
                boolean isValidState = false;
                statesByTransition = new HashSet<Integer>();

                while (!isValidState) {
                    String statesAsString = console.readLine("delta(q" + stateIndex + ", " + alphabet.get(symbolIndex) + ")= ");
                    statesAsString = statesAsString.replace(" ", "");

                    if (statesAsString.length() == 0) {
                        isValidState = true;
                    } else {

                        for (String aStateAsString : statesAsString.split(",")) {
                            Integer aStateOfTransition = stringToInteger(aStateAsString, false);

                            if (aStateOfTransition == null) {
                                console.printf("\nEl estado:" + aStateAsString + " no es valido, introduzca solo numeros\n");
                                isValidState = false;
                                break;
                            } else {
                                statesByTransition.add(aStateOfTransition);
                                isValidState = true;
                            }
                        }
                    }
                }

                rowTransitionMatrix.add(statesByTransition);
            }

            transitionMatrix.add(rowTransitionMatrix);
        }

        console.printf("\n");
        initialState = askForInitialState(console, totalStates);
        finalStates = askForFinalStates(console, totalStates);
        console.printf("\n\n");

        NFAutomata nfa = new NFAutomata(alphabet, transitionMatrix, initialState, new HashSet<Integer>(finalStates));
        DFAutomata dfa = NFAHelper.convertToDFA(nfa);

        console.printf(" -----------  DFA equivalente ------------- \n\n");
        console.printf(dfa.toString(true));
        console.printf(" \n\n------------------------------------------ \n\n");

        while (exitNow) {

            console.printf("\nIntroduzca cadena para evaluar si es aceptable para la NFA y la DFA equivalente(SEPARAR con comas cada simbolo):\n");
            console.printf("Recuerdese que el alfabeto es:" + nfa.getAlphabet() + "\n");
            String stringToTest = console.readLine("Cadena a evaluar:");
            stringToTest = stringToTest.replace(" ", "");

            boolean isAceptableForNFA = nfa.isAcceptable(stringToTest, ",");
            boolean isAceptableForDFA = dfa.isAcceptable(stringToTest, ",");

            console.printf("la cadena:" + stringToTest + " " + (isAceptableForNFA ? "" : "NO") + " ES aceptable para la NFA \n");
            console.printf("la cadena:" + stringToTest + " " + (isAceptableForDFA ? "" : "NO") + " ES aceptable para la DFA equivalente \n");

            //console.printf(" \n--------------------\n" + dfa);
        }

    }

    private static List<String> askForAlphabet(Console console) {
        String alphabetAsString;
        List<String> alphabet;

        //se configura alfabeto
        alphabetAsString = console.readLine("Introduzca alfabeto(separado por comas):");
        alphabet = Arrays.asList(alphabetAsString.replace(" ", "").split(","));

        return alphabet;
    }

    private static List<Integer> askForFinalStates(Console console, int totalStates) {
        String finalStatesAsString;
        List<Integer> finalStates;
        boolean isValid = false;

        //se definen los subindices de los estados finales
        finalStates = new ArrayList<Integer>();
        while (!isValid) {

            finalStatesAsString = console.readLine("\nIntroduzca los subindices(solo numeros) de los estados finales, estos deben estar SEPARADOS por comas Y deben de estar en el conjunto {0,..., " + (totalStates - 1) + "}:");
            for (String numberAsString : finalStatesAsString.replace(" ", ",").split(",")) {

                Integer stateIndex = stringToInteger(numberAsString, false);
                if (stateIndex == null) {
                    console.printf("el estado:" + numberAsString + " no es un numero valido!, vuelva a introducir los estados finales\n");
                    break;
                } else {
                    finalStates.add(stateIndex);
                }

            }
            isValid = true;
        }
        return finalStates;
    }

    private static Integer askForInitialState(Console console, int totalStates) {
        String initialStateAsString;
        Integer initialState = null;
        boolean isValidNumber = false;

        while (!isValidNumber) {
            //se definen el estado inicial
            initialStateAsString = console.readLine("Introduzca subindice(solo el numero) del estado inicial, este debe estar en el conjunto {0,..., " + (totalStates - 1) + "}:");
            initialState = stringToInteger(initialStateAsString, false);
            if (initialState == null) {
                console.printf("El subindice del estado inicial no es valido, debe introducir solo numeros\n");
            } else {
                isValidNumber = true;
            }
        }

        return initialState;
    }
}
