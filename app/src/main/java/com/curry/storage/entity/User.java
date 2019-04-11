package com.curry.storage.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by curry on 2016/10/8.
 */

@Entity
public class User {

    /**
     * 主键类型只能用long
     */
    @Id
    private Long id;
    private String name;
    @Transient
    private int tempData;

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

    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 873297011)
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
