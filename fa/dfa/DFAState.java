package fa.dfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fa.State;
/**
 * This class extends state to create a DFAState that we will then use
 * in our DFA class. It can addStateTransition, return the next state we can visit 
 * upon a given symbol and set the name of a state.
 * @author Pierce Rodriguez and Nolan Stetz
 */
public class DFAState extends State{

    //instantiating our transition map and DFAState.
    private Map<Character, State> transitionMap;
    protected DFAState newState;

    /**
     * Constructor for our DFAState
     * Gives it a name and creates a HashMap
     * @param name
     */
    public DFAState(String name) {
        super(name);
        transitionMap = new HashMap<>();
    }
    /**
     * Puts a new transition with our parameters(char symbol, State nextState) in our hash map of transitions
     * @param symbol
     * @param nextState
     */
    public void addStateTransition(char symbol, DFAState nextState){
        transitionMap.put(symbol, nextState);
    }

    public State getTransitionState(char symbol){
        return transitionMap.get(symbol);
    }
    /**
     * Returns the next state given a character. We already know what state we 
     * are at.
     * @param symbol
     * @return the next state by finding where the symbol goes based on our
     * hash map. We already know what state we are at.
     */
    public State nextState(char symbol){
        return transitionMap.get(symbol);
    }
    /**
     * Creates a new state when you want to change the name of existing one
     * @param newName
     * @return true or false depending if the state name isn't null
     */
    public boolean setName(String newName) {
        if(newName != null){ //checking if newName is null
            newState = new DFAState(newName); //creating new state
            return true;
        }else{
            return false;
        }
    }
    public void removeTransition(Character symbol) {
       transitionMap.remove(symbol);
    }
    public DFAState get(Character symb) {
        return (DFAState) transitionMap.get(symb);
    }
}
