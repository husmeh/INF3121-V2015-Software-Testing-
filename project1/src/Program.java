import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Program {
	
	PersonListe personliste;
	
	public Program(){
		this.personliste = new PersonListe();
	}
	
	public void kommandoProgram(){
		String innKommando = "start";
		Scanner scanner = new Scanner(System.in);
		while (!innKommando.equalsIgnoreCase("slutt")){
			System.out.print("ordre> ");
			String inLine = scanner.nextLine();
			
			if (!inLine.trim().equals("")){
				String[] words = inLine.split(" ");
				innKommando = words[0];
				
				if (innKommando.equalsIgnoreCase("nyperson") && (words.length >= 3)){
					// Det er forutsatt at alle personer har et navn og et telefonnummer
					String eposter = null;
					if (words.length > 3){
						eposter = words[3];
						for (int j = 4; j<words.length; j++){
							eposter = eposter + " " + words[j];
						}
					} else {
						eposter = " ";
					}
					cmdNyperson(words[1], words[2], new String[] {eposter});
				} else if (innKommando.equalsIgnoreCase("tlf") && (words.length > 2)){
					cmdTlf(words[1], words[2]);
				} else if (innKommando.equalsIgnoreCase("fjern") && (words.length > 1)){
					cmdFjern(words[1]);
				} else if (innKommando.equalsIgnoreCase("alle")){
					cmdAlle();
				} else if (innKommando.equalsIgnoreCase("hjelp")){
					cmdHjelp();
				} else if (innKommando.equalsIgnoreCase("venner") && (words.length > 2)){
					personliste.nyVenn(words[1], words[2]);
				} else if (innKommando.equalsIgnoreCase("uvenner") && (words.length > 2)){
					personliste.fjernVenn(words[1], words[2]);
				} else if (innKommando.equalsIgnoreCase("vis") && (words.length > 1)){
					cmdVis (words[1]);
				} else if (innKommando.equalsIgnoreCase("tilfil") && (words.length > 1)){
					cmdTilfil(words[1]);
				} else if (innKommando.equalsIgnoreCase("frafil") && (words.length > 1)){
					cmdFrafil(words[1]);
				} else if (innKommando.equalsIgnoreCase("slutt")){
					System.out.println("Ha det bra!");
				}
				else {
					System.out.println("Kommandoen du gav er ikke på gyldig form");
					cmdHjelp();
				}
			}
		}
	}
	
	
	public void cmdHjelp() {
		System.out.println("Tilgjengelige kommandoer: ");
		System.out.println("\tnyperson <navn> <tlfnr> <epostadr1> ..   - lager en ny person (navn tlfnr kreves)");
		System.out.println("\ttlf <navn> <tlfnr>   - forandrer en persons telefonnummer");
		System.out.println("\tfjern <navn>   - fjerner en person");
		System.out.println("\tvenner <navn> <navn på venn>   - ny vennskapsforbindelse");
		System.out.println("\tuvenner <navn> <navn på ikke-venn>   - fjerner en vennskapsforbindelse");
		System.out.println("\tvis <navn>   - viser info om en person");
		System.out.println("\talle   - lister opp alle personer i listen");
		System.out.println("\ttilfil <filnavn>   - skriver datastruktur til fil");
		System.out.println("\tfrafil <filnavn>   - leser data fra fil og oppretter datastruktur");
		System.out.println("\thjelp   - skriver ut denne kommandooversikten");
		System.out.println("\tslutt   - avslutter programmet");
	}
	
	
	public void cmdNyperson(String navn, String tlfnr, String[] epostadr){
		if (personliste.leggTilPerson(navn, tlfnr, epostadr)){
			int antallEpostAdresser = epostadr.length;
			String epostLinje = "";
			for (int i = 0; i < (antallEpostAdresser); i++){
				epostLinje = epostLinje + epostadr[i] + " ";
			}
			System.out.println("Personen " + navn + " med telefonnummer: " + tlfnr + " og e-postadresse(r): " + epostLinje + "\nhar blitt lagt til\n");
			
		}
	}
	
	
	public void cmdTlf(String navn, String tlfnr){
		Person personTlfnr = personliste.hentPerson(navn);
		if (personTlfnr != null){
			String gammelTlf = personTlfnr.hentTlfnr();
			personTlfnr.endrePaaTlf(tlfnr);
			System.out.println(navn + "s telefonnummer er endret fra " + gammelTlf + " til " + tlfnr);
		} else {
			System.out.println(navn + " finnes ikke");
		}
	}
	
	
	public void cmdFjern(String navn){
		if (personliste.fjernPerson(navn)){
			System.out.println("Personen " + navn + " er blitt fjernet.");
		} else {
			System.out.println("Personen " + navn + " ble ikke funnet, og kunne derfor ikke fjernes");
		}
	}
	
	
	public void cmdVis(String navnPaaPerson){
		Person midlertidig = personliste.hentPerson(navnPaaPerson);
		if (midlertidig != null && midlertidig.hentNavn().equalsIgnoreCase(navnPaaPerson)){
			Person [] venner = midlertidig.hentVenner();
			System.out.println("Navn: " + midlertidig.hentNavn());
			System.out.println("Telefonnummer: " + midlertidig.hentTlfnr());
			System.out.println("E-postadresse(r): " + midlertidig.hentEpostadr()[0]);
			System.out.println("- - - Venneliste" + " (totalt: " + venner.length + " venner)" + " for " + midlertidig.hentNavn() + " - - -");
			for (int i = 0; i < venner.length; i++){
				System.out.println((i + 1) + ". " + venner[i].hentNavn());
			}
		} else {
			System.out.println("FEIL: Personen " + navnPaaPerson + " ble ikke funnet");
		}
	}
	
	
	public void cmdAlle(){
		Person [] allesammen = personliste.hentPersoner();
		System.out.println("Alle registrerte personer i listen:");
		for (int i = 0; i < allesammen.length; i++){
			System.out.println((i + 1) + ". " + allesammen[i].hentNavn() + " med telefonnummer: " + allesammen[i].hentTlfnr() + " og e-postadresse(r): " + allesammen[i].hentEpostadr()[0]);
		}
		System.out.println();
	}

	
	public void cmdTilfil(String filnavn){
		PrintWriter fileOut = null;
		try {
			fileOut = new PrintWriter( new FileWriter(filnavn));
			Person[] liste = personliste.hentPersoner();
			for (int i = 0; i < liste.length; i++){
				fileOut.println("nyperson " + liste[i].hentNavn() + " " + liste[i].hentTlfnr() + " " + liste[i].hentEpostadr()[0]);
			}
			for (int i = 0; i < liste.length; i++){
				for (int j = 0; j < liste[i].hentVenner().length; j++){
					fileOut.println("venner " + liste[i].hentNavn() + " " + liste[i].hentVenner()[j].hentNavn());
				}
			}
			System.out.println("OK: Filen " + filnavn + " ble skrevet til disk");
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		finally {
			if (fileOut != null)
				fileOut.close();
		}
	}
	
	
	public void cmdFrafil(String filnavn){
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(new FileReader(filnavn));
			while (fileIn.hasNextLine()) {
				String oneLine = fileIn.nextLine();
				String[] words2 = oneLine.split(" ");
				if (words2[0].equals("nyperson")){
					String eposter = null;
					if (words2.length > 3){
						eposter = words2[3];
						for (int j = 4; j<words2.length; j++){
							eposter = eposter + " " + words2[j];
						}
					} else {
						eposter = " ";
					}
					cmdNyperson(words2[1], words2[2], new String[]{eposter});
				} else if (words2[0].equals("venner")){
					personliste.nyVenn(words2[1], words2[2]);
				}
				
			}
		}
		catch ( IOException e ) {
			System.err.println("FEIL: Filen " + filnavn + " ble ikke funnet");
		}
		finally {
			if (fileIn != null)
				fileIn.close();
		}
	}
	
	
}
