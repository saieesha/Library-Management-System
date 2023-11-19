package indProject;

import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import net.proteanit.sql.DbUtils;
import java.sql.Connection;

public class SearchDataFull extends JFrame
{
    ////////////////////////////////////
    String Isbn;
    String Title;
    String Author;
    String bookAvailability;
    ///////////////////////////////////
	
	JFrame mainPage;
	JPanel buttonOptions;
	JTable table;
	public static final Color VERY_LIGHT_YELLOW = new Color(255,255,204);
	public static final Color BRIGHT_YELLOW = new Color(255,204,51);
	public static final Color VERY_LIGHT_RED = new Color(255,102,102);
	public static final Color VERY_LIGHT_BLUE = new Color(51,153,255);
	static Connection conn=null;
	
	SearchDataFull() throws SQLException
	{
		prepareGUI();
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					SearchDataFull search=new SearchDataFull();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	
	void prepareGUI() throws SQLException
	{
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(600,350);
		mainPage.setLocation(20, 50);
		mainPage.setMinimumSize(mainPage.getSize());
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout searchButtonOptions=new GridBagLayout();
		searchButtonOptions.columnWidths=new int[]{0,0};
		searchButtonOptions.rowHeights = new int[]{0, 0, 0, 0, 0,0,0,0};
		//searchButtonOptions.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		//searchButtonOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		buttonOptions.setLayout(searchButtonOptions);
		buttonOptions.setBackground(VERY_LIGHT_YELLOW);
		
		
		JLabel searchLabel=new JLabel("Search a book",JLabel.CENTER);
		searchLabel.setFont(new Font("Arial",Font.BOLD,30));
		GridBagConstraints buttonSearchLabel=new GridBagConstraints();
		buttonSearchLabel.insets=new Insets(0,0,5,0);
		buttonSearchLabel.gridx=0;
		buttonSearchLabel.gridy=1;
		buttonSearchLabel.gridwidth=2;
		buttonOptions.add(searchLabel, buttonSearchLabel);
		
		//Text Box
		JLabel textLabel = new JLabel("Give ISBN or Author name or Title",JLabel.LEFT);
		textLabel.setFont(new Font("Arial",Font.PLAIN,14));
		
		GridBagConstraints buttonTextLabel=new GridBagConstraints();
		buttonTextLabel.insets=new Insets(0,0,5,0);
		buttonTextLabel.gridx=0;
		buttonTextLabel.gridy=2;
		buttonTextLabel.gridwidth=2;
		buttonOptions.add(textLabel, buttonTextLabel);
		
		JLabel space1=new JLabel("  ",JLabel.CENTER);
		//searchLabel.setFont(new F);
		GridBagConstraints buttonSpace=new GridBagConstraints();
		buttonSpace.insets=new Insets(0,0,5,0);
		buttonSpace.gridx=0;
		buttonSpace.gridy=3;
		buttonSpace.gridwidth=2;
		buttonOptions.add(space1, buttonSpace);

		
		JTextField searchText = new JTextField();
		
		searchText.setFont(new Font("Arial",Font.PLAIN,12));
		searchText.setForeground(Color.black);
		GridBagConstraints buttonSearchText=new GridBagConstraints();
		buttonSearchText.fill=GridBagConstraints.HORIZONTAL;
		buttonSearchText.insets=new Insets(0,0,5,0);
		buttonSearchText.gridx=1;
		buttonSearchText.gridy=4;
		buttonSearchText.gridwidth=2;
		buttonOptions.add(searchText, buttonSearchText);
		searchText.setColumns(20);
		
		
		JButton search=new JButton("search data");
		search.setForeground(Color.black);
		search.setBackground(BRIGHT_YELLOW);
		search.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e) 
			{
			
				try {
					searchDataFullSQL(searchText.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//}
			}
		});
		GridBagConstraints buttonsearchDataFull2=new GridBagConstraints();
		buttonsearchDataFull2.fill=GridBagConstraints.HORIZONTAL;
		buttonsearchDataFull2.insets = new Insets(0, 0, 5, 0);
		buttonsearchDataFull2.gridx = 1;
		buttonsearchDataFull2.gridy = 5;
		buttonOptions.add(search, buttonsearchDataFull2);
		
		JLabel space2=new JLabel("  ",JLabel.CENTER);
		GridBagConstraints buttonSpace2=new GridBagConstraints();
		buttonSpace2.insets=new Insets(0,0,5,0);
		buttonSpace2.gridx=0;
		buttonSpace2.gridy=6;
		buttonSpace2.gridwidth=2;
		buttonOptions.add(space2, buttonSpace2);
		
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
		GridBagConstraints buttonSearchClose=new GridBagConstraints();
		buttonSearchClose.fill=GridBagConstraints.HORIZONTAL;
		buttonSearchClose.insets = new Insets(0, 0, 5, 0);
		buttonSearchClose.gridx = 1;
		buttonSearchClose.gridy = 7;
		buttonSearchClose.anchor=GridBagConstraints.PAGE_END;
		buttonOptions.add(close, buttonSearchClose);		

		mainPage.add(buttonOptions);
		mainPage.setVisible(true);
	}
	
	void searchDataFullSQL(String searchWord) throws SQLException
	{
		prepareGUI(searchWord);
	}
	
	void prepareGUI(String searchWord) throws SQLException
	{
		mainPage=new JFrame("Library Management System");
		mainPage.setSize(500,500);
		mainPage.setLocation(20, 50);
		//mainPage.setBackground(Color.RED);
		//mainPage.pack();
		//mainPage.setVisible(true);
		mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		buttonOptions=new JPanel();
		buttonOptions.setBorder(new EmptyBorder(10,10,10,10));
		setContentPane(buttonOptions);
		GridBagLayout searchDatabuttonOptions=new GridBagLayout();
		searchDatabuttonOptions.columnWidths=new int[]{0,0};
		searchDatabuttonOptions.rowHeights = new int[]{0, 0, 0};
		searchDatabuttonOptions.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		searchDatabuttonOptions.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		buttonOptions.setLayout(searchDatabuttonOptions);
		
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
		GridBagConstraints searchDatalayeredPane = new GridBagConstraints();
		searchDatalayeredPane.insets = new Insets(0, 0, 5, 0);
		searchDatalayeredPane.fill = GridBagConstraints.BOTH;
		searchDatalayeredPane.gridx = 0;
		searchDatalayeredPane.gridy = 0;
		buttonOptions.add(layeredPane, searchDatalayeredPane);
		
		table=new JTable();
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setForeground(Color.black);
		scrollPane.setBackground(VERY_LIGHT_RED);
		scrollPane.setBounds(6,6,1100,600);
		layeredPane.add(scrollPane);
		
		
		scrollPane.setViewportView(table);
		
		GridBagConstraints searchDataClose = new GridBagConstraints();
		searchDataClose.fill=GridBagConstraints.HORIZONTAL;
		searchDataClose.insets = new Insets(0, 0, 5, 0);
		searchDataClose.gridx = 0;
		searchDataClose.gridy = 2;
		searchDataClose.anchor=GridBagConstraints.PAGE_END;
		buttonOptions.add(close, searchDataClose);		
		try{		
			//jdbc connection to database
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Saraswathi1969@");
			Statement stmt=conn.createStatement();
			ResultSet rs= null;
			
			if(searchWord.equals(" ")|| searchWord.equals(""))
			{
				//rs=stmt.executeQuery("SELECT b.Isbn, b.Title, GROUP_CONCAT(a.Name) as Authors, (case when b.Isbn in(Select Isbn from proj1.book_loans where Date_in IS NULL) then 'no' else 'yes' end) AS Available FROM proj1.book AS b left outer join proj1.book_authors AS ba on b.Isbn=ba.Isbn left outer join proj1.authors as a on ba.Author_id=a.Author_id group by b.Isbn; ");
				String query = "SELECT Main.Isbn , Main.Title, group_concat(Main.Name) as Authors, "
            			+ "IF(EXISTS(SELECT Date_in from Book_loans where  Book_loans.Isbn = Main.Isbn order by Loan_id desc limit 0,1) AND "
            			+ "(SELECT Date_in from Book_loans where Book_loans.Isbn = Main.Isbn order by Loan_id desc limit 0,1) is null,0,1) as Availability "
            			+ "FROM (SELECT book.Isbn as Isbn, book.Title as Title, authors.Name as Name from book "
            			+ "join book_authors on book_authors.Isbn = book.Isbn "
            			+ "join authors on book_authors.Author_id = authors.Author_id) "
            			+ "AS Main GROUP BY Main.Isbn, Main.Title;";
			
				rs = stmt.executeQuery(query);   		
			
			}
			else
			{
				String query = "SELECT Main.Isbn , Main.Title, group_concat(Main.Name) as Authors,"
            			+ "IF(EXISTS(SELECT Date_in from Book_loans where  Book_loans.Isbn = Main.Isbn order by Loan_id desc limit 0,1) AND "
            			+ "(SELECT Date_in from Book_loans where Book_loans.Isbn = Main.Isbn order by Loan_id desc limit 0,1) is null,0,1) as Availability "
            			+ "FROM (SELECT book.Isbn as Isbn, book.Title as Title, authors.Name as Name from book "
            			+ "join book_authors on book_authors.Isbn = book.Isbn "
            			+ "join authors on book_authors.Author_id = authors.Author_id "
            			+ "having (book.Isbn like '%"+searchWord+"%') or (book.Title like '%"+searchWord+"%') or "
            			+ "(authors.Name like '%"+searchWord+"%')) AS Main GROUP BY Main.Isbn, Main.Title;";
			
				rs = stmt.executeQuery(query);
                
			}
			table.setModel(DbUtils.resultSetToTableModel(rs));
			table.setEnabled(false);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(1).setPreferredWidth(500);
			table.getColumnModel().getColumn(2).setPreferredWidth(350);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			
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

