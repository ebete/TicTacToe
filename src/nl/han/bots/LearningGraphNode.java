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
    private final int WEIGHT_CUTOFF = -50;
    
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
     * Returns the node with the given value.
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
     * Returns the node with the highest
     * probability of winning in the previous rounds
     * or returns null signalling to do a move that has yet
     * to be created. This happens if the best node has a weight that
     * lies below a certain threshold or no nodes are attached.
     * 
     * @return The node with the highest winning rate or {@code null}.
     */
    public LearningGraphNode calculateBestMove() {
        LearningGraphNode bestNode = null;
        
        for(LearningGraphNode node : _children) {
            if(bestNode == null || bestNode.getWeight() < node.getWeight())
                bestNode = node;
        }

        if(bestNode == null || bestNode.getWeight() < WEIGHT_CUTOFF)
            return null;
        else
            return bestNode;
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
        ++_weight;
        if(_parent != null)
            _parent.winSituation();
    }

    /**
     * Call this when the bot lost the round.
     * This will decrement the weight for each followed node.
     */
    public void loseSituation() {
        --_weight;
        if(_parent != null)
            _parent.loseSituation();
    }
}
