package keep.products.banners;

import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Banner;
import query.SimpleWhere;
import to.products.banners.TOBanner;
import to.products.banners.TOFilterBanner;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepBannerSBean extends AbstractKeep<Banner, TOBanner> implements IKeepBannerSBean, IKeepBannerRemoteSBean {
	public KeepBannerSBean() {
		super(Banner.class, TOBanner.class);
	}

	@Override
	public void save(TOBanner banner) {
		Banner model = this.convertToModel(banner);
		
		this.getEntityManager().persist(model);
		
		banner.setId(model.getId());
	}

	@Override
	public void change(TOBanner banner) {
		Banner model = this.convertToModel(banner);
		
		this.getEntityManager().merge(model);
		
		banner.setId(model.getId());
	}

	@Override
	public void remove(TOBanner banner) {
		Banner model = this.convertToModel(banner);
		
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public TOBanner findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Banner.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOBanner> getResults(TOFilterBanner filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(this.getSelectListBanners());
		sql.append(this.getWhereListBanners(filter));
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Banner.class);
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		
		return this.convertModelResults(query.getResultList());
	}

	@Override
	public int countBanners(TOFilterBanner filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT COUNT(B.id) FROM ");
		sql.append(Banner.class.getSimpleName()).append(" B ");
		sql.append(this.getWhereListBanners(filter));
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Banner.class);
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}
	
	private String getSelectListBanners() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT B FROM ");
		sql.append(Banner.class.getSimpleName()).append(" B ");
		
		return sql.toString();
	}
	
	private String getWhereListBanners(TOFilterBanner filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE 1 = 1 ");
		sql.append(SimpleWhere.queryFilter("B.name", filter.getName()));
		
		return sql.toString();
	}
}
