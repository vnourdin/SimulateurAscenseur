public class EvenementPassageCabinePalier extends Evenement {

    private Etage etage;

    public EvenementPassageCabinePalier(long d, Etage e) {
        super(d);
        assert e != null;
        etage = e;
    }

    public void afficheDetails(Immeuble immeuble) {
        System.out.print("PCP ");
        System.out.print(etage.numero());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        cabine.etage = this.etage;
        assert !cabine.porteOuverte;
        boolean ouvertureNecessaire = false;
        if (cabine.vaOuvrirLesPortesA(etage) && cabine.nbPassagers() == 1 && !cabine.qqunDansLaDirection(cabine.status()) && cabine.qqunDansLaDirection(cabine.statusContraire())) {
            cabine.changerStatus(cabine.statusContraire());
        }
        if (cabine.passagersQuiDescendentACetEtage().length != 0)
            ouvertureNecessaire = true;
        else if (etage.passagersInteresses(cabine.status()))
            ouvertureNecessaire = true;
        else if (!cabine.aUneDestination() && etage.auMoinsUnPassagers()) {
            ouvertureNecessaire = true;
            cabine.changerStatus(etage.regarderDirectionPremierPassager());
        }

        if (ouvertureNecessaire)
            echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + Constantes.tempsPourOuvrirLesPortes));

        else {
            if (etage == immeuble.etageLePlusBas())
                cabine.changerStatus('^');
            if (etage == immeuble.etageLePlusHaut())
                cabine.changerStatus('v');

            if (cabine.status() == 'v') {
                echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, etage.plus_bas));
            } else if (cabine.status() == '^') {
                echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, etage.plus_haut));
            }
        }
    }
}