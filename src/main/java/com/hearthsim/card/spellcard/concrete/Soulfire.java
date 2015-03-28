package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellDamage;
import com.hearthsim.card.spellcard.SpellRandom;
import com.hearthsim.event.EffectMinionAction;
import com.hearthsim.event.EffectMinionSpellDamage;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.HearthAction;
import com.hearthsim.util.IdentityLinkedList;
import com.hearthsim.util.tree.HearthTreeNode;
import com.hearthsim.util.tree.RandomEffectNode;

import java.util.ArrayList;
import java.util.Collection;

public class Soulfire extends SpellDamage implements SpellRandom {

    public Soulfire() {
        super();
    }

    @Deprecated
    public Soulfire(boolean hasBeenUsed) {
        this();
        this.hasBeenUsed = hasBeenUsed;
    }

    // TODO: find a better way to do this...
    private HearthTreeNode callSuperUseOn(
            PlayerSide side,
            Minion targetMinion,
            HearthTreeNode boardState,
            boolean singleRealizationOnly)
        throws HSException {
        return super.useOn(side, targetMinion, boardState, singleRealizationOnly);
    }

    @Override
    public Collection<HearthTreeNode> createChildren(PlayerSide originSide, int originIndex, HearthTreeNode boardState) throws HSException {
        PlayerModel currentPlayer = boardState.data_.modelForSide(originSide);

        int totalTargets = currentPlayer.getHand().size();

        HearthTreeNode newState;
        ArrayList<HearthTreeNode> children = new ArrayList<>();
        switch (totalTargets) {
            case 0: // shouldn't happen
                break;
            case 1: // only soulfire; remove it and carry on
                newState = new HearthTreeNode(boardState.data_.deepCopy());
                newState.data_.modelForSide(originSide).getHand().remove(originIndex);
                children.add(newState);
                break;
            default: // more than 1 card in hand; create one child for each non-Soulfire card
                for (int i = 0; i < totalTargets; i++) {
                    if (i == originIndex) {
                        continue; // don't target Soulfire with Soulfire
                    }

                    newState = new HearthTreeNode(boardState.data_.deepCopy());
                    newState.data_.modelForSide(originSide).getHand().remove(originIndex);
                    newState.data_.modelForSide(originSide).getHand().remove(i < originIndex ? i : i - 1);
                    children.add(newState);
                }
                break;
        }
        return children;
    }
}
