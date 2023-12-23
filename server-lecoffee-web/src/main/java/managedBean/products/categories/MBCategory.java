package managedBean.products.categories;

import java.util.List;
import java.util.Map;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.products.categories.IKeepCategorySBean;
import to.products.categories.TOCategory;
import to.products.categories.TOFilterCategory;

@Named("MBCategory")
@ViewScoped
public class MBCategory extends AbstractMBean {

	private static final long serialVersionUID = 2550109976791739360L;
	
	private LazyDataModel<TOCategory> results;
	private TOFilterCategory filter;
	private TOCategory category;
	private Integer totalLines;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@PostConstruct
	public void init() {
		this.setFilter(new TOFilterCategory());
		this.searchCategories();
	}
	
	public String getTextButtonInit() {
		return "Teste Geovani " + this.getTotalLines();
	}
	
	public void searchCategories() {
		this.setResults(new LazyDataModel<TOCategory>() {

			private static final long serialVersionUID = -3979332855121980190L;

			private List<TOCategory> categories;
			
			@Override
			public List<TOCategory> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				
				getFilter().setFirstResult(first);
				getFilter().setMaxResults(pageSize);
				
				MBCategory.this.setTotalLines(getCategorySBean().countCategories(getFilter()));
				
				setRowCount(MBCategory.this.getTotalLines());
				
				categories = getCategorySBean().getResults(getFilter());
				
				return categories;
			}
			
			@Override
			public TOCategory getRowData(String rowKey) {
				for(TOCategory category : categories) {
					if(String.valueOf(category.getId()).equals(rowKey)) {
						return category;
					}
				}
				
				return null;
			}
			
			@Override
			public String getRowKey(TOCategory object) {
				return String.valueOf(object.getId());
			}
			
			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				return getCategorySBean().countCategories(getFilter());
			}
		});
	}
	
    public void onRowSelect(SelectEvent<TOCategory> event) {
    	this.getMBCategoryInfo().editCategory(event.getObject());
    }
    
    public void clearFilters() {
    	this.setFilter(new TOFilterCategory());
    }
	
	public void initNewCategory() {
		this.getMBCategoryInfo().initCategory();
	}
	
	public MBCategoryInfo getMBCategoryInfo() {
		return this.getMBean("MBCategoryInfo");
	}

	// Getters and Setters
	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}
	
	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public LazyDataModel<TOCategory> getResults() {
		return results;
	}

	public void setResults(LazyDataModel<TOCategory> results) {
		this.results = results;
	}

	public TOFilterCategory getFilter() {
		return filter;
	}

	public void setFilter(TOFilterCategory filter) {
		this.filter = filter;
	}

	public TOCategory getCategory() {
		return category;
	}

	public void setCategory(TOCategory category) {
		this.category = category;
	}

	public Integer getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(Integer totalLines) {
		this.totalLines = totalLines;
	}

}
