package keep.products.categories;

import java.util.List;

import jakarta.ejb.Local;
import to.products.categories.TOCategory;
import to.products.categories.TOFilterCategory;

@Local
public interface IKeepCategorySBean {
	public void save(TOCategory category);
	public void change(TOCategory category);
	public void remove(TOCategory category);
	public int countCategories(TOFilterCategory filter);
	public List<TOCategory> getResults(TOFilterCategory filter);
	public List<TOCategory> getResults();
	public TOCategory findById(int id);
}
