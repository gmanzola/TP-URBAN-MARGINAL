package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;
import outils.connexion.Connection;

public class Boule extends Objet implements Global{
	
	private JeuServeur jeuServeur;
	
	public Boule(JeuServeur jeuServeur){
		
		this.jeuServeur = jeuServeur;
		super.label = new Label(Label.nbLabel, new JLabel());
		Label.nbLabel++;
		super.label.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		super.label.getjLabel().setVerticalAlignment(SwingConstants.CENTER);
		super.label.getjLabel().setBounds(0, 0, L_BOULE, H_BOULE);
		super.label.getjLabel().setIcon(new ImageIcon(BOULE));
		super.label.getjLabel().setVisible(false);
		jeuServeur.nouveauLabelJeu(super.label);
	}
	
	public void tireBoule(Joueur attaquant, ArrayList<Mur> lesMurs, Hashtable<Connection, Joueur> lesJoueurs){
		if (attaquant.getOrientation() == GAUCHE){
			this.posX = attaquant.getPosX()-L_BOULE-1;
		}
		else{
			this.posX = attaquant.getPosX()+L_PERSO+1;
		}
		this.posY = attaquant.posY+H_PERSO/2;
		new Attaque(attaquant, jeuServeur, lesMurs, lesJoueurs);
	}
}
