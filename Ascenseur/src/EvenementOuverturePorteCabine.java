public class EvenementOuverturePorteCabine extends Evenement {

    public EvenementOuverturePorteCabine(long d) {
        super(d);
    }

    public void afficheDetails(Immeuble immeuble) {
        System.out.print("OPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        assert !cabine.porteOuverte;
        cabine.porteOuverte = true;

        int nbPersonnesDescendues = cabine.faireDescendreCeuxQuiVeulent(immeuble, this.date);

        int nbPersonnesMontees = cabine.faireMonterCeuxQuiVeulent();

        echeancier.ajouter(new EvenementFermeturePorteCabine(this.date + Constantes.tempsPourFermerLesPortes + Constantes.tempsPourSortirDeLaCabine * nbPersonnesDescendues + Constantes.tempsPourEntrerDansLaCabine * nbPersonnesMontees));
    }
}