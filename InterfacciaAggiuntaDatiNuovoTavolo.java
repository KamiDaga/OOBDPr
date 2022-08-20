import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterfacciaAggiuntaDatiNuovoTavolo extends JFrame {
	private JTextField numeroTavolo;
	private JTextField capienzaTavolo;
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	private Controller theController;
	private Sala salaDiAppartenenza;
	private ArrayList<Tavolo> tavoliGiaEsistenti;
	JLabel etichettaNumeroTavolo;
	JLabel etichettaCapienzaTavolo;
	
	public InterfacciaAggiuntaDatiNuovoTavolo(Controller controller, Sala sala, ArrayList<Tavolo> tavoliGiaEsistenti)
	{
		super("Inserire i dati del nuovo tavolo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 199);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		this.theController=controller;
		this.salaDiAppartenenza=sala;
		this.tavoliGiaEsistenti = tavoliGiaEsistenti;
		
		numeroTavolo = new JTextField();
		numeroTavolo.setBounds(10, 30, 86, 20);
		getContentPane().add(numeroTavolo);

		capienzaTavolo = new JTextField();
		capienzaTavolo.setBounds(10, 75, 86, 20);
		getContentPane().add(capienzaTavolo);
		capienzaTavolo.setColumns(10);
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(10, 106, 89, 23);
		bottoneOk.setBorder(null);
		bottoneOk.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneOk);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(200, 106, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneIndietro);
		
		bottoneOk.addActionListener(new GestoreBottoni());
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		etichettaNumeroTavolo = new JLabel("Numero del tavolo");
		etichettaNumeroTavolo.setForeground(Color.WHITE);
		etichettaNumeroTavolo.setBounds(10, 11, 163, 14);
		getContentPane().add(etichettaNumeroTavolo);
		
		etichettaCapienzaTavolo = new JLabel("Capienza del tavolo");
		etichettaCapienzaTavolo.setForeground(Color.WHITE);
		etichettaCapienzaTavolo.setBounds(10, 61, 140, 14);
		getContentPane().add(etichettaCapienzaTavolo);
		
		setVisible(true);
		setResizable(false);

	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == bottoneOk)
			{
				boolean valoriValidi = false;
				Tavolo tavolo = new Tavolo();
				
				if(numeroTavolo.getText().isBlank()== false && capienzaTavolo.getText().isBlank()==false)
				{
					try 
					{
						tavolo.setNumero(Integer.parseInt(numeroTavolo.getText()));
						tavolo.setCapacita(Integer.parseInt(capienzaTavolo.getText()));
						if (tavolo.getNumero()>0 && tavolo.getCapacita()>0)
							valoriValidi = true;
						else
							valoriValidi = false;
					}
					catch(NumberFormatException c)
					{
						valoriValidi = false;
					}
				}
			
				if(valoriValidi) theController.bottoneOkInterfacciaAggiuntaCapienzaNumeroNuovoTavoloPremuto(tavolo,salaDiAppartenenza,tavoliGiaEsistenti);
				else JOptionPane.showMessageDialog(null,"Non sono stati inseriti dei numeri validi. Riprovare.","Errore!", JOptionPane.ERROR_MESSAGE);
			}
			else if (e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroInterfacciaAggiuntaCapienzaNumeroNuovoTavoloPremuto(salaDiAppartenenza);
			}
		}
	}
}