import javax.swing.JOptionPane;

public class OperazioneFallitaException extends ErrorePersonalizzato {

	public void stampaMessaggio(){
		JOptionPane.showMessageDialog(null, "C'e' stato un errore di connnessione, riprovare l'operazione.",
				"Errore!", JOptionPane.ERROR_MESSAGE);
	}
  
}