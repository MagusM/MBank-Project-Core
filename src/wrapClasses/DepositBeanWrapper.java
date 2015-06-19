package wrapClasses;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import beans.DepositBean;
import enums.DepositType;

/**
 * This is a wrapper class for deposit bean to be used by JAXB
 * @author Simon Mor
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"depositId", "depositId", "clientId", "estimatedBalance", "balance", "depositType", "openingDate", "closingDate"})
public class DepositBeanWrapper {
	
	private int depositId, clientId, estimatedBalance; 
	private double balance;
	private String depositType;
	private Date openingDate, closingDate;
	
	public DepositBeanWrapper() {}
	
	public DepositBeanWrapper(DepositBean deposit) {
		this.depositId = deposit.getDepositId();
		this.clientId = deposit.getClientId();
		this.estimatedBalance = deposit.getEstimatedBalance();
		this.depositType = deposit.getDepositType();
		this.openingDate = deposit.getOpeningDate();
		this.closingDate = deposit.getClosingDate();
	}

	public int getDepositId() {
		return depositId;
	}

	public void setDepositId(int depositId) {
		this.depositId = depositId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getEstimatedBalance() {
		return estimatedBalance;
	}

	public void setEstimatedBalance(int estimatedBalance) {
		this.estimatedBalance = estimatedBalance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}
	
	public DepositBean convertWrappedDepositToDepositBean() {
		DepositBean depositBean = new DepositBean();
		depositBean.setDepositId(this.getDepositId());
		depositBean.setClientId(this.getClientId());
		depositBean.setDepositType(DepositType.convertStringToDepositType(this.getDepositType()));
		depositBean.setEstimatedBalance(this.getEstimatedBalance());
		depositBean.setOpeningDate(this.getOpeningDate());
		depositBean.setClosingDate(this.getClosingDate());
		return depositBean;
	}
	
}
