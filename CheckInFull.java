package indProject;

import java.awt.*;
import java.util.Date;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import net.proteanit.sql.DbUtils;
import java.sql.Connection;

public class CheckInFull extends JFrame {
	JFrame mainPage;
	JPanel buttonOptions;
	String currentDate;
	String dueDate;
	static Connection conn = null;
	int row = 0;
	public static final Color VERY_LIGHT_YELLOW = new Color(255,255,204);
	public static final Color BRIGHT_YELLOW = new Color(255,204,51);
	public static final Color VERY_LIGHT_RED = new Color(255,102,102);
	public static final Color VERY_LIGHT_BLUE = new Color(51,153,255);
	
	JTable table;
	
	CheckInFull()
	{
		prepareGUI();
	}
	
	
	public static void main(String[] args)
	{
		CheckInFull checkInFull = new CheckInFull();
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
		GridBagLayout checkInButtonOptions=new GridBagLayout();
		checkInButtonOptions.columnWidths=new int[]{0,0};
		checkInButtonOptions.rowHeights = new int[]{0, 0, 0,0,0,0,0};
//		checkInButtonOptions.columnWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
//		checkInButtonOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		buttonOptions.setLayout(checkInButtonOptions);
		buttonOptions.setBackground(VERY_LIGHT_YELLOW);
		
		JLabel space1=new JLabel("  ",JLabel.CENTER);
		GridBagConstraints checkInSpace1=new GridBagConstraints();
		checkInSpace1.insets=new Insets(0,0,5,0);
		checkInSpace1.gridx=0;
		checkInSpace1.gridy=1;
		checkInSpace1.gridwidth=2;
		buttonOptions.add(space1, checkInSpace1);

		//Book ISBN
		JLabel lblIsbn=new JLabel("                 ISBN ",JLabel.LEFT);
		lblIsbn.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints checkInLabelIsbn=new GridBagConstraints();
		checkInLabelIsbn.insets=new Insets(0,0,5,0);
		checkInLabelIsbn.gridx=0;
		checkInLabelIsbn.gridy=2;
		buttonOptions.add(lblIsbn, checkInLabelIsbn);
		
		//Isbn Text Box
		JTextField isbnText = new JTextField();
		
		isbnText.setFont(new Font("Arial",Font.PLAIN,14));
		isbnText.setForeground(Color.black);
		GridBagConstraints checkInIsbnText=new GridBagConstraints();
		checkInIsbnText.fill=GridBagConstraints.HORIZONTAL;
		checkInIsbnText.insets=new Insets(0,0,5,0);
		checkInIsbnText.gridx=1;
		checkInIsbnText.gridy=2;
		buttonOptions.add(isbnText, checkInIsbnText);
		isbnText.setColumns(15);
		
		//Borrower Card No.
		JLabel lblcard=new JLabel("      Card Number   ",JLabel.RIGHT);
		lblcard.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints checkInLabelCard=new GridBagConstraints();
		checkInLabelCard.insets=new Insets(0,0,5,0);
		checkInLabelCard.gridx=0;
		checkInLabelCard.gridy=3;
		buttonOptions.add(lblcard, checkInLabelCard);
		
		//Card No.Text Box
		JTextField cardText = new JTextField();
	
		cardText.setFont(new Font("Arial",Font.PLAIN,14));
		cardText.setForeground(Color.black);
		GridBagConstraints checkInCardText =new GridBagConstraints();
		checkInCardText.fill=GridBagConstraints.HORIZONTAL;
		checkInCardText.insets=new Insets(0,0,5,0);
		checkInCardText.gridx=1;
		checkInCardText.gridy=3;
		buttonOptions.add(cardText, checkInCardText);
		cardText.setColumns(15);
		
		//Borrower
		JLabel lblBorrower=new JLabel("Borrower Name ",JLabel.RIGHT);
		lblBorrower.setFont(new Font("Arial",Font.BOLD,14));
		GridBagConstraints checkInLabelBorrower=new GridBagConstraints();
		checkInLabelBorrower.insets=new Insets(0,0,5,0);
		checkInLabelBorrower.gridx=0;
		checkInLabelBorrower.gridy=4;
		buttonOptions.add(lblBorrower, checkInLabelBorrower);
		
		//Borrower Text Box
		JTextField borrowerText = new JTextField();
		
		borrowerText.setFont(new Font("Arial",Font.PLAIN,14));
		borrowerText.setForeground(Color.black);
		GridBagConstraints checkInBorrowerText=new GridBagConstraints();
		checkInBorrowerText.fill=GridBagConstraints.HORIZONTAL;
		checkInBorrowerText.insets=new Insets(0,0,5,0);
		checkInBorrowerText.gridx=1;
		checkInBorrowerText.gridy=4;
		buttonOptions.add(borrowerText, checkInBorrowerText);
		isbnText.setColumns(15);
		
		
		JButton checkOut=new JButton("Check In");
		checkOut.setForeground(Color.black);
		checkOut.setBackground(BRIGHT_YELLOW);
		checkOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(isbnText.getText().equals("") && cardText.getText().equals("") && borrowerText.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please enter something in any the text box");
				}
				else{
				try {
	
				 CheckInFullSQL(isbnText.getText(),cardText.getText(),borrowerText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
		});
		GridBagConstraints checkInButtonCheckOut=new GridBagConstraints();
		checkInButtonCheckOut.fill=GridBagConstraints.HORIZONTAL;
		checkInButtonCheckOut.insets = new Insets(0, 0, 5, 0);
		checkInButtonCheckOut.gridx = 0;
		checkInButtonCheckOut.gridy = 5;
		checkInButtonCheckOut.gridwidth=2;
		buttonOptions.add(checkOut,checkInButtonCheckOut);
		
		JLabel space2=new JLabel("  ",JLabel.CENTER);
		GridBagConstraints checkInSpace2=new GridBagConstraints();
		checkInSpace2.insets=new Insets(0,0,5,0);
		checkInSpace2.gridx=0;
		checkInSpace2.gridy=6;
		checkInSpace2.gridwidth=2;
		buttonOptions.add(space2, checkInSpace2);
		
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
		GridBagConstraints checkInButtonClose=new GridBagConstraints();
		checkInButtonClose.fill=GridBagConstraints.HORIZONTAL;
		checkInButtonClose.insets = new Insets(0, 0, 5, 0);
		checkInButtonClose.gridx = 0;
		checkInButtonClose.gridy = 7;
		checkInButtonClose.anchor=GridBagConstraints.PAGE_END;
		checkInButtonClose.gridwidth=2;
		buttonOptions.add(close, checkInButtonClose);		

		
		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
	}
	void CheckInFullSQL(String isbn, String card, String borrower) throws SQLException
	{
		prepareGUI(isbn,card,borrower);
	}
	
	
	void prepareGUI(String isbn, String card, String borrower) throws SQLException
	{
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(500,500);
		mainPage.setLocation(20, 50);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout checkInButtonOptions=new GridBagLayout();
		checkInButtonOptions.columnWidths=new int[]{0,0};
		checkInButtonOptions.rowHeights = new int[]{0, 0, 0};
		checkInButtonOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		checkInButtonOptions.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		buttonOptions.setLayout(checkInButtonOptions);
		
		JButton close= new JButton("Close");
		close.setForeground(Color.black);
		close.setBackground(VERY_LIGHT_RED);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainPage.setVisible(false); 
				mainPage.dispose();
			}
		});
		
		JLayeredPane layeredPane=new JLayeredPane();
		GridBagConstraints checkInLayeredPane = new GridBagConstraints();
		checkInLayeredPane.insets = new Insets(0, 0, 5, 0);
		checkInLayeredPane.fill = GridBagConstraints.BOTH;
		checkInLayeredPane.gridx = 0;
		checkInLayeredPane.gridy = 0;
		buttonOptions.add(layeredPane, checkInLayeredPane);
		
		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setBounds(6,6,1200,600);
		layeredPane.add(scrollPane);
		
		table=new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evnt)
			{
				if(evnt.getClickCount() ==1 || evnt.getClickCount()==2)
				{
					int loanId=(int)table.getModel().getValueAt(table.rowAtPoint(evnt.getPoint()), 0);
						                
					try
					{
						conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
						
						String DateCalculation = "SELECT CURDATE()";
						Statement stmt1 = conn.createStatement();
		                ResultSet rs1 = stmt1.executeQuery(DateCalculation);
		                rs1.next();
		                currentDate = rs1.getString("CURDATE()");
						
		                String updateCheckIn = "UPDATE book_loans SET book_loans.Date_in = \'"+currentDate+"\' WHERE "
		                		+ "book_loans.Loan_id= \'"+loanId+"\' ";
						Statement stmt2=conn.createStatement();
						stmt2.executeUpdate(updateCheckIn);
						
						String enterFines = "SELECT * FROM book_loans WHERE book_loans.Loan_id= \'"+loanId+"\' "
								+ "AND book_loans.Due_date < book_loans.Date_in";
						Statement stmt3=conn.createStatement();
						ResultSet rs3 = stmt3.executeQuery(enterFines);
						
						if(rs3.next()){						
							
							JOptionPane.showMessageDialog(null, "Fines pending!");
							mainPage.setVisible(false);
							new FinesTable();							
						}
						else{
	                        JOptionPane.showMessageDialog(null, "Checked in successfully!!");
	                    	mainPage.dispose();
	                    	new CheckIn();
	                    }
					}
					catch(SQLException e)
					{
						System.out.println("Sql Error : "+e.getMessage());
					} 
				}
			}
		});
			
		//Close Button
		GridBagConstraints checkInButtonClose = new GridBagConstraints();
		checkInButtonClose.fill=GridBagConstraints.HORIZONTAL;
		checkInButtonClose.insets = new Insets(0, 0, 5, 0);
		checkInButtonClose.gridx = 0;
		checkInButtonClose.gridy = 2;
		checkInButtonClose.anchor=GridBagConstraints.PAGE_END;
		buttonOptions.add(close, checkInButtonClose);		
		try{		
			//jdbc connection to database
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
			Statement stmt=conn.createStatement();
			ResultSet rs= null;
			
			//Only one field given
			if(card.equals("") && borrower.equals(""))
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Isbn = \'"+isbn+"\' and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);
			}
			else if(isbn.equals("") && borrower.equals(""))
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Card_id = \'"+card+"\' and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);
			}
			else if(isbn.equals("") && card.equals(""))
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Card_id in "
						+ "(Select Card_id from proj1.borrower where Bname like \'%"+borrower+"%\') and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);   		
			}
			//Any two fields given
			else if(borrower.equals(""))
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Isbn = \'"+isbn+"\' "
						+ "and Card_id = \'"+card+"\' and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);
			}
			else if(isbn.equals(""))
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Card_id = \'"+card+"\' and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);
			}
			else if(card.equals(""))
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Isbn = \'"+isbn+"\' "
						+ "and Card_id in (Select Card_id from proj1.borrower where Bname like \'%"+borrower+"%\') and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);
			}
			//All three fields given
			else
			{
				String clickCheckIn = "Select Loan_id,Isbn,Card_id from book_loans where Isbn = \'"+isbn+"\' "
						+ "and Card_id="+card+" and Date_in is null";
				rs=stmt.executeQuery(clickCheckIn);
			}
		
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
			table.setEnabled(false);
			
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
			}
		
		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
		
	}

}
