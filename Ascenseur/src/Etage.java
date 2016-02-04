import java.util.ArrayList;

public class Etage extends Constantes {

    private int numero; // de l'Etage pour l'usager

    public Etage plus_haut; // ou null sinon

    public Etage plus_bas; // ou null sinon

    private LoiDePoisson poissonFrequenceArrivee; // dans l'Etage

    private ArrayList<Passager> listePassagersEtage = new ArrayList<Passager>();

    public Etage(Etage pb, int n, int fa) {
        plus_bas = pb;
        numero = n;
        int germe = n << 2;
        if (germe <= 0) {
            germe = -germe + 1;
        }
        poissonFrequenceArrivee = new LoiDePoisson(germe, fa);
    }

    public void afficheLaSituation(Immeuble immeuble) {
        if (numero() >= 0) {
            System.out.print(' ');
        }
        System.out.print(numero());
        if (this == immeuble.cabine.etage) {
            System.out.print(" C ");
            if (immeuble.cabine.porteOuverte) {
                System.out.print("[  ]: ");
            } else {
                System.out.print(" [] : ");
            }
        } else {
            System.out.print("   ");
            System.out.print(" [] : ");
        }
        int i = 0;
        boolean stop = listePassagersEtage.size() == 0;
        while (!stop) {
            if (i >= listePassagersEtage.size()) {
                stop = true;
            } else if (i > 6) {
                stop = true;
                System.out.print("...(");
                System.out.print(listePassagersEtage.size());
                System.out.print(')');
            } else {
                listePassagersEtage.get(i).affiche();
                i++;
                if (i < listePassagersEtage.size()) {
                    System.out.print(", ");
                }
            }
        }
        System.out.print('\n');
    }

    public int numero() {
        return this.numero;
    }

    public void ajouter(Passager passager) {
        assert passager != null;
        listePassagersEtage.add(passager);
    }

    public long arriveeSuivante() {
        return poissonFrequenceArrivee.suivant();
    }

    public boolean passagersInteresses(char status) {
        boolean auMoinsUnInteresse = false;
        switch (status) {
            case 'v':
                for (Passager passager : this.listePassagersEtage) {
                    if (passager.etageDestination().numero() < this.numero())
                        auMoinsUnInteresse = true;
                }
                break;
            case '^':
                for (Passager passager : this.listePassagersEtage) {
                    if (passager.etageDestination().numero() > this.numero())
                        auMoinsUnInteresse = true;
                }
                break;
        }
        return auMoinsUnInteresse;
    }
}
