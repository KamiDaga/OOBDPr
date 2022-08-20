import java.awt.BorderLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;

public class InterfacciaSelezioneCamerieri extends JFrame 
{
	private DefaultListModel<Cameriere> modelloListaCameriere = new DefaultListModel<Cameriere>();
	private ArrayList<Cameriere> arrayCameriere;
	private JList<Cameriere> listaCamerieri = new JList<Cameriere>(modelloListaCameriere);
	private DefaultListModel<Cameriere> modelloListaCamerieriSelezionati = new DefaultListModel<Cameriere>();
	private ArrayList<Cameriere> arrayCameriereSelezionati = new ArrayList<Cameriere>();
	private JList<Cameriere> listaCamerieriSelezionati = new JList<Cameriere>(modelloListaCamerieriSelezionati);
	private Controller theController;
	private JButton bottoneIndietro;
	private JButton bottoneConfermaSelezione;
	private String data;
	private ArrayList<Tavolo> tavoli;
	private JLabel etichettaCamerieri;
	private int tavoloScelto;
	private int indiceDiSelezioneCamerieri;
	private int indiceDiSelezioneSelezionati;
	private JButton bottoneAggiungiAiSelezionati;
	private JButton bottoneRimuoviDaiSelezionati;
	private boolean diVisualizzazione = false;
	private JScrollPane scrollPaneCamerieri;
	private JScrollPane scrollPaneCamerieriSelezionati;
	
	public InterfacciaSelezioneCamerieri(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String data) 
	{
		super("Selezione camerieri disponibili in data "+ data);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 250);
		getContentPane().setLayout(null);
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		this.data = data;
		this.tavoli = tavoli;
		this.theController = controller;
		this.tavoloScelto = tavoloScelto;
		scrollPaneCamerieri = new JScrollPane();
		scrollPaneCamerieri.setBounds(10, 41, 170, 119);
		getContentPane().add(scrollPaneCamerieri);
		scrollPaneCamerieri.setViewportView(listaCamerieri);
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		bottoneConfermaSelezione = new JButton("Conferma");
		bottoneConfermaSelezione.setBounds(274, 178, 101, 23);
		getContentPane().add(bottoneConfermaSelezione);
		bottoneConfermaSelezione.addActionListener(new GestoreBottoni());
		bottoneConfermaSelezione.setBackground(new Color(0, 255, 127));
		bottoneConfermaSelezione.setBorder(null);
		bottoneConfermaSelezione.setOpaque(true);
	
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 178, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		
		bottoneIndietro.addActionListener(new GestoreBottoni());
		etichettaCamerieri = new JLabel("Camerieri disponibili");
		etichettaCamerieri.setBounds(10, 16, 153, 14);
		etichettaCamerieri.setForeground(Color.white);
		getContentPane().add(etichettaCamerieri);
		
		scrollPaneCamerieriSelezionati = new JScrollPane();
		scrollPaneCamerieriSelezionati.setBounds(239, 41, 170, 119);
		getContentPane().add(scrollPaneCamerieriSelezionati);
		listaCamerieriSelezionati.setBackground(Color.WHITE);
		
		scrollPaneCamerieriSelezionati.setViewportView(listaCamerieriSelezionati);
		
		bottoneAggiungiAiSelezionati = new JButton(">");
		bottoneAggiungiAiSelezionati.setBounds(187, 73, 45, 23);
		getContentPane().add(bottoneAggiungiAiSelezionati);
		bottoneAggiungiAiSelezionati.setBackground(new Color(0, 255, 127));
		bottoneAggiungiAiSelezionati.setBorder(null);
		bottoneAggiungiAiSelezionati.setOpaque(true);
		
		bottoneRimuoviDaiSelezionati = new JButton("<");
		bottoneRimuoviDaiSelezionati.setBounds(187, 115, 45, 23);
		getContentPane().add(bottoneRimuoviDaiSelezionati);
		bottoneRimuoviDaiSelezionati.setBackground(new Color(0, 255, 127));
		bottoneRimuoviDaiSelezionati.setBorder(null);
		bottoneRimuoviDaiSelezionati.setOpaque(true);
		
		bottoneAggiungiAiSelezionati.addActionListener(new GestoreBottoni());
		bottoneRimuoviDaiSelezionati.addActionListener(new GestoreBottoni());
		
		listaCamerieri.addListSelectionListener(new GestoreLista());
		listaCamerieriSelezionati.addListSelectionListener(new GestoreLista());
		
		bottoneConfermaSelezione.setEnabled(false);
		bottoneAggiungiAiSelezionati.setEnabled(false);
		bottoneRimuoviDaiSelezionati.setEnabled(false);
		
		arrayCameriere = theController.estraiCamerieriAssegnabili(data,tavoli.get(tavoloScelto).getSala_App().getRistoranteDiAppartenenza());
		modelloListaCameriere.addAll(arrayCameriere);
		
		listaCamerieriSelezionati.setBackground(new Color(20, 20, 40));
		listaCamerieriSelezionati.setBorder(null);
		listaCamerieriSelezionati.setForeground(Color.white);
		listaCamerieriSelezionati.setSelectionBackground(new Color(40,40,80));
		listaCamerieriSelezionati.setSelectionForeground(Color.white);
		listaCamerieriSelezionati.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		scrollPaneCamerieriSelezionati.setBackground(new Color(20, 20, 40));
		scrollPaneCamerieriSelezionati.setBorder(null);
		scrollPaneCamerieriSelezionati.setOpaque(true);
		scrollPaneCamerieriSelezionati.getHorizontalScrollBar().setOpaque(true);
		scrollPaneCamerieriSelezionati.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieriSelezionati.getHorizontalScrollBar().setBorder(null);
		scrollPaneCamerieriSelezionati.getVerticalScrollBar().setOpaque(true);
		scrollPaneCamerieriSelezionati.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieriSelezionati.getVerticalScrollBar().setBorder(null);
		
		listaCamerieri.setBackground(new Color(20, 20, 40));
		listaCamerieri.setBorder(null);
		listaCamerieri.setSelectionBackground(new Color(40,40,80));
		listaCamerieri.setSelectionForeground(Color.white);
		listaCamerieri.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		listaCamerieri.setForeground(Color.white);
		
		scrollPaneCamerieri.setBackground(new Color(20, 20, 40));
		scrollPaneCamerieri.setBorder(null);
		scrollPaneCamerieri.setOpaque(true);
		scrollPaneCamerieri.getHorizontalScrollBar().setOpaque(true);
		scrollPaneCamerieri.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieri.getHorizontalScrollBar().setBorder(null);
		scrollPaneCamerieri.getVerticalScrollBar().setOpaque(true);
		scrollPaneCamerieri.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieri.getVerticalScrollBar().setBorder(null);
		
		setVisible(true);
		setResizable(false);
	}
	
	public InterfacciaSelezioneCamerieri(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String data, ArrayList<Integer> camerieriGiaPresenti) 
	{
		super("Selezione camerieri disponibili in data "+ data);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 250);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		this.data = data;
		this.tavoli = tavoli;
		this.theController = controller;
		this.tavoloScelto = tavoloScelto;
		this.diVisualizzazione = true;
		scrollPaneCamerieri = new JScrollPane();
		scrollPaneCamerieri.setBounds(10, 41, 170, 119);
		getContentPane().add(scrollPaneCamerieri);
		scrollPaneCamerieri.setViewportView(listaCamerieri);
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		bottoneConfermaSelezione = new JButton("Conferma");
		bottoneConfermaSelezione.setBounds(274, 178, 101, 23);
		getContentPane().add(bottoneConfermaSelezione);
		bottoneConfermaSelezione.addActionListener(new GestoreBottoni());
		bottoneConfermaSelezione.setBackground(new Color(0, 255, 127));
		bottoneConfermaSelezione.setBorder(null);
		bottoneConfermaSelezione.setOpaque(true);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 178, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		
		bottoneIndietro.addActionListener(new GestoreBottoni());
		etichettaCamerieri = new JLabel("Camerieri disponibili");
		etichettaCamerieri.setBounds(10, 16, 153, 14);
		etichettaCamerieri.setForeground(Color.white);
		getContentPane().add(etichettaCamerieri);
		
		scrollPaneCamerieriSelezionati = new JScrollPane();
		scrollPaneCamerieriSelezionati.setBounds(239, 41, 170, 119);
		getContentPane().add(scrollPaneCamerieriSelezionati);
		
		scrollPaneCamerieriSelezionati.setViewportView(listaCamerieriSelezionati);
		
		bottoneAggiungiAiSelezionati = new JButton(">");
		bottoneAggiungiAiSelezionati.setBounds(187, 73, 45, 23);
		getContentPane().add(bottoneAggiungiAiSelezionati);
		bottoneAggiungiAiSelezionati.setBackground(new Color(0, 255, 127));
		bottoneAggiungiAiSelezionati.setBorder(null);
		bottoneAggiungiAiSelezionati.setOpaque(true);
		
		bottoneRimuoviDaiSelezionati = new JButton("<");
		bottoneRimuoviDaiSelezionati.setBounds(187, 115, 45, 23);
		getContentPane().add(bottoneRimuoviDaiSelezionati);
		bottoneRimuoviDaiSelezionati.setBackground(new Color(0, 255, 127));
		bottoneRimuoviDaiSelezionati.setBorder(null);
		bottoneRimuoviDaiSelezionati.setOpaque(true);
		
		bottoneAggiungiAiSelezionati.addActionListener(new GestoreBottoni());
		bottoneRimuoviDaiSelezionati.addActionListener(new GestoreBottoni());
		
		listaCamerieri.addListSelectionListener(new GestoreLista());
		listaCamerieriSelezionati.addListSelectionListener(new GestoreLista());
		
		bottoneConfermaSelezione.setEnabled(false);
		bottoneAggiungiAiSelezionati.setEnabled(false);
		bottoneRimuoviDaiSelezionati.setEnabled(false);
		
		listaCamerieriSelezionati.setBackground(new Color(20, 20, 40));
		listaCamerieriSelezionati.setBorder(null);
		listaCamerieriSelezionati.setForeground(Color.white);
		listaCamerieriSelezionati.setSelectionBackground(new Color(40,40,80));
		listaCamerieriSelezionati.setSelectionForeground(Color.white);
		listaCamerieriSelezionati.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		scrollPaneCamerieriSelezionati.setBackground(new Color(20, 20, 40));
		scrollPaneCamerieriSelezionati.setBorder(null);
		scrollPaneCamerieriSelezionati.setOpaque(true);
		scrollPaneCamerieriSelezionati.getHorizontalScrollBar().setOpaque(true);
		scrollPaneCamerieriSelezionati.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieriSelezionati.getHorizontalScrollBar().setBorder(null);
		scrollPaneCamerieriSelezionati.getVerticalScrollBar().setOpaque(true);
		scrollPaneCamerieriSelezionati.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieriSelezionati.getVerticalScrollBar().setBorder(null);
		
		listaCamerieri.setBackground(new Color(20, 20, 40));
		listaCamerieri.setBorder(null);
		listaCamerieri.setSelectionBackground(new Color(40,40,80));
		listaCamerieri.setSelectionForeground(Color.white);
		listaCamerieri.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		listaCamerieri.setForeground(Color.white);
		
		scrollPaneCamerieri.setBackground(new Color(20, 20, 40));
		scrollPaneCamerieri.setBorder(null);
		scrollPaneCamerieri.setOpaque(true);
		scrollPaneCamerieri.getHorizontalScrollBar().setOpaque(true);
		scrollPaneCamerieri.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieri.getHorizontalScrollBar().setBorder(null);
		scrollPaneCamerieri.getVerticalScrollBar().setOpaque(true);
		scrollPaneCamerieri.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneCamerieri.getVerticalScrollBar().setBorder(null);
	
	
		arrayCameriere = theController.estraiCamerieriAssegnabili(data,tavoli.get(tavoloScelto).getSala_App().getRistoranteDiAppartenenza());
		
		if (diVisualizzazione)
		{
			int j = 0;
			
			do {
				
				if(camerieriGiaPresenti.contains(arrayCameriere.get(j).getId_Cameriere()))
				{
					arrayCameriere.remove(j);
				}
				else 
					j++;
				
			} while (j<arrayCameriere.size());
		}
				
		modelloListaCameriere.addAll(arrayCameriere);
		
		setVisible(true);
		setResizable(false);
	}
	
	private class GestoreLista implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			indiceDiSelezioneCamerieri = listaCamerieri.getSelectedIndex();
			indiceDiSelezioneSelezionati = listaCamerieriSelezionati.getSelectedIndex();
			
			if(indiceDiSelezioneCamerieri != -1) bottoneAggiungiAiSelezionati.setEnabled(true);
			else bottoneAggiungiAiSelezionati.setEnabled(false);	
			
			if(indiceDiSelezioneSelezionati != -1) bottoneRimuoviDaiSelezionati.setEnabled(true);
			else bottoneRimuoviDaiSelezionati.setEnabled(false);
		}
		
	}
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneIndietro)
			{
				if (diVisualizzazione)
					theController.bottoneIndietroSelezioneCameriereDiVisualizzazione(tavoli, tavoloScelto, data);
				else
					theController.bottoneIndietroSelezioneCamerieriPremuto(tavoli,data);
			}	
			else if(e.getSource() == bottoneConfermaSelezione)
			{
				if(diVisualizzazione) 
					theController.bottoneConfermaSelezioneCameriereDiVisualizzazione(arrayCameriereSelezionati, tavoli, tavoloScelto, data);
				else 
					theController.bottoneConfermaSelezioneCamerieriPremuto(arrayCameriereSelezionati, data, tavoli, tavoloScelto);
			}
			else if(e.getSource() == bottoneAggiungiAiSelezionati)
			{
				arrayCameriereSelezionati.add(arrayCameriere.get(indiceDiSelezioneCamerieri));
				modelloListaCamerieriSelezionati.removeAllElements();
				modelloListaCamerieriSelezionati.addAll(arrayCameriereSelezionati);
				arrayCameriere.remove(indiceDiSelezioneCamerieri);
				modelloListaCameriere.removeAllElements();
				modelloListaCameriere.addAll(arrayCameriere);
				bottoneAggiungiAiSelezionati.setEnabled(false);
				bottoneRimuoviDaiSelezionati.setEnabled(false);
				bottoneConfermaSelezione.setEnabled(true);
			}
			else if(e.getSource() == bottoneRimuoviDaiSelezionati)
			{
				arrayCameriere.add(arrayCameriereSelezionati.get(indiceDiSelezioneSelezionati));
				modelloListaCameriere.removeAllElements();
				modelloListaCameriere.addAll(arrayCameriere);
				arrayCameriereSelezionati.remove(indiceDiSelezioneSelezionati);
				modelloListaCamerieriSelezionati.removeAllElements();
				modelloListaCamerieriSelezionati.addAll(arrayCameriereSelezionati);
				bottoneAggiungiAiSelezionati.setEnabled(false);
				bottoneRimuoviDaiSelezionati.setEnabled(false);
				if(arrayCameriereSelezionati.size()==0) bottoneConfermaSelezione.setEnabled(false);
			}
		}
	}

	public boolean isDiVisualizzazione() {
		return diVisualizzazione;
	}

	public void setDiVisualizzazione(boolean diVisualizzazione) {
		this.diVisualizzazione = diVisualizzazione;
	}
	
	
}
