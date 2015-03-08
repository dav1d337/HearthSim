package com.hearthsim.event.deathrattle;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class DeathrattleDamageAllMinions extends DeathrattleAction {

    private final byte damage_;

    public DeathrattleDamageAllMinions(byte damage) {
        damage_ = damage;
    }

    @Override
    public HearthTreeNode performAction(Card origin,
                                        PlayerSide playerSide,
                                        HearthTreeNode boardState,
                                        Deck deckPlayer0,
                                        Deck deckPlayer1,
                                        boolean singleRealizationOnly) throws HSException {
        HearthTreeNode toRet = super.performAction(origin, playerSide, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
        if (toRet != null) {
            PlayerModel currentPlayer = toRet.data_.modelForSide(PlayerSide.CURRENT_PLAYER);
            PlayerModel waitingPlayer = toRet.data_.modelForSide(PlayerSide.WAITING_PLAYER);
            for (Minion aMinion : waitingPlayer.getMinions()) {
                toRet = aMinion.takeDamage(damage_, playerSide, PlayerSide.WAITING_PLAYER, toRet, deckPlayer0, deckPlayer1, false, false);
            }
            for (Minion aMinion : currentPlayer.getMinions()) {
                toRet = aMinion.takeDamage(damage_, playerSide, PlayerSide.CURRENT_PLAYER, toRet, deckPlayer0, deckPlayer1, false, false);
            }
        }
        return toRet;
    }
}
