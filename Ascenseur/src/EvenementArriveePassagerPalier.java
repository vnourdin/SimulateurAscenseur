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
            if ((this.isModeParfait() && (immeuble.cabine.aUneDestination() && passagerQuiArrive.sens() == immeuble.cabine.status()) || !immeuble.cabine.aUneDestination())
                    || !this.isModeParfait()) {
                if (immeuble.cabine.ajouterPassagerSiPossible(passagerQuiArrive)) {
                    Evenement fpc = echeancier.retournerEtEnleverFPC();
                    fpc.date += Constantes.tempsPourEntrerDansLaCabine;
                    echeancier.ajouter(fpc);
                    this.date += this.etageDeDepart.arriveeSuivante();
                    echeancier.ajouter(this);
                } else
                    ajouterALetageEtRelancerAPP(passagerQuiArrive, echeancier);
            } else
                ajouterALetageEtRelancerAPP(passagerQuiArrive, echeancier);
        } else
            ajouterALetageEtRelancerAPP(passagerQuiArrive, echeancier);
    }

    private void ajouterALetageEtRelancerAPP(Passager passagerQuiArrive, Echeancier echeancier) {
        this.etageDeDepart.ajouter(passagerQuiArrive);
        this.date += this.etageDeDepart.arriveeSuivante();
        echeancier.ajouter(this);
    }
}