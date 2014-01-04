package com.springapp.mvc.service;

import com.springapp.mvc.model.Account;
import com.springapp.mvc.model.TransactionEntry;
import com.springapp.mvc.utilities.HibernateQueries;
import com.springapp.mvc.utilities.HibernateUtilities;
import com.springapp.mvc.utilities.PayUUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author: abgupta
 * @Date: 1/3/14
 * @Time: 2:29 PM
 */
@Component
@PropertySource(value="config/payubank.properties")
public class TransactionService {

    @Autowired private Environment env;

    private static SessionFactory factory = HibernateUtilities.getSessionFactory();

    public String transact(String residentId, double amount, boolean isCredit) {
        if (!isCredit) amount = -amount;
        String transactionId = PayUUtilities.getTransactionId();
        Date timestamp = new Date();
        TransactionEntry entry = new TransactionEntry(residentId,amount,transactionId, timestamp);
        Session session = factory.openSession();
        String response = null;
        try {
            addTransaction(entry,session);
            response = updateAccountBalance(residentId,amount,session,isCredit);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        if (response == null) {
            JSONObject object = new JSONObject();
            object.put("Error","Unable to process this request");
        }
        return response;
    }

    private String updateAccountBalance(String residentId, double amount, Session session, boolean isCredit) {
        Double cashBackPercentage = Double.parseDouble(env.getProperty("cashback_percentage"));
        Transaction tx = session.getTransaction();
        String hql = HibernateQueries.getAccountsQueryBasedonResidentId(residentId);
        System.out.println("\n\n\n\n The query string is :::" + hql);
        org.hibernate.Query query = session.createQuery(hql);
        List<Account> results = query.list();

        Account userAccount = null;
        JSONObject response = new JSONObject();
        if (isCredit == true) {
            if (results == null || !results.isEmpty()) {
                userAccount = results.get(0);
                double currentBalance = userAccount.getAmount();
                userAccount.setAmount(currentBalance+amount);
            }
            else {
                userAccount = new Account(residentId,amount,0,0);
            }
            response.put("Current Account Balance",userAccount.getAmount());
            response.put("Success","Credited account with "+amount);
        }
        else {
            if (results == null || !results.isEmpty()) {
                userAccount = results.get(0);
                double currentBalance = userAccount.getAmount();
                double currentBonusBitcoins = userAccount.getBonusBitcoins();
                if (currentBalance < Math.abs(amount)) {
                    response.put("Error","Not Enough Balance in Account");
                    response.put("Current Account Balance", currentBalance);
                    return response.toJSONString();
                }
                double bonusBitcoins = Math.abs(amount*cashBackPercentage);
                userAccount.setBonusBitcoins(currentBonusBitcoins+bonusBitcoins);
                userAccount.setAmount(currentBalance + amount + bonusBitcoins);
                response.put("Current Account Balance",userAccount.getAmount());
                response.put("Success","Debited account with "+ -amount);
            }
            else {
                response = new JSONObject();
                response.put("Error","Account does not exist, please credit the account with some bitcoins before debit");
                return response.toJSONString();
            }
        }

        try {
            tx = session.getTransaction();
            session.save(userAccount);
            tx.commit();
        }
        catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }

        if (response == null) {
            response.put("Error", "Unable to process your request");
        }
        return response.toJSONString();
    }

    public void addTransaction(TransactionEntry entry, Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(entry);
        }
        catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
    }


}
