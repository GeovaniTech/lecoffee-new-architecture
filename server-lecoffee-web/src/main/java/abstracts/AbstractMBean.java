package abstracts;

import java.io.Serializable;

import jakarta.faces.application.FacesMessage;
import utils.MessageUtil;

public abstract class AbstractMBean extends AbstractSession implements Serializable {
	private static final long serialVersionUID = -3126954606226723860L;
	
	public abstract void init();
	
	public void showMessageItemSaved(String itemName) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_saved", itemName), FacesMessage.SEVERITY_INFO);
	}
	
	public void showMessageItemChanged(String itemName) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_changed", itemName), FacesMessage.SEVERITY_INFO);
	}
	
	public void showMessageItemRemoved(String itemName) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_removed", itemName), FacesMessage.SEVERITY_ERROR);
	}
	
	public void showMessageError(Exception e) {
		MessageUtil.sendMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
	}
}
