package actions;

import java.util.List;

import mBankExceptions.MBankException;
import managers.AccountsDBManager;
import managers.ActivityDBManager;
import managers.ClientDBManager;
import managers.DepositDBManager;
import managers.PropertiesDBManager;
import beans.AccountsBean;
import beans.ActivityBean;
import beans.ClientBean;
import beans.DepositBean;
import beans.PropertiesBean;

/**
 * Interface for all action classes.
 * @author Simon Mor
 */
public interface Action {
	final ClientDBManager cdbm = new ClientDBManager();
	final DepositDBManager ddbm = new DepositDBManager();
	final ActivityDBManager adbm = new ActivityDBManager();
	final PropertiesDBManager pdbm = new PropertiesDBManager();
	final AccountsDBManager acdbm = new AccountsDBManager();
	
	void updateClientDetails(ClientBean client) throws MBankException;
	ClientBean viewClientDetails(int clientId) throws MBankException;
	AccountsBean viewAccountDetails(ClientBean client) throws MBankException;
	List<DepositBean> viewClientDeposits(ClientBean client) throws MBankException;
	List<ActivityBean> viewClientActivities(ClientBean client) throws MBankException;
	PropertiesBean viewSystemProperty(String propName) throws MBankException;

}
