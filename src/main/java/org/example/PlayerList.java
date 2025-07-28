package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * В настоящий класс вынесено изначальное создание списка игроков, чтобы не повторять эту операцию дважды,
 * в классе Main и в DiceTournamentAnalyzerTest. В каждом из этих классов просто создаётся своя ссылка
 * на данную коллекцию, и работа производится через неё.
 */
public class PlayerList {

    public static List<Player> players = new ArrayList<>();

    static {
        players.add(new Player("Alice", new Dice(), 1000));
        players.add(new Player("Bob", new Dice(), 750));
        players.add(new Player("Charlie", new Dice(), 500));
        players.add(new Player("David", new Dice(), 1200));
        players.add(new Player("Eva", new Dice(), 300));
    }

}
