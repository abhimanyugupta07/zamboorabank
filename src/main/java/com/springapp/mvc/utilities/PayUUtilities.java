package com.springapp.mvc.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 5:01 PM
 */
public class PayUUtilities {

    private static Properties payUProperties;

    public static String getTransactionId() {
        return UUID.randomUUID().toString();
    }
}
