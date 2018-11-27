package main.java.com.trendyolcase.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
	private List<Campaign> campaignList;
	private List<Coupon> couponList;
	private Map<String, List<CartItem>> cartItemsGroupByCategory;
	private DeliveryCostCalculator deliveryCostCalculator;

	public ShoppingCart() {
		this.campaignList = new ArrayList<>();
		this.couponList = new ArrayList<>();
		this.cartItemsGroupByCategory = new HashMap<String, List<CartItem>>();
	}

	public ShoppingCart(List<Campaign> campaignList, List<Coupon> couponList,
			Map<String, List<CartItem>> cartItemsGroupByCategory) {
		this.campaignList = campaignList;
		this.couponList = couponList;
		this.cartItemsGroupByCategory = cartItemsGroupByCategory;
	}

	public Double getCouponDiscount(Double totalAmount) {
		Double totalCouponDiscount = 0.0;
		for (Coupon coupon : couponList) {
			if (coupon.getMinAmountLimit() < totalAmount - totalCouponDiscount) {
				if (DiscountType.Amount.equals(coupon.getDiscountType())) {
					totalCouponDiscount += coupon.getDiscountAmount();
				} else if (DiscountType.Rate.equals(coupon.getDiscountType())) {
					totalCouponDiscount += (totalAmount * coupon.getDiscountAmount()) / 100.00;
				}
			}
		}
		return totalCouponDiscount;
	}

	public Double getCampaignDiscount() {
		Double maxDiscountAmount = 0.0;
		for (Campaign campaign : campaignList) {
			String categoryName = campaign.getCategory().getName();
			if (cartItemsGroupByCategory.get(categoryName).size() > campaign.getNumOfItemLimit()) {
				Double discountAmount = 0.0;
				if (DiscountType.Amount.equals(campaign.getDiscountType())) {
					discountAmount = campaign.getDiscountAmount();
				} else if (DiscountType.Rate.equals(campaign.getDiscountType())) {
					discountAmount = (getTotalAmountByCategory(categoryName) * campaign.getDiscountAmount()) / 100.00;
				}
				if (discountAmount > maxDiscountAmount) {
					maxDiscountAmount = discountAmount;
				}
			}
		}
		return maxDiscountAmount;
	}

	public Double getTotalAmountByCategory(String categoryName) {
		Double totalPrice = 0.0;
		if (cartItemsGroupByCategory.containsKey(categoryName)) {
			for (CartItem cartItem : cartItemsGroupByCategory.get(categoryName)) {
				totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
			}
		}
		return totalPrice;
	}

	public Double getTotalAmountBeforeDiscount() {
		Double totalAmount = 0.0;
		if (!cartItemsGroupByCategory.isEmpty()) {
			for (List<CartItem> cartItemList : cartItemsGroupByCategory.values()) {
				for (CartItem cartItem : cartItemList) {
					totalAmount += cartItem.getProduct().getPrice() * cartItem.getQuantity();
				}
			}
		}
		return totalAmount;
	}

	public Double getTotalDiscount() {
		Double campaignDiscount = getCampaignDiscount();
		Double totalAmountAfterCampaignDiscount = getTotalAmountBeforeDiscount() - campaignDiscount;
		Double totalDiscount = campaignDiscount + getCouponDiscount(totalAmountAfterCampaignDiscount);
		return totalDiscount;

	}

	public Double getTotalAmount() {
		Double totalAmountAfterCampaignDiscount = getTotalAmountBeforeDiscount() - getCampaignDiscount();
		Double totalAmount = totalAmountAfterCampaignDiscount - getCouponDiscount(totalAmountAfterCampaignDiscount);
		return totalAmount;
	}

	public void addItem(Product product, Integer quantity) {
		CartItem cartItem = new CartItem(product, quantity);
		String categoryName = product.getCategory().getName();
		if (!cartItemsGroupByCategory.containsKey(categoryName)) {
			List<CartItem> cartItemList = new ArrayList<CartItem>();
			cartItemList.add(cartItem);
			cartItemsGroupByCategory.put(categoryName, cartItemList);
		} else {
			cartItemsGroupByCategory.get(categoryName).add(cartItem);
		}
	}

	public Double getDeliveryCost() {
		return deliveryCostCalculator.calculateFor(cartItemsGroupByCategory);
	}

	public void setDeliveryCostCalculator(DeliveryCostCalculator deliveryCostCalculator) {
		this.deliveryCostCalculator = deliveryCostCalculator;
	}

	public void addCampaign(Campaign campaign) {
		this.campaignList.add(campaign);
	}

	public void addCoupon(Coupon coupon) {
		this.couponList.add(coupon);
	}

	public void print() {

		System.out.println("Category Name - Product Name - Quantity - Unit Price - TotalPrice - TotalDiscount");
		Double totalDiscount = getTotalDiscount();
		for (List<CartItem> cartItemList : cartItemsGroupByCategory.values()) {
			String categoryName = cartItemList.get(0).getProduct().getCategory().getName();
			for (CartItem cartItem : cartItemList) {
				System.out.print(categoryName + "   -   ");
				System.out.print(cartItem.getProduct().getProductName() + "   -   ");
				System.out.print(cartItem.getQuantity() + "   -   ");
				System.out.print(cartItem.getProduct().getPrice() + "   -   ");
				System.out.print(cartItem.getProduct().getPrice() * cartItem.getQuantity() + "   -   ");
				System.out.print(totalDiscount);
				System.out.println();
			}
		}
		System.out.println();
		System.out.println("Total Amount : " + getTotalAmount() + " - " + "Delivery Cost : " + getDeliveryCost());
		System.out.println();
	}

}