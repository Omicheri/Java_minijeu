import java.util.Random;
public class Main {
    public static void main(String[] args) {
        Joueur joueur = new Joueur("Héros");
        Random random = new Random();
        int monstresNiveau1Tues = 0;
        int monstresNiveau2Tues = 0;

        while (joueur.estVivant()) {
            // Création aléatoire d'un monstre
            MonstreNiveau1 monstre;
            boolean estMonstreNiveau2 = random.nextBoolean();

            if (estMonstreNiveau2) {
                monstre = new MonstreNiveau2();
                System.out.println("Un monstre de niveau 2 apparaît !");
            } else {
                monstre = new MonstreNiveau1();
                System.out.println("Un monstre de niveau 1 apparaît !");
            }

            // Combat
            while (monstre.estVivant() && joueur.estVivant()) {
                // Le joueur attaque
                if (joueur.attaquer(monstre)) {
                    if (estMonstreNiveau2) {
                        monstresNiveau2Tues++;
                        System.out.println("Monstre de niveau 2 vaincu !");
                    } else {
                        monstresNiveau1Tues++;
                        System.out.println("Monstre de niveau 1 vaincu !");
                    }
                    break;
                }

                // Le monstre attaque
                if (monstre.estVivant()) {
                    monstre.attaquer(joueur);
                    System.out.println("Points de vie restants : " + joueur.getPointsVie());
                }
            }
        }

        // Résultats finaux
        System.out.println(joueur.getNom() + " est mort...");
        System.out.println("Bravo, vous avez tué " + monstresNiveau1Tues +
                " monstres de niveau 1 et " + monstresNiveau2Tues +
                " monstres de niveau 2.");
        System.out.println("Vous avez gagné " +
                (monstresNiveau1Tues + (monstresNiveau2Tues * 2)) +
                " points.");
    }
}


class De {
    private Random random = new Random();

    public int jeterDe() {
        return random.nextInt(6) + 1;  // Génère un nombre entre 1 et 6
    }
}

// Fichier Joueur.java
class Joueur {
    private String nom;
    private int pointsVie;
    private De de;

    public Joueur(String nom) {
        this.nom = nom;
        this.pointsVie = 100;
        this.de = new De();
    }

    public boolean estVivant() {
        return pointsVie > 0;
    }

    public int jeterDe() {
        return de.jeterDe();
    }

    public boolean attaquer(MonstreNiveau1 monstre) {
        int jetJoueur = jeterDe();
        int jetMonstre = monstre.jeterDe();

        if (jetJoueur >= jetMonstre) {
            monstre.recoitDegat();
            return true;
        }
        return false;
    }

    public void recoitDegat(int degats) {
        int jetBouclier = jeterDe();
        if (jetBouclier > 2) {  // Le bouclier n'a pas bloqué
            this.pointsVie -= degats;
        }
    }

    public String getNom() {
        return nom;
    }

    public int getPointsVie() {
        return pointsVie;
    }
}

// Fichier MonstreNiveau1.java
class MonstreNiveau1 {
    protected boolean vivant;
    protected int degats;
    protected De de;

    public MonstreNiveau1() {
        this.vivant = true;
        this.degats = 10;
        this.de = new De();
    }

    public int jeterDe() {
        return de.jeterDe();
    }

    public void attaquer(Joueur joueur) {
        int jetMonstre = jeterDe();
        int jetJoueur = joueur.jeterDe();

        if (jetMonstre > jetJoueur) {
            joueur.recoitDegat(degats);
        }
    }

    public void recoitDegat() {
        this.vivant = false;
    }

    public boolean estVivant() {
        return vivant;
    }
}

// Fichier MonstreNiveau2.java
class MonstreNiveau2 extends MonstreNiveau1 {
    private int degatSort;

    public MonstreNiveau2() {
        super();
        this.degatSort = 5;
    }

    @Override
    public void attaquer(Joueur joueur) {
        super.attaquer(joueur);  // Attaque normale du monstre niveau 1

        // Sort magique
        int jetSort = jeterDe();
        if (jetSort != 6) {
            joueur.recoitDegat(jetSort * degatSort);
        }
    }
}