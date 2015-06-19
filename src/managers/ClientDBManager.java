package managers;

import interfaces.ClientManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mBankExceptions.MBankException;
import mbank.ConnectionPool;
import beans.ClientBean;
import enums.AccountType;

/**
 * this class defines the clients methods on the database
 * @author Simon Mor
 *
 */
public class ClientDBManager implements ClientManager {
	private ConnectionPool connectionPool; 
	private Connection conn = null;
	
	/**
	 * Constructor
	 */
	public ClientDBManager() { 
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
	 * Adds A new client to database
	 * @param ClientBean client
	 * @throws MBankException
	 */
	@Override
	public void addNewClient(ClientBean client) throws MBankException {
		try{
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement
					("INSERT INTO Clients (client_name, password, type, address, email, phone, comment) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, client.getName());
			ps.setString(2, client.getPassword());
			ps.setString(3, client.getType());
			ps.setString(4, client.getAddress());
			ps.setString(5, client.getEmail());
			ps.setString(6, client.getPhone());
			ps.setString(7, client.getComment());
			ps.execute();
		}
		catch (Exception e) {
			throw new MBankException("Failed creating new Client", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * Update client's details
	 * @param ClientBean client
	 * @return true if updating a client's detail was successful
	 * @throws MBankException 
	 */
	@Override
	public boolean updateClientDetails(ClientBean client) throws MBankException  {
		boolean flag = false;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE Clients SET client_name=?, password=?, type=?, address=?, email=?, phone=?, comment=? WHERE client_id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, client.getName());
			ps.setString(2, client.getPassword());
			ps.setString(3, (client.getType()));
			ps.setString(4, client.getAddress());
			ps.setString(5, client.getEmail());
			ps.setString(6, client.getPhone());
			ps.setString(7, client.getComment());
			ps.setInt(8, client.getId());
			flag = ps.execute();
		}
		catch (Exception e) {
			throw new MBankException("problem updating client details", e);
		}
		finally {
			clearConnection();
		}
		return flag;
	}
	
	/**
	 * Removes a client from database
	 * @param ClientBean client
	 * @throws MBankException
	 */
	@Override
	public void removeClient(ClientBean client) throws MBankException {
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clients WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rs.deleteRow();
			}
			AccountsDBManager adbm = new AccountsDBManager();
			DepositDBManager ddbm = new DepositDBManager();
			adbm.removeAccount(adbm.getAccountObjectByClientId(client.getId()));
			ddbm.removeAllClientsDeposits(client.getId(), conn);
		}
		catch (Exception e) {
			throw new MBankException("Failed removing client:"+client.getId()+"", e);
		}
		finally {
			clearConnection();
		}
	}
	
	/**
	 * View client's details
	 * @param ClientBean client
	 * @return ClientBean
	 * @throws MBankException
	 */
	@Override
	public ClientBean viewClientDetails(ClientBean client) throws MBankException {
		ClientBean tempClient = null;
		try  {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clients WHERE client_id=?", 
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, client.getId());
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				tempClient = new ClientBean(rs.getInt("client_id"), rs.getString("client_name"), rs.getString("password"), AccountType.getTypeFromString(rs.getString("type")),
						rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("comment"));
			}
		}
		catch (Exception e) {
			throw new MBankException("Failed retrieving Client:"+client.getId()+""+" details", e);
		}
		finally {
			clearConnection();
		}
		return tempClient;
	}
	
	/**
	 * View all client table details
	 * @return List<ClientBean>
	 * @throws MBankException
	 */
	@Override
	public List<ClientBean> viewAllClientDetails() throws MBankException {
		List<ClientBean> clientsList = new ArrayList<ClientBean>();
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clients", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clientsList.add(new ClientBean(rs.getInt("client_id"), rs.getString("client_name"), rs.getString("password"), AccountType.getTypeFromString(rs.getString("type")),
						rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("comment")));
			}
		}
		catch (Exception e) {
			throw new MBankException("Problem retrieving all Clients details", e);
		}
		finally {
			clearConnection();
		}
		return clientsList;
	}

	/**
	 * Determines if a specific client already exists in database
	 * @param ClientBean client
	 * @return true if client already exist in database
	 * @throws MBankException
	 */
	public boolean isClientAlreadyExist(ClientBean client) throws MBankException {
		boolean flag = false;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT password FROM Clients WHERE client_name=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, client.getName());
			ResultSet rs = ps.executeQuery();
			String password;
			while (rs.next()) {
				password = rs.getString("password");
				if (password.equals(client.getPassword())) {
					flag = true;
					break;
				}
			}
		}
		catch (Exception e) {
			throw new MBankException("Error finding similiar client", e);
		}
		finally {
			clearConnection();
		}

		return flag;
	}
	
	/**
	 * Retrieves client object by id
	 * @param int id
	 * @return ClientBean
	 * @throws MBankException
	 */
	public ClientBean getClientObjectById(int id) throws MBankException {
		ClientBean tempClient = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clients WHERE client_id=?"
					, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tempClient = new ClientBean(id, rs.getString("client_name"), rs.getString("password"), AccountType.getTypeFromString(rs.getString("type")),
						rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("comment"));
			}

		}
		catch (Exception e) {
			throw new MBankException("Problem retrieving Client NO."+id, e);
		}
		finally {
			clearConnection();
		}
		return tempClient;
	}
	
	/**
	 * Retrieves client id
	 * @param ClientBean client
	 * @return int
	 * @throws MBankException
	 */
	public int getClientId(ClientBean client) throws MBankException {
		int id = 0;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT * FROM Clients WHERE password=?"
					, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, client.getPassword());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				boolean nameCheck = client.getName().equals(rs.getString("client_name"));
				boolean typeCheck = client.getType().equals(rs.getString("type"));
				boolean addressCheck = client.getAddress().equals(rs.getString("address"));
				boolean emailCheck = client.getEmail().equals(rs.getString("email"));
				boolean phoneCheck = client.getPhone().equals(rs.getString("phone"));
				boolean commentCheck = client.getComment().equalsIgnoreCase(rs.getString("comment"));
				boolean allCheck = nameCheck && typeCheck && addressCheck && emailCheck && phoneCheck && commentCheck;
				if (allCheck) {
					id = rs.getInt("client_id");
				}
			}
		}
		catch (Exception e) {
			throw new MBankException("problem retrieving client id", e);
		}
		finally {
			clearConnection();
		}
		return id;
	}


}
