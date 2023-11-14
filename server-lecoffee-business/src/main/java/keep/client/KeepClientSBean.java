package keep.client;

import java.util.Date;
import java.util.List;

import abstracts.BaseKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;
import to.client.TOClient;
import to.client.TOFilterClient;
import to.client.TOFilterLovClient;
import utils.EncryptionUtil;
import utils.JWTUtil;
import utils.MessageUtil;
import utils.RedirectURL;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean extends BaseKeep<Client, TOClient> implements IKeepClientSBean, IKeepClientRemoteSBean {

	public KeepClientSBean() {
		super(Client.class, TOClient.class);
	}
	
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
		Client model = this.convertToModel(client);
		
		Client pattern = this.getEntityManager().find(Client.class, model.getId());
		
		if(StringUtil.isNull(model.getEmail())) {
			model.setEmail(pattern.getEmail());
		}
		
		model.setPassword(pattern.getPassword());
		
		this.getEntityManager().merge(model);
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
		sql.append(" WHERE C.email = :email ");
		sql.append(" AND C.creationDate IS NOT NUll ");
		sql.append(" AND C.blocked = false ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", email);
		
		if(query.getResultList().size() == 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean logar(String email, String password) {		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email = :pEmail AND C.password = :pPassword ")
			.append(" AND C.creationDate IS NOT NUll ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Client.class);
		query.setParameter("pEmail", email);
		query.setParameter("pPassword", EncryptionUtil.encryptTextSHA(password));
		
		if(query.getResultList().size() == 1) {
			Client client = (Client) query.getSingleResult();
			
			if(client.isBlocked()) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_blocked"), FacesMessage.SEVERITY_ERROR);
				
				return false;
			}
			
			String encrypedEmail = EncryptionUtil.encryptNormalText(client.getEmail());
			
			if(client.isChangePassword()) {
				String token = JWTUtil.generateToken("newPassword", encrypedEmail);
				
				RedirectURL.redirectTo("/lecoffee/newpassword/ " + token);
				
				return false;
			}
			
			TOClient to = this.convertToDTO(client);
			to.setLastLogin(new Date());
			
			this.change(to);
			
			this.getSession().setAttribute("client", to);
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			Cookie userCookie = new Cookie("userSession", EncryptionUtil.encryptNormalText(client.getEmail()));
			userCookie.setMaxAge(60*60*24*30);
			userCookie.setPath("/lecoffee");
			
			response.addCookie(userCookie);
			
			if(client.getSecurityLevel().equals("client")) {
				RedirectURL.redirectTo("/lecoffee/home");
			} else {
				RedirectURL.redirectTo("/lecoffee/admin/clients");
			}	
			
			return true;
		}
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_or_password_incorrect"), FacesMessage.SEVERITY_ERROR);
		
		return false;
	}

	@Override
	public TOClient findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email  = :pEmail ");
		
		Client client = (Client) this.getEntityManager()
				.createQuery(sql.toString())
				.setParameter("pEmail", email)
				.getSingleResult();
		
		return this.convertToDTO(client);
	}

	@Override
	public TOClient findById(int id) {
		Client client = this.getEntityManager().find(Client.class, id);
		
		return this.convertToDTO(client);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOClient> list(TOFilterClient filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ");
		
		sql.append(this.getWhereListClients());
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Client.class);
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		
		return this.convertModelResults(query.getResultList());
	}
	
	@Override
	public int countClient(TOFilterClient filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.id) FROM ")
			.append(Client.class.getSimpleName()).append(" C ");
		
		sql.append(this.getWhereListClients());
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Number.class);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}
	
	public String getWhereListClients() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE C.creationDate IS NOT NULL ");
		
		return sql.toString();
	}

	@Override
	public List<TOClient> listClientsLov(TOFilterLovClient filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNewPassword(String email, String password) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" SET C.password = :pPassword ")
			.append(" WHERE  C.email =  :pEmail ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("pPassword", EncryptionUtil.encryptTextSHA(password));
		query.setParameter("pEmail", email);
		
		query.executeUpdate();
		
		RedirectURL.redirectTo("/lecoffee/login/newpassword");
	}

	@Override
	public void finishRegister(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email  = :pEmail ")
			.append(" AND C.creationDate IS NULL ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("pEmail", email);
		
		if(query.getResultList().size() == 1) {
			Client client = (Client) query.getSingleResult();
			
			client.setCreationDate(new Date());
			client.setCreationUser("Lecoffee System");
			client.setSecurityLevel("client");
			
			this.getEntityManager().merge(client); 
			
			RedirectURL.redirectTo("/lecoffee/login/finished");
			
			return;
		}
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_already_completed_registration"), FacesMessage.SEVERITY_ERROR);
	}

}
