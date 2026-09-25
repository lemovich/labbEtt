package org.example.lab;

public class Library {
    private Book[] books;
    private Member[] borrowedBy;
    private Member[] members;

    private int bookCount;
    private int memberCount;

    public Library(int bookCapacity, int memberCapacity) {
        this.books = new Book[bookCapacity];
        this.borrowedBy = new Member[bookCapacity];
        this.members = new Member[memberCapacity];
        this.bookCount = 0;
        this.memberCount = 0;
    }

    public boolean addBook(Book book) {
        if (bookCount >= books.length) {
            IO.println("Biblioteket är fullt – kan inte lägga till fler böcker.");
            return false;
        }
        books[bookCount] = book;
        bookCount++;
        return true;
    }

    public Member registerMember(String name) {
        if (memberCount >= members.length) {
            IO.println("Biblioteket har nått maxantal medlemmar – kan inte registrera fler.");
            return null;
        }
        Member newMember = new Member(name);
        members[memberCount] = newMember;
        IO.println(name + " registrerad med ID: " + newMember.getId());
        memberCount++;
        return newMember;
    }

    private int findBookIndex(String isbn) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isbn().equalsIgnoreCase(isbn)) {
                return i;
            }
        }
        return -1;
    }

    private Member findMemberById(int id) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getId() == id) {
                return members[i];
            }
        }
        return null;
    }

    public boolean borrowBook(String isbn, int memberId) {
        int bookIndex = findBookIndex(isbn);
        if (bookIndex == -1) {
            IO.println("Ingen bok med ISBN " + isbn + " hittades.");
            return false;
        }

        if (borrowedBy[bookIndex] != null) {
            IO.println("Boken \"" + books[bookIndex].title() + "\" är redan utlånad.");
            return false;
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            IO.println("Ingen medlem med ID " + memberId + " hittades.");
            return false;
        }

        if (!member.canBorrowMore()) {
            IO.println(member.getName() + " har redan lånat max antal böcker (3).");
            return false;
        }

        borrowedBy[bookIndex] = member;
        member.registerLoan();
        IO.println(member.getName() + " lånade \"" + books[bookIndex].title() + "\".");
        return true;
    }

    public boolean returnBook(String isbn) {
        int bookIndex = findBookIndex(isbn);
        if (bookIndex == -1) {
            IO.println("Ingen bok med ISBN " + isbn + " hittades.");
            return false;
        }

        Member borrower = borrowedBy[bookIndex];
        if (borrower == null) {
            IO.println("Boken \"" + books[bookIndex].title() + "\" är inte utlånad.");
            return false;
        }

        borrower.registerReturn();
        borrowedBy[bookIndex] = null;
        IO.println(borrower.getName() + " lämnade tillbaka \"" + books[bookIndex].title() + "\".");
        return true;
    }

    private void printBookLine(Book book, Member borrower) {
        String status = (borrower != null) ? "Utlånad till " + borrower.getName() : "Tillgänglig";
        System.out.println("- " + book.title() + " av " + book.author() + " (ISBN: " + book.isbn() + ") — " + status);
    }

    public void listAllBooks() {
        if (bookCount == 0) {
            System.out.println("Inga böcker är registrerade i biblioteket.");
            return;
        }
        for (int i = 0; i < bookCount; i++) {
            printBookLine(books[i], borrowedBy[i]);
        }
    }

    public int searchBooks(String query) {
        String lowerQuery = query.toLowerCase();
        int matches = 0;

        for (int i = 0; i < bookCount; i++) {
            Book book = books[i];
            boolean titleMatches = book.title().toLowerCase().contains(lowerQuery);
            boolean authorMatches = book.author().toLowerCase().contains(lowerQuery);

            if (titleMatches || authorMatches) {
                printBookLine(book, borrowedBy[i]);
                matches++;
            }
        }

        if (matches == 0) {
            System.out.println("Inga böcker matchade \"" + query + "\".");
        }

        return matches;
    }
}
