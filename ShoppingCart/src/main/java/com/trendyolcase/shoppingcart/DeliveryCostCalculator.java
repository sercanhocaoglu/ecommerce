package main.java.com.trendyolcase.shoppingcart;

import java.util.List;
import java.util.Map;

public class DeliveryCostCalculator {
	private Double costPerDelivery;
	private Double costPerProduct;
	private static Double fixedCost = 2.99;

	public DeliveryCostCalculator(Double costPerDelivery, Double costPerProduct) {
		this.costPerDelivery = costPerDelivery;
		this.costPerProduct = costPerProduct;
	}

	public Double calculateFor(Map<String, List<CartItem>> cartItemsGroupByCategory) {
		Integer numberOfDeliveries = cartItemsGroupByCategory.size();
		Integer numberOfProducts = 0;
		for (List<CartItem> cartItemList : cartItemsGroupByCategory.values()) {
			numberOfProducts += cartItemList.size();
		}
		return costPerDelivery * numberOfDeliveries + costPerProduct * numberOfProducts + fixedCost;

	}

	public Double getCostPerDelivery() {
		return costPerDelivery;
	}

	public void setCostPerDelivery(Double costPerDelivery) {
		this.costPerDelivery = costPerDelivery;
	}

	public Double getCostPerProduct() {
		return costPerProduct;
	}

	public void setCostPerProduct(Double costPerProduct) {
		this.costPerProduct = costPerProduct;
	}

	public static Double getFixedCost() {
		return fixedCost;
	}

	public static void setFixedCost(Double fixedCost) {
		DeliveryCostCalculator.fixedCost = fixedCost;
	}

}