package main.java.com.trendyolcase.shoppingcart;

public class Coupon extends Discount {
	private Double minAmountLimit;

	public Coupon(Double discountAmount, Double minAmountLimit, DiscountType discountType) {
		super(discountAmount, discountType);
		this.minAmountLimit = minAmountLimit;
	}

	public Double getMinAmountLimit() {
		return minAmountLimit;
	}

	public void setMinAmountLimit(Double minAmountLimit) {
		this.minAmountLimit = minAmountLimit;
	}

}