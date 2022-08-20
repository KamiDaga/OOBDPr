  import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class InterfacciaModificaLayout extends JFrame {

	private Controller theController;
	private Sala sala;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> tavoliGrafici = new ArrayList<JLabel>();
	private JButton bottoneConferma;
	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private JLabel tavoloSelezionato;
	private JLabel tavoloTrascinamento;
	private Point puntoDiPartenza;
	private Rectangle rettangoloDiPartenza;
	private boolean ridimensionamentoVerticale = false;
	private boolean ridimensionamentoOrizzontale = false;
	private boolean ridimensionamentoTotale = false;
	private boolean spostamento = false;
	private ArrayList<EstensoreEst> listaEtichetteEst = new ArrayList<EstensoreEst>();
	private ArrayList<EstensoreSud> listaEtichetteSud = new ArrayList<EstensoreSud>();
	private ArrayList<EstensoreSudEst> listaEtichetteSudEst = new ArrayList<EstensoreSudEst>();
	private JButton bottoneIndietro;

	public InterfacciaModificaLayout(Controller c, Sala salaCorrente) {
		super("Modifica layout di "+ salaCorrente.getNome());
		getContentPane().setLayout(null);

		getContentPane().setBackground(new Color(20,20,40));
		
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		theController = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 501);
		this.sala = salaCorrente;
		getContentPane().setLayout(null);
		bottoneConferma = new JButton("Conferma modifiche");
		bottoneConferma.setBackground(new Color(0, 255, 127));
		bottoneConferma.setBorder(null);
		bottoneConferma.setOpaque(true);
		bottoneConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (presentiSovrapposizioni(tavoliGrafici))
					
					JOptionPane.showMessageDialog(null, "Ci sono dei tavoli sovrapposti!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				
				if (presentiTavoliTroppoPiccoli(tavoliGrafici))
					JOptionPane.showMessageDialog(null, "Ci sono dei tavoli troppo piccoli!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				
				if (presentiTavoliFuoriFinestra(tavoliGrafici, areaDiDisegno))
					JOptionPane.showMessageDialog(null, "Ci sono dei tavoli che fuoriescono dalla finestra!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				
				if (!presentiTavoliFuoriFinestra(tavoliGrafici, areaDiDisegno) &&
						!presentiTavoliTroppoPiccoli(tavoliGrafici) &&
						!presentiSovrapposizioni(tavoliGrafici)) {
					
					for (JLabel numero : tavoliGrafici) {
						for (Tavolo tavolo : tavoli) {
							if (tavolo.getNumero() == (Integer.parseInt(numero.getText()))) {
								tavolo.setPosX(numero.getX());
								tavolo.setPosY(numero.getY());
								tavolo.setDimX(numero.getWidth());
								tavolo.setDimY(numero.getHeight());
							}
						}
						
					}
					theController.confermaModificheLayoutPremuto(tavoli, sala);
				}
			}
		});
		
		bottoneConferma.setBounds(468, 429, 236, 23);
		getContentPane().add(bottoneConferma);
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 694, 407);
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 694, 407);
		getContentPane().add(areaDiDisegno);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.modificaLayoutIndietroPremuto(sala);
			}
		});
		bottoneIndietro.setBounds(10, 429, 89, 23);
		getContentPane().add(bottoneIndietro);
		areaDiDisegno.add(panel, 0);
		background = new JLabel();
		background.setBounds(0, 0, 694, 407);
		background.setBackground(Color.white);
		background.setOpaque(true);
		areaDiDisegno.add(background, -30000);
		gestoreIcone handler = new gestoreIcone();
		
		tavoli = theController.estrazioneTavoliSala(salaCorrente);
		for (Tavolo tavolo: tavoli)
		{
			JLabel tavoloCorrente = new JLabel(String.format("%d",tavolo.getNumero()));
			tavoloCorrente.setBackground(new Color(129,116,37));
			tavoloCorrente.setOpaque(true);
			tavoloCorrente.setBounds(tavolo.getPosX(), tavolo.getPosY(), tavolo.getDimX(), tavolo.getDimY());
			tavoloCorrente.addMouseListener(handler);
			tavoloCorrente.addMouseMotionListener(handler);
			tavoloCorrente.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			tavoloCorrente.setHorizontalAlignment(SwingConstants.CENTER);
			
			EstensoreEst etichettaDestra = new EstensoreEst(tavoloCorrente);
			etichettaDestra.setBounds(tavolo.getPosX()+tavolo.getDimX()-3, (tavolo.getPosY()+tavolo.getDimY()/2) -3, 6, 6);
			etichettaDestra.setOpaque(true);
			etichettaDestra.setBackground(Color.WHITE);
			etichettaDestra.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			EstensoreSud etichettaSotto = new EstensoreSud(tavoloCorrente);
			etichettaSotto.setBounds((tavolo.getPosX()+tavolo.getDimX()/2)-3, tavolo.getPosY()+tavolo.getDimY() -3, 6, 6);
			etichettaSotto.setOpaque(true);
			etichettaSotto.setBackground(Color.WHITE);
			etichettaSotto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			EstensoreSudEst etichettaAngolo = new EstensoreSudEst(tavoloCorrente);
			etichettaAngolo.setBounds(tavolo.getPosX()+tavolo.getDimX()-3, tavolo.getPosY()+tavolo.getDimY() -3, 6, 6);
			etichettaAngolo.setOpaque(true);
			etichettaAngolo.setBackground(Color.WHITE);
			etichettaAngolo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			areaDiDisegno.add(tavoloCorrente,0,1);
			areaDiDisegno.add(etichettaDestra,0,0);
			areaDiDisegno.add(etichettaSotto,0,0);
			areaDiDisegno.add(etichettaAngolo,0,0);
			
			listaEtichetteEst.add(etichettaDestra);
			listaEtichetteSud.add(etichettaSotto);
			listaEtichetteSudEst.add(etichettaAngolo);
			tavoliGrafici.add(tavoloCorrente);
		}
		
		setVisible(true);
		setResizable(false);
	}
	
	private class pannelloTavoli extends JPanel
	{
		public void paintComponent (Graphics g)
		{
		g.drawLine(0,0,this.getBounds().width,0);
		g.drawLine(0,0,0,this.getBounds().height);
		g.drawLine(this.getBounds().width-1,0,this.getBounds().width-1,this.getBounds().height-1);
		g.drawLine(0, this.getBounds().height-1,this.getBounds().width,this.getBounds().height-1);
		}
	}
	
	private class gestoreIcone implements MouseListener, MouseMotionListener
	{ 
		public void mousePressed (MouseEvent e)
		{
			boolean tavolo = false;
			int controllo = -1;
			while(!tavolo && controllo < tavoliGrafici.size())
				{
					controllo++;
					if(e.getSource() == tavoliGrafici.get(controllo))
					{
						tavolo = true;
						tavoloSelezionato = tavoliGrafici.get(controllo);
					}
				}
			
				if(tavolo)
				{
					if (e.getPoint().distance(tavoloSelezionato.getWidth(),tavoloSelezionato.getHeight())<=6) {
						//Angolo in basso a destra
						ridimensionamentoVerticale = false;
						ridimensionamentoOrizzontale = false;
						ridimensionamentoTotale = true;
						spostamento = false;
					}
					else if (Math.abs(e.getY()-tavoloSelezionato.getHeight())<=6) {
						//Lato in basso
						ridimensionamentoVerticale = true;
						ridimensionamentoOrizzontale = false;
						ridimensionamentoTotale = false;
						spostamento = false;
					}
					else if (Math.abs(e.getX()-tavoloSelezionato.getWidth())<=6) {
						//Lato a destra
						ridimensionamentoVerticale = false;
						ridimensionamentoOrizzontale = true;
						ridimensionamentoTotale = false;
						spostamento = false;
					}
					else {
						//Centro
						ridimensionamentoVerticale = false;
						ridimensionamentoOrizzontale = false;
						ridimensionamentoTotale = false;
						spostamento = true;
						
						tavoloSelezionato.setVisible(false);
						
						tavoloTrascinamento = new JLabel(tavoloSelezionato.getText());
						tavoloTrascinamento.setBackground(tavoloSelezionato.getBackground());
						tavoloTrascinamento.setOpaque(true);
						tavoloTrascinamento.setBounds(tavoloSelezionato.getBounds());
						tavoloTrascinamento.setBorder(tavoloSelezionato.getBorder());
						tavoloTrascinamento.setHorizontalAlignment(SwingConstants.CENTER);	
						areaDiDisegno.add(tavoloTrascinamento,0,1 );
					}
					
					puntoDiPartenza = e.getPoint();
					rettangoloDiPartenza = tavoloSelezionato.getBounds();
					
				}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			if (spostamento) {
				
				tavoloSelezionato.setBounds(tavoloTrascinamento.getBounds());
				tavoloTrascinamento.setVisible(false);
				tavoloSelezionato.setVisible(true);
				
				for (EstensoreEst etichetta : listaEtichetteEst) {
					if(etichetta.getTavoloAssociato().equals(tavoloTrascinamento))
							etichetta.setTavoloAssociato(tavoloSelezionato);
				}
				
				for (EstensoreSud etichetta : listaEtichetteSud) {
					if(etichetta.getTavoloAssociato().equals(tavoloTrascinamento))
						etichetta.setTavoloAssociato(tavoloSelezionato);
				}
				
				for (EstensoreSudEst etichetta : listaEtichetteSudEst) {
					if(etichetta.getTavoloAssociato().equals(tavoloTrascinamento))
						etichetta.setTavoloAssociato(tavoloSelezionato);
				}
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
		
		}	

		@Override
		public void mouseDragged(MouseEvent e) {
			
			
			
			if (ridimensionamentoVerticale) {
				
				int differenzaVerticale = (int) (e.getY()-puntoDiPartenza.getY());
				tavoloSelezionato.setBounds((int) rettangoloDiPartenza.getX(), (int) rettangoloDiPartenza.getY(), 
						(int) rettangoloDiPartenza.getWidth(),
						(int) (rettangoloDiPartenza.getHeight()+differenzaVerticale));
				
				aggiornamentoEtichette(tavoloSelezionato);
				
			}
			else if (ridimensionamentoOrizzontale) {
				
				int differenzaOrizzontale = (int) (e.getX()-puntoDiPartenza.getX());
				tavoloSelezionato.setBounds((int) rettangoloDiPartenza.getX(), (int) rettangoloDiPartenza.getY(), 
						(int) (rettangoloDiPartenza.getWidth()+differenzaOrizzontale), (int) rettangoloDiPartenza.getHeight());
				
				aggiornamentoEtichette(tavoloSelezionato);
				
			}
			else if (ridimensionamentoTotale) {
				
				int differenzaVerticale = (int) (e.getY()-puntoDiPartenza.getY());
				int differenzaOrizzontale = (int) (e.getX()-puntoDiPartenza.getX());
				tavoloSelezionato.setBounds((int) rettangoloDiPartenza.getX(), (int) rettangoloDiPartenza.getY(), 
						(int) (rettangoloDiPartenza.getWidth()+differenzaOrizzontale), 
						(int) (rettangoloDiPartenza.getHeight()+differenzaVerticale));

				aggiornamentoEtichette(tavoloSelezionato);
				
			}
			else if (spostamento) {
				
				int differenzaVerticale = e.getY()- (int) (puntoDiPartenza.getY());
				int differenzaOrizzontale = e.getX()- (int) (puntoDiPartenza.getX());
				tavoloTrascinamento.setBounds(((int) (rettangoloDiPartenza.getX())) +differenzaOrizzontale, 
						((int) (rettangoloDiPartenza.getY())) + differenzaVerticale, 
						(int) rettangoloDiPartenza.getWidth(), 
						(int) rettangoloDiPartenza.getHeight());
				
				for (EstensoreEst etichetta : listaEtichetteEst) {
					if(etichetta.getTavoloAssociato().equals(tavoloSelezionato))
							etichetta.setTavoloAssociato(tavoloTrascinamento);
				}
				
				for (EstensoreSud etichetta : listaEtichetteSud) {
					if(etichetta.getTavoloAssociato().equals(tavoloSelezionato))
						etichetta.setTavoloAssociato(tavoloTrascinamento);
				}
				
				for (EstensoreSudEst etichetta : listaEtichetteSudEst) {
					if(etichetta.getTavoloAssociato().equals(tavoloSelezionato))
						etichetta.setTavoloAssociato(tavoloTrascinamento);
				}
				
				aggiornamentoEtichette(tavoloTrascinamento);
				
			}		
			
		}
		
		public void mouseMoved(MouseEvent e) {
		
		}
	}
	
	public boolean presentiSovrapposizioni (ArrayList<JLabel> listaPannelliTavoli) {
		
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
	
	public boolean presentiTavoliTroppoPiccoli (ArrayList<JLabel> listaPannelliTavoli) {
		
		boolean piccoli = false;
		
		for (int i = 0; i<listaPannelliTavoli.size(); i++) {
			
			Rectangle rettangoloCorrente = new Rectangle(listaPannelliTavoli.get(i).getBounds());
					
			if (rettangoloCorrente.getWidth()<10 || rettangoloCorrente.getHeight()<10) {
				piccoli = true;
			}
		}
		
		return piccoli;
		
	}
	
	public boolean presentiTavoliFuoriFinestra (ArrayList<JLabel> listaPannelliTavoli, JLayeredPane finestra) {
		
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
	
	public void aggiornamentoEtichette (JLabel tavoloScelto) {
		
		for (EstensoreEst etichetta : listaEtichetteEst) {
			if(etichetta.getTavoloAssociato().equals(tavoloScelto))
					etichetta.setLocation(tavoloScelto.getX()+tavoloScelto.getWidth()-3, 
							(tavoloScelto.getY()+tavoloScelto.getHeight()/2)-3);
		}
		
		for (EstensoreSud etichetta : listaEtichetteSud) {
			if(etichetta.getTavoloAssociato().equals(tavoloScelto))
					etichetta.setLocation((tavoloScelto.getX()+tavoloScelto.getWidth()/2)-3, 
							tavoloScelto.getY()+tavoloScelto.getHeight()-3);
		}
		
		for (EstensoreSudEst etichetta : listaEtichetteSudEst) {
			if(etichetta.getTavoloAssociato().equals(tavoloScelto))
				etichetta.setLocation(tavoloScelto.getX()+tavoloScelto.getWidth()-3, 
						tavoloScelto.getY()+tavoloScelto.getHeight()-3);
		}
		
		if (tavoloScelto.getWidth()<=0||tavoloScelto.getHeight()<=0) {
			
			for (EstensoreEst etichetta : listaEtichetteEst) {
				if(etichetta.getTavoloAssociato().equals(tavoloScelto))
						etichetta.setVisible(false);
			}
			
			for (EstensoreSud etichetta : listaEtichetteSud) {
				if(etichetta.getTavoloAssociato().equals(tavoloScelto))
						etichetta.setVisible(false);
			}
			
			for (EstensoreSudEst etichetta : listaEtichetteSudEst) {
				if(etichetta.getTavoloAssociato().equals(tavoloScelto))
						etichetta.setVisible(false);
			}
			
		} else {
			
			for (EstensoreEst etichetta : listaEtichetteEst) {
				if(etichetta.getTavoloAssociato().equals(tavoloScelto))
						etichetta.setVisible(true);
			}
			
			for (EstensoreSud etichetta : listaEtichetteSud) {
				if(etichetta.getTavoloAssociato().equals(tavoloScelto))
						etichetta.setVisible(true);
			}
			
			for (EstensoreSudEst etichetta : listaEtichetteSudEst) {
				if(etichetta.getTavoloAssociato().equals(tavoloScelto))
						etichetta.setVisible(true);
			}
			
		}
		
	}
	
	private class EstensoreEst extends JLabel{
		
		private JLabel tavoloGraficoAssociato;
		
		public JLabel getTavoloAssociato() {
			return tavoloGraficoAssociato;
		}

		public void setTavoloAssociato(JLabel tavoloAssociato) {
			this.tavoloGraficoAssociato = tavoloAssociato;
		}

		EstensoreEst(JLabel tavolo){
			this.tavoloGraficoAssociato = tavolo;
		}
		
	}
	
	private class EstensoreSud extends JLabel{
		
		private JLabel tavoloAssociato;
		
		public JLabel getTavoloAssociato() {
			return tavoloAssociato;
		}

		public void setTavoloAssociato(JLabel tavoloAssociato) {
			this.tavoloAssociato = tavoloAssociato;
		}

		EstensoreSud(JLabel tavolo){
			this.tavoloAssociato = tavolo;
		}
		
	}
	
	private class EstensoreSudEst extends JLabel{
		
		private JLabel tavoloAssociato;
		
		public JLabel getTavoloAssociato() {
			return tavoloAssociato;
		}

		public void setTavoloAssociato(JLabel tavoloAssociato) {
			this.tavoloAssociato = tavoloAssociato;
		}

		EstensoreSudEst(JLabel tavolo){
			this.tavoloAssociato = tavolo;
		}
		
	}
}