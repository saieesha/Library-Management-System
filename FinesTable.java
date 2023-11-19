package indProject;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.sql.Connection;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.border.*;

public class FinesTable extends JFrame
{
	String currentDate;
	String yesLoanId;
	boolean LoanId_Exists;
	String outFineCardId;
	double allFineAmt;
	
	String outPaidCardId;
	double paid_FineAmt;
	
	int loan_id=0;
	
	JFrame mainPage;
	JPanel buttonOptions;
	JTable table;
	static Connection conn = null;
	
	public static final Color VERY_LIGHT_YELLOW = new Color(255,255,204);
	public static final Color BRIGHT_YELLOW = new Color(255,204,51);
	public static final Color VERY_LIGHT_RED = new Color(255,102,102);
	public static final Color VERY_LIGHT_BLUE = new Color(51,153,255);
	
	FinesTable()
	{
		prepareGUI();
	}
		
	public static void main(String[] args)
	{
		FinesTable fines=new FinesTable();
	}
	
	void prepareGUI()
	{
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(500,300);
		mainPage.setLocation(20, 50);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout finesButtonOptions=new GridBagLayout();
		finesButtonOptions.columnWidths=new int[]{0,0,0};
		finesButtonOptions.rowHeights = new int[]{0, 0, 0,0,0,0};
		finesButtonOptions.columnWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};
		finesButtonOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		buttonOptions.setLayout(finesButtonOptions);
		buttonOptions.setBackground(VERY_LIGHT_YELLOW);
		
		//update Fines table
		
		
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
			
			String DateCalculation = "SELECT CURDATE()";
			Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(DateCalculation);
            rs1.next();
            currentDate = rs1.getString("CURDATE()");
            
            String LoanIdExists = "SELECT Loan_id as b, "
             		+ "CASE WHEN Loan_id NOT IN(SELECT Loan_id from fines) THEN FALSE "
             		+ "WHEN Loan_id IN(SELECT Loan_id from fines) THEN TRUE "
             		+ "END AS Loan_id_exists "
             		+ "FROM book_loans";
             Statement stmt2 = conn.createStatement();
             ResultSet rs2 = stmt2.executeQuery(LoanIdExists);
             while(rs2.next()) {
            	 yesLoanId = rs2.getString("b");
            	 LoanId_Exists = rs2.getBoolean("Loan_id_exists");
                 //System.out.print(LoanId_Exists + "\t");
                 //System.out.print(yesLoanId + "\t");
                 //System.out.println();
            	 if(LoanId_Exists) {
            		 String updatePaidAmt = "UPDATE fines JOIN book_loans on fines.Loan_id = book_loans.Loan_id "
            		 		+ "SET Fine_amt = IF(fines.Paid = TRUE, DATEDIFF(book_loans.Date_in, "
            		 		+ "book_loans.Due_date) * 0.25, DATEDIFF(CURDATE(), book_loans.Due_date) * 0.25) "
            		 		+ "WHERE book_loans.Loan_id = \'"+yesLoanId+"\' ";
            		 Statement stmt3 = conn.createStatement();
                     stmt3.executeUpdate(updatePaidAmt);
                 } else {
                	 String insertFinesRecord = "INSERT INTO fines (Loan_id, Fine_amt, Paid) "
                	 		+ "SELECT * FROM (SELECT book_loans.Loan_id, DATEDIFF(CURDATE(), book_loans.Due_date) * 0.25, "
                	 		+ "FALSE as Paid "
                	 		+ "from book_loans LEFT JOIN fines "
                	 		+ "on fines.Loan_id  = book_loans.Loan_id "
                	 		+ "where book_loans.Loan_id = \'"+yesLoanId+"\' AND book_loans.Due_date < CURDATE()) as t";
                	 Statement stmt4 = conn.createStatement();
                	 stmt4.executeUpdate(insertFinesRecord);
                 }
             }
			
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Sql Error : "+ e1.getMessage());
		}
		

		//Header
		JLabel header=new JLabel("Fines",JLabel.CENTER);
		header.setFont(new Font("Times New Roman",Font.BOLD,20));
		GridBagConstraints finesHeader=new GridBagConstraints();
		finesHeader.insets=new Insets(0,0,5,0);
		finesHeader.gridx=0;
		finesHeader.gridy=1;
		finesHeader.gridwidth=3;
		buttonOptions.add(header, finesHeader);		
		
		//
		JLabel space1=new JLabel("  ",JLabel.CENTER);
		//searchLabel.setFont(new F);
		GridBagConstraints finesSpace1=new GridBagConstraints();
		finesSpace1.insets=new Insets(0,0,5,0);
		finesSpace1.gridx=0;
		finesSpace1.gridy=2;
		finesSpace1.gridwidth=3;
		buttonOptions.add(space1, finesSpace1);
		
		//Borrower Card No.
		JLabel lblcard=new JLabel("Card No.*  :",JLabel.LEFT);
		lblcard.setFont(new Font("Times New Roman",Font.BOLD,14));
		GridBagConstraints finesLabelCard=new GridBagConstraints();
		finesLabelCard.insets=new Insets(0,0,5,0);
		finesLabelCard.gridx=0;
		finesLabelCard.gridy=3;
		//finesLabelCard.gridwidth=2;
		buttonOptions.add(lblcard, finesLabelCard);
		
		//Card No.Text Box
		JTextField cardText = new JTextField();
	
		cardText.setFont(new Font("Ariel",Font.PLAIN,14));
		cardText.setForeground(Color.black);
		//cardText.setSize(200, 50);;
		GridBagConstraints finesCardText =new GridBagConstraints();
		finesCardText.fill=GridBagConstraints.HORIZONTAL;
		finesCardText.insets=new Insets(0,0,5,0);
		finesCardText.gridx=1;
		finesCardText.gridy=3;
		finesCardText.gridwidth=2;
		buttonOptions.add(cardText, finesCardText);
		//buttonOptions.add(searchText);
		cardText.setColumns(15);
		
		//All Fines Due
		JButton finesDue=new JButton("All Fines Due");
		finesDue.setForeground(Color.black);
		finesDue.setBackground(BRIGHT_YELLOW);
		finesDue.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
				    FinesDue(cardText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints finesTableDue=new GridBagConstraints();
		//finesTableDue.fill=GridBagConstraints.HORIZONTAL;
		finesTableDue.insets = new Insets(0, 0, 5, 0);
		finesTableDue.gridx = 0;
		finesTableDue.gridy = 4;
		//finesTableDue.gridwidth=2;
		buttonOptions.add(finesDue,finesTableDue);
		
		//Paid Fines
		JButton paidFines=new JButton("Paid Fines");
		paidFines.setForeground(Color.black);
		paidFines.setBackground(BRIGHT_YELLOW);
		paidFines.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					PaidFines(cardText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints finesPaidFines=new GridBagConstraints();
		//finesPaidFines.fill=GridBagConstraints.HORIZONTAL;
		finesPaidFines.insets = new Insets(0, 0, 5, 0);
		finesPaidFines.gridx = 1;
		finesPaidFines.gridy = 4;
		//finesPaidFines.gridwidth=2;
		buttonOptions.add(paidFines,finesPaidFines);
		
		//Total Amount Due***************************************
		JButton totalDue=new JButton("Total Amount Due");
		totalDue.setForeground(Color.black);
		totalDue.setBackground(BRIGHT_YELLOW);
		totalDue.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				try {
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
					
					String card_id = cardText.getText();
					String totalFineAmt = "SELECT book_loans.Card_id as cardId, "
	                 		+ "SUM(fines.Fine_amt) as totalFine FROM fines "
	                 		+ "LEFT JOIN book_loans on fines.Loan_id = book_loans.Loan_id "
	                 		+ "GROUP BY book_loans.Card_id "
	                 		+ "HAVING book_loans.Card_id = \'"+card_id+"\'";
					Statement stmt5 = conn.createStatement();
	                ResultSet rs5 = stmt5.executeQuery(totalFineAmt);
	                if(rs5.next()) {
	                    outFineCardId = rs5.getString("cardId");
	                    allFineAmt = rs5.getDouble("totalFine");
	                    //System.out.print(outFineCardId + "\t");
	                    //System.out.print(allFineAmt + "\t");
	                    //System.out.println();
	                    //BigDecimal fine=allFineAmt.getBigDecimal(2);
	                    JOptionPane.showMessageDialog(null, "Total fine due : " + allFineAmt);
	                }
					else
					{
						JOptionPane.showMessageDialog(null, "No Fines Due");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.println("SQL Exception : " + e1.getMessage());
				}
				
			}
		});
		GridBagConstraints finesTotalDue=new GridBagConstraints();
		finesTotalDue.insets = new Insets(0, 0, 5, 0);
		finesTotalDue.gridx = 2;
		finesTotalDue.gridy = 4;
		buttonOptions.add(totalDue,finesTotalDue);
		
		JLabel space2=new JLabel("  ",JLabel.CENTER);
		GridBagConstraints finesSpace2=new GridBagConstraints();
		finesSpace2.insets=new Insets(0,0,5,0);
		finesSpace2.gridx=0;
		finesSpace2.gridy=5;
		finesSpace2.gridwidth=2;
		buttonOptions.add(space2, finesSpace2);
		
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
		GridBagConstraints finesButtonClose=new GridBagConstraints();
		finesButtonClose.fill=GridBagConstraints.HORIZONTAL;
		finesButtonClose.insets = new Insets(0, 0, 5, 0);
		finesButtonClose.gridx = 0;
		finesButtonClose.gridy = 6;
		finesButtonClose.anchor=GridBagConstraints.PAGE_END;
		finesButtonClose.gridwidth=3;
		buttonOptions.add(close, finesButtonClose);		

		
		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
	}
	
	void PaidFines(String card_str) throws SQLException
	{
		prepareGUI(card_str);
	}
	
	void prepareGUI(String card_str) throws SQLException
	{
		
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(500,500);
		mainPage.setLocation(20, 50);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout finesButtonOptions=new GridBagLayout();
		finesButtonOptions.columnWidths=new int[]{0,0};
		finesButtonOptions.rowHeights = new int[]{0, 0, 0};
		finesButtonOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		finesButtonOptions.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		buttonOptions.setLayout(finesButtonOptions);
		
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
		GridBagConstraints finesLayeredPane = new GridBagConstraints();
		finesLayeredPane.insets = new Insets(0, 0, 5, 0);
		finesLayeredPane.fill = GridBagConstraints.BOTH;
		finesLayeredPane.gridx = 0;
		finesLayeredPane.gridy = 0;
		buttonOptions.add(layeredPane, finesLayeredPane);
		
		table=new JTable();
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setBounds(6,6,600,400);
		layeredPane.add(scrollPane);
		
		
		scrollPane.setViewportView(table);
		
		GridBagConstraints finesButtonClose = new GridBagConstraints();
		finesButtonClose.fill=GridBagConstraints.HORIZONTAL;
		finesButtonClose.insets = new Insets(0, 0, 5, 0);
		finesButtonClose.gridx = 0;
		finesButtonClose.gridy = 2;
		finesButtonClose.anchor=GridBagConstraints.PAGE_END;
		buttonOptions.add(close, finesButtonClose);		
		try{		
			//jdbc connection to database
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
			
			
			
			String paidFineAmt = "SELECT book_loans.Card_id as cardIdPaid, "
             		+ "SUM(fines.Fine_amt) as paidFine FROM fines "
            		+ "JOIN book_loans on fines.Loan_id = book_loans.Loan_id "
            		+ "WHERE fines.Paid = 1 AND book_loans.Card_id = \'"+card_str+"\' "
              		+ "GROUP BY book_loans.Card_id";
             		//+ "HAVING fines.Paid = 1 AND book_loans.Card_id like \'"+card_str+"\' ";
			//System.out.println("card_str: " + card_str); 
			Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(paidFineAmt);

			table.setModel(DbUtils.resultSetToTableModel(rs));
			table.setEnabled(false);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(1).setPreferredWidth(500);
//			table.getColumnModel().getColumn(2).setPreferredWidth(350);
//			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
			}
		
		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
		
	}
	
	void FinesDue(String card_str) throws SQLException
	{
		prepareGUI2(card_str);
	}
	
	void prepareGUI2(String card_str) throws SQLException
	{
		
		//int card=Integer.parseInt(card_str);
		
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(500,500);
		mainPage.setLocation(20, 50);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout finesButtonOptions=new GridBagLayout();
		finesButtonOptions.columnWidths=new int[]{0,0};
		finesButtonOptions.rowHeights = new int[]{0, 0, 0};
		finesButtonOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		finesButtonOptions.rowWeights = new double[]{0.0,0.0,1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		buttonOptions.setLayout(finesButtonOptions);
		
		
		//details
		JLabel details=new JLabel("Click on the desired row and then click Pay",JLabel.LEFT);
		details.setFont(new Font("Times New Roman", Font.PLAIN,16));
		GridBagConstraints finesDetails=new GridBagConstraints();
		finesDetails.insets=new Insets(0,0,5,0);
		finesDetails.gridx=0;
		finesDetails.gridy=0;
		finesDetails.gridwidth=2;
		buttonOptions.add(details, finesDetails);
				
		//space
		JLabel space1=new JLabel(" ",JLabel.CENTER);
		//space2.setFont(new Font("Times New Roman", Font.PLAIN,14));
		GridBagConstraints finesSpace1=new GridBagConstraints();
		finesSpace1.insets=new Insets(0,0,5,0);
		finesSpace1.gridx=0;
		finesSpace1.gridy=1;
		finesSpace1.gridwidth=2;
		buttonOptions.add(space1, finesSpace1);
						
		
		//close button
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
		GridBagConstraints finesLayeredPane = new GridBagConstraints();
		finesLayeredPane.insets = new Insets(0, 0, 5, 0);
		finesLayeredPane.fill = GridBagConstraints.BOTH;
		finesLayeredPane.gridx = 0;
		finesLayeredPane.gridy = 2;
		buttonOptions.add(layeredPane, finesLayeredPane);
		
		table=new JTable();
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setBounds(6,6,600,400);
		layeredPane.add(scrollPane);
		
		
		scrollPane.setViewportView(table);

		table.setColumnSelectionAllowed(false);;
		table.setRowSelectionAllowed(true);
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evnt)
			{
				if(evnt.getClickCount() ==1 || evnt.getClickCount()==2)
				{
					loan_id=(int)table.getModel().getValueAt(table.rowAtPoint(evnt.getPoint()), 0);
					setBackground(Color.blue);
				}
			}
		});


		//Pay
		JButton pay=new JButton("Pay Fine");
		pay.setForeground(Color.black);
		pay.setBackground(BRIGHT_YELLOW);
		pay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					
					//System.out.println("Before jdbc connection");
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
					
					//System.out.println("After jdbc connection");
					String selectLateBooks = "SELECT * FROM book_loans WHERE Loan_id = \'"+loan_id+"\' AND Date_in is null";
					Statement stmt1 = conn.createStatement();
					ResultSet rs1 = stmt1.executeQuery(selectLateBooks);

					if(rs1.next()){
						
					  conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
					  
					  String updateFineAmount = "UPDATE fines SET Paid = 1 WHERE Loan_id = \'"+loan_id+"\'";
					  Statement stmt2=conn.createStatement();
					  stmt2.executeUpdate(updateFineAmount);
					  
					  JOptionPane.showMessageDialog(null, "Fine amount for Loan Id "+loan_id+" is cleared ");
					  mainPage.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "This book is not returned");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		GridBagConstraints finesPay=new GridBagConstraints();
		finesPay.fill=GridBagConstraints.HORIZONTAL;
		finesPay.insets = new Insets(0, 0, 5, 0);
		finesPay.gridx = 0;
		finesPay.gridy = 3;
		finesPay.gridwidth=2;
		buttonOptions.add(pay, finesPay);
	
		//space
		JLabel space2=new JLabel("  ",JLabel.CENTER);
		//searchLabel.setFont(new F);
		GridBagConstraints finesSpace2=new GridBagConstraints();
		finesSpace2.insets=new Insets(0,0,5,0);
		finesSpace2.gridx=0;
		finesSpace2.gridy=4;
		finesSpace2.gridwidth=2;
		buttonOptions.add(space2, finesSpace2);

		
		GridBagConstraints finesButtonClose = new GridBagConstraints();
		finesButtonClose.fill=GridBagConstraints.HORIZONTAL;
		finesButtonClose.insets = new Insets(0, 0, 5, 0);
		finesButtonClose.gridx = 0;
		finesButtonClose.gridy = 5;
		finesButtonClose.anchor=GridBagConstraints.PAGE_END;
		buttonOptions.add(close, finesButtonClose);		
		try{		
			//jdbc connection to database
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
			
			String totalFineAmt = "SELECT book_loans.Loan_id as loanId, book_loans.Isbn as Isbn, "
             		+ "fines.Fine_amt as fine_amount FROM fines "
             		+ "LEFT JOIN book_loans on fines.Loan_id = book_loans.Loan_id "
             		+ "WHERE book_loans.Card_id = \'"+card_str+"\' AND fines.Paid = 0 "
             		+ "GROUP BY book_loans.Isbn";
			 Statement stmt4 = conn.createStatement();
             ResultSet rs4 = stmt4.executeQuery(totalFineAmt);

			table.setModel(DbUtils.resultSetToTableModel(rs4));
			table.setEnabled(false);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			//table.getColumnModel().getColumn(3).setPreferredWidth(100);
			
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