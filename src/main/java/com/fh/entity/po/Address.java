package com.fh.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 使用mybatis_plus
 */
@TableName("shop_address2")   // value写不写都不会影响
public class Address {

    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "vipId")
    private String vipId;
    @TableField(value = "name")
    private String name;
    @TableField(value = "detailAddress")
    private String detailAddress;   // 详细地址
    @TableField(value = "areaIds")
    private String areaIds;     // 地区
    @TableField(value = "state")
    private Integer state;  // 是否选中
    @TableField(value = "iphone")
    private String iphone;     // 电话号码
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")	// springMVC绑定报错
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "createDate")
    private Date createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")	// springMVC绑定报错
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "updateDate")
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", vipId='" + vipId + '\'' +
                ", name='" + name + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", areaIds='" + areaIds + '\'' +
                ", state=" + state +
                ", iphone='" + iphone + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
