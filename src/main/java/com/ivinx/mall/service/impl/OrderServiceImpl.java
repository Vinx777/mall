package com.ivinx.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ivinx.mall.dao.OrderItemMapper;
import com.ivinx.mall.dao.OrderMapper;
import com.ivinx.mall.dao.ProductMapper;
import com.ivinx.mall.dao.ShippingMapper;
import com.ivinx.mall.enums.OrderStatusEnum;
import com.ivinx.mall.enums.PaymentTypeEnum;
import com.ivinx.mall.enums.ProductStatusEnum;
import com.ivinx.mall.enums.ResponseEnum;
import com.ivinx.mall.pojo.*;
import com.ivinx.mall.service.ICartService;
import com.ivinx.mall.service.IOrderService;
import com.ivinx.mall.vo.OrderItemVo;
import com.ivinx.mall.vo.OrderVo;
import com.ivinx.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Vinx
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private ShippingMapper shippingMapper;

	@Autowired
	private ICartService cartService;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;

	@Override
	@Transactional
	public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
		Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
		if (shipping == null) {
			return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
		}

		List<Cart> cartList = cartService.listForCart(uid).stream()
				.filter(Cart::getProductSelected)
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(cartList)) {
			return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
		}

		Set<Integer> productIdSet = cartList.stream()
				.map(Cart::getProductId)
				.collect(Collectors.toSet());
		List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
		Map<Integer, Product> map  = productList.stream()
				.collect(Collectors.toMap(Product::getId, product -> product));

		List<OrderItem> orderItemList = new ArrayList<>();
		Long orderNo = generateOrderNo();
		for (Cart cart : cartList) {
			Product product = map.get(cart.getProductId());
			if (product == null) {
				return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
						"Product does not exist. productId = " + cart.getProductId());
			}
			if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())) {
				return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
						"The product is not for sale. " + product.getName());
			}

			if (product.getStock() < cart.getQuantity()) {
				return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR,
						"Incorrect inventory. " + product.getName());
			}

			OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
			orderItemList.add(orderItem);

			product.setStock(product.getStock() - cart.getQuantity());
			int row = productMapper.updateByPrimaryKeySelective(product);
			if (row <= 0) {
				return ResponseVo.error(ResponseEnum.ERROR);
			}
		}

		Order order = buildOrder(uid, orderNo, shippingId, orderItemList);

		int rowForOrder = orderMapper.insertSelective(order);
		if (rowForOrder <= 0) {
			return ResponseVo.error(ResponseEnum.ERROR);
		}

		int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
		if (rowForOrderItem <= 0) {
			return ResponseVo.error(ResponseEnum.ERROR);
		}

		for (Cart cart : cartList) {
			cartService.delete(uid, cart.getProductId());
		}

		OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
		return ResponseVo.success(orderVo);
	}

	@Override
	public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Order> orderList = orderMapper.selectByUid(uid);

		Set<Long> orderNoSet = orderList.stream()
				.map(Order::getOrderNo)
				.collect(Collectors.toSet());
		List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
		Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
				.collect(Collectors.groupingBy(OrderItem::getOrderNo));

		Set<Integer> shippingIdSet = orderList.stream()
				.map(Order::getShippingId)
				.collect(Collectors.toSet());
		List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
		Map<Integer, Shipping> shippingMap = shippingList.stream()
				.collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

		List<OrderVo> orderVoList = new ArrayList<>();
		for (Order order : orderList) {
			OrderVo orderVo = buildOrderVo(order,
					orderItemMap.get(order.getOrderNo()),
					shippingMap.get(order.getShippingId()));
			orderVoList.add(orderVo);
		}
		PageInfo pageInfo = new PageInfo<>(orderList);
		pageInfo.setList(orderVoList);

		return ResponseVo.success(pageInfo);
	}

	@Override
	public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
		Order order = orderMapper.selectByOrderNo(orderNo);
		if (order == null || !order.getUserId().equals(uid)) {
			return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
		}
		Set<Long> orderNoSet = new HashSet<>();
		orderNoSet.add(order.getOrderNo());
		List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

		Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

		OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
		return ResponseVo.success(orderVo);
	}

	@Override
	public ResponseVo cancel(Integer uid, Long orderNo) {
		Order order = orderMapper.selectByOrderNo(orderNo);
		if (order == null || !order.getUserId().equals(uid)) {
			return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
		}
		if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
			return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
		}

		order.setStatus(OrderStatusEnum.CANCELED.getCode());
		order.setCloseTime(new Date());
		int row = orderMapper.updateByPrimaryKeySelective(order);
		if (row <= 0) {
			return ResponseVo.error(ResponseEnum.ERROR);
		}

		return ResponseVo.success();
	}

	@Override
	public void paid(Long orderNo) {
		Order order = orderMapper.selectByOrderNo(orderNo);
		if (order == null) {
			throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc() + "order id:" + orderNo);
		}
		if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
			throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc() + "order id:" + orderNo);
		}

		order.setStatus(OrderStatusEnum.PAID.getCode());
		order.setPaymentTime(new Date());
		int row = orderMapper.updateByPrimaryKeySelective(order);
		if (row <= 0) {
			throw new RuntimeException("Failed to update order to paid status, order id:" + orderNo);
		}
	}

	private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
		OrderVo orderVo = new OrderVo();
		BeanUtils.copyProperties(order, orderVo);

		List<OrderItemVo> OrderItemVoList = orderItemList.stream().map(e -> {
			OrderItemVo orderItemVo = new OrderItemVo();
			BeanUtils.copyProperties(e, orderItemVo);
			return orderItemVo;
		}).collect(Collectors.toList());
		orderVo.setOrderItemVoList(OrderItemVoList);

		if (shipping != null) {
			orderVo.setShippingId(shipping.getId());
			orderVo.setShippingVo(shipping);
		}

		return orderVo;
	}

	private Order buildOrder(Integer uid,
							 Long orderNo,
							 Integer shippingId,
							 List<OrderItem> orderItemList
							 ) {
		BigDecimal payment = orderItemList.stream()
				.map(OrderItem::getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Order order = new Order();
		order.setOrderNo(orderNo);
		order.setUserId(uid);
		order.setShippingId(shippingId);
		order.setPayment(payment);
		order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
		order.setPostage(0);
		order.setStatus(OrderStatusEnum.NO_PAY.getCode());
		return order;
	}

	/**
	 * @return
	 */
	private Long generateOrderNo() {
		return System.currentTimeMillis() + new Random().nextInt(999);
	}

	private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product) {
		OrderItem item = new OrderItem();
		item.setUserId(uid);
		item.setOrderNo(orderNo);
		item.setProductId(product.getId());
		item.setProductName(product.getName());
		item.setProductImage(product.getMainImage());
		item.setCurrentUnitPrice(product.getPrice());
		item.setQuantity(quantity);
		item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
		return item;
	}
}
