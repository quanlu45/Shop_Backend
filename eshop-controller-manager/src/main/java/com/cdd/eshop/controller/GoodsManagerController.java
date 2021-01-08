package com.cdd.eshop.controller;

import com.cdd.eshop.bean.bo.BatchGoodsTypeBO;
import com.cdd.eshop.bean.bo.GoodsBO;
import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.bean.po.GoodsType;
import com.cdd.eshop.common.BaseController;
import com.cdd.eshop.common.StatusEnum;
import com.cdd.eshop.service.GoodsService;
import com.cdd.eshop.service.GoodsTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品类型管理控制器
 *
 * @author quan
 * @date 2021/01/07
 */

@RestController
@RequestMapping("/v1/goods")
public class GoodsManagerController extends BaseController {

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


    @ApiOperation("上架商品")
    @PostMapping("/on")
    public ResponseDTO putOn(@RequestParam(name = "goodsId") Integer goodsId) {
        return goodsService.putOn(goodsId);
    }


    @ApiOperation("下架商品")
    @PostMapping("/off")
    public ResponseDTO putOff(@RequestParam(name = "goodsId") Integer goodsId) {
        return goodsService.putOff(goodsId);
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    public ResponseDTO deleteGoodsById(Integer goodsId) {
        return goodsService.deleteGoodsById(goodsId);
    }

    @ApiOperation(value ="新增或更新商品", notes = "不传id则为新增")
    @PostMapping("/saveOrUpdate")
    ResponseDTO saveOrUpdateGoods(@RequestBody GoodsBO goodsBO) {
        return goodsService.saveOrUpdateGoods(goodsBO);
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

    @ApiOperation(value ="新增或更新商品类型", notes = "不传id则为新增")
    @PostMapping("/type/saveOrUpdate")
    ResponseDTO saveOrUpdateGoodsType(@RequestBody GoodsType goodsType) {
        return goodsTypeService.saveOrUpdateGoodsType(goodsType);
    }

    @ApiOperation(value ="批量保存或更新商品类型", notes = "不传id则为新增")
    @PostMapping("/type/batchSaveOrUpdate")
    ResponseDTO batchSaveOrUpdateGoodsType(@RequestBody BatchGoodsTypeBO batchGoodsTypeBO) {

        if (batchGoodsTypeBO.getGoodsTypeList() == null || batchGoodsTypeBO.getGoodsTypeList().size()<1){
            return ResponseDTO.error(StatusEnum.PARAM_ERROR,"参数为空！");
        }
        return goodsTypeService.batchSaveOrUpdateGoodsType(batchGoodsTypeBO.getGoodsTypeList());
    }

    @ApiOperation(value ="删除更新商品类型")
    @PostMapping("/type/delete")
    ResponseDTO deleteGoodsTypeById(@RequestParam(value = "typeId",required = true) Integer typeId) {
        return goodsTypeService.deleteGoodsTypeById(typeId);
    }


    @ApiOperation(value ="获取商品类型详情")
    @PostMapping("/type/detail")
    ResponseDTO getGoodsTypeById(@RequestParam(value = "typeId",required = true) Integer typeId) {
        return goodsTypeService.getGoodsTypeById(typeId);
    }
}
