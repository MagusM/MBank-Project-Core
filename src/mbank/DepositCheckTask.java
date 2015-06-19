package mbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.sql.Date;

import mBankExceptions.MBankException;

/**
 * This class defines the Deposit Check Task Thread.<P>
 * Will activate itself every 24 hours.
 * @author Simon Mor
 */
public class DepositCheckTask implements Runnable {
	private ConnectionPool connectionPool;
	private Connection conn = null;
	private final long WAIT_TIME = 24 * 3600 * 1000; // hours * (min * sec) * mili
	
	 {
		try {
			connectionPool = ConnectionPool.getConnectionPool();
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
	}
	
	 private void clearConnection() {
			if (conn != null) {
				if (connectionPool.returnConnection(conn)) {
					conn = null;
				}
			}
		}
	 private static int counter = 0;
	
	@Override
	public void run() {
		try {
			Connection conn = connectionPool.getConnection();
			while (true) {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits");
				ResultSet rs = ps.executeQuery();
				Calendar cal = Calendar.getInstance();
				Date currentDate = new java.sql.Date(cal.getTimeInMillis());
				while (rs.next()) {
					if (!currentDate.before(rs.getDate("closing_date"))) {
						new managers.DepositDBManager().closeDeposit(rs.getInt("deposit_id"));
					}
				}
				Thread.sleep(WAIT_TIME);
				DepositCheckTask.counter++;
				System.out.println("Deposit check task #"+counter+" on Date:"+currentDate);
			}
		}
		catch (Exception e) {
			System.err.println("problem with check deposit task");
			System.err.println(e.getMessage());
		}
		finally {
			clearConnection();
		}
	}

}
