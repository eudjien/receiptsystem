package ru.clevertec.checksystem.cli;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        var application = new Application();
        try {
            application.start(args);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
