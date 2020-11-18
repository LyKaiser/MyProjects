package jpp.gametheory.generic;

import java.util.*;

public class Game<C extends IChoice> {

    private final Set<IPlayer<C>> players;
    private final IReward<C> reward;
    private final List<IGameRound<C>> rounds = new ArrayList<>();


    public Game(Set<IPlayer<C>> players, IReward<C> reward) throws IllegalArgumentException, NullPointerException {
        if (players == null || reward == null) {
            throw new NullPointerException();
        }
        if (players.size() <= 1) {
            IllegalException e = new IllegalException();
            e.error = "Zu wenig Spieler - min. zwei Spieler!";
            throw e;
        }
        this.players = players;
        this.reward = reward;
    }


    public Set<IPlayer<C>> getPlayers() {
        return players;
    }

    public IGameRound<C> playRound() {

        Map<IPlayer<C>, C> playR = new HashMap<>();
        for (IPlayer<C> player : players) {
            playR.put(player, player.getChoice(rounds));
        }
        IGameRound<C> round = new GameRound<>(playR);
        rounds.add(round);
        return round;
    }

    public void playNRounds(int n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i <= n; i++) {
            playRound();
        }

    }

    public Optional<IGameRound<C>> undoRound() {
        if (rounds.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(rounds.remove(rounds.size() - 1));
    }

    public void undoNRounds(int n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i <= n; i++) {
            undoRound();
        }

    }

    public List<IGameRound<C>> getPlayedRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public int getPlayerProfit(IPlayer<C> player) throws IllegalArgumentException, NullPointerException {
        if (player == null) {
            throw new NullPointerException();
        }
        int rew = 0;
        for (IGameRound<C> round : rounds) {
            if (!(round.getPlayers().contains(player))) {
                IllegalException x = new IllegalException();
                x.error = "Spieler hat nicht mitgespielt!";
                throw x;
            } else {
                rew += reward.getReward(player, round);
            }
        }
        return rew;
    }

    public Optional<IPlayer<C>> getBestPlayer() {
        int gewinn = 0;
        IPlayer<C> gewinner = null;
        for (IPlayer<C> player : players) {
            int gewinnNeu = getPlayerProfit(player);
            if (gewinnNeu > gewinn) {
                gewinn = gewinnNeu;
                gewinner = player;
            } else if (gewinnNeu == gewinn) {
                gewinn = 0;
            }
        }
        if (gewinn == 0) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(gewinner);
        }
    }

    @Override
    public String toString() {
        List<IPlayer<C>> players = new ArrayList<>(this.players);
        players.sort((o1, o2) -> {
            if (getPlayerProfit(o2) == getPlayerProfit(o1)) {
                return o1.getName().compareTo(o2.getName());
            } else if (getPlayerProfit(o2) < getPlayerProfit(o1)) {
                return -1;
            } else {
                return 1;

            }
        });

        StringBuilder s = new StringBuilder();
        for (IPlayer<C> player : players) {
            int profit = getPlayerProfit(player);
            s.append("\n").append(profit).append(" : ").append(player);
        }


        return "Spiel nach " + rounds.size() + " Runden:" + "\n" + "Profit : Spieler"  + s.toString();

    }
}


