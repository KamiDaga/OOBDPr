import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InterfacciaModificaSala extends JFrame {

	private JButton BottoneOk;
	private JButton bottoneIndietro;
	private JTextField textFieldNomeSala;
	private Controller theController;
	private Sala sala;
	private JLabel contaCaratteri;
	private JLabel etichettaInserisciNome;
	
	public InterfacciaModificaSala(Controller theController, Sala salaScelta) 
	{
		super("Modifica i dati della sala " + salaScelta.getNome());
		setResizable(false);
		getContentPane().setLayout(null);
		GestoreBottoni GestoreNomeSala = new GestoreBottoni();
		this.theController = theController;
		this.sala = salaScelta;
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		setBounds(100,100,320,154);
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		textFieldNomeSala = new JTextField();
		textFieldNomeSala.setBounds(10, 30, 86, 20);
		textFieldNomeSala.setColumns(40);
	    getContentPane().add(textFieldNomeSala);
	    textFieldNomeSala.setText(sala.getNome());
		
		etichettaInserisciNome = new JLabel("Nome della sala");
		etichettaInserisciNome.setForeground(new Color(255, 255, 255));
		etichettaInserisciNome.setBounds(10, 11, 200, 14);
		getContentPane().add(etichettaInserisciNome);
		
		BottoneOk = new JButton("Ok");
		BottoneOk.setBounds(107, 65, 53, 20);
		getContentPane().add(BottoneOk);
		BottoneOk.setBackground(new Color(0, 255, 127));
		BottoneOk.setBorder(null);
		BottoneOk.setOpaque(true);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 64, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		
		textFieldNomeSala.getDocument().addDocumentListener(new ContaCaratteri());
		textFieldNomeSala.setFocusable(true);
		contaCaratteri = new JLabel(String.format("%d", textFieldNomeSala.getText().length()));
		contaCaratteri.setForeground(new Color(255, 255, 255));
		contaCaratteri.setBounds(106, 33, 46, 14);
		getContentPane().add(contaCaratteri);
		BottoneOk.addActionListener(GestoreNomeSala);
		bottoneIndietro.addActionListener(GestoreNomeSala);
		setVisible(true);
		
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==BottoneOk)
			{	
				boolean nomeTroppoLungo = false;
				if(textFieldNomeSala.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null,"Non puoi inserire una sala senza nome!","Errore", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(textFieldNomeSala.getText().length()>40)
						nomeTroppoLungo = true;
				
					if (nomeTroppoLungo)
					{
						JOptionPane.showMessageDialog(null,"Il nome inserito e'  troppo lungo (piu' di 40 caratteri). Si prega di riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
						textFieldNomeSala.selectAll();
						textFieldNomeSala.replaceSelection("");
					}
					else
					{
						theController.interfacciaModificaSalaOkPremuto(textFieldNomeSala.getText(), sala);
					}
				}
			}
			else
			{
				theController.bottoneTornaIndietroInterfacciaModificaSalaPremuto(sala.getRistoranteDiAppartenenza());
			}
		}
	}
	
	private class ContaCaratteri implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			contaCaratteri.setText(String.format("%d", textFieldNomeSala.getText().length()));
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			contaCaratteri.setText(String.format("%d", textFieldNomeSala.getText().length()));
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			
			
		}
		
	}
}

