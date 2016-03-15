import java.util.ArrayList;

public class Etage extends Constantes {

    public Etage plus_haut; // ou null sinon
    public Etage plus_bas; // ou null sinon
    private int numero; // de l'Etage pour l'usager
    private LoiDePoisson poissonFrequenceArrivee; // dans l'Etage

    private ArrayList<Passager> listePassagersEtage = new ArrayList<>();

    public Etage(Etage pb, int n, int fa) {
        plus_bas = pb;
        numero = n;
        int germe = n << 2;
        if (germe <= 0)
            germe = -germe + 1;
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

    public boolean auMoinsUnPassagerInteresse(char status) {
        boolean auMoinsUnInteresse = false;
        if (this.isModeParfait()) {
            switch (status) {
                case 'v':
                    for (int i = this.listePassagersEtage.size() - 1; i >= 0 && !auMoinsUnInteresse; i--) {
                        if (this.listePassagersEtage.get(i).etageDestination().numero() < this.numero())
                            auMoinsUnInteresse = true;
                    }
                    break;
                case '^':
                    for (int i = this.listePassagersEtage.size() - 1; i >= 0 && !auMoinsUnInteresse; i--) {
                        if (this.listePassagersEtage.get(i).etageDestination().numero() > this.numero())
                            auMoinsUnInteresse = true;
                    }
                    break;
            }
        } else {
            return this.auMoinsUnPassager();
        }
        return auMoinsUnInteresse;
    }

    public boolean auMoinsUnPassager() {
        return (this.listePassagersEtage.size() != 0);
    }


    public int ajouterTousLesPassagersInteresses(Cabine cabine) {
        assert cabine != null;
        assert cabine.etage == this;

        ArrayList<Passager> passagersASupprimer = new ArrayList<>();

        if (this.isModeParfait()) {
            for (Passager passager : this.listePassagersEtage) {
                if (cabine.status() == 'v' && passager.etageDestination().numero() < this.numero() && cabine.ajouterPassagerSiPossible(passager)) {
                    passagersASupprimer.add(passager);
                } else if (cabine.status() == '^' && passager.etageDestination().numero() > this.numero() && cabine.ajouterPassagerSiPossible(passager)) {
                    passagersASupprimer.add(passager);
                }
            }
        } else {
            for (Passager passager : this.listePassagersEtage) {
                if (cabine.ajouterPassagerSiPossible(passager))
                    passagersASupprimer.add(passager);
            }
        }
        this.supprimerPassagers(passagersASupprimer);
        return passagersASupprimer.size();
    }

    private void supprimerPassagers(ArrayList<Passager> passagersASupprimer) {
        boolean supprime;
        for (int j = passagersASupprimer.size() - 1; j >= 0; j--) {
            supprime = false;
            for (int i = this.listePassagersEtage.size() - 1; i >= 0 && !supprime; i--) {
                if (this.listePassagersEtage.get(i) == passagersASupprimer.get(j)) {
                    this.listePassagersEtage.remove(i);
                    supprime = true;
                }
            }
        }
    }

    public char regarderDirectionPremierPassager() {
        return this.listePassagersEtage.get(0).sens();
    }
}