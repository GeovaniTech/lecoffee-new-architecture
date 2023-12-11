package to.products.categories;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;

public class TOFilterCategory extends AbstractTOFilter {

	private static final long serialVersionUID = 1878905644868784442L;

	private TOInputFilter name;
	private TOInputFilter icon;
	private Integer priority;
	
	public TOFilterCategory() {
		this.setName(new TOInputFilter());
		this.setIcon(new TOInputFilter());
	}
	
	public TOInputFilter getName() {
		return name;
	}
	public void setName(TOInputFilter name) {
		this.name = name;
	}
	public TOInputFilter getIcon() {
		return icon;
	}
	public void setIcon(TOInputFilter icon) {
		this.icon = icon;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
