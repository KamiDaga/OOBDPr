
public class NumeroAvventoriGiorno {

	private Integer NumAvventori;
	private Integer Giorno;
	private Mese MeseRiferimento = new Mese();
	private Integer AnnoRiferimento;
	
	NumeroAvventoriGiorno(Integer numeroAvventoriCorrente, Integer giornoCorrente, Integer meseIntero, Integer annoCorrente){
		
		this.NumAvventori = numeroAvventoriCorrente;
		this.Giorno = giornoCorrente;
		this.MeseRiferimento.setValoreNumerico(meseIntero);
		this.AnnoRiferimento = annoCorrente;
	}

	public Integer getNumAvventori() {
		return NumAvventori;
	}

	public void setNumAvventori(Integer numAvventori) {
		NumAvventori = numAvventori;
	}

	public Integer getGiorno() {
		return Giorno;
	}

	public void setGiorno(Integer giorno) {
		Giorno = giorno;
	}

	public Mese getMeseRiferimento() {
		return MeseRiferimento;
	}

	public void setMeseRiferimento(Mese meseRiferimento) {
		MeseRiferimento = meseRiferimento;
	}

	public Integer getAnnoRiferimento() {
		return AnnoRiferimento;
	}

	public void setAnnoRiferimento(Integer annoRiferimento) {
		AnnoRiferimento = annoRiferimento;
	}
}
