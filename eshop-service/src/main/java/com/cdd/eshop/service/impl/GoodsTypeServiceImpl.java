package com.cdd.eshop.service.impl;

import com.alibaba.fastjson.JSON;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.GoodsType;
import com.cdd.eshop.bean.vo.GoodsTypeTreeVO;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.mapper.GoodsTypeRepository;
import com.cdd.eshop.service.GoodsTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品类型服务实现类
 *
 * @author quan
 * @date 2020/12/22
 */

@Slf4j
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {


    @Autowired
    GoodsTypeRepository goodsTypeRepository;

    @Override
    public ResponseDTO listGoodsType(String parentTypeCode) {

        GoodsType type = new GoodsType();
        type.setParentTypeCode(parentTypeCode);
        type.setIsDelete(false);
        List<GoodsType> list = goodsTypeRepository.findAll(Example.of(type));
        return ResponseDTO.success().data(list);
    }

    @Override
    public ResponseDTO saveOrUpdateGoodsType(GoodsType type) {
        type.setIsDelete(false);
        GoodsType old = null;
        try {
            //todo 这里暂时随机一个code，以后再搞，本想子类code前缀串是父类
            if (type.getTypeId() == null){
                type.setTypeCode(UUID.randomUUID().toString().substring(0,5));
                old = type;
            }else{
                Optional<GoodsType> optional = goodsTypeRepository.findById(type.getTypeId());
                if (!optional.isPresent()){
                    return ResponseDTO.error(StatusEnum.PARAM_ERROR,"id不存在！");
                }else {
                    old = optional.get();
                    if (type.getTypeDesc()!=null){
                        old.setTypeDesc(type.getTypeDesc());
                        old.setTypeName(type.getTypeName());
                    }
                }
            }

            goodsTypeRepository.saveAndFlush(old);
            return ResponseDTO.success().withKeyValueData("typeId",type.getTypeId());
        }catch (Exception e){
            log.info("saveOrUpdateGoodsType===> {} ,type ={}",e.getCause().getMessage(), JSON.toJSONString(type));
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }

    }

    @Override
    public ResponseDTO batchSaveOrUpdateGoodsType(List<GoodsType> goodsTypeList) {

        if (null == goodsTypeList || goodsTypeList.isEmpty()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"列表为空！");
        }

        try {

            // 删除标志位置空
            goodsTypeList.forEach(e->e.setIsDelete(null));
            goodsTypeRepository.saveAll(goodsTypeList);
            goodsTypeRepository.flush();

            //返回Id列表
            List<Integer> typeIds = goodsTypeList.stream()
                    .map(GoodsType::getTypeId)
                    .collect(Collectors.toList());

            return ResponseDTO.success().withKeyValueData("typeIdList",typeIds);

        }catch (Exception e){
            log.info("batchSaveOrUpdateGoodsType===> {} ,types ={}",e.getCause().getMessage(), JSON.toJSONString(goodsTypeList));
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }
    }

    @Override
    public ResponseDTO deleteGoodsTypeById(Integer typeId) {

        GoodsType type = new GoodsType();
        type.setTypeId(typeId);
        type.setIsDelete(Boolean.TRUE);
        try{
            goodsTypeRepository.saveAndFlush(type);
            return ResponseDTO.success();
        }catch (Exception e){
            log.info("deleteGoodsTypeById===> {} ,typeId ={}",e.getCause().getMessage(), typeId);
            return ResponseDTO.error().msg(e.getCause().getMessage());
        }
    }

    @Override
    public ResponseDTO getGoodsTypeById(Integer typeId) {
        return ResponseDTO.success().data(goodsTypeRepository.getOne(typeId));
    }

    @Override
    public ResponseDTO treeType(String parentTypeCode) {

        //typeId = 0 为根结点
        if (parentTypeCode == null){
            parentTypeCode = "0";
        }

        //查找父节点
        GoodsType queryCondition = new GoodsType();
        queryCondition.setIsDelete(Boolean.FALSE);
        queryCondition.setTypeCode(parentTypeCode);

        Optional<GoodsType> parentType = goodsTypeRepository.findOne(Example.of(queryCondition));
        if (!parentType.isPresent()){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"此父节点不存在或已删除！");
        }


        //查找子节点
        GoodsType condition = new GoodsType();
        condition.setIsDelete(Boolean.FALSE);
        List<GoodsType> typeList = goodsTypeRepository.findAll(Example.of(condition));

        //构造map，减少遍历
        Map<String,List<GoodsType>> cacheMap = new HashMap<>();

        typeList.forEach(type->{
            String parentCode = type.getParentTypeCode();
            if (cacheMap.containsKey(parentCode)){
                cacheMap.get(parentCode).add(type);
            }else {
                List<GoodsType> list = new LinkedList<>();
                list.add(type);
                cacheMap.put(parentCode,list);
            }

        });

        //父节点入栈
        Stack<GoodsTypeTreeVO> stack = new Stack<>();
        GoodsTypeTreeVO parentVO = new GoodsTypeTreeVO();
        BeanUtils.copyProperties(parentType.get(),parentVO);
        stack.push(parentVO);


        //构造树
        while (!stack.isEmpty()){

            //出栈
            GoodsTypeTreeVO parent = stack.pop();

            List<GoodsTypeTreeVO> childrenVOList = new LinkedList<>();
            if (cacheMap.containsKey(parent.getTypeCode())){

                List<GoodsType> childrenList = cacheMap.get(parent.getTypeCode());
                for (GoodsType child : childrenList) {
                    GoodsTypeTreeVO vo = new GoodsTypeTreeVO();
                    BeanUtils.copyProperties(child,vo);
                    childrenVOList.add(vo);

                    //入栈
                    stack.push(vo);
                }
                parent.setChildren(childrenVOList);
            }

        }
        return ResponseDTO.success().data(parentVO);

    }
}
