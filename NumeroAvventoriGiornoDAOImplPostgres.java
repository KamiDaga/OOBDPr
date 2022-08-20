import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NumeroAvventoriGiornoDAOImplPostgres implements NumeroAvventoriGiornoDAO {

	@Override
	public ArrayList<NumeroAvventoriGiorno> estraiStatisticheMese(Integer annoScelto, Integer meseScelto,
			Ristorante ristoranteScelto) throws OperazioneFallitaException {
		try
		{
			ArrayList<NumeroAvventoriGiorno> listaStatistiche = new ArrayList<NumeroAvventoriGiorno>();
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery;
			risultatoQuery = stmt.executeQuery(" SELECT EXTRACT (DAY FROM T.DATA) AS GIORNO, "
										+   "COUNT (E_A.N_CID) AS NUMAVVENTORI "
										+	"FROM TAVOLATA AS T NATURAL JOIN ELENCO_AVVENTORI AS E_A "
										+	" NATURAL JOIN TAVOLO AS TAV NATURAL JOIN SALA AS S "
										+	"WHERE EXTRACT (YEAR FROM T.DATA) = " + annoScelto.toString()
										+	" AND S.Id_Ristorante = " + ristoranteScelto.getId_Ristorante()
										+	" AND EXTRACT (MONTH FROM T.DATA) = " + meseScelto.toString()
										+	" GROUP BY EXTRACT (DAY FROM T.DATA); ");
			
			while(risultatoQuery.next()) {
				Integer giornoCorrente = risultatoQuery.getInt(1);
				Integer numAvventoriCorrente = risultatoQuery.getInt(2);
				NumeroAvventoriGiorno statisticheCorrenti = new NumeroAvventoriGiorno(numAvventoriCorrente, giornoCorrente, meseScelto, annoScelto);
				listaStatistiche.add(statisticheCorrenti);
			}
			
			return listaStatistiche;
		
		}
		catch (SQLException e)
		{
			OperazioneFallitaException ecc = new OperazioneFallitaException();
			throw ecc;
		}
		
	}

	
}
