public class EvenementOuverturePorteCabine extends Evenement {

    public EvenementOuverturePorteCabine(long d) {
        super(d);
    }

    public void afficheDetails(Immeuble immeuble) {
        System.out.print("OPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        Etage etage = cabine.etage;
        assert !cabine.porteOuverte;

        cabine.porteOuverte = true;

        assert cabine.porteOuverte;


        int nbPersonnesDescendues = cabine.faireDescendreCeuxQuiVeulent(immeuble);
        int nbPersonnesMontees = cabine.faireMonterCeuxQuiVeulent();
        if (!cabine.aUneDestination() && etage.auMoinsUnPassagers()) {
            if (cabine.status() == '^') {
                cabine.changerStatus('v');
            } else {
                cabine.changerStatus('^');
            }
            nbPersonnesMontees += cabine.faireMonterCeuxQuiVeulent();
        }

        echeancier.ajouter(new EvenementFermeturePorteCabine(this.date + Constantes.tempsPourFermerLesPortes + Constantes.tempsPourSortirDeLaCabine * nbPersonnesDescendues + Constantes.tempsPourEntrerDansLaCabine * nbPersonnesMontees));

    }

}
