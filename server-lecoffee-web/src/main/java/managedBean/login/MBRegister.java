package managedBean.login;

import java.util.ArrayList;
import java.util.List;

import abstracts.BaseMBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import keep.login.IKeepClientSBean;
import utils.EmailUtil;
import utils.EncryptionUtil;
import utils.JWTUtil;
import utils.MessageUtil;
import utils.StringUtil;

@Named("MBRegister")
@SessionScoped
public class MBRegister extends BaseMBean {

	private static final long serialVersionUID = 4108901291531109382L;
	
	private String email;
	private String password;
	private String repeatedPassword;
	private String token;
	private List<String> blacklistTokens;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	public MBRegister() {
		this.setBlacklistTokens(new ArrayList<String>());
	}
	
	public void register() {
		if(verifyFields()) {
			this.getClientSBean().save(this.getEmail(), this.getPassword());
			sendConfirmationTokenEmail(this.getEmail());
		}
	}
	
	public void finishRegister() {
		if(StringUtil.isNotNull(this.getToken())) {
			String encryptedEmail = JWTUtil.getValueFromToken("registerToken", this.getToken());
			
			if(StringUtil.isNotNull(encryptedEmail) && !this.getBlacklistTokens().contains(this.getToken())) {
				this.getClientSBean().finishRegister(EncryptionUtil.decryptNormalText(encryptedEmail));
				this.getBlacklistTokens().add(this.getToken());
				this.resetFields();
				
				return;
			}
			
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_or_expired_token"), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void sendConfirmationTokenEmail(String email) {
		String title = "LeCoffee - Confirmação de email";
		
		StringBuilder description = new StringBuilder();
		
		description.append("<h2>Confirme o seu cadastro na LeCoffee</h2>");
		description.append("<p>Olá,	</p>");
		description.append("<p>Agradecemos por se cadastrar na LeCoffee! ");
		description.append("Estamos felizes em tê-lo(a) como nosso(a) cliente.</p>");
		description.append("Para finalizar o seu cadastro, por favor, confirme a sua conta clicando no link abaixo:</p>");
		description.append("<p><a href=http://localhost:8080/lecoffee/register/");
		description.append(JWTUtil.generateToken("registerToken", EncryptionUtil.encryptNormalText(email)) + ">");
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
	
	public void resetFields() {
		this.setEmail(null);
		this.setPassword(null);
		this.setRepeatedPassword(null);
		this.setToken(null);
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

	public List<String> getBlacklistTokens() {
		return blacklistTokens;
	}

	public void setBlacklistTokens(List<String> blacklistTokens) {
		this.blacklistTokens = blacklistTokens;
	}
	
}
