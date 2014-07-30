package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.exception.HSInvalidPlayerIndexException;
import com.hearthsim.util.tree.HearthTreeNode;

public class MindControl extends SpellCard {
	
	/**
	 * Constructor
	 * 
	 * @param hasBeenUsed Whether the card has already been used or not
	 */
	public MindControl(boolean hasBeenUsed) {
		super("Mind Control", (byte)10, hasBeenUsed);
	}

	/**
	 * Constructor
	 * 
	 * Defaults to hasBeenUsed = false
	 */
	public MindControl() {
		this(false);
	}

	@Override
	public Object deepCopy() {
		return new MindControl(this.hasBeenUsed_);
	}

	/**
	 * 
	 * Use the card on the given target
	 * 
	 * Take control of 1 enemy minion
	 * 
	 * @param thisCardIndex The index (position) of the card in the hand
	 * @param playerIndex The index of the target player.  0 if targeting yourself or your own minions, 1 if targeting the enemy
	 * @param minionIndex The index of the target minion.
	 * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
	 * 
	 * @return The boardState is manipulated and returned
	 */
	@Override
	protected HearthTreeNode use_core(
			int targetPlayerIndex,
			Minion targetMinion,
			HearthTreeNode boardState,
			Deck deckPlayer0,
			Deck deckPlayer1)
		throws HSInvalidPlayerIndexException
	{
		if ((targetMinion instanceof Hero) || targetPlayerIndex == 0) {
			return null;
		}

		HearthTreeNode toRet = super.use_core(targetPlayerIndex, targetMinion, boardState, deckPlayer0, deckPlayer1);
		if (toRet != null) {
			toRet.data_.removeMinion_p1(targetMinion);
			toRet.data_.placeMinion(0, targetMinion);
		}
		return toRet;
	}
}
