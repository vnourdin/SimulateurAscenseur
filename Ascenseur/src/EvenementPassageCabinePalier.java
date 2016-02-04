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
        if (cabine.passagersQuiDescendentACetEtage().length != 0)
            ouvertureNecessaire = true;
        if (etage.passagersInteresses(cabine.status()))
            ouvertureNecessaire = true;

        if (ouvertureNecessaire)
            notYetImplemented();
        else {
            if (cabine.status() == 'v')
                echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, etage.plus_bas));
            if (cabine.status() == '^')
                echeancier.ajouter(new EvenementPassageCabinePalier(this.date + Constantes.tempsPourBougerLaCabineDUnEtage, etage.plus_haut));
        }
    }
}
