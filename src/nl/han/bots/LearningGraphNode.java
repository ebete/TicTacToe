package nl.han.bots;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * Data structure used for learning bots.
 * Finds the best outcome for a given move.
 * 
 * @author Thom Griffioen
 */
public class LearningGraphNode {
    private LearningGraphNode _parent = null;
    private ArrayList<LearningGraphNode> _children = new ArrayList<>();
    private int _weight = 0;
    private long _value = 0L;

    /**
     * Initializes the node.
     * 
     * @param parent The parent node.
     * @param value The node value.
     */
    public LearningGraphNode(LearningGraphNode parent, long value) {
        _parent = parent;
        _value = value;
    }

    /**
     * Moves to the node with the given value.
     * If it doesn't exist, it will be created.
     * 
     * @param value The node value to find.
     * @return The node with the value.
     */
    public LearningGraphNode moveToNode(long value) {
        for(LearningGraphNode node : _children) {
            if(node.getValue() == value)
                return node;
        }
        
        // Node not found.
        _children.add(new LearningGraphNode(this, value));
        return moveToNode(value);
    }

    /**
     * Calculates the move with the highest
     * probability of winning in the previous rounds.
     * 
     * @return The node with the highest winning rate.
     */
    public LearningGraphNode calculateBestMove() {
        // TODO: Add calculate best move implementation.
        throw new NotImplementedException();
    }

    /**
     * Returns the node weight.
     * 
     * @return The node weight.
     */
    public int getWeight() { return _weight; }

    /**
     * Returns the node value.
     * 
     * @return The node value.
     */
    public long getValue() { return _value; }

    /**
     * Call this when the bot won the round.
     * This will increment the weight for each followed node.
     */
    public void winSituation() {
        // TODO: Add the tree win situation functionality.
        throw new NotImplementedException();
    }

    /**
     * Call this when the bot lost the round.
     * This will decrement the weight for each followed node.
     */
    public void loseSituation() {
        // TODO: Add the tree lose situation functionality.
        throw new NotImplementedException();
    }
    
    /**
     * Export the tree to a file.
     *
     * @return True on success, otherwise false.
     */
    public boolean exportTree() {
        // TODO: Add the tree export functionality.
        throw new NotImplementedException();
    }

    /**
     * Import the tree from a file.
     *
     * @return True on success, otherwise false.
     */
    public boolean importTree() {
        // TODO: Add the tree import functionality.
        throw new NotImplementedException();
    }
}
