
public class Tavolata 
{
	private int Id_Tavolata;
	private Tavolo TavoloAssociato;
	private String Data;
	
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		this.Data = data;
	}
	public Tavolo getTavoloAssociato() {
		return TavoloAssociato;
	}
	public void setTavoloAssociato(Tavolo tavoloAssociato) {
		this.TavoloAssociato = tavoloAssociato;
	}
	public Tavolata(Tavolo tavolo, String data) {
		super();
		this.TavoloAssociato = tavolo;
		this.Data = data;
	}
	
	
}
