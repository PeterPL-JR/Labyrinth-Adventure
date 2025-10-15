package com.peterpl.labyrinth;

import java.util.*;

public class Rand {
    private static Random random = new Random();

    public static int number(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static <T> T element(T ... elements) {
        return elements[random.nextInt(elements.length)];
    }

    public static boolean bool() {
        return random.nextInt(2) == 0;
    }
}
