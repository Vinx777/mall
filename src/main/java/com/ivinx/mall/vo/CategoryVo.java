package com.ivinx.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Vinx
 */
@Data
public class CategoryVo {

	private Integer id;

	private Integer parentId;

	private String name;

	private Integer sortOrder;

	private List<CategoryVo> subCategories;
}
