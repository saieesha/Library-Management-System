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

public class CheckOutBook extends JFrame
{
	String in_dateOutChkOut = null;
	String in_dueDateChkOut = null;
	boolean bookAvailable = true;
	long count = 0;
	JFrame mainPage;
	JPanel buttonOptions;
	static Connection conn;
	public static final Color VERY_LIGHT_YELLOW = new Color(255,255,204);
	public static final Color BRIGHT_YELLOW = new Color(255,204,51);
	public static final Color VERY_LIGHT_RED = new Color(255,102,102);
	public static final Color VERY_LIGHT_BLUE = new Color(51,153,255);
	
	CheckOutBook()
	{
		prepareGUI();
	}
	
	public static void main(String[] args)
	{
		CheckOutBook checkOut=new CheckOutBook();
	}
	
	void prepareGUI()
	{
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(500,300);
		mainPage.setLocation(20, 50);
		mainPage.setMinimumSize(mainPage.getSize());
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout checkOutbuttonOptions=new GridBagLayout();
		checkOutbuttonOptions.columnWidths=new int[]{0,0};
		checkOutbuttonOptions.rowHeights = new int[]{0, 0, 0,0,0,0};
//		checkOutbuttonOptions.columnWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
//		checkOutbuttonOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		buttonOptions.setLayout(checkOutbuttonOptions);
		buttonOptions.setBackground(VERY_LIGHT_YELLOW);
		
		JLabel spaceOut = new JLabel("  ",JLabel.CENTER);
		GridBagConstraints checkOutspace1=new GridBagConstraints();
		checkOutspace1.insets=new Insets(0,0,5,0);
		checkOutspace1.gridx=0;
		checkOutspace1.gridy=1;
		checkOutspace1.gridwidth=2;
		buttonOptions.add(spaceOut, checkOutspace1);

		//Book ISBN
		JLabel labelIsbn=new JLabel("     ISBN ",JLabel.LEFT);
		labelIsbn.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints checkOutLabelIsbn=new GridBagConstraints();
		checkOutLabelIsbn.insets=new Insets(0,0,5,0);
		checkOutLabelIsbn.gridx=0;
		checkOutLabelIsbn.gridy=2;
		//checkOutLabelIsbn.gridwidth=2;
		buttonOptions.add(labelIsbn, checkOutLabelIsbn);
		
		//Isbn Text Box
		JTextField isbnText = new JTextField();
		
		isbnText.setFont(new Font("Arial",Font.PLAIN,14));
		isbnText.setForeground(Color.black);
		GridBagConstraints checkOutIsbnText=new GridBagConstraints();
		checkOutIsbnText.fill=GridBagConstraints.HORIZONTAL;
		checkOutIsbnText.insets=new Insets(0,0,5,0);
		checkOutIsbnText.gridx=1;
		checkOutIsbnText.gridy=2;
		//checkOutIsbnText.gridwidth=2;
		buttonOptions.add(isbnText, checkOutIsbnText);
		//buttonOptions.add(searchText);
		isbnText.setColumns(15);
		
		//Borrower Card No.
		JLabel CKlblcard=new JLabel("Card No ",JLabel.LEFT);
		CKlblcard.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints checkOutLblcard=new GridBagConstraints();
		checkOutLblcard.insets=new Insets(0,0,5,0);
		checkOutLblcard.gridx=0;
		checkOutLblcard.gridy=3;
		//checkOutLabelIsbn.gridwidth=2;
		buttonOptions.add(CKlblcard, checkOutLblcard);
		
		//Card No.Text Box
		JTextField CkCardText = new JTextField();
	
		CkCardText.setFont(new Font("Arial",Font.PLAIN,14));
		CkCardText.setForeground(Color.black);
		//CkCardText.setSize(200, 50);;
		GridBagConstraints checkOutCardText =new GridBagConstraints();
		checkOutCardText.fill=GridBagConstraints.HORIZONTAL;
		checkOutCardText.insets=new Insets(0,0,5,0);
		checkOutCardText.gridx=1;
		checkOutCardText.gridy=3;
		//checkOutIsbnText.gridwidth=2;
		buttonOptions.add(CkCardText, checkOutCardText);
		//buttonOptions.add(searchText);
		CkCardText.setColumns(15);
		
		JButton checkOut = new JButton("Check Out");
		checkOut.setForeground(Color.black);
		checkOut.setBackground(BRIGHT_YELLOW);
		//checkOut.setLocation(50, 50);
		checkOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{if(isbnText.getText().equals("") || CkCardText.getText().equals("") )
			{
				JOptionPane.showMessageDialog(null, "Please fill in the required fields");
			}
			else{
				try{
					
					String inIsbn = isbnText.getText();
					//int cardId = Integer.parseInt(CkCardText.getText());
					String cardId = CkCardText.getText();
					
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
					
					Statement stmt1=conn.createStatement();
					ResultSet rs1 = stmt1.executeQuery("SELECT * FROM book WHERE Isbn='"+inIsbn+"';");
					Statement stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery("SELECT * FROM borrower WHERE Card_id='"+cardId+"';");
					
					if(rs1.next()&&rs2.next()) {
					
					String currentDate = "SELECT CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY)";
					Statement stmt3 = conn.createStatement();
	                ResultSet rs3 = stmt3.executeQuery(currentDate);
	                while(rs3.next()) {
	                in_dateOutChkOut = rs3.getString(1);
	                in_dueDateChkOut = rs3.getString(2);
	                System.out.println(in_dateOutChkOut + "\t"); 
	                System.out.println(in_dueDateChkOut + "\t"); 
	                System.out.println();
	                }
	                
                    //////////////////////////////////////SSN COUNT//////////////////////////////////////////
                    String ssnMinCount = "SELECT count(*) as counter FROM book_loans where "
                    		+ "Date_in IS NULL AND Card_id = '"+cardId+"';";
                    Statement stmt4 = conn.createStatement();
                    ResultSet rs4 = stmt4.executeQuery(ssnMinCount);
                    while(rs4.next()) {
                    count = rs4.getLong("counter");
                    }
                    System.out.println("count: " + count);   
                    
                    ////////////////////////////////////// BOOK AVAILABLE OR NOT/////////////////////////////
                    String bookAvailableOrNot = "SELECT Isbn as isbnExists FROM book_loans where "
                    		+ "Isbn = \'"+inIsbn+"\' AND Date_in IS NULL";
                    Statement stmt7 = conn.createStatement();
                    ResultSet rs7 = stmt7.executeQuery(bookAvailableOrNot);
                    while(rs7.next()) {
                    bookAvailable = false;
                    }
                    System.out.println("bookAvailable: " + bookAvailable); 
                    
                    if(bookAvailable) {    
                    if(count >= 3) {
                    	JOptionPane.showMessageDialog(null, "The count of books borrowed has reached its maximum!!");
						isbnText.setText(""); 
						CkCardText.setText("");
                    }else{
                        ///////////////////////////////////CHECK FINE NOT PAID///////////////////////////////////
                        String fineNotPaid = "SELECT * FROM book_loans, fines "
          		                + "WHERE Card_id = '\"+cardId+\"' AND "
          		                + "book_loans.Loan_id = fines.Loan_id AND paid = 0";
                        Statement stmt5 = conn.createStatement();
                        ResultSet rs5 = stmt5.executeQuery(fineNotPaid);
                        
                        if(rs5.next()) {
                    	JOptionPane.showMessageDialog(null, "The fine amount for previous books borrowed has not been paid!!");
						isbnText.setText(""); 
						CkCardText.setText("");
                        }else {
                        	String checkOutBook = "INSERT INTO book_loans (Isbn, Card_id, Date_out, Due_date, Date_in)"
                            		+ "VALUES(\'"+inIsbn+"\', '"+cardId+"\', \'"+in_dateOutChkOut+"\', '"+in_dueDateChkOut+"\', null)";
                            Statement stmt6 = conn.createStatement();
                            stmt6.executeUpdate(checkOutBook);
                            JOptionPane.showMessageDialog(null, "Check Out Done");
							isbnText.setText(""); 
							CkCardText.setText("");
                        }
                    }
				}
                    else {
    					JOptionPane.showMessageDialog(null, "Book unavailable. Select another book!");
    					isbnText.setText(""); 
    					CkCardText.setText("");
    				}
                    
                    
				}else {
					JOptionPane.showMessageDialog(null, "Incorrect Isbn Or Card Id");
					isbnText.setText(""); 
					CkCardText.setText("");
				}
				
		    }
			catch(SQLException ex) {
				System.out.println("Error in connection: " + ex.getMessage());
			}
			
			}
			}
		});
		GridBagConstraints gbc_btnCheckOutBook=new GridBagConstraints();
		gbc_btnCheckOutBook.fill=GridBagConstraints.HORIZONTAL;
		gbc_btnCheckOutBook.insets = new Insets(0, 0, 5, 0);
		gbc_btnCheckOutBook.gridx = 0;
		gbc_btnCheckOutBook.gridy = 4;
		gbc_btnCheckOutBook.gridwidth=2;
		buttonOptions.add(checkOut,gbc_btnCheckOutBook);
		
		JLabel space2=new JLabel("  ",JLabel.CENTER);
		//searchLabel.setFont(new F);
		GridBagConstraints gbc_space2=new GridBagConstraints();
		gbc_space2.insets=new Insets(0,0,5,0);
		gbc_space2.gridx=0;
		gbc_space2.gridy=5;
		gbc_space2.gridwidth=2;
		buttonOptions.add(space2, gbc_space2);
		
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
		GridBagConstraints gbc_btnClose=new GridBagConstraints();
		gbc_btnClose.fill=GridBagConstraints.HORIZONTAL;
		gbc_btnClose.insets = new Insets(0, 0, 5, 0);
		gbc_btnClose.gridx = 0;
		gbc_btnClose.gridy = 6;
		gbc_btnClose.anchor=GridBagConstraints.PAGE_END;
		gbc_btnClose.gridwidth=2;
		buttonOptions.add(close, gbc_btnClose);		

		
		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
	}
}