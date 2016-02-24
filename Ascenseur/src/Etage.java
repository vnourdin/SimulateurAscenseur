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

    public boolean auMoinsUnPassagers() {
        return (this.listePassagersEtage.size() != 0);
    }


    public int ajouterPassagerInteresses(Cabine cabine) {
        assert cabine.etage == this;
        ArrayList<Passager> passagersASupprimer = new ArrayList<Passager>();
        for (Passager passager : this.listePassagersEtage) {
            if (cabine.status() == 'v' && passager.etageDestination().numero() < this.numero() && cabine.nbPlaceDispo() >= 1) {
                cabine.ajouterPassager(passager);
                passagersASupprimer.add(passager);
            } else if (cabine.status() == '^' && passager.etageDestination().numero() > this.numero() && cabine.nbPlaceDispo() >= 1) {
                cabine.ajouterPassager(passager);
                passagersASupprimer.add(passager);
            }
        }
        this.supprimerPassagers(passagersASupprimer);
        return passagersASupprimer.size();
    }

    private void supprimerPassagers(ArrayList<Passager> passagersASupprimer) {
        for (int j = 0; j < passagersASupprimer.size(); j++) {
            for (int i = 0; i < this.listePassagersEtage.size(); i++) {
                if (this.listePassagersEtage.get(i) == passagersASupprimer.get(j)) {
                    this.listePassagersEtage.remove(i);
                }
            }
        }
    }
}
