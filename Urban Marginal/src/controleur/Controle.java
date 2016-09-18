package controleur;
import javax.swing.JFrame;

import modele.Jeu;
import modele.JeuServeur;
import outils.connexion.ClientSocket;
import outils.connexion.ServeurSocket;
import vue.EntreeJeu;


public class Controle {
	private EntreeJeu frmEntreeJeu;
	private Jeu leJeu;

	public static void main(String[] args) {
		new Controle();
		
	}
	public Controle() {
		this.frmEntreeJeu = new EntreeJeu(this) ;
		frmEntreeJeu.setVisible(true);
		
		
	}
	
	// Gestion des evenements du modele
	
		public void evenementVue(JFrame uneFrame,Object info) {
			if (uneFrame instanceof EntreeJeu){
			evenementEntreeJeu(info);
			}	
		}
	private void evenementEntreeJeu(Object info) {
		if((String)info=="serveur"){
			new ServeurSocket(this,6666);
			leJeu = new JeuServeur(this);
			frmEntreeJeu.dispose();
			
			
		}
			else{
				// DEMANDER A LA PROF CE QUE FAIT EXACTEMENT CETTE LIGNE
				(new ClientSocket((String)info,6666,this)).isConnexionOk();
				
			}
			
		}

	}
