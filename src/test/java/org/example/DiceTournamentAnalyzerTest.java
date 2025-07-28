package org.example;

import java.util.*;

import static org.example.DiceTournamentAnalyzer.ChipCategory.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class DiceTournamentAnalyzerTest {

    @org.junit.jupiter.api.Test
    void getMapOfAverageScores() {

        Player firstPlayer = makePlayer("Paul", 200, 2,2);
        Player secondPlayer = makePlayer("David", 1200, 3,3);
        Player thirdPlayer = makePlayer("Alice", 1200, 4,4);
        Map<Player, Double> averageRolls = analyzerOf(firstPlayer, secondPlayer, thirdPlayer).getMapOfAverageScores();
        assertEquals(4.0, averageRolls.get(thirdPlayer));
        assertEquals(2.0, averageRolls.get(firstPlayer));
        assertEquals(3.0, averageRolls.get(secondPlayer));

    }

    @org.junit.jupiter.api.Test
    void getTopThreeAveragePlayers_whenPlayerListIsEmpty_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();                                       // Given
        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> topThreeAveragePlayers = dta.getTopThreeAveragePlayers();          // When

        assertTrue(topThreeAveragePlayers.isEmpty());                                   // Then

    }

    @org.junit.jupiter.api.Test
    void getTopThreeAveragePlayers_whenFivePlayersExist_thenReturnTopThreePlayers() {

        List<Player> players = PlayerList.players;                                      // Given
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> topThreeAveragePlayers = dta.getTopThreeAveragePlayers();          // When

        assertEquals(3, topThreeAveragePlayers.size());                        // Then
        assertEquals("Jenny", dta.getTopThreeAveragePlayers().getFirst().getName());

    }

    @org.junit.jupiter.api.Test
    void getTheLosers_whenUnluckyPlayerDoesNotExist_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();                                      // Given
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> Losers = dta.getTheLosers();                                 // When

        assertTrue(Losers.isEmpty());                                            // Then

    }

    @org.junit.jupiter.api.Test
    void getTheLosers_whenUnluckyPlayersExist_thenReturnLosers() {

        List<Player> players = new ArrayList<>();                                      // Given
        players.add(makePlayer("McFly", 2000, 1,2,6,1,2,3,6,3,1,6));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> theLosers = dta.getTheLosers();                                 // When

        assertEquals("McFly", theLosers.getFirst().getName());              // Then

    }

    @org.junit.jupiter.api.Test
    void getTheLucky_whenPlayersDoNotExist_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();
        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> theLucky = dta.getTheLucky();

        assertEquals(0, theLucky.size());

    }

    @org.junit.jupiter.api.Test
    void getTheLucky_whenLuckyPlayersDoNotExist_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();
        players.add(makePlayer("McFly", 2000, 1,2,6,1,2,3,6,3,1,6));
        players.add(makePlayer("Jade", 2000, 3,2,6,4,6,4,5,2,1,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> theLucky = dta.getTheLucky();

        assertEquals(0, theLucky.size());

    }

    @org.junit.jupiter.api.Test
    void getTheLucky_whenLuckyPlayersExist_thenReturnLuckyPlayerList() {

        List<Player> players = new ArrayList<>();
        players.add(makePlayer("McFly", 2000, 1,2,6,1,2,3,6,3,1,6));
        players.add(makePlayer("Jade", 2000, 3,2,6,4,6,4,5,2,1,6));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        players.add(makePlayer("Julia", 2000, 6,3,5,6,2,1,6,4,4,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> theLucky = dta.getTheLucky();

        assertEquals(2, theLucky.size());
        assertTrue(Objects.equals(theLucky.getFirst().getName(), "Jenny") &&
                Objects.equals(theLucky.getLast().getName(), "Julia"));

    }

    @org.junit.jupiter.api.Test
    void getPlayersGroupedByChips_whenChipCategoryHasNoPlayers_thenReturnEmptyValueForThatMapKey() {

        List<Player> players = new ArrayList<>();

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        Map<DiceTournamentAnalyzer.ChipCategory, List<Player>> theLucky = dta.getPlayersGroupedByChips();

        assertTrue(theLucky.get(HIGH).isEmpty());
        assertTrue(theLucky.get(MEDIUM).isEmpty());
        assertTrue(theLucky.get(LOW).isEmpty());

    }

    @org.junit.jupiter.api.Test
    void getPlayersGroupedByChips_whenChipCategoryPlayers_thenReturnListOfPlayersForThatMapKey() {

        List<Player> players = new ArrayList<>();
        players.add(makePlayer("McFly", 400, 1,2,6,1,2,3,6,3,1,6));
        players.add(makePlayer("Jade", 600, 3,2,6,4,6,4,5,2,1,6));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        players.add(makePlayer("Julia", 2000, 6,3,5,6,2,1,6,4,4,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        Map<DiceTournamentAnalyzer.ChipCategory, List<Player>> theLucky = dta.getPlayersGroupedByChips();

        assertEquals(2, theLucky.get(HIGH).size());
        assertEquals(1, theLucky.get(MEDIUM).size());
        assertEquals(1, theLucky.get(LOW).size());

        assertEquals("Jenny", theLucky.get(HIGH).getFirst().getName());
        assertEquals("Julia", theLucky.get(HIGH).getLast().getName());
        assertEquals("Jade", theLucky.get(MEDIUM).getFirst().getName());
        assertEquals("McFly", theLucky.get(LOW).getFirst().getName());

    }


    @org.junit.jupiter.api.Test
    void getStablePlayerList_whenNoPlayerExists_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        assertTrue(dta.getStablePlayerList().isEmpty());

    }

    @org.junit.jupiter.api.Test
    void getStablePlayerList_whenAnyStablePlayerExists_thenReturnListOfStablePlayers() {

        List<Player> players = new ArrayList<>();
        players.add(makePlayer("McFly", 400, 1,2,6,1,2,3,6,3,1,6));
        players.add(makePlayer("Jade", 600, 3,2,6,4,6,4,5,2,1,6));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        players.add(makePlayer("Julia", 2000, 6,4,5,6,2,1,6,4,4,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> stablePlayerList = dta.getStablePlayerList();

        assertEquals(2, stablePlayerList.size());
        assertTrue(Objects.equals(stablePlayerList.getFirst().getName(), "Jenny") &&
                Objects.equals(stablePlayerList.getLast().getName(), "Julia"));

    }

    @org.junit.jupiter.api.Test
    void getRiskyPlayerList_whenNoPlayerExists_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        assertTrue(dta.getRiskyPlayerList().isEmpty());

    }

    @org.junit.jupiter.api.Test
    void getRiskyPlayerList_whenAnyRiskyPlayerExists_thenReturnListOfRiskyPlayers() {

        List<Player> players = new ArrayList<>();
        players.add(makePlayer("Perrin", 200, 1,2,6,1,1,3,6,3,1,6));
        players.add(makePlayer("Eeyore", 100, 3,2,6,4,6,4,1,1,1,6));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        players.add(makePlayer("Julia", 2000, 6,4,5,6,2,1,6,4,4,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        List<Player> riskyPlayers = dta.getRiskyPlayerList();

        assertEquals(2, riskyPlayers.size());
        assertTrue(Objects.equals(riskyPlayers.getFirst().getName(), "Perrin") &&
                Objects.equals(riskyPlayers.getLast().getName(), "Eeyore"));

    }

    @org.junit.jupiter.api.Test
    void getBalancedDicePlayerList_whenNoPlayerExists_thenReturnEmptyList() {

        List<Player> players = new ArrayList<>();

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        assertTrue(dta.getBalancedDicePlayerList().isEmpty());

    }

    @org.junit.jupiter.api.Test
    void getLuckIndex_whenPlayerDoNotExist_thenReturnMapWithEmptyValues() {

        List<Player> players = new ArrayList<>();                                            // Given

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        Map<Player, Double> luckIndexMap = dta.getLuckIndex();                               // When

        assertEquals(0, luckIndexMap.size());                                       // Then

    }

    @org.junit.jupiter.api.Test
    void getLuckIndex_whenPlayersExist_thenReturnMapOfLuckIndicesForEachPlayer() {

        List<Player> players = new ArrayList<>();                                           // Given
        players.add(makePlayer("Perrin", 200, 1,2,6,1,1,3,6,3,1,6));
        players.add(makePlayer("Eeyore", 100, 3,2,6,4,6,4,1,1,1,6));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        players.add(makePlayer("Julia", 2000, 6,4,5,6,2,1,6,4,4,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        Map<Player, Double> luckIndexMap = dta.getLuckIndex();                               // When

        assertEquals(4, luckIndexMap.size());                                       // Then

        Set<Player> keys = luckIndexMap.keySet();

        for (Player key : keys) {

            switch (key.getName()) {

                case "Perrin":
                    assertEquals(100.0, luckIndexMap.get(key), 0.01);
                    break;

                case "Eeyore":
                    assertEquals(56.7, luckIndexMap.get(key), 0.01);
                    break;

                case "Jenny":
                    assertEquals(2000.0, luckIndexMap.get(key), 0.01);
                    break;

                case "Julia":
                    assertEquals(1466.7, luckIndexMap.get(key), 0.01);
                    break;

            }

        }

    }

    @org.junit.jupiter.api.Test
    void getMaxMathVariancePlayer_whenNoPlayerExists_thenReturnNull() {

        List<Player> players = new ArrayList<>();                                           // Given

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        Player maxVariancePlayer = dta.getMaxMathVariancePlayer();                          // When

        assertNull(maxVariancePlayer);                                            // Then

    }

    @org.junit.jupiter.api.Test
    void getMaxMathVariancePlayer_whenPlayersExist_thenReturnPlayerWithMaxVariance() {

        List<Player> players = new ArrayList<>();                                           // Given
        players.add(makePlayer("Joker", 1500, 6,1,6,1,6,6,1,6,1,1));
        players.add(makePlayer("Eeyore", 100, 1,2,1,3,2,4,1,1,1,2));
        players.add(makePlayer("Jenny", 2000, 6,6,6,6,6,6,6,6,6,6));
        players.add(makePlayer("Julia", 2000, 6,4,5,6,2,1,6,4,4,6));

        DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer(players);

        Player maxVariancePlayer = dta.getMaxMathVariancePlayer();                // When

        assertEquals("Joker", maxVariancePlayer.getName());

    }

    private Player makePlayer(String name, int chips, int... rolls) {

        Player p = new Player(name, new Dice(), chips);
        Arrays.stream(rolls).forEach(r -> p.getRollsHistory().add(r));
        return p;

    }

    private DiceTournamentAnalyzer analyzerOf(Player... players) {
        return new DiceTournamentAnalyzer(Arrays.asList(players));
    }

}
