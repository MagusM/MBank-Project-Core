package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mBankExceptions.MBankException;
import managers.PropertiesDBManager;
import beans.AccountsBean;
import beans.ActivityBean;
import beans.ClientBean;
import beans.DepositBean;
import beans.PropertiesBean;
import enums.AccountType;
import enums.DepositType;


public class ClientActions implements Action {

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

	@Override
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
		List<DepositBean> depositsList  = new ArrayList<DepositBean>();
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

	@Override
	public ClientBean viewClientDetails(int clientId) {
		ClientBean tempClient = null;
		tempClient = getClientObjectById(clientId);
		return tempClient;
	}

	//clients methods

	public boolean withdrawFromAccount(ClientBean client, double amount) {
		boolean flag = false;
		try {
			if (amount > 0) {
				AccountsBean tempAccount = acdbm.getAccountObjectByClientId(client.getId());
				double currentBalance = acdbm.getBalanceFromAccount(tempAccount.getAccountId());
				acdbm.withdrawFromAccount(client, amount); 
				flag = acdbm.getBalanceFromAccount(acdbm.getAccountObjectByClientId(client.getId()).getAccountId()) < currentBalance ? true:false;
			}
		}
		catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return flag;
	}

	public void depositToAccount(ClientBean client, double amount) throws MBankException {
		try {
			acdbm.depositToAccount(client, amount);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public int calculateNewEstimatedBalance(String clientType, long daysToCalculate, double balance) throws MBankException {
		int estimatedBalance = 0;
		String systemProp = PropertiesDBManager.stringAddDailyInterest(clientType.toString());
		double dailyInterest = Double.parseDouble(pdbm.viewSystemProperty(systemProp).getPropValue());
		estimatedBalance = (int) (dailyInterest * daysToCalculate * balance);
		return estimatedBalance;
	}

	public DepositBean createNewDeposit(ClientBean client, DepositBean deposit, boolean typeIndicator) {
		Long DepositTimeSpan = deposit.getClosingDate().getTime() - deposit.getOpeningDate().getTime();
		long daysBetweenopenTillCloseDeposit = 	TimeUnit.MILLISECONDS.toDays(DepositTimeSpan);
		DepositType type = typeIndicator ? DepositType.SHORT:DepositType.LONG;
		DepositBean newDeposit = null;
		try {
			int estimatedBalance = calculateNewEstimatedBalance(client.getType(), daysBetweenopenTillCloseDeposit, deposit.getBalance());
			newDeposit = new DepositBean(deposit.getClientId(), deposit.getBalance(), type, 
					estimatedBalance, deposit.getOpeningDate(), deposit.getClosingDate());
			ddbm.createNewDeposit(client, newDeposit);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		return newDeposit;
	}
	
	public void preOpenDeposit(ClientBean client, DepositBean deposit) {
		try {
			ddbm.preOpenDeposit(client, deposit);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
	}

	public DepositBean getDepositById(int depositId) {
		 DepositBean tempDeposit = null;
		 try {
			tempDeposit = ddbm.getDepositById(depositId);
		} catch (MBankException e) {
			System.err.println(e.getMessage());
		}
		 return tempDeposit;
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
	
	
	
}
