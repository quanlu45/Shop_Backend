package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 订单存储库
 *
 * @author quan
 * @date 2021/01/06
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
