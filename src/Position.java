/**
 * Classe gérant les positions
 *
 * @version 1
 */
public class Position {

    // Attributs
    private int x;
    private int y;

    /**
     * Constructeur permettant
     *
     * @param x valeur position x
     * @param y valeur position y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Méthode permettant d'obtenir la position X en public
     *
     * @return position X
     */
    public int X() {
        return x;
    }

    /**
     * Méthode permettant d'obtenir la position Y en public
     *
     * @return position Y
     */
    public int Y() {
        return y;
    }

}
