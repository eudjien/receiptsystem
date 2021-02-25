package ru.clevertec.checksystem.cli;

public class Main {
    public static void main(String[] args) {
        var application = new Application();
        try {
            application.start(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
