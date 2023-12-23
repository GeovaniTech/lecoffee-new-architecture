package keep.products.banners;

import java.util.List;

import jakarta.ejb.Local;
import to.products.banners.TOBanner;
import to.products.banners.TOFilterBanner;

@Local
public interface IKeepBannerSBean {
	public void save(TOBanner banner);
	public void change(TOBanner banner);
	public void remove(TOBanner banner);
	public TOBanner findById(int id);
	public List<TOBanner> getResults(TOFilterBanner filter);
	public int countBanners(TOFilterBanner filter);
}
