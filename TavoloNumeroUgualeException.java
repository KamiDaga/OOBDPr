import javax.swing.JOptionPane;

public class TavoloNumeroUgualeException extends ErrorePersonalizzato {

	@Override
	void stampaMessaggio() {
		JOptionPane.showMessageDialog(null, "Esiste gia' un tavolo con questo numero in questa sala!",
				"Attenzione!", JOptionPane.WARNING_MESSAGE);

	}

}
