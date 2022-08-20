import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.*;
public class SalaDAOImplPostgres implements SalaDAO 
{
public ArrayList<Sala> estraiSaleRistorante(Ristorante ristoranteCorrente)
		{
			ArrayList<Sala> risultato = new ArrayList<Sala>();
			try 
			{	
				Connection c = DB_Connection.getInstance().getConnection();
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * "
												+ "FROM Sala "
												+ "WHERE Id_Ristorante = " + ristoranteCorrente.getId_Ristorante() +" "
												+ "ORDER BY Nome;");  
				while(rs.next())
				{
					Sala salaCorrente = new Sala();
					salaCorrente.setId_Sala(rs.getInt(1));
					salaCorrente.setNome(rs.getString(2));
					salaCorrente.setRistoranteDiAppartenenza(ristoranteCorrente);
					risultato.add(salaCorrente);
				}
			}
			catch(SQLException e)
			{
				OperazioneFallitaException ecc = new OperazioneFallitaException();
				ecc.stampaMessaggio();
			}
			return risultato;
		}
	public void RimuoviSalaRistorante(Sala s)
	{
		try
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("DELETE FROM Sala WHERE Id_Sala ="+s.getId_Sala()+";");  
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
	}
	
	public void AggiuntaSalaRistorante(String nomeSala,int id_ristorante) throws ErrorePersonalizzato
	{
		try 
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("INSERT INTO Sala(Nome,Id_Ristorante) VALUES ('"+ nomeSala +"',"+id_ristorante+");");
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23505"))
				throw new NomeSalaUgualeException();
				
			else if (e.getSQLState().equals("42601")) 
				throw new StringheNonValideException();
			
			else 
				throw new OperazioneFallitaException();
		}
	}
	
	public void modificaSala(String nome, Sala sala) throws ErrorePersonalizzato {
		try 
		{
			Connection c = DB_Connection.getInstance().getConnection();
			Statement stmt = c.createStatement();
			stmt.executeUpdate("UPDATE SALA AS S SET Nome = '" + nome + "' WHERE S.Id_Sala = " + sala.getId_Sala() + " ;");
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23505"))
				throw new NomeSalaUgualeException();
				
			else if (e.getSQLState().equals("42601")) 
				throw new StringheNonValideException();
			
			else 
				throw new OperazioneFallitaException();
		}

	}	
}
