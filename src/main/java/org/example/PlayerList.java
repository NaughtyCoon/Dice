package org.example;

import java.util.List;

/**
 * В настоящий класс вынесено изначальное создание списка игроков, чтобы не повторять эту операцию дважды,
 * в классе Main и в DiceTournamentAnalyzerTest. В каждом из этих классов просто создаётся своя ссылка
 * на данную коллекцию, и работа производится через неё.
 */
public class PlayerList {

    public static List<Player> players = List.of(
            new Player("Alice", new Dice(), 1000),
            new Player("Bob", new Dice(), 750),
            new Player("Charlie", new Dice(), 500),
            new Player("David", new Dice(), 1200),
            new Player("Eva", new Dice(), 300)
    );

}
