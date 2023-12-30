package managedBean.products.banners;

import java.io.IOException;
import java.util.Date;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.products.banners.IKeepBannerSBean;
import to.products.banners.TOBanner;
import utils.FileUtil;
import utils.MessageUtil;

@Named(MBBannerInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBBannerInfo extends AbstractMBean {

	private static final long serialVersionUID = 267690876074804550L;
	public static final String MANAGED_BEAN_NAME = "MBBannerInfo";
	
	private TOBanner banner;
	private boolean editing;
	
	@EJB
	private IKeepBannerSBean bannerSBean;
	
	@PostConstruct
	@Override
	public void init() {
		
	}
	
	public void initBanner() {
		this.setBanner(new TOBanner());
		this.setEditing(false);
		
		PrimeFaces.current().ajax().update("tabViewProducts:dialogBannerInfo:formBannerInfo");
	}
	
	public void editBanner(TOBanner banner) {
		this.setBanner(banner);
		this.setEditing(true);
		
		PrimeFaces.current().ajax().update("tabViewProducts:dialogBannerInfo:formBannerInfo");
	}
	
	public void save() {
		try {
			if(this.isValidBanner()) {
				this.getBanner().setCreationUser(this.getClientSession().getEmail());
				this.getBanner().setCreationDate(new Date());
				
				this.getBannerSBean().save(this.getBanner());
				this.setEditing(true);
				
				showMessageItemSaved(this.getBanner().getName());
			}
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void change() {
		try {
			if(isValidBanner()) {
				this.getBanner().setChangeUser(this.getClientSession().getEmail());
				this.getBanner().setChangeDate(new Date());
				
				this.getBannerSBean().change(this.getBanner());
				
				showMessageItemChanged(this.getBanner().getName());
			}
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void remove() {
		try {
			this.getBannerSBean().remove(this.getBanner());
			
			showMessageItemRemoved(this.getBanner().getName());
			this.initBanner();
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public boolean isValidBanner() {
		if(this.getBanner().getBytes() != null) {
			return true;
		} 
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), FacesMessage.SEVERITY_ERROR);
		
		return false;
	}
	
	public void addImage(FileUploadEvent event) throws IOException {		
		this.getBanner().setBytes(FileUtil.getBytesFromPrimefacesFile(event.getFile()));
	}

	public TOBanner getBanner() {
		return banner;
	}

	public void setBanner(TOBanner banner) {
		this.banner = banner;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public IKeepBannerSBean getBannerSBean() {
		return bannerSBean;
	}

	public void setBannerSBean(IKeepBannerSBean bannerSBean) {
		this.bannerSBean = bannerSBean;
	}

}
