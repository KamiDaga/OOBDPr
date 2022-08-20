import java.awt.BorderLayout;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
public class InterfacciaSale extends JFrame 
{
	private Ristorante ristorante;
	private ArrayList<Sala> listaSale = new ArrayList<Sala>();
	private DefaultListModel<Sala> modelloLista = new DefaultListModel<Sala>();
	private JList<Sala> listaVisibile = new JList<>(modelloLista);
	private int elementoSelezionato = -1;
	private JButton bottoneGestioneCamerieri;
	private JButton bottoneRimuoviSala ;
	private JButton bottoneVediTavoli ;
	private JButton bottoneAggiuntaSala;
	private JScrollPane scorrimentoPerListaVisibile;
	private Controller theController;
	private JButton bottoneIndietro;
	private JButton bottoneModificaSala;

	public InterfacciaSale(Controller c, Ristorante ristorante) 
	{
		super("Sale del ristorante "+ ristorante.getNome());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 301);

		getContentPane().setLayout(null);
		theController = c;
		this.ristorante = ristorante;
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		bottoneAggiuntaSala = new JButton("Aggiungi sala");
		bottoneAggiuntaSala.setBounds(295, 77, 191, 23);
		getContentPane().add(bottoneAggiuntaSala);
		bottoneAggiuntaSala.setBackground(new Color(0, 255, 127));
		bottoneAggiuntaSala.setBorder(null);
		bottoneAggiuntaSala.setOpaque(true);
		
		bottoneGestioneCamerieri = new JButton("Gestisci camerieri");
		bottoneGestioneCamerieri.setBounds(295, 11, 191, 23);
	    getContentPane().add(bottoneGestioneCamerieri);
	    bottoneGestioneCamerieri.setBackground(new Color(0, 255, 127));
	    bottoneGestioneCamerieri.setBorder(null);
	    bottoneGestioneCamerieri.setOpaque(true);
		
		bottoneRimuoviSala = new JButton("Rimuovi sala selezionata");
		bottoneRimuoviSala.setBounds(295, 145, 191, 23);
		if (elementoSelezionato == -1) bottoneRimuoviSala.setEnabled(false);
		getContentPane().add(bottoneRimuoviSala);
		bottoneRimuoviSala.setBackground(new Color(0, 255, 127));
		bottoneRimuoviSala.setBorder(null);
		bottoneRimuoviSala.setOpaque(true);
		
		bottoneVediTavoli = new JButton("Vedi i tavoli della sala selezionata");
		bottoneVediTavoli.setBounds(295, 198, 191, 23);
		getContentPane().add(bottoneVediTavoli);
		if (elementoSelezionato == -1) bottoneVediTavoli.setEnabled(false);
		bottoneVediTavoli.setBackground(new Color(0, 255, 127));
		bottoneVediTavoli.setBorder(null);
		bottoneVediTavoli.setOpaque(true);
		
		scorrimentoPerListaVisibile =new JScrollPane();
		scorrimentoPerListaVisibile.setBounds(10, 11, 253, 210);
	    getContentPane().add(scorrimentoPerListaVisibile);
		
	    scorrimentoPerListaVisibile.setViewportView(listaVisibile);
	    
	    bottoneIndietro = new JButton("Indietro");
	    bottoneIndietro.setBounds(10, 234, 120, 17);
	    getContentPane().add(bottoneIndietro);
	    bottoneIndietro.setBackground(new Color(0, 255, 127));
	    bottoneIndietro.setBorder(null);
	    bottoneIndietro.setOpaque(true);

	    bottoneModificaSala = new JButton("Modifica nome sala");
	    bottoneModificaSala.setBounds(295, 111, 191, 23);
	    getContentPane().add(bottoneModificaSala);
	    GestoreClickMouse handler = new GestoreClickMouse();
	    if (elementoSelezionato == -1) bottoneModificaSala.setEnabled(false);
	    bottoneModificaSala.setBackground(new Color(0, 255, 127));
	    bottoneModificaSala.setBorder(null);
	    bottoneModificaSala.setOpaque(true);

		GestoreSelezioneLista selezione = new GestoreSelezioneLista();
		listaVisibile.addListSelectionListener(selezione);
		
		bottoneIndietro.addActionListener(handler);
		bottoneRimuoviSala.addActionListener(handler);
		bottoneAggiuntaSala.addActionListener(handler);
		bottoneGestioneCamerieri.addActionListener(handler);
		bottoneVediTavoli.addActionListener(handler);
		bottoneModificaSala.addActionListener(handler);

		listaSale.clear();
		listaSale = theController.estraiSaleRistorante(ristorante);
		
		modelloLista.removeAllElements();
		modelloLista.addAll(listaSale);
		
		listaVisibile.setBackground(new Color(20, 20, 40));
		listaVisibile.setBorder(null);
		listaVisibile.setSelectionBackground(new Color(40,40,80));
		listaVisibile.setSelectionForeground(Color.white);
		listaVisibile.setForeground(Color.white);
		listaVisibile.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		scorrimentoPerListaVisibile.setBackground(new Color(20, 20, 40));
		scorrimentoPerListaVisibile.setBorder(null);
		scorrimentoPerListaVisibile.setOpaque(true);
		scorrimentoPerListaVisibile.getHorizontalScrollBar().setOpaque(true);
		scorrimentoPerListaVisibile.getHorizontalScrollBar().setBackground(new Color (20, 20, 40));
		scorrimentoPerListaVisibile.getHorizontalScrollBar().setBorder(null);
		scorrimentoPerListaVisibile.getVerticalScrollBar().setOpaque(true);
		scorrimentoPerListaVisibile.getVerticalScrollBar().setBackground(new Color (20, 20, 40));
		scorrimentoPerListaVisibile.getVerticalScrollBar().setBorder(null);
		
		setResizable(false);
		setVisible(true);
	}
	private class GestoreClickMouse implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == bottoneRimuoviSala)
			{
				Sala corrente = listaSale.get(elementoSelezionato);
				listaSale.remove(elementoSelezionato);
				theController.bottoneRimozioneSalaPremuto(corrente);
				modelloLista.removeAllElements();
				modelloLista.addAll(listaSale);
				bottoneVediTavoli.setEnabled(false);
				bottoneModificaSala.setEnabled(false);
				bottoneRimuoviSala.setEnabled(false);
			}
			else if (e.getSource() == bottoneAggiuntaSala)
			{
				theController.bottoneAggiuntaSalaPremuto(ristorante);
			}
			else if (e.getSource() == bottoneGestioneCamerieri)
			{
				theController.bottoneGestioneCamerieriPremuto(ristorante);
			}
			else if (e.getSource() == bottoneIndietro)
			{
				theController.bottoneTornaIndietroSalePremuto();
			}
			else if (e.getSource() == bottoneVediTavoli)
			{
				Sala corrente = listaSale.get(elementoSelezionato);
				theController.bottoneVediTavoliPremuto(corrente);
			}
			else if (e.getSource() == bottoneModificaSala)
			{
				Sala corrente = listaSale.get(elementoSelezionato);
				theController.bottoneModificaSalaPremuto(corrente);
			}
		}
	}

	private class GestoreSelezioneLista implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			elementoSelezionato = listaVisibile.getSelectedIndex();
			bottoneRimuoviSala.setEnabled(true);
			bottoneVediTavoli.setEnabled(true);
			bottoneModificaSala.setEnabled(true);
		}
	}
}
