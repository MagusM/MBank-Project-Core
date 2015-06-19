package swingUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * This class defines the panel contains the operation command buttons
 * @author Simon Mor
 */
public class FunctionsToolBar extends JToolBar implements ActionListener{
	private TextPanel textPanel;
	
	private JButton addNewClient;
	private JButton updateClientDetails;
	private JButton removeClient;
	private JButton createNewAccount;
	private JButton removeAccount;
	private JButton withdrawFromAccount;
	private JButton depositToAccount;
	private JButton createNewDeposit;
	private JButton preOpenDeposit;
	private JButton viewClientDetails;
	private JButton viewAccountDetails;
	private JButton viewClientDeposits;
	private JButton viewAllClientActivities;
	private JButton updateSystemProperty;
	private JButton viewSystemProperty;
	
	private Dimension btnDimension = new Dimension();
	public FunctionsToolBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		Dimension dim = getPreferredSize();
		dim.width = 160;
		setPreferredSize(dim);
		setBorder(BorderFactory.createEtchedBorder());
		
		addNewClient = new JButton("Add New Client"); addNewClient.setName("addNewClient");
		updateClientDetails = new JButton("Update Client Details"); updateClientDetails.setName("updateClientDetails");
		removeClient = new JButton("Remove Client"); removeClient.setName("removeClient");
		createNewAccount = new JButton("Create New Account"); createNewAccount.setName("createNewAccount");
		removeAccount = new JButton("Remove Account"); removeAccount.setName("removeAccount");
		withdrawFromAccount = new JButton("Withdraw From Account"); withdrawFromAccount.setName("withdrawFromAccount");
		depositToAccount = new JButton("Deposit To Account"); depositToAccount.setName("depositToAccount");
		createNewDeposit = new JButton("Create New Deposit"); createNewDeposit.setName("createNewDeposit");
		preOpenDeposit = new JButton("Pre Open Deposit"); preOpenDeposit.setName("preOpenDeposit");
		viewClientDetails = new JButton("View Client Details"); viewClientDetails.setName("viewClientDetails");
		viewAccountDetails = new JButton("View Account Details"); viewAccountDetails.setName("viewAccountDetails");
		viewClientDeposits = new JButton("View Client Deposits"); viewClientDeposits.setName("viewClientDeposits");
		viewAllClientActivities = new JButton("View All Client Activities"); viewAllClientActivities.setName("viewAllClientActivities");
		updateSystemProperty = new JButton("Update System Property"); updateSystemProperty.setName("updateSystemProperty");
		viewSystemProperty = new JButton("View System Property"); viewSystemProperty.setName("viewSystemProperty");
		
		List<JButton> btnlist = new ArrayList<JButton>();
		btnlist.add(addNewClient); btnlist.add(updateClientDetails); btnlist.add(removeClient); btnlist.add(createNewAccount); btnlist.add(removeAccount);
		btnlist.add(withdrawFromAccount); btnlist.add(depositToAccount); btnlist.add(createNewDeposit); btnlist.add(preOpenDeposit); 
		btnlist.add(viewClientDetails); btnlist.add(viewAccountDetails); btnlist.add(viewClientDeposits); btnlist.add(viewAllClientActivities);
		btnlist.add(updateSystemProperty); btnlist.add(viewSystemProperty);
		
		btnDimension = viewAllClientActivities.getPreferredSize();
		btnDimension.width = 145;
		for (JButton jButton : btnlist) {
			jButton.setPreferredSize(btnDimension);
		}
		
		addNewClient.addActionListener(this);
		updateClientDetails.addActionListener(this);
		removeClient.addActionListener(this);
		createNewAccount.addActionListener(this);
		removeAccount.addActionListener(this);
		withdrawFromAccount.addActionListener(this);
		depositToAccount.addActionListener(this);
		createNewDeposit.addActionListener(this);
		preOpenDeposit.addActionListener(this);
		viewClientDetails.addActionListener(this);
		viewAccountDetails.addActionListener(this);
		viewClientDeposits.addActionListener(this);
		viewAllClientActivities.addActionListener(this);
		updateSystemProperty.addActionListener(this);
		viewSystemProperty.addActionListener(this);
		
		add(addNewClient); add(updateClientDetails); add(removeClient); add(createNewAccount); add(removeAccount); add(withdrawFromAccount);
		add(depositToAccount); add(createNewDeposit); add(preOpenDeposit); add(viewClientDetails); add(viewAccountDetails); add(viewClientDeposits);
		add(viewAllClientActivities); add(updateSystemProperty); add(viewSystemProperty);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnClicked = (JButton) e.getSource();
		switch (btnClicked.getName()) {
		case "addNewClient":
			FormPanel.switchPanel(FormPanel.FORM_ADD_NEW_CLIENT);
			break;
		
		case "updateClientDetails":
			FormPanel.switchPanel(FormPanel.FORM_UPDATE_CLIENT_DETAILS);
			break;
		
		case "removeClient":
			FormPanel.switchPanel(FormPanel.FORM_REMOVE_CLIENT);
			break;
		case "createNewAccount":
			FormPanel.switchPanel(FormPanel.FORM_CREATE_NEW_ACCOUNT);
			break;
		case "removeAccount":
			FormPanel.switchPanel(FormPanel.FORM_REMOVE_ACCOUNT);
			break;
		case "withdrawFromAccount":
			FormPanel.switchPanel(FormPanel.FORM_WITHDRAW_FROM_ACCOUNT);
			break;
		case "depositToAccount":
			FormPanel.switchPanel(FormPanel.FORM_DEPOSIT_TO_ACCOUNT);
			break;
		case "createNewDeposit":
			FormPanel.switchPanel(FormPanel.FORM_CREATE_NEW_DEPOSIT);
			break;
		case "preOpenDeposit":
			FormPanel.switchPanel(FormPanel.FORM_PRE_OPEN_DEPOSIT);
			break;
		case "viewClientDetails":
			FormPanel.switchPanel(FormPanel.FORM_VIEW_CLIENT_DETAILS);
			break;
		case "viewAccountDetails":
			FormPanel.switchPanel(FormPanel.FORM_VIEW_ACCOUNT_DETAILS);
			break;
		case "viewClientDeposits":
			FormPanel.switchPanel(FormPanel.FORM_VIEW_CLIENT_DEPOSITS);
			break;
		case "viewAllClientActivities":
			FormPanel.switchPanel(FormPanel.FORM_VIEW_ALL_CLIENT_ACTIVITIES);
			break;
		case "updateSystemProperty":
			FormPanel.switchPanel(FormPanel.FORM_UPDATE_SYSTEM_PROPERTY);
			break;
		case "viewSystemProperty":
			FormPanel.switchPanel(FormPanel.FORM_VIEW_SYSTEM_PROPERTY);
			break;
		
		default:
			FormPanel.switchPanel(FormPanel.FORM_DEFAULT_CARD);
			break;
		}
	}

	public void setTextPanel(TextPanel textPanel) {
		this.textPanel = textPanel;
		
	}
	

}
