import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CameriereDAOImplPostgres implements CameriereDAO
{
	public ArrayList<Cameriere> estraiCamerieriInServizio(Ristorante ristorante)
	{
		ArrayList<Cameriere> Risultato = new ArrayList<Cameriere>();
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery= stmt.executeQuery("SELECT * "
													+ "FROM Cameriere "
													+ "WHERE Id_Ristorante = "+ristorante.getId_Ristorante()+" "
													+ "AND Data_Licenziamento IS NULL "
													+ "ORDER BY Cognome, Nome;");
			while(risultatoQuery.next())
			{
				Cameriere attuale = new Cameriere(risultatoQuery.getInt(1),risultatoQuery.getString(2),risultatoQuery.getString(3),risultatoQuery.getString(4),
						risultatoQuery.getInt(5),risultatoQuery.getString(6),risultatoQuery.getString(7));
				Risultato.add(attuale);
			}
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
		return Risultato;
	}
	public ArrayList<Cameriere> estraiCamerieriLicenziati(Ristorante ristorante)
	{
		ArrayList<Cameriere> Risultato = new ArrayList<Cameriere>();
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery= stmt.executeQuery("SELECT distinct CID_Cameriere, Nome, Cognome, Id_Ristorante "
													+ "FROM Cameriere "
													+ "WHERE Id_Ristorante = "+ristorante.getId_Ristorante()+" "
													+ "AND Data_Licenziamento IS NOT NULL "
													+ "AND CID_Cameriere NOT IN (SELECT CID_Cameriere "
																			+ "FROM Cameriere AS C "
																			+ "WHERE C.Data_Licenziamento IS NULL "
																			+ "AND C.Id_Ristorante = " + ristorante.getId_Ristorante() + ") "
													+ "ORDER BY Cognome, Nome;");
			while(risultatoQuery.next())
			{
				Cameriere attuale = new Cameriere(risultatoQuery.getString(1),risultatoQuery.getString(2),risultatoQuery.getString(3),risultatoQuery.getInt(4));
				Risultato.add(attuale);
			}
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
		return Risultato;
	}
	
	public boolean riassumiCameriereLicenziato(Cameriere c,String data)
	{
		try 
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultato = stmt.executeQuery("SELECT MAX (Data_Licenziamento) "
												+ "FROM Cameriere AS C "
												+ "WHERE  C.Data_Licenziamento >= '" + data +"'  "
												+ "AND C.CID_Cameriere = '" + c.getCID_Cameriere() + "'; ");
			risultato.next();
			String dataLicenziamentoSeguenteRiassunzione = risultato.getString(1);
			if(dataLicenziamentoSeguenteRiassunzione!=null){
				
				JOptionPane.showMessageDialog(null, "Un cameriere non puo' essere riassunto prima di quando e' "
						+ "stato licenziato! ( " + risultato.getString(1) + " )", "Attenzione!", JOptionPane.WARNING_MESSAGE);
				return false;
				
				}
			else {
				
				try
				{
					stmt.executeUpdate("INSERT INTO Cameriere(CID_Cameriere,Nome,Cognome,Id_Ristorante,Data_Ammissione) "
									+ "VALUES ('"+c.getCID_Cameriere()+"', '"+c.getNome()+"', '"+c.getCognome()+"', "+c.getRistorante().getId_Ristorante()+", DATE'"+data+"');");
					return true;
				}
				catch(SQLException e)
				{
					OperazioneFallitaException ecc = new OperazioneFallitaException();
					ecc.stampaMessaggio();
					return false;
				}
			}
		}
		catch (SQLException e)
		{
			OperazioneFallitaException ex = new OperazioneFallitaException();
			ex.stampaMessaggio();
			return false;
		}
		
	}
	public String licenziaCameriereAssunto(Cameriere c,String data)
	{
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			stmt.executeUpdate("UPDATE Cameriere "
							+ "SET Data_Licenziamento = DATE '"+data+"' "
							+ "WHERE Id_Cameriere = "+c.getId_Cameriere()+";");
			return "Tutto_Bene";
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23514")) 
				return "Data_Licenziamento_Precedente";
			else
			{
				OperazioneFallitaException ex = new OperazioneFallitaException();
				ex.stampaMessaggio();
				return "Operazione_Fallita";
			}
		}
	}
	
	public String assumiNuovoCameriere(Cameriere c)
	{
		try
		{
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultato = stmt.executeQuery("SELECT COUNT(*) "
												+ "FROM Cameriere AS C "
												+ "WHERE  C.CID_Cameriere = '" + c.getCID_Cameriere() + "'"
												+ "AND C.Id_Ristorante = " + c.getRistorante().getId_Ristorante()+ "; ");
			risultato.next();
			int contaCID = risultato.getInt(1);
			if(contaCID!=0){
				
				return "CID_Gia_Presente";
				
				}
			
			stmt.executeUpdate("INSERT INTO Cameriere (CID_Cameriere,Nome,Cognome,Id_Ristorante,Data_Ammissione) "
							+ "VALUES ('"+c.getCID_Cameriere()+"','"+c.getNome()+"','"+c.getCognome()+"',"+c.getRistorante().getId_Ristorante()+",DATE '"+c.getData_Ammissione()+"');");
			return "Nessun_Errore";
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("23514"))
			{
				return "CID_Non_Valido";
			}
			else if (e.getSQLState().equals("42601")) 
			{
				return "NomeCognome_Non_Validi";
			}
			else 
			{
				return "Problema_Di_Connessione";
			}
		}
	}
	
	public void rimuoviCameriereDalTavoloInData(Cameriere c, String data, int idTavolo)
	{
		try
		{
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT id_tavolata "
																									+ "FROM Tavolata "
																									+ "WHERE id_tavolo ="+idTavolo+" "
																									+ "AND DATA = '"+data+"';");
			rs.next();
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Servizio "
																					+ "WHERE id_tavolata = "+rs.getInt(1)+" "
																					+ "AND id_cameriere = "+c.getId_Cameriere()+";");
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
	}
	
	public ArrayList<Cameriere> camerieriInServizioAlTavoloInData(int idTavolo, String data)
	{
		ArrayList<Cameriere> risultato = new ArrayList<Cameriere>();
		try
		{
			ResultSet tavolata = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT id_tavolata "
																										+ "FROM Tavolata "
																										+ "WHERE id_tavolo = "+idTavolo+" "
																										+ "AND data = '"+data+"';");
			tavolata.next();
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT CA.Nome, CA.Cognome, CA.id_cameriere, CA.cid_Cameriere "
																									+ "FROM Cameriere as CA, Servizio AS S "
																									+ "WHERE S.id_tavolata = "+tavolata.getInt(1)+" "
																									+ "AND S.id_cameriere = CA.id_cameriere "
																									+ "ORDER BY Cognome, Nome;");
			while(rs.next())
			{
				risultato.add(new Cameriere(rs.getInt(3),rs.getString(4),rs.getString(1),rs.getString(2)));
			}
			return risultato;
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
			return risultato;
		}
	}
	
	public ArrayList<Cameriere> camerieriAssegnabiliAlTavoloInData(String data, Ristorante ristorante)
	{
		ArrayList<Cameriere> risultato = new ArrayList<Cameriere>();
		try
		{
			ResultSet rs = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT id_cameriere, cid_cameriere, nome, cognome "
																								+ "FROM cameriere "
																								+ "WHERE data_ammissione<= '"+data+"' "
																								+ "AND (data_licenziamento is null OR data_licenziamento >= '"+data+"') "
																								+ "AND Id_ristorante = "+ristorante.getId_Ristorante()+" "
																								+ "ORDER BY Cognome, Nome;");
			while(rs.next())
			{
				risultato.add(new Cameriere(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			}
			return risultato;
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
			return risultato;
		}
	}

	
	public void inserimentoMultiploCamerieriInServizio(ArrayList<Cameriere> listaCamerieri,
			String data, Tavolo tavolo)
	{
		
		int tavolata = -1;
		try
		{
			ResultSet tavolataDB = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT id_tavolata "
																											+ "FROM tavolata AS tav "
																											+ "WHERE tav.data = '"+data+"' "
																											+ "AND id_tavolo = "+tavolo.getId_Tavolo()+";");
			tavolataDB.next();
			tavolataDB.getInt(1);
			tavolata =  tavolataDB.getInt(1);
		}	
		catch(SQLException e)
		{
			//L'eccezione parte solo se i camerieri sono inseriti in una tavolata nonesistente,
			//cosa che succede ogni volta che vengono inseriti avventori gia' occupati, quindi evitiamo messaggi di errore
		}

		for(Cameriere cameriereCorrente : listaCamerieri)
		{
			try
			{
				DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO servizio "
																						+ "VALUES("+cameriereCorrente.getId_Cameriere()+","+tavolata+");");
			}
			catch(SQLException e)
			{
				//L'eccezione parte solo se i camerieri sono inseriti in una tavolata nonesistente,
				//cosa che succede ogni volta che vengono inseriti avventori gia' occupati, quindi evitiamo messaggi di errore
			}
		}
	}
	
	public boolean cameriereOccupatoDopoDiData(Cameriere cameriere, String data) {

		String ultimaOccupazione = null;

		try
		{
			ResultSet risultato = DB_Connection.getInstance().getConnection().createStatement().executeQuery("SELECT MAX (T.Data) "
																										+ "FROM Servizio AS S NATURAL JOIN Tavolata AS T "
																										+ "WHERE S.Id_Cameriere = "+ cameriere.getId_Cameriere() +""
																										+ " AND T.Data >= '"+data+"';");
			risultato.next();
			ultimaOccupazione = risultato.getString(1);
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}

		if (ultimaOccupazione == null)
			return false;
		else {

			JOptionPane.showMessageDialog(null, "Questo cameriere e' gia' occupato per servire un tavolo\n"
					+ "nel giorno " + ultimaOccupazione + " !", "Attenzione!", JOptionPane.WARNING_MESSAGE);

			return true;

		}

	}
		
}
