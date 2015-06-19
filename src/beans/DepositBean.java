package beans;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class defines the deposit bean
 * @author Simon Mor
 *
 */
@XmlRootElement
public class DepositBean {
	private int depositId, clientId, estimatedBalance; 
	private double balance;
	private enums.DepositType depositType;
	Date openingDate, closingDate;
	
	public DepositBean(){super();}
	
	public DepositBean(int clientId, double balance, Date openingDate, Date closingDate) {
		super();
		this.clientId = clientId;
		this.balance = balance;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
	}
	
	public DepositBean(int clientId, double balance, enums.DepositType depositType, int estimatedBalance, Date openingDate, Date closingDate) {
		super();
		this.clientId = clientId;
		this.estimatedBalance = estimatedBalance;
		this.balance = balance;
		this.depositType = depositType;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
	}
	
	public DepositBean(int depositId, int clientId, double balance, enums.DepositType depositType, int estimatedBalance, Date openingDate, Date closingDate) {
		super();
		this.depositId = depositId;
		this.clientId = clientId;
		this.estimatedBalance = estimatedBalance;
		this.balance = balance;
		this.depositType = depositType;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
	}
	
	public String getDepositType() {
		return depositType.toString();
	}

	public void setDepositType(enums.DepositType depositType) {
		this.depositType = depositType;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public int getDepositId() {
		return depositId;
	}

	public int getClientId() {
		return clientId;
	}

	public int getEstimatedBalance() {
		return estimatedBalance;
	}

	public double getBalance() {
		return balance;
	}

	public Date getOpeningDate() {
		return openingDate;
	}
	
	public void setDepositId(int depositId) {
		this.depositId = depositId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setEstimatedBalance(int estimatedBalance) {
		this.estimatedBalance = estimatedBalance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	@Override
	public String toString() {
		return "DepositBean [depositId=" + depositId + ", clientId=" + clientId
				+ ", estimatedBalance=" + estimatedBalance + ", balance="
				+ balance + ", depositType=" + depositType + ", openingDate="
				+ openingDate + ", closingDate=" + closingDate + "]";
	}
	
	
	
}
