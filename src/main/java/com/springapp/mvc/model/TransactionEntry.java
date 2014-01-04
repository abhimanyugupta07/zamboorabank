package com.springapp.mvc.model;

import lombok.Getter;
import lombok.Setter;
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
    @Getter @Setter
    private int uniqueId;
    @Getter @Setter
    private String residentId;
    @Getter @Setter
    private double amount;
    @Getter @Setter
    private String transactionId;

    @Getter @Setter
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
}
