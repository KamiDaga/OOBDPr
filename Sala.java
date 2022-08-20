


public class Sala 
{
	private int Id_Sala;
	private String Nome;
	private Ristorante RistoranteDiAppartenenza = new Ristorante();
	
	public int getId_Sala() {
		return Id_Sala;
	}
	public void setId_Sala(int id_Sala) {
		Id_Sala = id_Sala;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String toString()
	{
		return Nome;
	}
	public Ristorante getRistoranteDiAppartenenza() {
		return RistoranteDiAppartenenza;
	}
	public void setRistoranteDiAppartenenza(Ristorante ristoranteDiAppartenenza) {
		RistoranteDiAppartenenza = ristoranteDiAppartenenza;
	}
}