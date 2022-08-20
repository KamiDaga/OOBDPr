import java.sql.*;
import javax.swing.JOptionPane;
public class DB_Builder 
{
	
	public DB_Builder() throws CreazioneErrataDatabaseException, DriverMancanteException
	{
		boolean preesistente = false;
		try 
		{	
			//Connessione con url del server senza database in caso il database non sia presente
			//(La connessione con accesso al database e' gestita dalla classe singleton DB_Connection)
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "1754Ggdf");	
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE ristorantidb;");
			//Nota: ogni volta che bisogna connettersi al db i caratteri 
			//devono essere tutti minuscoli, altrimenti dara' errore
			//(database non esistente)
			conn.close();
		}
		catch(ClassNotFoundException e)
		{
			DriverMancanteException ecc = new DriverMancanteException();
			throw ecc;
		}
		catch(SQLException e)
		{
			if (e.getSQLState().equals("42P04")) preesistente = true; //Stato di SQL in caso di Database gia' esistente
			else 
			{
				JOptionPane.showMessageDialog(null,"C'e' stato un errore, il database non e' stato creato correttamente\n"
						+ "Riprovare a riavviare l'applicativo.", "Errore!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(!preesistente)
		{
			try
			{
				Statement stmt = DB_Connection.getInstance().getConnection().createStatement();
				
				stmt.executeUpdate("CREATE TABLE Ristorante"
								+ "(Id_Ristorante SERIAL,"
								+ "Nome VARCHAR(40) NOT NULL, "
								+ "Via VARCHAR(40) NOT NULL, "
								+ "N_Civico INTEGER NOT NULL, "
								+ "Citta VARCHAR(40) NOT NULL, "
								+ "PRIMARY KEY(Id_Ristorante),"
								+ "CONSTRAINT CivicoSensato CHECK (N_Civico>0), "
								+ "CONSTRAINT UnicaLocalita UNIQUE(Via, N_Civico, Citta));");

				stmt.executeUpdate("CREATE TABLE Sala"
								+ "(Id_Sala SERIAL,"
								+ "Nome VARCHAR(40) NOT NULL,"
								+ "Id_Ristorante INTEGER NOT NULL,"
								+ "PRIMARY KEY(Id_Sala),"
								+ "CONSTRAINT Appartenenza FOREIGN KEY(Id_Ristorante) REFERENCES Ristorante(Id_Ristorante)"
								+ "                            ON DELETE CASCADE                    ON UPDATE CASCADE,"
								+ "CONSTRAINT NomeUnicoSalaDelRistorante UNIQUE(Nome,Id_Ristorante));");
				
				stmt.executeUpdate("CREATE TABLE Tavolo"
								+ "(Id_Tavolo SERIAL,"
								+ "Capacita INTEGER NOT NULL,"
								+ "Id_Sala INTEGER NOT NULL,"
								+ "Numero INTEGER NOT NULL,"
								+ "PRIMARY KEY(Id_Tavolo),"
								+ "CONSTRAINT DellaSala FOREIGN KEY(Id_Sala) REFERENCES Sala(Id_Sala)"
								+ "			 ON DELETE CASCADE              ON UPDATE CASCADE, "
								+ "CONSTRAINT DatiTavoloSensati CHECK(Capacita>0 AND Numero>0), "
								+ "CONSTRAINT TavoloUnicoPerSala UNIQUE(Id_Sala,Numero));");

				stmt.executeUpdate("CREATE TABLE Adiacenza"
								+ "(Id_Tavolo1 INTEGER NOT NULL,"
								+ "Id_Tavolo2 INTEGER NOT NULL,"
								+ "CONSTRAINT Tavolo1 FOREIGN KEY(Id_Tavolo1) REFERENCES Tavolo(Id_Tavolo) "
								+ "			 ON DELETE CASCADE              ON UPDATE CASCADE, "
								+ "CONSTRAINT Tavolo2 FOREIGN KEY(Id_Tavolo2) REFERENCES Tavolo(Id_Tavolo) "
								+ "			 ON DELETE CASCADE              ON UPDATE CASCADE, "
								+ "CONSTRAINT AntiRiflessivo CHECK (Id_Tavolo1 <> Id_Tavolo2), "
								+ "CONSTRAINT UnicaCoppiaAdiacenti UNIQUE (Id_Tavolo1, Id_Tavolo2));");
			
				stmt.executeUpdate("CREATE TABLE Tavolata"
								+ "(Id_Tavolata SERIAL PRIMARY KEY,"
								+ "Data DATE NOT NULL,"
								+ "Id_Tavolo INTEGER NOT NULL,"
								+ "CONSTRAINT Del FOREIGN KEY (Id_tavolo) REFERENCES Tavolo(Id_Tavolo)"
								+ "                   ON DELETE CASCADE                 ON UPDATE CASCADE,"
								+ "CONSTRAINT UnicaTavolataPerGiornoATavolo UNIQUE(Data,Id_Tavolo));");
			
				stmt.executeUpdate("CREATE TABLE Avventori"
								+ "(Nome VARCHAR(30) NOT NULL,"
								+ "Cognome VARCHAR(30) NOT NULL,"
								+ "N_CID CHAR(9) NOT NULL PRIMARY KEY, "
								+ "N_Tel CHAR(10), "
								+ "CONSTRAINT FormatoCIDGiustoAvventori CHECK (N_CID SIMILAR TO 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]'), "
								+ "CONSTRAINT FormatoNTelGiustoOAssenteAvventori CHECK (N_Tel SIMILAR TO '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR N_Tel IS NULL));");
			
				stmt.executeUpdate("CREATE TABLE Elenco_Avventori"
								+ "(Id_Tavolata INTEGER NOT NULL,"
								+ "N_CID CHAR(9) NOT NULL, "
								+ "CONSTRAINT InTavolata FOREIGN KEY(Id_Tavolata) REFERENCES Tavolata(Id_Tavolata) ON DELETE CASCADE ON UPDATE CASCADE, "
								+ "CONSTRAINT DiAvventore FOREIGN KEY(N_CID) REFERENCES Avventori(N_CID) ON DELETE CASCADE ON UPDATE CASCADE, "
								+ "CONSTRAINT AvventoreUnicoPerTavolata UNIQUE (Id_Tavolata, N_CID) );");
			
				stmt.executeUpdate("CREATE VIEW N_Avventori AS "
								+ "SELECT T.Id_Tavolata, COUNT (EA.N_CID) AS Num "
								+ "FROM Tavolata AS T NATURAL JOIN Elenco_Avventori AS EA "
								+ "GROUP BY T.Id_Tavolata;");
			
				stmt.executeUpdate("CREATE TABLE Cameriere"
								+ "(Id_Cameriere SERIAL PRIMARY KEY,"
								+ "CID_Cameriere CHAR(9) NOT NULL, "
								+ "Nome VARCHAR(30) NOT NULL,"
								+ "Cognome VARCHAR(30) NOT NULL,"
								+ "Id_Ristorante INTEGER NOT NULL,"
								+ "Data_Ammissione DATE NOT NULL,"
								+ "Data_Licenziamento DATE DEFAULT NULL, "
								+ "CONSTRAINT FormatoCIDGiustoCamerieri CHECK (CID_Cameriere SIMILAR TO 'C[A-Z][0-9][0-9][0-9][0-9][0-9][A-Z][A-Z]'), "
								+ "CONSTRAINT LavoraNel FOREIGN KEY(Id_Ristorante) REFERENCES Ristorante(Id_Ristorante)"
								+ "                         ON DELETE CASCADE                    ON UPDATE CASCADE,"
								+ "CONSTRAINT ConsistenzaTemporaleAmmissioneLicenziamento CHECK (Data_Ammissione<Data_Licenziamento));");
						
				stmt.executeUpdate("CREATE TABLE Servizio "
								+ "(Id_Cameriere INTEGER NOT NULL, "
								+ "Id_Tavolata INTEGER NOT NULL, "
								+ "CONSTRAINT Cameriere FOREIGN KEY(Id_Cameriere) REFERENCES Cameriere(Id_Cameriere)"
								+ "                         ON DELETE CASCADE                   ON UPDATE CASCADE,"
								+ "CONSTRAINT Tavolata FOREIGN KEY(Id_Tavolata) REFERENCES Tavolata(Id_Tavolata)"
								+ "                        ON DELETE CASCADE                  ON UPDATE CASCADE);");
				
				stmt.executeUpdate("CREATE TABLE Posizioni"
						          +"(Id_Tavolo INTEGER NOT NULL UNIQUE,"
						          + "PosX INTEGER NOT NULL,"
						          +"PosY INTEGER NOT NULL,"
						          +"DimX INTEGER NOT NULL,"
						          +"DimY INTEGER NOT NULL,"
						          + "CONSTRAINT DimensioniTavoloSensate CHECK (PosX>0 AND Posy>0 AND DimX>0 AND DimY>0), "
						          + "CONSTRAINT DelTavolo FOREIGN KEY(Id_Tavolo) REFERENCES Tavolo(Id_Tavolo) "
						          + "                     ON DELETE CASCADE                 ON UPDATE CASCADE);");

				stmt.executeUpdate ("CREATE FUNCTION InserisciSimmetrico() RETURNS TRIGGER "
								+" AS $$ "
								+ "DECLARE "
								+ "CheckConto INTEGER; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO CheckConto "
								+ "FROM Adiacenza AS A "
								+ "WHERE A.Id_Tavolo1 = NEW.Id_Tavolo2 AND A.Id_Tavolo2 = NEW.Id_Tavolo1; "
								+ "IF (CheckConto=0) THEN "
								+ "	INSERT INTO Adiacenza "
								+ "	VALUES (NEW.Id_Tavolo2, NEW.Id_Tavolo1); "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");

				stmt.executeUpdate("CREATE TRIGGER SimmetriaInserimento "
								+ "AFTER INSERT ON Adiacenza "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION InserisciSimmetrico();"); 

				stmt.executeUpdate ("CREATE FUNCTION CancellaSimmetrico() RETURNS TRIGGER "
								+ " AS $$ "
								+ "DECLARE "
								+ "CheckConto INTEGER; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO CheckConto "
								+ "FROM Adiacenza AS A " 
								+ "WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1; "
								+ "IF (CheckConto>0) THEN "
								+ "	DELETE FROM Adiacenza AS A "
								+ "	WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");

				stmt.executeUpdate("CREATE TRIGGER SimmetriaCancellazione "
								+ "AFTER DELETE ON Adiacenza "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION CancellaSimmetrico();"); 

				stmt.executeUpdate ("CREATE FUNCTION ModificaSimmetrico() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "CheckConto INTEGER; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO CheckConto "
								+ "FROM Adiacenza AS A "
								+ "WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1; "
								+ "IF (CheckConto>0) THEN "
								+ "UPDATE Adiacenza AS A "
								+ "SET Id_Tavolo1 = NEW.Id_Tavolo2, Id_Tavolo2 = NEW.Id_Tavolo1 "
								+ "WHERE A.Id_Tavolo1 = OLD.Id_Tavolo2 AND A.Id_Tavolo2 = OLD.Id_Tavolo1; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; "); 

				stmt.executeUpdate("CREATE TRIGGER SimmetriaModifica "
								+ "AFTER UPDATE ON Adiacenza "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION ModificaSimmetrico();"); 

				stmt.executeUpdate("CREATE FUNCTION ConsistenzaServizioTavolataInserimento() RETURNS TRIGGER "
								+ "as $$ "
								+ "DECLARE "
								+ "CheckConto INTEGER; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO CheckConto "
								+ "FROM Cameriere AS C, Tavolata AS T "
								+ "WHERE C.Id_Cameriere=NEW.Id_Cameriere AND T.Id_Tavolata = NEW.Id_Tavolata AND (C.Data_Ammissione>T.Data OR C.Data_Licenziamento <T.Data); "
								+ "IF (CheckConto > 0) THEN "
								+ "DELETE FROM Servizio "
								+ "WHERE Id_Cameriere=NEW.Id_Cameriere AND Id_Tavolata = NEW.Id_Tavolata; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");

				stmt.executeUpdate("CREATE TRIGGER ConsistenzaServizioInserimento "
								+ "AFTER INSERT ON Servizio "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION ConsistenzaServizioTavolataInserimento(); ");

				stmt.executeUpdate("CREATE FUNCTION ConsistenzaServizioTavolataModificaServizio() RETURNS TRIGGER "
								+ "as $$ "
								+ "DECLARE "
								+ "CheckConto INTEGER; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO CheckConto "
								+ "FROM Cameriere AS C, Tavolata AS T "
								+ "WHERE C.Id_Cameriere=NEW.Id_Cameriere AND T.Id_Tavolata = NEW.Id_Tavolata AND (C.Data_Ammissione>T.Data OR C.Data_Licenziamento <T.Data); "
								+ "IF (CheckConto>0) THEN "
								+ "UPDATE Servizio "
								+ "SET Id_Cameriere = OLD.Id_Cameriere, Id_Tavolata = OLD.Id_Tavolata "
								+ "WHERE Id_Cameriere = NEW.Id_Cameriere AND Id_Tavolata = NEW.Id_Tavolata; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");

				stmt.executeUpdate("CREATE TRIGGER ConsistenzaServizioUpdate "
								+ "AFTER UPDATE ON Servizio "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION  ConsistenzaServizioTavolataModificaServizio(); ");

				stmt.executeUpdate("CREATE FUNCTION ConsistenzaServizioTavolataModificaAmmissioneLicenziamento() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "Check_AConto INTEGER; "
								+ "Check_LConto INTEGER; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO Check_AConto "
								+ "FROM Servizio AS S, Tavolata AS T "
								+ "WHERE S.Id_Cameriere = NEW.Id_Cameriere AND S.Id_Tavolata=T.Id_Tavolata AND NEW.Data_Ammissione>T.Data; "
								+ "SELECT COUNT(*) INTO Check_LConto "
								+ "FROM Servizio AS S, Tavolata AS T "
								+ "WHERE S.Id_Cameriere = NEW.Id_Cameriere AND S.Id_Tavolata=T.Id_Tavolata AND NEW.Data_Licenziamento<T.Data; "
								+ "IF (Check_AConto > 0) THEN "
								+ "NEW.Data_Ammissione = OLD.Data_Ammissione; "
								+ "END IF; "
								+ "IF (Check_LConto > 0) THEN "
								+ "NEW.Data_Licenziamento = OLD.Data_Licenziamento; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql;");

				stmt.executeUpdate("CREATE TRIGGER ConsistenzaServizioAggiornamentoAmmissioneLicenziamento "
								+ "AFTER UPDATE ON Cameriere "
								+ "FOR EACH ROW "
								+ "WHEN (OLD.Data_Ammissione <> NEW.Data_Ammissione OR OLD.Data_Licenziamento <> NEW.Data_Licenziamento) "
								+ "EXECUTE FUNCTION  ConsistenzaServizioTavolataModificaAmmissioneLicenziamento(); ");

				stmt.executeUpdate("CREATE FUNCTION RimuoviDataAmmissioneSbagliata() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "Counting integer; "
								+ "BEGIN "
								+ "SELECT COUNT(*) into Counting "
								+ "FROM Cameriere as C "
								+ "WHERE C.cid_cameriere = NEW.cid_cameriere AND NEW.data_ammissione<C.data_licenziamento AND C.id_cameriere <> NEW.id_cameriere; "
								+ "IF (Counting >0) THEN "
								+ "   DELETE FROM cameriere WHERE id_cameriere = NEW.id_cameriere; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");


				stmt.executeUpdate("CREATE TRIGGER ConsistenzaDateInserimentoCameriere "
								+ "AFTER INSERT ON Cameriere "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION  RimuoviDataAmmissioneSbagliata(); ");

				stmt.executeUpdate("CREATE FUNCTION NoTavolatePiuGrandiDiCapacita() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "Counting integer; "
								+ "BEGIN "
								+ "SELECT COUNT(*) INTO Counting "
								+ "FROM (N_Avventori AS N_A JOIN Tavolata AS TA ON N_A.Id_Tavolata = TA.Id_Tavolata) AS N_TA JOIN Tavolo AS TAV ON N_TA.Id_Tavolo = TAV.Id_Tavolo "
								+ "WHERE N_TA.Num>TAV.Capacita; "
								+ "IF (Counting>0) THEN "
								+ "	DELETE "
								+ "	FROM Elenco_Avventori AS EA "
								+ "	WHERE EA.Id_Tavolata = NEW.Id_Tavolata "
								+ " AND EA.N_CID = NEW.N_CID; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");

				stmt.executeUpdate("CREATE TRIGGER TriggerNoTavolatePiuGrandi "
								+ "AFTER INSERT ON Elenco_Avventori "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION NoTavolatePiuGrandiDiCapacita(); ");

				stmt.executeUpdate("CREATE FUNCTION NoTavolateSenzaNumeriDiTelefono() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "Counting integer; "
								+ "BEGIN "
								+ "SELECT COUNT(AV.N_Tel) INTO Counting "
								+ "FROM Elenco_Avventori AS E_A, AVVENTORI AS AV "
								+ "WHERE AV.N_CID = E_A.N_CID AND E_A.Id_Tavolata = NEW.Id_Tavolata; "
								+ "IF (Counting = 0) THEN "
								+ "	DELETE "
								+ "	FROM TAVOLATA AS TA "
								+ "	WHERE TA.Id_Tavolata = NEW.Id_Tavolata; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");

				stmt.executeUpdate("CREATE TRIGGER TriggerNoNumeriDiTelefono "
								+ "AFTER INSERT ON Elenco_Avventori "
								+ "FOR EACH ROW "
								+ "EXECUTE FUNCTION NoTavolateSenzaNumeriDiTelefono();");
				
				stmt.executeUpdate("CREATE FUNCTION ImpedireModificaTavolataSenzaNumeriDiTelefono() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "CursoreNumeriTelefono CURSOR IS SELECT COUNT(AV.N_Tel) AS Numeri, TA.Id_Tavolata "
								+ "								FROM AVVENTORI AS AV, TAVOLATA AS TA, ELENCO_AVVENTORI AS EA "
								+ "								WHERE NEW.N_CID IN (SELECT EA2.N_CID FROM ELENCO_AVVENTORI AS EA2 WHERE EA2.Id_Tavolata = TA.Id_Tavolata) "
								+ "								AND AV.N_CID = EA.N_CID AND TA.ID_TAVOLATA = EA.ID_TAVOLATA "
								+ "								GROUP BY TA.Id_Tavolata; "
								+ "NessunProblema BOOLEAN := true; "
								+ "BEGIN  "
								+ "FOR NumeriTavolataCorrente IN CursoreNumeriTelefono "
								+ "LOOP "
								+ "IF (NumeriTavolataCorrente.Numeri = 0) THEN  "
								+ "NessunProblema = false; "
								+ "END IF;  "
								+ "END LOOP; "
								+ "IF (NessunProblema = false) THEN "
								+ "UPDATE AVVENTORI AS AV "
								+ "SET N_TEL = OLD.N_TEL "
								+ "WHERE AV.N_CID = NEW.N_CID; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql;");
				
				stmt.executeUpdate("CREATE TRIGGER TriggerModificaNumeriDiTelefono "
								+ "AFTER UPDATE OF N_Tel ON AVVENTORI "
								+ "FOR EACH ROW "
								+ "WHEN (NEW.N_Tel IS NULL AND OLD.N_TEL IS NOT NULL) "
								+ "EXECUTE FUNCTION ImpedireModificaTavolataSenzaNumeriDiTelefono();");
				
				stmt.executeUpdate("CREATE FUNCTION ImpedisciModificaTavolatePiuGrandiDiCapacita() RETURNS TRIGGER "
								+ "AS $$ "
								+ "DECLARE "
								+ "Counting integer; "
								+ "BEGIN  "
								+ "SELECT COUNT(*) INTO Counting "
								+ "FROM (N_Avventori AS N_A JOIN Tavolata AS TA ON N_A.Id_Tavolata = TA.Id_Tavolata) AS N_TA JOIN Tavolo AS TAV ON N_TA.Id_Tavolo = TAV.Id_Tavolo "
								+ "WHERE N_TA.Num>TAV.Capacita; "
								+ "IF (Counting>0) THEN "
								+ "	UPDATE ELENCO_AVVENTORI AS EA "
								+ "	SET Id_Tavolata = OLD.Id_Tavolata "
								+ "	WHERE EA.N_CID = OLD.N_CID AND EA.Id_Tavolata = NEW.Id_Tavolata; "
								+ "END IF; "
								+ "RETURN NEW; "
								+ "END; "
								+ "$$ LANGUAGE plpgsql; ");
				
				stmt.executeUpdate("CREATE TRIGGER TriggerModificaNoTavolatePiuGrandi "
								+ "AFTER UPDATE OF Id_Tavolata ON Elenco_Avventori "
								+ "FOR EACH ROW "
								+ "WHEN (NEW.Id_Tavolata <> OLD.Id_Tavolata) "
								+ "EXECUTE FUNCTION ImpedisciModificaTavolatePiuGrandiDiCapacita();");
				
				stmt.executeUpdate("CREATE INDEX Indice_Data_Occupazioni ON TAVOLATA (DATA);");
				
				stmt.executeUpdate("CREATE PROCEDURE unicoTavPerData(CID elenco_avventori.n_cid%TYPE, tavolata elenco_avventori.id_tavolata%TYPE)"
								+ "AS $$ "
								+ "DECLARE "
								+ "Conta integer; "
								+ "DataScelta DATE; "
								+ "BEGIN "
								+ "INSERT INTO ELENCO_AVVENTORI VALUES(tavolata,CID); "
								+ "SELECT tav.data into DataScelta "
								+ "FROM tavolata as tav "
								+ "WHERE tav.id_tavolata = tavolata; "
								+ "SELECT COUNT(*) into Conta "
								+ "FROM Elenco_avventori as EA, Tavolata as TAV "
								+ "WHERE EA.id_tavolata = TAV.id_tavolata AND EA.n_cid = CID AND TAV.data = DataScelta; "
								+ "ASSERT Conta = 1 , 'L avventore si trova gia in un altra tavolata nella data scelta!'; "
								+ "IF (Conta >1) THEN "
								+ "	ROLLBACK; "
								+ "END IF; "
								+ "COMMIT; "
								+ "END; "
								+ "$$ language plpgsql; ");
				

			}
			catch(SQLException e)
			{
				CreazioneErrataDatabaseException ecc = new CreazioneErrataDatabaseException();
				ecc.stampaMessaggio();
			}
		}
	}
}
