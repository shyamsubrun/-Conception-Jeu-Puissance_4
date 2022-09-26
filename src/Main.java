import java.util.*;

/**
 * Main
 *
 * @version 1
 */
public class Main {


    /**
     * Main
     *
     * @param args arguments
     */
    public static void main(String[] args) {

        // Initialisations
        Scanner sc = new Scanner(System.in);
        Grille Grille = new Grille();
        int tour = 1;
        Joueur joueur = null;

        System.out.println("Quel mode de jeu ?\n1. Humain vs Humain\n2. Humain vs IA");
        int mode = sc.nextInt(); sc.nextLine();

        if(mode == 1) { // Humain vs Humain
            System.out.println("Comment s'appel le premier joueur ? (celui-ci jouera les X)");
            String repJoueur1 = sc.nextLine();
            Joueur Joueur1 = new Joueur(repJoueur1, 'X');
            System.out.println("Comment s'appel le deuxième joueur ? (celui-ci joueura les O)");
            String repJoueur2 = sc.nextLine();
            Joueur Joueur2 = new Joueur(repJoueur2, 'O');

            System.out.println("La partie opposant " + Joueur1.getNom() + " (X) et " + Joueur2.getNom() + " (O) va commencer !");
            joueur = Joueur1;

            // Lancement d'une partie
            while(true) {

                int x = 0;
                int y = 0;
                boolean erreur = false;

                while(true) {
                    Grille.Afficher();

                    if(erreur) System.out.println("\nCette colonne n'est pas jouable ! Merci d'essayez une autre colonne.");
                    System.out.println("\nC'est à " + joueur.getNom() + " (" + joueur.getJeton() + ") de jouer, choisissez une colonne :");

                    x = sc.nextInt()-1;
                    if(x < 0 || x > 6) { erreur = true; continue; }
                    y = Grille.PlacerJeton(x, joueur);

                    if(y != -1) break;
                    else erreur = true;
                }

                if(Grille.Verifier(new Position(y, x))) break;

                tour++;
                if(tour == 43) break;

                if(joueur == Joueur1) joueur = Joueur2;
                else joueur = Joueur1;
            }

        } else { // Humain vs IA
            System.out.println("Comment vous appelez-vous ? (vous jouerez les X)");
            String repJoueur = sc.nextLine();
            Joueur Joueur1 = new Joueur(repJoueur, 'X');
            Joueur Joueur2 = new Joueur("IA", 'O');

            System.out.println("La partie opposant " + Joueur1.getNom() + " (X) et " + Joueur2.getNom() + " (O) va commencer !");
            joueur = Joueur1;

            // Lancement d'une partie
            while(true) {

                int x = 0;
                int y = 0;
                boolean erreur = false;

                if(joueur == Joueur1) { // c'est l'humain qui joue
                    while(true) {
                        Grille.Afficher();

                        if(erreur) System.out.println("\nCette colonne n'est pas jouable ! Merci d'essayez une autre colonne.");
                        System.out.println("\nC'est à " + joueur.getNom() + " (" + joueur.getJeton() + ") de jouer, choisissez une colonne :");

                        x = sc.nextInt()-1;
                        if(x < 0 || x > 6) { erreur = true; continue; }
                        y = Grille.PlacerJeton(x, joueur);

                        if(y != -1) break;
                        else erreur = true;
                    }
                } else { // c'est l'ordinateur qui joue
                    x = Grille.Calculer();
                    y = Grille.PlacerJeton(x, joueur);
                }

                if(Grille.Verifier(new Position(y, x))) break;

                tour++;
                if(tour == 43) break;

                if(joueur == Joueur1) joueur = Joueur2;
                else joueur = Joueur1;
            }


        }

        if(tour <= 42) {
            Grille.Afficher();
            System.out.println("\nFin de partie, le gagnant est " + joueur.getNom() + ", félicitations !");
        } else {
            Grille.Afficher();
            System.out.println("\nFin de partie, il y a égalité !");
        }

    }

}