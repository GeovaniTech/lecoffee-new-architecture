package keep.address;

import java.util.List;

import jakarta.ejb.Local;
import to.address.TOAddress;

@Local
public interface IKeepAddressSBean {
	public void save(TOAddress address);
	public void remove(TOAddress address);
	public void change(TOAddress address);
	public TOAddress findById(int id);
	public List<TOAddress> getAdressesByClientId(int id);
}
