package com.springapp.mvc.model;

import lombok.Data;
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
@Data
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
}
