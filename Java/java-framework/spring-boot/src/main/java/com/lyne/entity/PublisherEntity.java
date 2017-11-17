package com.lyne.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nn_liu
 * @Created 2017-07-28-18:19
 */

@Table(name = "publisher")
public class PublisherEntity {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("PublisherEntity{id=%d, name=%s},",id, name);
    }
}
