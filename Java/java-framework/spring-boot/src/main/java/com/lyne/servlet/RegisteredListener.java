package com.lyne.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author nn_liu
 * @Created 2017-07-20-16:56
 */

public class RegisteredListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==>" + this.getClass().getSimpleName() + "启动！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
