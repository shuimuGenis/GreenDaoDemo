package com.example.greendaodemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author shuimu{lwp}
 * @time 2020/3/25  11:25
 * @desc
 */
@Entity
public class BookData {
    @Id
    private Long id;
    @Property
    private String name;
    /**
     * 表示userID字段的值将会等于UserData中主键ID的值
     */
    @NotNull
    private Long userId;

    @Generated(hash = 35671284)
    public BookData(Long id, String name, @NotNull Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @Generated(hash = 687480960)
    public BookData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
