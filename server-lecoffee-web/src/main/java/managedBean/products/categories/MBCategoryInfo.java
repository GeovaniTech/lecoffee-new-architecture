package managedBean.products.categories;

import java.util.Date;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.products.categories.IKeepCategorySBean;
import to.products.categories.TOCategory;

@Named("MBCategoryInfo")
@ViewScoped
public class MBCategoryInfo extends AbstractMBean {

	private static final long serialVersionUID = 8085396815053098155L;
	
	private TOCategory category;
	private boolean editing;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@PostConstruct
	@Override
	public void init() {
		// TODO Auto-generated method stub	
	}
	
	public void initCategory() {
		this.setCategory(new TOCategory());
		this.setEditing(false);
		
		PrimeFaces.current().ajax().update("tabViewProducts:dialogCategoryInfo:formCategoryInfo");
	}
	
	public void editCategory(TOCategory category) {
		this.setCategory(category);
		this.setEditing(true);
		
		PrimeFaces.current().ajax().update("tabViewProducts:dialogCategoryInfo:formCategoryInfo");
	}
	
	public void save() {
		try {
			this.getCategory().setCreationUser(this.getClientSession().getEmail());
			this.getCategory().setCreationDate(new Date());
			
			this.getCategorySBean().save(this.getCategory());
			this.setEditing(true);
			
			showMessageItemSaved(this.getCategory().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void change() {
		try {
			this.getCategory().setChangeUser(this.getClientSession().getChangeUser());
			this.getCategory().setChangeDate(new Date());
			
			this.getCategorySBean().change(this.getCategory());
			
			showMessageItemChanged(this.getCategory().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void remove() {
		try {			
			this.getCategorySBean().remove(this.getCategory());

			showMessageItemRemoved(this.getCategory().getName());
			
			this.initCategory();
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public TOCategory getCategory() {
		return category;
	}
	public void setCategory(TOCategory category) {
		this.category = category;
	}
	public boolean isEditing() {
		return editing;
	}
	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}
	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}
	
}
