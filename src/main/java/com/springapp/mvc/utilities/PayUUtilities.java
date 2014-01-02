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
    private final static String PATH_TO_PROPERTIES_FILE = "config/payubank.properties";

    public static String getTransactionId() {
        return UUID.randomUUID().toString();
    }

    public static Properties getPayUProperties() {
        return payUProperties;
    }

    public static void loadPropertyFile() {
        //private final Logger logger = LoggerFactory.getLogger(EmailScorerBuilder.class);
        //  Logger logger = LoggerFactory.getLogger(EmailScorerBuilder.class);

        Properties temp = new Properties();

        try {
            InputStream propFile = PayUUtilities.class.getClassLoader().getResourceAsStream(PATH_TO_PROPERTIES_FILE);
            if (propFile == null) {
                //logger.error("domainlist Properties File Not Found");
            }
            try {
                temp.load(propFile);
            }
            finally {
                    propFile.close();
            }
        }
        catch (IOException e) {
            //logger.error("Cannot read properties from the domainlist.properties file",e);
        }
        payUProperties = temp;
    }
}
