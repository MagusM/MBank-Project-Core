package managers;

import interfaces.ActivityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mBankExceptions.MBankException;
import mbank.ConnectionPool;
import beans.ActivityBean;
import beans.ClientBean;

/**
 * this class defines the activity methods on the database
 * @author Simon Mor
 *
 */
public class ActivityDBManager extends Manager implements ActivityManager {
	private ConnectionPool connectionPool;
	private Connection conn = null;
	
	/**
	 * Constructor
	 */
	public ActivityDBManager() { 
		super();
		try {
			connectionPool = ConnectionPool.getConnectionPool();
		} catch (MBankException e) {
			e.printStackTrace();
		}
	}

	private void clearConnection() {
		if (conn != null) {
			if (connectionPool.returnConnection(conn)) {
				conn = null;
			}
		}
	}
	
	/**
	 * Views client's activities
	 * @param ClientBean client
	 * @return List<ActivityBean>
	 * @throws MBankException
	 */
	@Override
	public List<ActivityBean> viewClientActivities(ClientBean client) throws MBankException {
		List<ActivityBean> tempActivities = new ArrayList<ActivityBean>();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Activity WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ActivityBean tempAct = new ActivityBean(rs.getInt("id"), rs.getInt("client_id"), rs.getDouble("amount"), rs.getDate("activity_date"), 
						rs.getDouble("commission"), rs.getString("description"));
				tempActivities.add(tempAct);
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed viewing client:"+client.getId()+""+" activities", e);
		}
		finally {
			clearConnection();
		}
		return tempActivities;
	}
	
	/**
	 * View all activities
	 * @return List<ActivityBean>
	 * @throws MBankException
	 */
	@Override
	public List<ActivityBean> viewAllActivities() throws MBankException {
		List<ActivityBean> activityList = new ArrayList<ActivityBean>();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Activity", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ActivityBean tempAct = new ActivityBean(rs.getInt("id"), rs.getInt("client_id"), rs.getDouble("amount"), rs.getDate("activity_date"), 
						rs.getDouble("commission"), rs.getString("description"));
				activityList.add(tempAct);
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed viewing all activities", e);
		}
		finally {
			clearConnection();
		}
		return activityList;
	}
	
	/**
	 * Add a new activity to database
	 * @param ActivityBean activity
	 * @throws MBankException
	 */
	public void addNewActivity(ActivityBean activity) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Activity (client_id, amount, activity_date, commission, description) "
					+ "VALUES (?, ?, ?, ?, ?)");
			ps.setInt(1, activity.getClientID());
			ps.setDouble(2, activity.getAmount());
			ps.setDate(3, activity.getActivityDate());
			ps.setDouble(4, activity.getCommission());
			ps.setString(5, activity.getDescription());
			ps.execute();
		}
		catch (Exception e) {
			throw new MBankException("Failed adding activity", e);
		}
		finally {
			clearConnection();
		}
	}

}
