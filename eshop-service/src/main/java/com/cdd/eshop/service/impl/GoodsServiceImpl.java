package com.cdd.eshop.service.impl;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Goods;
import com.cdd.eshop.bean.po.GoodsType;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.GoodsRepository;
import com.cdd.eshop.mapper.GoodsTypeRepository;
import com.cdd.eshop.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

/**
 * 商品服务实现类
 *
 * @author quan
 * @date 2020/12/29
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    GoodsTypeRepository goodsTypeRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Override
    public ResponseDTO getGoodsById(Integer goodsId) {

        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);
        if (!goodsOptional.isPresent() || goodsOptional.get().getStatus() == 3){
            return ResponseDTO.error().msg("商品不存在或已删除！");
        }
        return ResponseDTO.success().data(goodsOptional.get());
    }

    @Override
    public ResponseDTO searchGoodsByName(String goodsName,Integer pageNumber,Integer pageSize) {

        //组装分页参数
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC,"goodsId");

        Page<Goods> goodsPage = goodsRepository.findAll((root, query, builder) -> {
            Predicate[] pre = new Predicate[2];
            //未删除
            pre[0] = builder.notEqual(root.get("status").as(Short.class),3);
            //模糊查询
            pre[1] = builder.like(root.get("goodsName").as(String.class),"%"+goodsName+"%");
            return query.where(pre).getRestriction();
        },pageable);

        return ResponseDTO.success().data(goodsPage);
    }

    @Override
    public ResponseDTO listGoodsByTypeCode(String goodsTypeCode, Integer pageNumber, Integer pageSize) {

        if (null == goodsTypeCode){
            goodsTypeCode = "0";
        }

        GoodsType queryCondition = new GoodsType();
        queryCondition.setIsDelete(Boolean.FALSE);
        Optional<GoodsType> goodsTypeOptional = goodsTypeRepository.findOne(Example.of(queryCondition));

        if (!goodsTypeOptional.isPresent()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"此商品类型code不存在！");
        }

        //组装分页参数
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC,"goodsId");

        final String regx = goodsTypeCode + "%";
        Page<Goods> goodsPage = goodsRepository.findAll((root, query, builder) -> {
            Predicate[] pre = new Predicate[2];
            //未删除
            pre[0] = builder.notEqual(root.get("status").as(Short.class),3);
            //商品类型
            pre[1] = builder.like(root.get("goodsTypeCode").as(String.class), regx);
            return query.where(pre).getRestriction();
        },pageable);

        return ResponseDTO.success().data(goodsPage);
    }


    private ResponseDTO changeStatus(Integer goodsId,Short targetStatus){

        Optional<Goods> goodsOptional =goodsRepository.findById(goodsId);
        if (!goodsOptional.isPresent() || goodsOptional.get().getStatus() == 3){
            return ResponseDTO.error().msg("不存在此商品或者已经被删除！");
        }

        if (goodsOptional.get().getStatus() == targetStatus){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"已经是此状态，无需操作!");
        }

        try{
            Goods goods = goodsOptional.get();
            goods.setStatus(targetStatus);
            goodsRepository.saveAndFlush(goods);

        }catch (Exception e){
            log.error("changeStatus ==> targetStatus ={}, {}" ,targetStatus,e.getCause().getMessage());
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }

        return  ResponseDTO.success().msg("操作成功!");
    }

    @Override
    public ResponseDTO deleteGoodsById(Integer goodsId) {
        return this.changeStatus(goodsId, (short) 3);
    }

    @Override
    public ResponseDTO putOn(Integer goodsId) {
        return this.changeStatus(goodsId,(short)1);
    }

    @Override
    public ResponseDTO putOff(Integer goodsId) {
        return this.changeStatus(goodsId,(short)2);
    }

}
