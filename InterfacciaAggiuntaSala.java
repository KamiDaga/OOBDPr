import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class InterfacciaAggiuntaSala extends JFrame
{
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	private Ristorante ristorante;
	private JTextField nomeSala;
	private Controller theController;
	private JLabel contaCaratteri;
	private JLabel etichettaInserisciNome;
	
	public InterfacciaAggiuntaSala(Ristorante ristorante, Controller theController) 
	{
		super("Aggiunta Sala");
		setResizable(false);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		GestoreBottoni GestoreNomeSala = new GestoreBottoni();
		this.theController = theController;
		this.ristorante = ristorante;
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		setBounds(100,100,320,154);
		
		getContentPane().setBackground(new Color(20, 20, 40));

		
		nomeSala = new JTextField();
		nomeSala.setBounds(10, 30, 86, 20);
		nomeSala.setColumns(40);
	    getContentPane().add(nomeSala);
		
		etichettaInserisciNome = new JLabel("Nome della sala");
		etichettaInserisciNome.setForeground(Color.WHITE);
		etichettaInserisciNome.setBounds(10, 11, 200, 14);
		getContentPane().add(etichettaInserisciNome);
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(107, 65, 53, 20);
		bottoneOk.setBorder(null);
		bottoneOk.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneOk);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 64, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneIndietro);
		
		nomeSala.getDocument().addDocumentListener(new ContaCaratteri());
		nomeSala.setFocusable(true);
		contaCaratteri = new JLabel("0");
		contaCaratteri.setForeground(Color.WHITE);
		contaCaratteri.setBounds(106, 33, 46, 14);
		getContentPane().add(contaCaratteri);
		bottoneOk.addActionListener(GestoreNomeSala);
		bottoneIndietro.addActionListener(GestoreNomeSala);
		setVisible(true);

	}

	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==bottoneOk)
			{	
				boolean nomeTroppoLungo = false;
				if(nomeSala.getText().isBlank())
				{
					JOptionPane.showMessageDialog(null,"Non puoi inserire una sala senza nome!","Errore", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					if(nomeSala.getText().length()>40)
						nomeTroppoLungo = true;
				
					if (nomeTroppoLungo)
					{
						JOptionPane.showMessageDialog(null,"Il nome inserito e'  troppo lungo (piu' di 40 caratteri). "
								+ "Si prega di riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
						nomeSala.selectAll();
						nomeSala.replaceSelection("");
					}
					else
					{
						theController.interfacciaCreazioneSalaOkPremuto(nomeSala.getText(), ristorante);
					}
				}
			}
			else
			{
				theController.bottoneTornaIndietroInterfacciaCreazioneSalaPremuto(ristorante);
			}
		}
	}
	private class ContaCaratteri implements DocumentListener{
		@Override
		public void insertUpdate(DocumentEvent e) {
			contaCaratteri.setText(String.format("%d", nomeSala.getText().length()));
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			contaCaratteri.setText(String.format("%d", nomeSala.getText().length()));
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			
		}
		
	}
}