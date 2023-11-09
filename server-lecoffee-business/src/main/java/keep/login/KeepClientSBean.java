package keep.login;

import java.util.List;

import abstracts.BaseKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import model.Client;
import to.TOClient;
import to.TOFilterLovClient;
import utils.EncryptionUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean extends BaseKeep implements IKeepClientSBean, IKeepClientRemoteSBean {

	@Override
	public boolean save(String email, String password) {
		Client client = new Client();
		client.setEmail(email);
		client.setPassword(EncryptionUtil.encryptTextSHA(password));
		
		this.getEntityManager().persist(client);
		
		return true;
	}

	@Override
	public void save(TOClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void change(TOClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(TOClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean verifyClient(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getSimpleName()).append(" C ");
		sql.append(" WHERE C.email = :email");
		
		try {
			Client client = this.getEntityManager().createQuery(sql.toString(), Client.class)
					.setParameter("email", email)
					.getSingleResult();
			
			return client != null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean logar(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TOClient findByEmail(String email) {
		// TODO Auto-generated method stubTOFilterLovClient
		return null;
	}

	@Override
	public TOClient findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TOClient> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TOClient> listClientsLov(TOFilterLovClient filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNewPassword(String email, String password) {
		// TODO Auto-generated method stub
		
	}

}
