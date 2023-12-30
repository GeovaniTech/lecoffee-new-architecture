package managedBean.client;

import java.util.Date;

import org.primefaces.event.SelectEvent;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.address.IKeepAddressSBean;
import keep.client.IKeepClientSBean;
import to.address.TOAddress;
import to.address.TOCepAPIViaCep;
import to.client.TOClient;
import utils.CepUtil;
import utils.JsonUtil;
import utils.MessageUtil;
import utils.StringUtil;

@Named("MBClientAddress")
@ViewScoped
public class MBClientAddress extends AbstractMBean {

	private static final long serialVersionUID = 5915193679411589406L;
	
	private TOClient client;
	private TOAddress addressSelected;
	private TOAddress address;
	private boolean editing;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	@EJB
	private IKeepAddressSBean addressSBean;
	
	@PostConstruct
	@Override
	public void init() {
		this.setAddress(new TOAddress());
		this.setEditing(false);
	}
	
	public void addAddress() {
		try {
			if(isValidAddress()) {
				this.getClient().getAdresses().add(this.getAddress());
				this.getClientSBean().change(this.getClient());
				
				this.clearFields();
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_address_add"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_address_add"), FacesMessage.SEVERITY_ERROR);
		}
		
		
	}
	
	public void removeAddress(TOAddress address) {
		this.getClient().getAdresses().remove(address);
	}
	
	public void saveAddress() {	
		try {
			for(TOAddress address : this.getClient().getAdresses()) {
				if(address.getCreationDate() == null) {
					address.setCreationDate(new Date());
					address.setCreationUser(this.getClientSession().getEmail());
					
					this.getAddressSBean().save(address);
					
					continue;
				}
				
				address.setChangeDate(new Date());
				address.setChangeUser(this.getClientSession().getEmail());
				
				this.getAddressSBean().change(address);
			}
			
			this.getClientSBean().change(this.getClient());
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_address_add"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_address_add"), FacesMessage.SEVERITY_ERROR);
		}
		
		
	}
	
	public void getCEPInformations() {
		if(this.getAddress().getCep() != null && !this.getAddress().getCep().toString().equals("")) {
			try {
				TOCepAPIViaCep cep = JsonUtil.convertJson(CepUtil.getCepJSON(this.getAddress().getCep()), TOCepAPIViaCep.class);
				
				if(StringUtil.isNotNull(cep.getLocalidade()) && !cep.getLocalidade().equals("Blumenau")) {
					MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_from_other_city"), FacesMessage.SEVERITY_ERROR);
					return;
				} else if(StringUtil.isNull(cep.getLocalidade())) {
					MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_not_found"), FacesMessage.SEVERITY_ERROR);
					return;
				}
				
				this.getAddress().setNeighborhood(cep.getBairro());
				this.getAddress().setStreet(cep.getLogradouro());
			} catch (Exception e) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_not_found"), FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		}
	}
	
	public void changeAddress() {
		this.getAddressSBean().change(this.getAddress());
		this.clearFields();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_address_changed"), FacesMessage.SEVERITY_INFO);
	}
	
	public void removeAddress() {
		try {
			this.getClient().getAdresses().remove(this.getAddress());
			this.getClientSBean().change(this.getClient());
			
			this.getAddressSBean().remove(this.getAddress());
			
			this.setAddress(new TOAddress());
			this.setEditing(false);
			
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_address_removed"), FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_removing_address"), FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
	}
	
    public void onRowSelect(SelectEvent<TOAddress> event) {
    	this.setEditing(true);
    	
    	if(this.getAddressSelected() != null) {
    		this.setAddress(this.getAddressSelected());
    	}
    }
    
    public void clearFields() {
    	this.setEditing(false);
    	this.setAddress(new TOAddress());
    	this.setAddressSelected(null);
    }
    
    public boolean isValidAddress() {
    	if(this.getAddress() != null) {
    		if(StringUtil.isNull(this.getAddress().getStreet())) {
    			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("street_required"), FacesMessage.SEVERITY_WARN);
    			return false;
    		}
    		
    		if(StringUtil.isNull(this.getAddress().getNeighborhood())) {
    			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("neighborhood_required"), FacesMessage.SEVERITY_WARN);
    			return false;
    		}
    	}
    	
    	
    	return true;
    }
	
	// Getters and Setters
	public TOClient getClient() {
		return client;
	}
	
	public void setClient(TOClient client) {
		this.client = client;
	}
	
	public TOAddress getAddress() {
		return address;
	}
	
	public void setAddress(TOAddress address) {
		this.address = address;
	}

	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public IKeepAddressSBean getAddressSBean() {
		return addressSBean;
	}

	public void setAddressSBean(IKeepAddressSBean addressSBean) {
		this.addressSBean = addressSBean;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public TOAddress getAddressSelected() {
		return addressSelected;
	}

	public void setAddressSelected(TOAddress addressSelected) {
		this.addressSelected = addressSelected;
	}

}
