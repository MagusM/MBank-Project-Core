package wrapClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mBankExceptions.MBankException;
import beans.ClientBean;
import enums.AccountType;

/**
 * This is a wrapper class for client bean to be used by JAXB
 * @author Simon Mor
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"id", "name", "password", "type", "address", "email", "phone", "comment"})
public class ClientBeanWrapper {
	private String id, name, password, type, address, email, phone, comment;
	
	public ClientBeanWrapper() {super();}
	
	public ClientBeanWrapper(String id, String password, String email) {
		this.id = id;
		this.password = password;
		this.email = email;
	}
	
	public ClientBeanWrapper(ClientBean client) {
		this.id = client.getId()+"";
		this.name = client.getName();
		this.password = client.getPassword();
		this.type = client.getType();
		this.address = client.getAddress();
		this.email = client.getEmail();
		this.phone = client.getPhone();
		this.comment = client.getComment();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public ClientBean convertWrappedClientToClientBean() {
		ClientBean clientBean = new ClientBean();
		clientBean.setId(Integer.parseInt(this.getId()));
		try {
			clientBean.setName(this.getName());
			clientBean.setPassword(this.getPassword());
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		clientBean.setType(AccountType.getTypeFromString(this.getType()));
		clientBean.setEmail(this.getEmail());
		clientBean.setAddress(this.getAddress());
		clientBean.setPhone(this.getPhone());
		clientBean.setComment(this.getComment());
		return clientBean;
	}
	
	

}
