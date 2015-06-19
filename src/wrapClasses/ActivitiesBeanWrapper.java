package wrapClasses;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import beans.ActivityBean;

/**
 * This is a wrapper class for activities bean to be used by JAXB
 * @author Simon Mor
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"id", "clientID", "amount", "commission", "activityDate", "description"})
public class ActivitiesBeanWrapper {
	private int id, clientID;
	private double amount, commission;
	private Date activityDate;
	private String description;
	
	public ActivitiesBeanWrapper() {super();}

	public ActivitiesBeanWrapper(ActivityBean activityBean) {
		this.id = activityBean.getId();
		this.clientID = activityBean.getClientID();
		this.amount = activityBean.getAmount();
		this.commission = activityBean.getCommission();
		this.activityDate = activityBean.getActivityDate();
		this.description = activityBean.getDescription();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
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
	
	public ActivityBean convertWrappedActivityToActivityBean() {
		ActivityBean activityBean = new ActivityBean();
		activityBean.setId(this.getId());
		activityBean.setClientID(this.getClientID());
		activityBean.setAmount(this.getAmount());
		activityBean.setCommission(this.getCommission());
		activityBean.setActivityDate(this.getActivityDate());
		activityBean.setDescription(this.getDescription());
		return activityBean;
	}

}
