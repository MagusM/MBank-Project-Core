package swingUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.PropertiesBean;

public class PropertiesTableModel extends AbstractTableModel {
	private List<PropertiesBean> properties = new ArrayList<PropertiesBean>();
	private String[] columnNames = {"Property Key", "Property Value"};

	public PropertiesTableModel() {
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return properties.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PropertiesBean tempProperty = properties.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return tempProperty.getPropKey();
		case 1:
			return tempProperty.getPropValue();
		}
		return null;
	}

	public List<PropertiesBean> getProperties() {
		return properties;
	}

	public void setProperties(PropertiesBean property) {
		this.properties.add(property);
	}
	
}
