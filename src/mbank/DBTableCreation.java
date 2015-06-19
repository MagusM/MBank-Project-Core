package mbank;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * This class defines the database table creation
 * @author Simon Mor
 *
 */
public class DBTableCreation extends RegisterDBDriver {
	private MBank master = MBank.getMBank();
	private Connection conn;
	private String ClientTable="CREATE table Clients ("
			+ "client_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ "client_name VARCHAR(30),"
			+ "password VARCHAR(10),"
			+ "type VARCHAR(30),"
			+ "address VARCHAR(30),"
			+ "email VARCHAR(30),"
			+ "phone VARCHAR(15),"
			+ "comment VARCHAR(30))";
	private String accountsTable="CREATE table Accounts ("
			+ "account_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ "Foreign Key (client_id) BIGINT REFERENCES Clients (client_id)"
			+ "balance DOUBLE,"
			+ "credit_limit DOUBLE,"
			+ "comment VARCHAR(30))";
	private String tableDeposits="(CREATE table Deposits ("
			+ "deposit_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ "Foreign Key (client_id) BIGINT REFERENCES Clients (client_id),"
			+ "balance DOUBLE,"
			+ "type VARCHAR(30),"
			+ "estimated_balance BIGINT,"
			+ "opening_date DATE,"
			+ "closing_date DATE)";
	private String activityTable="CREATE table Activity ("
			+ "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ "Foreign Key (client_id) BIGINT REFERENCES Clients (clients_id),"
			+ "amount DOUBLE,"
			+ "activity_date DATE,"
			+ "commission DOUBLE,"
			+ "description VARCHAR(100))";
	private String tableProperties="CRAETE table Properties("
			+ "prop_key VARCHAR(30) NOT NULL,"
			+ "prop_value VARCHAR(30) NOT NULL)";

	public DBTableCreation() throws Exception {
		//super constructor registers the derby driver
		super();
		//get connection
		try {
			conn = master.connectionPool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PreparedStatement ps = conn.prepareStatement(ClientTable);
		ps.addBatch(accountsTable);
		ps.addBatch(tableDeposits);
		ps.addBatch(activityTable);
		ps.addBatch(tableProperties);
		//execute table creation statement update 
		//stm.executeUpdate(ClientTable);
//		stm.executeUpdate(accountsTable);
//		stm.executeUpdate(tableDeposits);
//		stm.executeUpdate(activityTable);
//		stm.executeUpdate(tableProperties);
		ps.execute();
		ps.clearBatch();
		
		ps.addBatch("INSERT INTO properties (prop_key,prop_value) VALUES (?,?,?)");
		String[] propKeyArray = {"regular_deposit_rate", "gold_deposit_rate", "platinum_deposit_rate", "regular_deposit_commission", "gold_deposit_commission", 
				"platinum_deposit_commission", "regular_credit_limit", "gold_credit_limit", "platinum_credit_limit", "commission_rate", "regular_daily_interest",
				"pre_open_fee", "admin_username", "admin_password"};
		String[] propValueArray = { "10000$", "100000$", "1000000$", "1.5%", "1.0%", "0.5%", "100000$", "1000000$", "unlimited", "0.5$", "5/365", "7/365",
				"8/365", "1%", "system", "admin"};
		for (int i=0;i<propKeyArray.length;i++) {
			ps.setString(1, propKeyArray[i]);
			ps.setString(2, propValueArray[i]);
			ps.execute();
		}

	}

}
