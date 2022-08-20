import java.util.ArrayList;

public interface NumeroAvventoriMeseDAO {

	public abstract ArrayList<NumeroAvventoriMese> estraiStatisticheAnno(Integer annoScelto, Ristorante ristoranteScelto) throws OperazioneFallitaException;
	
}
