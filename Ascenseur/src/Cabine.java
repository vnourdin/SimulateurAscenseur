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
            if (p.etageDestination() == this.etage) {
                tmp[i] = p;
                i++;
            }
        }

        Passager[] passagersQuidescendent = new Passager[i];
        for (int j = 0; j < i; j++)
            passagersQuidescendent[j] = tmp[j];

        return passagersQuidescendent;
    }
}
