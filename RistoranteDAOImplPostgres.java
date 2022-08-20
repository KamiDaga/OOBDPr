

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class RistoranteDAOImplPostgres implements RistoranteDAO {
	
	public ArrayList<Ristorante> estraiTuttiRistoranti() throws OperazioneFallitaException{
		
		try
		{
			ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery = stmt.executeQuery("SELECT * "
														+ "FROM RISTORANTE "
														+ "ORDER BY Nome;");
			
			while(risultatoQuery.next()) {
				
				Integer idCorrente = risultatoQuery.getInt(1);
				String nomeCorrente = risultatoQuery.getString(2);
				String viaCorrente = risultatoQuery.getString(3);
				Integer n_CivicoCorrente = risultatoQuery.getInt(4);
				String cittaCorrente = risultatoQuery.getString(5);
				Ristorante ristoranteCorrente = new Ristorante(idCorrente, nomeCorrente, cittaCorrente, viaCorrente, n_CivicoCorrente);
				listaRistoranti.add(ristoranteCorrente);
			}
			
			return listaRistoranti;
		
		}
		catch (SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
	}

	public void inserisciRistorante(String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException, RistoranteUgualeException, StringheNonValideException{
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("INSERT INTO Ristorante (Nome, Via, N_Civico, Citta) VALUES ("
					+ "'" + nome + "'" + ", " + "'" + via+ "'" + ", " 
					+ n_Civico + ", " + "'"+ citta +"'" + " );");
			
		}
		catch (SQLException e)
		{
			if (e.getSQLState().equals("23505")){
				RistoranteUgualeException ecc = new RistoranteUgualeException();
				throw ecc;
			}
			else if (e.getSQLState().equals("42601")) 
			{
				StringheNonValideException ecc = new StringheNonValideException();
				throw ecc;
			}
			else {
				OperazioneFallitaException ecc = new OperazioneFallitaException();
				throw ecc;
			}
		}
	}

	public void modificaRistorante(Ristorante ristorante, String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException, RistoranteUgualeException, StringheNonValideException {

		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("UPDATE Ristorante AS R "
					+ "SET "
					+ "Nome = " + "'" + nome + "'" + ", " 
					+ "Via = " + "'" + via+ "'" + ", " 
					+ "N_Civico = " + n_Civico + ", " 
					+ "Citta = " + "'" + citta +"'" +
					"WHERE Id_Ristorante = " + ristorante.getId_Ristorante() + ";");
		}
		catch (SQLException e)
		{
			if (e.getSQLState().equals("23505")){
				RistoranteUgualeException ecc = new RistoranteUgualeException();
				throw ecc;
			}
			else if (e.getSQLState().equals("42601")) 
			{
				StringheNonValideException ecc = new StringheNonValideException();
				throw ecc;
			}
			else {
				OperazioneFallitaException ecc = new OperazioneFallitaException();
				throw ecc;
			}
		}
	}
	
	public void eliminaRistorante(Ristorante ristoranteCorrente) throws OperazioneFallitaException {
        try
        {
          Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
          stmt.executeUpdate("DELETE FROM RISTORANTE WHERE "
          + "Id_Ristorante = " + ristoranteCorrente.getId_Ristorante() + ";");
        }
        catch (SQLException e)
        {
          OperazioneFallitaException ecc= new OperazioneFallitaException();
          throw ecc;
        }
		}
}
