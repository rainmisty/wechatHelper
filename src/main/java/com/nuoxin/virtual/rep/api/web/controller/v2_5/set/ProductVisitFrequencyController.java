package com.nuoxin.virtual.rep.api.web.controller.v2_5.set;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationFrequencyService;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductVisitFrequencyService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationFrequencyUpdateRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductVisitFrequencyRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.PotentialClassificationResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFrequencyResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductVisitFrequencyResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tiancun
 * @date 2019-01-04
 */
@RestController
@Api(value = "V2.5医生拜访频次设置")
@RequestMapping(value = "/product/frequency")
public class ProductVisitFrequencyController {

    @Resource
    private ProductClassificationFrequencyService productClassificationFrequencyService;

    @Resource
    private ProductVisitFrequencyService productVisitFrequencyService;

    @ApiOperation(value = "新增频次需要的选项列表", notes = "新增频次需要的选项列表")
    @PostMapping(value = "/potential/classification/list/{productId}")
    public DefaultResponseBean<PotentialClassificationResponseBean> getPotentialClassification(@PathVariable(value = "productId") Long productId) {

        PotentialClassificationResponseBean potentialClassification = productClassificationFrequencyService.getPotentialClassification(productId);
        DefaultResponseBean<PotentialClassificationResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(potentialClassification);
        return responseBean;
    }


    @ApiOperation(value = "医生分型拜访频次新增", notes = "医生分型拜访频次新增")
    @PostMapping(value = "/classification/add")
    public DefaultResponseBean<Boolean> add(@RequestBody ProductClassificationFrequencyRequestBean bean) {

        productClassificationFrequencyService.add(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "医生分型拜访频次编辑", notes = "医生分型拜访频次编辑")
    @PostMapping(value = "/classification/update")
    public DefaultResponseBean<Boolean> update(@RequestBody ProductClassificationFrequencyUpdateRequestBean bean) {

        productClassificationFrequencyService.update(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "医生分型拜访频次删除", notes = "医生分型拜访频次删除")
    @GetMapping(value = "/classification/delete/{batchNo}")
    public DefaultResponseBean<Boolean> delete(@PathVariable(value = "batchNo") String batchNo) {

        productClassificationFrequencyService.deleteByBatchNo(batchNo);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "医生分型拜访频次列表", notes = "医生分型拜访频次列表")
    @GetMapping(value = "/classification/list/{productId}")
    public DefaultResponseBean<Map<String,Object>> getList(@PathVariable(value = "productId") Long productId) {
        Map<String,Object> map = new HashMap<>();
        List<TableHeader> tableHeaderList = productClassificationFrequencyService.getTableHeaderList(productId);
        map.put("tableHeaders", tableHeaderList);

        List<ProductClassificationFrequencyResponseBean> productClassificationFrequencyList = productClassificationFrequencyService.getProductClassificationFrequencyList(productId);
        map.put("content", productClassificationFrequencyList);

        DefaultResponseBean<Map<String,Object>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(map);
        return responseBean;
    }


    @ApiOperation(value = "医生拜访频次新增", notes = "医生分型拜访频次新增")
    @PostMapping(value = "/visit/add")
    public DefaultResponseBean<Boolean> addProductVisitFrequency(@RequestBody ProductVisitFrequencyRequestBean bean) {

        productVisitFrequencyService.addProductVisitFrequency(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "医生拜访频次列表", notes = "医生分型拜访频次列表")
    @GetMapping(value = "/visit/list/{productId}")
    public DefaultResponseBean<ProductVisitFrequencyResponseBean> getProductVisitFrequency(@PathVariable(value = "productId") Long productId) {

        ProductVisitFrequencyResponseBean productVisitFrequency = productVisitFrequencyService.getProductVisitFrequency(productId);
        DefaultResponseBean<ProductVisitFrequencyResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productVisitFrequency);
        return responseBean;
    }


}
