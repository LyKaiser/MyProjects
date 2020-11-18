package jpp.gametheory.rockPaperScissors;

import jpp.gametheory.generic.*;


public class RPSReward implements IReward<RPSChoice> {


    @Override
    public int getReward(IPlayer<RPSChoice> player, IGameRound<RPSChoice> gameRound) throws IllegalArgumentException, NullPointerException {
        if (player == null || gameRound == null) {
            throw new NullPointerException();
        }
        if (gameRound.getPlayerChoices().get(player) == null) {
            IllegalException x = new IllegalException();
            x.error = "Spieler hat nicht mitgespielt!";
            throw x;
        }

        int counter = 0;
        RPSChoice s = RPSChoice.SCISSORS;
        RPSChoice r = RPSChoice.ROCK;
        RPSChoice p = RPSChoice.PAPER;
        RPSChoice choice = gameRound.getChoice(player);

        for (IPlayer x : gameRound.getOtherPlayers(player)) {
            RPSChoice choiceA = gameRound.getChoice(x);
            if (choice.equals(s)) {
                if (choiceA.equals(r)) {
                    counter -= 1;

                } else if (choiceA.equals(p)) {
                    counter += 2;
                }
            }
            if (choice.equals(r)) {
                if (choiceA.equals(s)) {
                    counter += 2;

                } else if (choiceA.equals(p)) {
                    counter -= 1;
                }
            }
            if (choice.equals(p)) {
                if (choiceA.equals(s)) {
                    counter -= 1;
                } else if (choiceA.equals(r)) {
                    counter += 2;
                }
            }
        }

        return counter;
    }
}
