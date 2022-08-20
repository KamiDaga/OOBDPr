import javax.swing.JOptionPane;

public class MeseErratoException extends Throwable {
	
	public void stampaMessaggio(){
		JOptionPane.showMessageDialog(null, "Inserire un mese valido, in forma litterale o numerica!",
				"Attenzione!", JOptionPane.WARNING_MESSAGE);
	}
}
