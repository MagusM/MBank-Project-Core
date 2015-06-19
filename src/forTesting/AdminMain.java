package forTesting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mBankExceptions.MBankException;
import mbank.ConnectionPool;
import mbank.MBank;


/**
 * This class is just like trash and test class, please ignore it.
 * @author Simon Mor
 *
 */
public class AdminMain {
	public static void main(String[] args) throws Exception {
				String ClientTable="CREATE TABLE Clients ("
						+ "client_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT client_id PRIMARY KEY,"
						+ "client_name VARCHAR(30),"
						+ "password VARCHAR(10),"
						+ "type VARCHAR(30),"
						+ "address VARCHAR(30),"
						+ "email VARCHAR(30),"
						+ "phone VARCHAR(15),"
						+ "comment VARCHAR(30))";
				String accountsTable="CREATE TABLE Accounts ("
						+ "account_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT account_id PRIMARY KEY,"
						+ "client_id INTEGER,"
						+ "balance DOUBLE,"
						+ "credit_limit DOUBLE,"
						+ "comment VARCHAR(30))";
				String tableDeposits="CREATE TABLE Deposits ("
						+ "deposit_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT deposit_id PRIMARY KEY,"
						+ "client_id INTEGER,"
						+ "balance DOUBLE,"
						+ "type VARCHAR(30),"
						+ "estimated_balance INTEGER,"
						+ "opening_date DATE,"
						+ "closing_date DATE)";
				String activityTable="CREATE TABLE Activity ("
						+ "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT id PRIMARY KEY,"
						+ "client_id INTEGER,"
						+ "amount DOUBLE,"
						+ "activity_date DATE,"
						+ "commission DOUBLE,"
						+ "description VARCHAR(100))";
				String tableProperties="CREATE TABLE Properties("
						+ "prop_key VARCHAR(30) NOT NULL,"
						+ "prop_value VARCHAR(30) NOT NULL)";

				String[] propKey = {"regular_deposit_rate", "gold_deposit_rate", "platinum_deposit_rate", "regular_deposit_commission", "gold_deposit_commission", 
						"platinum_deposit_commission", "regular_credit_limit", "gold_credit_limit", "platinum_credit_limit", "commission_rate", "regular_daily_interest",
						"gold_daily_interest", "platinum_daily_interest", "pre_open_fee", "admin_username", "admin_password"};
				String[] propValue = {"10000", "100000", "1000000", ((1.5/100d)+""), ((1.0/100d)+""), ((0.5/100d)+""), (100000+""),
						"1000000", "unlimited", ((0.5/100d)+""), ((5/365d)+""), ((7/365d)+""), ((8/365d)+""), ((1/100d)+""),
						"system", "admin"};


//		ConnectionPool connectionPool = null;
//		try {
//			connectionPool = ConnectionPool.getConnectionPool();
//		} catch (MBankException e1) {
//			System.out.println(e1.getMessage());
//			e1.printStackTrace();
//		}
//
		MBank master = MBank.getMBank();
		
//		/*
//		 * load the MBank app (swing)
//		 */
//		SwingUtilities.invokeLater(new Runnable() {
//			{
//				System.out.println("MBank App is now running");
//			}
//			@Override
//			public void run() {
//				new swingUI.MainFrame();
//			}
//		});





		///////////////test//////////////

//		Connection conn = connectionPool.getConnection();

//		try {
//			PreparedStatement ps1 = conn.prepareStatement("DELETE FROM Clients");
//			PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Accounts");
//			PreparedStatement ps3 = conn.prepareStatement("DELETE FROM Deposits");
//			PreparedStatement ps4 = conn.prepareStatement("DELETE FROM Activity");
//			
//			ps1.execute();
//			ps2.execute();
//			ps3.execute();
//			ps4.execute();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}



//		Connection con = connectionPool.getConnection();
		//con.createStatement().execute(ClientTable);
		//con.createStatement().execute(accountsTable);
		//con.createStatement().execute(tableDeposits);
		//con.createStatement().execute(activityTable);
		//con.createStatement().execute(tableProperties);
		
//		PreparedStatement ps ;
//		for (int i = 0; i < propValue.length; i++) {
//			ps = con.prepareStatement("INSERT INTO Properties (prop_key, prop_value) VALUES (?, ?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			ps.setString(1, propKey[i]);
//			ps.setString(2, propValue[i]);
//			ps.execute();
//		}

				master.start();


	}	
}










