package to.products.categories;

import abstracts.AbstractTOObject;

public class TOCategory extends AbstractTOObject {

	private static final long serialVersionUID = 6714985906883204379L;

	private int id;
	private String name;
	private String icon;
	private Integer priority;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
