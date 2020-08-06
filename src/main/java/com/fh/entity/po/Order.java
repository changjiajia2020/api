package com.fh.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@TableName("shop_order2")
public class Order {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("addressId")
    private Integer addressId;
    @TableField("payType")
    private Integer payType;
    @TableField("proTypeCount")
    private Integer proTypeCount;   // 啥意思啊
    @TableField("subtotal")
    private BigDecimal subtotal;
    @TableField("payStatus")
    private Integer payStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")	// springMVC绑定报错
    @TableField("createDate")
    private Date createDate;

    @TableField("vipId")
    private Integer vipId;  // 收件人   冗余字段   为了在订单列表页面。展示收件人
    @TableField("proName")
    private Integer proName;  // 商品名称   冗余字段   为了在订单列表页面。展示收件人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getProTypeCount() {
        return proTypeCount;
    }

    public void setProTypeCount(Integer proTypeCount) {
        this.proTypeCount = proTypeCount;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getProName() {
        return proName;
    }

    public void setProName(Integer proName) {
        this.proName = proName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", addressId=" + addressId +
                ", payType=" + payType +
                ", proTypeCount=" + proTypeCount +
                ", subtotal=" + subtotal +
                ", payStatus=" + payStatus +
                ", createDate=" + createDate +
                ", vipId=" + vipId +
                ", proName=" + proName +
                '}';
    }
}
