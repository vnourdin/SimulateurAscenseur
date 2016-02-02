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

    public Passager[] getTableauPassager() {
        return tableauPassager;
    }
}
