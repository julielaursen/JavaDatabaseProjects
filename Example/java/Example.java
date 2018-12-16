package Example.java;

import java.sql.*;
//import java.util.Scanner;

public class Example {

	private static int addAuthor;
	private static int updateAuthor;
	private static int insertTitle;
	private static int deleteAuthors;

	public static int getAddAuthor() {
		return addAuthor;
	}

	public static void setAddAuthor(int addAuthor) {
		Example.addAuthor = addAuthor;
	}

	public static int getUpdateAuthor() {
		return updateAuthor;
	}

	public static void setUpdateAuthor(int updateAuthor) {
		Example.updateAuthor = updateAuthor;
	}
	
	
	public static int getInsertTitle() {
		return insertTitle;
	}

	public static void setInsertTitle(int insertTitle) {
		Example.insertTitle = insertTitle;
	}
	
	public static int getDeleteAuthors() {
		return deleteAuthors;
	}

	public static void setDeleteAuthors(int deleteAuthors) {
		Example.deleteAuthors = deleteAuthors;
	}
	
	public static void main(String args[]) throws ClassNotFoundException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "SYSTEM";
		String pass = "xxxxxxx";
		
		Connection c = DriverManager.getConnection(dbUrl, user, pass);
		if (c != null)
		{
			System.out.println("You're in your database now");
		}
		else {
			System.out.println("Failed to make connection");
		}
		
		System.out.println("Starting queries..");
		Statement myStmt = c.createStatement();
	
		ResultSet rset = myStmt.executeQuery("SELECT * FROM authors");
	
		ResultSetMetaData metaData = rset.getMetaData();
		int numberOfColumns = metaData.getColumnCount();
		System.out.printf("Authors Table of Books Database:%n%n");

		while (rset.next())
		{
			for (int i = 1; i <= numberOfColumns; i++)
			{
				System.out.printf("%-8s\t", rset.getObject(i));
			}
			System.out.println();
		}
		addAuthor = myStmt.executeUpdate(
				"insert into authors " + 
				"(firstname, lastname)" + 
				"values " + 
				"('Bob', 'Harper')");
				
		updateAuthor = myStmt.executeUpdate(
				"update authors " + 
				"set lastname = 'Wright' " +
				"WHERE lastname='Harper' and firstname = 'Bob'"
				);
		
		insertTitle = myStmt.executeUpdate(
				"update titles " +
				"set title = 'Golang Programming 4' " +
				"WHERE ISBN='0133764036' " 
				);
		
		ResultSet selectAuthors = myStmt.executeQuery(
			"SELECT b.title, b.copyright, b.isbn, a.firstname, a.lastname " + 
			"FROM titles b, authors a, authorisbn ai " +
			"WHERE ai.isbn=b.isbn AND a.authorid = ai.authorid " +
			"ORDER by a.firstname, a.lastname"
				);
		    numberOfColumns = metaData.getColumnCount();

			while (selectAuthors.next())
			{
			
			for (int i = 1; i <= numberOfColumns; i++)
			{
				System.out.printf("%-8s\t", selectAuthors.getObject(i));
			}
			System.out.println();
			}
		
		    deleteAuthors = myStmt.executeUpdate(
			"delete from authors " +
			"where lastname='Wright' and firstname='Bob'"
		);
		
		}

		catch (SQLException e) {
			e.printStackTrace();
		}	
	}



}
