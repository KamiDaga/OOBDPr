import java.util.ArrayList;
import java.util.Date;

public interface CameriereDAO 
{
	public ArrayList<Cameriere> estraiCamerieriInServizio(Ristorante ristorante);
	
	public ArrayList<Cameriere> estraiCamerieriLicenziati(Ristorante ristorante);
	
	public boolean riassumiCameriereLicenziato(Cameriere c,String data);
	
	public String licenziaCameriereAssunto(Cameriere c, String data);

	public String assumiNuovoCameriere(Cameriere c);

	public void rimuoviCameriereDalTavoloInData(Cameriere c, String data, int idTavolo);
	
	public ArrayList<Cameriere> camerieriInServizioAlTavoloInData(int idTavolo, String data);
	
	public ArrayList<Cameriere> camerieriAssegnabiliAlTavoloInData(String data, Ristorante ristorante);
	
	public boolean cameriereOccupatoDopoDiData (Cameriere cameriere, String data);
	
	public void inserimentoMultiploCamerieriInServizio(ArrayList<Cameriere> listaCamerieri, String data, Tavolo tavolo);
}
