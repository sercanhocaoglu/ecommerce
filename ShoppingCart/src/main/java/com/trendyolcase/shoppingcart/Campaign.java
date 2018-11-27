package main.java.com.trendyolcase.shoppingcart;

public class Campaign extends Discount {

	private Category category;
	private Integer numOfItemLimit;

	public Campaign(Category category, Double discountAmount, Integer numOfItemLimit, DiscountType discountType) {
		super(discountAmount, discountType);
		this.category = category;
		this.numOfItemLimit = numOfItemLimit;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getNumOfItemLimit() {
		return numOfItemLimit;
	}

	public void setNumOfItemLimit(Integer numOfItemLimit) {
		this.numOfItemLimit = numOfItemLimit;
	}

}
