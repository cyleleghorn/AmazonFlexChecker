package flexChecker;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class AmazonFlexChecker 
{

	private JFrame frmAmazonFlexChecker;
	public static final String version = "V0.1.1";
	private JTextField refreshIntervalTextField;
	public static int port = 5555;
	public static int refreshInterval = 10;
	public static Preferences prefs;
	public static String username = "";
	public static String password = "";
	public static boolean autoRefresh = true;
	public static JMenuBar menuBar = new JMenuBar();
	public static JMenu mnOptions = new JMenu("Options");
	public static JMenuItem loginInfoMenuItem = new JMenuItem("Amazon Flex Account Information");
	public static JMenuItem mntmListeningPort = new JMenuItem("Listening Port");
	public static JMenu mntmAutorefresh = new JMenu("Auto-Refresh");
	public static JCheckBoxMenuItem chckbxmntmEnableAutorefresh = new JCheckBoxMenuItem("Enable Auto-Refresh");
	public static JSeparator separator_1 = new JSeparator();
	public static JLabel lblRefreshIntervalIn = new JLabel("Refresh Interval in Seconds");
	public static JButton btnApply = new JButton("Apply");
	public static JSeparator separator = new JSeparator();
	public static JButton btnRefresh = new JButton("Refresh");

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					AmazonFlexChecker window = new AmazonFlexChecker();
					window.frmAmazonFlexChecker.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AmazonFlexChecker() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmAmazonFlexChecker = new JFrame();
		frmAmazonFlexChecker.setTitle("Amazon Flex Checker " + version);
		frmAmazonFlexChecker.setBounds(100, 100, 450, 400);
		frmAmazonFlexChecker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAmazonFlexChecker.getContentPane().setLayout(new MigLayout("", "[]", "[]"));


		/*   Set the look and feel to windows.  Might use this, might not.

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 */

		prefs = Preferences.userNodeForPackage(this.getClass());
		port = prefs.getInt("PORT", port);
		refreshInterval = prefs.getInt("RI", refreshInterval);
		autoRefresh = prefs.getBoolean("AR", autoRefresh);
		chckbxmntmEnableAutorefresh.setSelected(autoRefresh);

		
		frmAmazonFlexChecker.setJMenuBar(menuBar);

		menuBar.add(mnOptions);

		loginInfoMenuItem.setToolTipText("Enter your login information for Amazon Flex so the program can log in and check for available time blocks.");
		mnOptions.add(loginInfoMenuItem);

		mnOptions.add(mntmListeningPort);

		mnOptions.add(mntmAutorefresh);

		chckbxmntmEnableAutorefresh.setSelected(true);
		mntmAutorefresh.add(chckbxmntmEnableAutorefresh);

		mntmAutorefresh.add(separator_1);

		mntmAutorefresh.add(lblRefreshIntervalIn);

		refreshIntervalTextField = new JTextField();
		refreshIntervalTextField.setText(Integer.toString(prefs.getInt("RI", refreshInterval)));
		mntmAutorefresh.add(refreshIntervalTextField);
		refreshIntervalTextField.setColumns(10);

		mntmAutorefresh.add(btnApply);

		separator.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator);

		btnRefresh.setToolTipText("Refresh the Flex data manually");
		menuBar.add(btnRefresh);
		btnRefresh.setFocusable(false);


		//Action Listeners

		mntmListeningPort.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String newPort = (String) JOptionPane.showInputDialog(frmAmazonFlexChecker, "Please enter the port you want the server to listen on.", "Port", JOptionPane.QUESTION_MESSAGE, null, null, prefs.getInt("PORT", port));
				if(newPort==null){
				}
				else if(newPort.isEmpty()){
				}
				else{
					port=Integer.parseInt(newPort);
					prefs.putInt("PORT", port);
					try {
						prefs.flush();
					} catch (BackingStoreException e1) {
						JOptionPane.showMessageDialog(frmAmazonFlexChecker, "There was an error storing the values.  You may need\nto set them again the next time you run the program!", "Options Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(frmAmazonFlexChecker, "You should probably restart the program now to avoid\nit still listening for the next connection on the old port.", "Please Restart", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		btnRefresh.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("The refresh button was pressed");
			}
		});
		
		
		btnApply.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("The apply button was pressed: " + refreshIntervalTextField.getText() + " seconds.");
				
				//This next line simply closes the menu when you press apply.
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
				if(Integer.parseInt(refreshIntervalTextField.getText()) <= 5)
				{
					JOptionPane.showMessageDialog(frmAmazonFlexChecker, "Sorry, the refresh time must be at least 5 seconds to give it time to finish.", "Refresh Time too Short", JOptionPane.ERROR_MESSAGE);
					refreshIntervalTextField.setText(Integer.toString(refreshInterval));
				}
				else
				{
					prefs.putInt("RI", Integer.parseInt(refreshIntervalTextField.getText()));
					refreshInterval = Integer.parseInt(refreshIntervalTextField.getText());
				}
			}
		});
		
		
		chckbxmntmEnableAutorefresh.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				prefs.putBoolean("AR", chckbxmntmEnableAutorefresh.isSelected());
				autoRefresh = chckbxmntmEnableAutorefresh.isSelected();
			}
		});
	}
}
