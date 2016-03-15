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
        assert !cabine.porteOuverte;

        cabine.etage = this.etage;
        boolean ouvertureNecessaire = false;

        if (etage == immeuble.etageLePlusBas())
            cabine.changerStatus('^');
        else if (etage == immeuble.etageLePlusHaut())
            cabine.changerStatus('v');

        if (cabine.vaSeVider(etage) && cabine.nbPassagers() > 0 && !cabine.qqunDansLaDirection(cabine.status()) && cabine.qqunDansLaDirection(cabine.statusContraire()))
            cabine.changerStatus(cabine.statusContraire());

        if (cabine.passagersQuiDescendentACetEtage().length != 0)
            ouvertureNecessaire = true;
        else if (!cabine.aUneDestination() && etage.auMoinsUnPassager()) {
            ouvertureNecessaire = true;
            cabine.changerStatus(etage.regarderDirectionPremierPassager());
        } else if (etage.auMoinsUnPassagerInteresse(cabine.status()))
            ouvertureNecessaire = true;

        if (ouvertureNecessaire)
            echeancier.ajouter(new EvenementOuverturePorteCabine(this.date + Constantes.tempsPourOuvrirLesPortes));
        else {
            this.date += Constantes.tempsPourBougerLaCabineDUnEtage;
            if (cabine.status() == 'v') {
                this.etage = this.etage.plus_bas;
                echeancier.ajouter(this);
            } else if (cabine.status() == '^') {
                this.etage = this.etage.plus_haut;
                echeancier.ajouter(this);
            }
        }
    }
}