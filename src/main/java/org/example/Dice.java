package org.example;

import java.util.Random;

public class Dice {

    private final Random random = new Random();

    public int throwMe() {
        return random.nextInt(1,7);
    }

}
