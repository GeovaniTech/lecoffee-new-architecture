package managedBean.client;

import abstracts.BaseMBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import to.client.TOClient;

@Named("MBClientInfo")
@ViewScoped
public class MBClientInfo extends BaseMBean {
	
	private static final long serialVersionUID = 9183706213942034831L;
	
	private TOClient client;	
	
	// Getters and Setters
	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}
	
}
