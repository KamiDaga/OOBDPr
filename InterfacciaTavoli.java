import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLayeredPane;
public class InterfacciaTavoli extends JFrame 
{ 
	private Controller theController;
	private Sala sala;
	private ArrayList<Tavolo> tavoli = new ArrayList<Tavolo>();
	private ArrayList<JLabel> tavoliGrafici = new ArrayList<JLabel>();
	private JButton bottoneAggiuntaTavolo;
	private JButton bottoneIndietro;
	private JButton bottoneModificaLayout;
	private JButton bottoneGestisciAdiacenze;
	private JButton bottoneGestisciOccupazione;
	private int numeroTavoloSelezionato = 0;
	private JLabel background;
	private JLayeredPane areaDiDisegno;
	private JButton bottoneModificaDatiTavolo;
	private JButton bottoneEliminaTavolo;
	public InterfacciaTavoli(Controller c, Sala salaCorrente) {
  
		super("Visualizzazione tavoli di "+ salaCorrente.getNome());
		getContentPane().setLayout(null);

		getContentPane().setBackground(new Color(20,20,40));
		
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		theController = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 600);
    
		this.sala = salaCorrente;
		getContentPane().setLayout(null);
		
		bottoneGestisciOccupazione = new JButton("Gestisci occupazioni della sala");
		bottoneGestisciOccupazione.setBounds(372, 497, 332, 23);
		getContentPane().add(bottoneGestisciOccupazione);
		bottoneGestisciOccupazione.setBackground(new Color(0, 255, 127));
		bottoneGestisciOccupazione.setBorder(null);
		bottoneGestisciOccupazione.setOpaque(true);
		
		bottoneGestisciAdiacenze = new JButton("Gestisci tavoli adiacenti a quello selezionato");
		bottoneGestisciAdiacenze.setBounds(372, 463, 332, 23);
		getContentPane().add(bottoneGestisciAdiacenze);
		bottoneGestisciAdiacenze.setEnabled(false);
		bottoneGestisciAdiacenze.setBackground(new Color(0, 255, 127));
		bottoneGestisciAdiacenze.setBorder(null);
		bottoneGestisciAdiacenze.setOpaque(true);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 527, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		
		bottoneAggiuntaTavolo = new JButton("Aggiungi tavolo");
		bottoneAggiuntaTavolo.setBounds(10, 429, 332, 23);
		getContentPane().add(bottoneAggiuntaTavolo);
		bottoneAggiuntaTavolo.setBackground(new Color(0, 255, 127));
		bottoneAggiuntaTavolo.setBorder(null);
		bottoneAggiuntaTavolo.setOpaque(true);
		
		pannelloTavoli panel = new pannelloTavoli();
		panel.setBounds(0, 0, 694, 407);

		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 694, 407);
		getContentPane().add(areaDiDisegno);
		
		bottoneModificaDatiTavolo = new JButton("Modifica dati del tavolo selezionato");
		bottoneModificaDatiTavolo.setBounds(10, 463, 332, 23);
		getContentPane().add(bottoneModificaDatiTavolo);
		bottoneModificaDatiTavolo.setEnabled(false);
		bottoneModificaDatiTavolo.setBackground(new Color(0, 255, 127));
		bottoneModificaDatiTavolo.setBorder(null);
		bottoneModificaDatiTavolo.setOpaque(true);
		
		bottoneEliminaTavolo = new JButton("Elimina tavolo selezionato");
		bottoneEliminaTavolo.setBounds(10, 497, 332, 23);
		getContentPane().add(bottoneEliminaTavolo);
		areaDiDisegno.add(panel, 0,1);
		bottoneEliminaTavolo.setEnabled(false);
		bottoneEliminaTavolo.setBackground(new Color(0, 255, 127));
		bottoneEliminaTavolo.setBorder(null);
		bottoneEliminaTavolo.setOpaque(true);
		
		bottoneModificaLayout = new JButton("Modifica layout");
		bottoneModificaLayout.setBounds(372, 429, 332, 23);
		getContentPane().add(bottoneModificaLayout);
		bottoneModificaLayout.setBackground(new Color(0, 255, 127));
		bottoneModificaLayout.setBorder(null);
		bottoneModificaLayout.setOpaque(true);
		
		background = new JLabel();
		background.setBounds(0, 0, 694, 407);
		background.setBackground(Color.white);
		background.setOpaque(true);
		areaDiDisegno.add(background, 0,-1);
		
		gestoreIcone handler = new gestoreIcone();
		tavoli = theController.estrazioneTavoliSala(salaCorrente);
		
		for (Tavolo tavolo : tavoli)
		{
			JLabel tavoloGraficoCorrente = new JLabel(String.format("%d", tavolo.getNumero()),SwingConstants.CENTER);
			tavoloGraficoCorrente.setBackground(new Color(129,116,37));
			tavoloGraficoCorrente.setOpaque(true);
			tavoloGraficoCorrente.setBounds(tavolo.getPosX(), tavolo.getPosY(), tavolo.getDimX(), tavolo.getDimY());
			tavoloGraficoCorrente.addMouseListener(handler);
			tavoloGraficoCorrente.setToolTipText("Capacita': " + tavolo.getCapacita());

			tavoloGraficoCorrente.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			areaDiDisegno.add(tavoloGraficoCorrente,0,1);
			tavoliGrafici.add(tavoloGraficoCorrente);
		}
		
		if(tavoli.size() == 0)
		{
			bottoneGestisciOccupazione.setEnabled(false);
		}
		
		GestioneBottoni handlerB = new GestioneBottoni();
		
		bottoneAggiuntaTavolo.addActionListener(handlerB);
		bottoneGestisciOccupazione.addActionListener(handlerB);
		bottoneGestisciAdiacenze.addActionListener(handlerB);
		bottoneIndietro.addActionListener(handlerB);
		bottoneModificaDatiTavolo.addActionListener(handlerB);
		bottoneEliminaTavolo.addActionListener(handlerB);
		bottoneModificaLayout.addActionListener(handlerB);

		areaDiDisegno.addMouseListener(new gestoreSfondo());
		

		setVisible(true);
		setResizable(false);
	}
	private class GestioneBottoni implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == bottoneAggiuntaTavolo)
				{
					theController.bottoneAggiuntaTavoloPremuto(sala, tavoli);
				}
				else if(e.getSource() == bottoneGestisciOccupazione)
				{
					theController.bottoneGestioneOccupazioneInterfacciaTavoliPremuto(tavoli);
				}
				else if (e.getSource() == bottoneGestisciAdiacenze)
				{
					theController.bottoneGestisciAdiacenze(trovaTavoloAssociato(numeroTavoloSelezionato));
				}
				else if(e.getSource() == bottoneModificaLayout)
				{
					theController.bottoneModificaLayoutPremuto(sala);
				}
				else if(e.getSource() == bottoneModificaDatiTavolo)
				{
					theController.bottoneModificaDatiPremuto(trovaTavoloAssociato(numeroTavoloSelezionato));
				}
				else if(e.getSource() == bottoneEliminaTavolo)
				{
					theController.bottoneEliminaTavoloPremuto(trovaTavoloAssociato(numeroTavoloSelezionato));
					tavoli.remove(trovaTavoloAssociato(numeroTavoloSelezionato));
					areaDiDisegno.remove(trovaLabelTavoloAssociato(numeroTavoloSelezionato));	
					areaDiDisegno.validate();
					areaDiDisegno.repaint();
					bottoneGestisciAdiacenze.setEnabled(false);
					bottoneEliminaTavolo.setEnabled(false);
					bottoneModificaDatiTavolo.setEnabled(false);
				}
				else if(e.getSource() == bottoneIndietro)
				{
					theController.bottoneIndietroGestioneTavoliPremuto(sala);
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
	
	private class gestoreIcone implements MouseListener
	{ 
	public void mouseClicked(MouseEvent e)
	{
		boolean tavolo = false;
		boolean aggiustato = false;
		int controllo = 0;
		if(numeroTavoloSelezionato!= 0)
		{
			while(controllo<tavoliGrafici.size() && !aggiustato)
			{
				
				if(tavoliGrafici.get(controllo).getText().equals(String.format("%d", numeroTavoloSelezionato)))
				{
					aggiustato = true;
					tavoliGrafici.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
				controllo++;
			}
		}
		controllo = -1;
		while(!tavolo && controllo < tavoliGrafici.size())
			{
				controllo++;
				if(e.getSource() == tavoliGrafici.get(controllo))
				{
					tavolo = true;
					numeroTavoloSelezionato = Integer.parseInt(tavoliGrafici.get(controllo).getText());
					tavoliGrafici.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				}
			}
			if(tavolo)
			{
				bottoneGestisciAdiacenze.setEnabled(true);
				bottoneEliminaTavolo.setEnabled(true);
				bottoneModificaDatiTavolo.setEnabled(true);
			}
		} public void mousePressed(MouseEvent e) {
		
		}
		public void mouseReleased(MouseEvent e) {
		
		} public void mouseEntered(MouseEvent e) {
		
		} public void mouseExited(MouseEvent e) {
		
		}
	}

	private class gestoreSfondo implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {

			for (JLabel tavoloGrafico : tavoliGrafici) {
				tavoloGrafico.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			}
			bottoneGestisciAdiacenze.setEnabled(false);
			bottoneEliminaTavolo.setEnabled(false);
			bottoneModificaDatiTavolo.setEnabled(false);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			

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
	
	public JLabel trovaLabelTavoloAssociato (int numeroTavolo) {
		
		JLabel tavoloTrovato = null;
		
		for (JLabel tavoloGrafico : tavoliGrafici) {
			if (Integer.parseInt(tavoloGrafico.getText()) == numeroTavolo) {
				tavoloTrovato = tavoloGrafico;
			}
		}
		
		return tavoloTrovato;	
	}
}
