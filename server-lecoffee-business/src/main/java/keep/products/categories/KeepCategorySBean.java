package keep.products.categories;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Category;
import query.SimpleWhere;
import to.TOParameter;
import to.products.categories.TOCategory;
import to.products.categories.TOFilterCategory;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCategorySBean extends AbstractKeep<Category, TOCategory> implements IKeepCategorySBean, IKeepCategoryRemoteSBean {

	public KeepCategorySBean() {
		super(Category.class, TOCategory.class);
	}

	@Override
	public void save(TOCategory category) {
		Category model = this.convertToModel(category);
		
		this.getEntityManager().persist(model);
		
		category.setId(model.getId());
	}

	@Override
	public void change(TOCategory category) {
		Category model = this.convertToModel(category);
		
		this.getEntityManager().merge(model);	
	}

	@Override
	public void remove(TOCategory category) {
		Category model = this.convertToModel(category);
		
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));;
	}

	@Override
	public int countCategories(TOFilterCategory filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.id) FROM ")
			.append(Category.class.getSimpleName()).append(" C ");
		
		List<TOParameter> params = new  ArrayList<TOParameter>();
		
		sql.append(this.getWhereListCategories(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Number.class);
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOCategory> getResults(TOFilterCategory filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ");
		sql.append(Category.class.getSimpleName()).append(" C ");
		
		List<TOParameter> params = new  ArrayList<TOParameter>();
		
		sql.append(this.getWhereListCategories(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Category.class);
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TOCategory> getResults() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ");
		sql.append(Category.class.getSimpleName()).append(" C ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Category.class);
		return this.convertModelResults(query.getResultList());
	}

	@Override
	public TOCategory findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Category.class, id));
	}
	
	private String getWhereListCategories(TOFilterCategory filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE 1 = 1 ");
		sql.append(SimpleWhere.queryFilter("C.name", filter.getName()));
		sql.append(SimpleWhere.queryFilter("C.icon", filter.getIcon()));
		
		return sql.toString();
	}
	
}
