package managedBean.login;

import abstracts.BaseMBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import keep.login.IKeepClientSBean;
import utils.EmailUtil;
import utils.JWTUtil;
import utils.MessageUtil;

@Named("MBRegister")
@SessionScoped
public class MBRegister extends BaseMBean {

	private static final long serialVersionUID = 4108901291531109382L;
	
	private String email;
	private String password;
	private String repeatedPassword;
	private String token;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	public void register() {
		if(verifyFields()) {
			sendConfirmationTokenEmail();
		}
	}
	
	public void sendConfirmationTokenEmail() {
		String title = "LeCoffee - Confirmação de email";
		
		StringBuilder description = new StringBuilder();
		
		description.append("<h2>Confirme o seu cadastro na LeCoffee</h2>");
		description.append("<p>Olá,	</p>");
		description.append("<p>Agradecemos por se cadastrar na LeCoffee! ");
		description.append("Estamos felizes em tê-lo(a) como nosso(a) cliente.</p>");
		description.append("Para finalizar o seu cadastro, por favor, confirme a sua conta clicando no link abaixo:</p>");
		description.append("<p><a href=https://www.devpree.com.br/lecoffee/pages/register.xhtml?token=");
		description.append(JWTUtil.generateToken("registerToken", title) + ">");
		description.append("Finalizar Cadastro</a></p>");
		description.append("<p>Caso você não tenha criado uma conta na LeCoffee ");
		description.append("ou acredite que este email tenha sido enviado por engano, por favor, desconsidere esta mensagem.</p>");
		description.append("Atenciosamente, <br>");
		description.append("A equipe LeCoffee <br>");
		
		EmailUtil.sendMail(email, title, description.toString(), MessageUtil.getMessageFromProperties("confirmation_email_sent"));	
	}
	
	public boolean verifyFields() {
		if(!EmailUtil.validateEmail(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		if(this.getClientSBean().verifyClient(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		if(!this.getPassword().equals(this.getRepeatedPassword())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_are_not_the_same"), FacesMessage.SEVERITY_WARN);
			return false;
		}
		
		return true;
	}
	
	// Getters and Setters
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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
	
}
