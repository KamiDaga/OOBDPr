import javax.swing.JOptionPane;

public class NomeSalaUgualeException extends ErrorePersonalizzato {

	@Override
	void stampaMessaggio() {
		JOptionPane.showMessageDialog(null,"Questo nome e' gia' utilizzato per un'altra sala di questo ristorante!","Attenzione!",JOptionPane.WARNING_MESSAGE);
	}

}
