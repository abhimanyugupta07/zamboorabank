package com.springapp.mvc.model;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 11:51 AM
 */
public class Account {
    private String residentId;
    private double amount;
    private double amountBlocked;
    private double bonusBitcoins;

    public Account(){}
    public Account(String residentId, double amount, double amountBlocked, double bonusBitcoins){
        this.residentId = residentId;
        this.amount = amount;
        this.amountBlocked = amountBlocked;
        this.bonusBitcoins = bonusBitcoins;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAmountBlocked(double amountBlocked) {
        this.amountBlocked = amountBlocked;
    }

    public void setBonusBitcoins(double bonusBitcoins) {
        this.bonusBitcoins = bonusBitcoins;
    }

    public String getResidentId() {
        return residentId;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountBlocked() {
        return amountBlocked;
    }

    public double getBonusBitcoins() {
        return bonusBitcoins;
    }
}
