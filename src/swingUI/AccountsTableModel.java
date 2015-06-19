package swingUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.AccountsBean;

/**
 * This class defines the accounts table model
 * @author Simon Mor
 */
public class AccountsTableModel extends AbstractTableModel {
	private List<AccountsBean> accounts = new ArrayList<AccountsBean>();
	private String[] columnNames = {"Account ID", "Client ID", "Balance", "Credit Limit", "Comment"};

	public AccountsTableModel() {
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return accounts.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AccountsBean tempAccount = accounts.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return tempAccount.getAccountId();
		case 1:
			return tempAccount.getClientId();
		case 2:
			return tempAccount.getBalance();
		case 3:
			return tempAccount.getCreditLimit();
		case 4:
			return tempAccount.getComment();
		}
		return null;
	}

	public void setAccount(AccountsBean account) {
		accounts.add(account);
	}

	public List<AccountsBean> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountsBean> accounts) {
		for (AccountsBean accountsBean : accounts) {
			this.accounts.add(accountsBean);
		}
	}
	
	public void clearAccounts() {
		this.accounts.clear();
	}
	
	


}
