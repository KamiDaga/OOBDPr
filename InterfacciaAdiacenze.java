import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class InterfacciaAdiacenze extends JFrame {

	private Controller theController;
	private Sala sala;
	private Tavolo tavoloProtagonista;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> tavoliGrafici = new ArrayList<JLabel>();
	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private JLabel tavoloSelezionato;
	private Tavolo tavoloEntitaSelezionato;
	private JButton bottoneIndietro;
	private JButton bottoneConferma;
	private JLabel iconaInformazioni;
	
	public InterfacciaAdiacenze(Controller c, Tavolo tavoloScelto) {
		super("Adiacenze del tavolo numero " + tavoloScelto.getNumero() + " della sala "+ tavoloScelto.getSala_App().getNome());
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
	
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		theController = c;
		tavoloProtagonista = tavoloScelto;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 547);
		this.sala = tavoloScelto.getSala_App();
		getContentPane().setLayout(null);
		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 694, 407);
		getContentPane().add(areaDiDisegno);
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 694, 407);
		areaDiDisegno.add(panel, 0,1);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.adiacenzeIndietroPremuto(sala);
			}
		});
		bottoneIndietro.setBounds(10, 429, 89, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneIndietro);
		
		gestoreIcone handler = new gestoreIcone();
		
		bottoneConferma = new JButton("Conferma");
		bottoneConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theController.bottoneConfermaModificheAdiacenzePremuto(tavoloProtagonista);
			}
		});
		bottoneConferma.setBounds(594, 429, 110, 23);
		bottoneConferma.setBorder(null);
		bottoneConferma.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneConferma);
		background = new JLabel();
		background.setBounds(0, 0, 694, 407);
		background.setBackground(Color.white);
		background.setOpaque(true);
		areaDiDisegno.add(background,0,-1);
		
		iconaInformazioni = new JLabel();
		iconaInformazioni.setIcon(UIManager.getIcon("OptionPane.informationIcon"));	
		iconaInformazioni.setBounds(672, 463, 32, 43);
		iconaInformazioni.setToolTipText("<html>Cliccare su un tavolo marrone per renderlo adiacente a quello selezionato nella schermata precedente (diventera' giallo).<br>Cliccare su un tavolo giallo per non renderlo piU' adiacente a quello selezionato (diventera' marrone).<br></html>");
		getContentPane().add(iconaInformazioni);
		
		tavoli = theController.estrazioneTavoliSala(sala);
		tavoloProtagonista.setTavoliAdiacenti(theController.estraiTavoliAdiacenti (tavoloProtagonista));
		for (Tavolo tavolo: tavoli)
		{
			JLabel tavoloCorrente = new JLabel(String.format("%d",tavolo.getNumero()));
			tavoloCorrente.addMouseListener(handler);
			tavoloCorrente.setBackground(new Color(129,116,37));
			tavoloCorrente.setOpaque(true);
			tavoloCorrente.setBounds(tavolo.getPosX(), tavolo.getPosY(), tavolo.getDimX(), tavolo.getDimY());
			tavoloCorrente.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			tavoloCorrente.setHorizontalAlignment(SwingConstants.CENTER);
			
			
			for (Tavolo tavoloAdiacente : tavoloProtagonista.getTavoliAdiacenti()) {
				if (tavoloAdiacente.getNumero() == (tavolo.getNumero()))
					tavoloCorrente.setBackground(Color.YELLOW);
			}
			
			areaDiDisegno.add(tavoloCorrente,0,0);
	
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
			g.drawLine(this.getBounds().width -1,0,this.getBounds().width -1,this.getBounds().height -1);
			g.drawLine(0,this.getBounds().height -1,this.getBounds().width -1,this.getBounds().height -1);
		}
	}
	
	private class gestoreIcone implements MouseListener
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
						tavoloEntitaSelezionato = trovaTavoloAssociato(Integer.parseInt(tavoloSelezionato.getText()));
					}
				}
			
				if(tavolo)
				{
					if (Integer.parseInt(tavoloSelezionato.getText()) == tavoloProtagonista.getNumero()) {
						JOptionPane.showMessageDialog(null, "Un tavolo non puo' essere adiacente di se stesso!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}
					else {
						if (tavoloSelezionato.getBackground().equals(Color.YELLOW)) {
							//Deseleziona
							boolean trovato = false;
							tavoloSelezionato.setBackground(new Color(129,116,37));
							for(Tavolo tavoloAdiacente : tavoloProtagonista.getTavoliAdiacenti()) {
								if (tavoloAdiacente.getNumero() == (tavoloEntitaSelezionato.getNumero())) {
									tavoloEntitaSelezionato = tavoloAdiacente;
									trovato = true;
								}
							}

							if (trovato)
								tavoloProtagonista.getTavoliAdiacenti().remove(tavoloEntitaSelezionato);

						}
						else {
							//Seleziona
							tavoloSelezionato.setBackground(Color.YELLOW);
							tavoloProtagonista.getTavoliAdiacenti().add(tavoloEntitaSelezionato);
						}
					}
					
				}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
		}	
	}
	
	public Tavolo trovaTavoloAssociato (int numeroTavolo) {
		
		Tavolo tavoloTrovato = null;
		
		for (Tavolo tavolo : tavoli) {
			if (tavolo.getNumero() == numeroTavolo) {
				tavoloTrovato = tavolo;
			}
		}
		
		return tavoloTrovato;		
		
	}
}
