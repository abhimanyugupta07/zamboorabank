package com.springapp.mvc.controller;

import com.springapp.mvc.model.Account;
import com.springapp.mvc.model.TransactionEntry;
import com.springapp.mvc.service.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/account")
public class AccountsController {

    @Autowired private AccountService accountService;

    @RequestMapping(value = "{resident_id}/info", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getAccountInfo (@PathVariable("resident_id") String residentId,@RequestParam(value="number", required=false) String number) {

        int numberTransactions;
        if (number == null) {
            number = "-1";
        }
        try {
            numberTransactions = Integer.parseInt(number);
        }
        catch (Exception e) {
            return "{\"Error\":\"Invalid Number format for the parameter number\"}";
        }
        return accountService.info(residentId, numberTransactions);
    }

    private @Autowired com.springapp.mvc.service.TransactionService transactionService;
    @RequestMapping(value = "{resident_id}/credit", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String creditAmount (@PathVariable(value="resident_id") String residentId,
                                              @RequestParam(value="amount") double amount) throws IOException {

        return transactionService.transact(residentId,amount,true);
    }

    @RequestMapping(value = "{resident_id}/debit", method = RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String DebitAmount (@PathVariable(value="resident_id") String residentId,
                                             @RequestParam(value="amount") double amount) throws IOException {
        return transactionService.transact(residentId,amount,false);
    }


}
