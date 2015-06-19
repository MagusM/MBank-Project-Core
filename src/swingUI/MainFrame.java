package swingUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import mbank.MbankFileFilter;

/**
 * This class defines the MBank app main frame
 * @author Simon Mor
 *
 */
public class MainFrame extends JFrame implements ActionListener{
	private TextPanel textPanel;
	private JToolBar toolBar;
	private JLabel toolBarLabel;
	private JButton magus;
	private FunctionsToolBar fToolBar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private TableCardsPanel tablePanel;
	
	/**
	 * Constructor
	 */
	public MainFrame() {
		//frame
		super("MBank - Banking System");
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 650)); 
		setLocation(120, 120);
		setLayout(new BorderLayout());
		setJMenuBar(createMenuBar());
		
		//south toolbar
		toolBar = new JToolBar();
		add(toolBar, BorderLayout.SOUTH);
		toolBarLabel = new JLabel("NOVUS ORDO SECLORUM");
		magus = new JButton("MaguS");
		toolBar.add(toolBarLabel);
		toolBar.add(magus);
		magus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i=0;i<10;i++){
					System.out.println();
				}
				JOptionPane.showMessageDialog(MainFrame.this, "Please visit:\nwww.unumoculus.com !!");
			}
		});
		
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		toolBar.setLayout(new FlowLayout());
		
		//east ftoolbar
		fToolBar = new FunctionsToolBar();
		this.add(fToolBar, BorderLayout.EAST);
		fToolBar.setTextPanel(textPanel);
		
		//form panel 
		formPanel = new FormPanel();
		this.add(formPanel, BorderLayout.WEST);
		
		//table
		tablePanel = new TableCardsPanel();
		add(tablePanel, BorderLayout.CENTER);
		
		//file chooser
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new MbankFileFilter());
		
		//mini login confirmation
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = {
		    "Username:", username,
		    "Password:", password
		};
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String inputUserName = username.getText();
			String inputPassword = password.getText();
		    if ("system".equals(inputUserName) && "password".equals(inputPassword)) {
		        JOptionPane.showMessageDialog(null, "Login successful\nWelcome To Mbank");
		    } 
		    else {
		    	JOptionPane.showMessageDialog(null, "Login faild");
		    	System.exit(0);
		    	
		    }
		} else {
			JOptionPane.showMessageDialog(null, "Login canceled");
			System.exit(0);
		}
	}
	
	protected TextPanel getTextPanel() {
		return textPanel;
	}
	
	//defining JMenuBar
	private JMenuBar  createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu  fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		//File's JItems
		JMenuItem importDataItem = new JMenuItem("Import Data..."); fileMenu.add(importDataItem);
		importDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(MainFrame.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						//TODO continue from here.
					}
				}
			}
		});
		JMenuItem exportDataItem = new JMenuItem("Export Data..."); fileMenu.add(exportDataItem); 
		exportDataItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showSaveDialog(MainFrame.this);
			}
		});
		
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit"); fileMenu.add(exitItem); exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int optionClicked = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (optionClicked == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		
		//View menue and its JItems
		JMenu  viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		JMenuItem refreshView = new JMenuItem("Refresh View"); viewMenu.add(refreshView);
		refreshView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//FormPanel.tablePanel.refreshView(FormPanel.tablePanel.getIndicator());
				for (int i=0; i<5; i++) {
					FormPanel.tablePanel.refreshView(i);
					}
				}
		});

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		
		
		return menuBar;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame.this.invalidate();
		
	}

	
	
	
}
