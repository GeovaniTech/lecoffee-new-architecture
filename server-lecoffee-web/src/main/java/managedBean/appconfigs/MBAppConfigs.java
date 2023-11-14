package managedBean.appconfigs;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import keep.client.IKeepClientSBean;
import model.AppConfigs;
import to.client.TOClient;
import utils.CookieUtil;
import utils.EncryptionUtil;
import utils.ImageUtil;
import utils.RedirectURL;
import utils.StringUtil;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends AbstractMBean {

	private static final long serialVersionUID = 8432905268667991640L;
	
	private AppConfigs appConfigs;
	private List<Locale> localeList;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public MBAppConfigs() {
		//Attributes
		this.setAppConfigs(new AppConfigs());
		this.setLocaleList(new ArrayList<Locale>());
		this.getLocaleList().add(new Locale("pt_BR"));
		this.getLocaleList().add(new Locale("en_US"));
		
		//Initial Configurations
		this.getAppConfigs().setLanguage(Locale.getDefault().getLanguage());
		this.getAppConfigs().setDarkMode(false);
		
		//Getting User preferences
		this.getConfigsFromCookies();	
	}
	
	@PostConstruct
	public void init() {		
		redirectUserFromCookie();
	}
	
 	public boolean getConfigsFromCookies() {
 		this.getAppConfigs().setDarkMode(CookieUtil.getDarkModeCookie());
 		
 		if(CookieUtil.getLanguageCookie() != null) {
 			this.getAppConfigs().setLanguage(CookieUtil.getLanguageCookie());
 			
 			return true;
 		}
 		return false;
 	}
	
	public void redirectUserFromCookie() {
		String userEmail = CookieUtil.getUserCookie();
		
		if(StringUtil.isNotNull(userEmail)) {
			try {
				TOClient client =  this.getClientSBean().findByEmail(EncryptionUtil.decryptNormalText(userEmail));
				
				if(client.isBlocked()) {
					RedirectURL.redirectTo("/lecoffee/login");
					
					return;
				}
				
				this.getSession().setAttribute("client", client);
				
				client.setLastLogin(new Date());
				this.getClientSBean().change(client);
			
				if(this.getClientLogged().getSecurityLevel().equals("admin")) {
					RedirectURL.redirectTo("/lecoffee/admin/clients");
				} else {
					RedirectURL.redirectTo("/lecoffee/home");
				}
			} catch (Exception e) {
				e.printStackTrace();
				removeUserFromCookie();
				RedirectURL.redirectTo("/lecoffee/home");
			}
		}
	}
	
	public void createCookiePreferences() {	
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie darkMode = new Cookie("darkMode", "" + this.getAppConfigs().isDarkMode());
		darkMode.setMaxAge(60*60*24*30);
		darkMode.setPath("/lecoffee");
		
		Cookie language = new Cookie("language", this.getAppConfigs().getLanguage());
		language.setMaxAge(60*60*24*30);
		language.setPath("/lecoffee");
		
		response.addCookie(darkMode);
		response.addCookie(language);
	}
	
	public void removeUserFromCookie() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie userSession = new Cookie("userSession", null);
		userSession.setMaxAge(60*60*24*30);
		userSession.setPath("/lecoffee");
		
		response.addCookie(userSession);
	}
	
	public void logout() {
		removeUserFromCookie();
		finishSession();
		
		RedirectURL.redirectTo("/lecoffee/login");
	}
	
	public boolean isUserLogged() {
		if(this.getClient() != null) {
			return true;
		}
		
		return false;
	}
	
	public void redirectTo(String url) {
		RedirectURL.redirectTo(url);
	}
	
	public String getBrazilianCurrency(Double value) {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(localeBR);
		String formattedValue = brazilianFormat.format(value);

		return formattedValue;
	}

	public String getRenderedImage(byte[] imageBytes) {
		return ImageUtil.geRenderedImage(imageBytes);
	}

	public void refreshPage() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	// Getters and Setters
	public AppConfigs getAppConfigs() {
		return appConfigs;
	}
	public void setAppConfigs(AppConfigs appConfigs) {
		this.appConfigs = appConfigs;
	}
	public List<Locale> getLocaleList() {
		return localeList;
	}
	public void setLocaleList(List<Locale> localeList) {
		this.localeList = localeList;
	}
	public TOClient getClientLogged() {
		return this.getClient();
	}
	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
	
}

