package com.lyne.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lyne.domain.User;
import com.lyne.service.UserServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by nn_liu on 2016/8/17.
 */

@Controller
public class BaseController
{

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/usersinfo", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserInfo() {
        List<User> users = userService.getAll();
        return users;
    }

}
