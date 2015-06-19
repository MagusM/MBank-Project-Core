package interfaces;

import java.util.List;

import mBankExceptions.MBankException;
import beans.ClientBean;


/**
 * This interface defines the methods for Client DB Manager
 * @author Simon Mor
 *
 */
public interface ClientManager extends InterfaceManager {

	void addNewClient(ClientBean client) throws MBankException;
	void removeClient(ClientBean client) throws MBankException;
	boolean updateClientDetails(ClientBean client) throws MBankException;
	ClientBean viewClientDetails(ClientBean client) throws MBankException;
	List<ClientBean> viewAllClientDetails() throws MBankException;
}
