package com.ivinx.mall.controller;

import com.ivinx.mall.consts.MallConst;
import com.ivinx.mall.form.CartAddForm;
import com.ivinx.mall.form.CartUpdateForm;
import com.ivinx.mall.pojo.User;
import com.ivinx.mall.service.ICartService;
import com.ivinx.mall.vo.CartVo;
import com.ivinx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Vinx
 */
@RestController
public class CartController {

	@Autowired
	private ICartService cartService;

	@GetMapping("/carts")
	public ResponseVo<CartVo> list(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.list(user.getId());
	}

	@PostMapping("/carts")
	public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
								  HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.add(user.getId(), cartAddForm);
	}

	@PutMapping("/carts/{productId}")
	public ResponseVo<CartVo> update(@PathVariable Integer productId,
									 @Valid @RequestBody CartUpdateForm form,
									 HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.update(user.getId(), productId, form);
	}

	@DeleteMapping("/carts/{productId}")
	public ResponseVo<CartVo> delete(@PathVariable Integer productId,
									 HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.delete(user.getId(), productId);
	}

	@PutMapping("/carts/selectAll")
	public ResponseVo<CartVo> selectAll(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.selectAll(user.getId());
	}

	@PutMapping("/carts/unSelectAll")
	public ResponseVo<CartVo> unSelectAll(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.unSelectAll(user.getId());
	}

	@GetMapping("/carts/products/sum")
	public ResponseVo<Integer> sum(HttpSession session) {
		User user = (User) session.getAttribute(MallConst.CURRENT_USER);
		return cartService.sum(user.getId());
	}
}
