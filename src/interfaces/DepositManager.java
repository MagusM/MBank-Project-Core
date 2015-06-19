package interfaces;

import java.util.List;

import mBankExceptions.MBankException;
import beans.ClientBean;
import beans.DepositBean;

/**
 * This interface defines the methods for Deposit DB Manager
 * @author Simon Mor
 *
 */
public interface DepositManager extends InterfaceManager {
	//boolean closeDeposit(int clientID, int depositId, Connection conn) throws MBankException;
	List<DepositBean> viewClientDeposits(ClientBean client) throws MBankException;
	void createNewDeposit(ClientBean client, DepositBean deposit) throws MBankException;
	void preOpenDeposit(ClientBean client, DepositBean deposit) throws MBankException;
	List<DepositBean> viewAllDeposits() throws MBankException;
}
