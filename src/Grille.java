import java.util.Random;

/**
 * Classe gérant notre grille de jeu
 *
 * @version 1
 */
public class Grille {

    // Valeurs de configuration rapide
    int configLargeur = 7;
    int configHauteur = 6;
    char vide = ' ';

    // Déclaration de la variable aléatoire
    Random rand = new Random();

    // Attributs
    private int Largeur;
    private int Hauteur;
    private char[][] Contenu;
    private int[] Interdit;
    private int Position_Min_Max;

    /**
     * Constructeur permettant de générer une grille
     */
    public Grille() {
        this.Largeur = configLargeur;
        this.Hauteur = configHauteur;
        this.Contenu = new char[configHauteur][configLargeur];
        for(int i = 0; i < Hauteur; i++) for(int j = 0; j < Largeur; j++) this.Contenu[i][j] = vide;
        this.Interdit = new int[configLargeur];
    }

    // #################################################################################################################

    /**
     * Méthode permettant d'afficher notre grille de jeu
     */
    public void Afficher() {
        System.out.println();
        for(int i = 0; i < Hauteur; i++) {
            System.out.print("|");
            for(int j = 0; j < Largeur; j++) System.out.print(Contenu[i][j] + "|");
            System.out.println();
        }
        System.out.print(" ");
        for(int k = 1; k <= Largeur; k++) System.out.print(k + " ");
        System.out.println();
    }

    /**
     * Méthode permettant de vérifier l'alignement de 4 pions à partir d'une position
     *
     * @param p une position de départ
     * @return est-ce que un alignement de 4 pions existent à partir de cette position ?
     * @see boolean
     */
    public boolean Verifier(Position p) {
        int x = p.X(); int y = p.Y();
        char jeton = Contenu[x][y];
        int ver = 0; int hor = 0; int diag1 = 0; int diag2 = 0;

        // Vérification verticale
        if(x-1 >= 0) if(Contenu[x-1][y] == jeton) if(x-2 >= 0) if(Contenu[x-2][y] == jeton) if(x-3 >= 0) if(Contenu[x-3][y] == jeton) return true; else ver += 3; else ver += 3; else ver += 2; else ver += 2; else ver++;
        if(x+1 < Hauteur) if(Contenu[x+1][y] == jeton) if(x+2 < Hauteur) if(Contenu[x+2][y] == jeton) if(x+3 < Hauteur) if(Contenu[x+3][y] == jeton) return true; else ver += 2; else ver += 2; else ver++; else ver++;
        if(ver >= 4) return true;

        // Vérification horizontale
        if(y-1 >= 0) if(Contenu[x][y-1] == jeton) if(y-2 >= 0) if(Contenu[x][y-2] == jeton) if(y-3 >= 0) if(Contenu[x][y-3] == jeton) return true; else hor += 3; else hor += 3; else hor += 2; else hor += 2; else hor++;
        if(y+1 < Largeur) if(Contenu[x][y+1] == jeton) if(y+2 < Largeur) if(Contenu[x][y+2] == jeton) if(y+3 < Largeur) if(Contenu[x][y+3] == jeton) return true; else hor += 2; else hor += 2; else hor++; else hor++;
        if(hor >= 4) return true;

        // Vérification diagonale (bas gauche vers haut droite)
        if(x+1 < Hauteur && y-1 >= 0) if(Contenu[x+1][y-1] == jeton) if(x+2 < Hauteur && y-2 >= 0) if(Contenu[x+2][y-2] == jeton) if(x+3 < Hauteur && y-3 >= 0) if(Contenu[x+3][y-3] == jeton) return true; else diag1 += 3; else diag1 += 3; else diag1 += 2; else diag1 += 2; else diag1++;
        if(x-1 >= 0 && y+1 < Largeur) if(Contenu[x-1][y+1] == jeton) if(x-2 >= 0 && y+2 < Largeur) if(Contenu[x-2][y+2] == jeton) if(x-3 >= 0 && y+3 < Largeur) if(Contenu[x-3][y+3] == jeton) return true; else diag1 += 2; else diag1 += 2; else diag1++; else diag1++;
        if(diag1 >= 4) return true;

        // Vérification diagonale (haut gauche vers bas droite)
        if(x-1 >= 0 && y-1 >= 0) if(Contenu[x-1][y-1] == jeton) if(x-2 >= 0 && y-2 >= 0) if(Contenu[x-2][y-2] == jeton) if(x-3 >= 0 && y-3 >= 0) if(Contenu[x-3][y-3] == jeton) return true; else diag2 += 3; else diag2 += 3; else diag2 += 2; else diag2 += 2; else diag2++;
        if(x+1 < Hauteur && y+1 < Largeur) if(Contenu[x+1][y+1] == jeton) if(x+2 < Hauteur && y+2 < Largeur) if(Contenu[x+2][y+2] == jeton) if(x+3 < Hauteur && y+3 < Largeur) if(Contenu[x+3][y+3] == jeton) return true; else diag2 += 2; else diag2 += 2; else diag2++; else diag2++;
        if(diag2 >= 4) return true;

        return false;
    }

    /**
     * Méthode permettant de vérifier si un joueur donné a gagné une partie ou nom
     *
     * @param jeton jeton
     * @return est-ce que ce joueur a gagné la partie ?
     * @see boolean
     */
    public boolean VerifierVictoire(char jeton) {
        for(int x = 0; x < Hauteur; x++) {
            for(int y = 0; y < Largeur; y++) {
                if(Contenu[x][y] == jeton) if(Verifier(new Position(x, y))) return true;
            }
        }
        return false;
    }

    /**
     * Méthode permettant de placer un jeton
     *
     * @param x colonne où placer le jeton
     * @param joueur joueur qui place le jeton
     * @return position verticale de où a été placé le jeton
     * @see int
     */
    public int PlacerJeton(int x, Joueur joueur) {
        int y = Haut(x);
        if(y >= 0) Contenu[y][x] = joueur.getJeton();
        return y;
    }

    /**
     * Méthode permettant de calculer les positions + ou - avantageuses où joueur
     *
     * @return position horizontale où le jeton va être placé
     * @see int
     */
    public int Calculer() {
        // Initialisation
        int pos = 0;

        // Réinitialisation tableau d'interdits
        for(int i = 0; i < Largeur; i++) {
            Interdit[i] = 2;
        }

        // Vérification coup décisif (de l'IA ou de l'humain)
        if(CoupDecisif(0) < 0) {
            // Vérification coup décisif joueur
            if(CoupDecisif(1) < 0) {
                // Vérification reste colonne
                if(Reste(2)) {
                    Min_Max(7, -1000, -1000, 'O');
                    return Position_Min_Max;
                }
                else {
                    if(Reste(0)) { // coup moins désavantageux
                        pos = rand.nextInt(Largeur);
                        while (Haut(pos) <= -1 || Interdit[pos] != 0) pos = rand.nextInt(Largeur);
                        return pos;
                    } else { // jeu obligatoire
                        pos = rand.nextInt(Largeur);
                        while(Haut(pos) <= -1) pos = rand.nextInt(Largeur);
                        return pos;
                    }
                }
            }
            return CoupDecisif(1);
        }
        return CoupDecisif(0);
    }

    /**
     * Méthode de vérification du coup décisif
     *
     * @param Action action réalisée
     * @return résultat du coup décisif
     * @see int
     */
    public int CoupDecisif(int Action) {
        char jeton = 'O'; int max = 0; int random = 0;

        if(Action == 0) jeton = 'O';
        else jeton = 'X';

        // Vertical
        for(int i = 0; i < Largeur; i++) {
            max = Haut(i)+1;
            if(max > 0 && max < Hauteur-2 && Contenu[max][i] == jeton && Contenu[max+1][i] == jeton && Contenu[max+2][i] == jeton) {
                return i;
            }
        }

        // Horizontal
        for(int i = 0; i < Hauteur; i++) {

            for(int j = 0; j < 3; j++) {

                if(i == 5 || (Contenu[i+1][j] != vide || Contenu[i+1][j+1] != vide || Contenu[i+1][j+2] != vide  || Contenu[i+1][j+3] != vide  || Contenu[i+1][j+4] != vide)) {

                    // classique : 1 jeton, 1 trou, 1 jeton
                    if(Contenu[i][j] == vide && Contenu[i][j+1] == jeton && Contenu[i][j+2] == vide && Contenu[i][j+3] == jeton && Contenu[i][j+4] == vide) {
                        switch(rand.nextInt(3)) {
                            case 0:
                                if(Haut(j)-i == 0) return j;
                                else if(Haut(j)-i == 1) Interdit[j] = Action;
                            case 1:
                                if(Haut(j+2)-i == 0) return j+2;
                                else if(Haut(j+2)-i == 1) Interdit[j+2] = Action;
                            case 2:
                                if(Haut(j+4)-i == 0) return j+4;
                                else if(Haut(j+4)-i == 1) Interdit[j+4] = Action;
                        }
                    }

                    // 2 trous, 2 pions, 1 trou
                    if(Contenu[i][j] == vide && Contenu[i][j+1] == vide && Contenu[i][j+2] == jeton && Contenu[i][j+3] == jeton && Contenu[i][j+4] == vide) {
                        if(Haut(j+1)-i == 0) return j+1;
                        else if(Haut(j+1)-i == 1) Interdit[j+1] = Action;
                        if(Haut(j+4)-i == 0) return j+4;
                        else if(Haut(j+4)-i == 1) Interdit[j+4] = Action;
                    }

                    // 1 trous, 2 pions, 2 trous
                    if(Contenu[i][j] == vide && Contenu[i][j+1] == jeton && Contenu[i][j+2] == jeton && Contenu[i][j+3] == vide && Contenu[i][j+4] == vide) {
                        if(Haut(j)-i == 0) return j;
                        else if(Haut(j)-i == 1) Interdit[j] = Action;
                        if(Haut(j+3)-i == 0) return j+3;
                        else if(Haut(j+3)-i == 1) Interdit[j+3] = Action;
                    }
                    
                }
            }

            for(int j = 0; j < 4; j++) {

                // 1 trou, 3 pions
                if(Contenu[i][j] == vide && Contenu[i][j+1] == jeton && Contenu[i][j+2] == jeton && Contenu[i][j+3] == jeton) {
                    if(Haut(j)-i == 0) return j;
                    else if(Haut(j)-i == 1) Interdit[j] = Action;
                }

                // 1 pion, 1 trou, 2 pions
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == vide && Contenu[i][j+2] == jeton && Contenu[i][j+3] == jeton) {
                    if(Haut(j+1)-i == 0) return j+1;
                    else if(Haut(j+1)-i == 1) Interdit[j+1] = Action;
                }

                // 2 pions, 1 trou, 1 pion
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == jeton && Contenu[i][j+2] == vide && Contenu[i][j+3] == jeton) {
                    if(Haut(j+2)-i == 0) return j+2;
                    else if(Haut(j+2)-i == 1) Interdit[j+2] = Action;
                }

                // 3 pions, 1 trou
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == jeton && Contenu[i][j+2] == jeton && Contenu[i][j+3] == vide) {
                    if(Haut(j+3)-i == 0) return j+3;
                    else if(Haut(j+3)-i == 1) Interdit[j+3] = Action;
                }

            }

        }

        // Diagonal
        for(int j = 0; j < 4; j++) {
            for(int i = 0; i > -3; i--) {

                // gauche : 1 trou, 3 pions
                if(Contenu[i+2][j] == vide && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) {
                    if(Haut(j)-i-2 == 0) return j;
                    else if(Haut(j)-i-2 == 1) Interdit[j] = Action;
                }

                // gauche : 1 pion, 1 trou, 2 pions
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == vide && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) {
                    if(Haut(j+1)-i-3 == 0) return j+1;
                    else if(Haut(j+1)-i-3 == 1) Interdit[j+1] = Action;
                }

                // gauche : 2 pions, 1 trou, 1 pion
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == vide && Contenu[i+5][j+3] == jeton) {
                    if(Haut(j+2)-i-4 == 0) return j+2;
                    else if(Haut(j+2)-i-4 == 1) Interdit[j+2] = Action;
                }

                // gauche : 3 pions, 1 trou
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == vide) {
                    if(Haut(j+3)-i-5 == 0) return j+3;
                    else if(Haut(j+3)-i-5 == 1) Interdit[j+3] = Action;
                }

                // droite : 1 trou, 3 pions
                if(Contenu[i+2][j] == vide && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) {
                    if(Haut(j)-i-5 == 0) return j;
                    else if(Haut(j)-i-5 == 1) Interdit[j] = Action;
                }

                // droite : 1 pion, 1 trou, 2 pions
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == vide && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) {
                    if(Haut(j+1)-i-4 == 0) return j+1;
                    else if(Haut(j+1)-i-4 == 1) Interdit[j+1] = Action;
                }

                // droite : 2 pions, 1 trou, 1 pion
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == vide && Contenu[i+5][j+3] == jeton) {
                    if(Haut(j+2)-i-3 == 0) return j+2;
                    else if(Haut(j+2)-i-3 == 1) Interdit[j+2] = Action;
                }

                // droite : 3 pions, 1 trou
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == vide) {
                    if(Haut(j+3)-i-2 == 0) return j+3;
                    else if(Haut(j+3)-i-2 == 1) Interdit[j+3] = Action;
                }

            }
        }
        return -1;
    }

    /**
     * Méthode de l'algorithme MinMax
     *
     * @param profondeur Profondeur du calcul
     * @param alpha Valeur de alpha
     * @param beta Valeur de beta
     * @param jeton Jeton à placer
     * @return Score calculé
     * @see int
     */
    public int Min_Max(int profondeur, int alpha, int beta, char jeton) {
        int Score = 0;
        if(profondeur <= 0) return AttribuerPoints(jeton);
        else {
            int MeilleurScore = -1000;
            for(int i = 0; i < Largeur; i++) {
                if(Interdit[i] != 2) {
                    int Max = Haut(i);
                    if(Max >= 0) {
                        Contenu[Max][i] = jeton;
                        if(MeilleurScore > alpha) alpha = MeilleurScore;
                        Score = -Min_Max(profondeur-1, -beta, -alpha, InverseJeton(jeton));
                        if(Score >= MeilleurScore) {
                            if(Score == MeilleurScore) {
                                if(rand.nextInt(2) == 0) Position_Min_Max = i;
                            } else {
                                MeilleurScore = Score;
                                Position_Min_Max = i;
                            }
                        }
                        Contenu[Max][i] = vide;
                        if(MeilleurScore <= beta) break;
                    }
                }
            }
            return MeilleurScore;
        }
    }

    /**
     * Méthode de calcul des possibilités à 3 jetons
     *
     * @param jeton jeton à placer
     * @return résultat
     * @see int
     */
    public int Points_3Pions(char jeton) {
        int max = 0; int resultat = 0;

        // vertical
        for(int i = 0; i < Largeur; i++) {
            max = Haut(i)+1;
            if(max > 0 && max < 4 && Contenu[max][i] == jeton && Contenu[max+1][i] == jeton && Contenu[max+2][i] == jeton) resultat++;
        }

        // horizontal
        for(int i = 0; i < Hauteur; i++) {
            for(int j = 0; j < 4; j++) {

                // 1 trou, 3 pions
                if(Contenu[i][j] == vide && Contenu[i][j+1] == jeton && Contenu[i][j+2] == jeton && Contenu[i][j+3] == jeton) resultat++;

                // 1 pion, 1 trou, 2 pions
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == vide && Contenu[i][j+2] == jeton && Contenu[i][j+3] == jeton) resultat++;

                // 2 pions, 1 trou, 1 pion
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == jeton && Contenu[i][j+2] == vide && Contenu[i][j+3] == jeton) resultat++;

                // 3 pions, 1 trou
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == jeton && Contenu[i][j+2] == jeton && Contenu[i][j+3] == vide) resultat++;

            }
        }

        // diagonal
        for(int j = 0; j < 4; j++) {
            for (int i = 0; i > -3; i--) {

                // gauche : 1 trou, 3 pions
                if(Contenu[i+2][j] == vide && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) resultat++;

                // gauche : 1 pion, 1 trou, 2 pions
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == vide && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) resultat++;


                // gauche : 2 pions, 1 trou, 1 pion
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == vide && Contenu[i+5][j+3] == jeton) resultat++;

                // gauche : 3 pions, 1 trou
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == vide) resultat++;

                // droite : 1 trou, 3 pions
                if(Contenu[i+5][j] == vide && Contenu[i+4][j+1] == jeton && Contenu[i+3][j+2] == jeton && Contenu[i+2][j+3] == jeton) resultat++;

                // droite : 1 pion, 1 trou, 2 pions
                if(Contenu[i+5][j] == jeton && Contenu[i+4][j+1] == vide && Contenu[i+3][j+2] == jeton && Contenu[i+2][j+3] == jeton) resultat++;

                // droite : 2 pions, 1 trou, 1 pion
                if(Contenu[i+5][j] == jeton && Contenu[i+4][j+1] == jeton && Contenu[i+3][j+2] == vide && Contenu[i+2][j+3] == jeton) resultat++;

                // droite : 3 pions, 1 trou
                if(Contenu[i+5][j] == jeton && Contenu[i+4][j+1] == jeton && Contenu[i+3][j+2] == jeton && Contenu[i+2][j+3] == vide) resultat++;

            }
        }

        return resultat;
    }

    /**
     * Méthode de calcul des possibilités à 2 jetons
     *
     * @param jeton jeton à placer
     * @return résultat
     * @see int
     */
    public int Points_2Pions(char jeton) {
        int max = 0; int resultat = 0;

        // vertical
        for(int i = 0; i < Largeur; i++) {
            max = Haut(i)+1;
            if(max > 0 && max < 4 && Contenu[max][i] == jeton && Contenu[max+1][i] == jeton) resultat++;
        }

        // horizontal
        for(int i = 0; i < Hauteur; i++) {
            for(int j = 0; j < 4; j++) {

                // 2 trous, 2 pions
                if(Contenu[i][j] == vide && Contenu[i][j+1] == vide && Contenu[i][j+2] == jeton && Contenu[i][j+3] == jeton) resultat++;

                // 2 pions, 2 trous
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == jeton && Contenu[i][j+2] == vide && Contenu[i][j+3] == vide) resultat++;

                // 1 pion, 1 trou, 1 pion, 1 trou
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == vide && Contenu[i][j+2] == jeton && Contenu[i][j+3] == vide) resultat++;

                // 1 trou, 1 pion, 1 trou, 1 pion
                if(Contenu[i][j] == vide && Contenu[i][j+1] == jeton && Contenu[i][j+2] == vide && Contenu[i][j+3] == jeton) resultat++;

                // 1 trou, 2 pions, 1 trou
                if(Contenu[i][j] == vide && Contenu[i][j+1] == jeton && Contenu[i][j+2] == jeton && Contenu[i][j+3] == vide) resultat++;

                // 1 pion, 2 trous, 1 pion
                if(Contenu[i][j] == jeton && Contenu[i][j+1] == vide && Contenu[i][j+2] == vide && Contenu[i][j+3] == jeton) resultat++;

            }
        }

        // diagonal
        for(int j = 0; j < 4; j++) {
            for (int i = 0; i > -3; i--) {

                // gauche : 2 trous, 2 pions
                if(Contenu[i+2][j] == vide && Contenu[i+3][j+1] == vide && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == jeton) resultat++;

                // gauche : 2 pions, 2 trous
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == vide && Contenu[i+5][j+3] == vide) resultat++;

                // gauche : 1 pion, 1 trou, 1 pion, 1 trou
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == vide && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == vide) resultat++;

                // gauche : 1 trou, 1 pion, 1 trou, 1 pion
                if(Contenu[i+2][j] == vide && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == vide && Contenu[i+5][j+3] == jeton) resultat++;

                // gauche : 1 trou, 2 pions, 1 trou
                if(Contenu[i+2][j] == vide && Contenu[i+3][j+1] == jeton && Contenu[i+4][j+2] == jeton && Contenu[i+5][j+3] == vide) resultat++;

                // gauche : 1 pion, 2 trous, 1 pion
                if(Contenu[i+2][j] == jeton && Contenu[i+3][j+1] == vide && Contenu[i+4][j+2] == vide && Contenu[i+5][j+3] == jeton) resultat++;

                // droite : 2 trous, 2 pions
                if(Contenu[i+5][j] == vide && Contenu[i+4][j+1] == vide && Contenu[i+3][j+2] == jeton && Contenu[i+2][j+3] == jeton) resultat++;

                // droite : 2 pions, 2 trous
                if(Contenu[i+5][j] == jeton && Contenu[i+4][j+1] == jeton && Contenu[i+3][j+2] == vide && Contenu[i+2][j+3] == vide) resultat++;

                // droite : 1 pion, 1 trou, 1 pion, 1 trou
                if(Contenu[i+5][j] == jeton && Contenu[i+4][j+1] == vide && Contenu[i+3][j+2] == jeton && Contenu[i+2][j+3] == vide) resultat++;

                // droite : 1 trou, 1 pion, 1 trou, 1 pion
                if(Contenu[i+5][j] == vide && Contenu[i+4][j+1] == jeton && Contenu[i+3][j+2] == vide && Contenu[i+2][j+3] == jeton) resultat++;

                // droite : 1 trou, 2 pions, 1 trou
                if(Contenu[i+5][j] == vide && Contenu[i+4][j+1] == jeton && Contenu[i+3][j+2] == jeton && Contenu[i+2][j+3] == vide) resultat++;

                // droite : 1 pion, 2 trous, 1 pion
                if(Contenu[i+4][j] == jeton && Contenu[i+4][j+1] == vide && Contenu[i+3][j+2] == vide && Contenu[i+2][j+3] == jeton) resultat++;

            }
        }

        return resultat;
    }

    /**
     * Méthode d'attribution des points
     *
     * @param jeton jeton à placer
     * @return résultat
     * @see int
     */
    public int AttribuerPoints(char jeton) {
        int pointsX = 0; int pointsO = 0;
        if(VerifierVictoire('X')) pointsX = 10000;
        else pointsX = 0;
        if(VerifierVictoire('O')) pointsO = 10000;
        else pointsO = 0;

        pointsX += 100*Points_3Pions('X');
        pointsO += 100*Points_3Pions('O');
        pointsX += Points_2Pions('X');
        pointsO += Points_3Pions('O');

        if(jeton == 'X') return pointsX-pointsO;
        else return pointsO-pointsX;
    }

    /**
     * Méthode permettant de voir ce qui est restant par rapport aux interdits
     *
     * @param num Numéro de l'interdit à vérifier
     * @return résultat
     * @see boolean
     */
    public boolean Reste(int num) {
        for(int i = 0; i < Largeur; i++) if(Interdit[i] == num && Haut(i) > -1) return true;
        return false;
    }

    /**
     * Méthode permettant connaître la plus haute case disponible au sein d'une colonne
     *
     * @param colonne Colonne sur laquelle on souhaite récupérer la ligne la plus haute
     * @return hauteur
     * @see int
     */
    public int Haut(int colonne) {
        for(int i = Hauteur-1; i >= 0; i--) if(Contenu[i][colonne] == vide) return i;
        return -1;
    }

    /**
     * Méthode permettant d'obtenir l'autre jeton à partir d'un jeton
     *
     * @param jeton Jeton à inverser
     * @return Inverse du jeton
     * @see char
     */
    public char InverseJeton(char jeton) {
        if(jeton == 'O') return 'X';
        else return 'O';
    }

}