import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InterfacciaVisualizzazioneOccupazione extends JFrame {

	private ArrayList<Tavolo> tavoli;
	private Controller theController;
	private DefaultListModel<Avventori> modelloListaAvventori = new DefaultListModel<Avventori>();
	private ArrayList<Avventori> arrayAvventori;
	private JList<Avventori> listaAvventori = new JList<Avventori>(modelloListaAvventori);
	private DefaultListModel<Cameriere> modelloListaCameriere = new DefaultListModel<Cameriere>();
	private ArrayList<Cameriere> arrayCameriere;
	private JList<Cameriere> listaCamerieri = new JList<Cameriere>(modelloListaCameriere);
	private JButton bottoneIndietro;
	private String dataScelta;
	private int tavoloScelto;
	private JButton bottoneRimuoviCameriere;
	private int indiceListaCamerieri = -1;
	private int indiceListaAvventori = -1;
	private int contaNTel = 0;
	private JButton bottoneRimuoviAvventore;
	private JButton bottoneAggiungiAvventore;
	private JButton bottoneAggiungiCameriere;
	private JScrollPane scrollPaneAvventori;
	private JScrollPane scrollPaneCamerieri;
	private JLabel indicazioneAvventori;
	private JLabel indicazioneCamerieri;
	
	public InterfacciaVisualizzazioneOccupazione(Controller controller, ArrayList<Tavolo> tavoli, int tavoloScelto, String dataScelta) 
	{
		super("Tavolata del tavolo "+ tavoli.get(tavoloScelto).getNumero()+" in data "+ dataScelta+".");
		setLayout(null);
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		getContentPane().setBackground(new Color(20,20,40));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 330);
		getContentPane().setLayout(null);
		this.theController=controller;
		this.dataScelta= dataScelta;
		this.tavoloScelto = tavoloScelto;
		this.tavoli = tavoli;
		scrollPaneAvventori = new JScrollPane();
		scrollPaneAvventori.setBounds(10, 45, 170, 100);
		getContentPane().add(scrollPaneAvventori);
		
		getContentPane().setBackground(new Color(20, 20, 40));

		scrollPaneAvventori.setViewportView(listaAvventori);
		
		scrollPaneCamerieri = new JScrollPane();
		scrollPaneCamerieri.setBounds(310, 45, 164, 100);
		getContentPane().add(scrollPaneCamerieri);
		
		scrollPaneCamerieri.setViewportView(listaCamerieri);
		
		indicazioneAvventori = new JLabel("Avventori");
		indicazioneAvventori.setForeground(new Color(255, 255, 255));
		indicazioneAvventori.setBounds(10, 20, 170, 14);
		getContentPane().add(indicazioneAvventori);
		
		indicazioneCamerieri = new JLabel("Camerieri che hanno servito");
		indicazioneCamerieri.setForeground(new Color(255, 255, 255));
		indicazioneCamerieri.setBounds(310, 20, 164, 14);
		getContentPane().add(indicazioneCamerieri);
		
		bottoneAggiungiCameriere = new JButton("Aggiungi cameriere");
		bottoneAggiungiCameriere.setBounds(310, 156, 164, 23);
		getContentPane().add(bottoneAggiungiCameriere);
		bottoneAggiungiCameriere.addActionListener(new GestioneBottoni());
		bottoneAggiungiCameriere.setBackground(new Color(0, 255, 127));
		bottoneAggiungiCameriere.setBorder(null);
		bottoneAggiungiCameriere.setOpaque(true);
		
		bottoneRimuoviCameriere = new JButton("Rimuovi cameriere");
		bottoneRimuoviCameriere.setBounds(310, 190, 164, 23);
		getContentPane().add(bottoneRimuoviCameriere);
		bottoneRimuoviCameriere.setEnabled(false);
		bottoneRimuoviCameriere.addActionListener(new GestioneBottoni());
		bottoneRimuoviCameriere.setBackground(new Color(0, 255, 127));
		bottoneRimuoviCameriere.setBorder(null);
		bottoneRimuoviCameriere.setOpaque(true);
		
		bottoneAggiungiAvventore = new JButton("Aggiungi avventore");
		bottoneAggiungiAvventore.setBounds(10, 156, 170, 23);
		getContentPane().add(bottoneAggiungiAvventore);
		bottoneAggiungiAvventore.addActionListener(new GestioneBottoni());
		bottoneAggiungiAvventore.setBackground(new Color(0, 255, 127));
		bottoneAggiungiAvventore.setBorder(null);
		bottoneAggiungiAvventore.setOpaque(true);
		
		bottoneRimuoviAvventore = new JButton("Rimuovi avventore");
		bottoneRimuoviAvventore.setBounds(10, 190, 170, 23);
		getContentPane().add(bottoneRimuoviAvventore);
		bottoneRimuoviAvventore.addActionListener(new GestioneBottoni());
		bottoneRimuoviAvventore.setEnabled(false);
		bottoneRimuoviAvventore.setBackground(new Color(0, 255, 127));
		bottoneRimuoviAvventore.setBorder(null);
		bottoneRimuoviAvventore.setOpaque(true);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 257, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.addActionListener(new GestioneBottoni());
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		
		arrayAvventori = theController.estrazioneAvventoriDelTavoloInData(tavoli.get(tavoloScelto), dataScelta);
		modelloListaAvventori.removeAllElements();
		modelloListaAvventori.addAll(arrayAvventori);
		
		arrayCameriere = theController.estrazioneCamerieriInServizioAlTavoloinData(tavoli.get(tavoloScelto).getId_Tavolo(), dataScelta);
		modelloListaCameriere.removeAllElements();
		modelloListaCameriere.addAll(arrayCameriere);
		
		for (Avventori avventoreCorrente : arrayAvventori)
		{
			if(Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", avventoreCorrente.getN_tel())) contaNTel++;

		}
		
		listaAvventori.addListSelectionListener(new GestioneListe());
		listaCamerieri.addListSelectionListener(new GestioneListe());
		
		if(tavoli.get(tavoloScelto).getCapacita() == arrayAvventori.size()) 
		{
		
			bottoneAggiungiAvventore.setEnabled(false);
			bottoneAggiungiAvventore.setToolTipText("Capacita' massima del tavolo raggiunta");
		}
		
		listaAvventori.setBackground(new Color(20, 20, 40));
		listaAvventori.setBorder(null);
		listaAvventori.setSelectionBackground(new Color(40,40,80));
		listaAvventori.setSelectionForeground(Color.white);
		listaAvventori.setForeground(Color.white);
		listaAvventori.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		scrollPaneAvventori.setBackground(new Color(20, 20, 40));
		scrollPaneAvventori.setBorder(null);
		scrollPaneAvventori.setOpaque(true);
		scrollPaneAvventori.getHorizontalScrollBar().setOpaque(true);
		scrollPaneAvventori.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneAvventori.getHorizontalScrollBar().setBorder(null);
		scrollPaneAvventori.getVerticalScrollBar().setOpaque(true);
		scrollPaneAvventori.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scrollPaneAvventori.getVerticalScrollBar().setBorder(null);
		
		listaCamerieri.setBackground(new Color(20, 20, 40));
		listaCamerieri.setBorder(null);
		listaCamerieri.setSelectionBackground(new Color(40,40,80));
		listaCamerieri.setSelectionForeground(Color.white);
		listaCamerieri.setForeground(Color.white);
		listaCamerieri.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
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
	
	private class GestioneBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e )
		{
			if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroVisualizzazioneOccupazione(tavoli, dataScelta);
			}
			else if(e.getSource() == bottoneRimuoviCameriere)
			{
				theController.bottoneRimuoviCameriereVisualizzazioneOccupazione(dataScelta, tavoli.get(tavoloScelto).getId_Tavolo(), arrayCameriere.get(indiceListaCamerieri));
				arrayCameriere.remove(indiceListaCamerieri);
				modelloListaCameriere.removeAllElements();
				modelloListaCameriere.addAll(arrayCameriere);
			
			}
			else if(e.getSource() == bottoneRimuoviAvventore)
			{
				theController.bottoneRimuoviAvventoreVisualizzazioneOccupazione(dataScelta,tavoli.get(tavoloScelto).getId_Tavolo(), arrayAvventori.get(indiceListaAvventori));
				if(!arrayAvventori.get(indiceListaAvventori).getN_tel().isBlank()) contaNTel--;
				arrayAvventori.remove(indiceListaAvventori);
				modelloListaAvventori.removeAllElements();
				modelloListaAvventori.addAll(arrayAvventori);
				
			}
			else if(e.getSource() == bottoneAggiungiAvventore)
			{
				ArrayList<String> avventoriAlTavolo = new ArrayList<String>();
				for (Avventori avventoreCorrente : arrayAvventori) avventoriAlTavolo.add(avventoreCorrente.getN_CID());
				theController.bottoneAggiungiAvventoreVisualizzazioneOccupazione(dataScelta, tavoli, tavoloScelto,avventoriAlTavolo);
			}
			else if(e.getSource() == bottoneAggiungiCameriere)
			{
				ArrayList<Integer> idCamerieriPresenti = new ArrayList<Integer>();
				
				for (Cameriere cameriereCorrente : arrayCameriere) idCamerieriPresenti.add(cameriereCorrente.getId_Cameriere());

				theController.bottoneAggiungiCameriereInterfacciaVisualizzazioneOccupazione(tavoli, tavoloScelto, dataScelta,idCamerieriPresenti);
			}
		}
	}
	
	private class GestioneListe implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if(e.getSource() == listaCamerieri)
			{
				if(listaCamerieri.getSelectedIndex() == -1)
				{
					bottoneRimuoviCameriere.setEnabled(false);
					indiceListaCamerieri = -1;
				}
				else
				{
					bottoneRimuoviCameriere.setEnabled(true);
					indiceListaCamerieri = listaCamerieri.getSelectedIndex();
					listaAvventori.clearSelection();
					indiceListaAvventori = -1;
					bottoneRimuoviAvventore.setEnabled(false);
				}
				
			}
			else if (e.getSource() == listaAvventori)
			{
				if(listaAvventori.getSelectedIndex() == -1)
				{
					bottoneRimuoviAvventore.setEnabled(false);
					indiceListaAvventori = -1;
				}
				else
				{
					listaCamerieri.clearSelection();;
					bottoneRimuoviAvventore.setEnabled(true);
					bottoneRimuoviAvventore.setToolTipText("");
					indiceListaAvventori = listaAvventori.getSelectedIndex();
					indiceListaCamerieri = -1;
					bottoneRimuoviCameriere.setEnabled(false);
				}
			}
			if(arrayCameriere.size() == 1) bottoneRimuoviCameriere.setEnabled(false);
			if(arrayAvventori.size() == 1) bottoneRimuoviAvventore.setEnabled(false);
			if(!arrayAvventori.get(indiceListaAvventori!=-1 ? indiceListaAvventori : 0).getN_tel().isBlank() && contaNTel == 1) 
				{
					bottoneRimuoviAvventore.setEnabled(false);
					bottoneRimuoviAvventore.setToolTipText("Non si puo' rimuovere un avventore con un numero di telefono!");
				}
			if(tavoli.get(tavoloScelto).getCapacita() == arrayAvventori.size()) 
				{
				
					bottoneAggiungiAvventore.setEnabled(false);
					bottoneAggiungiAvventore.setToolTipText("Capacita' massima del tavolo raggiunta");
				}
			else
				{
					bottoneAggiungiAvventore.setEnabled(true);
					bottoneAggiungiAvventore.setToolTipText("");
				}
		}
	}
	
}
