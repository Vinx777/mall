package com.ivinx.mall.service;

import com.github.pagehelper.PageInfo;
import com.ivinx.mall.vo.ProductDetailVo;
import com.ivinx.mall.vo.ResponseVo;

/**
 * Created by Vinx
 */
public interface IProductService {

	ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

	ResponseVo<ProductDetailVo> detail(Integer productId);
}
