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
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired private AccountService accountService;

    @RequestMapping(value = "/info/{resident_id}", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
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

}
