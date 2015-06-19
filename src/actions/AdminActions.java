package actions;

import java.util.ArrayList;
import java.util.List;

import mBankExceptions.MBankException;
import beans.AccountsBean;
import beans.ActivityBean;
import beans.ClientBean;
import beans.DepositBean;
import beans.PropertiesBean;
import enums.AccountType;


public class AdminActions implements Action {

	@Override
	public ClientBean viewClientDetails(int clientId) {
		ClientBean tempClient = null;
		tempClient = getClientObjectById(clientId);
		return tempClient;
	}

	@Override
	public AccountsBean viewAccountDetails(ClientBean client) {
		AccountsBean tempAccount = null;
		try {
			tempAccount = acdbm.viewAccountDetails(client);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return tempAccount;
	}

	@Override
	public List<DepositBean> viewClientDeposits(ClientBean client) {
		List<DepositBean> depositsList = new ArrayList<DepositBean>();
		try {
			depositsList = ddbm.viewClientDeposits(client);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return depositsList;
	}

	@Override
	public List<ActivityBean> viewClientActivities(ClientBean client) {
		List<ActivityBean> tempActivities = new ArrayList<ActivityBean>();
		try {
			tempActivities = adbm.viewClientActivities(client);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return tempActivities;
	}

	public void updateSystemProperty(PropertiesBean pb) {
		try {
			pdbm.updateSystemProperty(pb);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public PropertiesBean viewSystemProperty(String propName) {
		PropertiesBean tempProperty = null;
		try {
			tempProperty = pdbm.viewSystemProperty(propName);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return tempProperty;
	}

	//admin

	public ClientBean getClientObjectById(int id) {
		ClientBean tempClient = null;
		try {
			tempClient = cdbm.getClientObjectById(id);
		} catch (MBankException e) {
			System.err.println("Invalid client id");
			System.err.println(e.getMessage());
		}
		return tempClient;
	}

	public List<ClientBean> viewAllClientsDetails() {
		List<ClientBean> clientsList = new ArrayList<ClientBean>();
		try {
			clientsList = cdbm.viewAllClientDetails();
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return clientsList;
	}

	public boolean addNewClient(ClientBean client) {
		boolean flag = true;
		try {
			flag = cdbm.isClientAlreadyExist(client);
			if (!flag) {
				cdbm.addNewClient(client);
			}
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return flag;
	}

	public void updateClientDetails(ClientBean client) {  
		try {
			ClientBean returnToUpdateClient = cdbm.getClientObjectById(client.getId());
			returnToUpdateClient.setType(AccountType.getTypeFromString(client.getType())); 
			for (int i = 1; i < 8; i++) {
				switch (i) {
				case 1: if (client.getName() != null) {returnToUpdateClient.setName(client.getName());} break;
				case 2: if (client.getPassword() != null) {returnToUpdateClient.setPassword(client.getPassword());} break;
				case 4: if (client.getAddress() != null) {returnToUpdateClient.setAddress(client.getAddress());} break;
				case 5: if (client.getEmail() != null) {returnToUpdateClient.setEmail(client.getEmail());} break;
				case 6: if (client.getPhone() != null) {returnToUpdateClient.setPhone(client.getPhone());} break;
				case 7: if (client.getComment() != null) {returnToUpdateClient.setComment(client.getComment());} break;
				}
			}
			cdbm.updateClientDetails(returnToUpdateClient);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void removeClient(ClientBean client) {
		try {
			cdbm.removeClient(client);
			AccountsBean tempAccount = acdbm.getAccountObjectByClientId(client.getId());
			if (tempAccount != null) {
				acdbm.removeAccount(tempAccount);
			}
		}
		catch (MBankException e) {
			System.err.println("Error removing client id:"+client.getId()+"\n"+e.getMessage());
		}
	}

	public void createNewAccount(ClientBean client, AccountsBean account) {
		try {
			acdbm.isAccountExistForClient(client.getId());
			acdbm.createNewAccount(client, account);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
	}

	public void removeAccount(AccountsBean account) {
		try {
			acdbm.removeAccount(account);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
	}

	public List<AccountsBean> viewAllAccountsDetails() {
		List<AccountsBean> accountsList = new ArrayList<AccountsBean>();
		try {
			accountsList = acdbm.viewAllAccountsDetails();
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return accountsList;
	}

	public List<DepositBean> viewAllDeposits() {
		List<DepositBean> depositsList = new ArrayList<DepositBean>();
		try {
			depositsList = ddbm.viewAllDeposits();
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return depositsList;
	}

	public List<ActivityBean> viewAllActivities() {
		List<ActivityBean> activitiesList = new ArrayList<ActivityBean>();
		try {
			activitiesList = adbm.viewAllActivities();
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return activitiesList;
	}

	public AccountsBean getAccountObjectByClientId(int clientId)  {
		AccountsBean tempAccount = null;
		try {
			tempAccount = acdbm.getAccountObjectByClientId(clientId);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return tempAccount;
	}

	/**
	 * @param 
	 * @return int - id
	 * 
	 */
	public int getClientId(ClientBean client) {
		int id = 0;
		try {
			id = cdbm.getClientId(client);
		}
		catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return id;
	}

}
