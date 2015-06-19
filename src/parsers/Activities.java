package parsers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import wrapClasses.ActivitiesBeanWrapper;
import beans.ActivityBean;

@XmlRootElement(name="activities")
@XmlAccessorType(XmlAccessType.FIELD)
public class Activities {
	
	@XmlElement(name="activity", type=ActivityBean.class)
	private List<ActivityBean> activities;

	public List<ActivityBean> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityBean> activitiesList) {
		List<ActivitiesBeanWrapper> wrappedActivities = new ArrayList<ActivitiesBeanWrapper>();
		for (ActivityBean activityBean : activitiesList) {
			wrappedActivities.add(new ActivitiesBeanWrapper(activityBean));
		}
		this.activities = activitiesList;
	}

}
