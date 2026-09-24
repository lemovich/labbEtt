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

    public record Book(String isbn, String title, String author) {
    }
}
