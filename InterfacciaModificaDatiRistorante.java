import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class InterfacciaModificaDatiRistorante extends JFrame {
	
	private JPanel contentPane;
	private Controller theController;
	
	private JTextField textFieldNomeRistorante;
	private JTextField textFieldCittaRistorante;
	private JTextField textFieldViaRistorante;
	private JTextField textFieldN_CivicoRistorante;

	private JButton bottoneOk;
	private JButton bottoneIndietro;

	private JLabel etichettaN_CivicoRistorante;
	private JLabel etichettaViaRistorante;
	private JLabel etichettaCittaRistorante;
	private JLabel etichettaNomeRistorante;

	private JLabel nCaratteriNomeRistorante;
	private JLabel nCaratteriCitta;	
	private JLabel nCaratteriVia;

	public InterfacciaModificaDatiRistorante(Controller c, Ristorante ristoranteCorrente) {
		super ("Modifica dati del ristorante " + ristoranteCorrente.getNome());
		theController = c;
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(20,20,40));


		textFieldNomeRistorante = new JTextField();
		textFieldNomeRistorante.setBounds(206, 11, 260, 20);
		contentPane.add(textFieldNomeRistorante);
		textFieldNomeRistorante.setColumns(10);
		textFieldNomeRistorante.setText(ristoranteCorrente.getNome());
		
		etichettaNomeRistorante = new JLabel("Nome");
		etichettaNomeRistorante.setForeground(Color.WHITE);
		etichettaNomeRistorante.setBounds(10, 11, 235, 20);

		contentPane.add(etichettaNomeRistorante);

		textFieldCittaRistorante = new JTextField();
		textFieldCittaRistorante.setBounds(206, 42, 260, 20);
		contentPane.add(textFieldCittaRistorante);
		textFieldCittaRistorante.setColumns(10);
		textFieldCittaRistorante.setText(ristoranteCorrente.getCitta());
	
		etichettaCittaRistorante = new JLabel("Citta'");
		etichettaCittaRistorante.setForeground(Color.WHITE);
		etichettaCittaRistorante.setBounds(10, 42, 235, 20);

		contentPane.add(etichettaCittaRistorante);

		textFieldViaRistorante = new JTextField();
		textFieldViaRistorante.setText("");
		textFieldViaRistorante.setBounds(206, 73, 260, 20);
		contentPane.add(textFieldViaRistorante);
		textFieldViaRistorante.setColumns(10);
		textFieldViaRistorante.setText(ristoranteCorrente.getVia());
	
		etichettaViaRistorante = new JLabel("Via");
		etichettaViaRistorante.setForeground(Color.WHITE);
		etichettaViaRistorante.setBounds(10, 73, 235, 20);

		contentPane.add(etichettaViaRistorante);

		textFieldN_CivicoRistorante = new JTextField();
		textFieldN_CivicoRistorante.setText("");
		textFieldN_CivicoRistorante.setBounds(206, 104, 260, 20);
		contentPane.add(textFieldN_CivicoRistorante);
		textFieldN_CivicoRistorante.setColumns(10);
		textFieldN_CivicoRistorante.setText(ristoranteCorrente.getN_Civico().toString());
		
		etichettaN_CivicoRistorante = new JLabel("Numero Civico");
		etichettaN_CivicoRistorante.setForeground(Color.WHITE);
		etichettaN_CivicoRistorante.setBounds(10, 104, 235, 20);

		contentPane.add(etichettaN_CivicoRistorante);

		bottoneOk = new JButton("Ok");
		bottoneOk.setBackground(new Color(0, 255, 127));
		bottoneOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (bottoneOk.isEnabled()) {

					try
					{
						String nomeCorrente = textFieldNomeRistorante.getText();
						String cittaCorrente = textFieldCittaRistorante.getText();
						String viaCorrente = textFieldViaRistorante.getText();
						Integer n_CivicoCorrente = Integer.parseInt(textFieldN_CivicoRistorante.getText());
						if (nomeCorrente.length()>40) {
							JOptionPane.showMessageDialog(null, "Il nome del ristorante puo' contenere al massimo 40 caratteri!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else if (cittaCorrente.length()>40) {
							JOptionPane.showMessageDialog(null, "Il nome della citta' puo' contenere al massimo 40 caratteri!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else if (viaCorrente.length()>40) {
							JOptionPane.showMessageDialog(null, "Il nome della via puo' contenere al massimo 40 caratteri!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else if (n_CivicoCorrente<1) {
							JOptionPane.showMessageDialog(null, "Il numero civico deve essere un numero valido!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
						}
						else theController.modificaRistoranteOkPremuto(ristoranteCorrente, nomeCorrente, viaCorrente, n_CivicoCorrente, cittaCorrente);
					}
					catch (NumberFormatException ecc)
					{
						JOptionPane.showMessageDialog(null, "Il numero civico deve essere un numero!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}

				}
			}
		});

		bottoneOk.setBounds(467, 137, 63, 23);
		bottoneOk.setBorder(null);

		contentPane.add(bottoneOk);
		
		bottoneOk.setOpaque(true);
		bottoneOk.setBorder(null);

		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneIndietroModificaRistorantePremuto();
			}
		});

		bottoneIndietro.setBounds(10, 135, 89, 23);
		bottoneIndietro.setBorder(null);

		contentPane.add(bottoneIndietro);
		
		bottoneIndietro.setOpaque(true);
		bottoneIndietro.setBorder(null);

		nCaratteriNomeRistorante = new JLabel(String.format("%d", textFieldNomeRistorante.getText().length()));
		nCaratteriNomeRistorante.setForeground(new Color(255, 255, 255));
		nCaratteriNomeRistorante.setBounds(494, 14, 36, 14);
		contentPane.add(nCaratteriNomeRistorante);

		nCaratteriCitta = new JLabel(String.format("%d", textFieldCittaRistorante.getText().length()));
		nCaratteriCitta.setForeground(new Color(255, 255, 255));
		nCaratteriCitta.setBounds(494, 45, 36, 14);
		contentPane.add(nCaratteriCitta);

		nCaratteriVia = new JLabel(String.format("%d", textFieldViaRistorante.getText().length()));
		nCaratteriVia.setForeground(new Color(255, 255, 255));
		nCaratteriVia.setBounds(494, 76, 36, 14);
		contentPane.add(nCaratteriVia);

		textFieldNomeRistorante.getDocument().addDocumentListener(new GestoreTesti());
		textFieldCittaRistorante.getDocument().addDocumentListener(new GestoreTesti());
		textFieldViaRistorante.getDocument().addDocumentListener(new GestoreTesti());
		textFieldN_CivicoRistorante.getDocument().addDocumentListener(new GestoreTesti());

		setVisible(true);
		setResizable(false);
	}

	private class GestoreTesti implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {

			if (e.getDocument() == textFieldNomeRistorante.getDocument())
			{
				nCaratteriNomeRistorante.setText(String.format("%d", textFieldNomeRistorante.getText().length()));
			}
			else if(e.getDocument() == textFieldCittaRistorante.getDocument())
			{
				nCaratteriCitta.setText(String.format("%d", textFieldCittaRistorante.getText().length()));
			}
			else if(e.getDocument() == textFieldViaRistorante.getDocument())
			{
				nCaratteriVia.setText(String.format("%d", textFieldViaRistorante.getText().length()));
			}
			if(textFieldNomeRistorante.getText().isBlank() || textFieldCittaRistorante.getText().isBlank() || 
					textFieldViaRistorante.getText().isBlank() || textFieldN_CivicoRistorante.getText().isBlank())
			{
				bottoneOk.setEnabled(false);
			}
			else
				bottoneOk.setEnabled(true);

		}

		@Override
		public void removeUpdate(DocumentEvent e) {

			if (e.getDocument() == textFieldNomeRistorante.getDocument())
			{
				nCaratteriNomeRistorante.setText(String.format("%d", textFieldNomeRistorante.getText().length()));
			}
			else if(e.getDocument() == textFieldCittaRistorante.getDocument())
			{
				nCaratteriCitta.setText(String.format("%d", textFieldCittaRistorante.getText().length()));
			}
			else if(e.getDocument() == textFieldViaRistorante.getDocument())
			{
				nCaratteriVia.setText(String.format("%d", textFieldViaRistorante.getText().length()));
			}
			if(textFieldNomeRistorante.getText().isBlank() || textFieldCittaRistorante.getText().isBlank() || 
					textFieldViaRistorante.getText().isBlank() || textFieldN_CivicoRistorante.getText().isBlank())
			{
				bottoneOk.setEnabled(false);
			}
			else
				bottoneOk.setEnabled(true);

		}

		@Override
		public void changedUpdate(DocumentEvent e) {

		}

	}
}
