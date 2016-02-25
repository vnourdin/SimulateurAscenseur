public class Cabine extends Constantes {

    public Etage etage; // actuel

    public boolean porteOuverte;

    private char status; // '-' ou 'v' ou '^'

    private Passager[] tableauPassager;

    public Cabine(Etage e) {
        assert e != null;
        etage = e;
        tableauPassager = new Passager[nombreDePlacesDansLaCabine];
        porteOuverte = false;
        status = 'v';
    }

    public void afficheLaSituation() {
        System.out.print("Contenu de la cabine: ");
        for (int i = tableauPassager.length - 1; i >= 0; i--) {
            Passager p = tableauPassager[i];
            if (p != null) {
                p.affiche();
                System.out.print(' ');
            }
        }
        assert (status == 'v') || (status == '^') || (status == '-');
        System.out.println("\nStatus de la cabine: " + status);
    }

    public char status() {
        assert (status == 'v') || (status == '^') || (status == '-');
        return status;
    }

    public void changerStatus(char s) {
        assert (s == 'v') || (s == '^') || (s == '-');
        status = s;
    }

    private boolean estVide() {
        for (int i = 0; i < nombreDePlacesDansLaCabine; i++) {
            if (this.tableauPassager[i] != null)
                return false;
        }
        return true;
    }

    public Passager[] passagersQuiDescendentACetEtage() {

        if (this.estVide()) return new Passager[0];

        Passager[] tmp = new Passager[nombreDePlacesDansLaCabine];
        int i = 0;
        for (Passager p : this.tableauPassager) {
            if (p != null && p.etageDestination() == this.etage) {
                tmp[i] = p;
                i++;
            }
        }

        Passager[] passagersQuidescendent = new Passager[i];
        for (int j = 0; j < i; j++)
            passagersQuidescendent[j] = tmp[j];

        return passagersQuidescendent;
    }

    public boolean aUneDestination() {
        return (!this.estVide() || this.qqunDansLaDirection(this.status));
    }

    public boolean qqunDansLaDirection(char direction) {
        assert (direction != '-');

        Etage etg;
        if (direction == 'v') etg = this.etage.plus_bas;
        else etg = etage.plus_haut;

        while (etg != null) {
            if (etg.passagersInteresses('v') || etg.passagersInteresses('^'))
                return true;

            if (direction == 'v') etg = etg.plus_bas;
            else if (direction == '^') etg = etg.plus_haut;
        }

        return false;
    }

    public int nbPlaceDispo() {
        int tmp = 0;
        for (int i = 0; i < this.tableauPassager.length; i++) {
            if (this.tableauPassager[i] == null) {
                tmp++;
            }
        }
        return tmp;
    }

    public void ajouterPassager(Passager passager) {
        assert this.nbPlaceDispo() >= 1;

        boolean arret = false;
        int i;

        for (i = 0; i < this.tableauPassager.length && !arret; i++) {
            if (this.tableauPassager[i] == null) {
                this.tableauPassager[i] = passager;
                arret = true;
            }
        }

        assert this.tableauPassager[i - 1] == passager;
    }

    public int faireDescendreCeuxQuiVeulent(Immeuble immeuble, long date) {
        int nbPDescendus = 0;
        for (int i = 0; i < this.tableauPassager.length; i++) {
            if (this.tableauPassager[i] != null && this.tableauPassager[i].etageDestination() == this.etage) {
                nbPDescendus++;
                immeuble.nombreTotalDesPassagersSortis++;
                immeuble.cumulDesTempsDeTransport += (date - tableauPassager[i].dateDepart());
                this.tableauPassager[i] = null;
            }
        }

        return nbPDescendus;
    }

    public int faireMonterCeuxQuiVeulent() {
        return etage.ajouterPassagerInteresses(this);
    }
}
