package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {

	Connection connection=null;
	public Connection sqliteConncetion(){
		try{
			Class.forName("org.sqlite.JDBC");
			connection=DriverManager.getConnection("jdbc:sqlite:F:\\OCJP\\movielens.sqlite");
			JOptionPane.showMessageDialog(null,"Connection successfull");
			return connection;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
