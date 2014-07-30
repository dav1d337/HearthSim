package com.hearthsim.util.tree;

import com.hearthsim.card.Deck;

/**
 * A tree node that stops AI from picking the "best" outcome from its branches. 
 *
 */
public abstract class StopNode extends HearthTreeNode {
		
	public StopNode(HearthTreeNode origNode) {
		super(origNode.data_, origNode.score_, origNode.depth_);
		children_ = origNode.children_;
		numNodesTried_ = origNode.numNodesTried_;
	}

	public abstract HearthTreeNode finishAllEffects(Deck deck);
}
