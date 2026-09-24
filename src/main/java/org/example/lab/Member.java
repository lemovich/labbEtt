package org.example.lab;

public class Member {
    private static int nextId = 1;
    private static final int MAX_ACTIVE_LOANS = 3;

    private final int id;
    private String name;
    private int activeLoans;

    public Member(String name) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.activeLoans = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getActiveLoans() {
        return activeLoans;
    }

    public boolean canBorrowMore() {
        return activeLoans < MAX_ACTIVE_LOANS;
    }

    public void registerLoan() {
        activeLoans++;
    }

    public void registerReturn() {
        if (activeLoans > 0) {
            activeLoans--;
        }
    }

}
