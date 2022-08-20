import java.util.ArrayList;

public interface NumeroAvventoriGiornoDAO {

	public abstract ArrayList<NumeroAvventoriGiorno> estraiStatisticheMese(Integer annoScelto, Integer meseScelto, Ristorante ristoranteScelto) throws OperazioneFallitaException;
	
}
