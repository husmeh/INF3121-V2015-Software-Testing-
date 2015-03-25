public class PersonListe {
	
	int teller = 0; //skal holde styr på antall personer i personlista
	Person foerste;
	Person peker = foerste;
	
	
	/*
	 * Legg til en person til lista
	 * @param navn		personens navn, påkrevet
	 * @param tlfnr		personens telefonnummer, påkrevet
	 * @param epostadr	personens e-postadresse(r), ikke påkrevet
	 * @return			true hvis personen ble lagt til
	 * 					false hvis personen allerede var i lista, ingen kan ha samme navn
	 */
	public boolean leggTilPerson(String navn, String tlfnr, String[] epostadr){
		Person nyPers = new Person(navn, tlfnr, epostadr);
		if (hentPerson(navn) != null ){
			System.out.println("FEIL: " + navn + " finnes allerede i listen");
			return false;
		} else {
			if (foerste == null){
				foerste = nyPers;
			} else {
				nyPers.nestePerson = foerste;
				foerste = nyPers;
			}
			teller++;
			return true;
		}
	}
	
	
	/*
	 * Fjern en person fra lista
	 * @param navn		personens navn
	 * @return			true hvis fjerningen var vellykket
	 * 					false hvis personen ikke fantes i lista
	 */
	public boolean fjernPerson(String navn) {
		peker = foerste;
		for (Person k = foerste; k != null; k = k.nestePerson){
			Person venn = k.hentVenn(navn);
			if (venn != null){
				fjernVenn(k.hentNavn(), venn.hentNavn());
			}
		}
		if (foerste == null) return false;
		if (foerste.hentNavn().equalsIgnoreCase(navn)){
			foerste = foerste.nestePerson;
			teller--;
			return true;
		}
		for (peker = foerste; peker.nestePerson != null; peker = peker.nestePerson){
			if (peker.nestePerson.hentNavn().equalsIgnoreCase(navn)){
				peker.nestePerson = peker.nestePerson.nestePerson;
				teller--;
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * Legg til en person på en annens venneliste
	 * @param navn		navnet til personen som får en venn
	 * @param vnavn		navnet til den nye vennen
	 * @return 			true hvis vennskapsforholdet ble opprettet
	 * 					false hvis
	 * 						-relasjonen fantes
	 * 						-en eller begge personene fantes ikke
	 */
	public boolean nyVenn(String navn, String vnavn){
		Person a = hentPerson(navn);
		Person enVenn = hentPerson(vnavn);
		Venn v = new Venn(enVenn);
		if (a == null){
			System.out.println("FEIL: " + navn + " finnes ikke");
			return false;
		} else if (enVenn == null) {
			System.out.println("FEIL: " + vnavn + " finnes ikke");
			return false;
		} else if (a.hentVenn(vnavn) != null) {
			System.out.println("FEIL: " + vnavn + " var allerede på vennelisten til " + navn);
			return false;
		} else if (a.foersteVenn == null){
			a.foersteVenn = v;
			System.out.println("OK: " + a.foersteVenn.minVenn.hentNavn() + " er nå lagt til på vennelisten til " + a.hentNavn());
			a.venneTeller++;
			return true;
		} else {
			v.nesteVenn = a.foersteVenn;
			a.foersteVenn = v;
			System.out.println("OK: " + a.foersteVenn.minVenn.hentNavn() + " er nå lagt til på vennelisten til " + a.hentNavn());
			a.venneTeller++;
			return true;
		}
	}
	

	/*
	 * Fjern en person som venn til en annen person
	 * @param navn		navnet til personen som mister en venn
	 * @param vnavn		navnet til personen som skal fjernes som venn
	 * @return 			true hvis vennskapsforholdet ble fjernet
	 * 					false hvis vennskapsforholdet ikke eksisterte eller en av personene ikke fantes
	 */
	public boolean fjernVenn(String navn, String vnavn){
		Person a = hentPerson(navn);
		Person enVenn = hentPerson(vnavn);
		if (a == null){
			System.out.println("FEIL: " + navn + " finnes ikke");
			return false;
		}
		else if (a.foersteVenn == null){
			System.out.println("FEIL: " + navn + " har ingen personer på sin venneliste");
			return false;
		}
		else if (enVenn == null) {
			System.out.println("FEIL: " + vnavn + " finnes ikke");
			return false;
		}
		else if (a.hentVenn(enVenn.hentNavn()) == null){
			System.out.println("FEIL: " + vnavn + " ble ikke funnet på " + navn + "s venneliste");
			return false;
		}
		else if (a.foersteVenn.minVenn.hentNavn().equalsIgnoreCase(vnavn)){
			a.foersteVenn = a.foersteVenn.nesteVenn;
			System.out.println("OK: " + vnavn + " er nå fjernet fra vennelisten til " + navn);
			a.venneTeller--;
			return true;
		}
		else {
			for (Venn peker = a.foersteVenn; peker.nesteVenn != null; peker = peker.nesteVenn){
				if (peker.nesteVenn.minVenn.hentNavn().equalsIgnoreCase(enVenn.hentNavn())){
					peker.nesteVenn = peker.nesteVenn.nesteVenn;
					System.out.println("OK: " + vnavn + " er nå fjernet fra vennelisten til " + navn);
					a.venneTeller--;
					return true;
				}
			}
		}
		return true;
	}
	
	
	/*
	 * Hent antall personer i lista
	 * @return 	antall personer
	 */
	public int hentAntall() {
		int stoerrelse = 0;
		Person p = foerste;
		while (p != null) {
			stoerrelse++;
			p = p.nestePerson;
		}
		return stoerrelse;
	}
	
	
	/*
	 * Hent et array med alle personene i lista
	 * @return	array med personobj.
	 */
	public Person [] hentPersoner() {
		Person midlertidig;
		midlertidig = foerste;
		Person[] allePersListe = new Person[teller];
		for (int i = 0; i<teller; i++) {
			allePersListe[i] = midlertidig;
			midlertidig = midlertidig.nestePerson;
		}
		return allePersListe;
	}
	
	
	/*
	 * Hent et personobjekt
	 * @param navn 	navnet til personobj som skal hentes
	 * @return 		personobj med navnet som skulle hentes, null hvis ikke funnet
	 */
	public Person hentPerson(String navn) {
		Person finn = foerste;
		while (finn != null) {
			if (finn.hentNavn().equals(navn)) {
				return finn;
			}
			finn = finn.nestePerson;
		} //løkka er slutt
		return null;
	}
	

}

