package com.lyne.servlet;

import com.lyne.common.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

/**
 * 通过ServletRegistrationBean添加自定义servlet至spring boot项目
 *
 * @author nn_liu
 * @Created 2017-07-20-11:13
 */

public class RegisteredServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(RegisteredServlet.class);

    public void service(HttpServletRequest req, HttpServletResponse resp){
        String method = req.getMethod();

        if (method.equals(HttpMethod.METHOD_GET)) {
            logger.info("registered servlet GET method is calling...");
        } else if (method.equals(HttpMethod.METHOD_HEAD)) {
            logger.info("registered servlet HEAD method is calling...");
        } else if (method.equals(HttpMethod.METHOD_POST)) {
            logger.info("registered servlet POST method is calling...");
        } else if (method.equals(HttpMethod.METHOD_PUT)) {
            logger.info("registered servlet PUT method is calling...");
        } else if (method.equals(HttpMethod.METHOD_DELETE)) {
            logger.info("registered servlet DELETE method is calling...");
        } else if (method.equals(HttpMethod.METHOD_OPTIONS)) {
            logger.info("registered servlet OPTIONS method is calling...");
        } else if (method.equals(HttpMethod.METHOD_TRACE)) {
            logger.info("registered servlet TRACE method is calling...");
        } else {
            logger.info("Oppos, registered servlet can't service for you!");
        }
    }

}
