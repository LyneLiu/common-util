package com.lyne;

import com.lyne.bo.PrdBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private PrdBean prdBean;

    @Test
    public void testPostConstruct() throws InterruptedException {
        int index = 0;
        while (index < 5) {
            System.out.println(prdBean.toString());
            System.out.println("name:"+prdBean.getName()+",addr:"+prdBean.getAddr());
            index++;
            Thread.sleep(2000);
        }

        ((ConfigurableApplicationContext)Application.getContext()).close();

        Thread.sleep(2000);
        System.out.println(prdBean.toString());
    }

}
