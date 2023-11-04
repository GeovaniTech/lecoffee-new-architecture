package managedBean.login;

import abstracts.BaseMBean;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.login.IKeepClientSBean;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends BaseMBean {
	
	private static final long serialVersionUID = -6951441926941734297L;
	
	private String email;
	private String password;
	private String registerFinished;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public void logar() {
		
	}
	
	public void sendRegisterFinishedMessage() {
		
	}
	
	// Getters and Setters
	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegisterFinished() {
		return registerFinished;
	}

	public void setRegisterFinished(String registerFinished) {
		this.registerFinished = registerFinished;
	}

}
