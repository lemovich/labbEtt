package org.example.lab;
import java.util.Scanner;

public class TestLibrary {

    static void main() {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library(20, 20);

        boolean running = true;

        while (running) {
            Menu.printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> Menu.addBookFlow(scanner, library);
                case "2" -> Menu.registerMemberFlow(scanner, library);
                case "3" -> Menu.borrowBookFlow(scanner, library);
                case "4" -> Menu.returnBookFlow(scanner, library);
                case "5" -> Menu.searchBookFlow(scanner, library);
                case "6" -> Menu.listAllBooksFlow(library);
                case "e", "E" -> {
                    running = false;
                    IO.println("Avslutar programmet.");
                }
                default -> IO.println("Ogiltigt val, försök igen.");
            }
        }
    }
}
