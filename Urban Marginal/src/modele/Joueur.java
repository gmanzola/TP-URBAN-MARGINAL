package modele;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;
import outils.connexion.Connection;

public class Joueur extends Objet implements Global {

	private String pseudo;
	private int numPerso;
	private Label message;
	private JeuServeur jeuServeur;
	private int vie;
	private int etape;
	private int orientation;
	private Boule boule;
	private int nbPuissance;
	private static final int MAXVIE = 20;
	private static final int GAIN = 1;
	private static final int PERTE = 2;
	private static final int GAINSUP = 1;
	private static final int PERTESUP = 4;

	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		vie = MAXVIE;
		etape = 1; // numéro d'étape dans l'animation
		orientation = DROITE;
	}

	public void initPerso(String pseudo, int numPerso, Hashtable<Connection, Joueur> lesJoueurs,
			ArrayList<Mur> lesMurs) {

		this.pseudo = pseudo;
		this.numPerso = numPerso;
		label = new Label(Label.getNbLabel(), new JLabel());
		Label.setNbLabel(Label.getNbLabel() + 1);
		jeuServeur.nouveauLabelJeu(label);
		label.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		label.getjLabel().setVerticalAlignment(SwingConstants.CENTER);

		message = new Label(Label.getNbLabel(), new JLabel());
		Label.setNbLabel(Label.getNbLabel() + 1);
		message.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		message.getjLabel().setFont(new Font("Dialog", Font.PLAIN, 8));
		jeuServeur.nouveauLabelJeu(message);
		premierePosition(lesJoueurs, lesMurs);
		affiche(MARCHE, etape);
		boule = new Boule(jeuServeur);
		jeuServeur.envoi(boule.getLabel());

		// bouleP = new BoulePuissante(jeuServeur);
		// jeuServeur.envoi(bouleP.getLabel());
	}

	public void affiche(String etat, int etape) {
		label.getjLabel().setBounds(posX, posY, L_PERSO, H_PERSO);
		label.getjLabel().setIcon(new ImageIcon(PERSO + this.numPerso + etat + etape + "d" + orientation + EXTIMAGE));
		message.getjLabel().setBounds(posX - 10, posY + H_PERSO, L_PERSO + 0, H_MESSAGE);
		message.getjLabel().setText(PSEUDO + " : " + vie);
		this.jeuServeur.envoi(super.label);
		this.jeuServeur.envoi(message);

	}

	private void premierePosition(Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		label.getjLabel().setBounds(0, 0, L_PERSO, H_PERSO);
		do {
			super.posX = (int) Math.round(Math.random() * (L_ARENE - L_PERSO));
			// on enleve les dimensions du perso pour ne pas qu'il sorte de
			// l'arene

			super.posY = (int) Math.round(Math.random() * (H_ARENE - H_PERSO - H_MESSAGE));
			// on enleve la hauteur du message sous le perso

		} while (toucheJoueur(lesJoueurs) || toucheMur(lesMurs));
	}

	private int deplace(int action, int position, int orientation, int lepas, int max,
			Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.orientation = orientation;
		int ancienneposition = position;
		position += lepas;
		if (position < 0) {
			position = 0;
		}
		if (position > max) {
			position = max;
		}
		if (action == GAUCHE || action == DROITE) {
			posX = position;
		} else {
			posY = position;
		}
		if (toucheJoueur(lesJoueurs) || toucheMur(lesMurs)) {
			position = ancienneposition;
		}
		etape = etape % NBETATSMARCHE + 1;
		return position;

	}

	public void action(int action, Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		switch (action) {

		case GAUCHE:
			posX = deplace(action, super.posX, GAUCHE, -LEPAS, L_ARENE - (H_PERSO + H_MESSAGE), lesJoueurs, lesMurs);
			break;
		case DROITE:
			posX = deplace(action, super.posX, DROITE, LEPAS, L_ARENE - (H_PERSO + H_MESSAGE), lesJoueurs, lesMurs);
			break;
		case HAUT:
			posY = deplace(action, super.posY, orientation, -LEPAS, H_ARENE - (H_PERSO + H_MESSAGE), lesJoueurs,
					lesMurs);
			break;
		case BAS:
			posY = deplace(action, super.posY, orientation, LEPAS, H_ARENE - (H_PERSO + H_MESSAGE), lesJoueurs,
					lesMurs);
			break;
		case TIRE:
			if (!boule.getLabel().getjLabel().isVisible()) {
				jeuServeur.envoi(FIGHT);
				boule.tireBoule(this, lesMurs, lesJoueurs);
			}
			break;
		}
		affiche(MARCHE, etape);
	}

	private boolean toucheJoueur(Hashtable<Connection, Joueur> lesJoueurs) {
		for (Joueur unJoueur : lesJoueurs.values()) {
			if (!unJoueur.equals(this)) {
				if (super.toucheObjet(unJoueur)) {
					return true;
				}
			}

		}
		return false;
	}

	private boolean toucheMur(ArrayList<Mur> lesMurs) {
		for (Mur unMur : lesMurs) {
			if (super.toucheObjet(unMur)) {
				return true;
			}
		}
		return false;
	}

	public void recussite() {
		vie = 20;
	}

	public boolean estMort() {
		if (vie == 0) {
			return true;
		}
		return false;
	}

	public Label getMessage() {
		return message;
	}

	public String getPseudo() {
		return pseudo;
	}

	public Boule getBoule() {
		return boule;
	}

	public int getNbPuissance() {
		return nbPuissance;
	}

	public void setNbPuissance(int nbPuissance) {
		this.nbPuissance = nbPuissance;
	}

	public void gainVie() {
		vie += GAIN;
		if (vie > 20) {
			vie = 20;
		}
	}

	public void perteVie() {
		vie -= PERTE;
		if (vie < 0) {
			vie = 0;
		}
	}

	public void gainVieBoulePuissante() {
		vie += GAINSUP;
		vie += GAIN;
		if (vie > 20) {
			vie = 20;
		}
	}

	public void perteVieBoulePuissante() {
		vie -= PERTESUP;
		if (vie < 0) {
			vie = 0;
		}
	}

	public int getOrientation() {
		return orientation;
	}

	public void departJoueur() {
		if (this.label != null) {
			this.message.getjLabel().setVisible(false);
			super.label.getjLabel().setVisible(false);
			this.boule.getLabel().getjLabel().setVisible(false);
			jeuServeur.envoi(label);
			jeuServeur.envoi(message);
			jeuServeur.envoi(boule.getLabel());
		}

	}

}