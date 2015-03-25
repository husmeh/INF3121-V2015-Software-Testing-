
public class Person {

	// har endret til public fra private (fikk ikke tilgang til noe med private)
	
	private String navn;
	private String tlfnr;
	public String[] epostadr;
	public Person nestePerson;
	public Venn foersteVenn;
	public int venneTeller;
	
	public Person(String navn, String tlfnr, String[] epostadr) {    //konstruktøren
		this.navn = navn;
		this.tlfnr = tlfnr;
		this.epostadr = epostadr;
	}
	
	
	public String hentNavn() {
		return navn;
	}
	
	
	public String hentTlfnr() {
		return tlfnr;
	}
	
	
	public String [] hentEpostadr() {
		return epostadr;
	}
	
	
	/*
	 * Skal hente alle personene i personens venneliste
	 * @return		en peker til en personarray med personens venner
	 */
	public Person [] hentVenner() {
		int i = 0;
		Venn v = foersteVenn;
		if (foersteVenn == null) {
			//hvis ingen venner returneres array med 0 elementer
			return new Person[0];
		} else {
			//vi teller opp antall venner i variabelen i
			while (v != null) {
				i++;
				v = v.nesteVenn;
			}
			//lager et personarray (peker p) av størrelse i:
			Person [] p = new Person[i];
			v = foersteVenn;
			while (v != null){
				//løkka legger Personen førstevenn bakerst i arrayet og siste venn først i arrayet, plass 0
				p[i-1] = v.minVenn;
				i--;
				v = v.nesteVenn;
			}
			//array som returneres hvis personen har venner:
			return p;
		}
	}
	
	
	public Person hentVenn(String vnavn){
		for (Venn peker = foersteVenn; peker != null; peker = peker.nesteVenn){
			if (peker.minVenn.hentNavn().equalsIgnoreCase(vnavn)){
				return peker.minVenn;
			}
		}
		return null;
	}
	
	
	public void endrePaaTlf(String nummer){
		this.tlfnr = nummer;
	}
	
}
