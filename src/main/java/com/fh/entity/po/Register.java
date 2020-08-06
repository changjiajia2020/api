package com.fh.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Register {

    private Integer id;
    private String name;    // 姓名
    private Integer sex;    // 性别
    private String iphone;  // 手机号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;  // 生日
    private String areaIds;     // 地区   三级联动
    private String imgPath;     // 图片

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Register{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", iphone='" + iphone + '\'' +
                ", birthday=" + birthday +
                ", areaIds='" + areaIds + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
