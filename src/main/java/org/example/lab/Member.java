package org.example.lab;

public class Member {
    private static final int MAX_ACTIVE_LOANS = 3;

    private int id;
    private String name;
    private int activeLoans;

    public Member(int id, String name) {
        this.id = id;
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
