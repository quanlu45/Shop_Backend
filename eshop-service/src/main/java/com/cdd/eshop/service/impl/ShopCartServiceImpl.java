package com.cdd.eshop.service.impl;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.Goods;
import com.cdd.eshop.bean.po.GoodsImg;
import com.cdd.eshop.bean.po.ShopCart;
import com.cdd.eshop.bean.vo.ShopCartItemVO;
import com.cdd.eshop.mapper.GoodsImgRepository;
import com.cdd.eshop.mapper.GoodsRepository;
import com.cdd.eshop.mapper.ShopCartRepository;
import com.cdd.eshop.service.ShopCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * 商店车服务实现类
 *
 * @author quan
 * @date 2021/01/06
 */
@Service
@Slf4j
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    ShopCartRepository shopCartRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    GoodsImgRepository goodsImgRepository;

    @Override
    public ResponseDTO addItem(ShopCart shopCartItem) {
        try {
            List<ShopCart> itemList = shopCartRepository.findAllByUserId(shopCartItem.getUserId());

            //已经存在，则合并
            if (itemList.size()>0){
                for (ShopCart item : itemList){
                    if (item.getGoodsId().equals(shopCartItem.getGoodsId())){
                        item.setAmount(item.getAmount()+1);
                        shopCartRepository.saveAndFlush(item);
                        return ResponseDTO.success().withKeyValueData("itemId",item.getCartId());
                    }
                }
            }

            shopCartRepository.saveAndFlush(shopCartItem);
            return ResponseDTO.success().withKeyValueData("itemId",shopCartItem.getCartId());
        }catch (Exception e){
            return ResponseDTO.error().msg(e.getMessage());
        }
    }

    @Override
    public ResponseDTO deleteItem(Integer userId, Integer shopCartId) {
        shopCartRepository.deleteByUserIdAndCartId(userId,shopCartId);
        return ResponseDTO.success();
    }

    @Override
    public ResponseDTO listItem(Integer userId) {

        List<ShopCart> shopCartList = shopCartRepository.findAllByUserId(userId);

        if (shopCartList.size()<1){
            return ResponseDTO.success()
                    .msg("购物车为空！")
                    .withKeyValueData("cartItemList","[]");
        }

        HashMap<Integer,ShopCartItemVO> voHashMap = new HashMap<>();

        //填充购物车数量，id
        List<Integer> goodsIds = new LinkedList<>();
        for (ShopCart shopCart : shopCartList){
            goodsIds.add(shopCart.getGoodsId());
            ShopCartItemVO vo = new ShopCartItemVO();
            vo.setAmount(shopCart.getAmount());
            vo.setShopCartId(shopCart.getCartId());
            voHashMap.put(shopCart.getGoodsId(),vo);
        }

        //填充商品详情
        List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(goodsIds);
        for (Goods goods : goodsList){
            ShopCartItemVO vo = voHashMap.get(goods.getGoodsId());
            vo.setGoodsName(goods.getGoodsName());
            vo.setPrice(goods.getGoodsPrice());
            vo.setGoodsId(goods.getGoodsId());
        }

        //填充商品图片
        List<GoodsImg> goodsImgList =goodsImgRepository.findAllByGoodsIdIn(goodsIds);

        for (GoodsImg img: goodsImgList){
            ShopCartItemVO vo = voHashMap.get(img.getGoodsId());
            if (vo.getGoodsImgUrl()!=null) continue;;
            vo.setGoodsImgUrl(img.getImgUrl());
        }
        return ResponseDTO.success().withKeyValueData("cartItemList",voHashMap.values());
    }

    @Override
    public ResponseDTO updateItem(ShopCart shopCartItem) {
        shopCartRepository.saveAndFlush(shopCartItem);
        return ResponseDTO.success();
    }
}
