package com.quan.wechat.controller;

import com.quan.wechat.entity.ProductCategory;
import com.quan.wechat.entity.ProductInfo;
import com.quan.wechat.service.CategoryService;
import com.quan.wechat.service.ProductService;
import com.quan.wechat.util.ResultVOUtil;
import com.quan.wechat.vo.ProductInfoVO;
import com.quan.wechat.vo.ProductVO;
import com.quan.wechat.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * \* @Author: Quan
 * \* @Date: 2019/8/7 0007
 * \ 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
       //1.查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
         //2.查询所有的上架的商品对应有的类目(传统写法)
      /*  List<Integer>  categotyTpyeList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            categotyTpyeList.add(productInfo.getCategoryType());
        }*/
        //lambda表达式
        List<Integer>  categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        //4.根据类目查出类目表
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());
            //5,上架的商品在类目中的加进去
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO  productInfoVO  = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
       return ResultVOUtil.success(productVOList);
    }
}
