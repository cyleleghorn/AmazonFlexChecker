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
		
		JMenuBar menuBar = new JMenuBar();
		frmAmazonFlexChecker.setJMenuBar(menuBar);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenuItem loginInfoMenuItem = new JMenuItem("Amazon Flex Account Information");
		loginInfoMenuItem.setToolTipText("Enter your login information for Amazon Flex so the program can log in and check for available time blocks.");
		mnOptions.add(loginInfoMenuItem);
		
		JMenuItem mntmListeningPort = new JMenuItem("Listening Port");
		mnOptions.add(mntmListeningPort);
		
		JMenu mntmAutorefresh = new JMenu("Auto-Refresh");
		mnOptions.add(mntmAutorefresh);
		
		JCheckBoxMenuItem chckbxmntmEnableAutorefresh = new JCheckBoxMenuItem("Enable Auto-Refresh");
		chckbxmntmEnableAutorefresh.setSelected(true);
		mntmAutorefresh.add(chckbxmntmEnableAutorefresh);
		
		JSeparator separator_1 = new JSeparator();
		mntmAutorefresh.add(separator_1);
		
		JLabel lblRefreshIntervalIn = new JLabel("Refresh Interval in Seconds");
		mntmAutorefresh.add(lblRefreshIntervalIn);
		
		refreshIntervalTextField = new JTextField();
		refreshIntervalTextField.setText(Integer.toString(prefs.getInt("RI", refreshInterval)));
		mntmAutorefresh.add(refreshIntervalTextField);
		refreshIntervalTextField.setColumns(10);
		
		JButton btnApply = new JButton("Apply");
		mntmAutorefresh.add(btnApply);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setToolTipText("Refresh the Flex data manually");
		menuBar.add(btnRefresh);
		btnRefresh.setFocusable(false);
		
		
		
		
		
		
		
		
		//Action Listeners
		
		mntmListeningPort.addActionListener(new ActionListener() {
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
	}

}
