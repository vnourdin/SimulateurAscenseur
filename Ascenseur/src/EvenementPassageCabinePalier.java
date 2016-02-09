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
        if (!cabine.aUneDestination() && etage.passagers())
            ouvertureNecessaire = true;
        if (cabine.passagersQuiDescendentACetEtage().length != 0)
            ouvertureNecessaire = true;
        if (etage.passagersInteresses(cabine.status()))
            ouvertureNecessaire = false;

        if (ouvertureNecessaire)
            notYetImplemented();
        else {
            if (etage == immeuble.etageLePlusBas())
                cabine.changerStatus('^');
            if (etage == immeuble.etageLePlusHaut())
                cabine.changerStatus('v');

            if (cabine.status() == 'v')
                echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, etage.plus_bas));
            if (cabine.status() == '^')
                echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, etage.plus_haut));
        }
    }
}
