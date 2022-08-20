import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public class InterfacciaStatistichePerMese extends JFrame {

	private Integer annoCorrente;
	private Mese meseCorrente = new Mese();
	private Controller theController;
	private JTextField textFieldMeseCorrente;
	private JTextField textFieldAnnoCorrente;
	private JPanel panel;
	private JButton bottoneAumentaMese;
	private JButton bottoneDiminuisciMese;
	private JButton bottonePassaggioAdAnno;
	private JButton bottoneDiminuisciAnno;
	private JButton bottoneAumentaAnno;
	private JFreeChart grafico;
	private ChartPanel pannelloGrafico;

	public InterfacciaStatistichePerMese(Controller c, Integer annoScelto, Integer meseScelto, Ristorante ristoranteScelto) {
		super ("Statistiche per mese del ristorante " + ristoranteScelto.getNome());
		
		theController = c;
		annoCorrente = annoScelto;
		meseCorrente.setValoreNumerico(meseScelto);
		
		
		ImageIcon icona = new ImageIcon("src/IconaProgetto.jpeg");
		setIconImage(icona.getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 500);
		
		modellaStatistiche (ristoranteScelto);

		panel =  new JPanel();
		panel.setBounds(0, 0, 824, 461);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel.setBackground(new Color(20, 20, 40));
		
		bottoneAumentaMese = new JButton(">");
		bottoneAumentaMese.setBounds(556, 407, 18, 20);
		panel.add(bottoneAumentaMese);
		bottoneAumentaMese.setBackground(new Color(0, 255, 127));
		bottoneAumentaMese.setBorder(null);
		bottoneAumentaMese.setOpaque(true);
		
		bottoneDiminuisciMese = new JButton("<");
		bottoneDiminuisciMese.setBounds(269, 407, 18, 20);
		panel.add(bottoneDiminuisciMese);
		bottoneDiminuisciMese.setBackground(new Color(0, 255, 127));
		bottoneDiminuisciMese.setBorder(null);
		bottoneDiminuisciMese.setOpaque(true);
		
		bottonePassaggioAdAnno = new JButton("Torna alla visualizzazione delle statistiche per anno");
		bottonePassaggioAdAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				theController.passaggioAdAnnoPremuto(annoCorrente, ristoranteScelto);
			}
		});
		bottonePassaggioAdAnno.setBounds(441, 11, 373, 32);
		panel.add(bottonePassaggioAdAnno);
		bottonePassaggioAdAnno.setBackground(new Color(0, 255, 127));
		bottonePassaggioAdAnno.setBorder(null);
		bottonePassaggioAdAnno.setOpaque(true);
		
		textFieldMeseCorrente = new JTextField();
		textFieldMeseCorrente.setBounds(286, 407, 271, 20);
		panel.add(textFieldMeseCorrente);
		textFieldMeseCorrente.setText(meseCorrente.toString());
		textFieldMeseCorrente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(textFieldMeseCorrente.getText().isBlank()) {
								JOptionPane.showMessageDialog(null, "Inserire un mese valido, in forma litterale o numerica!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldMeseCorrente.setText(meseCorrente.toString());
					}
					else {
						try
						{
							Integer possibileMese = Integer.parseInt(textFieldMeseCorrente.getText());
							if(possibileMese>=1 && possibileMese<=12) {
								meseCorrente.setValoreNumerico(possibileMese);
								textFieldMeseCorrente.setText(meseCorrente.toString());
								modellaStatistiche(ristoranteScelto);
							}
							else {
								JOptionPane.showMessageDialog(null, "Inserire un mese valido, in forma litterale o numerica!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldMeseCorrente.setText(meseCorrente.toString());
							}
						}
						catch (NumberFormatException ecc)
						{
							try {
								meseCorrente.setDaStringa(textFieldMeseCorrente.getText());
								textFieldMeseCorrente.setText(meseCorrente.toString());
								modellaStatistiche(ristoranteScelto);
							} catch (MeseErratoException e1) {
								e1.stampaMessaggio();
								textFieldMeseCorrente.setText(meseCorrente.toString());
							}
						}
					}
				}
			}
		});
		
		
		bottoneDiminuisciAnno = new JButton("<");
		bottoneDiminuisciAnno.setBounds(269, 430, 18, 20);
		panel.add(bottoneDiminuisciAnno);
		bottoneDiminuisciAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer possibileAnno = annoCorrente-1;
				if (possibileAnno>=0) {
					annoCorrente = possibileAnno;
					modellaStatistiche(ristoranteScelto);
				}
				else {
					JOptionPane.showMessageDialog(null, "Non penso ci sia interesse per statistiche precedenti all'anno 0!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				}
				textFieldAnnoCorrente.setText(annoCorrente.toString());
			}
		});
		bottoneDiminuisciAnno.setBackground(new Color(0, 255, 127));
		bottoneDiminuisciAnno.setBorder(null);
		bottoneDiminuisciAnno.setOpaque(true);
		
		textFieldAnnoCorrente = new JTextField();
		textFieldAnnoCorrente.setText((String) null);
		textFieldAnnoCorrente.setBounds(286, 430, 271, 20);
		textFieldAnnoCorrente.setText(annoCorrente.toString());
		panel.add(textFieldAnnoCorrente);
		textFieldAnnoCorrente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(textFieldAnnoCorrente.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "Inserire un anno valido!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
						textFieldAnnoCorrente.setText(annoCorrente.toString());
					}
					else {
						try
						{
							Integer possibileAnno = Integer.parseInt(textFieldAnnoCorrente.getText());
							if (possibileAnno>=0) {
								annoCorrente = possibileAnno;
								modellaStatistiche(ristoranteScelto);
							}
							else {
								JOptionPane.showMessageDialog(null, "Non penso ci sia interesse per statistiche precedenti all'anno 0!",
										"Attenzione!", JOptionPane.WARNING_MESSAGE);
								textFieldAnnoCorrente.setText(annoCorrente.toString());
							}
						}
						catch (NumberFormatException ecc)
						{
							JOptionPane.showMessageDialog(null, "L'anno deve essere un numero valido!",
									"Attenzione!", JOptionPane.WARNING_MESSAGE);
							textFieldAnnoCorrente.setText(annoCorrente.toString());
						}
					}
				}
			}
		});
		
		bottoneAumentaAnno = new JButton(">");
		bottoneAumentaAnno.setBounds(556, 430, 18, 20);
		panel.add(bottoneAumentaAnno);
		bottoneAumentaAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer possibileAnno = annoCorrente+1;
				if (possibileAnno>=0) {
					annoCorrente = possibileAnno;
					modellaStatistiche(ristoranteScelto);
				}
				else {
					JOptionPane.showMessageDialog(null, "Hai raggiunto il numero intero massimo!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				}
				textFieldAnnoCorrente.setText(annoCorrente.toString());
			}
		});
		bottoneAumentaAnno.setBackground(new Color(0, 255, 127));
		bottoneAumentaAnno.setBorder(null);
		bottoneAumentaAnno.setOpaque(true);
		
		bottoneAumentaMese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				meseCorrente.setValoreNumerico(meseCorrente.getValoreNumerico()+1);
				if (meseCorrente.getValoreNumerico().equals(13)) {
					Integer possibileAnno = annoCorrente+1;
					if (possibileAnno>=0) {
						meseCorrente.setValoreNumerico(1);
						annoCorrente = possibileAnno;
						modellaStatistiche(ristoranteScelto);
					}
					else {
						meseCorrente.setValoreNumerico(12);
						JOptionPane.showMessageDialog(null, "Hai raggiunto il numero intero massimo!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
					}
				}
				textFieldMeseCorrente.setText(meseCorrente.toString());
				textFieldAnnoCorrente.setText(annoCorrente.toString());
				modellaStatistiche(ristoranteScelto);
			}
		});
		
		bottoneDiminuisciMese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				meseCorrente.setValoreNumerico(meseCorrente.getValoreNumerico()-1);
				if (meseCorrente.getValoreNumerico().equals(0)) {
					Integer possibileAnno = annoCorrente-1;
					if (possibileAnno>=0) {
						annoCorrente = possibileAnno;
						meseCorrente.setValoreNumerico(12);
						modellaStatistiche(ristoranteScelto);
					}
					else {
						JOptionPane.showMessageDialog(null, "Non penso ci sia interesse per statistiche precedenti all'anno 0!",
								"Attenzione!", JOptionPane.WARNING_MESSAGE);
						meseCorrente.setValoreNumerico(1);
					}
				}
				textFieldMeseCorrente.setText(meseCorrente.toString());
				textFieldAnnoCorrente.setText(annoCorrente.toString());
				modellaStatistiche(ristoranteScelto);
			}
		});
		
		
		
		setResizable(false);
		setVisible(true);
	}

	public void modellaStatistiche(Ristorante ristoranteScelto) {
		
		DefaultCategoryDataset statisticheCorrenti = new DefaultCategoryDataset();
		statisticheCorrenti = theController.ricavaStatistiche(annoCorrente, meseCorrente.getValoreNumerico(), ristoranteScelto);
		grafico = creaGrafico(statisticheCorrenti);
		pannelloGrafico = new ChartPanel(grafico);
		pannelloGrafico.setVisible(true);
		pannelloGrafico.setBounds(31, 50, 756, 357);
	    pannelloGrafico.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	    getContentPane().add(pannelloGrafico);
	    pannelloGrafico.setBackground(new Color(20, 20, 40));
	    pannelloGrafico.setOpaque(true);
	    pannelloGrafico.setLayout(null);
	    pannelloGrafico.repaint();
	}
	
	public JFreeChart creaGrafico(DefaultCategoryDataset datiStatistiche) {
		 JFreeChart barChart = ChartFactory.createBarChart(
	                "",
	                "",
	                "Numero di avventori",
	                datiStatistiche,
	                PlotOrientation.VERTICAL,
	                false, true, false);
		 
		 barChart.getPlot().setBackgroundPaint(new Color (20, 20, 40));
		 StandardBarPainter colore = new StandardBarPainter();
		 ((BarRenderer) barChart.getCategoryPlot().getRenderer()).setBarPainter(colore);
		 BarRenderer rendererGrafico = (BarRenderer) barChart.getCategoryPlot().getRenderer();
		 rendererGrafico.setSeriesPaint(0, new Color(0, 255, 127));
		 
	        return barChart;
	}
}
