package com.lyne;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author nn_liu
 * @Created 2017-11-10-18:07
 */

@ContextConfiguration(value = "classpath:context-placeholder.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertyPlaceholderConfigurerTest {


    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testContextPlaceHolder(){

        BeanDemo demo1 = (BeanDemo) applicationContext.getBean("naruto");
        System.out.println(demo1.getName());

        BeanDemo demo2 = (BeanDemo) applicationContext.getBean("onepiece");
        System.out.println(demo2.getName());
    }

}
