package wrapClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import beans.AccountsBean;

/**
 * This is a wrapper class for accounts bean to be used by JAXB
 * @author Simon Mor
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"accountId", "clientId", "balance", "creditLimit", "comment"})
public class AccountsBeanWrapper {
	private int accountId, clientId;
	private double balance, creditLimit;
	private String comment;
	
	public AccountsBeanWrapper(){super();}

	public AccountsBeanWrapper(AccountsBean account) {
		this.accountId = account.getAccountId();
		this.clientId = account.getClientId();
		this.balance = account.getBalance();
		this.creditLimit = account.getCreditLimit();
		this.comment = account.getComment();
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public AccountsBean convertWrappedAccountToAccountBean() {
		AccountsBean accountBean = new AccountsBean();
		accountBean.setAccountId(this.getAccountId());
		accountBean.setClientId(this.getClientId());
		accountBean.setBalance(this.balance);
		accountBean.setCreditLimit(this.getCreditLimit());
		accountBean.setComment(this.getComment());
		return accountBean;
	}
	
	

}
