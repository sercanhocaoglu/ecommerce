package test.java.com.trendyolcase.shoppingcart;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import main.java.com.trendyolcase.shoppingcart.Campaign;
import main.java.com.trendyolcase.shoppingcart.Category;
import main.java.com.trendyolcase.shoppingcart.Coupon;
import main.java.com.trendyolcase.shoppingcart.DeliveryCostCalculator;
import main.java.com.trendyolcase.shoppingcart.DiscountType;
import main.java.com.trendyolcase.shoppingcart.Product;
import main.java.com.trendyolcase.shoppingcart.ShoppingCart;

public class TestShoppingCart {

	private ShoppingCart getShoppingCart() {
		Category category1 = new Category("category1");
		Category category2 = new Category("category2");
		category2.setParentCategory(Optional.ofNullable(category1));

		Product product1 = new Product("product1", 20.0, category1);
		Product product2 = new Product("product2", 10.0, category1);
		Product product3 = new Product("product3", 40.0, category1);
		Product product4 = new Product("product4", 30.0, category2);
		Product product5 = new Product("product5", 10.0, category2);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addItem(product1, 4);
		shoppingCart.addItem(product2, 2);
		shoppingCart.addItem(product3, 5);
		shoppingCart.addItem(product4, 1);
		shoppingCart.addItem(product5, 3);

		Campaign campaign1 = new Campaign(category1, 5.0, 2, DiscountType.Rate);
		Campaign campaign2 = new Campaign(category1, 10.0, 1, DiscountType.Amount);
		Coupon coupon1 = new Coupon(15.0, 100.0, DiscountType.Amount);
		Coupon coupon2 = new Coupon(10.0, 90.0, DiscountType.Rate);
		shoppingCart.addCampaign(campaign1);
		shoppingCart.addCampaign(campaign2);
		shoppingCart.addCoupon(coupon1);
		shoppingCart.addCoupon(coupon2);

		DeliveryCostCalculator calculator = new DeliveryCostCalculator(2.0, 2.0);
		shoppingCart.setDeliveryCostCalculator(calculator);
		return shoppingCart;
	}

	@Test
	public void testCampaignDiscountAmount() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertEquals(shoppingCart.getCampaignDiscount(), Double.valueOf(15.0));
	}

	@Test
	public void testGetTotalAmountByCategory() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertEquals(shoppingCart.getTotalAmountByCategory("category1"), Double.valueOf(300.0));
	}

	@Test
	public void testGetTotalAmountBeforeDiscount() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertEquals(shoppingCart.getTotalAmountBeforeDiscount(), Double.valueOf(360.0));
	}

	@Test
	public void testGetTotalDiscount() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertNotEquals(shoppingCart.getTotalDiscount(), Double.valueOf(22.0));
	}

	@Test
	public void testGetTotalAmount() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertEquals(shoppingCart.getTotalAmount(), Double.valueOf(295.5));
	}

	@Test
	public void testGetDeliveryCost() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertEquals(shoppingCart.getDeliveryCost(), Double.valueOf(16.990000000000002));
	}

	@Test
	public void testGetCouponDiscount() {
		ShoppingCart shoppingCart = getShoppingCart();
		Assert.assertEquals(
				shoppingCart.getCouponDiscount(
						shoppingCart.getTotalAmountBeforeDiscount() - shoppingCart.getCampaignDiscount()),
				Double.valueOf(49.5));
	}

	@Test
	public void print() {
		ShoppingCart shoppingCart = getShoppingCart();
		shoppingCart.print();
	}

}