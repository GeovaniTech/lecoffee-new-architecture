package managedBean.products.products;

import java.util.List;

import org.primefaces.event.SelectEvent;

import abstracts.mbfilter.AbstractFilterMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.products.categories.IKeepCategorySBean;
import keep.products.products.IKeepProductSBean;
import to.products.categories.TOCategory;
import to.products.products.TOFilterProduct;
import to.products.products.TOProduct;

@Named(MBProduct.MANAGED_BEAN_NAME)
@ViewScoped
public class MBProduct extends AbstractFilterMBean<TOProduct, TOFilterProduct> {

	private static final long serialVersionUID = 3004433029368077538L;
	public static final String MANAGED_BEAN_NAME = "MBProduct";
	
	private TOProduct product;
	
	private List<TOCategory> categories;
	
	@EJB
	private IKeepProductSBean productSBean;

	@EJB
	private IKeepCategorySBean categorySBean;
	
	@PostConstruct
	@Override
	public void init() {
		this.setFilter(new TOFilterProduct());
		this.searchResults();
		this.setCategories(this.getCategorySBean().getResults());
	}
	
	public void initNewProduct() {
		this.getMBProductInfo().init();
	}
	
	@Override
	public List<TOProduct> getData(TOFilterProduct filter) {
		return this.getProductSBean().getResults(filter);
	}

	@Override
	public Integer getCount(TOFilterProduct filter) {
		return this.getProductSBean().getCount(filter);
	}
	
	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterProduct());	
	}
	
	@Override
	public void onRowSelect(SelectEvent<TOProduct> event) {
		this.getMBProductInfo().changeProduct(event.getObject());
	}
	
	public MBProductInfo getMBProductInfo() {
		return this.getMBean(MBProductInfo.MANAGED_BEAN_NAME);
	}

	// Getters and Setters
	public IKeepProductSBean getProductSBean() {
		return productSBean;
	}
	public void setProductSBean(IKeepProductSBean productSBean) {
		this.productSBean = productSBean;
	}
	public TOProduct getProduct() {
		return product;
	}
	public void setProduct(TOProduct product) {
		this.product = product;
	}
	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}
	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}
	public List<TOCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}

}
