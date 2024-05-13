package com.ivinx.mall.service;

import com.ivinx.mall.form.CartAddForm;
import com.ivinx.mall.form.CartUpdateForm;
import com.ivinx.mall.pojo.Cart;
import com.ivinx.mall.vo.CartVo;
import com.ivinx.mall.vo.ResponseVo;

import java.util.List;

/**
 * Created by Vinx
 */
public interface ICartService {

	ResponseVo<CartVo> add(Integer uid, CartAddForm form);

	ResponseVo<CartVo> list(Integer uid);

	ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

	ResponseVo<CartVo> delete(Integer uid, Integer productId);

	ResponseVo<CartVo> selectAll(Integer uid);

	ResponseVo<CartVo> unSelectAll(Integer uid);

	ResponseVo<Integer> sum(Integer uid);

	List<Cart> listForCart(Integer uid);
}
