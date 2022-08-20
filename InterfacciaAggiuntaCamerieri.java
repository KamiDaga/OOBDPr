import javax.swing.JFrame;


import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JCalendar;

public class InterfacciaAggiuntaCamerieri extends JFrame
{
	private SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
	private Ristorante ristorante;
	private Controller theController;
	private JTextField textFieldNome;
	private JTextField textFieldCognome;
	private JTextField textFieldCID;
	
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	private JButton bottoneData;
	
	private JLabel nCaratteriNome;
	private JLabel nCaratteriCognome;
	private JLabel nCaratteriCID;
	private JLabel etichettaNome;
	private JLabel etichettaCognome;
	private JLabel etichettaCID;
	private JLabel etichettaDataAssunzione;
	
	public InterfacciaAggiuntaCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Modulo di assunzione di "+ristorante.getNome());
		getContentPane().setForeground(Color.WHITE);
		this.theController = theController;
		this.ristorante = ristorante;
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		setBounds(100,100,442,244);
		
		etichettaNome = new JLabel("Nome");
		etichettaNome.setForeground(Color.WHITE);
		etichettaNome.setBounds(33, 11, 46, 14);

		getContentPane().add(etichettaNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(33, 36, 142, 20);
		getContentPane().add(textFieldNome);
		textFieldNome.setColumns(30);
		
		etichettaCognome = new JLabel("Cognome");
		etichettaCognome.setForeground(Color.WHITE);
		etichettaCognome.setBounds(224, 11, 149, 14);

		getContentPane().add(etichettaCognome);
		
		textFieldCognome = new JTextField();
		textFieldCognome.setBounds(224, 36, 161, 20);
		getContentPane().add(textFieldCognome);
		textFieldCognome.setColumns(30);
		
		etichettaCID = new JLabel("Numero CID");
		etichettaCID.setForeground(Color.WHITE);
		etichettaCID.setBounds(33, 75, 86, 14);

		getContentPane().add(etichettaCID);
		
		textFieldCID = new JTextField();
		textFieldCID.setBounds(33, 100, 142, 20);
		getContentPane().add(textFieldCID);
		textFieldCID.setColumns(9);
		
		etichettaDataAssunzione = new JLabel("Data di assunzione");
		etichettaDataAssunzione.setForeground(Color.WHITE);
		etichettaDataAssunzione.setBounds(224, 75, 149, 14);

		getContentPane().add(etichettaDataAssunzione);
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(296, 172, 89, 23);
		bottoneOk.setEnabled(false);
		getContentPane().add(bottoneOk);
		bottoneOk.setBorder(null);
		bottoneOk.setBackground(new Color(0, 255, 127));
		
		textFieldNome.setFocusable(true);
		textFieldCognome.setFocusable(true);
		textFieldCID.setFocusable(true);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(30, 172, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		
		nCaratteriNome = new JLabel("");
		nCaratteriNome.setForeground(Color.WHITE);
		nCaratteriNome.setBounds(185, 39, 46, 14);

		getContentPane().add(nCaratteriNome);
		nCaratteriNome.setText("0");
		
		nCaratteriCognome = new JLabel("");
		nCaratteriCognome.setForeground(Color.WHITE);
		nCaratteriCognome.setBounds(395, 39, 46, 14);

		getContentPane().add(nCaratteriCognome);
		nCaratteriCognome.setText("0");
		
		nCaratteriCID = new JLabel("");
		nCaratteriCID.setForeground(Color.WHITE);
		nCaratteriCID.setBounds(185, 103, 46, 14);

		getContentPane().add(nCaratteriCID);
		nCaratteriCID.setText("0");
		
		bottoneData = new JButton("Scegli una data");
		bottoneData.setBounds(224, 99, 161, 23);
		getContentPane().add(bottoneData);
		bottoneData.setBorder(null);
		bottoneData.setBackground(new Color(0, 255, 127));

		GestoreTesti handlerTesti = new GestoreTesti();
		
		textFieldNome.getDocument().addDocumentListener(handlerTesti);
		textFieldCognome.getDocument().addDocumentListener(handlerTesti);
		textFieldCID.getDocument().addDocumentListener(handlerTesti);
		
		GestoreBottoni handlerBottoni = new GestoreBottoni();
		
		bottoneOk.addActionListener(handlerBottoni);
		bottoneIndietro.addActionListener(handlerBottoni);
		bottoneData.addActionListener(handlerBottoni);
		
		setResizable(false);
		setVisible(true);
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == bottoneOk)
			{
				String esito = "";
				boolean valido = true;
				try
				{
					formatoData.parse(bottoneData.getText());
				}
				catch(ParseException p)
				{
					valido = false;
				}
				if (valido && textFieldCognome.getText().length()<= 30 && textFieldNome.getText().length() <=30 && textFieldCID.getText().length()==9 )
					{
						Cameriere inAggiunta = new Cameriere(textFieldCID.getText(),textFieldNome.getText(),textFieldCognome.getText(), ristorante.getId_Ristorante());
						inAggiunta.setData_Ammissione(bottoneData.getText());
						esito = theController.bottoneOkAggiuntaCamerieriPremutoSuccessful(inAggiunta);
						if(esito.equals("Nessun_Errore"))
						{
							textFieldNome.selectAll();
							textFieldNome.replaceSelection("");
							textFieldCognome.selectAll();
							textFieldCognome.replaceSelection("");
							textFieldCID.selectAll();
							textFieldCID.replaceSelection("");
							bottoneData.setText("Scegli una data");
							nCaratteriCID.setText("");
							nCaratteriNome.setText("");
							nCaratteriCognome.setText("");
							JOptionPane.showMessageDialog(null,"Il cameriere e' stato aggiunto correttamente al database","Conferma",JOptionPane.INFORMATION_MESSAGE);
						}
						else if (esito.equals("CID_Non_Valido"))
						{
							JOptionPane.showMessageDialog(null, "Il numero CID non risulta valido. Si prega di riinserirlo.", "Errore!", JOptionPane.ERROR_MESSAGE);
							textFieldCID.selectAll();
							textFieldCID.replaceSelection("");
							nCaratteriCID.setText("");
						}
						else if (esito.equals("NomeCognome_Non_Validi")) 
						{
							JOptionPane.showMessageDialog(null, "Il nome o il cognome presentano caratteri non validi. Si prega di riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
							textFieldNome.selectAll();
							textFieldNome.replaceSelection("");
							textFieldCognome.selectAll();
							textFieldCognome.replaceSelection("");
							nCaratteriNome.setText("");
							nCaratteriCognome.setText("");
						}
						else if (esito.equals("CID_Gia_Presente"))
						{
							JOptionPane.showMessageDialog(null, "Esiste gia' un cameriere con questo CID!", "Errore!", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "C'e' stato un errore di connessione. Riprovare!", "Errore!", JOptionPane.ERROR_MESSAGE);
						}
				}
				else
				JOptionPane.showMessageDialog(null, "Si prega di controllare le dimensioni dei valori nelle caselle di testo (Nome, Cognome al piu' 30, CID deve essere esattamente 9.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
			else if (e.getSource() == bottoneIndietro)
			{
				theController.bottoneTornaIndietroAggiuntaCamerieriPremuto(ristorante);
			}
			else if (e.getSource() == bottoneData)
			{
				if(textFieldNome.getText().isBlank() || textFieldCognome.getText().isBlank() || 
						textFieldCID.getText().isBlank())
				{
					bottoneOk.setEnabled(false);
				}
				else
					bottoneOk.setEnabled(true);
				
				theController.bottoneSceltaDataDaAggiuntaCameriere();
			}
		}
	}
	
	
	private class GestoreTesti implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			
			if (e.getDocument() == textFieldNome.getDocument())
			{
				nCaratteriNome.setText(String.format("%d", textFieldNome.getText().length()));
			}
			else if(e.getDocument() == textFieldCognome.getDocument())
			{
				nCaratteriCognome.setText(String.format("%d", textFieldCognome.getText().length()));
			}
			else if(e.getDocument() == textFieldCID.getDocument())
			{
				nCaratteriCID.setText(String.format("%d", textFieldCID.getText().length()));
			}
			if(textFieldNome.getText().isBlank() || textFieldCognome.getText().isBlank() || 
					textFieldCID.getText().isBlank() || bottoneData.getText().equals("Scegli una data"))
			{
				bottoneOk.setEnabled(false);
			}
			else
				bottoneOk.setEnabled(true);
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			
			if (e.getDocument() == textFieldNome.getDocument())
			{
				nCaratteriNome.setText(String.format("%d", textFieldNome.getText().length()));
			}
			else if(e.getDocument() == textFieldCognome.getDocument())
			{
				nCaratteriCognome.setText(String.format("%d", textFieldCognome.getText().length()));
			}
			else if(e.getDocument() == textFieldCID.getDocument())
			{
				nCaratteriCID.setText(String.format("%d", textFieldCID.getText().length()));
			}
			if(textFieldNome.getText().isBlank() || textFieldCognome.getText().isBlank() || 
					textFieldCID.getText().isBlank() || bottoneData.getText().equals("Scegli una data"))
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


	public void dataScelta(String dataCorrente) {
		bottoneData.setText(dataCorrente);
	}
}
