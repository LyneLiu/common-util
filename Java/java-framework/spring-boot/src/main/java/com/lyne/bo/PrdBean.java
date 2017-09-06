package com.lyne.bo;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by nn_liu on 2017/5/18.
 */

@Component
public class PrdBean implements Serializable{

    @NotNull
    private String name;
    @NotNull
    private String addr;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<SubPrdBean> subPrdBeans;

    @PostConstruct
    public void init(){
        this.name = "test_name";
        this.addr = "test_addr";
    }

    @PreDestroy
    public void destroy(){
        System.out.println("destroy the prdbean");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<SubPrdBean> getSubPrdBeans() {
        return subPrdBeans;
    }

    public void setSubPrdBeans(List<SubPrdBean> subPrdBeans) {
        this.subPrdBeans = subPrdBeans;
    }
}
