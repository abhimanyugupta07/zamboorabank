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
import org.springframework.beans.factory.annotation.Autowired;
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

    private @Autowired com.springapp.mvc.service.TransactionService transactionService;
    @RequestMapping(value = "/credit", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String creditAmount (@RequestParam(value="resident_id") String residentId,
                                              @RequestParam(value="amount") double amount) throws IOException {

        return transactionService.credit(residentId,amount);
    }

    @RequestMapping(value = "/debit", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String DebitAmount (@RequestParam(value="resident_id") String residentId,
                                              @RequestParam(value="amount") double amount) throws IOException {
        return transactionService.debit(residentId,-amount);
    }
}
