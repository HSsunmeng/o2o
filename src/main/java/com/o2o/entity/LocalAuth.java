package com.o2o.entity;

import java.util.Date;
/**
 * 本地账号
 * */
public class LocalAuth {
    //id
    private Long LocalAuthId;
    //账号信息
    private String userName;
    //密码
    private String password;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lasrEditTime;
    //用户信息
    private PersionInfo persionInfo;

    public Long getLocalAuthId() {
        return LocalAuthId;
    }

    public void setLocalAuthId(Long localAuthId) {
        LocalAuthId = localAuthId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLasrEditTime() {
        return lasrEditTime;
    }

    public void setLasrEditTime(Date lasrEditTime) {
        this.lasrEditTime = lasrEditTime;
    }

    public PersionInfo getPersionInfo() {
        return persionInfo;
    }

    public void setPersionInfo(PersionInfo persionInfo) {
        this.persionInfo = persionInfo;
    }
}
