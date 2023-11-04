package managedBean.appconfigs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import abstracts.BaseMBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import model.AppConfigs;
import utils.RedirectURL;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends BaseMBean {

	private static final long serialVersionUID = 1L;
	
	private AppConfigs appConfigs;
	private List<Locale> localeList;
	
	public MBAppConfigs() {
		//Attributes
		this.setAppConfigs(new AppConfigs());
		this.setLocaleList(new ArrayList<Locale>());
		this.getLocaleList().add(new Locale("pt_BR"));
		this.getLocaleList().add(new Locale("en_US"));
		
		//Initial Configurations
		this.getAppConfigs().setLanguage(Locale.getDefault().getLanguage());
		this.getAppConfigs().setDarkMode(false);
	}
	
	public void redirectTo(String url) {
		RedirectURL.redirectTo(url);
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
}

