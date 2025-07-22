package org.example;

import java.util.List;

public class Main {

    static final String RESET = "\u001B[0m";
    static final String GREEN = "\u001B[32m";

    private static final int throwLimit = 10;

    private static final DiceTournamentAnalyzer dta = new DiceTournamentAnalyzer();

    public static void main(String[] args) {

        List<Player> players = PlayerList.players;

        // Симулируем 10 бросков для каждого игрока

        task("Таблица результатов:");

        players.forEach(player -> {

            for (int i = 0; i < throwLimit; i++) {
                player.throwDice();  // Броски сохраняются в игроке
            }
            player.showPlayersRollHistory();

        });

        // Далее анализ через DiceTournamentAnalyzer...

        task("1.\tСредний результат бросков (Map<Player, Double>).");
        System.out.println(dta.getMapOfAverageScores(players));

        task("2.\tТоп-3 игроков с самым высоким средним.");
        System.out.println(dta.getTopThreeAveragePlayers(players));

        task("3.\t\"Неудачники\" – игроки, у которых ≥3 раза выпала 1.");
        System.out.println(dta.getTheLosers(players));

        task("4.\t\"Везунчики\" – игроки, у которых ≥4 раза выпала 6.");
        System.out.println(dta.getTheLucky(players));

        task("5.\tГруппировка игроков по фишкам (>1000, 500-1000, <500).");
        System.out.println(dta.getPlayersGroupedByChips(players));

        task("6.\t\"Стабильные кости\" – у которых ≥8 из 10 бросков ≥4.");
        System.out.println(dta.getStablePlayerList(players));

        task("7.\t\"Рисковые игроки\" – у которых ≥2 раза подряд выпадала 1.");
        System.out.println(dta.getRiskyPlayerList(players));

        task("8.\t\"Сбалансированные кости\" – все числа от 1 до 6 выпали хотя бы раз.");
        System.out.println(dta.getBalancedDicePlayerList(players));

        task("9.\t\"Индекс удачи\" = (сумма бросков) / (6 × 10) × chips.");
        System.out.println(dta.getLuckIndex(players));

        task("10.\tИгрок с наибольшей дисперсией.");
        System.out.println(dta.getMaxMathVariancePlayer(players));

    }

    private static void task(String message) {
        System.out.println(GREEN + "\n" + message + RESET);
    }

}
