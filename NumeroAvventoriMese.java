
public class NumeroAvventoriMese {
	
	private Integer NumAvventori;
	private Mese MeseCorrente = new Mese();
	private Integer Anno;
	
	NumeroAvventoriMese(Integer numeroAvventoriCorrente, Integer meseIntero, Integer annoCorrente){
		
		this.NumAvventori = numeroAvventoriCorrente;
		this.MeseCorrente.setValoreNumerico(meseIntero);	
		this.Anno = annoCorrente;
		
	}
	
	public Integer getNumAvventori() {
		return NumAvventori;
	}

	public void setNumAvventori(Integer numAvventori) {
		this.NumAvventori = numAvventori;
	}

	public Mese getMese() {
		return MeseCorrente;
	}

	public void setMese(Mese mese) {
		this.MeseCorrente = mese;
	}

	public Integer getAnno() {
		return Anno;
	}

	public void setAnno(Integer anno) {
		this.Anno = anno;
	}
}
