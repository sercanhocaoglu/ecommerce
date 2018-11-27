package main.java.com.trendyolcase.shoppingcart;

import java.util.Optional;

public class Category {
	private String name;
	private Optional<Category> parentCategory;

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Optional<Category> getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Optional<Category> parentCategory) {
		this.parentCategory = parentCategory;
	}

}