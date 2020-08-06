package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.entity.po.OrderProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderProductDao extends BaseMapper<OrderProduct> {

    // 批量新增订单信息   多个参数时，要加@Param  起一个标识的作用
    void batchAdd(@Param("list") List<OrderProduct> list, @Param("oid") Integer oid);
}
