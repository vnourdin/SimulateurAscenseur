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
        assert ! cabine.porteOuverte;
        immeuble.etage(this.etage.numero());
        Passager[] passager = cabine.getTableauPassager();
        if(passager.length != 0){
            notYetImplemented();
        }else{
            notYetImplemented();
        }
	    //1notYetImplemented();
    }
}
