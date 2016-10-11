package modele;

import controleur.Global;

public class Attaque extends Thread implements Global{
	
	private Joueur attaquant;
	private JeuServeur jeuServeur;
	
	public Attaque(Joueur attaquant, JeuServeur jeuServeur){
		
		
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
		laBoule.label.getjLabel().setVisible(true);
		
		do {
			if(orientation == GAUCHE){
				laBoule.setPosX(laBoule.getPosX() - LEPAS);
			}
			else{
				laBoule.setPosX(laBoule.getPosX() + LEPAS);
			}
			laBoule.label.getjLabel().setBounds(laBoule.getPosX(), laBoule.getPosY(), L_BOULE, H_BOULE);
			jeuServeur.envoi(laBoule.label);
		} 
		while(laBoule.getPosX() >= 0 || laBoule.getPosX() <= L_ARENE);
		laBoule.label.getjLabel().setVisible(false);
		pause(15,0);
		jeuServeur.envoi(laBoule.label);
	}
}
