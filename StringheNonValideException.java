import javax.swing.JOptionPane;

public class StringheNonValideException extends ErrorePersonalizzato {

	@Override
	void stampaMessaggio() {
		JOptionPane.showMessageDialog(null, "Una o piu' delle parole inserite contengono caratteri non validi!",
				"Attenzione!", JOptionPane.WARNING_MESSAGE);

	}

}
