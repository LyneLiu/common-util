package com.lyne.servlet;

import com.lyne.common.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过@WebServlet注解添加自定义servlet至spring boot项目
 *
 * @author nn_liu
 * @Created 2017-07-20-11:13
 */

@WebServlet(urlPatterns="/customeWebServlet/", description="自定义Web Servlet")
public class CustomeWebServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(CustomeWebServlet.class);

    public void service(HttpServletRequest req, HttpServletResponse resp){
        String method = req.getMethod();

        if (method.equals(HttpMethod.METHOD_GET)) {
            logger.info("custome webServlet GET method is calling...");
        } else if (method.equals(HttpMethod.METHOD_HEAD)) {
            logger.info("custome webServlet HEAD method is calling...");
        } else if (method.equals(HttpMethod.METHOD_POST)) {
            logger.info("custome webServlet POST method is calling...");
        } else if (method.equals(HttpMethod.METHOD_PUT)) {
            logger.info("custome webServlet PUT method is calling...");
        } else if (method.equals(HttpMethod.METHOD_DELETE)) {
            logger.info("custome webServlet DELETE method is calling...");
        } else if (method.equals(HttpMethod.METHOD_OPTIONS)) {
            logger.info("custome webServlet OPTIONS method is calling...");
        } else if (method.equals(HttpMethod.METHOD_TRACE)) {
            logger.info("custome webServlet TRACE method is calling...");
        } else {
            logger.info("Oppos, custome webServlet can't service for you!");
        }
    }

}
