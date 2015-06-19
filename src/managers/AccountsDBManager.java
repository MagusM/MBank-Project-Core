package managers;

import interfaces.AccountsManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mBankExceptions.MBankException;
import mbank.ConnectionPool;
import beans.AccountsBean;
import beans.ActivityBean;
import beans.ClientBean;

/**
 * this class defines the accounts methods on the database
 * @author Simon Mor
 *
 */
public class AccountsDBManager extends Manager implements AccountsManager {
	private PropertiesDBManager pdbm = new PropertiesDBManager();
	private ActivityDBManager adbm = new ActivityDBManager();
	private ConnectionPool connectionPool;
	private Connection conn = null;
	
	/**
	 * Constructor<p>
	 * instantiate the connection pool
	 */
	public AccountsDBManager() {
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
	 * Creates a new account in database
	 * @param ClientBean client
	 * @param AccountsBean account
	 * @throws MBankException
	 */
	@Override
	public void createNewAccount(ClientBean client, AccountsBean account) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Accounts (client_id, balance, credit_limit, comment) VALUES (?, ?, ?, ?)", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ps.setDouble(2, account.getBalance());
			double creditLimit = 0;
			/*
			 * check client's type
			 */
			if ("platinum".equals(client.getType())) {
				creditLimit = Double.MAX_VALUE;
				account.setComment("Unlimited Credit Limit");
			}
			else {
				creditLimit = Double.parseDouble(pdbm.viewSystemProperty(PropertiesDBManager.stringAddCreditLimit(client.getType())).getPropValue());
			}
			ps.setDouble(3, creditLimit);
			ps.setString(4, account.getComment());
			ps.execute();
		}
		catch (Exception e) {
			throw new MBankException("Failed creating new Acount for Client:"+client.getId()+"", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * Removes a specific account from database
	 * @param AccountsBean account
	 * @throws MBankException
	 */
	public void removeAccount(AccountsBean account) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Accounts WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, (int) account.getClientId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rs.deleteRow();
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed removing account:"+account.getAccountId()+"", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * View a specific account's Details
	 * @param ClientBean client
	 * @throws MBankException
	 */
	@Override
	public AccountsBean viewAccountDetails(ClientBean client) throws MBankException {
		AccountsBean tempAccount = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Accounts WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tempAccount = new AccountsBean(rs.getInt("account_id"), rs.getInt("client_id"), rs.getDouble("balance"), rs.getDouble("credit_limit"),
						rs.getString("comment"));
			}
		} 
		catch (Exception e) {
			throw new MBankException("Failed retrieving Client:"+client.getId()+""+" account details", e);
		}
		finally {
			clearConnection();
		}
		return tempAccount;
	}
	
	/**
	 * View all accounts details in database
	 * @throws MBankException
	 */
	@Override
	public List<AccountsBean> viewAllAccountsDetails() throws MBankException {
		List<AccountsBean> accountsList = new ArrayList<AccountsBean>();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Accounts", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accountsList.add(new AccountsBean(rs.getInt("account_id"), rs.getInt("client_id"), rs.getDouble("balance"), 
						Double.parseDouble(rs.getString("credit_limit")), rs.getString("comment")));
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed viewing all account details", e);
		}
		finally {
			clearConnection();
		}
		return accountsList;
	}
	
	/**
	 * Withdraw from a specific account
	 * @param ClientBean client
	 * @param double amount
	 * @throws MBankException
	 */
	@Override
	public void withdrawFromAccount(ClientBean client, double amount) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT account_id, balance FROM Accounts WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				double currentAccountBalance = rs.getDouble("balance");
				PreparedStatement ps2 = conn.prepareStatement("UPDATE Accounts SET balance = ? WHERE account_id=?");
				/*
				 * calculate commission for the client
				 */
				double commRate = Double.parseDouble(pdbm.viewSystemProperty("commission_rate").getPropValue());
				double newBalance = currentAccountBalance - (amount + (amount * commRate));
				ps2.setDouble(1, newBalance);
				ps2.setInt(2, rs.getInt("account_id"));
				ps2.execute();
				Calendar cal = Calendar.getInstance();
				Date currentTime = new java.sql.Date(cal.getTimeInMillis());
				ActivityBean activity = new ActivityBean(client.getId(), amount, currentTime, commRate, "withdrawal From Account");
				adbm.addNewActivity(activity);
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed in withdraw attempt of client", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * Deposit to a specific account
	 * @param ClientBean client
	 * @param double amount
	 * @throws MBankException
	 */
	@Override
	public void depositToAccount(ClientBean client, double amount) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Accounts WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*
				 * calculate commission for the client
				 */
				double commRate = Double.parseDouble(pdbm.viewSystemProperty("commission_rate").getPropValue());
				PreparedStatement ps2 = conn.prepareStatement("UPDATE Accounts SET balance=? WHERE client_id=?");
				double newBalance = rs.getDouble("balance") + ((amount) - (amount * commRate))  ;
				ps2.setDouble(1, newBalance);
				ps2.setInt(2, client.getId());
				ps2.execute();
				Calendar cal = Calendar.getInstance();
				Date currentTime = new java.sql.Date(cal.getTimeInMillis());
				ActivityBean activity = new ActivityBean(client.getId(), amount, currentTime, commRate, "Deposit To Account");
				adbm.addNewActivity(activity);
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed deposit to the account of client:"+client.getId()+"", e);
		}
		finally {
			clearConnection();
		}
	}

	/**
	 * Determines if account exists for client
	 * @param int clintId
	 * @return True if there is already an account open for Client 
	 * @throws MBankException
	 */
	public boolean isAccountExistForClient(int clintId) throws MBankException {
		boolean flag = false;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT account_id FROM Accounts where client_id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, clintId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				flag = rs.getInt("account_id") > 0 ? true:false;
			}
		}
		catch (Exception e) {
			throw new MBankException("Error trying finding clients account", e);
		}
		finally {
			clearConnection();
		}
		return flag;
	}
	
	/**
	 * Retrieves account object by client id
	 * @param int clientId
	 * @return AccountsBean
	 * @throws MBankException
	 */
	public AccountsBean getAccountObjectByClientId(int clientId) throws MBankException {
		AccountsBean tempAccount = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Accounts WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, clientId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tempAccount = new AccountsBean(rs.getInt("account_id"), rs.getInt("client_id"), rs.getDouble("balance"), rs.getDouble("credit_limit"),
						rs.getString("comment"));
			}
		}
		catch (Exception e) {
			throw new MBankException("No account founded", e);
		}
		finally {
			clearConnection();
		}
		return tempAccount;
	}
	
	/**
	 * Retrieves balance from account
	 * @param int account id
	 * @return double
	 * @throws MBankException
	 */
	public double getBalanceFromAccount(int account_id) throws MBankException {
		double balance = 0;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT balance FROM Accounts WHERE account_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, account_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				balance = rs.getDouble("balance");
			}
		}
		catch (Exception e) {
			throw new MBankException("problem retrieving balance", e);
		}
		finally {
			clearConnection();
		}
		return balance;
	}

	/**
	 * Updates account's balance
	 * @param int clientId
	 * @param double balanceToAdd
	 * @throws MBankException
	 */
	public void updateAccountBalance(int clientId, double balanceToAdd) throws MBankException {
		double newBalance = 0;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT balance FROM Accounts WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, clientId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				newBalance = rs.getDouble("balance") + balanceToAdd;
			}
			PreparedStatement ps2 = conn.prepareStatement("UPDATE Accounts SET balance=? WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps2.setDouble(1, newBalance);
			ps2.setInt(2, clientId);
			ps2.execute();
		}
		catch (Exception e) {
			throw new MBankException("account's balance update failed", e);
		}
		finally {
			clearConnection();
		}
	}

}
