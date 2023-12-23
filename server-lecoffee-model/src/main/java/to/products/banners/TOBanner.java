package to.products.banners;

import abstracts.AbstractTOObject;

public class TOBanner extends AbstractTOObject {

	private static final long serialVersionUID = -8920243069417227893L;

	private int id;
	private String name;
	private Integer priority;
	private byte[] bytes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
