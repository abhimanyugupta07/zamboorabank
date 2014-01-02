package com.springapp.mvc.initialize;

import com.springapp.mvc.utilities.PayUUtilities;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: abgupta
 * @Date: 1/2/14
 * @Time: 9:18 PM
 */
public class PayuBankInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        PayUUtilities.loadPropertyFile();

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
