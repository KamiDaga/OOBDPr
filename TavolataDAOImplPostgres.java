import java.sql.*;

import javax.swing.JOptionPane;

public class TavolataDAOImplPostgres implements TavolataDAO
{
	public void inserimentoTavolata(Tavolata tavolata)
	{
		try
		{
			DB_Connection.getInstance().getConnection().createStatement().executeUpdate("INSERT INTO TAVOLATA(id_tavolo,data) VALUES ("+tavolata.getTavoloAssociato().getId_Tavolo()+",DATE '"+tavolata.getData()+"');");
		}
		catch(SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			ecc.stampaMessaggio();
		}
	}
}
