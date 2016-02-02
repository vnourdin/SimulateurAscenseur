public class Passager {

    private long numeroDeCreation;
    
    private long dateDepart;
    
    private Etage etageDepart;
    
    private Etage etageDestination;
    
    public long dateDepart() {
        return this.dateDepart;
    }
    
    public Etage etageDepart() {
        return this.etageDepart;
    }
    
    public int numeroDepart() {
        return this.etageDepart.numero();
    }
    
    public Etage etageDestination() {
        return this.etageDestination;
    }
    
    public int numeroDestination() {
        return this.etageDestination.numero();
    }
    
    public Passager(long dateDeDepart, Etage etadep, Immeuble immeuble) {
        Etage niveauDuSol = immeuble.niveauDuSol();
        int nbEtages = immeuble.nbEtages();
	assert etadep != null;
        etageDepart = etadep;
        dateDepart = dateDeDepart;
        compteurGlobalDeCreationDesPassagers++;
        numeroDeCreation = compteurGlobalDeCreationDesPassagers;
        if (etageDepart == niveauDuSol) {
            etageDestination = niveauDuSol;
            while (etageDestination == niveauDuSol) {
                int auPif = randomGenerator.intSuivant(nbEtages);
                etageDestination = immeuble.etage(auPif + immeuble.etageLePlusBas().numero() - 1);
            }
        } else {
            etageDestination = niveauDuSol;
        }
    }
    
    private static int compteurGlobalDeCreationDesPassagers = 0;
    
    private static final PressRandomNumberGenerator randomGenerator = new PressRandomNumberGenerator(34);

    public char sens () {
	return (etageDestination.numero() > etageDepart.numero() ? '^' : 'v');
    }

    public void affiche() {
	int depa = etageDepart.numero();
	int dest = etageDestination.numero();

	System.out.print('#');
	System.out.print(numeroDeCreation);
	System.out.print(':');
	System.out.print(depa);
	System.out.print(dest > depa ? '^' : 'v');
	System.out.print(dest);
	System.out.print(':');
	System.out.print(dateDepart);
    }
}
