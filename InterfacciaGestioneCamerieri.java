import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfacciaGestioneCamerieri extends JFrame 
{
	private Ristorante ristorante;
	private Controller theController;
	private DefaultListModel<Cameriere> modelloListaAssunti = new  DefaultListModel<Cameriere>();
	private	DefaultListModel<Cameriere> modelloListaLicenziati = new DefaultListModel<Cameriere>();
	private JList<Cameriere> listaAssunti = new JList<Cameriere>(modelloListaAssunti);
	private JList<Cameriere> listaLicenziati = new JList<Cameriere>(modelloListaLicenziati);
	private ArrayList<Cameriere> arrayListAssunti = new ArrayList<Cameriere>();
	private ArrayList<Cameriere> arrayListLicenziati = new ArrayList<Cameriere>();
	private JButton bottoneRiassumiCameriere;
	private JButton bottoneLicenziaCameriere;
	private JButton bottoneAggiuntaCameriere;
	private int indiceListaAssunti = -1;
	private int indiceListaLicenziati = -1;
	private GestioneBottoniETesti handlerB = new GestioneBottoniETesti();
	private	GestioneListe handlerL = new GestioneListe();
	private JScrollPane scrollPaneAssunti;
	private JScrollPane scrollPaneLicenziati;
	private JLabel etichettaLicenziati;
	private JLabel etichettaAssunti;
	private JButton bottoneIndietro;
	
	
	public InterfacciaGestioneCamerieri(Ristorante ristorante, Controller theController)
	{
		super("Gestione camerieri di "+ristorante.getNome());
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		setBounds(100, 100, 444, 375);

		this.theController=theController;
		this.ristorante = ristorante;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		scrollPaneAssunti = new JScrollPane();
		scrollPaneAssunti.setBounds(10, 35, 225, 105);
		getContentPane().add(scrollPaneAssunti);
		
		
		scrollPaneAssunti.setViewportView(listaAssunti);
		
		scrollPaneLicenziati = new JScrollPane();
		scrollPaneLicenziati.setBounds(10, 182, 225, 105);
		getContentPane().add(scrollPaneLicenziati);
		
		scrollPaneLicenziati.setViewportView(listaLicenziati);
		
		etichettaAssunti = new JLabel("Correntemente assunti");
		etichettaAssunti.setForeground(Color.WHITE);
		etichettaAssunti.setBounds(10, 10, 350, 14);

		getContentPane().add(etichettaAssunti);
		
		etichettaLicenziati = new JLabel("Licenziati");
		etichettaLicenziati.setForeground(Color.WHITE);
		etichettaLicenziati.setBounds(10, 157, 99, 14);
		getContentPane().add(etichettaLicenziati);
		
		bottoneAggiuntaCameriere = new JButton("Aggiungi Cameriere");
		bottoneAggiuntaCameriere.setBounds(245, 33, 160, 23);
		bottoneAggiuntaCameriere.setBorder(null);
		bottoneAggiuntaCameriere.setBackground(new Color(0, 255, 127));

		getContentPane().add(bottoneAggiuntaCameriere);
		
		bottoneRiassumiCameriere = new JButton("Riassumi Cameriere");
		bottoneRiassumiCameriere.setBounds(245, 180, 160, 23);
		bottoneRiassumiCameriere.setEnabled(false);
		bottoneRiassumiCameriere.setBorder(null);
		bottoneRiassumiCameriere.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneRiassumiCameriere);
		
		bottoneLicenziaCameriere = new JButton("Licenzia Cameriere");
		bottoneLicenziaCameriere.setBounds(245, 62, 160, 23);
		bottoneLicenziaCameriere.setEnabled(false);
		bottoneLicenziaCameriere.setBorder(null);
		bottoneLicenziaCameriere.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneLicenziaCameriere);
		
		arrayListAssunti = theController.estraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.estraiCamerieriLicenziatiC(ristorante);
		
		modelloListaAssunti.removeAllElements();
		modelloListaLicenziati.removeAllElements();
		
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.addAll(arrayListLicenziati);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 298, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		
		listaLicenziati.addListSelectionListener(handlerL);
		bottoneRiassumiCameriere.addActionListener(handlerB);
		bottoneIndietro.addActionListener(handlerB);
		bottoneLicenziaCameriere.addActionListener(handlerB);
		listaAssunti.addListSelectionListener(handlerL);
		bottoneAggiuntaCameriere.addActionListener(handlerB);
		
		listaAssunti.setBackground(new Color(20, 20, 40));
		listaAssunti.setBorder(null);
		listaAssunti.setForeground(Color.white);
		listaAssunti.setSelectionBackground(new Color(40,40,80));
		listaAssunti.setSelectionForeground(Color.white);
		listaAssunti.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		scrollPaneAssunti.setBackground(new Color(20, 20, 40));
		scrollPaneAssunti.setBorder(null);
		scrollPaneAssunti.setOpaque(true);
		scrollPaneAssunti.getHorizontalScrollBar().setOpaque(true);
		scrollPaneAssunti.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneAssunti.getHorizontalScrollBar().setBorder(null);
		scrollPaneAssunti.getVerticalScrollBar().setOpaque(true);
		scrollPaneAssunti.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneAssunti.getVerticalScrollBar().setBorder(null);
		
		listaLicenziati.setBackground(new Color(20, 20, 40));
		listaLicenziati.setBorder(null);
		listaLicenziati.setSelectionBackground(new Color(40,40,80));
		listaLicenziati.setSelectionForeground(Color.white);
		listaLicenziati.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		listaLicenziati.setForeground(Color.white);
		
		scrollPaneLicenziati.setBackground(new Color(20, 20, 40));
		scrollPaneLicenziati.setBorder(null);
		scrollPaneLicenziati.setOpaque(true);
		scrollPaneLicenziati.getHorizontalScrollBar().setOpaque(true);
		scrollPaneLicenziati.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneLicenziati.getHorizontalScrollBar().setBorder(null);
		scrollPaneLicenziati.getVerticalScrollBar().setOpaque(true);
		scrollPaneLicenziati.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneLicenziati.getVerticalScrollBar().setBorder(null);

		
		setVisible(true);
		setResizable(false);

	}
	
	private class GestioneBottoniETesti implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneRiassumiCameriere)
			{
				theController.generaCalendarioAssunzioneCameriere(arrayListLicenziati.get(indiceListaLicenziati));
			}
			else if(e.getSource() == bottoneLicenziaCameriere)
			{
				theController.generaCalendarioLicenziamentoCameriere(arrayListAssunti.get(indiceListaAssunti));
			}
			else if (e.getSource() == bottoneAggiuntaCameriere)
			{
				theController.bottoneAggiungiCamerierePremuto(ristorante);
				ripresaInterfaccia();
			}
			else
			{
				theController.bottoneTornaIndietroGestioneCamerieriPremuto(ristorante);
			}
		}
	}
	private class GestioneListe implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			
			if(e.getSource() == listaLicenziati)
			{
				
				indiceListaLicenziati = listaLicenziati.getSelectedIndex();
				
				if (indiceListaLicenziati == -1) {
					
					bottoneRiassumiCameriere.setEnabled(false);
					
				}
				else {
				
					bottoneRiassumiCameriere.setEnabled(true);
					bottoneLicenziaCameriere.setEnabled(false);
					listaAssunti.clearSelection();
					
				}
			}
			else if (e.getSource() == listaAssunti)
			{
				indiceListaAssunti = listaAssunti.getSelectedIndex();
				
				if (indiceListaAssunti == -1) 
				{
					bottoneLicenziaCameriere.setEnabled(false);
				}
				else 
				{
					bottoneLicenziaCameriere.setEnabled(true);
					bottoneRiassumiCameriere.setEnabled(false);
					listaLicenziati.clearSelection();
						
				}
			}
		}	
	}
	
	
	public void ripresaInterfaccia() {
		arrayListAssunti = theController.estraiCamerieriInServizioC(ristorante);
		arrayListLicenziati = theController.estraiCamerieriLicenziatiC(ristorante);
		listaAssunti.removeListSelectionListener(handlerL);
		listaLicenziati.removeListSelectionListener(handlerL);
		modelloListaAssunti.removeAllElements();
		modelloListaAssunti.addAll(arrayListAssunti);
		modelloListaLicenziati.removeAllElements();
		modelloListaLicenziati.addAll(arrayListLicenziati);
		listaAssunti.addListSelectionListener(handlerL);
		listaLicenziati.addListSelectionListener(handlerL);
		bottoneLicenziaCameriere.setEnabled(false);
		bottoneRiassumiCameriere.setEnabled(false);
	}
}
