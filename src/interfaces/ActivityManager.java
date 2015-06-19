package interfaces;

import java.util.List;

import mBankExceptions.MBankException;
import beans.ActivityBean;
import beans.ClientBean;

/**
 * This interface defines the methods for Actvity DB Manager
 * @author Simon Mor
 *
 */
public interface ActivityManager extends InterfaceManager {
	List<ActivityBean> viewClientActivities(ClientBean client) throws MBankException;
	List<ActivityBean> viewAllActivities() throws MBankException;
}
