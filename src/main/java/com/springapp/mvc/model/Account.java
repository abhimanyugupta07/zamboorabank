package com.springapp.mvc.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 11:51 AM
 */
@Data
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
}
