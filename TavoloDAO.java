import java.util.ArrayList;


public interface TavoloDAO 
{	
	public ArrayList<Tavolo> estraiTavoliSala(Sala salaScelta);
	
	public void modificaPosizioniTavoli(ArrayList<Tavolo> tavoli) throws OperazioneFallitaException;
	
	public ArrayList<Integer> tavoliOccupatiInData(String data, Sala sala);
	
	public void inserisciNuovoTavolo(Tavolo nuovoTavolo) throws OperazioneFallitaException, TavoloNumeroUgualeException;
	
	public ArrayList<Tavolo> ricavaTavoliAdiacenti(Tavolo tavoloScelto) throws OperazioneFallitaException;
	
	public void rimpiazzaAdiacenze(Tavolo tavoloProtagonista) throws OperazioneFallitaException;
	
	public void modificaDatiTavolo(Tavolo tavoloScelto, int numeroCorrente) throws OperazioneFallitaException, TavoloNumeroUgualeException;
	
	public void eliminaTavolo(Tavolo tavolo) throws OperazioneFallitaException;
	
}
