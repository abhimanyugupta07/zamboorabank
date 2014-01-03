package com.springapp.mvc.utilities;

/**
 * @author: abgupta
 * @Date: 1/3/14
 * @Time: 8:03 PM
 */
public class HibernateQueries {
    public static final String GET_ACCOUNT_INFO_BASED_ON_RESIDENT_ID = "FROM Account as account  WHERE account.residentId ='";
    public static final String GET_ALL_TRANSACTIONS_BASED_ON_RESIDENT_ID = "FROM TransactionEntry as transaction  WHERE transaction.residentId ='";

    public static String getAccountsQueryBasedonResidentId(String residentId) {
        return GET_ACCOUNT_INFO_BASED_ON_RESIDENT_ID+residentId+"'";
    }


    public static String getTransactionsQueryBasedonResidentId(String residentId) {
        return GET_ALL_TRANSACTIONS_BASED_ON_RESIDENT_ID+residentId+"'";
    }
}
