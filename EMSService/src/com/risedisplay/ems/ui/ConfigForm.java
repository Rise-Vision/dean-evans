// Copyright © 2010 - June 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risedisplay.ems.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigForm extends JDialog {

	private static final long serialVersionUID = -375155387402102130L;

	private final JPanel contentPanel = new JPanel();
	private JTextField textURL;
	private JTextField textUsername;
	private JPasswordField textPassword;
	
	private Properties configFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConfigForm dialog = new ConfigForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConfigForm() {
		setTitle("EMS Service Configuration");
		setModal(true);
		setSize(575, 165);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblEmsServiceUrl = new JLabel("EMS Service URL");
		lblEmsServiceUrl.setBounds(10, 11, 99, 14);
		contentPanel.add(lblEmsServiceUrl);
		
		textURL = new JTextField();
		textURL.setBounds(133, 8, 416, 20);
		contentPanel.add(textURL);
		textURL.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(10, 36, 99, 14);
		contentPanel.add(lblUserName);
		
		textUsername = new JTextField();
		textUsername.setBounds(133, 33, 205, 20);
		contentPanel.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 61, 99, 14);
		contentPanel.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(133, 58, 205, 20);
		contentPanel.add(textPassword);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String emsUrl = textURL.getText();
						String emsUsername = textUsername.getText();
						@SuppressWarnings("deprecation")
						String emsPassword = textPassword.getText();
						
						if (emsUrl.isEmpty()) {
							JOptionPane.showMessageDialog(new JFrame(),
								    "EMS Service URL cannot be empty.",
								    "Error",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if (emsUsername.isEmpty()) {
							JOptionPane.showMessageDialog(new JFrame(),
								    "User name cannot be empty.",
								    "Error",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						configFile.setProperty("url", emsUrl);
						configFile.setProperty("username", emsUsername);
						configFile.setProperty("password", emsPassword);
						try {
							configFile.store(new FileOutputStream(System.getProperty("user.dir") + "\\web\\emsservice.conf"), null);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		configFile = new Properties();
		try {
			configFile.load(new FileInputStream(System.getProperty("user.dir") + "\\web\\emsservice.conf"));
			textURL.setText(configFile.getProperty("url"));
			textUsername.setText(configFile.getProperty("username"));
			textPassword.setText(configFile.getProperty("password"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
