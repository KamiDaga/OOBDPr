import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NumeroAvventoriMeseDAOImplPostgres implements NumeroAvventoriMeseDAO {

	@Override
	public ArrayList<NumeroAvventoriMese> estraiStatisticheAnno(Integer annoScelto, Ristorante ristoranteScelto) throws OperazioneFallitaException {
		
		try
		{
			ArrayList<NumeroAvventoriMese> listaStatistiche = new ArrayList<NumeroAvventoriMese>();
			Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
			ResultSet risultatoQuery;
			risultatoQuery = stmt.executeQuery(" SELECT EXTRACT (MONTH FROM T.DATA) AS MESE, "
										+   "COUNT (E_A.N_CID) AS NUMAVVENTORI "
										+	"FROM TAVOLATA AS T NATURAL JOIN ELENCO_AVVENTORI AS E_A "
										+	" NATURAL JOIN TAVOLO AS TAV NATURAL JOIN SALA AS S "
										+	"WHERE EXTRACT (YEAR FROM T.DATA) = " + annoScelto.toString()
										+	" AND S.Id_Ristorante = " + ristoranteScelto.getId_Ristorante()
										+	" GROUP BY EXTRACT (MONTH FROM T.DATA); ");
			
			while(risultatoQuery.next()) {
				Integer meseCorrente = risultatoQuery.getInt(1);
				Integer numAvventoriCorrente = risultatoQuery.getInt(2);
				NumeroAvventoriMese statisticheCorrenti = new NumeroAvventoriMese(numAvventoriCorrente, meseCorrente, annoScelto);
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
