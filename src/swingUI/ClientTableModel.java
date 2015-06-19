package swingUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.ClientBean;

/**
 * This class defines the client table model
 * @author Simon Mor
 */
public class ClientTableModel extends AbstractTableModel {
	private List<ClientBean> clients = new ArrayList<ClientBean>();
	private String[] columnNames = {"ID", "Name", "Password", "Type", "Address", "Email", "Phone", "Comment"};

	public ClientTableModel() {

	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		if (clients != null) {
			return clients.size();
		}
		return 0;
	}

	@Override 
	public int getColumnCount() { 
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ClientBean tempClient = clients.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return tempClient.getId();
		case 1:
			return tempClient.getName();
		case 2:
			return tempClient.getPassword();
		case 3:
			return tempClient.getType();
		case 4:
			return tempClient.getAddress();
		case 5:
			return tempClient.getEmail();
		case 6:
			return tempClient.getPhone();
		case 7:
			return tempClient.getComment();
		}
		return null;
	}

	public List<ClientBean> getClients() {
		return clients;
	}

	public void setClients(List<ClientBean> clients) {
		for (ClientBean client : clients) {
			this.clients.add(client);
		}
	}

	public void setClient(ClientBean client) {
		this.clients.add(client);
	}

	public void clearClients() {
		clients.clear();
	}

}
