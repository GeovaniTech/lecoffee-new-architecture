package keep.products.products;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import model.Product;
import to.products.products.TOFilterProduct;
import to.products.products.TOProduct;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TOProduct> getResults(TOFilterProduct filter) {
		return new ArrayList<TOProduct>();
	}

	@Override
	public TOProduct findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Product.class, id));
	}
	
}
