package com.lyne.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nn_liu
 * @Created 2017-07-28-18:15
 */

@Table(name = "author")
public class AuthorEntiry {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Time")
    private String lastTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return String.format("AuthorEntiry{id=%d, firstName=%s, lastTime=%s}", id, firstName, lastTime);
    }

}
