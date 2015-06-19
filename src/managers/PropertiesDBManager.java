package managers;

import interfaces.PropertiesManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mBankExceptions.MBankException;
import mbank.ConnectionPool;
import beans.PropertiesBean;

/**
 * this class defines the properties methods on the database
 * @author Simon Mor
 *
 */
public class PropertiesDBManager extends Manager implements PropertiesManager  {
	private ConnectionPool connectionPool;
	private Connection conn = null;
	
	/**
	 * Constructor
	 */
	public PropertiesDBManager() { 
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
	 * Updates a system property
	 * @param PropertiesBean pb
	 * @throws MBankException
	 */
	public void updateSystemProperty(PropertiesBean pb) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE Properties SET prop_value=? WHERE prop_key=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, pb.getPropValue());
			ps.setString(2, pb.getPropKey());
			ps.execute();

		}
		catch (Exception e) {
			throw new MBankException("Failed updating system property:"+pb.getPropKey()+"", e);
		}
		finally {
			clearConnection();
		}

	}
	
	/**
	 * Views a system property
	 * @param String propKey
	 * @return PropertiesBean
	 * @throws MBankException
	 */
	@Override
	public PropertiesBean viewSystemProperty(String propKey) throws MBankException {
		PropertiesBean tempProperty = new PropertiesBean();
		tempProperty.setPropKey(propKey);
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT prop_value FROM Properties WHERE prop_key=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, propKey);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tempProperty.setPropValue(rs.getString("prop_value"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MBankException("failed viewing system property: "+propKey, e);
		}
		finally {
			clearConnection();
		}
		return tempProperty;
	}
	
	/**
	 * Adds String to String
	 * @param String str
	 * @return String
	 */
	public static String stringAddDepositRate(String str) {
		return str+"_deposit_rate";
	}
	
	/**
	 * Adds String to String
	 * @param String str
	 * @return String
	 */
	public static String stringAddCreditLimit(String str) {
		return str+"_credit_limit";
	}
	
	/**
	 * Adds String to String
	 * @param String str
	 * @return String
	 */
	public static String stringAddDailyInterest(String str) {
		return str+"_daily_interest";
	}
	
	// for testing
//	public List<PropertiesBean> getpropsList() {
//		List<PropertiesBean> temp = new ArrayList<PropertiesBean>();
//		conn = connectionPool.getConnection();
//		try {
//			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Properties", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				temp.add(new PropertiesBean(rs.getString("prop_key"), rs.getString("prop_value")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("WOOOOOOOOT!?!?!??!");
//		}
//		finally {
//			clearConnection();
//		}
//		return temp;
//	}

}
