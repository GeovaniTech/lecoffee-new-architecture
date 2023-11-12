package keep.client;

import java.util.List;

import jakarta.ejb.Local;
import to.TOClient;
import to.TOFilterLovClient;

@Local
public interface IKeepClientSBean {
	public boolean save(String email, String password);
	public void save(TOClient client);
	public void change(TOClient client);
	public void remove(TOClient client);
	public boolean verifyClient(String email);
	public boolean logar(String email, String password);
	public TOClient findByEmail(String email);
	public TOClient findById(int id);
	public List<TOClient> list();
	public List<TOClient> listClientsLov(TOFilterLovClient filter);
	public void setNewPassword(String email, String password);
	public void finishRegister(String email);
}
