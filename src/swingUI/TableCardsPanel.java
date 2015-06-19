package swingUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * This class defines the table's card panel
 * @author Simon Mor
 */
public class TableCardsPanel extends JPanel{
	private static ClientsTable clientsTable;
	private static AccountsTable accountsTable;
	private static DepositsTable depositsTable;
	private static ActivitiesTable activitiesTable;
	private static PropertiesTable propertiesTable;
	
	public final static String CLIENTS_TABLE = "clientsTable";
	public final static String ACCOUNTS_TABLE = "accountsTable";
	public final static String DEPOSITS_TABLE = "depositsTable";
	public final static String ACTIVITIES_TABLE = "activitiesTable";
	public final static String PROPERTIES_TABLE = "propertiesTable";
	
	protected static CardLayout tableCards;
	Map<Integer, JPanel> tablesMap;
	private static int indicator;
	private static JPanel thisLink;
	
	/**
	 * Constructor
	 */
	public TableCardsPanel() {
		thisLink = this;
		
//		Dimension dim = getPreferredSize();
//		setPreferredSize(dim);
		
		clientsTable = new ClientsTable();
		accountsTable = new AccountsTable();
		depositsTable = new DepositsTable();
		activitiesTable = new ActivitiesTable();
		propertiesTable = new PropertiesTable();
		
		tableCards = new CardLayout();
		setLayout(tableCards);
		
		add(clientsTable, CLIENTS_TABLE);
		add(accountsTable, ACCOUNTS_TABLE);
		add(depositsTable, DEPOSITS_TABLE);
		add(activitiesTable, ACTIVITIES_TABLE);
		add(propertiesTable, PROPERTIES_TABLE);
		tableCards.show(thisLink, CLIENTS_TABLE);

		tablesMap = new Hashtable<Integer, JPanel>();
		tablesMap.put(1, clientsTable); tablesMap.put(2, accountsTable); tablesMap.put(3, depositsTable); tablesMap.put(4, activitiesTable);
		tablesMap.put(5, propertiesTable);
	}
	
	protected void setTableModel(String modelName) {
		switch(modelName) {
		case "clientsTable":
			tableCards.show(thisLink, CLIENTS_TABLE);
			indicator = 1;
			break;
		case "accountsTable":
			tableCards.show(thisLink, ACCOUNTS_TABLE);
			indicator = 2;
			break;
		case "depositsTable":
			tableCards.show(thisLink, DEPOSITS_TABLE);
			indicator = 3;
			break;
		case "activitiesTable":
			tableCards.show(thisLink, ACTIVITIES_TABLE);
			indicator = 4;
			break;
		case "propertiesTable":
			tableCards.show(thisLink, PROPERTIES_TABLE);
			indicator = 5;
			break;
		}
	}
	
	public void refreshView(Integer i) {
		switch (i) {
		case 1:
			clientsTable.refreshView();
			break;
		case 2:
			accountsTable.refreshView();
			break;
		case 3:
			depositsTable.refreshView();
			break;
		case 4:
			activitiesTable.refreshView();
			break;
		case 5:
			propertiesTable.refreshView();
			break;
		}
	}
	
//	public JPanel getTabelPanel(int i) {
//		switch(i) {
//		case 1:
//			return tablesMap.get(i);
//		case 2:
//			return tablesMap.get(i);
//		case 3:
//			return tablesMap.get(i);
//		case 4:
//			return tablesMap.get(i);
//		case 5:
//			return tablesMap.get(i);
//		}
//		return null;
//	}

	public ClientsTable getClientsTable() {
		return clientsTable;
	}

	public AccountsTable getAccountsTable() {
		return accountsTable;
	}

	public DepositsTable getDepositsTable() {
		return depositsTable;
	}

	public ActivitiesTable getActivitiesTable() {
		return activitiesTable;
	}

	public PropertiesTable getPropertiesTable() {
		return propertiesTable;
	}

	public CardLayout getTableCards() {
		return tableCards;
	}

	public int getIndicator() {
		return indicator;
	}
	
}

//Tables
class ClientsTable extends JPanel {
	private JTable clientsTable;
	private ClientTableModel clientTableModel;
	public ClientsTable() {
		clientTableModel = new ClientTableModel();
		clientsTable = new JTable(clientTableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(clientsTable), BorderLayout.CENTER);
	}
	public ClientTableModel getTableModel() {
		return  (ClientTableModel) clientsTable.getModel();
	}
	public void refreshView() {
		clientTableModel.fireTableDataChanged();
	}
//	public void setClients(List<ClientBean> clients) {
//		clientsTable = new JTable(clientTableModel);
//		clientTableModel.setClients(clients);
//		add(new JScrollPane(clientsTable), BorderLayout.CENTER);
//	}
}

class AccountsTable extends JPanel {
	private JTable accountsTable;
	private AccountsTableModel accountsTableModel;
	public AccountsTable() {
		accountsTableModel = new AccountsTableModel();
		accountsTable = new JTable(accountsTableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(accountsTable), BorderLayout.CENTER);
	}
	public AccountsTableModel getTableModel() {
		return (AccountsTableModel) accountsTable.getModel();
	}
	public void refreshView() {
		accountsTableModel.fireTableDataChanged();
	}
}

class DepositsTable extends JPanel {
	private JTable depositsTable;
	private DepositsTableModel depositsTableModel;
	public DepositsTable() {
		depositsTableModel = new DepositsTableModel();
		depositsTable = new JTable(depositsTableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(depositsTable), BorderLayout.CENTER);
	}
	public DepositsTableModel getTableModel() {
		return (DepositsTableModel) depositsTable.getModel();
	}
	public void refreshView() {
		depositsTableModel.fireTableDataChanged();
	}
}

class ActivitiesTable extends JPanel {
	private JTable activitiesTable;
	private ActivityTableModel activitiesTableModel;
	public ActivitiesTable() {
		activitiesTableModel = new ActivityTableModel();
		activitiesTable = new JTable(activitiesTableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(activitiesTable), BorderLayout.CENTER);
	}
	public ActivityTableModel getTableModel() {
		return (ActivityTableModel) activitiesTable.getModel();
	}
	public void refreshView() {
		activitiesTableModel.fireTableDataChanged();
	}
}

class PropertiesTable extends JPanel {
	private JTable propertiesTable;
	private PropertiesTableModel propertiesTableModel;
	public PropertiesTable() {
		propertiesTableModel = new PropertiesTableModel();
		propertiesTable = new JTable(propertiesTableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(propertiesTable), BorderLayout.CENTER);
	}
	public PropertiesTableModel getTableModel() {
		return (PropertiesTableModel) propertiesTable.getModel();
	}
	public void refreshView() {
		propertiesTableModel.fireTableDataChanged();
	}
}