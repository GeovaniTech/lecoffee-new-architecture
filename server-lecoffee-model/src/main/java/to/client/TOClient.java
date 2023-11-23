package to.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abstracts.AbstractTOObject;
import to.address.TOAddress;

public class TOClient extends AbstractTOObject {

	private static final long serialVersionUID = -7590357042811071338L;
	
	private int id;
	private String email;
	private String phoneNumber;
	private String securityLevel;
	private Date lastLogin;
	private boolean blocked;
	private boolean changePassword;
	private List<TOAddress> adresses;
	
	public TOClient() {
		this.setSecurityLevel("client");
		this.setAdresses(new ArrayList<TOAddress>());
	}
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isChangePassword() {
		return changePassword;
	}
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}
	public List<TOAddress> getAdresses() {
		return adresses;
	}
	public void setAdresses(List<TOAddress> adresses) {
		this.adresses = adresses;
	}
	
}
