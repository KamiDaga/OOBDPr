import java.util.ArrayList;

public class Mese{
	
	private Integer ValoreNumerico;

	public String toString(){
		switch(ValoreNumerico) {
		case 1: return "Gennaio"; 
		case 2: return "Febbraio";
		case 3: return "Marzo";
		case 4: return "Aprile";
		case 5: return "Maggio";
		case 6: return "Giugno";
		case 7: return "Luglio";
		case 8: return "Agosto";
		case 9: return "Settembre";
		case 10: return "Ottobre";
		case 11: return "Novembre";
		case 12: return"Dicembre";
		default: return null;
		}
	}

	public Integer getValoreNumerico() {
		return ValoreNumerico;
	}

	public void setValoreNumerico(Integer valoreNumerico) {
		this.ValoreNumerico = valoreNumerico;
	}
	
	public void setDaStringa(String meseStringa) throws MeseErratoException {
		
		String meseStringaFormattato = meseStringa.toUpperCase();
		MeseErratoException ecc = new MeseErratoException();
		
		switch(meseStringaFormattato) {
		case "GENNAIO": this.ValoreNumerico = 1;
			break;
		case "FEBBRAIO": this.ValoreNumerico = 2;
			break;
		case "MARZO": this.ValoreNumerico = 3;
			break;
		case "APRILE": this.ValoreNumerico = 4;
			break;
		case "MAGGIO": this.ValoreNumerico = 5;
			break;
		case "GIUGNO": this.ValoreNumerico = 6;
			break;
		case "LUGLIO": this.ValoreNumerico = 7;
			break;
		case "AGOSTO": this.ValoreNumerico = 8;
			break;
		case "SETTEMBRE": this.ValoreNumerico = 9;
			break;
		case "OTTOBRE": this.ValoreNumerico = 10;
			break;
		case "NOVEMBRE": this.ValoreNumerico = 11;
			break;
		case "DICEMBRE": this.ValoreNumerico = 12;
			break;
		default: throw ecc;
		}
	}
}
