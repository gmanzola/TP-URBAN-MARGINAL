package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import controleur.Global;
import outils.connexion.Connection;

public class Attaque extends Thread implements Global {

	private Joueur attaquant;
	private JeuServeur jeuServeur;
	Hashtable<Connection, Joueur> lesJoueurs;
	private ArrayList<Mur> lesMurs;

	public Attaque(Joueur attaquant, JeuServeur jeuServeur, ArrayList<Mur> lesMurs,
			Hashtable<Connection, Joueur> lesJoueurs) {

		this.lesJoueurs = lesJoueurs;
		this.lesMurs = lesMurs;
		this.attaquant = attaquant;
		this.jeuServeur = jeuServeur;
		super.start();
	}

	public void pause(long milli, int nano) {
		try {
			Thread.sleep(milli, nano);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Boule laBoule = attaquant.getBoule();
		int orientation = attaquant.getOrientation();
		laBoule.getLabel().getjLabel().setVisible(true);
		Joueur victime = null;

		do {
			if (orientation == GAUCHE) {
				laBoule.setPosX(laBoule.getPosX() - LEPAS);
			} else {
				laBoule.setPosX(laBoule.getPosX() + LEPAS);
			}
			laBoule.getLabel().getjLabel().setBounds(laBoule.getPosX(), laBoule.getPosY(), L_BOULE, H_BOULE);
			
			// on met la condition ici car le test doit etre effectue avant que la boule ne soit envoyer au serveur
				if (attaquant.getNbPuissance() > 0){
					laBoule.getLabel().getjLabel().setIcon(new ImageIcon(BOULEPUISSANTE));
				}
				else{
					laBoule.getLabel().getjLabel().setIcon(new ImageIcon(BOULE));
				}
			jeuServeur.envoi(laBoule.getLabel());
			victime = toucheJoueur();
			
		} while (laBoule.getPosX() > 0 && laBoule.getPosX() < L_ARENE && toucheMur() == false && victime == null);

		if (victime != null && !victime.estMort()) {
			jeuServeur.envoi(HURT);
			System.out.println("Attaquant reste " + attaquant.getNbPuissance() + "boule(s) puissante");
			System.out.println("victime reste :" + victime.getNbbouclier() + "bouclier(s)");
			
			if(victime.getNbbouclier() == 0 && attaquant.getNbPuissance() == 0){
				attaquant.gainVie();
				victime.perteVie();
			}
			else if(victime.getNbbouclier() > 0 && attaquant.getNbPuissance() == 0){
				victime.setNbBouclier(victime.getNbbouclier()-1);
			}
			else if (attaquant.getNbPuissance() > 0 && victime.getNbbouclier() == 0){
				attaquant.gainVieBoulePuissante();
				victime.perteVieBoulePuissante();
				attaquant.setNbPuissance(attaquant.getNbPuissance() - 1);
			}
			else{
				attaquant.setNbPuissance(attaquant.getNbPuissance() - 1);
				victime.setNbBouclier(victime.getNbbouclier() - 1);
			}

			for (int i = 1; i <= NBETATSBLESSE; i++) {
				victime.affiche(BLESSE, i);
				this.pause(80, 80);

			}
			if (victime.estMort()) {
				jeuServeur.envoi(DEATH);
				attaquant.setNbPuissance(3);
				victime.setNbBouclier(2);
					if(victime.getNbPuissance() != 0){
						victime.setNbPuissance(0);
					}

				for (int i = 1; i <= NBETATSMORT; i++) {
					victime.affiche(MORT, i);
					this.pause(120, 0);
				}
				this.pause(5000, 0);

				for (int i = 2; i > 0; i--) {
					victime.affiche(MORT, i);
					this.pause(500, 0);

				}
				attaquant.getBoule().getLabel().getjLabel().setVisible(false);
				jeuServeur.envoi(attaquant.getBoule().getLabel());
				victime.recussite();
				victime.affiche(MARCHE, 1);
				
			} 
				else {
					victime.affiche(MARCHE, 1);
				}
			attaquant.affiche(MARCHE, 1);
		}
		laBoule.getLabel().getjLabel().setVisible(false);
		jeuServeur.envoi(laBoule.getLabel());
	}

	private boolean toucheMur() {
		for (Mur unMur : lesMurs) {
			if (attaquant.getBoule().toucheObjet(unMur)) {
				return true;
			}
		}
		return false;
	}

	public Joueur toucheJoueur() {
		for (Joueur unJoueur : lesJoueurs.values()) {
			if (attaquant.getBoule().toucheObjet(unJoueur)) {
				return unJoueur;
			}
		}
		return null;
	}
}