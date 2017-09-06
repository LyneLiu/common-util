package com.lyne.controller;

import com.lyne.service.DemoService;
import com.lyne.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过redis缓存存储/获取数据
 * @author nn_liu
 * @Created 2017-06-28-14:30
 */
@RestController
public class RedisController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/{name}",method = RequestMethod.POST)
    public String prdInfo(@PathVariable String name){
        return SerializeUtil.serialize(demoService.prdInfo(name)).toString();
    }

    @RequestMapping(value = "/{name}",method = RequestMethod.GET)
    public String getPrdInfo(@PathVariable String name){
        return SerializeUtil.serialize(demoService.getPrdInfo(name)).toString();
    }

}
