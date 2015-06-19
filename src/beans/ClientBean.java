package beans;

import javax.xml.bind.annotation.XmlRootElement;

import mBankExceptions.MBankException;
import enums.AccountType;

/**
 * This class defines the client bean
 * @author Simon Mor
 *
 */
@XmlRootElement
public class ClientBean {
	private int id;
	private String name, password, address, email, phone, comment;
	private enums.AccountType type;
	
	public ClientBean(){super();}
	
	public ClientBean(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public ClientBean(String name,String password,enums.AccountType type,String address,String email,String phone,String comment) throws MBankException{
		super(); 
		this.setName(name);
		this.setPassword(password);
		this.type = type;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.comment = comment;
	}
	
	public ClientBean(int id,String name,String password,enums.AccountType type,String address,String email,String phone,String comment) throws MBankException{
		super(); 
		this.id = id;
		this.setName(name);
		this.setPassword(password);
		this.type = type;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.comment = comment;
	}
	
	public int getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws MBankException {
		if (name != null) {
			this.name = name;
		}
		else {
			System.err.println("Name cannot be null");
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws MBankException {
		if (password != null) {
			this.password = password;
		}
		else {
			System.err.println("Name cannot be null");
		}
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
	
	public void setType(AccountType type) {
			this.type = type;
		}
	
	public String getType() {
		return type.toString();
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ClientBean [id=" + id + ", name=" + name + ", password="
				+ password + ", address=" + address + ", email=" + email
				+ ", phone=" + phone + ", comment=" + comment + ", type="
				+ type + "]";
	}
	

}



