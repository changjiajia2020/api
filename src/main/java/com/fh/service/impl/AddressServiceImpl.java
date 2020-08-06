package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.AddressDao;
import com.fh.entity.po.Address;
import com.fh.entity.po.Register;
import com.fh.entity.vo.AddressInfo;
import com.fh.service.AddressService;
import com.fh.utils.redis.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private HttpServletRequest request;

    // 查询地区的详细信息    只查询出页面需要展示的数据即可
    @Override
    public List<AddressInfo> queryAddressInfoList() {
        /**
         *  1. 获取登录人
         *  2. 根据当前登录人，查询收货地址(隐含：条件查询)
         *  3. 查询数据库的数据
         *  4. 但是需求不是数据库中的数据(即:两者查出的数据有差异)
         *      例如：详细地址   Address中的数据不满足
         *      正确写法：省市县+具体地址
         *         AddressInfo 中 detailAddressInfo才符合要求
         *  5. 处理真正想要的数据(即:AddressInfo 业务bean)
         */
        // 获取登录人    map  实体类！！！！！！不用做增删改查了 所以直接写map
        // "login_user" 与登录拦截器中 保持一致
/*        Map login_user = (Map) request.getAttribute("login_user");
        // "iphone" 与vipController  user.put("iphone",iphone);保持一致
        String iphone = (String) login_user.get("iphone");*/
        Register login_user = (Register) request.getAttribute("login_user");
        String iphone = login_user.getIphone();
        // 根据登录人，查询收货地址
        /**
         * abstract class Wrapper <T> implements
         *      Wrapper是抽象类     QueryWrapper接口
         */
        QueryWrapper<Address> qw = new QueryWrapper();
        qw.eq("vipId",iphone);  // mybatis_plus中的条件查询   eq精确查询
        // 查询数据库中的数据
        List<Address> addresses = addressDao.selectList(qw);
        // 业务bean
        List<AddressInfo>  addInfoList = new ArrayList<>();
        for (int i = 0; i < addresses.size(); i++) {
            AddressInfo temp = new AddressInfo();
            // 数据库中的数据  获取每一个元素
            Address address = addresses.get(i);
            temp.setId(address.getId());
            temp.setName(address.getName());
            temp.setIphone(address.getIphone());
            temp.setIsCheck(address.getState());
            // temp.setDetailAddressInfo(address.getAreaIds()+address.getDetailAddress());
            // address.getAreaIds()  获取1,2,3  无法获取中文名称   所以：要在redisUser中转换吗？
            String areaIds = address.getAreaIds();
            String areaNames = RedisUse.getAreaNames(areaIds);
            temp.setDetailAddressInfo(areaNames + address.getDetailAddress());
            addInfoList.add(temp);

        }
        return addInfoList;
    }
}
