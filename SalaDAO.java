import java.util.ArrayList;

public interface SalaDAO 
{
	public ArrayList<Sala> estraiSaleRistorante(Ristorante ristoranteCorrente);
	
	public void RimuoviSalaRistorante(Sala s);
	
	public void AggiuntaSalaRistorante(String nomeSala, int id_ristorante) throws ErrorePersonalizzato;
	
	public void modificaSala(String nome, Sala sala) throws ErrorePersonalizzato;
}
