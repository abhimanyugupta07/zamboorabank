package com.springapp.mvc.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 11:51 AM
 */
public class Account {
    @Getter @Setter
    private String residentId;
    @Getter @Setter
    private double amount;
    @Getter @Setter
    private double amountBlocked;
    @Getter @Setter
    private double bonusBitcoins;

    public Account(){}
    public Account(String residentId, double amount, double amountBlocked, double bonusBitcoins){
        this.residentId = residentId;
        this.amount = amount;
        this.amountBlocked = amountBlocked;
        this.bonusBitcoins = bonusBitcoins;
    }
}
