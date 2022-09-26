/**
 * Classe gérant les joueurs
 *
 * @version 1
 */
public class Joueur {

    // Attributs
    private String Nom;
    private char Jeton;

    /**
     * Constructeur permettant
     *
     * @param Nom nom du joueur
     * @param Jeton jeton du joueur
     */
    public Joueur(String Nom, char Jeton) {
        this.Nom = Nom;
        this.Jeton = Jeton;
    }

    /**
     * Méthode permettant d'obtenir le nom en public
     *
     * @return nom
     */
    public String getNom() {
        return Nom;
    }

    /**
     * Méthode permettant d'obtenir le jeton  en public
     *
     * @return jeton
     */
    public char getJeton() { return Jeton; }

}