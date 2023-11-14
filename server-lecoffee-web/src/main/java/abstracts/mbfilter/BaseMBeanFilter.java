package abstracts.mbfilter;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import abstracts.BaseMBean;

public abstract class BaseMBeanFilter<DataModel, Filter> extends BaseMBean {

	private static final long serialVersionUID = 4515288235703540053L;
	
	private LazyDataModel<DataModel> results;
	private Filter filter;
	
	public void searchResults() {
		this.setResults(new LazyDataModel<DataModel>() {

			private static final long serialVersionUID = -388135453341051527L;

			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<DataModel> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				// TODO Auto-generated method stub
				return null;
			}
		
		});
	}
	
	// Getters and Setters
	public LazyDataModel<DataModel> getResults() {
		return results;
	}
	
	public void setResults(LazyDataModel<DataModel> results) {
		this.results = results;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
}
