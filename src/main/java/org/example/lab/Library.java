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

    public record Book(String isbn, String title, String author) {
    }
}
