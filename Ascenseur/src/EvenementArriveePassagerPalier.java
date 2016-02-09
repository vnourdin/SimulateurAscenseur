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
        Passager p = new Passager(date, etageDeDepart, immeuble);

        if (immeuble.cabine.etage == this.etageDeDepart && immeuble.cabine.porteOuverte)
            notYetImplemented();
        else {
            this.etageDeDepart.ajouter(p);
            echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.etageDeDepart.arriveeSuivante(), this.etageDeDepart));
        }
    }
}
