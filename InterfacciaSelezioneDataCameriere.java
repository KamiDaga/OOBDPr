import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

public class InterfacciaSelezioneDataCameriere extends JDialog {

	private JButton bottoneSet;
	private JTextField textFieldData;
	private JCalendar calendar; 
	private JLabel istruzioni;
	private JLabel istruzioni2;
	private JLabel istruzioni3;
	private JLabel istruzioni4;
	private JButton bottoneGoNext;
	private JButton bottoneIndietro;
	private Controller theController;
	private boolean licenziamento;
	private Cameriere cameriereScelto;
	private String dataCorrente;
	private InterfacciaSelezioneDataCameriere riferimentoFinestra = this;
	
	public InterfacciaSelezioneDataCameriere(Controller controller, boolean licenziamento, Cameriere cameriere)
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 302, 310);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(20,20,40));
		this.theController = controller;
		this.licenziamento = licenziamento;
		cameriereScelto = cameriere;
		
		bottoneSet = new JButton("Set");
		bottoneSet.setBounds(194, 63, 66, 23);
		getContentPane().add(bottoneSet);
		bottoneSet.setBackground(new Color(0, 255, 127));
		bottoneSet.setBorder(null);
		bottoneSet.setOpaque(true);
		
		calendar = new JCalendar();
		calendar.setBounds(0, 30, 184, 153);
		getContentPane().add(calendar);
		calendar.getDayChooser().setDecorationBackgroundColor(new Color (20, 20, 40));
		calendar.getDayChooser().getDayPanel().setBackground(new Color (20, 20, 40));
		
		getContentPane().setBackground(new Color(20, 20, 40));
		
		textFieldData = new JTextField();
		textFieldData.setBounds(12, 203, 172, 23);
		textFieldData.setBackground(Color.white);
		textFieldData.setEditable(false);
		textFieldData.setOpaque(true);
		textFieldData.setColumns(10);
		
		getContentPane().add(textFieldData);
		
		istruzioni = new JLabel("Scegliere una data dal calendario.");
		istruzioni.setForeground(new Color(255, 255, 255));
		istruzioni.setBounds(10, 10, 235, 14);
		getContentPane().add(istruzioni);
		
		istruzioni2 = new JLabel("Poi, premere");
		istruzioni2.setForeground(new Color(255, 255, 255));
		istruzioni2.setBounds(194, 30, 82, 14);
		getContentPane().add(istruzioni2);
		
		istruzioni3 = new JLabel("\"Set\"");
		istruzioni3.setForeground(new Color(255, 255, 255));
		istruzioni3.setBounds(194, 42, 82, 14);
		getContentPane().add(istruzioni3);
		
		istruzioni4 = new JLabel("Dopo aver impostato la data, premere la freccia");
		istruzioni4.setForeground(new Color(255, 255, 255));
		istruzioni4.setBounds(10, 185, 284, 14);
		getContentPane().add(istruzioni4);
		
		bottoneGoNext = new JButton("->");
		bottoneGoNext.setBounds(194, 203, 56, 23);
		getContentPane().add(bottoneGoNext);
		bottoneGoNext.setBackground(new Color(0, 255, 127));
		bottoneGoNext.setBorder(null);
		bottoneGoNext.setOpaque(true);
		
		bottoneGoNext.addActionListener(new GestioneBottone());
		
		bottoneIndietro = new JButton("Indietro");
		bottoneIndietro.setBounds(14, 237, 89, 23);
		getContentPane().add(bottoneIndietro);
		bottoneIndietro.setBackground(new Color(0, 255, 127));
		bottoneIndietro.setBorder(null);
		bottoneIndietro.setOpaque(true);
		
		bottoneIndietro.addActionListener(new GestioneBottone());
		
		bottoneSet.addActionListener(new GestioneBottone());
		
		setResizable(false);
		setVisible(true);
	}

	private class GestioneBottone implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == bottoneSet)
			{
				textFieldData.setText(String.format("%s-%s-%s",calendar.getDate().getYear()+1900 <=9 ? String.format("000%d",calendar.getDate().getYear()+1900) : calendar.getDate().getYear()+1900 <=99? String.format("00%d", calendar.getDate().getYear()+1900) : calendar.getDate().getYear()+1900 <=999? String.format("0%d", calendar.getDate().getYear()+1900): String.format("%d", calendar.getDate().getYear()+1900) , calendar.getDate().getMonth()+1<=9? String.format("0%d", calendar.getDate().getMonth()+1) : String.format("%d",calendar.getDate().getMonth()+1),calendar.getDayChooser().getDay()<=9? String.format("0%d",calendar.getDayChooser().getDay()): String.format("%d",calendar.getDayChooser().getDay())));
			}
			else if(e.getSource()== bottoneGoNext)
			{
				if(textFieldData.getText().isBlank()) JOptionPane.showMessageDialog(null, "Scegliere prima una data dal calendario.");
				else
				{
					
					dataCorrente = textFieldData.getText();
					
					if (licenziamento) {
						if (!theController.presentiOccupazioniDiCameriereDopoData(cameriereScelto, dataCorrente)) 
						{

							String esito = theController.bottoneLicenziaCamerierePremuto(cameriereScelto ,dataCorrente);
							if(esito.equals("Tutto_Bene"))
							{
								theController.confermaSceltaDataCameriere(riferimentoFinestra);
							}
							else if(esito.equals("Operazione_Fallita"))
							{
								//E' gestito gia' dal DAO
							}
							else if (esito.equals("Data_Licenziamento_Precedente"))
							{
								JOptionPane.showMessageDialog(null, "Non si puo' licenziare un cameriere prima della sua data di assunzione!("+cameriereScelto.getData_Ammissione()+")", "Informazione", JOptionPane.INFORMATION_MESSAGE);
							}

						}
					}
					else 
					{
						
						boolean nessunProblema;
						nessunProblema = theController.bottoneRiassumiCamerierePremuto(cameriereScelto, dataCorrente);
						if (nessunProblema) {
							theController.confermaSceltaDataCameriere(riferimentoFinestra);
						}
					}	
				}
			}
			else if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroSceltaDataCameriere(riferimentoFinestra);
			}
		}
	}

}
