package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Global;
import outils.connexion.Connection;

public class Attaque extends Thread implements Global{
	
	private Joueur attaquant;
	private JeuServeur jeuServeur;
	Hashtable<Connection, Joueur> lesJoueurs;
	private ArrayList<Mur> lesMurs;
	
	public Attaque(Joueur attaquant, JeuServeur jeuServeur, ArrayList<Mur> lesMurs, Hashtable<Connection, Joueur> lesJoueurs){
		
		this.lesJoueurs = lesJoueurs;
		this.lesMurs = lesMurs;
		this.attaquant =  attaquant;
		this.jeuServeur = jeuServeur;
		super.start();
	}
	
	public void pause(long milli, int nano){
		try {
			Thread.sleep(milli, nano);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		Boule laBoule = attaquant.getBoule();
		int orientation = attaquant.getOrientation();
		laBoule.getLabel().getjLabel().setVisible(true);
		Joueur victime = null;
		
		do {
			if(orientation == GAUCHE){
				laBoule.setPosX(laBoule.getPosX()-LEPAS);
			}
			else{
				laBoule.setPosX(laBoule.getPosX()+LEPAS);
			}
			laBoule.getLabel().getjLabel().setBounds(laBoule.getPosX(), laBoule.getPosY(), L_BOULE, H_BOULE);
			jeuServeur.envoi(laBoule.getLabel());
			victime = toucheJoueur();
		} 
		while(laBoule.getPosX() > 0 && laBoule.getPosX() < L_ARENE && toucheMur() == false && victime == null);
		
		if(victime != null && victime.estMort() == false){
			jeuServeur.envoi(HURT);
			victime.perteVie();
			attaquant.gainVie();
			for(int i = 1 ; i < NBETATSBLESSE; i++){
				victime.affiche(BLESSE, i);
				pause(80,0);
			}
			if(victime.estMort()){
				jeuServeur.envoi(DEATH);
				for(int i=1 ; i < NBETATSMORT; i++){
					victime.affiche(MORT, i);
					pause(80,0);
				}
			}
					else{
						victime.affiche(MARCHE, 1);
					}
			attaquant.affiche(MARCHE, 1);
		}	
		laBoule.getLabel().getjLabel().setVisible(false);
		pause(15,15);
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
	
	public Joueur toucheJoueur(){
		for(Joueur unJoueur : lesJoueurs.values()){
			if(attaquant.getBoule().toucheObjet(unJoueur)){
				return unJoueur;
			}
		}
		return null;
}
}
