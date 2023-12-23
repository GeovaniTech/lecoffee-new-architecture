package to.products.banners;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;

public class TOFilterBanner extends AbstractTOFilter {

	private static final long serialVersionUID = 6500799727831301011L;
	
	private TOInputFilter name;
	
	public TOFilterBanner() {
		this.setName(new TOInputFilter());
	}

	public TOInputFilter getName() {
		return name;
	}
	public void setName(TOInputFilter name) {
		this.name = name;
	}
	
}
