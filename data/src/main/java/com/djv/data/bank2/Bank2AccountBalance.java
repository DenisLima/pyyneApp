package com.djv.data.bank2;

/**
 * Created by Par Renyard on 5/12/21.
 */
public class Bank2AccountBalance {

    private double balance;
    private String currency;

    public Bank2AccountBalance(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
