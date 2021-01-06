package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 订单项存储库
 *
 * @author quan
 * @date 2021/01/06
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
