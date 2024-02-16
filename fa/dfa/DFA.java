package fa.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import fa.State;
/**
 * This class creates 3 hashsets, 1 hashmap and a DFAState to handle
 * creating a deterministic finite automata. We have the functionality
 * to handle creating a DFA, printing out its five tuple as well as
 * determining if a string is accepted by that machine.
 * @author Pierce Rodriguez and Nolan Stetz
 */
public class DFA implements DFAInterface{

    //Instantiating our hashsets for regular states, final states and our alphabet(sigma)
    private final LinkedHashMap<String, DFAState> states;
    private final LinkedHashMap<String, DFAState> finalStates;
    private final LinkedHashSet<Character> alphabet;

    private final LinkedHashMap<Character, DFAState> transitions;
    //Instantiating our start DFAState
    private DFAState start;

    /**
     * Constructor that initializes empty DFA
     */
    public DFA(){
        states = new LinkedHashMap<>();
        finalStates = new LinkedHashMap<>();
        alphabet = new LinkedHashSet<>();
        transitions = new LinkedHashMap<>();
        start = null;
    }
    @Override
    public boolean addState(String name){
        //adding a new state to our states hashset
        DFAState newState = new DFAState(name);

        // Check if the state already exists
        if (states.containsKey(name)) {
            return false; // State already exists
        }

        // If the state doesn't exist, add it to the set of states
        states.put(name, newState);
        return true;

    }

    @Override
    public boolean setFinal(String name){
        DFAState isFinal = null;
        // Check if the state exists before marking it as final
        if (states.containsKey(name)) {
            isFinal = states.get(name);
            isFinal.createFinal();
            finalStates.put(name, isFinal);
            return true;
        }

        // If the state doesn't exist, return false
        return false;
    }

    @Override
    public boolean setStart(String name) {
        //checking if we have a state with the same name
        if(states.containsKey(name)) {
            start = states.get(name); //setting it as DFAState start
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void addSigma(char symbol) {
        //adds a new character to alphabet
        alphabet.add(symbol);
    }

    @Override
    public boolean accepts(String s){
        if (start == null || s.isEmpty()) {
            return false;
        }
        return accepts(s, start);
    }
    @Override
    public Set<Character> getSigma() {
        return alphabet;

    }

    @Override
    public State getState(String name) {
        //returns the value to which the specified key is mapped, or defaultValue if this map contains no mapping for the key
        return states.getOrDefault(name, null);

    }

    @Override
    public boolean isFinal(String name) {
        //returns true if this map contains a mapping for the specified key
        return finalStates.containsKey(name);
    }

    @Override
    public boolean isStart(String name) {
        // Check if the start state is not null and has the provided name
        return start != null && start.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        //creating states from parameters so we can use in checks
        DFAState DFAFrom = getDFAState(fromState);
        DFAState DFAto = getDFAState(toState);

        //checking if 'to' and 'from' aren't null and if the symb is in alphabet
        if (DFAFrom != null && DFAto != null && alphabet.contains(onSymb)) {
            //if so add transition else return false
            DFAFrom.addStateTransition(onSymb, DFAto);
            return true;
        }

        return false;

    }

    /**
     * Returns a DFAState given state name
     * @param stateName
     * @return DFAState
     */
    private DFAState getDFAState(String stateName) {
        return states.get(stateName);
    }


    @Override
    public DFA swap(char symb1, char symb2) {
        //Creating new dfa state to use in swap
        DFA newDFA = new DFA();

        // Copy states
        for (DFAState state : states.values()) {
            newDFA.addState(state.getName());
            if (finalStates.containsValue(state)) {
                newDFA.setFinal(state.getName());
            }
        }

        // Copy alphabet
        for (Character symbol : alphabet) {
            newDFA.addSigma(symbol);
        }

        // Copy transitions
        for (Map.Entry<String, DFAState> entry : states.entrySet()) {
            String stateName = entry.getKey();
            DFAState fromState = entry.getValue();

            for (Character symbol : alphabet) {
                DFAState toState = (DFAState) fromState.getTransitionState(symbol);
                if (toState != null) {
                    newDFA.addTransition(stateName, toState.getName(), symbol);
                }
            }
        }

        // Swap symbols in transitions
        for (Map.Entry<String, DFAState> entry : states.entrySet()) {
            String stateName = entry.getKey();
            DFAState fromState = entry.getValue();

            for (Character symbol : alphabet) {
                DFAState toState = (DFAState) fromState.getTransitionState(symbol);
                if (toState != null) {
                    fromState.removeTransition(symbol);
                    newDFA.addTransition(stateName, toState.getName(), (symbol == symb1) ? symb2 : (symbol == symb2) ? symb1 : symbol);
                }
            }
        }

        // Copy start state
        if (start != null) {
            newDFA.setStart(start.getName());
        }
        //returns new dfa created at top of method
        return newDFA;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        //Building our states set, alphabet set, and delta for the string
        str.append("Q = { ").append(states.keySet().toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", ""))
                .append(" }\n")
                .append("Sigma = { ").append(getSigma().toString().replace("[", "")
                        .replace("]", "")
                        .replace(",", ""))
                .append(" }\n")
                .append("delta = \n\t");

        for (Character symb : getSigma()) {
            str.append(symb).append("\t");
        }
        str.append("\n");

        for (Map.Entry<String, DFAState> entry : states.entrySet()) {
            String stateName = entry.getKey();
            str.append(getState(stateName)).append("\t");

            for (Character symb : getSigma()) {
                DFAState nextState = (DFAState) transitions.getOrDefault(new HashMap<>(), entry.getValue()).getTransitionState(symb);

                if (nextState == null) {
                    str.append("err\t");
                } else {
                    str.append(nextState.getName()).append("\t");
                }
            }
            str.append("\n");
        }
        str.append("\n");
        //building our starting state string
        if (start != null) {
            str.append("q0 = ").append(start).append("\n");
        } else {
            str.append("q0 = \n");
        }
        //Building our final states string
        str.append("F = { ").append(finalStates.keySet().toString().replace("[", "")
                        .replace("]", "")
                        .replace(",", ""))
                .append(" }\n");

        return str.toString();
    }
    /**
     *  Simulates a DFA on input s to determine
     *  whether the DFA accepts the string. This is done recursively
     * @param s
     * @param state
     * @return true or false depending on whether the string is accepted in the DFA or not.
     */
    public boolean accepts(String s, DFAState state) {
        //checking if our state or string is empty
        if (state == null || s.isEmpty()) {
            return finalStates.containsValue(state);
        }

        // Get the next state based on the transition for the current symbol.
        DFAState nextState = state.getTransitionState(s.charAt(0));

        // Recursively call accepts with the remaining string and the next state.
        return accepts(s.substring(1), nextState);
    }

}
