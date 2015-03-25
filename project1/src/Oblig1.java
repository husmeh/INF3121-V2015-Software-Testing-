
/**
 * Prekode for Oblig1 - INF1010 2012.
 * 
 * @author inf1010
 *
 */
public class Oblig1 {

	public static void main(String[] argumenter) throws Exception {

		if (argumenter.length == 0) {
			System.out.println("INF1010 2012 - Obligatorisk oppgave 1");
			System.out.println("Bruk:");
			System.out.println("Kjøre testene: java Oblig1 test");
			System.out.println("Kjøre programmet: java Oblig1 program");
		}
		else if (argumenter[0].equals("test")) {
			
			System.out.println("Editer Oblig1.java og kompiler med Oblig1Test.java for å kjøre testene.");
			
			// Fjern kommentartegnene for det fÃ¸lgende for Ã¥ kunne kjÃ¸re testene i Oblig1Test.java:
			
			PersonListe personlist = new PersonListe();			
			Oblig1Test tester = new Oblig1Test(personlist);
			tester.testivei();
			
		}
		else if (argumenter[0].equals("program")) {
			System.out.println("Skriv inn ordet *hjelp* (uten stjerner) for å få en kommandooversikt.");
			Program nykjoering = new Program();
			nykjoering.kommandoProgram();
		}
		else {
			System.out.println("INF1010 2012 - Obligatorisk oppgave 1"); 
            System.out.println("Bruk:"); 
            System.out.println("Kjøre testene: java Oblig1 test"); 
            System.out.println("Kjøre programmet: java Oblig1 program"); 
		} 
	}
}
