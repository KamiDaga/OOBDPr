import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

public class InterfacciaSelezioneDataAggiuntaCameriere extends JDialog {

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
	private String dataCorrente;
	private InterfacciaSelezioneDataAggiuntaCameriere riferimentoFinestra = this;
	
	public InterfacciaSelezioneDataAggiuntaCameriere(Controller controller)
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 302, 307);
		getContentPane().setLayout(null);
		this.theController = controller;
		
		bottoneSet = new JButton("Set");
		bottoneSet.setBounds(194, 63, 66, 23);
		getContentPane().add(bottoneSet);
		bottoneSet.setBackground(new Color(0, 255, 127));
		bottoneSet.setBorder(null);
		bottoneSet.setOpaque(true);
		
		calendar = new JCalendar();
		calendar.setBounds(0, 30, 184, 153);
		getContentPane().add(calendar);		
		getContentPane().setBackground(new Color(20, 20, 40));
		calendar.getDayChooser().setDecorationBackgroundColor(new Color (20, 20, 40));
		calendar.getDayChooser().getDayPanel().setBackground(new Color (20, 20, 40));
		
		textFieldData = new JTextField();
		textFieldData.setBounds(10, 203, 172, 23);
		textFieldData.setBackground(Color.white);
		textFieldData.setEditable(false);
		textFieldData.setOpaque(true);
		textFieldData.setColumns(10);
		
		getContentPane().add(textFieldData);
		
		istruzioni = new JLabel("Scegliere una data dal calendario.");
		istruzioni.setForeground(new Color(255, 255, 255));
		istruzioni.setBounds(10, 11, 235, 14);
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
					theController.bottoneConfermaSelezioneDataAggiuntaCameriere(dataCorrente, riferimentoFinestra);					
				}
			}
			else if(e.getSource() == bottoneIndietro)
			{
				theController.bottoneIndietroSceltaDataAggiuntCameriere(riferimentoFinestra);
			}
		}
	}

}
