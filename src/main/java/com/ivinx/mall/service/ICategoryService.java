package com.ivinx.mall.service;

import com.ivinx.mall.vo.CategoryVo;
import com.ivinx.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * Created by Vinx
 */
public interface ICategoryService {

	ResponseVo<List<CategoryVo>> selectAll();

	void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
