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
        Passager passagerQuiArrive = new Passager(date, etageDeDepart, immeuble);

        if (immeuble.cabine.etage.numero() == this.etageDeDepart.numero() && immeuble.cabine.porteOuverte) {
            if (immeuble.cabine.ajouterPassagerSiPossible(passagerQuiArrive)) {
                Evenement fpc = echeancier.retournerEtEnleverFPC();
                fpc.date += Constantes.tempsPourEntrerDansLaCabine;
                echeancier.ajouter(fpc);
                echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.etageDeDepart.arriveeSuivante(), this.etageDeDepart));
            } else {
                this.etageDeDepart.ajouter(passagerQuiArrive);
                echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.etageDeDepart.arriveeSuivante(), this.etageDeDepart));
            }
        } else {
            this.etageDeDepart.ajouter(passagerQuiArrive);
            echeancier.ajouter(new EvenementArriveePassagerPalier(this.date + this.etageDeDepart.arriveeSuivante(), this.etageDeDepart));
        }
    }
}