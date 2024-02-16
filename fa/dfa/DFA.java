package fa.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    
    //Instantiating our hashsets for regular states, final states and our alphabet
    private LinkedHashMap<String, DFAState> states;
    private LinkedHashMap<String, DFAState> finalStates;
    private LinkedHashSet<Character> alphabet;

    private LinkedHashMap<Character, DFAState> transitions;
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
    if (states.containsValue(newState) || finalStates.containsValue(newState) || (start != null && start.getName().equals(name))) {
        return false; // State already exists
    }

    // If the state doesn't exist, add it to the set of states
    states.put(name, newState);
    return true;

    }

    @Override
    public boolean setFinal(String name) {
        //adding it to our final states
       // return finalStates.add(new DFAState(name));
        
       DFAState existingState = getDFAState(name);
       existingState = states.get(name);
       // Check if the state exists before marking it as final
//       if (existingState != null) {
//           return finalStates.add(existingState);
//       }
   
       // If the state doesn't exist, return false
       return false;
    }

    @Override   
    public boolean setStart(String name) {
        // Check if the start state has already been set
        if (start != null) {
            return false; // Start state has already been set
        }
    
        // Create a new start state and set its name
        start = new DFAState(name);
        states.put(name, start); // Add the start state to the set of states
    
        return true;
    
    }

    @Override
    public void addSigma(char symbol) {
        //adds a new character to alphabet
        alphabet.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        if(s.length() == 0 || start == null){
            return false;
        }
        return accepts(s, start);
    }

    private boolean accepts(String s, DFAState state){
        if(state == null){
            return false;
        }else if(s.length() == 1){
            if(finalStates.containsValue(state.getTransitionState(s.charAt(0)))){
            return true;
        }
        return false;
        }else{
            return accepts(s.substring(1), (DFAState) state.getTransitionState(s.charAt(0)));
        }
    }



    @Override
    public Set<Character> getSigma() {
        
        Set<Character> sigmaSet = new HashSet<>(); //creating a new set to return
        
        for(char sigma: alphabet){
        sigmaSet.add(sigma); //looping thru alphabet to add to new set
        }

        return sigmaSet; //returning alphabet in new set

    }

    @Override
    public State getState(String name) {
        //checking all three places(regular states, start and final) a state can be 
        //to see if it's created
        DFAState getState = new DFAState(name); //creating a new state to check against hashset instead of string

        if(states.containsValue(getState) == true){
            return (State) getState;
        } else if(finalStates.containsValue(getState) == true){
            return (State) getState;
        } else if (start.getName() == name){
            return (State) getState;
        }

        return null;
        
    }
    

    @Override
    public boolean isFinal(String name) {
        DFAState isFinal = new DFAState(name);
        //checking if state name given is a final state and then return true or false
       
        return isFinal != null && finalStates.containsValue(isFinal);
     /*   
        if(finalStates.contains(isFinal) == true){
            return true;
        }
        System.out.println(name + " is not a final state");
        return false;
        */
    }

    @Override
    public boolean isStart(String name) {
    // Check if the start state is not null and has the provided name
    return start != null && start.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        
/* 
        Iterator<DFAState> i = states.iterator();

        while(i.hasNext()){
            if(!((State) i).getName().equals(fromState) || !((State) i).getName().equals(toState)){
                return false;
            }
        }

        Iterator<DFAState> h = finalStates.iterator();

        while(h.hasNext()){
            if(!((State) h).getName().equals(fromState) || !((State) h).getName().equals(toState)){
                return false;
            }
        }
        
        if( start.getName() != fromState || start.getName() != toState){
            return false;
        }

        if (!alphabet.contains(onSymb)) {
            return false;
        }
*/

        DFAState DFAFrom = getDFAState(fromState);
        DFAState DFAto = getDFAState(toState);


        if (DFAFrom != null && DFAto != null && alphabet.contains(onSymb)) {
            DFAFrom.addStateTransition(onSymb, DFAto);
            return true;
        }

        return false;
        
    }

    private DFAState getDFAState(String stateName) {
       /* 
        for (DFAState state : states) {
            if (state.getName().equals(stateName)) {
                return state;
            }
        }
        for (DFAState finalState : finalStates) {
            if (finalState.getName().equals(stateName)) {
                return finalState;
            }
        }
        if (start != null && start.getName().equals(stateName)) {
            return start;
        }
        return null;   
        
        */
        return states.get(stateName);
    
    }


    @Override
    public DFA swap(char symb1, char symb2) {
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
    
        return newDFA;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();

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
                DFAState nextState = (DFAState) transitions.getOrDefault(new HashMap<>(), entry.getValue()).get(symb);
    
                if (nextState == null) {
                    str.append("err\t");
                } else {
                    str.append(nextState.getName()).append("\t");
                }
            }
            str.append("\n");
        }
    
        str.append("\n");
        if (start != null) {
            str.append("q0 = ").append(start).append("\n");
        } else {
            str.append("q0 = \n");
        }
    
        str.append("F = { ").append(finalStates.keySet().toString().replace("[", "")
            .replace("]", "")
            .replace(",", ""))
            .append(" }\n");
    
        return str.toString();
    }
    
}
