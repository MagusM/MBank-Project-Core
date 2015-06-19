package mbank;

import java.sql.Date;
import java.util.Calendar;

import javax.swing.SwingUtilities;

import wrapClasses.ClientBeanWrapper;
import mBankExceptions.MBankException;
import managers.ClientDBManager;
import actions.Action;
import actions.AdminActions;
import actions.ClientActions;
import beans.AccountsBean;
import beans.ClientBean;
import beans.DepositBean;
import enums.AccountType;

/**
 * This class defines MBank.
 * @author Simon Mor <p>
 * {@link www.unumoculus.com}
 * @since 30/03/2015
 * @version 1.0
 */
public class MBank {
	private static MBank instance = new MBank();
	public ConnectionPool connectionPool;

	private MBank() {
		super();
		try {
			connectionPool = ConnectionPool.getConnectionPool();
		} catch (MBankException e) {
			e.printStackTrace();
		}

		//deposit check thread
		Thread checkDepositTask = new Thread(new DepositCheckTask());
		checkDepositTask.start();
	}

	/**
	 * Login validation
	 * @param id
	 * @param email
	 * @param password
	 * @return ClientAction
	 */
	public Action login(ClientBean tempClient) {
		int clientId = tempClient.getId();

		ClientActions ca = new ClientActions();
		ClientBean clientBean = ca.getClientObjectById(clientId);
		
		if (clientBean == null) {
			return null;
		}
		else {
			if (tempClient.getPassword().equals(clientBean.getPassword()) && tempClient.getEmail().equals(clientBean.getEmail())) {
				return ca;
			}
		}
		return null;



	}

	/**
	 * @return instance of MBank
	 */
	public static MBank getMBank(){
		return instance;
	}

	static {
		System.out.println("MBank is now ONLINE!\n");
	}

	/*
	 * Method to start MBank!
	 */
	public void start() {
		ConnectionPool connectionPool = null;
		try {
			connectionPool = ConnectionPool.getConnectionPool();
		} catch (MBankException e1) {
			System.err.println(e1.getMessage());
		}
		
		/*
		 * load the MBank app (swing)
		 */
		SwingUtilities.invokeLater(new Runnable() {
			{
				System.out.println("MBank App is now running");
			}
			@Override
			public void run() {
				new swingUI.MainFrame();
			}
		});

	}

	public static void populateData() {
		try {
			AdminActions ac = new AdminActions();
			ClientDBManager cdbm = new ClientDBManager();
			ClientActions ca = new ClientActions();
			//instantiating clients
			ClientBean[] clientsArray = {
			new ClientBean("simon", "mor", AccountType.PLATINUM, "Ashdod", "simon@mail.com", "0501234567", null),
			new ClientBean("obamba", "shmok", AccountType.REGULAR, "usa", "gay@mail.com", "0502345678", null),
			new ClientBean("bibinyahu", "sara", AccountType.GOLD, "lalaland", "bb@mail.com", "0503456789", null),
			new ClientBean("roni", "keren", AccountType.PLATINUM, "java", "boss@mail.com", "0504567890", null),
			new ClientBean("bob", "marley", AccountType.PLATINUM, "heaven", "ganja@mail.com", "0504765742", null),
			new ClientBean("ned", "nohead", AccountType.REGULAR, "pit", "stark@mail.com", "0507462984", null),
			new ClientBean("ragnar", "lothbrok", AccountType.REGULAR, "valhala", "viking@mail.com", "0508367690", null),
			new ClientBean("darth", "vader", AccountType.PLATINUM, "deathstar", "force@mail.com", "0507377893", null)
			};

			//instantiating accounts
			AccountsBean[] accountsArray = new AccountsBean[8];
			for (int i = 0; i < clientsArray.length; i++) {
				clientsArray[i].setId(cdbm.getClientId(clientsArray[i]));
				accountsArray[i] = new AccountsBean(clientsArray[i].getId(), ((int) Math.random() * 432), null);
				ac.addNewClient(clientsArray[i]);
				ac.createNewAccount(clientsArray[i], accountsArray[i]);
			}
			
			Calendar cal = Calendar.getInstance();
			for (int i = 0; i < clientsArray.length; i++) {
				Calendar newCal = Calendar.getInstance();
				Date openingDate = new java.sql.Date(cal.getTimeInMillis()); 
				int x = (int) Math.random() * 10;
				newCal.add(Calendar.YEAR, x);
				Date closingDate = new java.sql.Date(cal.getTimeInMillis());
				DepositBean dep = new DepositBean(clientsArray[i].getId(), ((int) Math.random() * 432), openingDate, closingDate);
				ca.createNewDeposit(clientsArray[i], dep, ((x > 1) ? true:false));
			}
			
			
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
	}


}


