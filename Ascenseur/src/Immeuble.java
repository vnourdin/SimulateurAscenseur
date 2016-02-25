public class Immeuble extends Constantes {

    private final int nbEtages = 8;
    public Cabine cabine; // de l'ascenseur.
    public long cumulDesTempsDeTransport = 0;
    public long nombreTotalDesPassagersSortis = 0;
    private Etage etageLePlusHaut;
    private Etage niveauDuSol; // le niveau 0.
    private Etage etageLePlusBas;

    public Immeuble(Echeancier echeancier) {
        Etage e = null;
        int c = 0;
        final int freqsol = 41;
        final int freqnor = freqsol * (nbEtages - 1);
        int n = -2;
        etageLePlusBas = new Etage(null, n++, freqnor);
        c++;
        Etage p = etageLePlusBas;
        while (c < nbEtages) {
            if (n == 0) {
                e = new Etage(p, n++, freqsol);
                niveauDuSol = e;
            } else {
                e = new Etage(p, n++, freqnor);
            }
            ;
            c++;
            p = e;
        }
        etageLePlusHaut = e;
        p = etageLePlusHaut;
        e = p.plus_bas;
        while (e != null) {
            e.plus_haut = p;
            p = e;
            e = p.plus_bas;
        }
        ;
        e = etageLePlusBas;
        while (e != null) {
            long date = e.arriveeSuivante();
            echeancier.ajouter(new EvenementArriveePassagerPalier(date, e));
            e = e.plus_haut;
        }
        e = niveauDuSol.plus_haut.plus_haut;
        cabine = new Cabine(e);
        echeancier.ajouter(new EvenementPassageCabinePalier(tempsPourBougerLaCabineDUnEtage * 2, e.plus_bas));
    }

    public Etage etageLePlusBas() {
        Etage res = etageLePlusBas;
        assert res.numero() < etageLePlusHaut.numero();
        return res;
    }

    public Etage etageLePlusHaut() {
        Etage res = etageLePlusHaut;
        assert res.numero() > etageLePlusBas.numero();
        return res;
    }

    public Etage niveauDuSol() {
        Etage res = niveauDuSol;
        assert etageLePlusHaut.numero() >= res.numero();
        assert etageLePlusBas.numero() <= res.numero();
        return res;
    }

    public void afficheLaSituation() {
        System.out.print("Immeuble (mode ");
        if (isModeParfait()) {
            System.out.print("parfait");
        } else {
            System.out.print("infernal");
        }
        System.out.println("):");
        Etage e = etageLePlusHaut;
        while (e != null) {
            e.afficheLaSituation(this);
            e = e.plus_bas;
        }
        cabine.afficheLaSituation();
        System.out.println("Cumul des temps de transport: " + cumulDesTempsDeTransport);
        System.out.println("Nombre total des passagers sortis: " + nombreTotalDesPassagersSortis);
        System.out.println("Ratio cumul temps / nb passagers : " +
                (nombreTotalDesPassagersSortis == 0 ? 0 : (cumulDesTempsDeTransport / nombreTotalDesPassagersSortis)));
    }

    public Etage etage(int i) {
        assert etageLePlusBas().numero() <= i : "ERREUR trop haut";
        assert etageLePlusHaut().numero() >= i : "ERREUR trop bas";
        Etage res = etageLePlusBas;
        while (i != res.numero()) {
            assert res.numero() + 1 == res.plus_haut.numero();
            res = res.plus_haut;
        }
        assert res.numero() == i;
        return res;
    }

    public int nbEtages() {
        int res = nbEtages;
        assert res == (etageLePlusHaut().numero() - etageLePlusBas().numero() + 1);
        return res;
    }
}
