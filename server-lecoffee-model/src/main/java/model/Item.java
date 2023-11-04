package model;

import abstracts.AbstractObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Item extends AbstractObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = false)
	private Integer id;
	
	@OneToOne
	private Product product;
	private int amount;
	
	public Item() {
		this.setAmount(0);
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
