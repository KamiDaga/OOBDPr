
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.postgresql.jdbc.PgResultSet.CursorResultHandler;

import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class InterfacciaAggiuntaTavoli extends JFrame {

	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private ArrayList<JLabel> tavoliGrafici = new ArrayList<JLabel>();
	private boolean paintTable = false;
	private int posXInizialeT;
	private int posYInizialeT;
	private JLabel nuovoTavolo;
	private JLayeredPane riferimento;
	private boolean tavoloDisegnato = false;
	private int posXInizialeS;
	private int posYInizialeS;
	private int dimXF;
	private int dimYF;
	private JLabel nuovaEtichettaInBasso;
	private JLabel nuovaEtichettaInBassoADestra;
	private JLabel nuovaEtichettaADestra;
	private JButton bottoneOk;
	private JButton bottoneIndietro;
	private Controller theController;
	private Tavolo tavoloDaAggiungere = new Tavolo();
	private InterfacciaAggiuntaTavoli riferimentoF = this;
	private boolean inizializzazzioneTavolo = false;
	private JLabel iconaInformazioni;
	
	public InterfacciaAggiuntaTavoli(Tavolo tavoloNuovo,Sala salaCorrente, ArrayList<Tavolo> tavoliGiaPresenti, Controller controller) 
	{
		super("Aggiunta tavolo alla sala "+ salaCorrente.getNome());
		getContentPane().setBackground(new Color(20, 20, 40));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 557);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40)); 
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		this.theController = controller;
		tavoloDaAggiungere.setCapacita(tavoloNuovo.getCapacita());
		tavoloDaAggiungere.setNumero(tavoloNuovo.getNumero());
		tavoloDaAggiungere.setSala_App(salaCorrente);
		
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 694, 407);
		
		areaDiDisegno = new JLayeredPane();
		riferimento = areaDiDisegno;
		areaDiDisegno.setBounds(10, 11, 694, 407);
		getContentPane().add(areaDiDisegno);
		areaDiDisegno.setLayout(null);
		
		bottoneOk = new JButton("Ok");
		bottoneOk.setBounds(580, 429, 89, 35);
		bottoneOk.setBorder(null);
		bottoneOk.setBackground(new Color(0, 255, 127));

		getContentPane().add(bottoneOk);
		bottoneOk.setOpaque(true);
		bottoneOk.setBorder(null);
		bottoneOk.setEnabled(false);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 441, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));

		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setOpaque(true);
		bottoneIndietro.setBorder(null);
		
		bottoneOk.addActionListener(new GestoreBottoni());
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		background = new JLabel();
		background.setBounds(0, 0, 694, 407);
		background.setBackground(Color.white);
		background.setOpaque(true);
		
		GestioneDisegnoSecondoClick handlerDD = new GestioneDisegnoSecondoClick();
		InizializzazioneDisegno handlerD = new InizializzazioneDisegno();
		
		background.addMouseMotionListener(handlerDD);
		background.addMouseListener(handlerD);
		
		for (Tavolo tavolo : tavoliGiaPresenti) 
		{
			JLabel tavoloGraficoCorrente = new JLabel(String.format("%d",tavolo.getNumero()),SwingConstants.CENTER);
			tavoloGraficoCorrente.setBackground(new Color(129,116,37));
			tavoloGraficoCorrente.setOpaque(true);
			tavoloGraficoCorrente.setBounds(tavolo.getPosX(), tavolo.getPosY(), tavolo.getDimX(), tavolo.getDimY());
			tavoloGraficoCorrente.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			areaDiDisegno.add(tavoloGraficoCorrente,0,1);
			tavoliGrafici.add(tavoloGraficoCorrente);
		}
		
		areaDiDisegno.add(panel, 0, 1);
		areaDiDisegno.add(background,0,-1);
		
		iconaInformazioni = new JLabel();
		iconaInformazioni.setIcon(UIManager.getIcon("OptionPane.informationIcon"));	
		iconaInformazioni.setBounds(637, 473, 32, 43);
		iconaInformazioni.setToolTipText("<html>Cliccare una volta per scegliere l'origine del tavolo. <br>Cliccare una seconda volta per scegliere il termine del tavolo.<br>Una volta creato il tavolo puo' essere spostato e ridimensionato.</html>");
		getContentPane().add(iconaInformazioni);
		
		setResizable(false);
		setVisible(true);
		
	}
	
	private boolean presentiSovrapposizioni (ArrayList<JLabel> listaPannelliTavoli) 
	{
		
		boolean sovrapposizione = false;
		
		for (int i = 0; i<listaPannelliTavoli.size(); i++) {
			
			Rectangle rettangoloCorrente = new Rectangle(listaPannelliTavoli.get(i).getBounds());
			
			for (int j = 0; j<listaPannelliTavoli.size(); j++) {
				
				if (i!=j) {
					
					Rectangle rettangoloConfronto = new Rectangle(listaPannelliTavoli.get(j).getBounds());
					
					sovrapposizione = rettangoloCorrente.intersects(rettangoloConfronto) ||
							rettangoloCorrente.contains(rettangoloConfronto) ||
							rettangoloConfronto.contains(rettangoloCorrente) || sovrapposizione ;
			
				}
			}
		}
		
		return sovrapposizione;
		
	}
	
	private boolean presentiTavoliFuoriFinestra (ArrayList<JLabel> listaPannelliTavoli, JLayeredPane finestra) 
	{
		
		boolean fuori = false;
		
		for (int i = 0; i<listaPannelliTavoli.size(); i++) {
			
			Rectangle rettangoloCorrente = new Rectangle(listaPannelliTavoli.get(i).getBounds());
				
			if (rettangoloCorrente.getMinX()<0 ||
					rettangoloCorrente.getMaxX()>finestra.getWidth() ||
					rettangoloCorrente.getMinY()<0 ||
					rettangoloCorrente.getMaxY()>finestra.getHeight()) {
				fuori = true;
			}
			
		}
		
		return fuori;
	}
	
	private class GestoreBottoni implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource()==bottoneOk)
			{
				
				tavoliGrafici.add(nuovoTavolo);
				if(!presentiSovrapposizioni(tavoliGrafici) && !presentiTavoliFuoriFinestra(tavoliGrafici,areaDiDisegno))
				{
					tavoloDaAggiungere.setPosX(posXInizialeT);
					tavoloDaAggiungere.setPosY(posYInizialeT);
					tavoloDaAggiungere.setDimX(dimXF);
					tavoloDaAggiungere.setDimY(dimYF);
					theController.bottoneOkInterfacciaAggiuntaTavoliPremuto(tavoloDaAggiungere);
				}
				else
				{
					tavoliGrafici.remove(tavoliGrafici.size()-1);
					JOptionPane.showMessageDialog(null,"Il tavolo si trova su altri tavoli oppure si trova fuori dall'area di disegno. Si prega di riprovare.", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			else if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroInterfacciaAggiuntaTavoliPremuto(tavoloDaAggiungere.getSala_App());
			}
		}
		
	}
	
	private class pannelloTavoli extends JPanel
	{
		
		public void paintComponent (Graphics g)
		{
			g.drawLine(0,0,this.getBounds().width,0);
			g.drawLine(0,0,0,this.getBounds().height);
			g.drawLine(this.getBounds().width -1,0,this.getBounds().width -1,this.getBounds().height -1);
			g.drawLine(0,this.getBounds().height -1,this.getBounds().width -1,this.getBounds().height -1);
		}
	}
	
	private class InizializzazioneDisegno implements MouseListener
	{
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getSource() == background)
			{
				if(paintTable == false && tavoloDisegnato == false) 
					{
						paintTable = true;
						posXInizialeT = e.getX();
						posYInizialeT = e.getY();
						nuovoTavolo = new JLabel(String.format("%d",tavoloDaAggiungere.getNumero()), SwingConstants.CENTER);
						nuovoTavolo.setBounds(posXInizialeT,posYInizialeT,0,0);
						nuovoTavolo.setOpaque(true);
						nuovoTavolo.setBackground(new Color(129,116,37));
						nuovoTavolo.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovoTavolo.addMouseListener(new InizializzazioneDisegno());
						nuovoTavolo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
						nuovaEtichettaADestra = new JLabel();
						nuovaEtichettaInBasso = new JLabel();
						nuovaEtichettaInBassoADestra = new JLabel();
						nuovaEtichettaInBassoADestra.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovaEtichettaInBassoADestra.addMouseListener(new InizializzazioneDisegno());
						nuovaEtichettaADestra.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						nuovaEtichettaInBasso.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						nuovaEtichettaInBassoADestra.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						nuovaEtichettaADestra.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovaEtichettaADestra.addMouseListener(new InizializzazioneDisegno());
						nuovaEtichettaInBasso.addMouseMotionListener(new GestioneDisegnoSecondoClick());
						nuovaEtichettaInBasso.addMouseListener(new InizializzazioneDisegno());
						nuovaEtichettaADestra.setBackground(new Color(192,192,192));
						nuovaEtichettaInBasso.setBackground(new Color(192,192,192));
						nuovaEtichettaInBassoADestra.setBackground(new Color(192,192,192));
						nuovaEtichettaInBassoADestra.setOpaque(true);
						nuovaEtichettaADestra.setOpaque(true);
						nuovaEtichettaInBasso.setOpaque(true);
						riferimento.add(nuovoTavolo,0,1);
						riferimento.add(nuovaEtichettaInBassoADestra,0,0);
						riferimento.add(nuovaEtichettaADestra,0,0);
						riferimento.add(nuovaEtichettaInBasso,0,0);
					}
				else if (paintTable)
					{
					 	if(inizializzazzioneTavolo)
					 	{
							nuovaEtichettaADestra.setBounds(posXInizialeT+dimXF -3, posYInizialeT+dimYF/2 -3,6,6);
							nuovaEtichettaInBasso.setBounds(posXInizialeT+dimXF/2 -3,posYInizialeT+dimYF -3,6,6);
							nuovaEtichettaInBassoADestra.setBounds(posXInizialeT+dimXF -3, posYInizialeT + dimYF -3,6,6);
							bottoneOk.setEnabled(true);
					 	}
					 	else
					 	{
					 		dimXF = 12;
					 		dimYF = 12;
					 		nuovoTavolo.setSize(new Dimension(dimXF,dimYF));
					 		nuovaEtichettaADestra.setBounds(posXInizialeT+dimXF -3, posYInizialeT+dimYF/2 -3,6,6);
							nuovaEtichettaInBasso.setBounds(posXInizialeT+dimXF/2 -3,posYInizialeT+dimYF -3,6,6);
							nuovaEtichettaInBassoADestra.setBounds(posXInizialeT+dimXF -3, posYInizialeT + dimYF -3,6,6);
					 	}
					 	paintTable = false;
						tavoloDisegnato = true;
					 }
			}	
	
		}


		public void mousePressed(MouseEvent e) 
		{
			if(tavoloDisegnato && e.getSource() == nuovoTavolo )
			{
				posXInizialeS = e.getX();
				posYInizialeS = e.getY();
			}
			else if(e.getSource() == nuovaEtichettaADestra)
			{
				posXInizialeS = e.getX();
			}
			else if(e.getSource()== nuovaEtichettaInBasso)
			{
				posYInizialeS = e.getY();
			}
			else if(e.getSource()== nuovaEtichettaInBassoADestra)
			{
				posXInizialeS=e.getX();
				posYInizialeS=e.getY();
			}

		}


		public void mouseReleased(MouseEvent e) 
		{
			
		}


		public void mouseEntered(MouseEvent e) 
		{
		}


		public void mouseExited(MouseEvent e) 
		{
			
			
		}
		
		
	}
	
	private class GestioneDisegnoSecondoClick implements MouseMotionListener
	{

		public void mouseDragged(MouseEvent e) 
		{
			if(e.getSource()==nuovoTavolo)
			{
				nuovoTavolo.setLocation(posXInizialeT =  posXInizialeT+(e.getX()-posXInizialeS),posYInizialeT =  posYInizialeT +(e.getY() - posYInizialeS));		
				nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
				nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
				nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
				
			}
			else if(e.getSource() == nuovaEtichettaADestra)
			{
				if(dimXF + (e.getX()-posXInizialeS) >= 12 && dimYF >= 12)
				{	
						nuovoTavolo.setSize(new Dimension(dimXF= dimXF + (e.getX()-posXInizialeS),dimYF));
						nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
						nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
						nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
				}
			}
			else if (e.getSource() == nuovaEtichettaInBasso)
			{	
				if(dimXF >= 12 && dimYF + (e.getY()-posYInizialeS) >= 12)
					{
						nuovoTavolo.setSize(new Dimension(dimXF,dimYF = dimYF + (e.getY()-posYInizialeS)));
						nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
						nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
						nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
					}
			}
			else if(e.getSource()==nuovaEtichettaInBassoADestra)
			{
				if(dimXF + (e.getX()-posXInizialeS)>= 12 && dimYF + (e.getY()-posYInizialeS)  >= 12)
					{
						nuovoTavolo.setSize(new Dimension(dimXF= dimXF + (e.getX()-posXInizialeS),dimYF = dimYF + (e.getY()-posYInizialeS)));
						nuovaEtichettaADestra.setLocation(posXInizialeT+dimXF-3,posYInizialeT+dimYF/2 -3);
						nuovaEtichettaInBassoADestra.setLocation(posXInizialeT+dimXF -3, posYInizialeT+dimYF -3);
						nuovaEtichettaInBasso.setLocation(posXInizialeT+dimXF/2 -3, posYInizialeT+dimYF -3);
					}
			}
		}

		public void mouseMoved(MouseEvent e) 
		{
			
			if(paintTable && (e.getSource()==background || e.getSource()==nuovoTavolo))
			{
				nuovoTavolo.setBounds(posXInizialeT,posYInizialeT,dimXF = e.getX()-posXInizialeT,dimYF = e.getY()-posYInizialeT);
				if(dimXF>= 12 && dimYF >= 12)
					inizializzazzioneTavolo = true;
				else 
					inizializzazzioneTavolo = false;
			}
			
			
		}
		
	}
}
