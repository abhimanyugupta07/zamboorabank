package com.springapp.mvc.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 11:50 AM
 */
public class TransactionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uniqueId")
    private int uniqueId;

    private String residentId;
    private double amount;
    private String transactionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date timestamp;

    public TransactionEntry() {}

    public TransactionEntry(String residentId, double amount, String transactionId, Date timestamp) {
        //this.uniqueId = 0;
        this.residentId = residentId;
        this.amount = amount;
        this.transactionId = transactionId;
        this.timestamp = timestamp;
    }
    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
