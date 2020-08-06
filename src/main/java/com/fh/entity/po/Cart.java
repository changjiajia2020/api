package com.fh.entity.po;

import java.math.BigDecimal;

public class Cart {

    private Integer id;
    private String name;
    private BigDecimal price;
    private boolean state;    // 是否选中     默认被选中
    private String imgPath;     // 图片路径
    private Integer count;      // 数量
    private BigDecimal subtotal;      // 总计

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", state=" + state +
                ", imgPath='" + imgPath + '\'' +
                ", count=" + count +
                ", subtotal=" + subtotal +
                '}';
    }
}
