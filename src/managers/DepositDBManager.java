package managers;

import interfaces.DepositManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mBankExceptions.MBankException;
import mbank.ConnectionPool;
import beans.ActivityBean;
import beans.ClientBean;
import beans.DepositBean;
import enums.DepositType;

/**
 * this class defines the deposits methods on the database
 * @author Simon Mor
 *
 */
public class DepositDBManager extends Manager implements DepositManager {
	private ConnectionPool connectionPool;
	private Connection conn = null;
	private PropertiesDBManager pdbm = new PropertiesDBManager();
	private AccountsDBManager adbm = new AccountsDBManager();
	private ActivityDBManager actdbm = new ActivityDBManager();
	
	/**
	 * Constructor
	 */
	public DepositDBManager() {
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
	 * Closes a specific deposit
	 * @param int depositID
	 * @throws MBankException
	 */
	public void closeDeposit(int depositID) throws MBankException{
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT client_id FROM Deposits WHERE deposit_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, depositID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				closeDeposit(rs.getInt("client_id"), depositID, conn);
			}
		}
		catch (Exception e) {
			throw new MBankException(e.getMessage(), e);
		}
		finally {
			clearConnection();
		}

	}
	
	private void closeDeposit(int clientID, int depositId, Connection conn) throws MBankException {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits WHERE deposit_id=?"
					, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, depositId);
			ResultSet rs = ps.executeQuery();
			Date depositClosingDate = null;
			double depositBalance = 0;
			/*
			 * check the date status of the deposit and act accordingly  
			 */
			while (rs.next()) {
				Date tempDate = rs.getDate("closing_date");
				if (tempDate != null) {
					depositClosingDate = tempDate;
					depositBalance = rs.getDouble("balance");
				}
			}
			Calendar cal = Calendar.getInstance();
			Date currentDate = new java.sql.Date(cal.getTimeInMillis());
			if (currentDate.after(depositClosingDate) || currentDate.equals(depositClosingDate)) {
				adbm.updateAccountBalance(clientID, depositBalance);
				removeDeposit(depositId, conn);
			}
			else {
				double commision = Double.parseDouble(pdbm.viewSystemProperty("pre_open_fee").getPropValue());
				double amountCalculated = depositBalance - (depositBalance * commision);
				adbm.updateAccountBalance(clientID, amountCalculated);
				rs.deleteRow();
				removeDeposit(depositId, conn);
			}
			
		}
		catch (Exception e) {
			throw new MBankException("Problem closing deposit #"+depositId+"", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * Views all client's Deposits
	 * @param ClientBean client
	 * @return List<DepositBean>
	 * @throws MBankException
	 */
	@Override
	public List<DepositBean> viewClientDeposits(ClientBean client) throws MBankException {
		List<DepositBean> depositsList = new ArrayList<DepositBean>();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				depositsList.add(new DepositBean(rs.getInt("deposit_id"), rs.getInt("client_id"), rs.getDouble("balance"),  
						DepositType.convertStringToDepositType(rs.getString("type")),rs.getInt("estimated_balance"), rs.getDate("opening_date"), 
						rs.getDate("closing_date")));
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed retrieving Client:"+client.getId()+""+" deposits", e);
		}
		finally {
			clearConnection();
		}
		return depositsList;
	}
	
	/**
	 * View all the deposits from database
	 * @return List<DepositBean>
	 * @throws MBankException
	 */
	@Override
	public List<DepositBean> viewAllDeposits() throws MBankException {
		List<DepositBean> depositsList = new ArrayList<DepositBean>();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				depositsList.add( new DepositBean(rs.getInt("deposit_id"), rs.getInt("client_id"), rs.getDouble("balance"),  
						DepositType.convertStringToDepositType(rs.getString("type")),rs.getInt("estimated_balance"), rs.getDate("opening_date"), 
						rs.getDate("closing_date")));
			}
		}
		catch (Exception e) {
			throw new MBankException("Error viewing all deposits", e);
		}
		finally {
			clearConnection();
		}
		return depositsList;
	}
	
	/**
	 * Creates new deposit in database
	 * @param ClientBean client
	 * @param DepositBean deposit
	 * @throws MBankException
	 */
	@Override
	public void createNewDeposit(ClientBean client, DepositBean deposit) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("INSERT INTO Deposits (client_id, balance, type, estimated_balance, opening_date, closing_date) "
					+ "VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ps.setDouble(2, deposit.getBalance());
			ps.setString(3, deposit.getDepositType());
			ps.setInt(4, deposit.getEstimatedBalance());
			ps.setDate(5, deposit.getOpeningDate());
			ps.setDate(6, deposit.getClosingDate());
			ps.execute();
		} 
		catch (Exception e) {
			throw new MBankException("Failed adding new deposit to client:"+client.getId()+"", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * Pre opens a specific deposit
	 * @param ClientBean client
	 * @param DepositBean deposit
	 * @throws MBankException
	 */
	@Override
	public void preOpenDeposit(ClientBean client, DepositBean deposit) throws MBankException {
		try {
			Connection conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits WHERE deposit_id=?"
					, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, deposit.getDepositId());
			ResultSet rs = ps.executeQuery();
			double amountFromDeposit = 0;
			while (rs.next()) {
				/*
				 * calculates the commission
				 * and updates the activity
				 */
				double commission = Double.parseDouble(pdbm.viewSystemProperty("pre_open_fee").getPropValue());
				amountFromDeposit = rs.getDouble("balance");
				double amountAfterCommission = amountFromDeposit - (amountFromDeposit * commission );
				adbm.updateAccountBalance(client.getId(), amountAfterCommission);
				removeDeposit(deposit.getDepositId(), conn);
				Calendar cal = Calendar.getInstance();
				Date currentTime = new java.sql.Date(cal.getTimeInMillis());
				ActivityBean activity = new ActivityBean(client.getId(), amountAfterCommission, currentTime, commission, "Pre Open Deposit");
				actdbm.addNewActivity(activity);
			}
		}
		catch (Exception e) {
			throw new MBankException("Falied pre open deposit:"+deposit.getDepositId()+"", e);
		}
		finally {
			clearConnection();
		}
	}

	private void removeDeposit(int depositId, Connection conn) throws MBankException, Exception {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM Deposits WHERE deposit_id=?", 
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setInt(1, depositId);
		ps.execute();
		
	}
	
	/**
	 * Retrieves a deposit by id
	 * @param int depositId
	 * @return DepositBean
	 * @throws MBankException
	 */
	public DepositBean getDepositById(int depositId) throws MBankException {
		DepositBean tempDeposit = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits WHERE deposit_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, depositId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tempDeposit = new DepositBean(rs.getInt("deposit_id"), rs.getInt("client_id"), rs.getDouble("balance"),  
						DepositType.convertStringToDepositType(rs.getString("type")),rs.getInt("estimated_balance"), rs.getDate("opening_date"), 
						rs.getDate("closing_date"));
			}
		}
		catch (Exception e) {
			throw new MBankException("problem retrieving Deposit object", e);
		}
		finally {
			clearConnection();
		}
		return tempDeposit;
	}

	/**
	 * Removes all of client's deposits
	 * @param int clindId
	 * @param Connection conn
	 * @throws MBankException
	 */
	public void removeAllClientsDeposits(int clindId, Connection conn) throws MBankException {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Deposits WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, clindId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rs.deleteRow();
			}
		}
		catch (Exception e) {
			throw new MBankException("Problem deleting all of client #"+clindId+""+" deposits", e);
		}
	}
	
}
