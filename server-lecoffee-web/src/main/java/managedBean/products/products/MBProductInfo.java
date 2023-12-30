package managedBean.products.products;

import java.io.IOException;
import java.util.List;

import org.primefaces.event.FileUploadEvent;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.products.categories.IKeepCategorySBean;
import keep.products.products.IKeepProductSBean;
import to.products.categories.TOCategory;
import to.products.products.TOProduct;
import utils.FileUtil;

@Named(MBProductInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBProductInfo extends AbstractMBean {
	private static final long serialVersionUID = -5614584323038988748L;
	public static final String MANAGED_BEAN_NAME = "MBProductInfo";
	
	private TOProduct product;
	private boolean editing;
	private List<TOCategory> categories;
	private int idCategorySelected;
	
	@EJB
	private IKeepProductSBean productSBean;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@PostConstruct
	@Override
	public void init() {
		this.setEditing(false);
		this.setProduct(new TOProduct());
		this.setCategories(this.getCategorySBean().getResults());
	}
	
	public void save() {
		
	}
	
	public void change() {
		
	}
	
	public void remove() {
		
	}

	public void addImage(FileUploadEvent event) throws IOException {		
		this.getProduct().setImageBytes(FileUtil.getBytesFromPrimefacesFile(event.getFile()));
	}
	
	// Getters and Setters
	public TOProduct getProduct() {
		return product;
	}

	public void setProduct(TOProduct product) {
		this.product = product;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public IKeepProductSBean getProductSBean() {
		return productSBean;
	}

	public void setProductSBean(IKeepProductSBean productSBean) {
		this.productSBean = productSBean;
	}

	public List<TOCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public int getIdCategorySelected() {
		return idCategorySelected;
	}

	public void setIdCategorySelected(int idCategorySelected) {
		this.idCategorySelected = idCategorySelected;
	}
	
}
