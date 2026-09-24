package org.example.lab;
import java.util.Scanner;

public class TestLibrary {
    static void main() {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library(20, 20);

        boolean running = true;

        while (running) {
            printMenu();
        }

    }
    private static void printMenu() {
        IO.println();
        IO.println("")
    }
}
