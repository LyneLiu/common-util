package com.lyne;

import org.lyne.idl.protocol.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nn_liu on 2017/6/19.
 */
public class PrdInfoImpl implements PrdInfo {

    // in this simple example just return details of the message
    public PrdInfoType queryPrdInfo(PrdIdentity prdIdentity) {
        PrdInfoType prdInfoType = new PrdInfoType();
        if (prdIdentity.getId() == 1){
            prdInfoType.setId(1);
            prdInfoType.setType("adult");
            prdInfoType.setPrice(20.1);
        }else {
            prdInfoType.setId(2);
            prdInfoType.setType("child");
            prdInfoType.setPrice(10.1);
        }
        return prdInfoType;
    }

    public UserInfoType queryUserInfo(UserIdentity userIdentity){
        UserInfoType userInfoType = new UserInfoType();
        Address address01 = new Address();
        address01.setCity("Japan");
        Address address02 = new Address();
        address02.setCity("China");
        Address address03 = new Address();
        address03.setCity("America");
        if (userIdentity.getId() == 0){
            List<Address> addrList = new ArrayList<>();
            addrList.add(address01);
            userInfoType.setId(0);
            userInfoType.setName("luffy");
            userInfoType.setAge(20);
            userInfoType.setAddr(addrList);
        }else {
            List<Address> addrList = new ArrayList<>();
            addrList.add(address02);
            addrList.add(address03);
            userInfoType.setId(1);
            userInfoType.setName("micky");
            userInfoType.setAge(36);
            userInfoType.setAddr(addrList);
        }

        return userInfoType;
    }
}
