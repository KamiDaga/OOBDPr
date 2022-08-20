import java.util.ArrayList;

public class Tavolo 
{
	private int Id_Tavolo;
	private int Capacita;
	private Sala Sala_App = new Sala();
	private int Numero;
	private int PosX;
	private int PosY;
	private int DimX;
	private int DimY;
	private ArrayList<Tavolo> TavoliAdiacenti = new ArrayList<Tavolo>();
		
	public Tavolo(int id_Tavolo, int numero, int capacita, int posX, int posY, int dimX, int dimY, Sala sala) {
		super();
		Id_Tavolo = id_Tavolo;
		Numero = numero;
		Capacita = capacita;
		PosX = posX;
		PosY = posY;
		DimX = dimX;
		DimY = dimY;
		TavoliAdiacenti = null;
		Sala_App = sala;
	}
	
	public Tavolo(int id_Tavolo, int capacita, Sala sala, int numero) {
		super();
		Id_Tavolo = id_Tavolo;
		Capacita = capacita;
		Sala_App = sala;
		Numero = numero;
	}

	public Tavolo(int id_Tavolo, int numero, int posX, int posY, int dimX, int dimY, ArrayList<Tavolo> tavoliAdiacentiScelti) {
		super();
		Id_Tavolo = id_Tavolo;
		Numero = numero;
		PosX = posX;
		PosY = posY;
		DimX = dimX;
		DimY = dimY;
		TavoliAdiacenti = tavoliAdiacentiScelti;
	}
	
	public Tavolo()
	{
		
	}
	
	public int getId_Tavolo() {
		return Id_Tavolo;
	}
	public void setId_Tavolo(int id_Tavolo) {
		Id_Tavolo = id_Tavolo;
	}

	public int getCapacita() {
		return Capacita;
	}
	public void setCapacita(int capacita) {
		Capacita = capacita;
	}
	public int getNumero() {
		return Numero;
	}
	public void setNumero(int numero) {
		Numero = numero;
	}
	public int getPosX() {
		return PosX;
	}
	public void setPosX(int posX) {
		PosX = posX;
	}
	public int getPosY() {
		return PosY;
	}
	public void setPosY(int posY) {
		PosY = posY;
	}
	public int getDimX() {
		return DimX;
	}
	public void setDimX(int dimX) {
		DimX = dimX;
	}
	public int getDimY() {
		return DimY;
	}
	public void setDimY(int dimY) {
		DimY = dimY;
	}

	public ArrayList<Tavolo> getTavoliAdiacenti() {
		return TavoliAdiacenti;
	}

	public void setTavoliAdiacenti(ArrayList<Tavolo> tavoliAdiacenti) {
		this.TavoliAdiacenti = tavoliAdiacenti;
	}
	public Sala getSala_App() {
		return Sala_App;
	}
	public void setSala_App(Sala sala_App) {
		Sala_App = sala_App;
	}
	
}
