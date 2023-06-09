package IHM;

import Global.Config;
import Modele.Coup;
import Modele.Plateau;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PlateauGraphique extends JComponent implements MouseListener {

    Image img, empoi;

    Plateau plateau;

    int tailleCases;

    FenetreJeu fenetreJeu;

    public PlateauGraphique(FenetreJeu fenetreJeu, Plateau plateau) {
        this.fenetreJeu = fenetreJeu;
        InputStream in = null;
        try {
            in = new FileInputStream("res/Mur.png");
            img = ImageIO.read(in);

            in = new FileInputStream("res/Pousseur.png");
            empoi = ImageIO.read(in);
        } catch (FileNotFoundException e) {
            System.out.println("Lol");
        } catch (Exception e) {
            System.out.println("J'y arrive po");
        }

        this.plateau = plateau;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;

        int taille = Math.min(getSize().width, getSize().height);

        tailleCases = taille / plateau.lignes();

        int[][] cases = plateau.grille();
        for (int i = 0; i < plateau.colonnes(); i++) {
            for (int j = 0; j < plateau.lignes(); j++) {
                if (cases[i][j] != Config.VIDE) {
                    drawable.drawImage(i == 0 && j == 0 ? empoi : img, i * tailleCases, j * tailleCases, tailleCases, tailleCases, null);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Clic souris à la position : " + mouseEvent.getX() + " " + mouseEvent.getY());
        int ligne = mouseEvent.getY() / tailleCases;
        int colonne = mouseEvent.getX() / tailleCases;
        if (ligne < plateau.lignes() && colonne < plateau.colonnes() && plateau.grille()[ligne][colonne] != Config.VIDE) {
            System.out.println("Case correspondante : " + ligne + " " + colonne + ", valeur : " + plateau.grille()[ligne][colonne]);

            fenetreJeu.menu.historique.append("Joueur " + plateau.getTurnPlayer() + " à la case " + ligne + " " + colonne + "\n");

            plateau.mange(new Coup(colonne, ligne));
            fenetreJeu.refresh();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}


