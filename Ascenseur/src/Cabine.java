import java.util.ArrayList;

public class Cabine extends Constantes {

    public Etage etage;
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
        for (Passager p : this.tableauPassager) {
            if (p != null) {
                p.affiche();
                System.out.print(' ');
            }
        }
        System.out.println("\nStatus de la cabine: " + this.status());
    }

    public char status() {
        assert (status == 'v') || (status == '^') || (status == '-');
        return status;
    }

    public char statusContraire() {
        assert (status == 'v') || (status == '^') || (status == '-');
        if (this.status == 'v')
            return '^';
        else
            return 'v';
    }

    public void changerStatus(char s) {
        assert (s == 'v') || (s == '^') || (s == '-');
        status = s;
    }

    public Passager[] passagersQuiDescendentACetEtage() {
        ArrayList<Passager> ceuxQuiDescendent = new ArrayList<>();
        for (Passager p : this.tableauPassager) {
            if (p != null && p.etageDestination().numero() == this.etage.numero()) {
                ceuxQuiDescendent.add(p);
            }
        }

        Passager[] ceuxQuiDescendent2 = new Passager[ceuxQuiDescendent.size()];
        for (int i = 0; i < ceuxQuiDescendent.size(); i++) {
            ceuxQuiDescendent2[i] = ceuxQuiDescendent.get(i);
        }

        return ceuxQuiDescendent2;
    }

    public boolean aUneDestination() {
        return (!this.estVide() || this.qqunDansLaDirection(this.status));
    }

    private boolean estVide() {
        for (Passager p : this.tableauPassager) {
            if (p != null)
                return false;
        }
        return true;
    }

    public boolean qqunDansLaDirection(char direction) {
        assert (direction == 'v' || direction == '^');

        Etage etg = (direction == 'v') ? this.etage.plus_bas : this.etage.plus_haut;

        while (etg != null) {
            if (etg.auMoinsUnPassagerInteresse('^') || etg.auMoinsUnPassagerInteresse('v'))
                return true;
            etg = (direction == 'v') ? etg.plus_bas : etg.plus_haut;
        }
        return false;
    }

    public boolean ajouterPassagerSiPossible(Passager passager) {

        boolean ajoute = false;
        int i;

        for (i = this.tableauPassager.length - 1; i >= 0 && !ajoute; i--) {
            if (this.tableauPassager[i] == null) {
                this.tableauPassager[i] = passager;
                ajoute = true;
            }
        }
        if (ajoute)
            assert this.tableauPassager[i + 1] == passager;

        return ajoute;
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
        return etage.ajouterTousLesPassagersInteresses(this);
    }

    public boolean vaSeVider(Etage etage) {
        int nbPassagers = 0;
        int nbPassagersQuiDescendent = 0;

        for (Passager p : this.tableauPassager) {
            if (p != null) {
                nbPassagers++;
                if (p.etageDestination().numero() == etage.numero())
                    nbPassagersQuiDescendent++;
            }
        }

        return (nbPassagers == nbPassagersQuiDescendent);
    }

    public int nbPassagers() {
        int nbPassagers = 0;
        for (Passager passager : this.tableauPassager) {
            if (passager != null) {
                nbPassagers++;
            }
        }
        return nbPassagers;
    }
}