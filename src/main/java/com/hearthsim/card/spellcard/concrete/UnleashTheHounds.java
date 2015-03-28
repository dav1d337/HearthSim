package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.Hound;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.event.EffectMinionAction;
import com.hearthsim.event.MinionFilterTargetedSpell;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class UnleashTheHounds extends SpellCard {

    @Deprecated
    public UnleashTheHounds(boolean hasBeenUsed) {
        this();
        this.hasBeenUsed = hasBeenUsed;
    }

    public UnleashTheHounds() {
        super();

        this.minionFilter = MinionFilterTargetedSpell.SELF;
    }

    /**
     *
     * Use the card on the given target
     *
     * @param side
     * @param targetMinion
     * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
     * @param singleRealizationOnly
     *
     * @return The boardState is manipulated and returned
     */
    @Override
    protected EffectMinionAction getEffect() {
        if (this.effect == null) {
            this.effect = new EffectMinionAction() {
                @Override
                public HearthTreeNode applyEffect(PlayerSide originSide, Card origin, PlayerSide targetSide, int targetCharacterIndex, HearthTreeNode boardState) throws HSException {
                    PlayerModel currentPlayer = boardState.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
                    PlayerModel waitingPlayer = boardState.data_.modelForSide(PlayerSide.WAITING_PLAYER);
                    int numHoundsToSummon = waitingPlayer.getNumMinions();
                    if (numHoundsToSummon + currentPlayer.getNumMinions() > 7)
                        numHoundsToSummon = 7 - currentPlayer.getNumMinions();
                    for (int indx = 0; indx < numHoundsToSummon; ++indx) {
                        Minion placementTarget = currentPlayer.getNumMinions() > 0 ? currentPlayer.getMinions().getLast() : currentPlayer.getHero();
                        boardState = new Hound().summonMinion(PlayerSide.CURRENT_PLAYER, placementTarget, boardState, false, false);
                    }
                    return boardState;
                }
            };
        }
        return this.effect;
    }
}
