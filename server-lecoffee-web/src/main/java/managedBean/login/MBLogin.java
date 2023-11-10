package managedBean.login;

import abstracts.BaseMBean;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.login.IKeepClientSBean;
import utils.EmailUtil;
import utils.EncryptionUtil;
import utils.JWTUtil;
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
			
			return;
		}
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
	}
	
	public void showFinishedRegisterMessage() {
		if(StringUtil.isNotNull(this.getMessageParam())) {
			if(this.getMessageParam().equals("finished")) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("registration_completed_successfully"), FacesMessage.SEVERITY_INFO);
			} else if(this.getMessageParam().equals("newpassword")){
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_change_successfully"), FacesMessage.SEVERITY_INFO);
			} else if(this.getMessageParam().equals("userblocked")) {
				MessageUtil.sendMessage( MessageUtil.getMessageFromProperties("user_blocked"), FacesMessage.SEVERITY_ERROR);
			}
		}
	}
	
	public void sendEmailForgotPassoword() {
		String title = "Lecoffee - Solicitação de Troca de Senha";
		
		StringBuilder description = new StringBuilder();
		
		description.append("<h2>Troca de Senha de acesso a LeCoffee<h2/>");
		description.append("<p>Olá,</p>");
		description.append("<p>Para trocar sua senha de acesso, entre no link abaixo e insira sua nova senha.</p>");
		description.append("<p><a href=http://localhost:8080/lecoffee/newpassword/");
		description.append(JWTUtil.generateToken("newPassword", EncryptionUtil.encryptNormalText(this.getEmail()))).append(">Troca de Senha</a><p/>");
		description.append("<p>Caso você não tenha solicitado a troca de senha na LeCoffee ");
		description.append("ou acredite que este email tenha sido enviado por engano, por favor, desconsidere esta mensagem.</p>");
		description.append("Atenciosamente, <br>");
		description.append("A equipe LeCoffee <br>");
		
		EmailUtil.sendMail(this.getEmail(), title, description.toString(), MessageUtil.getMessageFromProperties("email_new_password"));
	}
	
	public void validateSendNewPassword() {
		if(!EmailUtil.validateEmail(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		if(!this.getClientSBean().verifyClient(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_not_found"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		this.sendEmailForgotPassoword();
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
