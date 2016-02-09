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
        echeancier.ajouter(new EvenementFermeturePorteCabine(this.date + Constantes.tempsPourFermerLesPortes));

        assert cabine.porteOuverte;


        cabine.faireDescendreCeuxQuiVeulent();
        if (etage.passagersInteresses(cabine.status()))
            notYetImplemented();

        notYetImplemented();
    }

}
