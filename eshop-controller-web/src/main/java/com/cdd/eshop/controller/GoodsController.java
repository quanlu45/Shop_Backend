package com.cdd.eshop.controller;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.service.GoodsService;
import com.cdd.eshop.service.GoodsTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品控制器
 *
 * @author quan
 * @date 2020/12/29
 */
@RestController
@RequestMapping("/v1/goods")
public class GoodsController extends BaseController {

    @Autowired
    GoodsTypeService goodsTypeService;

    @Autowired
    GoodsService goodsService;

    @GetMapping("/search")
    @ApiOperation("使用商品名称搜索商品")
    public ResponseDTO searchGoods(@RequestParam(value = "goodsName",required = true)String goodsName,
                                   @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                                   @RequestParam(value = "pageSize",required = false)Integer pageSize){

        return goodsService.searchGoodsByName(
                goodsName,
                null == pageNumber? this.defaultPageNumber : pageNumber,
                null == pageSize? this.defaultPageSize : pageSize);
    }

    @GetMapping("/list")
    @ApiOperation("通过商品类型列出商品")
    public ResponseDTO listGoodsByTypeId(@RequestParam(value = "typeCode",required = false)String typeCode,
                                         @RequestParam(value = "pageNumber",required = false)Integer pageNumber,
                                         @RequestParam(value = "pageSize",required = false)Integer pageSize){
        return goodsService.listGoodsByTypeCode(
                typeCode,
                null == pageNumber? this.defaultPageNumber : pageNumber,
                null == pageSize? this.defaultPageSize : pageSize);
    }

    @ApiOperation("查询商品的详细信息")
    @GetMapping("/detail")
    public ResponseDTO getGoodsDetailById(@RequestParam(value = "goodsId",required = true)Integer goodsId) {
        return goodsService.getGoodsById(goodsId);
    }

    @GetMapping("/type/tree")
    @ApiOperation(value = "树形结构列出商品类型",notes = "parentTypeId 为0或者不传都为从根结点开始")
    public ResponseDTO treeTypes(@RequestParam(value = "parentTypeId",required = false) String parentTypeCode) {
        return goodsTypeService.treeType(parentTypeCode);
    }

    @GetMapping("/type/list")
    @ApiOperation(value = "列表形式列出商品类型",notes = "parentTypeId 为0是根结点，不传Code则列出全部")
    public ResponseDTO listTypes(@RequestParam(value = "parentTypeCode",required = false)String parentTypeCode) {
        return goodsTypeService.listGoodsType(parentTypeCode);
    }

}
