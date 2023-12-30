package to.products.products;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;
import to.products.categories.TOCategory;

public class TOFilterProduct extends AbstractTOFilter {
	private static final long serialVersionUID = 2973517230206716025L;
	
	private TOInputFilter name;
	private TOInputFilter description;
	private Double price;
	private TOCategory category;
	private Integer rating;
	
	public TOFilterProduct() {
		this.setName(new TOInputFilter());
		this.setDescription(new TOInputFilter());
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public TOCategory getCategory() {
		return category;
	}
	public void setCategory(TOCategory category) {
		this.category = category;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public TOInputFilter getName() {
		return name;
	}
	public void setName(TOInputFilter name) {
		this.name = name;
	}
	public TOInputFilter getDescription() {
		return description;
	}
	public void setDescription(TOInputFilter description) {
		this.description = description;
	}

}
