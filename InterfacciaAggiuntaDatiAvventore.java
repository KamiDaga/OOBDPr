import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class InterfacciaAggiuntaDatiAvventore extends JFrame 
{
	private JTextField nome;
	private JTextField cognome;
	private JTextField cid;
	private JTextField ntel;
	private JButton bottoneProssimoAvventore;
	private JButton bottoneAvventorePrecedente;
	private boolean ultima = false;
	private int indice;
	private boolean intero;
	private Controller theController;
	private ArrayList<Tavolo> tavoli;
	private int dimensioneTotale;
	private int tavoloScelto;
	private String data;
	private JLabel contaNome;
	private JLabel contaCognome;
	private JLabel contaCid;
	private JLabel contaNtel;
	private JButton bottoneIndietro;
	private boolean diVisualizzazione = false;
	private ArrayList<String> avventoriDelTavolo;
	private JLabel etichettaCid;
	private JLabel etichettaNome;
	private JLabel etichettaCognome;
	private JLabel etichettaNTel;
	

	public InterfacciaAggiuntaDatiAvventore(Controller controller, int indice, ArrayList<Tavolo> tavoli, int tavoloScelto, String data) 
	{
		super("Inserimento dati avventore "+ (indice+1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 220);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		this.tavoloScelto = tavoloScelto;
		this.tavoli = tavoli;
		this.theController = controller;
		this.indice = indice;
		this.data = data;
		nome = new JTextField();
		nome.setBounds(30, 40, 86, 20);
		getContentPane().add(nome);
		nome.setColumns(10);
		etichettaNome = new JLabel("Nome");
		etichettaNome.setForeground(Color.WHITE);
		etichettaNome.setBounds(30, 15, 61, 14);
		getContentPane().add(etichettaNome);
		
		cognome = new JTextField();
		cognome.setBounds(30, 100, 86, 20);
		getContentPane().add(cognome);
		cognome.setColumns(10);
		
		etichettaCognome = new JLabel("Cognome");
		etichettaCognome.setForeground(Color.WHITE);
		etichettaCognome.setBounds(30, 71, 79, 14);
		getContentPane().add(etichettaCognome);
		
		cid = new JTextField();
		cid.setBounds(180, 40, 86, 20);
		getContentPane().add(cid);
		cid.setColumns(10);
		
		etichettaCid = new JLabel("Numero CID");
		etichettaCid.setForeground(Color.WHITE);
		etichettaCid.setBounds(180, 15, 72, 14);
		getContentPane().add(etichettaCid);
		
		ntel = new JTextField();
		ntel.setBounds(180, 100, 86, 20);
		getContentPane().add(ntel);
		ntel.setColumns(10);
		
		etichettaNTel = new JLabel("Numero di telefono");
		etichettaNTel.setForeground(Color.WHITE);
		etichettaNTel.setBounds(180, 71, 119, 14);
		getContentPane().add(etichettaNTel);
		
		bottoneProssimoAvventore = new JButton("Prossimo");
		bottoneProssimoAvventore.setBounds(237, 147, 117, 23);
		bottoneProssimoAvventore.setBorder(null);
		bottoneProssimoAvventore.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneProssimoAvventore);
		
		bottoneAvventorePrecedente = new JButton("Precedente");
		bottoneAvventorePrecedente.setBounds(109, 147, 105, 23);
		bottoneAvventorePrecedente.setBorder(null);
		bottoneAvventorePrecedente.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneAvventorePrecedente);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 147, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		
		getContentPane().add(bottoneIndietro);
		
		contaNome = new JLabel("0");
		contaNome.setForeground(Color.WHITE);
		contaNome.setBounds(124, 40, 46, 23);
		getContentPane().add(contaNome);
		
		contaCognome = new JLabel("0");
		contaCognome.setForeground(Color.WHITE);
		contaCognome.setBounds(124, 103, 46, 14);
		getContentPane().add(contaCognome);
		
		contaCid = new JLabel("0");
		contaCid.setForeground(Color.WHITE);
		contaCid.setBounds(276, 43, 46, 14);
		getContentPane().add(contaCid);
		
		contaNtel = new JLabel("0");
		contaNtel.setForeground(Color.WHITE);
		contaNtel.setBounds(276, 103, 46, 14);
		getContentPane().add(contaNtel);
		bottoneIndietro.addActionListener(new GestioneBottoni());
		bottoneProssimoAvventore.addActionListener(new GestioneBottoni());
		bottoneAvventorePrecedente.addActionListener(new GestioneBottoni());
	
		if(indice == 0 ) 
		{
			bottoneAvventorePrecedente.setEnabled(false);
			bottoneAvventorePrecedente.setVisible(false);
		}
		
		
		nome.getDocument().addDocumentListener(new GestoreConta());
		cognome.getDocument().addDocumentListener(new GestoreConta());
		cid.getDocument().addDocumentListener(new GestoreConta());
		ntel.getDocument().addDocumentListener(new GestoreConta());
		nome.setFocusable(true);
		cognome.setFocusable(true);
		ntel.setFocusable(true);
		cid.setFocusable(true);
		
		setResizable(false);
	}

	public InterfacciaAggiuntaDatiAvventore(Controller controller, int indice, ArrayList<Tavolo> tavoli, int tavoloScelto, String data, ArrayList<String> avventoriDelTavolo) 
	{
		super("Inserimento dati avventore "+ (indice+1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 220);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		this.tavoloScelto = tavoloScelto;
		this.tavoli = tavoli;
		this.theController = controller;
		this.indice = indice;
		this.data = data;
		this.diVisualizzazione= true;
		this.avventoriDelTavolo = avventoriDelTavolo;
		nome = new JTextField();
		nome.setBounds(30, 40, 86, 20);
		getContentPane().add(nome);
		nome.setColumns(10);
		etichettaNome = new JLabel("Nome");
		etichettaNome.setForeground(Color.WHITE);
		etichettaNome.setBounds(30, 15, 61, 14);
		getContentPane().add(etichettaNome);
		
		cognome = new JTextField();
		cognome.setBounds(30, 100, 86, 20);
		getContentPane().add(cognome);
		cognome.setColumns(10);
		
		etichettaCognome = new JLabel("Cognome");
		etichettaCognome.setForeground(Color.WHITE);
		etichettaCognome.setBounds(30, 71, 79, 14);
		getContentPane().add(etichettaCognome);
		
		cid = new JTextField();
		cid.setBounds(180, 40, 86, 20);
		getContentPane().add(cid);
		cid.setColumns(10);
		
		etichettaCid = new JLabel("Numero CID");
		etichettaCid.setForeground(Color.WHITE);
		etichettaCid.setBounds(180, 15, 72, 14);
		getContentPane().add(etichettaCid);
		
		ntel = new JTextField();
		ntel.setBounds(180, 100, 86, 20);
		getContentPane().add(ntel);
		ntel.setColumns(10);
		
		etichettaNTel = new JLabel("Numero di telefono");
		etichettaNTel.setForeground(Color.WHITE);
		etichettaNTel.setBounds(180, 71, 119, 14);
		getContentPane().add(etichettaNTel);
		
		bottoneProssimoAvventore = new JButton("Prossimo");
		bottoneProssimoAvventore.setBounds(237, 147, 117, 23);
		bottoneProssimoAvventore.setBorder(null);
		bottoneProssimoAvventore.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneProssimoAvventore);
		
		bottoneAvventorePrecedente = new JButton("Precedente");
		bottoneAvventorePrecedente.setBounds(109, 147, 105, 23);
		bottoneAvventorePrecedente.setBorder(null);
		bottoneAvventorePrecedente.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneAvventorePrecedente);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 147, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneIndietro);
		
		contaNome = new JLabel("0");
		contaNome.setForeground(Color.WHITE);
		contaNome.setBounds(124, 40, 46, 23);
		getContentPane().add(contaNome);
		
		contaCognome = new JLabel("0");
		contaCognome.setForeground(Color.WHITE);
		contaCognome.setBounds(124, 103, 46, 14);
		getContentPane().add(contaCognome);
		
		contaCid = new JLabel("0");
		contaCid.setForeground(Color.WHITE);
		contaCid.setBounds(276, 43, 46, 14);
		getContentPane().add(contaCid);
		
		contaNtel = new JLabel("0");
		contaNtel.setForeground(Color.WHITE);
		contaNtel.setBounds(276, 103, 46, 14);
		getContentPane().add(contaNtel);
		bottoneIndietro.addActionListener(new GestioneBottoni());
		bottoneProssimoAvventore.addActionListener(new GestioneBottoni());
		bottoneAvventorePrecedente.addActionListener(new GestioneBottoni());
	
		if(indice == 0 ) 
		{
			bottoneAvventorePrecedente.setEnabled(false);
			bottoneAvventorePrecedente.setVisible(false);
		}
		
		
		nome.getDocument().addDocumentListener(new GestoreConta());
		cognome.getDocument().addDocumentListener(new GestoreConta());
		cid.getDocument().addDocumentListener(new GestoreConta());
		ntel.getDocument().addDocumentListener(new GestoreConta());
		nome.setFocusable(true);
		cognome.setFocusable(true);
		ntel.setFocusable(true);
		cid.setFocusable(true);
		
		setResizable(false);
	}
	
	
	public void impostaBottoniCorretti(int dimTot)
	{
		if (indice == dimTot-1)
		{
			this.dimensioneTotale = dimTot;
			bottoneProssimoAvventore.setText("Conferma");
			ultima = true;
		}
	}
	
	private class GestioneBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneProssimoAvventore)
			{
		
					if(!ntel.getText().isBlank())
					{						
						if(Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]",ntel.getText()))	intero = true;
						else intero = false;
					}
					else 
					{
						if (indice == 0) 
								intero = false;
						else 
							intero = true;
					}
			
		
				if(cid.getText().length() != 9 || nome.getText().length() >30 || cognome.getText().length() > 30 || !intero) JOptionPane.showMessageDialog(null, "Si controllino le dimensioni dei campi e di non aver inserito caratteri non validi come l'apostrofo. \nNome e cognome devono essere lunghi al piu 30, mentre CID deve essere lungo esattamente 9 caratteri, numero di telefono esattamente 10 caratteri decimali OPPURE vuoto."
						+ "\n(Il primo avventore di una tavolata deve inserire necessariamente il numero di telefono!)");
					
				else
					{
						if(ultima && !diVisualizzazione)
						{
							
							try
							{	
								theController.bottoneConfermaAggiuntaAvventoriPremuto(tavoli, tavoloScelto, data);		
							}
							catch(CampiNonCorrettiException c)
							{
								c.stampaMessaggio();
							}
						}
						else if(!ultima)
							{
								theController.bottoneAvventoreSuccessivoPremuto(indice);
							}
					
						else if (ultima && diVisualizzazione)
							{
								try 
								{
									if(avventoriDelTavolo.contains(cid.getText())) JOptionPane.showMessageDialog(null,"Esiste gia un avventore con lo stesso numero CID nella tavolata corrente!","Errore!", JOptionPane.ERROR_MESSAGE);
									else theController.bottoneConfermaAggiuntaAvventoreDiVisualizzazionePremuto(tavoli, tavoloScelto, data);	
								}
								catch (CampiNonCorrettiException e2) 
								{
									e2.stampaMessaggio();
								}
							}
					}

			}
			else if(e.getSource() == bottoneAvventorePrecedente)
			{
				theController.bottoneAvventorePrecedentePremuto(indice);
			}
			else if(e.getSource() == bottoneIndietro)
			{
				if(diVisualizzazione) theController.bottoneIndietroAggiuntaAvventoreDiVisualizzazionePremuto(tavoli, tavoloScelto, data);
				else theController.bottoneIndietroAggiuntaAvventorePremuto(tavoli, data, indice);
			}
		}
		
	}
	
	private class GestoreConta implements DocumentListener
	{

		@Override
		public void insertUpdate(DocumentEvent e) {

			if(e.getDocument() == nome.getDocument())
			{
				contaNome.setText(String.format("%d", nome.getText().length()));
			}
			else if(e.getDocument() == cognome.getDocument())
			{	
				contaCognome.setText(String.format("%d", cognome.getText().length()));
			}
			else if(e.getDocument() == cid.getDocument())
			{
				contaCid.setText(String.format("%d", cid.getText().length()));
			}
			else if(e.getDocument() == ntel.getDocument())
			{
				contaNtel.setText(String.format("%d", ntel.getText().length()));
			}
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {

			if(e.getDocument() == nome.getDocument())
			{
				contaNome.setText(String.format("%d", nome.getText().length()));
			}
			else if(e.getDocument() == cognome.getDocument())
			{	
				contaCognome.setText(String.format("%d", cognome.getText().length()));
			}
			else if(e.getDocument() == cid.getDocument())
			{
				contaCid.setText(String.format("%d", cid.getText().length()));
			}
			else if(e.getDocument() == ntel.getDocument())
			{
				contaNtel.setText(String.format("%d", ntel.getText().length()));
			}
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			
		}
		
	}

	public JTextField getNome() {
		return nome;
	}

	public void setNome(JTextField nome) {
		this.nome = nome;
	}

	public JTextField getCognome() {
		return cognome;
	}

	public void setCognome(JTextField cognome) {
		this.cognome = cognome;
	}

	public JTextField getCid() {
		return cid;
	}

	public void setCid(JTextField cid) {
		this.cid = cid;
	}

	public JTextField getNtel() {
		return ntel;
	}

	public void setNtel(JTextField ntel) {
		this.ntel = ntel;
	}

	public ArrayList<Tavolo> getTavoli() {
		return tavoli;
	}

	public int getTavoloScelto() {
		return tavoloScelto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public JButton getProssimoAvventore() {
		return bottoneProssimoAvventore;
	}

	public void setProssimoAvventore(JButton prossimoAvventore) {
		this.bottoneProssimoAvventore = prossimoAvventore;
	}

	public boolean isDiVisualizzazione() {
		return diVisualizzazione;
	}

	public void setDiVisualizzazione(boolean diVisualizzazione) {
		this.diVisualizzazione = diVisualizzazione;
	}
	
}
	

