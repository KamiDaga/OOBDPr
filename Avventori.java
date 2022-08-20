
public class Avventori 
{
	private String Nome;
	private String Cognome;
	private String N_CID;
	private String N_tel = new String();
	
	public Avventori(String nome, String cognome, String n_CID, String n_tel) 
	{
		super();
		Nome = nome;
		Cognome = cognome;
		N_CID = n_CID;
		N_tel = n_tel;
	}
	
	public String toString()
	{
		return "<html>"+Nome+" "+Cognome+" "+N_CID+ ( N_tel.isEmpty()? "" : "<b> N. telefono</b>= <i>"+N_tel+"</i> </html>");
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getN_CID() {
		return N_CID;
	}

	public void setN_CID(String n_CID) {
		N_CID = n_CID;
	}

	public String getN_tel() {
		return N_tel;
	}

	public void setN_tel(String n_tel) {
		N_tel = n_tel;
	}
	
	
}
