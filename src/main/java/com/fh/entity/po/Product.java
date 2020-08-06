package com.fh.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("shop_product2")
public class Product {

    @TableId(value = "proId",type = IdType.AUTO)
    private Integer proId;
    @TableField(value = "name") // 表示：与数据库字段信息一致
    private String name;
    @TableField(value = "imgPath")
    private String imgPath;
    @TableField(value = "price")
    private Double price;
    @TableField(value = "isHot")
    private int isHot;  // 是否热销 int类型，默认是0  1热销
    @TableField(value = "isUp")
    private int isUp;  // 是否上架  1 上架 0 下架  默认是0 按常理说：默认是下架
    @DateTimeFormat(pattern = "yyyy-MM-dd")	// springMVC绑定报错
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField(value = "productDate")
    private Date productDate;
    @TableField(value = "typeId")
    private String typeId;
    @TableField(value = "areaIds")
    private String areaIds;
    @TableField(value = "stock")
    private Integer stock;

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "proId=" + proId +
                ", name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", price=" + price +
                ", isHot=" + isHot +
                ", isUp=" + isUp +
                ", productDate=" + productDate +
                ", typeId='" + typeId + '\'' +
                ", areaIds='" + areaIds + '\'' +
                ", stock=" + stock +
                '}';
    }
}
