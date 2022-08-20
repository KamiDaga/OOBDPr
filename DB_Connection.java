import java.sql.*;

import javax.swing.JOptionPane;

public class DB_Connection {

	private static DB_Connection instance; 
	private Connection conn = null;
  
	private DB_Connection() throws DriverMancanteException {
		
		try 
		{	
			Class.forName("org.postgresql.Driver");
			
			//Connessione al db come utente postgres
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ristorantidb", "postgres", "1754Ggdf");
		}
		catch(ClassNotFoundException e)
		{
			DriverMancanteException ecc = new DriverMancanteException();
			throw ecc;
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "C'e' stato un errore di connessione al DBMS\n"
					+ "Si provi a riavviare l'applicativo.", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
		
	};
	
	public Connection getConnection() {
		return conn;
	}
	
	public static DB_Connection getInstance(){
		
		try
		{
		if (instance==null) {
			instance = new DB_Connection();
		} else
			try {
				if (instance.getConnection().isClosed()) {
					instance = new DB_Connection();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "C'e' stato un errore di connessione al DBMS\n"
						+ "Si provi a riavviare l'applicativo.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(DriverMancanteException e)
		{
			e.stampaMessaggio();
		}
		return instance;
		
	}
	
	
}