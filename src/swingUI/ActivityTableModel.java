package swingUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.ActivityBean;

/**
 * This class defines the activity table model
 * @author Simon Mor
 */
public class ActivityTableModel extends AbstractTableModel {
	private List<ActivityBean> activities = new ArrayList<ActivityBean>();
	private String[] columnNames = {"Activity ID", "Client ID", "Amount", "Commission", "Activity Date", "Description"};
	
	public ActivityTableModel() {
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return activities.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ActivityBean tempActivity = activities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return tempActivity.getId();
		case 1:
			return tempActivity.getClientID();
		case 2:
			return tempActivity.getAmount();
		case 3:
			return tempActivity.getCommission();
		case 4:
			return tempActivity.getActivityDate();
		case 5:
			return tempActivity.getDescription();
		}
		return null;
	}

	public void setActivities(List<ActivityBean> activities) {
		this.activities = activities;
	}
	
	public void setActivity(ActivityBean activity) {
		activities.add(activity);
	}
	
	public List<ActivityBean> getActivities() {
		return this.activities;
	}
	
	

}
