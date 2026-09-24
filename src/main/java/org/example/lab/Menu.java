package org.example.lab;

import java.util.Scanner;

public class Menu {

    public static void printMenu() {
        IO.println();
        IO.println("Bibliotekshanteraren");
        IO.println("====================");
        IO.println("1. Lägg till bok");
        IO.println("2. Registrera medlem");
        IO.println("3. Låna bok");
        IO.println("4. Lämna tillbaka bok");
        IO.println("e. Avsluta");
        IO.print("Val: ");
    }

    public static void addBookFlow(Scanner scanner, Library library) {
        IO.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        IO.print("Titel: ");
        String title = scanner.nextLine().trim();

        IO.print("Författare: ");
        String author = scanner.nextLine().trim();


        Book book = new Book(isbn, title, author);
        if (library.addBook(book)) {
            IO.println("Boken lades till!");
        }
    }

    public static void registerMemberFlow(Scanner scanner, Library library) {
        IO.print("Namn: ");
        String name = scanner.nextLine().trim();

        Member member = library.registerMember(name);
    }

    public static void borrowBookFlow(Scanner scanner, Library library) {
        IO.print("ISBN på boken: ");
        String isbn = scanner.nextLine().trim();

        int memberId = readValidatedInt(scanner, "Medlems-ID: ");
        if (memberId == -1) {
            return; // felmeddelande redan utskrivet i readValidatedInt
        }

        library.borrowBook(isbn, memberId);
    }

    public static void returnBookFlow(Scanner scanner, Library library) {
        IO.print("ISBN på boken: ");
        String isbn = scanner.nextLine().trim();

        library.returnBook(isbn);
    }

    public static int readValidatedInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (!isOnlyDigits(input)) {
            IO.println("Ogiltig inmatning – ange ett heltal.");
            return -1;
        }
        return Integer.parseInt(input);
    }

    public static boolean isOnlyDigits(String input) {
        if (input.isEmpty()) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
