package com.lyne.common.utils.jackson.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lyne.common.utils.beanrepo.Person;

import java.io.IOException;

/**
 * Created by nn_liu on 2016/9/23.
 */
public class XMLTest {
    /*创建一个Bean，然后将它转换为XML*/
    public static String obj2XML(Object object) {

        ObjectMapper mapper = new XmlMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*将XML装换为一个Bean*/
    public static <T> T xml2Object(String xml, Class<T> valueType) {
        ObjectMapper mapper = new XmlMapper();
        try {
            return mapper.readValue(xml, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Person person=new Person();
        person.setName("luffy");;
        person.setEmail("caomao@sea.com");
        person.setAge(20);

        /*test1*/
        String xmlStr = XMLTest.obj2XML(person);
        System.out.println(xmlStr);     //  结果：<Person><name>luffy</name><email>caomao@sea.com</email><age>20</age></Person>

        /*test2*/
        Object object = XMLTest.xml2Object(xmlStr,Person.class);
        System.out.println(object);    //  结果：PersonBean[name='luffy',email='caomao@sea.com',age='20']
    }

}
