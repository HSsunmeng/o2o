package com.o2o.entity;

import java.util.Date;
/**
 * 微信账号
 * */
public class WechatAuth {
    //id
    private Long wechatAuthId;
    //微信账号和公众号绑定的唯一标识
    private String openId;
    //创建时间
    private Date createTime;
    //用户信息
    private PersionInfo persionInfo;

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersionInfo getPersionInfo() {
        return persionInfo;
    }

    public void setPersionInfo(PersionInfo persionInfo) {
        this.persionInfo = persionInfo;
    }
}
