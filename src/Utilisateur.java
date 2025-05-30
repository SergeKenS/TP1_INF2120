import java.util.ArrayList;

public abstract class Utilisateur {

    private static int seqId = 1;

    private int id = 0;
    private String pseudo;
    private String motPasse;
    private String courriel;
    private ArrayList<Integer> evaluations;

    /**
     * Ce constructeur initialise les attributs d’instance pseudo, motPasse, et courriel avec les valeurs reçues en
     * paramètres. La liste des évaluations de cet utilisateur est non null et vide.
     * @param pseudo : Le pseudonyme de cet utilisateur.
     * @param motPasse : Le mot de passe de cet utilisateur.
     * @param courriel : Le courriel de cet utilisateur.
     */
    public Utilisateur(String pseudo, String motPasse, String courriel) {
        this.pseudo = pseudo;
        this.motPasse = motPasse;
        this.courriel = courriel;
        this.evaluations = new ArrayList<>(0);
        id = id + 1;
    }

    /**
     * Permet de construire une copie a partir de l'utilisateur passer en parametre.
     * @param utilisateur
     */
    public Utilisateur(Utilisateur utilisateur) {
        this.pseudo = utilisateur.pseudo;
        this.motPasse = utilisateur.motPasse;
        this.courriel = utilisateur.courriel;
        this.id = utilisateur.id;
        this.evaluations = (ArrayList<Integer>)utilisateur.getEvaluations().clone();
    }

    /**
     * @return Cette méthode retourne une liste de chaines de caractères représentant le profil de cet utilisateur
     */
    public abstract ArrayList<String> compilerProfil();

    /**
     * permet à un utilisateur d’évaluer un autre utilisateur
     *
     * @param utilisateur : L’utilisateur à évaluer par cet utilisateur
     * @param eval        : L’évaluation donnée par cet utilisateur à l’utilisateur reçu en paramètre
     *                    (entre 1 et 5)
     */
    public abstract void evaluer(Utilisateur utilisateur, int eval) throws Exception;

    public String getPseudo() {
        return pseudo;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public String getCourriel() {
        return courriel;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getEvaluations() {
        return evaluations;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    /**
     * Cette méthode calcule, et retourne la moyenne de toutes les évaluations de cet utilisateur
     * @return : la moyenne des evaluations recu par cette utilisateur.
     */
    public double evaluationMoyenne() {
        int somme = 0;
        double moyenne;
        if (evaluations.isEmpty()) {
            moyenne = 0;
        } else {
            for (int i = 0; i < evaluations.size(); i++) {
                somme = somme + evaluations.get(i);
            }
            moyenne = (double) Math.round(somme / evaluations.size() * 100) / 100;
        }
        return moyenne;
    }


    /**
     * Cette méthode permet d’ajouter une nouvelle évaluation reçue à la liste des évaluations de cet utilisateur.
     * @param eval : L’évaluation à ajouter à la liste d’évaluations de cet utilisateur
     */
    public void ajouterEvaluation(int eval) {
        try {
            if (eval < 1 || eval > 5) {
                throw new Exception();
            }
            this.evaluations.add(eval);
        } catch (Exception e) {
            System.out.println("\"Erreur, l'evaluation doit\n" +
                    "etre un nombre entier entre 1 et 5 inclusivement.\"");
        }
    }

    @Override
    /**
     * deux utilisateurs sont considérés comme étant égaux s’ils ont le même id.
     */
    public boolean equals(Object utilisateur) {
        return this.id == ((Utilisateur) utilisateur).getId();
    }

    /**
     * Retourne une representation de cet utilisateur sous forme d'une chaine de
     * caracteres (son id, son pseudonyme, son mot de passe, son courriel et le
     * nombre de ses evaluations reçues).
     *
     * @return une representation de cet utilisateur sous forme d'une chaine de
     * caracteres.
     */
    @Override
    public String toString() {
            return id + " : " + pseudo + " - " + motPasse + " - " + courriel
                    + " - " + evaluations.size();
    }
}