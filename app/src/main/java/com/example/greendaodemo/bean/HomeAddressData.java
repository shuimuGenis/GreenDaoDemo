package com.example.greendaodemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author shuimu{lwp}
 * @time 2020/3/25  11:28
 * @desc
 */
@Entity
public class HomeAddressData {
    @Id
    private Long id;
    @Property(nameInDb = "address")
    private String address;
    @Generated(hash = 911354543)
    public HomeAddressData(Long id, String address) {
        this.id = id;
        this.address = address;
    }
    @Generated(hash = 692059228)
    public HomeAddressData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
