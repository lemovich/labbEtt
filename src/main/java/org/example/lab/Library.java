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
            System.out.println("Library is full of books. Cannot add more books.");
            return false;
        }
        books[bookCount] = book;
        bookCount++;
        return true;
    }

    public Member registerMember(String name) {
        if (memberCount >= members.length) {
            System.out.println("Library is full of members. Cannot add more members.");
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
            System.out.println("No book with this ISBN " + isbn + " was found.");
            return false;
        }

        if (borrowedBy[bookIndex] != null) {
            System.out.println("The book \"" + books[bookIndex].title() + "\" is already borrowed.");
            return false;
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("No member with ID " + memberId + " was found.");
            return false;
        }

        if (!member.canBorrowMore()) {
            System.out.println(member.getName() + " has already borrowed max amount (3) of books.");
            return false;
        }

        borrowedBy[bookIndex] = member;
        member.registerLoan();
        System.out.println(member.getName() + " borrowed \"" + books[bookIndex].title() + "\".");
        return true;
    }

    public boolean returnBook(String isbn) {
        int bookIndex = findBookIndex(isbn);
        if (bookIndex == -1) {
            System.out.println("No book with ISBN " + isbn + " was found.");
            return false;
        }

        Member borrower = borrowedBy[bookIndex];
        if (borrower == null) {
            System.out.println("The book \"" + books[bookIndex].title() + "\" is not borrowed.");
            return false;
        }

        borrower.registerReturn();
        borrowedBy[bookIndex] = null;
        System.out.println(borrower.getName() + " returned \"" + books[bookIndex].title() + "\".");
        return true;
    }

    public record Book(String isbn, String title, String author) {
    }
}
