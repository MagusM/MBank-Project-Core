package swingUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.DepositBean;

/**
 * This class defines the deposit table model
 * @author Simon Mor
 */
public class DepositsTableModel extends AbstractTableModel {
	private List<DepositBean> deposits = new ArrayList<DepositBean>();
	private String[] columnNames = {"Deposit ID", "Client ID", "Balance" ,"Type", "Estimated Balance", "Openning Date", "Closing Date"};
	
	public DepositsTableModel() {
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return deposits.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DepositBean tempDeposit = deposits.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return tempDeposit.getDepositId();
		case 1:
			return tempDeposit.getClientId();
		case 2:
			return tempDeposit.getBalance();	
		case 3:
			return tempDeposit.getDepositType();
		case 4:
			return tempDeposit.getEstimatedBalance();
		case 5:
			return tempDeposit.getOpeningDate();
		case 6:
			return tempDeposit.getClosingDate();
		}
		return null;
	}

	public void setDeposit(DepositBean deposit) {
		deposits.add(deposit);
	}
	
	public void setDeposits(List<DepositBean> deposits) {
		for (DepositBean depositBean : deposits) {
			this.deposits.add(depositBean);
		}
	}
	
	public List<DepositBean> getDeposits() {
		return this.deposits;
	}
	
	public void clearDeposits() {
		this.deposits.clear();
	}


}
