package com.cdd.eshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cdd.eshop.bean.bo.GoodsBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Goods;
import com.cdd.eshop.bean.po.GoodsImg;
import com.cdd.eshop.bean.po.GoodsStatus;
import com.cdd.eshop.bean.po.GoodsType;
import com.cdd.eshop.bean.vo.GoodsVO;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.GoodsImgRepository;
import com.cdd.eshop.mapper.GoodsRepository;
import com.cdd.eshop.mapper.GoodsTypeRepository;
import com.cdd.eshop.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    GoodsImgRepository imgRepository;

    @Override
    public ResponseDTO getGoodsById(Integer goodsId) {

        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);
        if (!goodsOptional.isPresent() || goodsOptional.get().getStatus() == 3){
            return ResponseDTO.error().msg("商品不存在或已删除！");
        }
        List<GoodsImg> imgs = imgRepository.findAllByGoodsId(goodsId);
        GoodsVO vo = new GoodsVO();
        BeanUtils.copyProperties(goodsOptional.get(),vo);
        vo.setGoodsImgs(
                imgs.stream()
                        .map(GoodsImg::getImgUrl)
                        .collect(Collectors.toList())
        );

        return ResponseDTO.success().data(vo);
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

        List<Integer> goodsId = goodsPage.stream()
                .map(Goods::getGoodsId)
                .collect(Collectors.toList());

        List<GoodsImg> imgList = imgRepository.findAllByGoodsIdIn(goodsId);

        Page<GoodsVO> voPage = goodsPage.map(goods -> {
            GoodsVO vo = new GoodsVO();
            BeanUtils.copyProperties(goods,vo);
            List<String> imgs = new LinkedList<>();
            for (GoodsImg img:imgList){
                if (vo.getGoodsId().equals(img.getGoodsId())){
                    imgs.add(img.getImgUrl());
                }
            }
            vo.setGoodsImgs(imgs);
            return vo;
        });
        return ResponseDTO.success().data(voPage);
    }

    @Override
    public ResponseDTO listGoodsByTypeCode(String goodsTypeCode, Integer pageNumber, Integer pageSize) {

        if (null == goodsTypeCode){
            goodsTypeCode = "0";
        }

        GoodsType queryCondition = new GoodsType();
        queryCondition.setIsDelete(Boolean.FALSE);
        queryCondition.setTypeCode(goodsTypeCode);
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
        List<Integer> goodsId = goodsPage.stream()
                .map(Goods::getGoodsId)
                .collect(Collectors.toList());

        List<GoodsImg> imgList = imgRepository.findAllByGoodsIdIn(goodsId);

        Page<GoodsVO> voPage = goodsPage.map(goods -> {
            GoodsVO vo = new GoodsVO();
            BeanUtils.copyProperties(goods,vo);
            List<String> imgs = new LinkedList<>();
            for (GoodsImg img:imgList){
                if (vo.getGoodsId().equals(img.getGoodsId())){
                    imgs.add(img.getImgUrl());
                }
            }
            vo.setGoodsImgs(imgs);
            return vo;
        });
        return ResponseDTO.success().data(voPage);
    }


    private ResponseDTO changeStatus(Integer goodsId,Short targetStatus){

        Optional<Goods> goodsOptional =goodsRepository.findById(goodsId);
        if (!goodsOptional.isPresent() || goodsOptional.get().getStatus() == 3){
            return ResponseDTO.error().msg("不存在此商品或者已经被删除！");
        }

        if (goodsOptional.get().getStatus().equals(targetStatus)){
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
        return this.changeStatus(goodsId,GoodsStatus.DELETE.getCode());
    }

    @Override
    public ResponseDTO saveOrUpdateGoods(GoodsBO goodsBO) {

        log.debug("saveOrUpdateGoods ==> goodsId ={}", goodsBO.getGoodsId());
        //验证参数
        if (StringUtils.isBlank(goodsBO.getGoodsName())
                || null == goodsBO.getGoodsPrice()
                || null == goodsBO.getGoodsStock()){

            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"关键参数为空！");
        }
        if (null == goodsBO.getStatus()){
            goodsBO.setStatus(GoodsStatus.WAIT_ON.getCode());
        }
        try {
            Goods goods = new Goods();
            goods.setGoodsSold(0);
            BeanUtils.copyProperties(goodsBO,goods);


            boolean isUpdate = goods.getGoodsId()!=null;

            goodsRepository.saveAndFlush(goods);
            //新增商品图片
            List<String> imgUrls = goodsBO.getGoodsImgUrls();
            if (imgUrls !=null && imgUrls.size()>0){
               List<GoodsImg> goodsImgList = new LinkedList<>();
               imgUrls.forEach(img->{
                   GoodsImg goodsImg = new GoodsImg();
                   goodsImg.setImgUrl(img);
                   goodsImgList.add(goodsImg);
               });
               if (isUpdate){
                   imgRepository.deleteAllByGoodsId(goods.getGoodsId());
               }
               imgRepository.saveAll(goodsImgList);
               imgRepository.flush();
            }

            return ResponseDTO.success().withKeyValueData("goodsId",goodsBO.getGoodsId());
        }catch (Exception e){
            log.error("saveOrUpdateGoods ==> goods = {},{}", JSON.toJSONString(goodsBO),e.getCause().getMessage());
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }
    }

    @Override
    public ResponseDTO putOn(Integer goodsId) {
        return this.changeStatus(goodsId,GoodsStatus.ON.getCode());
    }

    @Override
    public ResponseDTO putOff(Integer goodsId) {
        return this.changeStatus(goodsId,GoodsStatus.OFF.getCode());
    }

}
