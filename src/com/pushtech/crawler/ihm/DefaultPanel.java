package com.pushtech.crawler.ihm;
import java.awt.BorderLayout;
import static com.pushtech.crawler.logging.LoggingHelper.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.pushtech.crawler.launcher.Crawler;

public class DefaultPanel extends JPanel {

	private Thread crawlThread;
	
	private CustomJButton launch;
	private CustomJButton cancel;
	private CustomJTextField urlField;
	private CustomJTextField dataBaseLinkField;
	private CustomJTextField dataBaseUserField;
	private CustomJTextField dataBaseUserPasswordField;
	private static final Dimension BUTTON_SIZE = new Dimension(200, 25);
	private static final Dimension TEXT_FIELD_SIZE = new Dimension(400, 25);

//	private static final Logger logger = Logger.getLogger(DefaultPanel.class);
	
	public DefaultPanel() {
		super();

		this.setLayout(new BorderLayout());

		JPanel labelPanel = new JPanel();
		GridLayout labelLayout = new GridLayout(4, 1);
		labelPanel.setLayout(labelLayout);

		CustomJLabel urlLabel = new CustomJLabel("Url to crawl");
		labelPanel.add(urlLabel);

		CustomJLabel dataBaseLink = new CustomJLabel("Data base link");
		labelPanel.add(dataBaseLink);

		CustomJLabel dataBaseUser = new CustomJLabel("Data base user");
		labelPanel.add(dataBaseUser);

		CustomJLabel dataBaseUserPassWord = new CustomJLabel("User password");
		labelPanel.add(dataBaseUserPassWord);

		this.add(labelPanel, BorderLayout.WEST);

		// ----------------------------------------------------------------------------------------------

		JPanel centerContainer = new JPanel();
		centerContainer.setLayout(new GridLayout(4, 1));

		urlField = new CustomJTextField("", TEXT_FIELD_SIZE);
		centerContainer.add(urlField);

		dataBaseLinkField = new CustomJTextField("", TEXT_FIELD_SIZE);
		centerContainer.add(dataBaseLinkField);

		dataBaseUserField = new CustomJTextField("", TEXT_FIELD_SIZE);
		centerContainer.add(dataBaseUserField);

		dataBaseUserPasswordField = new CustomJTextField("", TEXT_FIELD_SIZE);
		centerContainer.add(dataBaseUserPasswordField);

		this.add(centerContainer, BorderLayout.CENTER);

		// --------------------------------------------------------------------------------------------
		/**
		 * --------------------------------------------------------------------
		 * -------
		 **/
		JPanel userActionPanel = new JPanel();

		initLaunchButton();
		userActionPanel.add(launch);

		initCancelButton();
		userActionPanel.add(cancel);

		this.add(userActionPanel, BorderLayout.SOUTH);
	}

	private void initCancelButton() {
		cancel = new CustomJButton("Cancel", BUTTON_SIZE);
		cancel.getComponent().setEnabled(false);
		((JButton) cancel.getComponent())
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						launch.getComponent().setEnabled(true);
						cancel.getComponent().setEnabled(false);
						
						stopThread(crawlThread);
						
						urlField.getComponent().setEnabled(true);
						dataBaseLinkField.getComponent().setEnabled(true);
						dataBaseUserField.getComponent().setEnabled(true);
						dataBaseUserPasswordField.getComponent().setEnabled(true);
					}
				});
	}

	private void initLaunchButton() {
		launch = new CustomJButton("Launch", BUTTON_SIZE);
		launch.getComponent().setEnabled(true);
		((JButton) launch.getComponent())
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {

						String linkToCrawl = ((JTextField) urlField
								.getComponent()).getText();
						String dataBaseLink = ((JTextField) dataBaseLinkField
								.getComponent()).getText();
						String dataBaseUser = ((JTextField) dataBaseUserField
								.getComponent()).getText();
						String userPassWord = ((JTextField) dataBaseUserPasswordField
								.getComponent()).getText();

						// ceci n'est pas tres bien, envisager le changer
						// ulterieurement
						FormHandler.validateForm(linkToCrawl, dataBaseLink,
								dataBaseUser, userPassWord);
						
						 crawlThread = new Thread(new Crawler());
						launch.getComponent().setEnabled(false);
						cancel.getComponent().setEnabled(true);
						urlField.getComponent().setEnabled(false);
						dataBaseLinkField.getComponent().setEnabled(false);
						dataBaseUserField.getComponent().setEnabled(false);
						dataBaseUserPasswordField.getComponent().setEnabled(false);
						startThread(crawlThread);
					}
				});
	}
	
	private void startThread(Thread t){
		t.start();
//		System.out.println("Thread started "+t.getName());
		logger.info("Thread started "+t.getName());
	}
	
	private void stopThread(Thread t){
		t.stop();
//		System.out.println("Thread stoped "+t.getName());
		logger.info("Thread stoped "+t.getName());
	}
	
	private void pauseThread(Thread t){
		logger.info("Thread waiting "+t.getName());
	}
}
