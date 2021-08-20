package module;

import java.io.Serializable;

public class Account implements Serializable {
    private int accountId;
    private int id;
    private int balance;
    private char type;
    private char status;
    public Account(){

    }

    public Account(int accountId, int id, int balance, char type, char status) {
        this.accountId = accountId;
        this.id = id;
        this.balance = balance;
        this.type = type;
        this.status = status;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public char getType() {
        return type;
    }

    public char getStatus() {
        return status;
    }
}
