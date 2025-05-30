import java.util.ArrayList;
//NB revoir les boucle for pour voir si c'est possible dajouter les methode de la classe ArrayList'
public class Fournisseur extends Utilisateur {

    public ArrayList<Produit> produits;

    public Fournisseur(String pseudo, String motPasse, String courriel) {
        super(pseudo, motPasse, courriel);
        this.produits = new ArrayList<>(0);
    }


    public Fournisseur(Fournisseur fournisseur) {
        super(fournisseur);
        this.produits = (ArrayList<Produit>) fournisseur.produits.clone();
    }

    /**
     * Permet de recenser toutes les catégories des produits vendu par ce fournisseur.
     * @return retourne une liste contenant toutes les catégories différentes recensées sur les produits
     * vendus par ce fournisseur.
     */
    @Override
    public ArrayList<String> compilerProfil() {
        ArrayList<String> profil = new ArrayList<>(0);

        for (Produit produit : produits) {
            if (produit.getQuantite() > 0) {
                String categorie = produit.getCategorie();
                if (!profil.contains(categorie)) {
                    profil.add(categorie);
                }
            }
        }
        return profil;
    }

    /**
     * permet à ce fournisseur d’évaluer un consommateur (celui reçu en paramètre). Une évaluation
     * valide est une note comprise entre 1 et 5.
     * @param consommateur : L’utilisateur à évaluer par cet utilisateur
     * @param eval        : L’évaluation donnée par le fournisseur à l’utilisateur reçu en paramètre (entre 1 et 5)
     */
    @Override
    public void evaluer(Utilisateur consommateur, int eval) {

        //si le consommateur passé en paramètre est null.
        if (consommateur == null) {
            throw new NullPointerException();
        }
        //si le paramètre consommateur n’est pas de type Consommateur.
        if (!consommateur.getClass().equals(Utilisateur.class)) {
            throw new ClassCastException();
        }
        //si le consommateur passé en paramètre n’a jamais acheté de produit(s) de ce fournisseur.
        for (Produit produit : this.produits) {
            if (consommateur.compilerProfil().contains(produit.getCategorie())) {
                throw new RuntimeException("Erreur, ce fournisseur ne peut pas evaluer ce consommateur.");
            }
        }
        if (eval < 1 || eval > 5) {
            throw new RuntimeException("Erreur, l'evaluation doit etre un nombre entier entre 1 et 5 inclusivement.");
        }
        consommateur.ajouterEvaluation(eval);
    }

    public ArrayList<Produit> getProduits() {
        return produits;
    }

    /**
     * Cette méthode permet d’ajouter un nouveau produit à vendre dans la liste des produits de ce fournisseur, si
     * celui-ci n’y est pas déjà.
     *
     * @param produit     : Le produit à ajouter à la liste des produits vendus par ce fournisseur
     * @param qteEnStock  : La quantité en stock initiale du produit ajouté
     * @param prixDeVente : prix de vente du produit ajouter
     */
    public void ajouterNouveauProduit(Produit produit, int qteEnStock, double prixDeVente) {
        Produit copieProduit;
        if (produit == null) {
            throw new ExceptionProduitInvalide();
        }
        if (qteEnStock <= 0) {
            throw new RuntimeException("Erreur, quantite invalide.");
        }
        if (prixDeVente <= 0) {
            throw new RuntimeException("Erreur, prix invalide.");
        }
        if (this.produits.contains(produit)) {
            throw new RuntimeException("Erreur, ce produit ne peut etre ajoute car il existe deja.");
        }
        copieProduit = new Produit(produit);
        copieProduit.setIdFournisseur(this.getId());
        copieProduit.setQuantite(qteEnStock);
        copieProduit.setPrix(prixDeVente);
        this.produits.add(copieProduit);
    }

    /**
     * Cette méthode recherche, dans la liste des produits vendus par ce fournisseur, le produit ayant le code donné
     * @param code : Le code du produit qu’on veut obtenir
     * @return : Elle retourne le produit s’il a été trouvé, sinon elle retourne la valeur null.
     */
    public Produit obtenirProduit (int code) {
        Produit produit = null;
        for (int i = 0; i < produits.size(); i++) {
            if (produits.get(i).getCode() == code) {
                produit = produits.get(i);
            }
        }
        return produit;
    }

    /**
     * Cette méthode permet de diminuer la quantité en stock d’un produit dont on a vendu une certaine quantité.
     * @param codeProduit : Le code du produit dont on veut diminuer la quantité en stock
     * @param qteVendue : La quantité vendue qu’on veut soustraire à la quantité en stock du produit
     * donné
     */
    public void vendre (int codeProduit, int qteVendue) {
        //Produit correspondant au code passer en parametre
        Produit produit = obtenirProduit(codeProduit);

        if (obtenirProduit(codeProduit) == null) {
            throw new RuntimeException("Erreur, ce produit n'est pas vendu par ce fournisseur.");
        }
        if(qteVendue <= 0 || qteVendue > obtenirProduit(codeProduit).getQuantite()) {
            throw new RuntimeException("Erreur, quantite invalide.");
        }
        produit.setQuantite(produit.getQuantite() - qteVendue);
    }

    /**
     * Retourne une representation sous forme de chaine de caracteres de ce
     * Fournisseur.
     * @return une representation sous forme de chaine de caracteres de ce
     * Fournisseur.
     */
    public String toString() {
        return super.toString() + " - " + produits.size();
    }
}
