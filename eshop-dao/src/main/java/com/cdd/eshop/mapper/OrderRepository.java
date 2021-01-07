package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单存储库
 *
 * @author quan
 * @date 2021/01/06
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    @Query("select o from Order  o " +
            " where (:userId is null or o.userId =:userId) " +
            "and (:status is null or o.status = :status )")
    List<Order> findAllByUserIdAndStatus(@Param("userId")Integer userId,@Param("status")Short status);
}
