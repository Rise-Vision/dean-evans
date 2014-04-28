package com.risedisplay.ems.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URI;

import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import com.risedisplay.ems.webserver.AppContextBuilder;
import com.risedisplay.ems.webserver.JettyServer;

import java.awt.Component;

public class MainForm {

	private static final int DEFAULT_PORT = 8080;
	
	static public int runningPort = DEFAULT_PORT;
	
	private JettyServer jettyServer;
	private JFrame frmRiseEmsData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		int argPort = DEFAULT_PORT;
		boolean argLogToFile = false;
				
		for (String arg: args) {
			
			if (arg.startsWith("--") && arg.contains("=")) {
			
				String[] keyval = arg.split("=");
				if (keyval.length == 2) {

					if (keyval[0].equals("--log")) {
			
						argLogToFile = keyval[1].equals("file");
					
					} else if (keyval[0].equals("--port")) {
						
						try {
							argPort = Integer.parseInt(keyval[1]);
						} catch (NumberFormatException ne) {
							argPort = DEFAULT_PORT;
						}
						
					}
				}
			} 
		}

		if (argLogToFile) {

			try {
				PrintStream fileStream = new PrintStream(new FileOutputStream(System.getProperty("user.dir") + "\\web\\emsservice.log", false));

				// Redirecting console output and runtime exceptions to file
				System.setOut(fileStream);
				System.setErr(fileStream);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		runningPort = argPort;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmRiseEmsData.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRiseEmsData = new JFrame();
		frmRiseEmsData.getContentPane().setBackground(Color.WHITE);
		frmRiseEmsData.setTitle("Rise Dean Evans EMS Data Service");
		frmRiseEmsData.setSize(450, 260);
		frmRiseEmsData.setLocationRelativeTo(null);
		frmRiseEmsData.setResizable(false);
		frmRiseEmsData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRiseEmsData.getContentPane().setLayout(new BorderLayout(0, 0));
		
					
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		
		contexts.setHandlers(new Handler[] { new AppContextBuilder().buildWebAppContext()});
		
		this.jettyServer = new JettyServer(runningPort);
		jettyServer.setHandler(contexts);
		
		try {
			jettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				if(jettyServer.isStarted()) {
					try {
						jettyServer.stop();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		},"Stop Jetty Hook"));
		
		JPanel panelButtons = new JPanel();
		frmRiseEmsData.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		
		JButton btnConfiguration = new JButton("Configuration");
		btnConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ConfigForm dialog = new ConfigForm();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		panelButtons.add(btnConfiguration);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panelButtons.add(btnQuit);
		
		JPanel panelTitle = new JPanel();
		panelTitle.setBackground(Color.WHITE);
		frmRiseEmsData.getContentPane().add(panelTitle, BorderLayout.CENTER);
		panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.Y_AXIS));
		
		JButton btnWeb = new JButton("New button");
		btnWeb.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnWeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI("http://www.risedisplay.com"));
					} catch (Exception ex) {
						// TODO: error handling
					}
				} else {
					// TODO: error handling
				}
			}
		});
		btnWeb.setBorderPainted(false);
		btnWeb.setOpaque(false);
		btnWeb.setFocusPainted(false);
		btnWeb.setContentAreaFilled(false);
		btnWeb.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnWeb.setBackground(Color.WHITE);
		btnWeb.setToolTipText("www.risevision.com");
		btnWeb.setBorder(null);
		btnWeb.setHorizontalAlignment(SwingConstants.CENTER);
		btnWeb.setText("<html><a href=\"http://www.risedisplay.com\">www.risedisplay.com</a>");
		panelTitle.add(btnWeb);
		
		
		JLabel lblRiseEmsData = new JLabel("<html><h1>Rise Dean Evans EMS Data Service</h1>");
		lblRiseEmsData.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRiseEmsData.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblRiseEmsData);
		
		JLabel lblVersion = new JLabel("Version 1.1.0.1");
		lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblVersion);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBackground(Color.WHITE);
		labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogo.setIcon(new ImageIcon(MainForm.class.getResource("/images/logo.jpg")));
		frmRiseEmsData.getContentPane().add(labelLogo, BorderLayout.NORTH);
	}

}
