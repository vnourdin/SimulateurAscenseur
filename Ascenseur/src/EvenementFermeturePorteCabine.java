public class EvenementFermeturePorteCabine extends Evenement {

    public EvenementFermeturePorteCabine(long d) {
        super(d);
    }

    public void afficheDetails(Immeuble immeuble) {
        System.out.print("FPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        assert cabine.porteOuverte;
        cabine.porteOuverte = false;

        if (cabine.status() == 'v')
            echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, cabine.etage.plus_bas));
        else if (cabine.status() == '^')
            echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, cabine.etage.plus_haut));
    }
}