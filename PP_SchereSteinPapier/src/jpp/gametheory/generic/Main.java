package jpp.gametheory.generic;

import jpp.gametheory.rockPaperScissors.RPSChoice;
import jpp.gametheory.rockPaperScissors.RPSReward;
import jpp.gametheory.rockPaperScissors.strategies.CircleChoice;
import jpp.gametheory.rockPaperScissors.strategies.MostCommon;
import jpp.gametheory.rockPaperScissors.strategies.MostSuccessful;
import jpp.gametheory.rockPaperScissors.strategies.SingleChoice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Gebe einen Spieler an!");
        String i = sc.nextLine();
        Set<Player> player = new HashSet<>();
        player.add(new Player(i, new SingleChoice(RPSChoice.SCISSORS)));
        String s = "Y";
        while (s.equals("Y")) {
            System.out.println("Weiterer Spieler? Y/N");
            String st = sc.nextLine();
            if (st.equals("Y")) {
                System.out.println("Gebe einen Spieler an!");
                String j = sc.nextLine();
                player.add(new Player(j, new CircleChoice()));
            } else if (st.equals("N")){
                if (player.size()<=1){
                    System.out.println("Es müssen mindestens zwei Spieler sein!");
                }else s = "N";
            }else System.out.println("Bitte Y (für ja) oder N (für Nein) eingeben!");
        }
        Game game = new Game(player, new RPSReward());
        game.playNRounds(10);
        System.out.println(game.toString());

    }
}
