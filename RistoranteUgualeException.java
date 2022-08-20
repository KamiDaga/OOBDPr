import javax.swing.JOptionPane;

public class RistoranteUgualeException extends ErrorePersonalizzato {

	public void stampaMessaggio() {

		JOptionPane.showMessageDialog(null, "Esiste gia' un ristorante con questi dati!",
				"Attenzione!", JOptionPane.WARNING_MESSAGE);
	}
}
