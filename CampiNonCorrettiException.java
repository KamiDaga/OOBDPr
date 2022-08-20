import javax.swing.JOptionPane;

public class CampiNonCorrettiException extends ErrorePersonalizzato
{
	public void stampaMessaggio()
	{
		JOptionPane.showMessageDialog(null, "Si controllino i campi di tutti gli avventori, "
				+ "e ci si assicuri che ci sia almeno un numero di telefono e che tutti i campi Nome, Cognome, CID siano stati "
				+ "riempiti correttamente. Inoltre non si inseriscano duplicati di CID!");
	}
}
