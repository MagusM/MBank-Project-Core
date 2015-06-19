package interfaces;

import java.util.List;

import mBankExceptions.MBankException;
import beans.AccountsBean;
import beans.ClientBean;

/**
 * This interface defines the methods for Accounts DB Manager
 * @author Simon Mor
 *
 */
public interface AccountsManager extends InterfaceManager {
	void createNewAccount(ClientBean client, AccountsBean account) throws MBankException;
	public void removeAccount(AccountsBean account) throws MBankException;
	AccountsBean viewAccountDetails(ClientBean client) throws MBankException;
	List<AccountsBean> viewAllAccountsDetails() throws MBankException;
	void withdrawFromAccount(ClientBean client, double amount) throws MBankException;
	void depositToAccount(ClientBean client, double amount) throws MBankException;
}
