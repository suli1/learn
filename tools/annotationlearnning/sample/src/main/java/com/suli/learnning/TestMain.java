package com.suli.learnning;

import com.suli.learnning.store.IMeal;
import com.suli.learnning.store.PizzaStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by suli690 on 2017-01-01.
 */
public class TestMain {

    public static void main(String[] args) {
        try {

            PizzaStore pizzaStore = new PizzaStore();
            IMeal meal = pizzaStore.order(readConsole());
            System.out.println("Bill: $" + meal.getPrice());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readConsole() throws IOException {
        System.out.println("What do you like?");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return bufferedReader.readLine();
    }
}
