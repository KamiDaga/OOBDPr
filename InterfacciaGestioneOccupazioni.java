import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;

public class InterfacciaGestioneOccupazioni extends JFrame 
{
	private int numeroTavoloSelezionato;
	private ArrayList<JLabel> tavoliGrafici = new ArrayList<JLabel>();
	private JLayeredPane areaDiDisegno;
	private JLabel background;
	private Controller theController;
	private ArrayList<Tavolo> tavoli;
	private ArrayList<Integer> tavoliOccupati;
	private JButton bottoneIndietro;
	private JTextField textFieldNumeroAvventori;
	private JButton bottoneVediOccupazione;
	private JButton bottoneOccupaTavolo;
	private String data;
	private int indiceTavoloSelezionato;
	private boolean interoCorretto;
	private JLabel iconaInformazioni;
	private boolean occupato = true;
	private JLabel numeroOccupanti;
	private pannelloTavoli panel;
	
	public InterfacciaGestioneOccupazioni(Controller controller,ArrayList<Tavolo> tavoli, String data) 
	{
		super("Occupazioni della sala "+tavoli.get(0).getSala_App().getNome()+" del "+ data);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 600);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		ImageIcon icona = new ImageIcon("src/iconaProgetto.jpeg");
		setIconImage(icona.getImage());
		this.theController = controller;
		this.tavoli = tavoli;
		this.data = data;
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		areaDiDisegno = new JLayeredPane();
		areaDiDisegno.setBounds(10, 11, 694, 407);
		getContentPane().add(areaDiDisegno);
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(10, 527, 105, 23);
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.addActionListener(new GestoreBottoni());
		
		bottoneVediOccupazione = new JButton("Vedi occupazione");
		bottoneVediOccupazione.setBounds(10, 429, 130, 23);
		bottoneVediOccupazione.setBorder(null);
		bottoneVediOccupazione.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneVediOccupazione);
		bottoneVediOccupazione.addActionListener(new GestoreBottoni());
		
		bottoneOccupaTavolo = new JButton("Occupare");
		bottoneOccupaTavolo.setBounds(195, 429, 105, 23);
		bottoneOccupaTavolo.setBorder(null);
		bottoneOccupaTavolo.setBackground(new Color(0, 255, 127));
		getContentPane().add(bottoneOccupaTavolo);
		bottoneOccupaTavolo.addActionListener(new GestoreBottoni());
		
		
		
		textFieldNumeroAvventori = new JTextField();
		textFieldNumeroAvventori.setBounds(195, 468, 105, 20);
		getContentPane().add(textFieldNumeroAvventori);
		textFieldNumeroAvventori.setColumns(10);
		textFieldNumeroAvventori.addKeyListener(new GestioneTesto());
		textFieldNumeroAvventori.setFocusable(true);
		
		numeroOccupanti = new JLabel("Numero di avventori");
		numeroOccupanti.setForeground(Color.WHITE);
		numeroOccupanti.setBackground(Color.WHITE);
		numeroOccupanti.setBounds(195, 453, 105, 14);
		getContentPane().add(numeroOccupanti);
		background = new JLabel();
		background.setBounds(0, 0, 694, 407);
		background.setBackground(Color.white);
		background.setOpaque(true);
		background.addMouseListener(new GestoreIcone());
		
		panel = new pannelloTavoli();
		panel.setBounds(0, 0, 694, 407);
		
		GestoreIcone handler = new GestoreIcone();
		
		for (Tavolo tavoloCorrente: tavoli)
		{
			JLabel tavoloGraficoCorrente = new JLabel(String.format("%d",tavoloCorrente.getNumero()),SwingConstants.CENTER);
			tavoloGraficoCorrente.setBackground(new Color(129,116,37));
			tavoloGraficoCorrente.setOpaque(true);
			tavoloGraficoCorrente.setBounds(tavoloCorrente.getPosX(), tavoloCorrente.getPosY(), tavoloCorrente.getDimX(), tavoloCorrente.getDimY());
			tavoloGraficoCorrente.addMouseListener(handler);
			tavoloGraficoCorrente.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			tavoloGraficoCorrente.setToolTipText("Capacita': " + tavoloCorrente.getCapacita());
			
			areaDiDisegno.add(tavoloGraficoCorrente,0,1);
			tavoliGrafici.add(tavoloGraficoCorrente);
		}
		
		tavoliOccupati = theController.estrazioneTavoliOccupatiInData(tavoli.get(0).getSala_App(), data);
		
		for (int i = 0; i<tavoliOccupati.size(); i++)
		{
			for (int j = 0; j<tavoli.size();j++)
			{
				if(tavoliOccupati.get(i)== tavoli.get(j).getNumero())
				{
					for (int k = 0; k<tavoliGrafici.size(); k++)
					{
						if(Integer.parseInt(tavoliGrafici.get(k).getText()) == tavoli.get(j).getNumero())
						{
							tavoliGrafici.get(k).setBackground(Color.RED);
							tavoliGrafici.get(k).setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
						}
					}
				}
			}
		}
		
		bottoneVediOccupazione.setEnabled(false);
		bottoneOccupaTavolo.setEnabled(false);
		
		iconaInformazioni = new JLabel();
		iconaInformazioni.setIcon(UIManager.getIcon("OptionPane.informationIcon"));	
		iconaInformazioni.setBounds(622, 429, 32, 43);
		iconaInformazioni.setToolTipText("<html>In rosso: tavoli gia occupati;<br>In marrone tavoli da occupare.<br>Per occupare un tavolo bisogna inserire un numero di occupanti che rispetti la sua capacità.</html>");
	
		getContentPane().add(iconaInformazioni);
		areaDiDisegno.add(panel,0,1);
		areaDiDisegno.add(background,0,-1);
		
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
	
	private class GestoreBottoni implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==bottoneOccupaTavolo)
			{
				theController.bottoneOccupaGestioneOccupazionePremuto(Integer.parseInt(textFieldNumeroAvventori.getText()), tavoli,indiceTavoloSelezionato, data);
			}
			else if(e.getSource() == bottoneVediOccupazione)
			{
				theController.bottoneVisualizzaOccupazioneGestioneOccupazione(tavoli, data, indiceTavoloSelezionato);
			}
			else if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroGestioneOccupazioniPremuto(tavoli);
			}
		}
	}
	
	private class GestoreIcone implements MouseListener
	{ 
	public void mouseClicked(MouseEvent e)
	{
		boolean tavolo = false;
		boolean aggiustato = false;
		int controllo = 0;
		occupato  = false;
		if(e.getSource() == background)
		{
			bottoneVediOccupazione.setEnabled(false);
		}
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

		controllo = 0;
		while(!tavolo && controllo < tavoliGrafici.size())
			{
				
				if(e.getSource() == tavoliGrafici.get(controllo))
				{
					int controllo2 = 0;
					boolean trovato = false;
					tavolo = true;
					numeroTavoloSelezionato = Integer.parseInt(tavoliGrafici.get(controllo).getText());
					while(!trovato && controllo2<tavoli.size())
					{
						if(tavoli.get(controllo2).getNumero() == numeroTavoloSelezionato)
						{
							trovato = true;
							indiceTavoloSelezionato = controllo2;
							if(tavoliOccupati.contains(tavoli.get(controllo2).getNumero()))
							{
								occupato = true;
							}
						}
						controllo2++;
					}
					tavoliGrafici.get(controllo).setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
				}
				controllo++;
			}
		if(e.getSource() == background)
		{
			numeroTavoloSelezionato = 0;
			bottoneVediOccupazione.setEnabled(false);
		}
		
			try
			{
				if(!textFieldNumeroAvventori.getText().isBlank())
				{
					if(Integer.parseInt(textFieldNumeroAvventori.getText())>0 && Integer.parseInt(textFieldNumeroAvventori.getText())<= tavoli.get(indiceTavoloSelezionato).getCapacita()) interoCorretto = true;
					else interoCorretto = false;
				}
				else
				{
					interoCorretto = false;
				}
			}
			catch(NumberFormatException n)
			{
				interoCorretto = false;
			}
		
			if(numeroTavoloSelezionato != 0 && interoCorretto) bottoneOccupaTavolo.setEnabled(true);
			
			else bottoneOccupaTavolo.setEnabled(false);
			
			if(occupato) 
				{
					bottoneVediOccupazione.setEnabled(true);
					bottoneOccupaTavolo.setEnabled(false);
				}
			else bottoneVediOccupazione.setEnabled(false);
			
			
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {} 
		public void mouseEntered(MouseEvent e) {} 
		public void mouseExited(MouseEvent e) {}
	}
	private class GestioneTesto implements KeyListener
	{

	
		public void keyTyped(KeyEvent e) {}

	
		public void keyPressed(KeyEvent e) {}

		
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(!textFieldNumeroAvventori.getText().isBlank())
				{
					if(Integer.parseInt(textFieldNumeroAvventori.getText())>0 &&  Integer.parseInt(textFieldNumeroAvventori.getText())<= tavoli.get(indiceTavoloSelezionato).getCapacita()) interoCorretto = true;
					else interoCorretto = false;
				}
				else
				{
					interoCorretto = false;
				}
			}
			catch(NumberFormatException n)
			{
				interoCorretto = false;
			}
			
			if(numeroTavoloSelezionato != 0 && interoCorretto && !occupato) bottoneOccupaTavolo.setEnabled(true);
			
			else bottoneOccupaTavolo.setEnabled(false);
			
		}
		
	}
}

