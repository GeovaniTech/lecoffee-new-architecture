package managedBean.products.products;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.products.categories.IKeepCategorySBean;
import keep.products.products.IKeepProductSBean;
import to.products.categories.TOCategory;
import to.products.products.TOProduct;
import utils.FileUtil;
import utils.MessageUtil;

@Named(MBProductInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBProductInfo extends AbstractMBean {
	private static final long serialVersionUID = -5614584323038988748L;
	public static final String MANAGED_BEAN_NAME = "MBProductInfo";
	
	private TOProduct product;
	private boolean editing;
	private List<TOCategory> categories;
	private Integer idCategorySelected;
	
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
		this.setIdCategorySelected(null);
		this.updateForm();
	}
	
	public void changeProduct(TOProduct product) {
		this.setEditing(true);
		this.setProduct(product);
		this.setIdCategorySelected(product.getCategory().getId());
		this.updateForm();
	}
	
	public void save() {
		try {
			if(this.getProduct().getImageBytes() == null) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			this.getProduct().setCategory(this.getCategorySBean().findById(this.getIdCategorySelected()));
			this.getProduct().setCreationUser(this.getClientSession().getEmail());
			this.getProduct().setCreationDate(new Date());
			
			this.getProductSBean().save(this.getProduct());
			showMessageItemSaved(this.getProduct().getName());
			
			this.setEditing(true);
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void change() {
		try {
			if(this.getProduct().getImageBytes() == null) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			this.getProduct().setCategory(this.getCategorySBean().findById(this.getIdCategorySelected()));
			this.getProduct().setChangeUser(this.getClientSession().getEmail());
			this.getProduct().setChangeDate(new Date());
			
			this.getProductSBean().change(this.getProduct());
			showMessageItemChanged(this.getProduct().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void remove() {
		try {
			this.getProductSBean().remove(this.getProduct());
			
			showMessageItemRemoved(this.getProduct().getName());
			
			this.init();
		} catch (Exception e) {
			showMessageError(e);
		}
	}

	public void addImage(FileUploadEvent event) throws IOException {		
		this.getProduct().setImageBytes(FileUtil.getBytesFromPrimefacesFile(event.getFile()));
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_image_added_successfully"), FacesMessage.SEVERITY_INFO);
	}
	
	private void updateForm() {
		PrimeFaces.current().ajax().update("tabViewProducts:dialogProductInfo:formProductInfo");
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

	public Integer getIdCategorySelected() {
		return idCategorySelected;
	}

	public void setIdCategorySelected(Integer idCategorySelected) {
		this.idCategorySelected = idCategorySelected;
	}
	
}
