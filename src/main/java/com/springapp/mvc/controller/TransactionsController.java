package com.springapp.mvc.controller;

import com.springapp.mvc.Data;
import com.springapp.mvc.model.Account;
import com.springapp.mvc.model.TransactionEntry;
import com.springapp.mvc.utilities.PayUUtilities;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 11:59 AM
 */
@Controller
@RequestMapping("/transactions")
public class TransactionsController {

    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    @RequestMapping(value = "/credit", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String creditAmount (@RequestParam(value="resident_id") String residentId,
                                              @RequestParam(value="amount") double amount) throws IOException {


        System.out.println("resident_id: "+residentId + "\n" + "amount: "+amount);

        String transactionId = PayUUtilities.getTransactionId();
        Date timestamp = new Date();

        TransactionEntry entry = new TransactionEntry(residentId,amount,transactionId, timestamp);
        Session session = factory.openSession();
        String response = null;
        try {
            addTransaction(entry,session);
            response = updateAccountBalance(residentId,amount,session,true);
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

    @RequestMapping(value = "/debit", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String DebitAmount (@RequestParam(value="resident_id") String residentId,
                                              @RequestParam(value="amount") double amount) throws IOException {


        System.out.println("resident_id: "+residentId + "\n" + "amount: "+amount);

        String transactionId = PayUUtilities.getTransactionId();
        Date timestamp = new Date();

        TransactionEntry entry = new TransactionEntry(residentId,-amount,transactionId, timestamp);
        Session session = factory.openSession();
        String response = null;
        try {
            addTransaction(entry,session);
            response = updateAccountBalance(residentId,amount,session,false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        //       return null;
        if (response == null) {
            JSONObject object = new JSONObject();
            object.put("Error","Unable to process this request");
            response = object.toJSONString();
        }
        return response;
    }

    private String updateAccountBalance(String residentId, double amount, Session session, boolean isCredit) {

        Transaction tx = session.getTransaction();
        String hql = "FROM Account as account  WHERE account.residentId ='"+ residentId +"'";
        System.out.println("\n\n\n\n The query string is :::" + hql);
        org.hibernate.Query query = session.createQuery(hql);
        List <Account> results = query.list();

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
                if (currentBalance < amount) {
                    response.put("Error","Not Enough Balance in Account");
                    response.put("Current Account Balance", currentBalance);
                    return response.toJSONString();
                }
                double cashbackPercentage = Double.parseDouble(PayUUtilities.getPayUProperties().getProperty("cashback_percentage"));
                double bonusBitcoins = amount*cashbackPercentage;
                userAccount.setBonusBitcoins(currentBonusBitcoins+bonusBitcoins);
                userAccount.setAmount(currentBalance + amount + bonusBitcoins);
                response.put("Current Account Balance",userAccount.getAmount());
                response.put("Success","Debited account with "+ amount);
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
            //tx.commit();
        }
        catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
    }
}
