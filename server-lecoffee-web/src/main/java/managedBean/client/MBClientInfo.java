package managedBean.client;

import java.util.Date;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.client.IKeepClientSBean;
import to.client.TOClient;
import utils.EmailUtil;
import utils.MessageUtil;

@Named("MBClientInfo")
@ViewScoped
public class MBClientInfo extends AbstractMBean {
	
	private static final long serialVersionUID = 9183706213942034831L;
	
	private TOClient client;
	private boolean editing;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	public void save() {
		try {
			if(this.isEmailUserValid()) {
				this.getClient().setCreationUser(this.getClientSession().getEmail());
				this.getClient().setCreationDate(new Date());
				
				this.getClientSBean().save(this.getClient());
				this.setEditing(true);
				
				this.setClient(this.getClientSBean().findByEmail(this.getClient().getEmail()));
				this.getClientAddressMBean().setClient(this.getClient());
				
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_client_saved"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_saving_client"), FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}	
	}
	
	public void change() {
		try {
			if(this.isEmailUserValid()) {
				this.getClient().setChangeUser(this.getClientSession().getEmail());
				this.getClient().setChangeDate(new Date());
				
				this.getClientSBean().change(this.getClient());
				
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_client_edited"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_editing_client"), FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
	}
	
	public boolean isEmailUserValid() {
		if(!EmailUtil.isValidEmailPattern(this.getClient().getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		if(this.getClientSBean().existsClientByEmail(this.getClient().getEmail(), this.getClient().getId())) {

			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		return true;
	}
	
    public MBClientAddress getClientAddressMBean() {
    	return (MBClientAddress) this.getMBean("MBClientAddress");
    }
	
	// Getters and Settersclient
	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}

	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}
	
	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	
}
