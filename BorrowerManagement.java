package indProject;

import java.awt.*;
import java.awt.event.*;
import java.security.Timestamp;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import net.proteanit.sql.DbUtils;

import java.sql.Connection;

public class BorrowerManagement extends JFrame
{
	long ssnCount = 0;
	long cardIdCount = 0;
	String inCardId;
	JFrame mainPage;
	JPanel buttonOptions;
	static Connection conn;
	
	public static final Color VERY_LIGHT_YELLOW = new Color(255,255,204);
	public static final Color BRIGHT_YELLOW = new Color(255,204,51);
	public static final Color VERY_LIGHT_RED = new Color(255,102,102);
	public static final Color VERY_LIGHT_BLUE = new Color(51,153,255);
	
	BorrowerManagement()
	{
		prepareGUI();
	}
	
	public static void main(String[] args)
	{
		BorrowerManagement newBorrower=new BorrowerManagement();
		//mainPage.show();
	}
	
	void prepareGUI()
	{
		//System.out.println("enter borrower GUI");
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(600,600);
		mainPage.setLocation(20, 50);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout borrowerButtonOptions=new GridBagLayout();
		borrowerButtonOptions.columnWidths=new int[]{0,0,0,0};
		borrowerButtonOptions.rowHeights = new int[]{0, 0, 0,0,0,0,0,0};
//		borrowerButtonOptions.columnWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
//		borrowerButtonOptions.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		buttonOptions.setLayout(borrowerButtonOptions);
		buttonOptions.setBackground(VERY_LIGHT_YELLOW);
		
		
		//Header
		JLabel lblHeader=new JLabel("New Borrower",JLabel.CENTER);
		lblHeader.setFont(new Font("Arial",Font.BOLD,20));
		GridBagConstraints borrowerLabelHeader=new GridBagConstraints();
		borrowerLabelHeader.insets=new Insets(0,0,5,0);
		borrowerLabelHeader.gridx=0;
		borrowerLabelHeader.gridy=0;
		borrowerLabelHeader.gridwidth=4;
		buttonOptions.add(lblHeader, borrowerLabelHeader);

		//First Name
		JLabel lblfirstName=new JLabel("First Name ",JLabel.LEFT);
		lblfirstName.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelFirstName=new GridBagConstraints();
		borrowerLabelFirstName.insets=new Insets(0,0,5,0);
		borrowerLabelFirstName.gridx=0;
		borrowerLabelFirstName.gridy=1;
		buttonOptions.add(lblfirstName, borrowerLabelFirstName);
		
		//First Name Text Box
		JTextField firstNameText = new JTextField();
		
		firstNameText.setFont(new Font("Arial",Font.PLAIN,14));
		firstNameText.setForeground(Color.black);
		GridBagConstraints borrowerFirstNameText=new GridBagConstraints();
		borrowerFirstNameText.fill=GridBagConstraints.HORIZONTAL;
		borrowerFirstNameText.insets=new Insets(0,0,5,0);
		borrowerFirstNameText.gridx=1;
		borrowerFirstNameText.gridy=1;
		buttonOptions.add(firstNameText, borrowerFirstNameText);
		firstNameText.setColumns(15);
		
		//Last Name
		JLabel lbllastName=new JLabel("Last Name ",JLabel.LEFT);
		lbllastName.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelLastName=new GridBagConstraints();
		borrowerLabelLastName.insets=new Insets(0,0,5,0);
		borrowerLabelLastName.gridx=2;
		borrowerLabelLastName.gridy=1;
		buttonOptions.add(lbllastName, borrowerLabelLastName);
		
		//Last NameText Box
		JTextField lastNameText = new JTextField();
	
		lastNameText.setFont(new Font("Arial",Font.PLAIN,14));
		lastNameText.setForeground(Color.black);
		GridBagConstraints borrowerLastNameText =new GridBagConstraints();
		borrowerLastNameText.fill=GridBagConstraints.HORIZONTAL;
		borrowerLastNameText.insets=new Insets(0,0,5,0);
		borrowerLastNameText.gridx=3;
		borrowerLastNameText.gridy=1;
		buttonOptions.add(lastNameText, borrowerLastNameText);
		lastNameText.setColumns(15);
		
		//SSN
		JLabel lblSsn=new JLabel("          SSN ",JLabel.LEFT);
		lblSsn.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelSsn=new GridBagConstraints();
		borrowerLabelSsn.insets=new Insets(0,0,5,0);
		borrowerLabelSsn.gridx=0;
		borrowerLabelSsn.gridy=2;
		buttonOptions.add(lblSsn, borrowerLabelSsn);
		
		//Ssn Text Box
		JTextField ssnText = new JTextField();
		
		ssnText.setFont(new Font("Arial",Font.PLAIN,14));
		ssnText.setForeground(Color.black);
		GridBagConstraints borrowerSsnText=new GridBagConstraints();
		borrowerSsnText.fill=GridBagConstraints.HORIZONTAL;
		borrowerSsnText.insets=new Insets(0,0,5,0);
		borrowerSsnText.gridx=1;
		borrowerSsnText.gridy=2;
		buttonOptions.add(ssnText, borrowerSsnText);
		ssnText.setColumns(15);
		ssnText.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	           char c = e.getKeyChar();
	           if (!(!Character.isLetter(c) ||
	              (c == KeyEvent.VK_BACK_SPACE) ||
	              (c == KeyEvent.VK_DELETE))) {
	                e.consume();
	              }
	           if (ssnText.getText().length() >= 9 )
	        	   e.consume();
	         }
	       });
		
		//Email
		JLabel lblEmail=new JLabel("       Email ",JLabel.LEFT);
		lblEmail.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelEmail=new GridBagConstraints();
		borrowerLabelEmail.insets=new Insets(0,0,5,0);
		borrowerLabelEmail.gridx=2;
		borrowerLabelEmail.gridy=2;
		buttonOptions.add(lblEmail, borrowerLabelEmail);
		
		//Email Text Box
		JTextField emailText = new JTextField();
	
		emailText.setFont(new Font("Arial",Font.PLAIN,14));
		emailText.setForeground(Color.black);
		GridBagConstraints borrowerEmailText =new GridBagConstraints();
		borrowerEmailText.fill=GridBagConstraints.HORIZONTAL;
		borrowerEmailText.insets=new Insets(0,0,5,0);
		borrowerEmailText.gridx=3;
		borrowerEmailText.gridy=2;
		buttonOptions.add(emailText, borrowerEmailText);
		emailText.setColumns(15);
		
		//Address
		JLabel lblAddress=new JLabel("    Address ",JLabel.LEFT);
		lblAddress.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelAddress=new GridBagConstraints();
		borrowerLabelAddress.insets=new Insets(0,0,5,0);
		borrowerLabelAddress.gridx=0;
		borrowerLabelAddress.gridy=3;
		buttonOptions.add(lblAddress, borrowerLabelAddress);
		
		//Address Text Box
		JTextField addressText = new JTextField();
		
		addressText.setFont(new Font("Arial",Font.PLAIN,14));
		addressText.setForeground(Color.black);
		GridBagConstraints borrowerAddressText=new GridBagConstraints();
		borrowerAddressText.fill=GridBagConstraints.HORIZONTAL;
		borrowerAddressText.insets=new Insets(0,0,5,0);
		borrowerAddressText.gridx=1;
		borrowerAddressText.gridy=3;
		buttonOptions.add(addressText, borrowerAddressText);
		addressText.setColumns(15);

		//City
		JLabel lblCity=new JLabel("         City ",JLabel.LEFT);
		lblCity.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelCity=new GridBagConstraints();
		borrowerLabelCity.insets=new Insets(0,0,5,0);
		borrowerLabelCity.gridx=2;
		borrowerLabelCity.gridy=3;
		buttonOptions.add(lblCity, borrowerLabelCity);
		
		//City Text Box
		JTextField cityText = new JTextField();
		
		cityText.setFont(new Font("Arial",Font.PLAIN,14));
		cityText.setForeground(Color.black);
		GridBagConstraints borrowerCityText=new GridBagConstraints();
		borrowerCityText.fill=GridBagConstraints.HORIZONTAL;
		borrowerCityText.insets=new Insets(0,0,5,0);
		borrowerCityText.gridx=3;
		borrowerCityText.gridy=3;
		buttonOptions.add(cityText, borrowerCityText);
		cityText.setColumns(15);
		
		//State
		JLabel lblState=new JLabel("        State ",JLabel.LEFT);
		lblState.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelState=new GridBagConstraints();
		borrowerLabelState.insets=new Insets(0,0,5,0);
		borrowerLabelState.gridx=0;
		borrowerLabelState.gridy=4;
		buttonOptions.add(lblState, borrowerLabelState);
		
		//State Text Box
		JTextField stateText = new JTextField();
	
		stateText.setFont(new Font("Arial",Font.PLAIN,14));
		stateText.setForeground(Color.black);
		GridBagConstraints borrowerStateText =new GridBagConstraints();
		borrowerStateText.fill=GridBagConstraints.HORIZONTAL;
		borrowerStateText.insets=new Insets(0,0,5,0);
		borrowerStateText.gridx=1;
		borrowerStateText.gridy=4;
		buttonOptions.add(stateText, borrowerStateText);
		stateText.setColumns(15);
		
		//Phone
		JLabel lblPhone=new JLabel("      Phone ",JLabel.LEFT);
		lblPhone.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints borrowerLabelPhone=new GridBagConstraints();
		borrowerLabelPhone.insets=new Insets(0,0,5,0);
		borrowerLabelPhone.gridx=2;
		borrowerLabelPhone.gridy=4;
		buttonOptions.add(lblPhone, borrowerLabelPhone);
		
		//Phone Text Box
		JTextField phoneText = new JTextField();
	
		phoneText.setFont(new Font("Arial",Font.PLAIN,14));
		phoneText.setForeground(Color.black);
		GridBagConstraints borrowerPhoneText =new GridBagConstraints();
		borrowerPhoneText.fill=GridBagConstraints.HORIZONTAL;
		borrowerPhoneText.insets=new Insets(0,0,5,0);
		borrowerPhoneText.gridx=3;
		borrowerPhoneText.gridy=4;
		buttonOptions.add(phoneText, borrowerPhoneText);
		phoneText.setColumns(15);
		phoneText.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	           char c = e.getKeyChar();
	           if (!(!Character.isLetter(c) ||
	              (c == KeyEvent.VK_BACK_SPACE) ||
	              (c == KeyEvent.VK_DELETE))) {
	                e.consume();
	              }
	           if (phoneText.getText().length() >= 10 )
	        	   e.consume();
	         }
	       });
		

		JLabel space1=new JLabel("  ",JLabel.CENTER);
		GridBagConstraints borrowerSpace1=new GridBagConstraints();
		borrowerSpace1.insets=new Insets(0,0,5,0);
		borrowerSpace1.gridx=0;
		borrowerSpace1.gridy=5;
		borrowerSpace1.gridwidth=4;
		buttonOptions.add(space1, borrowerSpace1);

		//Add New Borrower Button
		JButton newBorrower=new JButton("Add");
		newBorrower.setForeground(Color.black);
		newBorrower.setBackground(BRIGHT_YELLOW);
		newBorrower.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try{
					String firstName=firstNameText.getText();
					String lastName=lastNameText.getText();
					String ssn=ssnText.getText();
					String email=emailText.getText();
					String address=addressText.getText();
					String city=cityText.getText();
					String state=stateText.getText();
					String phone=phoneText.getText();
					
					if(firstNameText.getText().equals("") && lastNameText.getText().equals("") && ssnText.getText().equals("")&& emailText.getText().equals("") && addressText.getText().equals("") && cityText.getText().equals("") && stateText.getText().equals("") && phoneText.getText().equals("") )
					{
						JOptionPane.showMessageDialog(null, "The fields cannot be empty");
					}	
					
					if(ssn.length() < 9)
					{
						JOptionPane.showMessageDialog(null, "SSN Incorrect");
						ssnText.setText("");
					}

					if(phone.length() != 0)
					{
						//System.out.println("entered if " );
						if(phone.length() < 10)
						{
						JOptionPane.showMessageDialog(null, "Phone Number Incorrect");
						phoneText.setText("");
						}
					}
					    //System.out.println("entered else aslo " ); 
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
						
						String ssnPresentOrNot = "SELECT COUNT(Ssn) as Ssn_count from borrower where Ssn='"+ssn+"'";
						Statement stmt1 = conn.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(ssnPresentOrNot);
                        rs1.next();
                        //ssnCount = rs1.getLong("Ssn_count");
                        //System.out.println("ssn count: " + ssnCount);
						if(ssnCount > 0)
						{
							JOptionPane.showMessageDialog(null, "The borrower already exists in the system");
							ssnText.setText("");
						}
						/////////////////
						else
						{
							/////////////////////// Generate New Card ID for New Borrower /////////////////////
							String CardIdcount = "SELECT COUNT(Card_id) as totalCount from borrower";
							Statement stmt2 = conn.createStatement();
			                ResultSet rs2 = stmt2.executeQuery(CardIdcount);
			                rs2.next();
			                cardIdCount = rs2.getLong("totalCount") + 1;
			                //System.out.println("Total Count: " + cardIdCount);
			                rs2.close();
			                String zerosCardId = String.format("%06d", cardIdCount);
			                inCardId = "ID" + zerosCardId;
			                //System.out.println("New Card Id: " + inCardId);
			                
			                ///////////////////////// Insert into Borrowers Table /////////////////////////////
			                
			              String insertBorrower = "INSERT INTO borrower(Card_id, Ssn, Bname, Address, Phone) "
			            		   		+ "VALUES(\'"+inCardId+"\', CONCAT(SUBSTR(\'"+ssn+"\',1,3), '-', SUBSTR(\'"+ssn+"\',4,3), '-', SUBSTR(\'"+ssn+"\',7,4)), "
			            		   		+ "\'"+firstName+" "+lastName+"\', \'"+address+", "+city+", "+state+"\', "
			            		   		+ "CONCAT('(', SUBSTR(\'"+phone+"\',1,3), ') ', SUBSTR(\'"+phone+"\',4,3), '-', SUBSTR(\'"+phone+"\',7,4)))";  
						  Statement stmt3=conn.createStatement();
						  stmt3.executeUpdate(insertBorrower);
						  JOptionPane.showMessageDialog(null, "Card Id of new borrower is : " + inCardId);
						  
						    firstNameText.setText("");
							lastNameText.setText("");
							ssnText.setText("");
							emailText.setText("");
							addressText.setText("");
							cityText.setText("");
							stateText.setText("");
							phoneText.setText("");
						}
						///////////////////
					System.out.println("New Borrower created successfully!!");
					
				}
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("Error in connection: " + e1.getMessage());
					}
					
					
					}
					
				
			
		});
		GridBagConstraints borrowerNewBorrower=new GridBagConstraints();
		borrowerNewBorrower.fill=GridBagConstraints.HORIZONTAL;
		borrowerNewBorrower.insets = new Insets(0, 0, 5, 0);
		borrowerNewBorrower.gridx = 0;
		borrowerNewBorrower.gridy = 6;
		borrowerNewBorrower.gridwidth=4;
		buttonOptions.add(newBorrower,borrowerNewBorrower);
		
		JLabel space2=new JLabel("  ",JLabel.CENTER);
		GridBagConstraints borrowerSpace2=new GridBagConstraints();
		borrowerSpace2.insets=new Insets(0,0,5,0);
		borrowerSpace2.gridx=0;
		borrowerSpace2.gridy=7;
		borrowerSpace2.gridwidth=4;
		buttonOptions.add(space2, borrowerSpace2);
		
		//Close Button
		JButton close=new JButton("Back");
		close.setForeground(Color.black);
		close.setBackground(VERY_LIGHT_RED);
		close.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				mainPage.setVisible(false);
				new MainPage();
			}
		});
		GridBagConstraints borrowerButtonClose=new GridBagConstraints();
		borrowerButtonClose.fill=GridBagConstraints.HORIZONTAL;
		borrowerButtonClose.insets = new Insets(0, 0, 5, 0);
		borrowerButtonClose.gridx = 0;
		borrowerButtonClose.gridy = 8;
		borrowerButtonClose.anchor=GridBagConstraints.PAGE_END;
		borrowerButtonClose.gridwidth=4;
		buttonOptions.add(close, borrowerButtonClose);		

		
		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
	}
}