package cinvestav.compu.statemachine.helpers;


public class CodeTemplateForC {
    
    static final String ALPHABET_SIZE_TOKEN = "ALPHABET_SIZE_TOKEN";
    static final String ALPHABET_TOKEN = "ALPHABET_TOKEN";
    static final String STATES_SIZE_TOKEN = "STATES_SIZE_TOKEN"; 
    static final String TRANSITION_MATRIX_TOKEN = "TRANSITION_MATRIX_TOKEN";
    static final String INITIAL_STATE_TOKEN = "INITIAL_STATE_TOKEN";
    static final String FINAL_STATES_TOKEN = "FINALSTATES_TOKEN";
    static final String FINAL_STATES_SIZE_TOKEN = "FINALS_SIZE_TOKEN";
    static final String LESSTHAN_SYMBOL = "LESSTHAN_SYMBOL";
    static final String GREATHERTHAN_SYMBOL = "GREATHERTHAN_SYMBOL";
    
    static final String CODE_TEMPLATE = "\n" +
"    #include " + LESSTHAN_SYMBOL + "stdio.h" + GREATHERTHAN_SYMBOL + "\n" +
"    #include " + LESSTHAN_SYMBOL + "string.h" + GREATHERTHAN_SYMBOL + "\n" +
"\n" +
"    char alphabet[" + ALPHABET_SIZE_TOKEN + "] = {" + ALPHABET_TOKEN + "};\n" + 
"    char inputString[100];\n" +    
"    int transitionMatrix[" + STATES_SIZE_TOKEN + "][" + ALPHABET_SIZE_TOKEN + "] = {" + TRANSITION_MATRIX_TOKEN + "};\n" +
"    int initialState = " + INITIAL_STATE_TOKEN + ";\n" +
"    int finalStates[" + FINAL_STATES_SIZE_TOKEN + "] = {" + FINAL_STATES_TOKEN + "};\n" +
"\n" +
"    int isInArray(int arraySize, int theArray[], int elementToSearch){\n" +
"      int index, found = 0;\n" +
"\n" +
"      for(index =0; index < arraySize; index++){\n" +
"         if(elementToSearch == theArray[index]){\n" +
"            found = 1;\n" +
"            break;\n" +
"         }\n" +
"      }\n" +
"      \n" +
"      return found;\n" +
"    }\n" +
"\n" +
"    int getIndexOfSymbol(char symbol){\n" +
"       int symbolIndex = -1;\n" +
"       int elementIndex = 0;\n" +
"       int alphabetSize = strlen(alphabet);\n" +
"       \n" +
"       for(elementIndex; elementIndex < alphabetSize; elementIndex++){\n" +
"     \n" +
"          if(alphabet[elementIndex]== symbol){\n" +
"             symbolIndex = elementIndex;\n" +
"             break;                    \n" +
"          }\n" +
"       }   \n" +
"\n" +
"       return symbolIndex;\n" +
"    }\n" +
"\n" +
"\n" +
"    int delta(int stateIndex, char symbol){\n" +    
"        int symbolIndex;\n" +
"     \n" +
"        symbolIndex = getIndexOfSymbol(symbol);\n" +
"        return transitionMatrix[stateIndex][symbolIndex];\n" +
"    }      \n" +
"\n" +
"    int deltaWord(int stateIndex, char *inputString){\n" +
"      int inputSize = (int)strlen(inputString);\n" +
"      int symbolIndex = 0, state = stateIndex;  \n" +
"\n" +
"      for(symbolIndex = 0; symbolIndex < inputSize; symbolIndex++){\n" +    
"           state = delta(state, inputString[symbolIndex]);\n" +
"      }\n" +
"\n" +
"      return state;\n" +
"    }\n" +
"\n" +
"    void main()\n" +
"    {  \n" +
"       int inputStringLength, state;\n" +   
"       int isFinal;\n" +
"          \n" +
"       printf(\"introduce la cadena a evaluar(sin espacios y sin comas):\\n\");\n" +
"       scanf(\"%s\",&inputString);\n" +
"       \n" +
"       inputStringLength = (int)strlen(inputString);\n" +
"       printf(\"La cadena introducida es: %s, su longitud es:%d\\n\", &inputString,inputStringLength);\n" +
"\n" +
"       state = deltaWord(initialState, inputString);\n" +
"       isFinal = isInArray(" + FINAL_STATES_SIZE_TOKEN +  ", finalStates, state);\n" +
"\n" +
"       if(isFinal){\n" +
"          printf(\"La DFA acepta a %s\",inputString);\n" +
"       } else {\n" +
"          printf(\"La DFA NO acepta a %s\",inputString);\n" +
"       }\n" +
"\n" +
"       printf(\"\\n\");\n" +
"    }";

}
