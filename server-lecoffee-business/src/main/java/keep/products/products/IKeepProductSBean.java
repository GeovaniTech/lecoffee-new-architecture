package keep.products.products;

import java.util.List;

import jakarta.ejb.Local;
import to.products.products.TOFilterProduct;
import to.products.products.TOProduct;

@Local
public interface IKeepProductSBean {
	public void save(TOProduct product);
	public void change(TOProduct product);
	public void remove(TOProduct product);
	public int getCount(TOFilterProduct filter);
	public List<TOProduct> getResults(TOFilterProduct filter);
	public TOProduct findById(int id);
}
