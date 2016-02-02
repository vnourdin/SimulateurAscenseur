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
        assert !cabine.porteOuverte;
        immeuble.etage(this.etage.numero());
        if (cabine.passagersQuiDescendentACetEtage().length != 0) {
            notYetImplemented();
        }
        notYetImplemented();
    }
}
