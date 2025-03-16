package com.pulsar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        
    }

    private static List<Integer> getRandomList() {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < random.nextInt(0, 100); i++) {
            result.add(random.nextInt(1, 500));
        }

        return result;
    }
}
