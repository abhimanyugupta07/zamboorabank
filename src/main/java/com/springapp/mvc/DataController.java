package com.springapp.mvc;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: abgupta
 * @Date: 12/31/13
 * @Time: 6:15 PM
 */
public class DataController {
    private static SessionFactory factory;
    public String fetch() {
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;


        //String dealId = null;
        //System.out.println("The deal is ::::" + request.toString());
        try{
            tx = session.beginTransaction();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String currentTime = df.format(new Date());
//            System.out.println("\n\n\nCurrent Date is :::" + currentTime.toString());
            String hql = "FROM Data as data";
            System.out.println("\n\n\n\n The query string is :::" + hql);
            org.hibernate.Query query = session.createQuery(hql);
            //System.out.println("mysql: "+query.);
            List<Data> results = query.list();
            JSONObject dealsResponse = new JSONObject();
            JSONArray dealsArray = new JSONArray();

            for (int i=0; i<results.size(); i++) {
                dealsResponse.put("row"+i,results.get(i).getId()+" "+results.get(i).getName());
            }
            //dealsResponse.put("test",dealsArray.toString());
            //System.out.println(results.toString());
            //   LOG.info("\n\n\n\nThe JSON object is" + dealsResponse.toString());
            return dealsResponse.toString();
        }
        catch(HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}

