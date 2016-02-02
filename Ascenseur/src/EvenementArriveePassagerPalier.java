public class EvenementArriveePassagerPalier extends Evenement {

    private Etage etageDeDepart;
    
    public EvenementArriveePassagerPalier(long d, Etage edd) {
        super(d);
        etageDeDepart = edd;
    }
    
    public void afficheDetails(Immeuble immeuble) {
        System.out.print("APP ");
        System.out.print(etageDeDepart.numero());
    }
    
    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        assert etageDeDepart != null;
    	Passager p = new Passager (date, etageDeDepart, immeuble);
	notYetImplemented();
    }
}
