package swingUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import mBankExceptions.MBankException;
import actions.AdminActions;
import actions.ClientActions;
import beans.AccountsBean;
import beans.ActivityBean;
import beans.ClientBean;
import beans.DepositBean;
import beans.PropertiesBean;

/**
 * This class defines the Form panel and all its sub classes<p>
 * All the classes's methods includes a simple check of user input.
 * @author Simon Mor
 */
public class FormPanel extends JPanel {
	protected static final actions.AdminActions adminActions = new AdminActions();
	protected static final actions.ClientActions clientActions = new ClientActions();
	protected static TableCardsPanel tablePanel = new TableCardsPanel();  

	protected static final String FORM_DEFAULT_CARD = "formDefaultCard";
	protected static final String FORM_ADD_NEW_CLIENT = "formAddNewClient";
	protected static final String FORM_UPDATE_CLIENT_DETAILS = "updateClientDetails";
	protected static final String FORM_REMOVE_CLIENT = "formRemoveClient";
	protected static final String FORM_CREATE_NEW_ACCOUNT = "formCreateNewAccount";
	protected static final String FORM_REMOVE_ACCOUNT = "formRemoveAccount";
	protected static final String FORM_WITHDRAW_FROM_ACCOUNT = "formWithdrawFromAccount";
	protected static final String FORM_DEPOSIT_TO_ACCOUNT = "formDepositToAccount";
	protected static final String FORM_CREATE_NEW_DEPOSIT = "formCreateNewDeposit";
	protected static final String FORM_PRE_OPEN_DEPOSIT = "formPreOpenDeposit";
	protected static final String FORM_VIEW_CLIENT_DETAILS = "formViewClientDetails";
	protected static final String FORM_VIEW_ACCOUNT_DETAILS = "formViewAccountDetails";
	protected static final String FORM_VIEW_CLIENT_DEPOSITS = "formViewClientDeposits";
	protected static final String FORM_VIEW_ALL_CLIENT_ACTIVITIES = "formViewAllClientActivities";
	protected static final String FORM_UPDATE_SYSTEM_PROPERTY = "formUpdateSystemProperty";
	protected static final String FORM_VIEW_SYSTEM_PROPERTY = "formViewSystemProperty";

	private static JPanel thisLink;

	protected static CardLayout cards;
	private FormDefaultCard formDefaultCard;
	private FormAddNewClient formAddNewClient;
	private UpdateClientDetails formUpdateClientDetails;
	private FormRemoveClient formRemoveClient;
	private FormCreateNewAccount formCreateNewAccount;
	private FormRemoveAccount formRemoveAccount;
	private FormWithdrawFromAccount formWithdrawFromAccount;
	private FormDepositToAccount formDepositToAccount;
	private FormCreateNewDeposit formCreateNewDeposit;
	private FormPreOpenDeposit formPreOpenDeposit;
	private FormViewClientDetails formViewClientDetails;
	private FormViewAccountDetails formViewAccountDetails;
	private FormViewClientDeposits formViewClientDeposits;
	private FormViewAllClientActivities formViewAllClientActivities;
	private FormUpdateSystemProperty formUpdateSystemProperty;
	private FormViewSystemProperty formViewSystemProperty;

	public FormPanel() {
		thisLink = this;
		cards = new CardLayout();
		setLayout(cards);
		Dimension dim = getPreferredSize();
		dim.width = 350;
		setPreferredSize(dim);
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		formDefaultCard = new FormDefaultCard();
		formAddNewClient = new FormAddNewClient();
		formUpdateClientDetails = new UpdateClientDetails();
		formRemoveClient = new FormRemoveClient();
		formCreateNewAccount = new FormCreateNewAccount();
		formRemoveAccount = new FormRemoveAccount();
		formWithdrawFromAccount = new FormWithdrawFromAccount();
		formDepositToAccount = new FormDepositToAccount();
		formCreateNewDeposit = new FormCreateNewDeposit();
		formPreOpenDeposit = new FormPreOpenDeposit();
		formViewClientDetails = new FormViewClientDetails();
		formViewAccountDetails = new FormViewAccountDetails();
		formViewClientDeposits = new FormViewClientDeposits();
		formViewAllClientActivities = new FormViewAllClientActivities();
		formUpdateSystemProperty = new FormUpdateSystemProperty();
		formViewSystemProperty = new FormViewSystemProperty();

		add(formDefaultCard, FORM_DEFAULT_CARD);
		add(formAddNewClient, FORM_ADD_NEW_CLIENT);
		add(formUpdateClientDetails, FORM_UPDATE_CLIENT_DETAILS);
		add(formRemoveClient, FORM_REMOVE_CLIENT);
		add(formCreateNewAccount, FORM_CREATE_NEW_ACCOUNT);
		add(formRemoveAccount, FORM_REMOVE_ACCOUNT);
		add(formWithdrawFromAccount,FORM_WITHDRAW_FROM_ACCOUNT);
		add(formDepositToAccount, FORM_DEPOSIT_TO_ACCOUNT);
		add(formCreateNewDeposit, FORM_CREATE_NEW_DEPOSIT);
		add(formPreOpenDeposit, FORM_PRE_OPEN_DEPOSIT);
		add(formViewClientDetails, FORM_VIEW_CLIENT_DETAILS);
		add(formViewAccountDetails, FORM_VIEW_ACCOUNT_DETAILS);
		add(formViewClientDeposits, FORM_VIEW_CLIENT_DEPOSITS);
		add(formViewAllClientActivities, FORM_VIEW_ALL_CLIENT_ACTIVITIES);
		add(formUpdateSystemProperty, FORM_UPDATE_SYSTEM_PROPERTY);
		add(formViewSystemProperty, FORM_VIEW_SYSTEM_PROPERTY);

		cards.show(this, FORM_DEFAULT_CARD);
	}

	protected static void switchPanel(String str) {
		cards.show(thisLink, str);
	}
}

class FormDefaultCard extends JPanel {
	private JLabel clientsDetails;
	private JButton allClientsDetailsBtn;
	private JLabel accountsDetails;
	private JButton allAccountsDetailsBtn;
	private JLabel allDeposits;
	private JButton allDepositsBtn;
	private JLabel allActivities;
	private JButton allActivitiesBtn;

	public FormDefaultCard () {
		setBorder(BorderFactory.createTitledBorder("MBank 1776"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		clientsDetails = new JLabel("Press button view all CLients");
		allClientsDetailsBtn = new JButton("Clients Details");
		accountsDetails = new JLabel("Press button to view all Accounts");
		allAccountsDetailsBtn = new JButton("Accounts Details");
		allDeposits = new JLabel("Press button to view all deposits");
		allDepositsBtn = new JButton("All Deposits");
		allActivities = new JLabel("Press button to view all activities");
		allActivitiesBtn = new JButton("All Activities");

		add(clientsDetails); add(allClientsDetailsBtn); add(accountsDetails); add(allAccountsDetailsBtn); add(allDeposits); add(allDepositsBtn);
		add(allActivities); add(allActivitiesBtn);

		allClientsDetailsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.tablePanel.getClientsTable().getTableModel().getClients().clear();
				FormPanel.tablePanel.setTableModel(TableCardsPanel.CLIENTS_TABLE);
				FormPanel.tablePanel.getClientsTable().getTableModel().setClients(FormPanel.adminActions.viewAllClientsDetails());
				FormPanel.tablePanel.refreshView(1);
			}
		});
		allAccountsDetailsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.tablePanel.getAccountsTable().getTableModel().getAccounts().clear();
				FormPanel.tablePanel.setTableModel(TableCardsPanel.ACCOUNTS_TABLE);
				FormPanel.tablePanel.getAccountsTable().getTableModel().setAccounts(FormPanel.adminActions.viewAllAccountsDetails());
				FormPanel.tablePanel.refreshView(2);
			}
		});
		allDepositsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.tablePanel.getDepositsTable().getTableModel().getDeposits().clear();
				FormPanel.tablePanel.setTableModel(TableCardsPanel.DEPOSITS_TABLE);
				FormPanel.tablePanel.getDepositsTable().getTableModel().setDeposits(FormPanel.adminActions.viewAllDeposits());
				FormPanel.tablePanel.refreshView(3);
			}
		});

		allActivitiesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.tablePanel.getActivitiesTable().getTableModel().getActivities().clear();
				FormPanel.tablePanel.setTableModel(TableCardsPanel.ACTIVITIES_TABLE);
				FormPanel.tablePanel.getActivitiesTable().getTableModel().setActivities(FormPanel.adminActions.viewAllActivities());
				FormPanel.tablePanel.refreshView(4);
			}
		});
	}
}

class FormAddNewClient extends JPanel {
	private JLabel clientName;
	private JTextField clientNameTxtField;
	private JLabel clientPassword;
	private JTextField clientPasswordTxtField;
	private ButtonGroup clientType;
	private JRadioButton regular;
	private JRadioButton gold;
	private JRadioButton platinum;
	private JLabel clientAddress;
	private JTextField clientAddressTxtField;
	private JLabel clientEmail;
	private JTextField clientEmailTxtField;
	private JLabel clientPhone;
	private JTextField clientPhoneTxtField;
	private JLabel clientComment;
	private JTextField clientCommentTxtField;
	private JLabel account;
	private JLabel accountBalance;
	private JTextField accountBalanceTxtField;
	private JLabel accountComment;
	private JTextField accountCommentTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormAddNewClient() {
		setBorder(BorderFactory.createTitledBorder("Add New Client"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientName = new JLabel("Client Name");
		clientNameTxtField = new JTextField(10);
		clientPassword = new JLabel("Client Password");
		clientPasswordTxtField = new JTextField(10);
		regular = new JRadioButton("Regualr");
		regular.setActionCommand("regular");
		gold = new JRadioButton("Gold");
		gold.setActionCommand("gold");
		platinum = new  JRadioButton("Platinum");
		platinum.setActionCommand("platinum");
		clientType = new ButtonGroup();
		clientType.add(regular);
		clientType.add(gold);
		clientType.add(platinum);
		regular.setSelected(true);
		clientAddress = new JLabel("Client Address");
		clientAddressTxtField = new JTextField(20);
		clientEmail = new JLabel("Client Email");
		clientEmailTxtField = new JTextField(20);
		clientPhone = new JLabel("Client Phone");
		clientPhoneTxtField = new JTextField(10);
		clientComment = new JLabel("Comment");
		clientCommentTxtField = new JTextField(20);
		account = new JLabel("Account Additional Input");
		Font font = account.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		account.setFont(font.deriveFont(attributes));
		accountBalance = new JLabel("Balance");
		accountBalanceTxtField = new JTextField(10);
		accountComment = new JLabel("Comment");
		accountCommentTxtField = new JTextField(20);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientName);
		add(clientNameTxtField);
		add(clientPassword);
		add(clientPasswordTxtField);
		add(new JLabel("Client Type"));
		add(regular);
		add(gold);
		add(platinum);
		add(clientAddress);
		add(clientAddressTxtField);
		add(clientEmail);
		add(clientEmailTxtField);
		add(clientPhone);
		add(clientPhoneTxtField);
		add(clientComment);
		add(clientCommentTxtField);
		add(account);
		add(accountBalance);
		add(accountBalanceTxtField);
		add(accountComment);
		add(accountCommentTxtField);
		add(submit);
		add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					String clientNameInput = clientNameTxtField.getText();
					boolean isNameValid = (clientNameInput != null && !clientNameInput.isEmpty()) ? true:false;
					String clientPassInput = clientPasswordTxtField.getText();
					boolean isPassValid = (clientPassInput != null && !clientPassInput.isEmpty()) ? true:false;
					String clientAdd = clientAddressTxtField.getText();
					boolean isAddValid = (clientAdd != null && !clientAdd.isEmpty()) ? true:false;
					String clientEmail = (clientEmailTxtField.getText() != null && !clientEmailTxtField.getText().isEmpty()) ?
							clientEmailTxtField.getText():"No Email was entered";
							String clientPhone = clientPhoneTxtField.getText();	
							boolean isPhoneValid = (clientPhone != null && !clientPhone.isEmpty()) ? true:false;
							String clientComm = (clientCommentTxtField.getText() != null && !clientCommentTxtField.getText().isEmpty()) ?
									clientCommentTxtField.getText():"No comment was entered";
									String accountBalInput = accountBalanceTxtField.getText();
									boolean isAccBalValid = ((accountBalInput != null && !accountBalInput.isEmpty())) ? true:false;
									String accountCom = (accountCommentTxtField.getText() != null && !accountCommentTxtField.getText().isEmpty()) ?
											accountCommentTxtField.getText():"No comment entered";
											if (isNameValid && isPassValid && isAddValid && isPhoneValid && isAccBalValid) {
												double accountBal = Double.parseDouble(accountBalInput);
												int clientId = 0;
												if (accountBal > 0) {
													ClientBean tempClient = new ClientBean(clientNameInput, clientPassInput, 
															enums.AccountType.getTypeFromString(clientType.getSelection().getActionCommand()), clientAdd, clientEmail, 
															clientPhone, clientComm);
													FormPanel.adminActions.addNewClient(tempClient); 
													FormPanel.tablePanel.getClientsTable().getTableModel().getClients().clear();
													FormPanel.tablePanel.setTableModel(TableCardsPanel.CLIENTS_TABLE);
													clientId = FormPanel.adminActions.getClientId(tempClient);
													tempClient.setId(clientId);
													FormPanel.tablePanel.getClientsTable().getTableModel().setClient(tempClient);
													FormPanel.tablePanel.refreshView(1);
													if (clientId != 0) {
														AccountsBean tempAccount = new AccountsBean(clientId, accountBal, accountCom);
														FormPanel.adminActions.createNewAccount(tempClient, tempAccount);
														FormPanel.tablePanel.refreshView(1);
													}
												}
												else {
													JOptionPane.showMessageDialog(null, "Please enter valid inputs in:\nname,password,email,phone,balance", "Input Error", JOptionPane.OK_OPTION);
												}
											}
											else {
												JOptionPane.showMessageDialog(null, "Please enter valid inputs in :\nname,password,email,phone,balance", "Input Error", JOptionPane.OK_OPTION);
											}

				} catch (MBankException e) {
					System.err.println("\nproblem in app , creating new client\n");
					e.printStackTrace();
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class UpdateClientDetails extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JLabel clientName;
	private JTextField clientNameTxtField;
	private JLabel clientPassword;
	private JTextField clientPasswordTxtField;
	private ButtonGroup clientType;
	private JRadioButton regular;
	private JRadioButton gold;
	private JRadioButton platinum;
	private JLabel clientAddress;
	private JTextField clientAddressTxtField;
	private JLabel clientEmail;
	private JTextField clientEmailTxtField;
	private JLabel clientPhone;
	private JTextField clientPhoneTxtField;
	private JLabel clientComment;
	private JTextField clientCommentTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public UpdateClientDetails() {
		setBorder(BorderFactory.createTitledBorder("Update Client Details"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		clientName = new JLabel("Client Name");
		clientNameTxtField = new JTextField(10);
		clientPassword = new JLabel("Client Password");
		clientPasswordTxtField = new JTextField(10);
		regular = new JRadioButton("Regualr");
		regular.setActionCommand("regular");
		gold = new JRadioButton("Gold");
		gold.setActionCommand("gold");
		platinum = new  JRadioButton("Platinum");
		platinum.setActionCommand("platinum");
		clientType = new ButtonGroup();
		clientType.add(regular);
		clientType.add(gold);
		clientType.add(platinum);
		regular.setSelected(true);
		clientAddress = new JLabel("Client Address");
		clientAddressTxtField = new JTextField(20);
		clientEmail = new JLabel("Client Email");
		clientEmailTxtField = new JTextField(20);
		clientPhone = new JLabel("Client Phone");
		clientPhoneTxtField = new JTextField(10);
		clientComment = new JLabel("Comment");
		clientCommentTxtField = new JTextField(20);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId);
		add(clientIdTxtField);
		add(clientName);
		add(clientNameTxtField);
		add(clientPassword);
		add(clientPasswordTxtField);
		add(new JLabel("Client Type"));
		add(regular);
		add(gold);
		add(platinum);
		add(clientAddress);
		add(clientAddressTxtField);
		add(clientEmail);
		add(clientEmailTxtField);
		add(clientPhone);
		add(clientPhoneTxtField);
		add(clientComment);
		add(clientCommentTxtField);
		add(submit);
		add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String clientIdInput = clientIdTxtField.getText();

					boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
					String clientNameInput = clientNameTxtField.getText();
					boolean isNameValid = (clientNameInput != null && !clientNameInput.isEmpty()) ? true:false;
					String clientPassInput = clientPasswordTxtField.getText();
					boolean isPassValid = (clientPassInput != null && !clientPassInput.isEmpty()) ? true:false;
					String clientAdd = clientAddressTxtField.getText();
					boolean isAddValid = (clientAdd != null && !clientAdd.isEmpty()) ? true:false;
					String clientEmail = (clientEmailTxtField.getText() != null && !clientEmailTxtField.getText().isEmpty()) ?
							clientEmailTxtField.getText():"No Email was entered";
							String clientPhone = clientPhoneTxtField.getText();	
							boolean isPhoneValid = (clientPhone != null && !clientPhone.isEmpty()) ? true:false;
							String clientComm = (clientCommentTxtField.getText() != null && !clientCommentTxtField.getText().isEmpty()) ?
									clientCommentTxtField.getText():"No comment was entered";
									if (isIdValid && isNameValid && isPassValid && isAddValid && isPhoneValid) {
										int clientId = Integer.parseInt(clientIdInput);
										if (clientId > 0) {
											ClientBean tempClient = new ClientBean(clientId, clientNameInput, clientPassInput, 
													enums.AccountType.getTypeFromString(clientType.getSelection().getActionCommand()), 
													clientAdd, clientEmail, clientPhone, clientComm);
											FormPanel.adminActions.updateClientDetails(tempClient);
											FormPanel.tablePanel.getClientsTable().getTableModel().getClients().clear();
											FormPanel.tablePanel.setTableModel(TableCardsPanel.CLIENTS_TABLE);
											FormPanel.tablePanel.getClientsTable().getTableModel().setClient(tempClient);
											FormPanel.tablePanel.refreshView(1);
										}
										else {
											JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
										}
									}
									else {
										JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
									}
				} catch (MBankException e1) {
					e1.printStackTrace();
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormRemoveClient extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormRemoveClient() {
		setBorder(BorderFactory.createTitledBorder("Remove Client"));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId);
		add(clientIdTxtField);
		add(submit);
		add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				if (clientIdInput != null && !clientIdInput.isEmpty()) {
					int clientId = Integer.parseInt(clientIdInput);
					if (clientId > 0) {
						ClientBean tempClient = FormPanel.adminActions.getClientObjectById(clientId);
						FormPanel.adminActions.removeClient(tempClient); 
						FormPanel.tablePanel.getClientsTable().getTableModel().getClients().clear();
						FormPanel.tablePanel.getClientsTable().getTableModel().getClients().remove(tempClient);
						FormPanel.tablePanel.refreshView(1);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormCreateNewAccount extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JLabel balance;
	private JTextField balanceTxtField;
	private JLabel comment;
	private JTextField commentTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormCreateNewAccount() {
		setBorder(BorderFactory.createTitledBorder("Create New Account"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		balance = new JLabel("Balance");
		balanceTxtField = new JTextField(10);
		comment = new JLabel("Comment");
		commentTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField); add(balance); add(balanceTxtField); add(comment); add(commentTxtField); 
		add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				String balanceInput = balanceTxtField.getText();
				String commentInput = commentTxtField.getText();
				boolean IsInputValid = ((clientIdInput != null && !clientIdInput.isEmpty()) && (balanceInput != null && !balanceInput.isEmpty()));
				if (IsInputValid) {
					int clientId = Integer.parseInt(clientIdInput);
					double balance = Double.parseDouble(balanceInput);
					if ((clientId > 0) && (balance > 0)) {
						AccountsBean tempAccount = new AccountsBean(clientId, balance, commentInput);
						ClientBean tempClient = FormPanel.adminActions.getClientObjectById(Integer.parseInt(clientIdTxtField.getText()));
						FormPanel.adminActions.createNewAccount(tempClient, tempAccount);
						FormPanel.tablePanel.getAccountsTable().getTableModel().getAccounts().clear();
						FormPanel.tablePanel.setTableModel(TableCardsPanel.ACCOUNTS_TABLE);
						FormPanel.tablePanel.getAccountsTable().getTableModel().setAccount(tempAccount);
						FormPanel.tablePanel.refreshView(2);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormRemoveAccount extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormRemoveAccount() {
		setBorder(BorderFactory.createTitledBorder("Remove Account"));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				if (clientIdInput != null && !clientIdInput.isEmpty()) {
					int clientId = Integer.parseInt(clientIdInput);
					if (clientId >= 0) {
						AccountsBean tempAccount = FormPanel.adminActions.getAccountObjectByClientId(clientId);
						FormPanel.adminActions.removeAccount(tempAccount);
						FormPanel.tablePanel.getClientsTable().getTableModel().getClients().remove(tempAccount);
						FormPanel.tablePanel.refreshView(2);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid input", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormWithdrawFromAccount extends JPanel {
	private JLabel clientid;
	private JTextField clientIdTxtField;
	private JLabel amount;
	private JTextField amountTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormWithdrawFromAccount() {
		setBorder(BorderFactory.createTitledBorder("CLIENT - Withdraw From Account"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientid = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		amount = new JLabel("Amount");
		amountTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientid); add(clientIdTxtField); add(amount); add(amountTxtField); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				String amountInput = amountTxtField.getText();
				boolean isAmountValid = (amountInput != null && !amountInput.isEmpty()) ? true:false;
				if (isIdValid && isAmountValid) {
					int clientId = Integer.parseInt(clientIdTxtField.getText());
					double amount = Double.parseDouble(amountInput);
					ClientBean tempClient = FormPanel.clientActions.getClientObjectById(clientId);
					if (FormPanel.clientActions.withdrawFromAccount(tempClient, amount)) {
						FormPanel.tablePanel.getAccountsTable().getTableModel().getAccounts().clear();
						List<AccountsBean> tempList = new ArrayList<AccountsBean>(); 
						tempList.add(FormPanel.adminActions.getAccountObjectByClientId(clientId));
						FormPanel.tablePanel.getAccountsTable().getTableModel().setAccounts(tempList);
						FormPanel.cards.show(getParent(), TableCardsPanel.ACCOUNTS_TABLE);
						FormPanel.tablePanel.refreshView(2);
					}
					else {
						System.err.println(amountTxtField.getText()+" unsuccessfully removed from client "+tempClient.getId()+" account");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}

			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormDepositToAccount extends JPanel {  
	private JLabel clientid;
	private JTextField clientIdTxtField;
	private JLabel amount;
	private JTextField amountTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormDepositToAccount() {
		setBorder(BorderFactory.createTitledBorder("CLIENT - Deposit To Account"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientid = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		amount = new JLabel("Amount");
		amountTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientid); add(clientIdTxtField); add(amount); add(amountTxtField); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				String amountInput = amountTxtField.getText();
				boolean isAmountValid = (amountInput != null && !amountInput.isEmpty()) ? true:false; 
				if (isIdValid && isAmountValid) {
					int clientId = Integer.parseInt(clientIdInput);
					double amount = Double.parseDouble(amountInput);
					if (clientId > 0 && amount > 0) {
						ClientBean tempClient = FormPanel.clientActions.getClientObjectById(clientId);
						try {
							FormPanel.clientActions.depositToAccount(tempClient, amount);
						} catch (MBankException e1) {
							System.err.println(e1.getMessage());
						}
						FormPanel.tablePanel.getAccountsTable().getTableModel().getAccounts().clear();
						FormPanel.tablePanel.getAccountsTable().getTableModel().setAccounts(
								FormPanel.adminActions.viewAllAccountsDetails());
						FormPanel.tablePanel.setTableModel(TableCardsPanel.ACCOUNTS_TABLE);
						FormPanel.tablePanel.refreshView(2);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormCreateNewDeposit extends JPanel { 
	private JLabel clientid;
	private JTextField clientIdTxtField;
	private JLabel balance;
	private JTextField balanceTxtField;
	private JLabel depositDuration;
	private JTextField depositDurationtxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormCreateNewDeposit() {
		setBorder(BorderFactory.createTitledBorder("CLIENT - Create New Deposit"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientid = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		balance = new JLabel("Balance");
		balanceTxtField = new JTextField(10);
		depositDuration = new JLabel("Deposit Duration");
		depositDurationtxtField = new JTextField(2);
		depositDurationtxtField.setToolTipText("enter years only");
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientid); add(clientIdTxtField); add(balance); add(balanceTxtField); add(depositDuration); add(depositDurationtxtField); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				String balanceInput = balanceTxtField.getText();
				boolean isBalValid = (balanceInput != null && !balanceInput.isEmpty()) ? true:false;
				String depDurationInput = depositDurationtxtField.getText();
				boolean isDepDurValid = (depDurationInput != null && !depDurationInput.isEmpty()) ? true:false;
				if (isIdValid && isBalValid && isDepDurValid ) {
					int clientId = Integer.parseInt(clientIdInput);
					double balance = Double.parseDouble(balanceInput);
					int yearsToAdd = Integer.parseInt(depDurationInput);
					if ((clientId > 0) && (yearsToAdd > 0) && (balance > 0)) {
						Calendar cal = Calendar.getInstance();
						Date currentTime = new java.sql.Date(cal.getTimeInMillis()); 
						cal.add(Calendar.YEAR, yearsToAdd);
						Date closingTime = new java.sql.Date(cal.getTimeInMillis());     
						DepositBean tempDeposit = new DepositBean(clientId, balance, currentTime, closingTime);
						ClientBean tempClient = FormPanel.clientActions.getClientObjectById(clientId);
						if (yearsToAdd == 1) {
							FormPanel.clientActions.createNewDeposit(tempClient, tempDeposit, true);
						}
						else {
							FormPanel.clientActions.createNewDeposit(tempClient, tempDeposit, false);
						}
						FormPanel.tablePanel.getDepositsTable().getTableModel().getDeposits().clear();
						List<DepositBean> clientDepList = FormPanel.clientActions.viewClientDeposits(tempClient);
						FormPanel.tablePanel.getDepositsTable().getTableModel().setDeposits(clientDepList); 
						FormPanel.tablePanel.setTableModel(TableCardsPanel.DEPOSITS_TABLE);
						FormPanel.tablePanel.refreshView(3);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormPreOpenDeposit extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JLabel depositId;
	private JTextField depositIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormPreOpenDeposit() {
		setBorder(BorderFactory.createTitledBorder("CLIENT- Pre Open Deposit"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		depositId = new JLabel("Deposit ID");
		depositIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField); add(depositId); add(depositIdTxtField); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				String depositIdInput = depositIdTxtField.getText();
				boolean isDepValid = (depositIdInput != null && !depositIdInput.isEmpty()) ? true:false;
				if (isIdValid && isDepValid) {
					int clientId = Integer.parseInt(clientIdInput);
					int depId = Integer.parseInt(depositIdInput); 
					if (clientId > 0 && depId > 0) {
						ClientBean tempClient = FormPanel.clientActions.getClientObjectById(clientId);
						DepositBean tempDeposit = FormPanel.clientActions.getDepositById(depId);
						FormPanel.clientActions.preOpenDeposit(tempClient ,tempDeposit);
						FormPanel.tablePanel.getDepositsTable().getTableModel().getDeposits().clear();
						List<DepositBean> tempDepList = FormPanel.clientActions.viewClientDeposits(tempClient);
						FormPanel.tablePanel.getDepositsTable().getTableModel().setDeposits(tempDepList);
						FormPanel.tablePanel.setTableModel(TableCardsPanel.DEPOSITS_TABLE);
						FormPanel.tablePanel.refreshView(3);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormViewClientDetails extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormViewClientDetails() {
		setBorder(BorderFactory.createTitledBorder("CLIENT- View Client Details"));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField);  add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				if (isIdValid) {
					int clientId = Integer.parseInt(clientIdInput);
					ClientBean tempClient = null;
					if (clientId > 0) {
						tempClient = FormPanel.clientActions.getClientObjectById(clientId);
						FormPanel.tablePanel.getClientsTable().getTableModel().getClients().clear();
						FormPanel.tablePanel.refreshView(1);
						FormPanel.tablePanel.setTableModel(TableCardsPanel.CLIENTS_TABLE);
						FormPanel.tablePanel.getClientsTable().getTableModel().setClient(tempClient);
						FormPanel.tablePanel.refreshView(1);
					}
					else if (tempClient == null) {
						JOptionPane.showMessageDialog(null, "No such client exist", "Input Error", JOptionPane.OK_OPTION);
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormViewAccountDetails extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormViewAccountDetails() {
		setBorder(BorderFactory.createTitledBorder("View Account Details"));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField);  add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				if  (isIdValid) {
					int clientId = Integer.parseInt(clientIdInput);
					AccountsBean tempAccount = null;
					if (clientId > 0) {
						ClientBean tempClient = FormPanel.adminActions.getClientObjectById(clientId);
						if (tempClient != null) {
							tempAccount = FormPanel.adminActions.viewAccountDetails(tempClient);
							if (tempAccount != null) {
								FormPanel.tablePanel.getAccountsTable().getTableModel().getAccounts().clear();
								FormPanel.tablePanel.getAccountsTable().getTableModel().setAccount(tempAccount);
								FormPanel.tablePanel.setTableModel(TableCardsPanel.ACCOUNTS_TABLE);
								FormPanel.tablePanel.refreshView(2);
							}
							else {
								JOptionPane.showMessageDialog(null, "No such account exist", "Input Error", JOptionPane.OK_OPTION);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "No such client exist", "Input Error", JOptionPane.OK_OPTION);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormViewClientDeposits extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormViewClientDeposits() {
		setBorder(BorderFactory.createTitledBorder("View Client Deposits"));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField);  add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				if (isIdValid) {
					int clientId = Integer.parseInt(clientIdInput);
					if (clientId > 0) {
						ClientBean tempClient = FormPanel.adminActions.getClientObjectById(clientId);
						List<DepositBean> tempDeposits = FormPanel.adminActions.viewClientDeposits(tempClient);
						if (tempDeposits != null) {
							FormPanel.tablePanel.setTableModel(TableCardsPanel.DEPOSITS_TABLE);
							FormPanel.tablePanel.getDepositsTable().getTableModel().setDeposits(tempDeposits);
							FormPanel.tablePanel.refreshView(3);
						}
						else {
							JOptionPane.showMessageDialog(null, "No deposits for client #"+tempClient.getId()+"", "Input Error", JOptionPane.OK_OPTION);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "No such client exist", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormViewAllClientActivities extends JPanel {
	private JLabel clientId;
	private JTextField clientIdTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormViewAllClientActivities() {
		setBorder(BorderFactory.createTitledBorder("View Client Activities"));

		clientId = new JLabel("Client ID");
		clientIdTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(clientId); add(clientIdTxtField);  add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clientIdInput = clientIdTxtField.getText();
				boolean isIdValid = (clientIdInput != null && !clientIdInput.isEmpty()) ? true:false;
				if (isIdValid) {
					int clientId = Integer.parseInt(clientIdInput);
					if (clientId > 0) {
						ClientBean tempClient = FormPanel.adminActions.getClientObjectById(clientId);
						List<ActivityBean> tempActivities = FormPanel.adminActions.viewClientActivities(tempClient);
						if (tempActivities != null) {
							FormPanel.tablePanel.setTableModel(TableCardsPanel.ACTIVITIES_TABLE);
							FormPanel.tablePanel.getActivitiesTable().getTableModel().setActivities(tempActivities);
							FormPanel.tablePanel.refreshView(4);
						}
						else {
							JOptionPane.showMessageDialog(null, "No activities found", "Input Error", JOptionPane.OK_OPTION);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "No such client found", "Input Error", JOptionPane.OK_OPTION);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter valid values", "Input Error", JOptionPane.OK_OPTION);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormUpdateSystemProperty extends JPanel {
	private JLabel choosePropKey;
	private JComboBox<String> propKeyBox;
	private JLabel propValue;
	private JTextField propValueTxtField;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormUpdateSystemProperty() {
		setBorder(BorderFactory.createTitledBorder("Update System Property "));

		choosePropKey = new JLabel("Choose System Property To Update");
		propKeyBox = new JComboBox<String>();
		DefaultComboBoxModel<String> propKeyModel = new DefaultComboBoxModel<String>();
		propKeyModel.addElement("regular_deposit_rate");
		propKeyModel.addElement("gold_deposit_rate");
		propKeyModel.addElement("platinum_deposit_rate");
		propKeyModel.addElement("regular_deposit_commission");
		propKeyModel.addElement("gold_deposit_commission");
		propKeyModel.addElement("platinum_deposit_commission");
		propKeyModel.addElement("regular_credit_limit");
		propKeyModel.addElement("gold_credit_limit");
		propKeyModel.addElement("platinum_credit_limit");
		propKeyModel.addElement("commission_rate");
		propKeyModel.addElement("regular_daily_interest");
		propKeyModel.addElement("gold_daily_interest");
		propKeyModel.addElement("platinum_daily_interest");
		propKeyModel.addElement("pre_open_fee");
		propKeyBox.setModel(propKeyModel);
		propValue = new JLabel("Enter New Value");
		propValueTxtField = new JTextField(10);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(choosePropKey); add(propKeyBox); add(propValue); add(propValueTxtField); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				String propValueInput = propValueTxtField.getText();
				boolean isPropValueValid = (propValueInput != null && !propValueInput.isEmpty()) ? true:false;
				if (isPropValueValid) {
					PropertiesBean tempPropBean = new PropertiesBean((String) propKeyBox.getSelectedItem(), propValueInput);
					FormPanel.adminActions.updateSystemProperty(tempPropBean);
					FormPanel.tablePanel.setTableModel(TableCardsPanel.PROPERTIES_TABLE);
					FormPanel.tablePanel.getPropertiesTable().getTableModel().getProperties().clear();
					List<PropertiesBean> properties = (List<PropertiesBean>) FormPanel.adminActions.viewSystemProperty(tempPropBean.getPropKey());
					FormPanel.tablePanel.getPropertiesTable().getTableModel().setProperties(tempPropBean);
					FormPanel.tablePanel.refreshView(5);
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter a valid value", "Input Error", JOptionPane.OK_OPTION);
				}

			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}

class FormViewSystemProperty extends JPanel {
	private JLabel choosePropKey;
	private JComboBox<String> propKeyBox;
	private JButton submit;
	private JButton toDefaultPanel;

	public FormViewSystemProperty() {
		setBorder(BorderFactory.createTitledBorder("View System Property "));

		choosePropKey = new JLabel("Choose System Property To View");
		propKeyBox = new JComboBox<String>();
		DefaultComboBoxModel<String> propKeyModel = new DefaultComboBoxModel<String>();
		propKeyModel.addElement("regular_deposit_rate");
		propKeyModel.addElement("gold_deposit_rate");
		propKeyModel.addElement("platinum_deposit_rate");
		propKeyModel.addElement("regular_deposit_commission");
		propKeyModel.addElement("gold_deposit_commission");
		propKeyModel.addElement("platinum_deposit_commission");
		propKeyModel.addElement("regular_credit_limit");
		propKeyModel.addElement("gold_credit_limit");
		propKeyModel.addElement("platinum_credit_limit");
		propKeyModel.addElement("commission_rate");
		propKeyModel.addElement("regular_daily_interest");
		propKeyModel.addElement("gold_daily_interest");
		propKeyModel.addElement("platinum_daily_interest");
		propKeyModel.addElement("pre_open_fee");
		propKeyBox.setModel(propKeyModel);
		submit = new JButton("Submit");
		toDefaultPanel = new JButton("Default Panel");

		add(choosePropKey); add(propKeyBox); add(submit); add(toDefaultPanel);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertiesBean tempProperty = FormPanel.adminActions.viewSystemProperty((String) propKeyBox.getSelectedItem());
				if (tempProperty != null) {
					FormPanel.tablePanel.setTableModel(TableCardsPanel.PROPERTIES_TABLE);
					FormPanel.tablePanel.getPropertiesTable().getTableModel().getProperties().clear();
					FormPanel.tablePanel.getPropertiesTable().getTableModel().setProperties(tempProperty);
					FormPanel.tablePanel.refreshView(5);
				}
			}
		});

		toDefaultPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormPanel.cards.show(getParent(), FormPanel.FORM_DEFAULT_CARD);
			}
		});
	}
}










