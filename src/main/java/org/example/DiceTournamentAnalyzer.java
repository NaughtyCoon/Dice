package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс предназначен для анализа статистики игроков в кости и распределения их мест в турнирных таблицах.
 */
public class DiceTournamentAnalyzer {

    public enum ChipCategory { HIGH, MEDIUM, LOW }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает Map, ключом в которой является объект игрока класса Player,
     * а значением - среднее значение его бросков.
     */
    public Map<Player, Double> getMapOfAverageScores(List<Player> list) {
        return
                list.stream()
                        .collect(Collectors.toMap(
                                player -> player,
                                this::playerAverageScore
                        ));
    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает коллекцию из трёх самых успешных игроков,
     * отсортированных по убыванию среднего значения бросков.
     */
    public List<Player> getTopThreeAveragePlayers(List<Player> list) {

        return
                getMapOfAverageScores(list).entrySet().stream()
                        .sorted(Map.Entry.<Player, Double>comparingByValue().reversed())
                        .limit(3)
                        .map(Map.Entry::getKey)
                        .toList();

    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает коллекцию из самых нерезультативных игроков
     * (три и более раза выпадала единица).
     */
    public List<Player> getTheLosers(List<Player> list) {
        return
                list.stream()
                        .filter(e->evaluateFate(e,1,3))
                        .toList();
    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает коллекцию из самых результативных игроков
     * (четыре и более раза выпадала шестёрка).
     */
    public List<Player> getTheLucky(List<Player> list) {
        return
                list.stream()
                        .filter(e->evaluateFate(e,6,4))
                        .toList();
    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает отсортированный по ключу объект LinkedHashMap, ключом в котором
     * является категория количества фишек (>1000 - HIGH, 500-1000 - MEDIUM, <500 - LOW),
     * а значением - коллекция игроков, попадающих в соответствующую категорию.
     */
    public Map<ChipCategory, List<Player>> getPlayersGroupedByChips(List<Player> list) {

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

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает список игроков, которым 8 или более раз удалось получить значение
     * кубика 4 или больше.
     */
    public List<Player> getStablePlayerList(List<Player> list) {
        return
                list.stream()
                        .filter(player -> player.getRollHistory().stream()
                                .filter(e -> e >= 4)
                                .count() >= 8)
                        .toList();
    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает список игроков, у которых не менее двух раз подряд
     * выпадала единица.
     */
    public List<Player> getRiskyPlayerList(List<Player> list) {
        return
                list.stream()
                        .filter(player -> {
                            List<Integer> history = player.getRollHistory();
                            return IntStream.range(0, history.size() - 1)
                                    .anyMatch(i -> history.get(i) == 1 && history.get(i + 1) == 1);
                        })
                        .toList();
    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает список игроков, у которых каждая сторона кости
     * выпадала хотя бы раз.
     */
    public List<Player> getBalancedDicePlayerList(List<Player> list) {
        return
                list.stream()
                        .filter(player -> (player.getRollHistory().stream()
                                .distinct()
                                .toList()
                                ).size() == 6)
                        .toList();
    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает Map, где ключами являются игроки, а значениями
     * их "индексы удачи", округлённые до 1 знака после запятой.
     */
    public Map<Player, Double> getLuckIndex(List<Player> list) {

        return
                list.stream()
                        .collect(Collectors.toMap(
                                player -> player,
                                player -> {

                                    List<Integer> history = player.getRollHistory();

                                    if (history.isEmpty()) {
                                        return 0.0;
                                    }

                                    return
                                            Math.round(
                                            (history.stream()
                                            .mapToInt(e -> e)
                                            .sum() / 6.0 * player.getChips()))/10.0;

                                }
                        ));

    }

    /**
     * @param list коллекция игроков класса Player
     * @return возвращает игрока (объект Player) с самой большой дисперсией
     * результатов бросков.
     */
    public Optional<Player> getMaxMathVariancePlayer(List<Player> list) {
        return list.stream()
                .max(Comparator.comparingDouble(this::calculateVariance));
    }

    /**
     * Служебный метод.
     * @param player объект класса Player, конкретный игрок.
     * @return возвращает дисперсию конкретного игрока.
     */
    private double calculateVariance(Player player) {

        List<Integer> history = player.getRollHistory();
        if (history.isEmpty()) return 0.0;

        double mu = playerAverageScore(player);

        return history.stream()
                .mapToDouble(e -> Math.pow(e - mu, 2))
                .average()
                .orElse(0.0);

    }

    /**
     * Служебный метод.
     * @param player объект класса Player, конкретный игрок.
     * @return возвращает среднее значение из всех бросков кубика для конкретного игрока.
     */
    private double playerAverageScore(Player player) {

        if (player.getRollHistory().isEmpty()) {
            return 0.0;
        }

        return
                player.getRollHistory().stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);

    }

    /**
     * Служебный метод.
     * @param player объект класса Player, конкретный игрок.
     * @param value значение кубика.
     * @param quantity проверяемое количество выпадений.
     * @return возвращает true, если количество выпадений определённого значения кубика
     * value равно или превосходит quantity.
     */
    private boolean evaluateFate(Player player, int value, int quantity) {

        return
                player.getRollHistory().stream()
                        .filter(e -> e == value)
                        .count() >= quantity;

    }

}
