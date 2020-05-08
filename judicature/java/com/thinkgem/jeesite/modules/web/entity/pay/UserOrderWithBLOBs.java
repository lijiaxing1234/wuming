package com.thinkgem.jeesite.modules.web.entity.pay;

public class UserOrderWithBLOBs extends UserOrder {
    private String orderInfo;

    private String payReuslt;

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPayReuslt() {
        return payReuslt;
    }

    public void setPayReuslt(String payReuslt) {
        this.payReuslt = payReuslt;
    }
}