package ru.otus.java.hw4.app;

import java.util.ArrayList;
import java.util.List;

public class App {

    List<String> stringList = new ArrayList<>();

    private App() {

    }

    public static App app() {
       return new App();
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    public void startApp() {

        while (true) {
            for (int i = 0; i < 10_000; i++) {
                stringList.add(String.valueOf(i));
            }

            for (int i = 0; i < 1000; i++) {
                stringList.remove(0);
            }
        }
    }
}
