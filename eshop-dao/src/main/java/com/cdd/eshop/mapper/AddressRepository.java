package com.cdd.eshop.mapper;

import com.cdd.eshop.bean.po.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 地址库
 *
 * @author quan
 * @date 2020/12/21
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {


    /**
     * 查询默认地址
     *
     * @param userId 用户Id
     * @return {@link Address}
     */
    @Query("select a from Address a where a.userId =:userId and a.status =1 ")
    Address selectDefaultAddress(@Param("userId") Integer userId);


    /**
     * 选择所有通过用户Id
     *
     * @param userId 用户Id
     * @return {@link List<Address>}
     */
    @Query("select a from Address a where a.userId =:userId and a.status in (1,2) ")
    List<Address> selectAllByUserId(@Param("userId") Integer userId);

    /**
     * 通过Id除地址
     *
     * @param addressId 地址Id
     * @return int
     */
    @Modifying
    @Query("update Address a set a.status = 2 where a.userId =:addressId")
    int deleteAddressById(@Param("addressId")Integer addressId);

}
