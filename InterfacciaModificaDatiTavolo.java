import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class InterfacciaModificaDatiTavolo extends JFrame {

	private JPanel contentPane;
	private Controller theController;
	private Tavolo tavoloScelto;
	private JTextField textFieldNumeroTavolo;
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	private int numeroCorrente;
	private JLabel etichettaNumeroTavolo;

	public InterfacciaModificaDatiTavolo(Controller c, Tavolo tavoloCorrente) {
		super ("Modifica i dati del tavolo numero " + tavoloCorrente.getNumero());
		
		theController = c;
		tavoloScelto = tavoloCorrente;
		
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 146);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		textFieldNumeroTavolo = new JTextField();
		textFieldNumeroTavolo.setBounds(10, 30, 86, 20);
		getContentPane().add(textFieldNumeroTavolo);
		textFieldNumeroTavolo.setText(((Integer)tavoloScelto.getNumero()).toString());

		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(225, 73, 89, 23);
		bottoneOk.setBorder(null);
		bottoneOk.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneOk);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 73, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneIndietro);
		
		bottoneOk.addActionListener(new GestoreBottoni());
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		etichettaNumeroTavolo = new JLabel("Numero del tavolo");
		etichettaNumeroTavolo.setForeground(Color.WHITE);
		etichettaNumeroTavolo.setBounds(10, 11, 163, 14);
		getContentPane().add(etichettaNumeroTavolo);
		
		setVisible(true);
		setResizable(false);
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource()==bottoneOk)
			{
				boolean valoriValidi = false;
				
				if(textFieldNumeroTavolo.getText().isBlank()== false)
				{
					try 
					{
						numeroCorrente = Integer.parseInt(textFieldNumeroTavolo.getText());
						
						if (numeroCorrente>0)
							valoriValidi = true;
						else
							valoriValidi = false;
					}
					catch(NumberFormatException c)
					{
						valoriValidi = false;
					}
				}
			
				if(valoriValidi) theController.bottoneConfermaModificheDatiTavoloPremuto(tavoloScelto, numeroCorrente);
				else JOptionPane.showMessageDialog(null,"Non sono stati inseriti dei numeri validi. Riprovare.","Errore!", JOptionPane.ERROR_MESSAGE);
			}
			
			else if (e.getSource() == bottoneIndietro) {
				theController.bottoneIndietroModificaDatiTavoloPremuto(tavoloScelto.getSala_App());
			}
		}
	}

}
