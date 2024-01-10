package keep.products.products;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Product;
import query.SimpleWhere;
import to.TOParameter;
import to.products.products.TOFilterProduct;
import to.products.products.TOProduct;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepProductSBean extends AbstractKeep<Product, TOProduct> implements IKeepProductSBean, IKeepProductRemoteSBean{

	public KeepProductSBean() {
		super(Product.class, TOProduct.class);
	}

	@Override
	public void save(TOProduct product) {
		Product model = this.convertToModel(product);
		
		this.getEntityManager().persist(model);
	}

	@Override
	public void change(TOProduct product) {
		Product model = this.convertToModel(product);
		
		this.getEntityManager().merge(model);
	}

	@Override
	public void remove(TOProduct product) {
		Product model = this.convertToModel(product);
		
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public int getCount(TOFilterProduct filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT COUNT(P.id) ")
			.append(this.getFromProducts())
			.append(this.getWhereProducts(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOProduct> getResults(TOFilterProduct filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT P ")
			.append(this.getFromProducts())
			.append(this.getWhereProducts(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}

	@Override
	public TOProduct findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Product.class, id));
	}
	
	private String getWhereProducts(TOFilterProduct filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE 1 = 1 ");
		
		sql.append(SimpleWhere.queryFilter("P.name", filter.getName()));
		sql.append(SimpleWhere.queryFilter("P.description", filter.getDescription()));
		sql.append(SimpleWhere.queryFilterNumberRange("P.price", filter.getPrice()));
		
		if(filter.getIdCategory() != null) {
			sql.append(" AND P.category.id = :idCategory ");
			params.add(new TOParameter("idCategory", filter.getIdCategory()));
		}
		
		return sql.toString();
	}
	
	private String getFromProducts() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" FROM ")
			.append(Product.class.getSimpleName())
			.append(" P ");
		
		return sql.toString();
	}
	
}
