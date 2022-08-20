
import javax.swing.JOptionPane;

public class CreazioneErrataDatabaseException extends ErroreIniziale {

	public void stampaMessaggio(){
		
		JOptionPane.showMessageDialog(null,"C'e' stato un errore, il database potrebbe"
				+ " non essere stato creato correttamente.\n"
				+ "E' consigliata la rimozione manuale del database dal DBMS "
				+ "e il riavvio dell'applicativo.", "Errore!", JOptionPane.ERROR_MESSAGE);
	}
}
