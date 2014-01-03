package com.springapp.mvc.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springapp.mvc.model.Account;
import com.springapp.mvc.model.TransactionEntry;
import com.springapp.mvc.utilities.HibernateQueries;
import com.springapp.mvc.utilities.HibernateUtilities;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: abgupta
 * @Date: 1/3/14
 * @Time: 3:19 PM
 */
@Component
public class AccountService {

    private static SessionFactory factory = HibernateUtilities.getSessionFactory();
    public String info(String residentId,int number) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        JSONObject response = new JSONObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        String hql = HibernateQueries.getAccountsQueryBasedonResidentId(residentId);
        System.out.println("\n\n\n\n The query string is :::" + hql);
        org.hibernate.Query query = session.createQuery(hql);

        List<Account> results = query.list();

        if (results == null || results.isEmpty())
        {
            response.put("Error","Sorry, the resident_id "+residentId+" does not exist");
            return response.toJSONString();
        }

        Account userAccount = results.get(0);
        String info = gson.toJson(userAccount);
        response.put("summary", JSONValue.parse(info));

        tx = session.getTransaction();
        hql = HibernateQueries.getTransactionsQueryBasedonResidentId(residentId);
        System.out.println("\n\n\n\n The query string is :::" + hql);
        org.hibernate.Query transactionQuery = session.createQuery(hql);

        if (number!=-1) {
            transactionQuery.setMaxResults(number);
        }

        List<TransactionEntry> transactions = transactionQuery.list();

        JSONArray transactionsJson = new JSONArray();

        for (TransactionEntry entry : transactions) {
            JSONObject tempJson = (JSONObject) JSONValue.parse(gson.toJson(entry));
            transactionsJson.add(tempJson);
        }

        response.put("transactions",transactionsJson);

        return response.toJSONString();
    }
}
