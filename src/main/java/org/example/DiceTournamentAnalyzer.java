package org.example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiceTournamentAnalyzer {

    public enum ChipCategory { HIGH, MEDIUM, LOW }

    public Map<Player, Double> mapOfAverageScores(List<Player> list) {
        return
                list.stream()
                        .collect(Collectors.toMap(
                                player -> player,
                                this::playerAverageScore
                        ));
    }

    public List<Player> topThreeAveragePlayers(List<Player> list) {

        return
                mapOfAverageScores(list).entrySet().stream()
                        .sorted(Map.Entry.<Player, Double>comparingByValue().reversed())
                        .limit(3)
                        .map(Map.Entry::getKey)
                        .toList();

    }

    public List<Player> showTheLosers(List<Player> list) {
        return
                list.stream()
                        .filter(e->evaluateFate(e,1,3))
                        .toList();
    }

    public List<Player> showTheLucky(List<Player> list) {
        return
                list.stream()
                        .filter(e->evaluateFate(e,6,4))
                        .toList();
    }

    public Map<ChipCategory, List<Player>> groupPlayersByChips(List<Player> list) {

        Map<ChipCategory, List<Player>> result = new LinkedHashMap<>();

        // Создадим порядок чтобы выводилась сначала HIGH, потом MEDIUM, потом LOW, а не как попало.
        result.put(ChipCategory.HIGH, new ArrayList<>());
        result.put(ChipCategory.MEDIUM, new ArrayList<>());
        result.put(ChipCategory.LOW, new ArrayList<>());

        list.forEach(player -> {

            int chips = player.getChips();

            if (chips > 1000) {
                result.get(ChipCategory.HIGH).add(player);
            } else if (chips >= 500) {
                result.get(ChipCategory.MEDIUM).add(player);
            } else {
                result.get(ChipCategory.LOW).add(player);
            }

        });

        return result;

    }

    public List<Player> getStablePlayerList(List<Player> list) {
        return
                list.stream()
                        .filter(player -> player.getRollsHistory().stream()
                                .filter(e -> e >= 4)
                                .count() >= 8)
                        .toList();
    }

    private double playerAverageScore(Player player) {

        if (player.getRollsHistory().isEmpty()) {
            return 0.0;
        }

        return
                player.getRollsHistory().stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);

    }

    private boolean evaluateFate(Player player, int value, int quantity) {

        return
                player.getRollsHistory().stream()
                        .filter(e -> e == value)
                        .count() >= quantity;

    }

}
