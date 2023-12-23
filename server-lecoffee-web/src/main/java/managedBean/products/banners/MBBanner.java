package managedBean.products.banners;

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
import keep.products.banners.IKeepBannerSBean;
import to.products.banners.TOBanner;
import to.products.banners.TOFilterBanner;

@Named(MBBanner.MANAGED_BEAN_NAME)
@ViewScoped
public class MBBanner extends AbstractMBean {

	private static final long serialVersionUID = 1727598425179761874L;
	public static final String MANAGED_BEAN_NAME = "MBBanner";
	
	private TOFilterBanner filter;
	private LazyDataModel<TOBanner> results;
	private TOBanner banner;
	
	@EJB
	private IKeepBannerSBean bannerSBean;
	
	@PostConstruct
	public void init() {
		this.setFilter(new TOFilterBanner());
		this.searchBanners();
	}
	
	public void initNewBanner() {
		this.getMBBannerInfo().initBanner();
	}
	
	public void clearFilters() {
		this.setFilter(new TOFilterBanner());
	}
	
	public void onRowSelect(SelectEvent<TOBanner> event) {
		this.getMBBannerInfo().editBanner(event.getObject());
	}
	
	public void searchBanners() {
		this.setResults(new LazyDataModel<TOBanner>() {
			
			private static final long serialVersionUID = -4531392901323609248L;

			private List<TOBanner> banners;
			
			@Override
			public List<TOBanner> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				
				getFilter().setFirstResult(first);
				getFilter().setMaxResults(pageSize);
				
				setRowCount(getBannerSBean().countBanners(getFilter()));
				
				banners = getBannerSBean().getResults(getFilter());
				
				return banners;
			}
			
			@Override
			public TOBanner getRowData(String rowKey) {
				for(TOBanner banner : banners) {
					if(String.valueOf(banner.getId()).equals(rowKey)) {
						return banner;
					}
				}
				
				return null;
			}
			
			@Override
			public String getRowKey(TOBanner object) {
				return String.valueOf(object.getId());
			}
			
			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				return getBannerSBean().countBanners(getFilter());
			}
		});
	}
	
	public MBBannerInfo getMBBannerInfo() {
		return this.getMBean(MBBannerInfo.MANAGED_BEAN_NAME);
	}

	public TOFilterBanner getFilter() {
		return filter;
	}

	public void setFilter(TOFilterBanner filter) {
		this.filter = filter;
	}

	public LazyDataModel<TOBanner> getResults() {
		return results;
	}

	public void setResults(LazyDataModel<TOBanner> results) {
		this.results = results;
	}

	public IKeepBannerSBean getBannerSBean() {
		return bannerSBean;
	}

	public void setBannerSBean(IKeepBannerSBean bannerSBean) {
		this.bannerSBean = bannerSBean;
	}

	public TOBanner getBanner() {
		return banner;
	}

	public void setBanner(TOBanner banner) {
		this.banner = banner;
	}
	
}
