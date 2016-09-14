package controleur;
import javax.swing.JFrame;

import vue.EntreeJeu;


public class Controle {
	private EntreeJeu frmEntreeJeu;

	public static void main(String[] args) {
		new Controle();
		
	}
	public Controle() {
		frmEntreeJeu = new EntreeJeu();
		frmEntreeJeu.setVisible(true);
		
	}
	
	// Gestion des evenements du modele
	
		public void evenementVue(JFrame uneFrame,Object info) {
			if (uneFrame instanceof EntreeJeu){
			evenementEntreeJeu(info);
			}	
		}
	private void evenementEntreeJeu(Object info) {
		// TODO Auto-generated method stub
		
	}

		

}
