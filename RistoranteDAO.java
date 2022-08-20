

import java.util.ArrayList;

public interface RistoranteDAO {

	public ArrayList<Ristorante> estraiTuttiRistoranti() throws OperazioneFallitaException;
	
	public void inserisciRistorante(String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException, RistoranteUgualeException, StringheNonValideException;

	public void modificaRistorante(Ristorante ristorante, String nome, String via, Integer n_Civico, String citta) throws OperazioneFallitaException, RistoranteUgualeException, StringheNonValideException;
	
	public void eliminaRistorante(Ristorante ristoranteCorrente) throws OperazioneFallitaException;
}
