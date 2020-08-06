package com.fh.entity.vo;

public class AddressInfo {

    /**
     * 只查询出页面需要展示的数据即可
     */
    private Integer id;
    /**
     *  登录人信息   用手机号登录的，为啥不直接用这个作为手机号码呢？
     *      因为：订单提交时，填写的电话号码  不一定是  登录用的手机号
     */
    // private Integer vipId;  // 登录人信息   可以理解为：登录时的手机号？
    private String name;
    private String detailAddressInfo;   // 详细地址     省市县+具体地址
    private String iphone;     // 电话号码
    private Integer isCheck;     // 是否默认被选中     1:选中状态  0:未选中状态

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

    public String getDetailAddressInfo() {
        return detailAddressInfo;
    }

    public void setDetailAddressInfo(String detailAddressInfo) {
        this.detailAddressInfo = detailAddressInfo;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detailAddressInfo='" + detailAddressInfo + '\'' +
                ", iphone='" + iphone + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
