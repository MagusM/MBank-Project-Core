package beans;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the activity bean
 * @author Simon Mor
 *
 */

@XmlRootElement
public class ActivityBean {
	private int id, clientID;
	private double amount, commission;
	private Date activityDate;
	private String description;
	
	public ActivityBean(){super();}
	
	public ActivityBean(int id, int clientID, double amount,
			Date activityDate, double commission, String description) {
		super();
		this.id = id;
		this.clientID = clientID;
		this.amount = amount;
		this.commission = commission;
		this.activityDate = activityDate;
		this.description = description;
	}
	
	public ActivityBean(int clientID, double amount,
			Date activityDate, double commission, String description) {
		super();
		this.clientID = clientID;
		this.amount = amount;
		this.commission = commission;
		this.activityDate = activityDate;
		this.description = description;
	}
	
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public int getClientID() {
		return clientID;
	}
	public double getAmount() {
		return amount;
	}
	public double getCommission() {
		return commission;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	
	@Override
	public String toString() {
		return "ActivityBean [id=" + id + ", clientID=" + clientID
				+ ", amount=" + amount + ", commission=" + commission
				+ ", activityDate=" + activityDate + ", description="
				+ description + "]";
	}
	

}
