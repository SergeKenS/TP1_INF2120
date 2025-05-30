import java.util.ArrayList;

/**
 * cecoja iuanwdunaiu uiauia uiauifa.
 * baysbcas.
 * uhsauiaiusnans.
 */
public class Amizone {

    public final static String MSG_ERR_UTILIS_NULL = "Erreur, utilisateur null.";
    public final static String MSG_ERR_FOURN_AUCUN_PRODUIT =
            "Erreur, ce fournisseur ne vend aucun produit.";
    public final static String MSG_ERR_UTILIS_EXISTANT =
            "Erreur, cet utilisateur existe deja.";

    /**
     * liste des utilisateurs inscrits sur le site Amizone.
     */
    private ArrayList<Utilisateur> utilisateurs;

    public Amizone () {
        this.utilisateurs = new ArrayList<Utilisateur>(0);
    }

    /**
     * @return retourne le nombre d’utilisateurs inscrits sur le site Amizone.
     */
    public int getNbrUtilisateurs () {
        return utilisateurs.size();
    }

    /**
     * ajoute l’utilisateur donné à la liste des utilisateurs d’Amizone. Si l’utilisateur donné est un fournisseur,
     * celui-ci doit vendre au moins un produit dont la quantité (en stock) est plus grande que 0.
     * @param utilisateur : soit un fournisseur soit un consommateur.
     * @throws Exception
     */
    public void inscrireUtilisateur(Utilisateur utilisateur) throws Exception {
        ArrayList<Produit> produits;
        // Si l'utilisateur passer en parametre est null
        if (utilisateur == null) {
            throw new Exception(MSG_ERR_UTILIS_NULL);
        }
        if (utilisateur.getClass().getSimpleName().equals("Fournisseur")) {
            produits = ((Fournisseur)utilisateur).getProduits();
            if (FournisseurValide(produits)) {
                utilisateurs.add(utilisateur);
            } else {
                throw new Exception(MSG_ERR_FOURN_AUCUN_PRODUIT);
            }
        }
        if(utilisateurs.contains(utilisateur)) {
            throw new Exception(MSG_ERR_UTILIS_EXISTANT);
        }
        if(utilisateur.getClass().getSimpleName().equals("Consommateur")) {
            utilisateurs.add(utilisateur);
        }
    }


    /**
     * Permet de determiner si la liste de produit du fournisseur contient un produit dont la quantite est > 0.
     * @param produits : un tableau contenant la liste des produits vendu par ce fournisseur
     * @return retourne vrai s'il ya au moins un produit dont la quantite > 0 et faux sinon
     */
    public boolean FournisseurValide (ArrayList<Produit> produits) {
        boolean valide = false;
        for (Produit produit : produits) {
            if (produit.getQuantite() > 0) {
                valide = true;
            }
        }
        return valide;
    }

    /**
     * permet de recommander à un consommateur des fournisseurs potentiellement intéressants pour ce consommateur
     * ou bien de recommander à un fournisseur des consommateurs potentiellement intéressés par les produits que
     * vend ce fournisseur
     * @param utilisateur
     * @return
     * @throws Exception
     */
    public ArrayList<Utilisateur> recommanderUtilisateurs(Utilisateur utilisateur) throws Exception {
        ArrayList<String> profilConsommateur;
        ArrayList<String> profilFournisseur;
        ArrayList<Utilisateur> recommandeUtilisateur = new ArrayList<>(0);
        if(utilisateur == null) {
            throw new Exception(MSG_ERR_UTILIS_NULL);
        }
        if (utilisateur instanceof Consommateur) {
            profilConsommateur = ((Consommateur)utilisateur).compilerProfil();
                for (Utilisateur fournisseur : utilisateurs) {
                    if (fournisseur instanceof Fournisseur) {
                        if (estCompatible (profilConsommateur, fournisseur.compilerProfil())) {
                            if (!recommandeUtilisateur.contains(fournisseur)) {
                                recommandeUtilisateur.add(fournisseur);
                            }
                        }
                    }
                }
        }
        if (utilisateur instanceof Fournisseur) {
            profilFournisseur = ((Fournisseur)utilisateur).compilerProfil();
            for (Utilisateur consommateur : utilisateurs) {
                if (consommateur instanceof Consommateur) {
                    if (estCompatible (profilFournisseur, consommateur.compilerProfil())) {
                        if (!recommandeUtilisateur.contains(consommateur)) {
                            recommandeUtilisateur.add(consommateur);
                        }
                    }
                }
            }
        }
        return recommandeUtilisateur;
    }

    /**
     * Permet de tester la compatibilite entre deux utilisateur dont l'un est un fournisseur et l'autre un
     * consommateur grace a leur profils compiler contenant les categories des produits vendu ou acheter
     * respectivement par un fournisseur ou un vendeur
     * @param utilisateur : Profil compiler d'un utilisisateur
     * @param utilisateur2 : Profil compiler d'un utilisisateur
     * @return : Retourne true si compatible ou false sinon
     */
    private boolean estCompatible (ArrayList<String> utilisateur, ArrayList<String> utilisateur2) {
        boolean compatible = false;
        for (String categorie1 : utilisateur) {
            for (String categorie2 : utilisateur2) {
                if (categorie1 != null && categorie2 != null
                        && categorie1.toLowerCase().equals(categorie2.toLowerCase()))
                    compatible = true;
            }
        }
        return compatible;
    }

    /**
     *
     * @param fournisseur
     * @param consommateur
     * @return : retourne une liste de tous les produits vendus par le fournisseur donné qui sont potentiellement
     * intéressants pour le consommateur donné.
     * @throws Exception
     */
    public ArrayList<Produit> recommanderProduits (Fournisseur fournisseur, Consommateur consommateur)
            throws Exception {
        if (fournisseur == null || consommateur == null) {
            throw new Exception(MSG_ERR_UTILIS_NULL);
        }
        ArrayList<Produit> resultat = new ArrayList<>();
        ArrayList<String> profilConso = consommateur.compilerProfil();

        for (Produit p : fournisseur.getProduits()) {
            if (p.getQuantite() > 0) {
                for (String cat : profilConso) {
                    if (p.getCategorie().equalsIgnoreCase(cat)) {
                        resultat.add(p);
                        break;
                    }
                }
            }
        }
        return resultat;
    }

    /**
     * Cette méthode permet d’effectuer une transaction achat/vente entre un fournisseur et un consommateur. Elle
     * permet au fournisseur donné de vendre le produit du code donné, de la quantité donnée, au consommateur
     * donné
     * @param fournisseur : celui qui vend le produit au consommateur
     * @param consommateur : celui qui achete un produit au fournisseur
     * @param codeProduit représente le code du vendue par le fournisseur
     * @param quantite : représente la quantité vendue par le fournisseur
     * @throws Exception
     */
    public void effectuerTransaction(Fournisseur fournisseur, Consommateur consommateur, int codeProduit, int quantite)
            throws Exception {
        Produit produit = fournisseur.obtenirProduit(codeProduit);
        if (produit == null) {
            throw new Exception("Erreur, ce produit n'est pas vendu par ce fournisseur.");
        }
        consommateur.acheter(produit, quantite);
        fournisseur.vendre(codeProduit, quantite);
    }


    /**
     * Permet d'effectuer un recherche grace au mot cle dans la description de tous les produits vendu par
     * tous les fournisseurs
     * @param motCle : le mot a rechercher dans la description des produits
     * @return : retourne une liste de tous les produits (parmi les produits vendus par tous les fournisseurs) dont
     * la description contient le mot clé donné
     */
    public ArrayList<Produit> rechercherProduitsParMotCle (String motCle) {
        ArrayList<Produit> result = new ArrayList<>();
        if (motCle == null) {
            return result;
        }
        String lc = motCle.toLowerCase();

        for (Utilisateur u : utilisateurs) {
            if (u instanceof Fournisseur) {
                for (Produit p : ((Fournisseur) u).getProduits()) {
                    if (p.getQuantite() > 0
                            && p.getDescription().toLowerCase().contains(lc)) {
                        result.add(p);
                    }
                }
            }
        }
        return result;
    }


    /**
     * Permet de produire une liste des fournisseurs qui vendent le produit du code donné, et dont la quantité (en
     * stock) est strictement plus grande que 0. Excepter les fournisseurs qui n'ont aucune evaluation
     * @param codeProduit
     * @return : retourne une liste des fournisseurs
     */
    public ArrayList<Fournisseur> rechercherFournisseursParEvaluation (int codeProduit) {
        ArrayList<Fournisseur> result = new ArrayList<>();

        for (Utilisateur u : utilisateurs) {
            if (u instanceof Fournisseur) {
                Fournisseur f = (Fournisseur) u;
                Produit p = f.obtenirProduit(codeProduit);
                if (p != null && p.getQuantite() > 0 && !f.getEvaluations().isEmpty()) {
                    result.add(f);
                }
            }
        }

        result.sort((f1, f2) -> Double.compare(f2.evaluationMoyenne(), f1.evaluationMoyenne()));
        return result;
    }

    /**
     * Retourne true s'il existe au moins une catégorie commune (cas non sensible)
     * entre les deux profils.
     */
    private boolean profilsSimilaires(ArrayList<String> p1, ArrayList<String> p2) {
        for (String c1 : p1) {
            for (String c2 : p2) {
                if (c1.equalsIgnoreCase(c2)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void main (String [] args) {
        System.out.println ("Bonjour");
    }


}
