package beans;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * This class defines the account bean
 * @author Simon Mor
 */
@XmlRootElement
public class AccountsBean {
	private int accountId, clientId;
	private double balance, creditLimit;
	private String comment;
	
	public AccountsBean(){super();}
	
	public AccountsBean(int clientId, double balance, String comment) {
		super();
		this.clientId = clientId;
		this.balance = balance;
		this.comment = comment;
	}
	
	public AccountsBean(int clientId, double balance,
			double creditLimit, String comment) {
		super();
		this.clientId = clientId;
		this.balance = balance;
		this.creditLimit = creditLimit;
		this.comment = comment;
	}
	
	public AccountsBean(int accountId, int clientId, double balance,
			double creditLimit, String comment) {
		super();
		this.accountId = accountId;
		this.clientId = clientId;
		this.balance = balance;
		this.creditLimit = creditLimit;
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getAccountId() {
		return accountId;
	}

	public int getClientId() {
		return clientId;
	}

	public double getBalance() {
		return balance;
	}

	public double getCreditLimit() {
		return creditLimit;
	}
	

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public String toString() {
		return "AccountsBean [accountId=" + accountId + ", clientId="
				+ clientId + ", balance=" + balance + ", creditLimit="
				+ creditLimit + ", comment=" + comment + "]";
	}
	
	

}
