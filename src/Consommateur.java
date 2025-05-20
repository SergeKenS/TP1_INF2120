import java.util.ArrayList;

public class Consommateur extends Utilisateur {

    private ArrayList<Produit> achats;

    public Consommateur(String pseudo, String motPasse, String courriel) {
        super(pseudo, motPasse, courriel);
        this.achats = new ArrayList<>(0);
    }

    public Consommateur(Consommateur consommateur) {
        super(consommateur);
        this.achats = (ArrayList<Produit>) consommateur.achats.clone();
    }

    @Override
    public ArrayList<String> compilerProfil() {
        ArrayList<String> profil = new ArrayList<>(0);
        String categorie;
        if(!achats.isEmpty()) {
            for (int i = 0; i < achats.size(); i++) {
                categorie = achats.get(i).getCategorie();
                if(!profil.contains(categorie))
                    profil.add(categorie);
            }
        }
        return profil;
    }

    /**
     * Cette méthode permet à ce consommateur d’évaluer un fournisseur (celui reçu en paramètre)
     * @param fournisseur : Le fournisseur à évaluer par cet utilisateur
     * @param eval        : L’évaluation donnée par cet utilisateur à l’utilisateur reçu en paramètre (le fournisseur)
     *                    (entre 1 et 5)
     */
    @Override
    public void evaluer(Utilisateur fournisseur, int eval) {
        boolean trouver = false;
        if (fournisseur == null)
            throw new NullPointerException();

        if (fournisseur.getClass().equals(Fournisseur.class))
            throw new ClassCastException();

        for (int i = 0; i < this.achats.size(); i++) {
            if (this.achats.get(i).getIdFournisseur() == fournisseur.getId()) {
                trouver = true;
            }
        }
        if (!trouver) {
            throw new RuntimeException("Erreur, ce consommateur ne peut pas evaluer ce fournisseur.");
        }

        fournisseur.ajouterEvaluation(eval);
    }

    //METHODES D'INSTANCE PUBLIC

    public ArrayList<Produit> getAchats() {
        return achats;
    }

    public void acheter (Produit produit, int qteAchetee) throws ExceptionProduitInvalide {
        if (produit == null) {
            throw new ExceptionProduitInvalide();
        } else if (produit.getIdFournisseur() == 0) {
            throw new RuntimeException("Erreur, ce produit n'est vendu par aucun fournisseur.");
        } else if (qteAchetee <= 0 || qteAchetee > produit.getQuantite()) {
            throw new RuntimeException("Erreur, quantite invalide.");
        } else {
            Produit produit1 = new Produit(produit);
            produit1.setQuantite(qteAchetee);
            this.achats.add(produit1);
        }
    }

    public ArrayList<Integer> fournisseurs () {
        ArrayList<Integer> idFournisseurs = new ArrayList<>(0);
        int id;
        if(!achats.isEmpty()) {
            for (int i = 0; i < achats.size(); i++) {
                id = achats.get(i).getIdFournisseur();
                if(!idFournisseurs.contains(id)) {
                    idFournisseurs.add(id);
                }
            }
        }
        return idFournisseurs;
    }

    /**
     * Retourne une representation sous forme de chaine de caracteres
     * de ce consommateur.
     * @return une representation sous forme de chaine de caracteres
     * de ce consommateur.
     */
    @Override
    public String toString() {
        return super.toString() + " - " + achats.size();
    }

}
