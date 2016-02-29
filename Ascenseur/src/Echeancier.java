import java.util.LinkedList;

public class Echeancier extends Constantes {
    // La liste triée chronologiquement des événements du simulateur.

    private LinkedList<Evenement> listeEvenements; // Changement de type autorisé.
    // La liste triée des événements.

    public Echeancier() {
        listeEvenements = new LinkedList<>();
    }

    public boolean estVide() {
        return listeEvenements.isEmpty();
    }

    public void ajouter(Evenement e) {
        int pos = 0;
        while (pos < listeEvenements.size()) {
            if (listeEvenements.get(pos).date >= e.date) {
                listeEvenements.add(pos, e);
                return;
            } else {
                pos++;
            }
        }
        listeEvenements.add(pos, e);
    }

    public Evenement retournerEtEnleverPremier() {
        Evenement e = this.listeEvenements.getFirst();
        this.listeEvenements.removeFirst();
        return e;
    }

    public Evenement retournerEtEnleverFPC() {
        for (Evenement event : this.listeEvenements) {
            if (event.getClass() == EvenementFermeturePorteCabine.class) {
                this.listeEvenements.remove(event);
                return event;
            }
        }
        return null;
    }

    public void afficheLaSituation(Immeuble ascenseur) {
        System.out.print("Echeancier = ");
        int index = 0;
        while (index < this.listeEvenements.size()) {
            this.listeEvenements.get(index).affiche(ascenseur);
            index++;
            if (index < this.listeEvenements.size()) {
                System.out.print(',');
            }
        }
        System.out.println("");
    }
}