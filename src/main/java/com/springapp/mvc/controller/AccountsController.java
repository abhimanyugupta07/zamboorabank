package com.springapp.mvc.controller;

import com.springapp.mvc.model.Account;
import com.springapp.mvc.model.TransactionEntry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 12:00 PM
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController {

    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    @RequestMapping(value = "/info/{resident_id}", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getAccountInfo (@PathVariable("resident_id") String residentId) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        JSONObject response = new JSONObject();

        String hql = "FROM Account as account  WHERE account.residentId ='"+ residentId +"'";
        System.out.println("\n\n\n\n The query string is :::" + hql);
        org.hibernate.Query query = session.createQuery(hql);
        List<Account> results = query.list();

        if (results == null || results.isEmpty())
        {
            response.put("Error","Sorry, the resident_id "+residentId+" does not exist");
            return response.toJSONString();
        }

        Account userAccount = results.get(0);
        response.put("resident_id",userAccount.getResidentId());
        response.put("balance",userAccount.getAmount());
        response.put("amount_blocked",userAccount.getAmountBlocked());
        response.put("bonus_bitcoins",userAccount.getBonusBitcoins());

        tx = session.getTransaction();
        hql = "FROM TransactionEntry as transaction  WHERE transaction.residentId ='"+ residentId +"'";
        System.out.println("\n\n\n\n The query string is :::" + hql);
        org.hibernate.Query transactionQuery = session.createQuery(hql);
        List<TransactionEntry> transactions = transactionQuery.list();

        JSONArray transactionsJson = new JSONArray();

        for (TransactionEntry entry : transactions) {
            JSONObject tempJson = new JSONObject();
            tempJson.put("uniqueId",entry.getUniqueId());
            tempJson.put("amount", entry.getAmount());
            tempJson.put("transaction_id",entry.getTransactionId());
            tempJson.put("timestamp",entry.getTimestamp());

            transactionsJson.add(tempJson);
        }

        response.put("transactions",transactionsJson);

        return response.toJSONString();
    }

}
