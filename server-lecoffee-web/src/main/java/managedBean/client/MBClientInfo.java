package managedBean.client;

import abstracts.AbstractMBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import to.client.TOClient;

@Named("MBClientInfo")
@ViewScoped
public class MBClientInfo extends AbstractMBean {
	
	private static final long serialVersionUID = 9183706213942034831L;
	
	private TOClient client;	
	
	public void save() {
		
	}
	
	public void change() {
		
	}
	
	// Getters and Setters
	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}
	
}
