import java.sql.*;
import java.util.regex.*;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Controller {
	private InterfacciaRistoranti frameRistoranti;
    private InterfacciaAggiuntaRistorante frameAggiuntaRistorante;
    private InterfacciaModificaDatiRistorante frameModificaRistorante;
    private InterfacciaSale frameSale;
	private InterfacciaAggiuntaSala frameCreateS;
	private InterfacciaGestioneCamerieri frameGestioneCamerieri;
	private InterfacciaStatistichePerAnno frameStatistichePerAnno; 
	private InterfacciaAggiuntaCamerieri frameAggiuntaCamerieri;
	private InterfacciaStatistichePerMese frameStatistichePerMese;
	private InterfacciaTavoli frameTavoli;
	private InterfacciaModificaLayout frameModificaLayout;
	private InterfacciaAggiuntaTavoli frameAggiuntaTavoli;
	private InterfacciaAggiuntaDatiNuovoTavolo frameAggiuntaDatiNuovoTavolo;
	private InterfacciaSelezioneDataOccupazione frameSelezioneDataOccupazione;
	private InterfacciaGestioneOccupazioni frameGestioneOccupazioni;
	private InterfacciaAdiacenze frameAdiacenze;
	private InterfacciaModificaDatiTavolo frameModificaDatiTavolo;
	private InterfacciaVisualizzazioneOccupazione frameVisualizzaOccupazione;
	private ArrayList<InterfacciaAggiuntaDatiAvventore> framesAggiuntaAvventore;
	private InterfacciaSelezioneCamerieri frameSelezioneCamerieri;
	private InterfacciaSelezioneDataCameriere frameSelezioneDataCameriere;
	private InterfacciaModificaSala frameModificaSala;
	private InterfacciaSelezioneDataAggiuntaCameriere frameSelezioneDataAggiuntaCameriere; 

	private RistoranteDAOImplPostgres ristoranteDao = new RistoranteDAOImplPostgres();
	private SalaDAOImplPostgres salaDao = new SalaDAOImplPostgres();
	private TavoloDAOImplPostgres tavoloDao = new TavoloDAOImplPostgres();
	private CameriereDAOImplPostgres cameriereDao = new CameriereDAOImplPostgres();
	private NumeroAvventoriGiornoDAOImplPostgres numeroAvventoriGiornoDao = new NumeroAvventoriGiornoDAOImplPostgres();
	private NumeroAvventoriMeseDAOImplPostgres numeroAvventoriMeseDao = new NumeroAvventoriMeseDAOImplPostgres();
	private AvventoriDAOImplPostgres avventoriDao = new AvventoriDAOImplPostgres();
	private TavolataDAOImplPostgres tavolataDao = new TavolataDAOImplPostgres();
	
	
	public static void main(String[] args) {

		try 
		{
			DB_Builder costruttore = new DB_Builder();
			Controller controllore = new Controller();
		}
		catch(ErroreIniziale e)
		{
			e.stampaMessaggio();
		}
	}
	
	public Controller() {
		frameRistoranti = new InterfacciaRistoranti(this);
	}
  
  	public void bottoneAggiungiRistorantePremuto() {
		frameRistoranti.setVisible(false);
		frameAggiuntaRistorante = new InterfacciaAggiuntaRistorante(this);
		frameAggiuntaRistorante.setVisible(true);
	}
	
	public void aggiuntaRistoranteOkPremuto(String nome, String via, Integer n_Civico, String citta) {
		try
		{
			ristoranteDao.inserisciRistorante(nome, via, n_Civico, citta);
			frameAggiuntaRistorante.setVisible(false);
			frameRistoranti = new InterfacciaRistoranti(this);
		}
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
		}
	}
	
	public void bottoneModificaRistorantePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameModificaRistorante = new InterfacciaModificaDatiRistorante(this, ristoranteCorrente);
	}
	
	public void modificaRistoranteOkPremuto(Ristorante ristorante, String nome, String via, Integer n_Civico, String citta) {
		try
		{
			ristoranteDao.modificaRistorante(ristorante, nome, via, n_Civico, citta);
			frameModificaRistorante.setVisible(false);
			frameRistoranti = new InterfacciaRistoranti(this);
		}
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
		}
	}
	
  
	public void bottoneRimozioneSalaPremuto(Sala c)
	{
			salaDao.RimuoviSalaRistorante(c);
	}
	
	public void bottoneAggiuntaSalaPremuto(Ristorante ristorante)
	{		
		frameCreateS = new InterfacciaAggiuntaSala(ristorante,this);
		frameSale.setVisible(false);
	}
	
	public void interfacciaCreazioneSalaOkPremuto(String nomeSala, Ristorante ristorante)
	{
		
		try {

			salaDao.AggiuntaSalaRistorante(nomeSala, ristorante.getId_Ristorante());
			frameCreateS.setVisible(false);
			frameSale = new InterfacciaSale(this,ristorante);
		} catch (ErrorePersonalizzato e) {
			e.stampaMessaggio();
		}
		
	}
	
	public void bottoneGestioneCamerieriPremuto(Ristorante ristorante)
	{
		frameSale.setVisible(false);
		frameGestioneCamerieri = new InterfacciaGestioneCamerieri(ristorante, this);
	}
	
	public void bottoneOkGestioneCamerieriPremuto(Ristorante ristorante)
	{
		frameGestioneCamerieri.setVisible(false);
		frameSale = new InterfacciaSale(this,ristorante);
	}
	
	public void bottoneTornaIndietroSalePremuto()
	{
		frameSale.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti(this);
	}
	
	public void bottoneTornaIndietroGestioneCamerieriPremuto(Ristorante ristorante)
	{
		frameGestioneCamerieri.setVisible(false);
		frameSale = new InterfacciaSale(this,ristorante);
	}
	
	public void bottoneTornaIndietroInterfacciaCreazioneSalaPremuto(Ristorante ristorante)
	{
		frameCreateS.setVisible(false);
		frameSale = new InterfacciaSale(this,ristorante);
	}
	
	public ArrayList<Sala> estraiSaleRistorante(Ristorante ristorante)
	{
		return salaDao.estraiSaleRistorante(ristorante);
	}
	
	public ArrayList<Cameriere> estraiCamerieriInServizioC(Ristorante ristorante)
	{
		return cameriereDao.estraiCamerieriInServizio(ristorante);
	}
	
	public ArrayList<Cameriere> estraiCamerieriLicenziatiC(Ristorante ristorante)
	{
		return cameriereDao.estraiCamerieriLicenziati(ristorante);
	}
	
	public boolean bottoneRiassumiCamerierePremuto(Cameriere c,String data)
	{
		 return cameriereDao.riassumiCameriereLicenziato(c,data);
	}
	
	public String bottoneLicenziaCamerierePremuto(Cameriere c, String data)
	{
		return cameriereDao.licenziaCameriereAssunto(c, data);
	}
	
	public void bottoneAggiungiCamerierePremuto(Ristorante r)
	{
		frameGestioneCamerieri.setVisible(false);
		frameAggiuntaCamerieri = new InterfacciaAggiuntaCamerieri(r, this); 
	}
	
	public void bottoneTornaIndietroAggiuntaCamerieriPremuto(Ristorante ristorante)
	{
		frameAggiuntaCamerieri.setVisible(false);
		frameGestioneCamerieri = new InterfacciaGestioneCamerieri(ristorante, this);
	}
	
	public String bottoneOkAggiuntaCamerieriPremutoSuccessful(Cameriere cameriere)
	{
		return cameriereDao.assumiNuovoCameriere(cameriere);
	}
	
	public void bottoneEliminaRistorantePremuto(Ristorante ristoranteCorrente) {
		try
		{
			ristoranteDao.eliminaRistorante(ristoranteCorrente);
		}
		catch (ErrorePersonalizzato e)
		{
			e.stampaMessaggio();
		}		
	}

	public void bottoneVisualizzaSalePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameSale = new InterfacciaSale(this, ristoranteCorrente);
	}

	public void bottoneVisualizzaStatistichePremuto(Ristorante ristoranteCorrente) {
		frameRistoranti.setVisible(false);
		frameStatistichePerAnno = new InterfacciaStatistichePerAnno(this, ristoranteCorrente, java.time.LocalDateTime.now().getYear());
	}

	public ArrayList<Ristorante> inizializzazioneRistoranti() {
		
		ArrayList<Ristorante> listaRistoranti = new ArrayList<Ristorante>();
		
		try
		{
			listaRistoranti = ristoranteDao.estraiTuttiRistoranti();
		}
		catch (OperazioneFallitaException ecc)
		{
			ecc.stampaMessaggio();
		}
		
		return listaRistoranti;
	}

	public DefaultCategoryDataset ricavaStatistiche(Integer anno, Ristorante ristorante) {
		ArrayList<NumeroAvventoriMese> listaCorrenteStatistiche = new ArrayList<NumeroAvventoriMese>();
		
		try
		{
			listaCorrenteStatistiche = numeroAvventoriMeseDao.estraiStatisticheAnno(anno, ristorante);
		}
		catch (OperazioneFallitaException e)
		{
			e.stampaMessaggio();
		}
		
		DefaultCategoryDataset risultato = new DefaultCategoryDataset();
		
		for (NumeroAvventoriMese numeroCorrente : listaCorrenteStatistiche) {
			risultato.setValue(numeroCorrente.getNumAvventori(), "Numero di avventori", numeroCorrente.getMese().toString());
		}
		
		return risultato;
	}
	
	public DefaultCategoryDataset ricavaStatistiche(Integer anno, Integer mese, Ristorante ristorante) {
		ArrayList<NumeroAvventoriGiorno> listaCorrenteStatistiche = new ArrayList<NumeroAvventoriGiorno>();
		DefaultCategoryDataset risultato = new DefaultCategoryDataset();
		
		try
		{
			listaCorrenteStatistiche = numeroAvventoriGiornoDao.estraiStatisticheMese(anno, mese, ristorante);
			for (NumeroAvventoriGiorno numeroCorrente : listaCorrenteStatistiche) {
				risultato.setValue(numeroCorrente.getNumAvventori(), "Numero di avventori", numeroCorrente.getGiorno());
			}
			
		}
		catch (OperazioneFallitaException e)
		{
			e.stampaMessaggio();
		}
		
		return risultato;
	}

	public void passaggioAMesePremuto(Integer annoScelto, Ristorante ristoranteScelto) {
		frameStatistichePerAnno.setVisible(false);
		frameStatistichePerMese = new InterfacciaStatistichePerMese(this, annoScelto, 1, ristoranteScelto);
	}

	public void passaggioAdAnnoPremuto(Integer annoCorrente, Ristorante ristoranteScelto) {
		frameStatistichePerMese.setVisible(false);
		frameStatistichePerAnno = new InterfacciaStatistichePerAnno(this, ristoranteScelto, annoCorrente);
		
	}

	public void bottoneIndietroStatistichePremuto(Ristorante ristoranteCorrente) {
		frameStatistichePerAnno.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti(this);
	}

	public void bottoneIndietroAggiungiRistorantePremuto() {
		frameAggiuntaRistorante.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti(this);
	}

	public void bottoneIndietroModificaRistorantePremuto() {
		frameModificaRistorante.setVisible(false);
		frameRistoranti = new InterfacciaRistoranti (this);
  }
	public void bottoneIndietroGestioneTavoliPremuto(Sala salaScelta) {
		frameTavoli.setVisible(false);
		frameSale = new InterfacciaSale(this, salaScelta.getRistoranteDiAppartenenza());
	}

	public void bottoneVediTavoliPremuto(Sala salaScelta) {
		frameSale.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaScelta);
	}
	
	public ArrayList<Tavolo> estrazioneTavoliSala(Sala sala)
	{
		return tavoloDao.estraiTavoliSala(sala);
	}
  
	public void bottoneModificaLayoutPremuto(Sala sala) {
		frameTavoli.setVisible(false);
		frameModificaLayout = new InterfacciaModificaLayout(this, sala);
	}

	public void modificaLayoutIndietroPremuto(Sala sala) {
		frameModificaLayout.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, sala);
	}

	public void confermaModificheLayoutPremuto(ArrayList<Tavolo> tavoli, Sala sala) {
		
		try 
		{
			tavoloDao.modificaPosizioniTavoli (tavoli);
			frameModificaLayout.setVisible(false);
			frameTavoli = new InterfacciaTavoli(this, sala);
			
		} catch (OperazioneFallitaException e) 
		{
			e.stampaMessaggio();
		}
	
	}
	
	public void bottoneAggiuntaTavoloPremuto(Sala salaScelta, ArrayList<Tavolo> tavoliGiaEsistenti)
	{
		frameTavoli.setVisible(false);
		frameAggiuntaDatiNuovoTavolo = new InterfacciaAggiuntaDatiNuovoTavolo(this,salaScelta,tavoliGiaEsistenti);
	}
	
	public void bottoneOkInterfacciaAggiuntaCapienzaNumeroNuovoTavoloPremuto(Tavolo tavolo,Sala salaScelta, ArrayList<Tavolo> tavoliGiaEsistenti)
	{
		frameAggiuntaDatiNuovoTavolo.setVisible(false);
		frameAggiuntaTavoli = new InterfacciaAggiuntaTavoli(tavolo,salaScelta,tavoliGiaEsistenti,this);
	}
	
	public void bottoneIndietroInterfacciaAggiuntaCapienzaNumeroNuovoTavoloPremuto(Sala salaScelta)
	{
		frameAggiuntaDatiNuovoTavolo.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,salaScelta);
	}
	
	public void bottoneOkInterfacciaAggiuntaTavoliPremuto(Tavolo nuovoTavolo)
	{
		try
		{
			tavoloDao.inserisciNuovoTavolo(nuovoTavolo);
		}
		catch(TavoloNumeroUgualeException e)
		{
			e.stampaMessaggio();
		} 
			catch (OperazioneFallitaException e) 
		{
			e.stampaMessaggio();
		}
		frameAggiuntaTavoli.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,nuovoTavolo.getSala_App());
	}
	
	public void bottoneIndietroInterfacciaAggiuntaTavoliPremuto(Sala salaScelta)
	{
		frameAggiuntaTavoli.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,salaScelta);
	}
	
	public void bottoneGestioneOccupazioneInterfacciaTavoliPremuto(ArrayList<Tavolo> tavoli)
	{
		frameTavoli.setVisible(false);
		frameSelezioneDataOccupazione = new InterfacciaSelezioneDataOccupazione(this, tavoli);
	}
	
	
	public void bottoneIndietroInterfacciaSelezioneDataGestioneOccupazionePremuto(Sala salaScelta)
	{
		frameSelezioneDataOccupazione.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaScelta);
	}
	
	public void bottoneGoNextInterfacciaSelezioneDataGestioneOccupazionePremuto(ArrayList<Tavolo> tavoli, String data)
	{
		frameSelezioneDataOccupazione.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}

	public ArrayList<Integer> estrazioneTavoliOccupatiInData(Sala sala, String data)
	{
		return tavoloDao.tavoliOccupatiInData(data, sala);
	}
	
	public ArrayList<Avventori> estrazioneAvventoriDelTavoloInData(Tavolo tavolo, String data)
	{
		AvventoriDAOImplPostgres ADAO = new AvventoriDAOImplPostgres();
		return ADAO.estraiAvventoriDelTavoloInData(tavolo, data);
	}
	
	public void bottoneVisualizzaOccupazioneGestioneOccupazione(ArrayList<Tavolo> tavoli, String data, int tavoloScelto)
	{
		frameGestioneOccupazioni.setVisible(false);
		frameVisualizzaOccupazione = new InterfacciaVisualizzazioneOccupazione(this,tavoli,tavoloScelto,data);
	}
	
	public void bottoneIndietroVisualizzazioneOccupazione(ArrayList<Tavolo> tavoli, String data)
	{
		frameVisualizzaOccupazione.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}
	
	public void bottoneRimuoviCameriereVisualizzazioneOccupazione(String data,int idTavolo, Cameriere cameriereScelto)
	{
		cameriereDao.rimuoviCameriereDalTavoloInData(cameriereScelto, data, idTavolo);
	}
	
	public ArrayList<Cameriere> estrazioneCamerieriInServizioAlTavoloinData(int idTavolo, String data)
	{
		return cameriereDao.camerieriInServizioAlTavoloInData(idTavolo, data);
	}
	
	public void bottoneOccupaGestioneOccupazionePremuto(int numeroAvv, ArrayList<Tavolo> tavoli, int tavoloScelto, String data)
	{
		frameGestioneOccupazioni.setVisible(false);
		framesAggiuntaAvventore = new ArrayList<InterfacciaAggiuntaDatiAvventore>();
		for (int i = 0; i< numeroAvv; i++)
		{
			framesAggiuntaAvventore.add(new InterfacciaAggiuntaDatiAvventore(this,i,tavoli,tavoloScelto,data));
		}
		for (int i = 0; i<numeroAvv; i++)
		{
			framesAggiuntaAvventore.get(i).impostaBottoniCorretti(framesAggiuntaAvventore.size());
			
		}
		framesAggiuntaAvventore.get(0).setVisible(true);
	}
	
	public void bottoneConfermaAggiuntaAvventoriPremuto(ArrayList<Tavolo> tavoli,int tavoloScelto, String data) throws CampiNonCorrettiException
	{
		int i=0;
		boolean controlloCampiNonVuoti = true;
		boolean controlloAlmenoUnTelefono = false;
		boolean formatoNTelGiusto = true;
		boolean noApostrofi = true;
		boolean formatoCidGiusto = true;
		boolean doppieCID= false;
		while(i<framesAggiuntaAvventore.size() && controlloCampiNonVuoti && formatoCidGiusto && formatoNTelGiusto && noApostrofi)
		{
			if(framesAggiuntaAvventore.get(i).getNome().getText().contains(new StringBuffer("'")) || framesAggiuntaAvventore.get(i).getCognome().getText().contains(new StringBuffer("'"))) noApostrofi = false;
			if(framesAggiuntaAvventore.get(i).getCid().getText().isBlank() || framesAggiuntaAvventore.get(i).getCognome().getText().isBlank() || framesAggiuntaAvventore.get(i).getNome().getText().isBlank()) controlloCampiNonVuoti = false;
			if(!framesAggiuntaAvventore.get(i).getNtel().getText().isBlank()) controlloAlmenoUnTelefono = true;
			if(!Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", framesAggiuntaAvventore.get(i).getNtel().getText()) && !framesAggiuntaAvventore.get(i).getNtel().getText().isEmpty()) formatoNTelGiusto = false;
			if(!Pattern.matches("C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]",framesAggiuntaAvventore.get(i).getCid().getText())) formatoCidGiusto =false;
			i++;
		}
		i = 0;
		while(i<framesAggiuntaAvventore.size() && !doppieCID)
		{
			for (int j = i+1 ; j< framesAggiuntaAvventore.size(); j++)
			{
				if (framesAggiuntaAvventore.get(i).getCid().getText().equals(framesAggiuntaAvventore.get(j).getCid().getText())) doppieCID = true;
			}
			i++;
		}
		
		if(controlloCampiNonVuoti && controlloAlmenoUnTelefono && formatoCidGiusto && !doppieCID && formatoNTelGiusto && noApostrofi)
		{
			framesAggiuntaAvventore.get(framesAggiuntaAvventore.size()-1).setVisible(false);
			frameSelezioneCamerieri = new InterfacciaSelezioneCamerieri(this,tavoli,tavoloScelto,data);
		}
		else
		{
		    throw new CampiNonCorrettiException();
		}
		
	}
	
	public ArrayList<Cameriere> estraiCamerieriAssegnabili(String data, Ristorante ristorante)
	{
		return cameriereDao.camerieriAssegnabiliAlTavoloInData(data, ristorante);
	}
	
	public void bottoneIndietroSelezioneCamerieriPremuto(ArrayList<Tavolo> tavoli, String data)
	{
		frameSelezioneCamerieri.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}
	
	public void bottoneIndietroGestioneOccupazioniPremuto(ArrayList<Tavolo> tavoli)
	{
		frameGestioneOccupazioni.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, tavoli.get(0).getSala_App());
	}
	
	public void bottoneAvventoreSuccessivoPremuto(int indice)
	{
		framesAggiuntaAvventore.get(indice).setVisible(false);
		framesAggiuntaAvventore.get(indice + 1).setVisible(true);
	}
	
	public void bottoneAvventorePrecedentePremuto(int indice)
	{
		framesAggiuntaAvventore.get(indice).setVisible(false);
		framesAggiuntaAvventore.get(indice-1).setVisible(true);
	}
	
	public void bottoneConfermaSelezioneCamerieriPremuto(ArrayList<Cameriere> camerieriScelti, String data, ArrayList<Tavolo> tavoli, int tavoloScelto)
	{
		tavolataDao .inserimentoTavolata(new Tavolata(tavoli.get(tavoloScelto),data));
		avventoriDao.inserimentoMultiploAvventori(framesAggiuntaAvventore,0);		
		
		cameriereDao.inserimentoMultiploCamerieriInServizio(camerieriScelti, data, tavoli.get(tavoloScelto));
		
		frameSelezioneCamerieri.setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this,tavoli,data);
	}
	
	public void bottoneGestisciAdiacenze(Tavolo tavoloScelto) 
	{
		frameTavoli.setVisible(false);
		frameAdiacenze = new InterfacciaAdiacenze(this, tavoloScelto);
	}

	public void adiacenzeIndietroPremuto(Sala sala) {
		frameAdiacenze.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this,sala);
		
	}

	public ArrayList<Tavolo> estraiTavoliAdiacenti(Tavolo tavoloScelto) {
		
		try {
			return tavoloDao.ricavaTavoliAdiacenti(tavoloScelto);
		}
		catch (OperazioneFallitaException e) {
			e.stampaMessaggio();
		}
		
		return null;
		
	}

	public void bottoneConfermaModificheAdiacenzePremuto(Tavolo tavoloProtagonista) {
		try {
			tavoloDao.rimpiazzaAdiacenze(tavoloProtagonista);
			frameAdiacenze.setVisible(false);
			frameTavoli = new InterfacciaTavoli(this,tavoloProtagonista.getSala_App());
		} catch (OperazioneFallitaException e) {
			e.stampaMessaggio();
		}
	}

	public void bottoneModificaDatiPremuto(Tavolo tavolo) {
		frameTavoli.setVisible(false);
		frameModificaDatiTavolo = new InterfacciaModificaDatiTavolo(this, tavolo);
	}

	public void bottoneEliminaTavoloPremuto(Tavolo tavolo) {
		try {
			tavoloDao.eliminaTavolo(tavolo);
		} catch (OperazioneFallitaException e) {
			e.stampaMessaggio();
		}
	}

	public void bottoneConfermaModificheDatiTavoloPremuto(Tavolo tavoloScelto, int numeroCorrente) {
		try {
			
			tavoloDao.modificaDatiTavolo(tavoloScelto, numeroCorrente);
			frameModificaDatiTavolo.setVisible(false);
			frameTavoli = new InterfacciaTavoli(this, tavoloScelto.getSala_App());
			
		} catch (ErrorePersonalizzato e) {
			e.stampaMessaggio();
		}
		
	}

	public void bottoneIndietroModificaDatiTavoloPremuto(Sala salaCorrente) 
	{
		frameModificaDatiTavolo.setVisible(false);
		frameTavoli = new InterfacciaTavoli(this, salaCorrente);
	}

	public ArrayList<InterfacciaAggiuntaDatiAvventore> getFramesAggiuntaAvventore()
	{
		return framesAggiuntaAvventore;
	}
	
	public void generaCalendarioLicenziamentoCameriere(Cameriere cameriere) {
		frameSelezioneDataCameriere = new InterfacciaSelezioneDataCameriere(this, true, cameriere);
	}

	public void generaCalendarioAssunzioneCameriere(Cameriere cameriere) {
		frameSelezioneDataCameriere = new InterfacciaSelezioneDataCameriere(this, false, cameriere);
	}

	public void bottoneIndietroSceltaDataCameriere(InterfacciaSelezioneDataCameriere finestra) {
		frameSelezioneDataCameriere = finestra;
		frameSelezioneDataCameriere.setVisible(false);
		frameGestioneCamerieri.setVisible(true);
	}
	public void confermaSceltaDataCameriere(InterfacciaSelezioneDataCameriere finestra) {
		frameSelezioneDataCameriere = finestra;
		frameSelezioneDataCameriere.setVisible(false);
		frameGestioneCamerieri.setVisible(true);
		frameGestioneCamerieri.ripresaInterfaccia();
	}
	
	public void bottoneModificaSalaPremuto(Sala corrente) {
		frameModificaSala = new InterfacciaModificaSala(this, corrente);
		frameSale.setVisible(false);		
	}

	public void interfacciaModificaSalaOkPremuto(String nome, Sala sala) {
		try {
			salaDao.modificaSala(nome, sala);
			frameModificaSala.setVisible(false);
			frameSale = new InterfacciaSale(this, sala.getRistoranteDiAppartenenza());
		} catch (ErrorePersonalizzato e) {
			e.stampaMessaggio();
		}
	}

	public void bottoneTornaIndietroInterfacciaModificaSalaPremuto(Ristorante ristorante) {
		frameModificaSala.setVisible(false);
		frameSale = new InterfacciaSale(this, ristorante);
	}

	public boolean presentiOccupazioniDiCameriereDopoData(Cameriere cameriereScelto, String dataCorrente) {
		return cameriereDao.cameriereOccupatoDopoDiData(cameriereScelto, dataCorrente);
	}
	
	public void bottoneRimuoviAvventoreVisualizzazioneOccupazione(String data, int id_tavolo,Avventori avventore)
	{
		avventoriDao.rimuoviAvventoreDaElencoAvventori(id_tavolo, data, avventore);
	}
	
	public void bottoneAggiungiAvventoreVisualizzazioneOccupazione(String data, ArrayList<Tavolo> tavoli, int tavoloScelto,ArrayList<String> avventoriDelTavolo)
	{
		frameVisualizzaOccupazione.setVisible(false);
		framesAggiuntaAvventore = new ArrayList<InterfacciaAggiuntaDatiAvventore>();
		framesAggiuntaAvventore.add(new InterfacciaAggiuntaDatiAvventore(this,0, tavoli, tavoloScelto, data, avventoriDelTavolo));
		framesAggiuntaAvventore.get(0).impostaBottoniCorretti(framesAggiuntaAvventore.size());
		framesAggiuntaAvventore.get(0).setVisible(true);
	}
	
	public void bottoneConfermaAggiuntaAvventoreDiVisualizzazionePremuto(ArrayList<Tavolo> tavoli, int tavoloScelto, String dataScelta) throws CampiNonCorrettiException
	{
		int i=0;
		boolean controlloCampiNonVuoti = true;
		boolean formatoNTelGiusto = true;
		boolean noApostrofi = true;
		boolean controlloAlmenoUnTelefono = false;
		boolean formatoCidGiusto = true;
		boolean doppieCID= false;
		while(i<framesAggiuntaAvventore.size() && controlloCampiNonVuoti && formatoCidGiusto && formatoNTelGiusto && noApostrofi)
		{
			if(framesAggiuntaAvventore.get(i).getNome().getText().contains(new StringBuffer("'")) || framesAggiuntaAvventore.get(i).getCognome().getText().contains(new StringBuffer("'"))) noApostrofi = false;
			if(framesAggiuntaAvventore.get(i).getCid().getText().isBlank() || framesAggiuntaAvventore.get(i).getCognome().getText().isBlank() || framesAggiuntaAvventore.get(i).getNome().getText().isBlank()) controlloCampiNonVuoti = false;
			if(!framesAggiuntaAvventore.get(i).getNtel().getText().isBlank()) controlloAlmenoUnTelefono = true;
			if(!Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", framesAggiuntaAvventore.get(i).getNtel().getText()) && !framesAggiuntaAvventore.get(i).getNtel().getText().isBlank()) formatoNTelGiusto = false;
			if(!Pattern.matches("C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]",framesAggiuntaAvventore.get(i).getCid().getText())) formatoCidGiusto =false;
			i++;
		}
		i = 0;
		while(i<framesAggiuntaAvventore.size() && !doppieCID)
		{
			for (int j = i+1 ; j< framesAggiuntaAvventore.size(); j++)
			{
				if (framesAggiuntaAvventore.get(i).getCid().getText().equals(framesAggiuntaAvventore.get(j).getCid().getText())) doppieCID = true;
			}
			i++;
		}
		
		if(controlloCampiNonVuoti  && formatoCidGiusto && !doppieCID && formatoNTelGiusto && noApostrofi && controlloAlmenoUnTelefono)
		{
			AvventoriDAOImplPostgres ADAO = new AvventoriDAOImplPostgres();
			ADAO.inserimentoMultiploAvventori(framesAggiuntaAvventore,1);
			framesAggiuntaAvventore.get(framesAggiuntaAvventore.size()-1).setVisible(false);
			frameVisualizzaOccupazione = new InterfacciaVisualizzazioneOccupazione(this, tavoli, tavoloScelto, dataScelta);
		}
		else
		{
		    throw new CampiNonCorrettiException();
		}
	}
	
	public void bottoneAggiungiCameriereInterfacciaVisualizzazioneOccupazione(ArrayList<Tavolo> tavoli, int tavoloScelto, String data, ArrayList<Integer> camerieriGiaPresenti)
	{
		frameVisualizzaOccupazione.setVisible(false);
		frameSelezioneCamerieri = new InterfacciaSelezioneCamerieri(this,tavoli,tavoloScelto,data, camerieriGiaPresenti);
		frameSelezioneCamerieri.setDiVisualizzazione(true);
	}
	
	public void bottoneIndietroAggiuntaAvventorePremuto(ArrayList<Tavolo> tavoli, String data,int indice)
	{
		framesAggiuntaAvventore.get(indice).setVisible(false);
		frameGestioneOccupazioni = new InterfacciaGestioneOccupazioni(this, tavoli, data);
	}
	
	public void bottoneIndietroAggiuntaAvventoreDiVisualizzazionePremuto(ArrayList<Tavolo> tavoli, int tavoloScelto, String data)
	{
		framesAggiuntaAvventore.get(0).setVisible(false);
		frameVisualizzaOccupazione = new InterfacciaVisualizzazioneOccupazione(this, tavoli, tavoloScelto, data);
	}
	
	public void bottoneConfermaSelezioneCameriereDiVisualizzazione(ArrayList<Cameriere> lista,ArrayList<Tavolo> tavoli, int tavoloScelto,String dataScelta)
	{
		cameriereDao.inserimentoMultiploCamerieriInServizio(lista, dataScelta, tavoli.get(tavoloScelto));
		frameSelezioneCamerieri.setVisible(false);
		frameVisualizzaOccupazione = new InterfacciaVisualizzazioneOccupazione(this,tavoli,tavoloScelto,dataScelta);
	}

	public void bottoneIndietroSelezioneCameriereDiVisualizzazione(ArrayList<Tavolo> tavoli, int tavoloScelto,
			String data) {
		
		frameSelezioneCamerieri.setVisible(false);
		frameVisualizzaOccupazione = new InterfacciaVisualizzazioneOccupazione(this, tavoli, tavoloScelto, data);
		
	}

	public void bottoneIndietroSceltaDataAggiuntCameriere(InterfacciaSelezioneDataAggiuntaCameriere riferimentoFinestra) {
		riferimentoFinestra.setVisible(false);
	}

	public void bottoneConfermaSelezioneDataAggiuntaCameriere(String dataCorrente, InterfacciaSelezioneDataAggiuntaCameriere riferimentoFinestra) {
		riferimentoFinestra.setVisible(false);
		frameAggiuntaCamerieri.dataScelta(dataCorrente);		
	}

	public void bottoneSceltaDataDaAggiuntaCameriere() {
		frameSelezioneDataAggiuntaCameriere = new InterfacciaSelezioneDataAggiuntaCameriere(this);
	}
}  
