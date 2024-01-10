package to.products.products;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;
import to.TOInputNumberFilter;

public class TOFilterProduct extends AbstractTOFilter {
	private static final long serialVersionUID = 2973517230206716025L;
	
	private TOInputFilter name;
	private TOInputFilter description;
	private TOInputNumberFilter price;
	private Integer idCategory;
	private Integer rating;
	
	public TOFilterProduct() {
		this.setName(new TOInputFilter());
		this.setDescription(new TOInputFilter());
		this.setPrice(new TOInputNumberFilter());
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
	public Integer getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(Integer idCategory) {
		this.idCategory = idCategory;
	}
	public TOInputNumberFilter getPrice() {
		return price;
	}
	public void setPrice(TOInputNumberFilter price) {
		this.price = price;
	}

}
