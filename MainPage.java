package indProject;

import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.*;

//import indProject.CheckIn;
import indProject.CheckInFull;
import indProject.CheckOutBook;
import indProject.FinesTable;
//import indProject.finesCheck;
import indProject.MainPage;
import indProject.BorrowerManagement;
import indProject.SearchDataFull;

import net.proteanit.sql.DbUtils;

import java.sql.Connection;
public class MainPage extends JFrame{
	public static final Color VERY_LIGHT_YELLOW = new Color(255,255,204);
	public static final Color BRIGHT_YELLOW = new Color(255,204,51);
	public static final Color VERY_LIGHT_RED = new Color(255,102,102);
	public static final Color VERY_LIGHT_BLUE = new Color(51,153,255);
	
	JFrame mainPage;
	JPanel buttonOptions;
	JLabel optionNames;
	
static Connection conn;
	
	MainPage()
	{
		prepareGUI();
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					MainPage mainPage=new MainPage();
					//mainPage.setVisible(true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	
	void prepareGUI()
	{
		
		mainPage=new JFrame("Library Management System Project");
		mainPage.setSize(600,500);
		mainPage.setLocation(20, 50);
		mainPage.setLayout(new GridLayout(1,1));
		mainPage.setBackground(VERY_LIGHT_RED);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		buttonOptions.setPreferredSize(mainPage.getSize());
		setContentPane(buttonOptions);
		GridBagLayout mainButtonOptions=new GridBagLayout();
		mainButtonOptions.columnWidths=new int[]{0,0};
		mainButtonOptions.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0};
		//mainButtonOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		//mainButtonOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		buttonOptions.setLayout(mainButtonOptions);
		buttonOptions.setBackground(VERY_LIGHT_YELLOW);
		
		//Heading
		optionNames=new JLabel("Library Management System",JLabel.CENTER);
		optionNames.setFont(new Font("Arial",Font.BOLD,30));

		buttonOptions.add(optionNames);

		
		//search button
		JButton search=new JButton("Search a book");
		search.setForeground(Color.black);
		search.setBackground(BRIGHT_YELLOW);
		search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
					mainPage.setVisible(false);
					try {
						new SearchDataFull();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		GridBagConstraints buttonSearchData=new GridBagConstraints();
		buttonSearchData.fill=GridBagConstraints.HORIZONTAL;
		buttonSearchData.insets = new Insets(0, 0, 5, 0);
		buttonSearchData.gridx = 0;
		buttonSearchData.gridy = 1;
		buttonSearchData.gridwidth = 8;
		buttonOptions.add(search, buttonSearchData);
		
				
		
		//Check Out Button
		JButton checkOut=new JButton("Checkout a book");
		checkOut.setForeground(Color.black);
		checkOut.setBackground(BRIGHT_YELLOW);
		checkOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				mainPage.setVisible(false);
				new CheckOutBook();
			}
		});
		GridBagConstraints buttonCheckOut=new GridBagConstraints();
		buttonCheckOut.fill=GridBagConstraints.HORIZONTAL;
		buttonCheckOut.insets = new Insets(0, 0, 5, 0);
		buttonCheckOut.gridx = 0;
		buttonCheckOut.gridy = 2;
		buttonCheckOut.gridwidth = 8;
		buttonOptions.add(checkOut,buttonCheckOut);
		
		//Check In Button
		JButton checkIn=new JButton("Checkin a book");
		checkIn.setForeground(Color.black);
		checkIn.setBackground(BRIGHT_YELLOW);
		checkIn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				mainPage.setVisible(false);
				new CheckInFull();
			}
		});
		GridBagConstraints buttonCheckIn=new GridBagConstraints();
		buttonCheckIn.fill=GridBagConstraints.HORIZONTAL;
		buttonCheckIn.insets = new Insets(0, 0, 5, 0);
		buttonCheckIn.gridx = 0;
		buttonCheckIn.gridy = 3;
		buttonCheckIn.gridwidth = 8;
		buttonOptions.add(checkIn, buttonCheckIn);

		
		//New Borrower Button
		JButton newBorrower = new JButton("Add a new Borrower");
		newBorrower.setForeground(Color.black);
		newBorrower.setBackground(BRIGHT_YELLOW);
		newBorrower.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				mainPage.setVisible(false);
				new BorrowerManagement();
			}
		});
		GridBagConstraints buttonNewBorrower=new GridBagConstraints();
		buttonNewBorrower.fill=GridBagConstraints.HORIZONTAL;
		buttonNewBorrower.insets = new Insets(0, 0, 5, 0);
		buttonNewBorrower.gridx = 0;
		buttonNewBorrower.gridy = 4;
		buttonNewBorrower.gridwidth = 8;
		buttonOptions.add(newBorrower,buttonNewBorrower);
		
		//Fines Button
		JButton fines=new JButton("Fines");
		fines.setForeground(Color.black);
		fines.setBackground(BRIGHT_YELLOW);
		fines.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
		
				//
				mainPage.setVisible(false);
				new FinesTable();
				//new finesCheck();
			}
		});
		GridBagConstraints buttonFines=new GridBagConstraints();
		buttonFines.fill=GridBagConstraints.HORIZONTAL;
		buttonFines.insets = new Insets(0, 0, 5, 0);
		buttonFines.gridx = 0;
		buttonFines.gridy = 5;
		buttonFines.gridwidth = 8;
		buttonOptions.add(fines, buttonFines);		
		
		//JLabel
		JLabel space=new JLabel(" ");
		GridBagConstraints buttonLblSpace=new GridBagConstraints();
		buttonLblSpace.fill=GridBagConstraints.HORIZONTAL;
		buttonLblSpace.insets = new Insets(0, 0, 5, 0);
		buttonLblSpace.gridx = 0;
		buttonLblSpace.gridy = 6;
		buttonOptions.add(space, buttonLblSpace);		


		//Close Button
		JButton close=new JButton("Close");
		close.setForeground(Color.black);
		close.setBackground(VERY_LIGHT_RED);
		close.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				mainPage.dispose();
			}
		});
		GridBagConstraints buttonClose=new GridBagConstraints();
		buttonClose.fill=GridBagConstraints.HORIZONTAL;
		buttonClose.insets = new Insets(0, 0, 5, 0);
		buttonClose.gridx = 10;
		buttonClose.gridy = 15;
		buttonClose.anchor=GridBagConstraints.PAGE_END;
		buttonOptions.add(close, buttonClose);		

		mainPage.add(buttonOptions);
		mainPage.pack();
		mainPage.setVisible(true);
	}

}
