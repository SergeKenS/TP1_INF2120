
import java.util.Locale;

/**
 * Cette classe modelise un produit consommable.
 * @author melanie lord
 * @version E25
 */
public class Produit {
   
   //-----------------------------------
   // ATTRIBUTS ET CONSTANTES DE CLASSE
   //-----------------------------------
   
   //messages d'erreurs
   public static final String MSG_ERR_ID_FOURN_INVALIDE = "Erreur, idFounisseur invalide";
   public static final String MSG_ERR_CAT_INVALIDE = "Erreur, categorie invalide";
   public static final String MSG_ERR_DESC_INVALIDE = "Erreur, description invalide";
   public static final String MSG_ERR_QTE_INVALIDE = "Erreur, quantite invalide";
   public static final String MSG_ERR_PRIX_INVALIDE = "Erreur, prix invalide";
   
   //Constante de classe listant toutes les categories valides pour un produit.
   public static final String [] CATEGORIES = {
      "ANIMALERIE",
      "AUTOMOBILE",
      "BAGAGES ET SACS",
      "BEBES",
      "ELECTRONIQUE",
      "FOUNITURE BUREAU",
      "JEUX ET JOUETS",
      "JEUX VIDEO",
      "LIVRES",
      "LOGICIELS",
      "MAISON ET CUISINE",
      "MONTRES",
      "OUTILS ET BRICOLAGE",
      "PRODUITS BEAUTE",
      "SANTE ET SOINS PERSONNELS",
      "SPORTS ET PLEIN AIR",
      "TERRASSE ET JARDIN"     
   };
   
   //Variable de classe representant une sequence pour l'assignation d'un       
   //code de produit unique.
   private static int seqCodeProduit = 1;
   
   //-----------------------------------
   // ATTRIBUTS D'INSTANCE
   //-----------------------------------
   
   private int code;           //code produit
   
   private String description; //description du produit
   
   private String categorie;   //la categorie du produit, doit etre une des 
                               //categories presentes dans le tableau CATEGORIES
   
   private int quantite;       //la quantite de ce produit (>= 0)
   
   private double prix;        //le prix du produit (0.0 signifie prix non assigné)
   
   private int idFournisseur;  //le numero d'identification unique du founisseur 
                               //de ce produit (>= 0). La valeur 0 signifie que
                               //ce produit n'a pas de fournisseur assigne.
   
   //-------------------
   // CONSTRUCTEURS
   //-------------------
   
   /**
    * Construit un Produit avec la description et la categorie donnees.
    * Le code du produit est assigne automatiquement (entier >= 1).
    * La quantite, le prix et idFournisseur sont initialises a 0.
    * @param description la description du produit. Ne doit pas etre null ou vide.
    *        ANTECEDENT : La description doit etre une suite de mots separees par 
    *                     des espaces. Il ne doit y avoir aucune ponctuation.
    *                     Ex: "Table de chevet" = composé de 3 mots 
    *                         "scie    dewalt" = composé de 2 mots.
    * @param categorie la categorie du produit. Doit etre une categorie presente
    *                  dans CATEGORIES (peu importe la casse).
    * @throws ExceptionProduitInvalide si la categorie est invalide ou si 
    *         la description est egale a null ou est vide.
    */
   public Produit (String description, String categorie) {
      int i; //indice de categorie dans CATEGORIES (-1 si categorie invalide)
      
      //verification categorie et description
      i = indiceCategorie(categorie);
      if (i == -1 || description == null || description.isEmpty()) {
         throw new ExceptionProduitInvalide();
      }
      this.categorie = CATEGORIES[i];
      this.description = description;
      
      //Assignation automatique d'un code unique
      code = seqCodeProduit;
      seqCodeProduit++;
   }
   
   /**
    * Constructeur de copie.
    * @param produit le produit a copier.
    * @throws ExceptionProduitInvalide si produit est null.
    */
   public Produit (Produit produit) {
      if (produit == null) {
         throw new ExceptionProduitInvalide();
      }
      this.code = produit.code;
      this.categorie = produit.categorie;
      this.description = produit.description;
      this.prix = produit.prix;
      this.quantite = produit.quantite;
      this.idFournisseur = produit.idFournisseur;
   }
   
   
   //----------------------
   // GETTERS ET SETTERS
   //----------------------
   /**
    * Retourne le code de ce produit.
    * @return le code de ce produit.
    */
   public int getCode() {
      return code;
   }

   /**
    * Retourne la description de ce produit.
    * @return la description de ce produit.
    */
   public String getDescription() {
      return description;
   }
   
   /**
    * Retourne la categorie de ce produit.
    * @return la categorie de ce produit.
    */
   public String getCategorie() {
      return categorie;
   }
   
   /**
    * Retourne la quantite de ce produit.
    * @return la quantite de ce produit.
    */
   public int getQuantite() {
      return quantite;
   }
   
   /**
    * Retourne le prix de ce produit.
    * @return le prix de ce produit.
    */
   public double getPrix() {
      return prix;
   }
   
   /**
    * Retourne le numero d'identification de ce fournisseur.
    * @return le numero d'identification de ce fournisseur.
    */
   public int getIdFournisseur() {
      return idFournisseur;
   }

   /**
    * Modifie la categorie de ce produit.
    * @param categorie la nouvelle categorie de ce produit. 
    * @throws ExceptionInfoProduitInvalide si la categorie donnee est invalide.
    */
   public void setCategorie(String categorie) {
      if (indiceCategorie(categorie) == -1) {
         throw new ExceptionInfoProduitInvalide(MSG_ERR_CAT_INVALIDE);
      }
      this.categorie = categorie;
   }
   
   /**
    * Modifie la description de ce produit.
    * @param description la nouvelle description de ce produit.
    * @throws ExceptionInfoProduitInvalide si la description donnee est null 
    *         ou vide.
    */
   public void setDescription(String description) {
      if (description == null || description.isEmpty()) {
         throw new ExceptionInfoProduitInvalide(MSG_ERR_DESC_INVALIDE);
      }
      this.description = description;
   }

   /**
    * Modifie la quantite de ce produit.
    * @param qte la nouvelle quantite pour ce produit.
    * @throws ExceptionInfoProduitInvalide si qte < 0.
    */
   public void setQuantite(int qte) {
      if (qte < 0) {
         throw new ExceptionInfoProduitInvalide(MSG_ERR_QTE_INVALIDE);
      }
      this.quantite = qte;
   }
   
   /**
    * Modifie le prix de ce produit.
    * @param prix le nouveau prix pour ce produit.
    * @throws ExceptionInfoProduitInvalide si prix < 0.
    */
   public void setPrix(double prix) {
      if (prix < 0) {
         throw new ExceptionInfoProduitInvalide(MSG_ERR_PRIX_INVALIDE);
      }
      this.prix = prix;
   }

   /**
    * Modifie le numero d'identification du fournisseur de ce produit.
    * @param idFournisseur le nouveau numero d'identification du fournisseur
    *        de ce produit.
    * @throws ExceptionInfoProduitInvalide si idFournisseur est invalide (< 0).
    */
   public void setIdFournisseur(int idFournisseur) {
      if (idFournisseur < 0) {
         throw new ExceptionInfoProduitInvalide (MSG_ERR_ID_FOURN_INVALIDE);
      }
      this.idFournisseur = idFournisseur;
   }
   
   //--------------------------------------
   // AUTRES METHODES D'INSTANCE PUBLIQUES
   //--------------------------------------
   
   /**
    * Ajoute qte a la quantite de ce produit.
    * @param qte la quantite a ajouter. Si qte <= 0, la quantite de ce produit 
    *        demeure inchangee.
    */
   public void augmenterQuantite (int qte) {
      if (qte > 0) {
         quantite = quantite + qte;
      }  
   }
   
   /**
    * Retire qte de la quantite de ce produit.
    * @param qte la quantite a retirer. Si qte <= 0, la quantite de ce produit
    *        demeure inchangee. Si qte > quantite de ce produit, la quantite
    *        de ce produit devient 0 (valeur negative non permise).
    */
   public void diminuerQuantite (int qte) {
      if (qte > 0) {
         quantite = quantite - qte;
         if (quantite < 0) {
            quantite = 0;
         }
      }
   }
   
   /**
    * Redefinition de la methode equals. Retourne vrai si les deux produits 
    * sont egaux, faux sinon. Deux produits sont consideres egaux s'ils ont 
    * le meme code de produit.
    * @param autreProduit le produit dont on veut tester l'egalite avec ce 
    *                     produit.
    * @return true si les deux produits compares sont egaux, false sinon.
    */
   @Override
   public boolean equals (Object autreProduit) {
      return autreProduit != null
              && this.getClass() == autreProduit.getClass()
              && this.code == ((Produit)autreProduit).code;
   }
   
   /**
    * Redefinition de la methode toString. Retourne une representation de ce 
    * produit sous forme de chaine de caracteres contenant le code de ce produit, 
    * sa description, sa categorie, son prix sa quantite et le code de son
    * fournisseur.
    * @return une representation de ce produit sous forme de chaine de caracteres.
    */
   @Override
   public String toString () {
      return code + " : " + description.toUpperCase() + "(" + categorie + ")\nPrix : "
              + String.format(Locale.ENGLISH, "%1.2f$", prix) + " / quantite : " 
              + quantite + "\nFournisseur : " + idFournisseur;
   }  
   
   //--------------------------------------
   // METHODE DE CLASSE PUBLIQUE
   //--------------------------------------
   
   /**
    * Reinitialise l'attribut seqCodeProduit a 1.
    */
   public static void intiSeqCodeProduit() {
      seqCodeProduit = 1;
   }

   //--------------------------------------
   // METHODE PRIVEE
   //--------------------------------------
   
   /**
    * Retourne l'indice de la categorie de ce produit dans le tableau CATEGORIES 
    * ou -1 si categorie est null ou n'est pas dans le tableau. 
    * La recherche de la categorie dans le tableau ne tient pas compte de la 
    * casse. Par exemple, les termes "AUTOMOBILE", "automobile" et AuTOMobiLE" 
    * correspondent tous a la categorie "AUTOMOBILE", a l'indice 1 dans le 
    * tableau CATEGORIES.
    * @param categorie la categorie a rechercher dans le tableau CATEGORIES
    * @return l'indice de categorie dans le tableau CATEGORIES ou -1 si 
    *         categorie est null ou ne se trouve pas dans le tableau
    *         CATEGORIES. 
    */
   private int indiceCategorie (String categorie) {
      int i = -1;             //indice de la categorie
      boolean trouve = false; //vrai si la categorie est valide
      
      if (categorie != null && !categorie.isEmpty()) {
         
         //trouver l'indice de categorie dans CATEGORIES
         while (i < CATEGORIES.length - 1 && !trouve) {
            i++;
            trouve = CATEGORIES[i].equalsIgnoreCase(categorie);
         }
         if (!trouve) {
            i = -1;
         }
      }
      return i;
   }
      
}
