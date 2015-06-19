package parsers;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import wrapClasses.AccountsBeanWrapper;
import wrapClasses.DepositBeanWrapper;
import actions.AdminActions;
import beans.AccountsBean;
import beans.ActivityBean;
import beans.ClientBean;
import beans.DepositBean;

public class DataExchanger {
	/*
	 * very important ! cannot parse Enums with JAXB. a solution is required.
	 */
	private Clients clients = new Clients();
	private Accounts accounts = new Accounts();
	private Deposits deposits = new Deposits();
	private Activities activities = new Activities();
	@SuppressWarnings("unused")
	private Properties properties = new Properties();

	private JAXBContext context;

	private StringWriter sw = new StringWriter();
	private AdminActions ac = new AdminActions();

	/*
	 * marshalling methods
	 */
	/**
	 * Marshalling ClientBean to XML method.<p>
	 * if client equals null : returns all clients XML. else return client XML 
	 * @param clientList
	 * @return ClientBean XML Description
	 */
	public String marshallClients(List<ClientBean> clientList) {
		try {
			if (clientList == null) {
				clients.setClients(ac.viewAllClientsDetails());
			}
			else {
				clients.setClients(clientList);
			}
			context = JAXBContext.newInstance(Clients.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(clients, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
		return sw.toString();
	}
	
	/**
	 * Marshalling AccountsBean to XML method.<p>
	 * if account equals null : returns all accounts XML. else return account XML 
	 * @param accountList
	 * @return AccountsBean XML Description
	 */
	public String marshallAccounts(List<AccountsBean> accountList) {
		try {
			if (accountList == null) {
				accounts.setAccounts(ac.viewAllAccountsDetails());
			} else {
				accounts.setAccounts(accountList);
			}
			context = JAXBContext.newInstance(Accounts.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(accounts, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
		return sw.toString();
	}
	
	/**
	 * Marshalling DepositBean to XML method.<p>
	 * if deposit equals null : returns all deposits XML. else return deposit XML 
	 * @param depositList
	 * @return DepositBean XML Description
	 */
	public String marshallDeposits(List<DepositBean> depositList) {
		try {
			if (depositList == null) {
				deposits.setDeposits(ac.viewAllDeposits());
			} else {
				deposits.setDeposits(depositList);
			} 
			context = JAXBContext.newInstance(Deposits.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(deposits, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
		return sw.toString();
	}
	
	/**
	 * Marshalling ActivityBean to XML method.<p>
	 * if activity equals null : returns all activities XML. else return activity XML 
	 * @param activityList
	 * @return ActivityBean XML Description
	 */
	public String marshallActivities(List<ActivityBean> activityList) {
		try{
			if (activityList == null) {
				activities.setActivities(ac.viewAllActivities());
			}
			else {
				activities.setActivities(activityList);
			}
			context = JAXBContext.newInstance(Activities.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(activities, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
		return sw.toString();
	}

	/*
	 * unmarshalling methods
	 * change output method when needed
	 */
	public ClientBean unmarshallClients(File file) {
		ClientBean client = null;
		try {
			context = JAXBContext.newInstance(Clients.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			clients = (Clients) unmarshaller.unmarshal(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
		return client;
	}

	public void unmarshallAccounts(File file) {
		try {
			context = JAXBContext.newInstance(Accounts.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			accounts = (Accounts) unmarshaller.unmarshal(file);
			List<AccountsBeanWrapper> data = accounts.getAccounts();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
	}

	public void unmarshallDeposits(File file) {
		try {
			context = JAXBContext.newInstance(Deposits.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			deposits =  (Deposits) unmarshaller.unmarshal(file);
			List<DepositBeanWrapper> data = deposits.getDeposits();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
	}

	public void unmarshallActivities(File file) {
		try {
			context = JAXBContext.newInstance(Activities.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			activities = (Activities) unmarshaller.unmarshal(file);
			List<ActivityBean> data = activities.getActivities();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		finally {
			context = null;
		}
	}
	

}