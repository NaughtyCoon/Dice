package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final Dice dice;
    private final int chips;  // количество фишек
    private final List<Integer> rollsHistory = new ArrayList<>();

    public Player(String name, Dice dice, int chips) {
        this.name = name;
        this.chips = chips;
        this.dice = dice;
    }

    public void throwDice() {
        rollsHistory.add(dice.throwMe());
    }

    public List<Integer> getRollsHistory() {
        return rollsHistory;
    }

    public void showPlayersRollHistory(){
        System.out.println(name +": " + rollsHistory);
    }

    public int getChips() {
        return chips;
    }

    @Override
    public String toString() {
        return name;
    }

}
