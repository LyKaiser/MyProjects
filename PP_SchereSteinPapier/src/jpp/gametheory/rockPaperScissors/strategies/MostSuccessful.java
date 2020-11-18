package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IReward;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;
import jpp.gametheory.rockPaperScissors.RPSReward;

import java.util.List;
import java.util.Set;

public class MostSuccessful implements IStrategy<RPSChoice> {
    private final IStrategy<RPSChoice> alternate;
    private final IReward<RPSChoice> reward;

    public MostSuccessful(IStrategy<RPSChoice> alternate, IReward<RPSChoice> reward) {
        this.alternate = alternate;
        this.reward = reward;
    }

    @Override
    public String name() {
        return "Most Successful Choice (Alternate: " + alternate + ")";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        int counterS = 0;
        int counterR = 0;
        int counterP = 0;
        for (IGameRound<RPSChoice> previousR : previousRounds) {
            Set<IPlayer<RPSChoice>> pl = previousR.getPlayers();
            for (IPlayer<RPSChoice> pla : pl) {
                int rew = reward.getReward(pla, previousR);
                RPSChoice choice = previousR.getChoice(pla);
                if (choice.equals(RPSChoice.SCISSORS)) {
                    counterS += rew;
                } else if (choice.equals(RPSChoice.ROCK)) {
                    counterR += rew;
                } else {
                    counterP += rew;
                }
            }
        }
        if (counterS > counterR && counterS > counterP) {
            return RPSChoice.SCISSORS;
        } else if (counterR > counterS && counterR > counterP) {
            return RPSChoice.ROCK;
        } else if (counterP > counterS && counterP > counterR) {
            return RPSChoice.PAPER;
        } else {
            return alternate.getChoice(player, previousRounds);
        }
    }

    @Override
    public String toString() {
        return name();
    }
}
