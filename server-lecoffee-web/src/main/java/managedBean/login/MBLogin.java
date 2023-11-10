package managedBean.login;

import abstracts.BaseMBean;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.login.IKeepClientSBean;
import utils.EmailUtil;
import utils.MessageUtil;
import utils.StringUtil;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends BaseMBean {
	
	private static final long serialVersionUID = -6951441926941734297L;
	
	private String email;
	private String password;
	private String messageParam;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public void logar() {
		if(EmailUtil.validateEmail(this.getEmail())) {
			this.getClientSBean().logar(this.getEmail(), this.getPassword());
		}
	}
	
	public void showFinishedRegisterMessage() {
		String message;
		
		if(StringUtil.isNotNull(this.getMessageParam())) {
			if(this.getMessageParam().equals("finished")) {
				message = MessageUtil.getMessageFromProperties("registration_completed_successfully");
			} else {
				message = MessageUtil.getMessageFromProperties("password_change_successfully");
			}
			
			MessageUtil.sendMessage(message, FacesMessage.SEVERITY_INFO);
		}
		
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

	public String getMessageParam() {
		return messageParam;
	}

	public void setMessageParam(String messageParam) {
		this.messageParam = messageParam;
	}

}
