package interfaces;

import mBankExceptions.MBankException;
import beans.PropertiesBean;

/**
 * This interface defines the methods for Deposit DB Manager
 * @author Simon Mor
 *
 */
public interface PropertiesManager extends InterfaceManager {
	void updateSystemProperty(PropertiesBean pb) throws MBankException;
	PropertiesBean viewSystemProperty(String propName) throws MBankException;
}
