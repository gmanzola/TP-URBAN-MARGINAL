package controleur;

import javax.swing.JFrame;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

public class Controle implements Global {
	private EntreeJeu frmEntreeJeu;
	private Jeu leJeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Connection connection;
	

	public static void main(String[] args) {
		new Controle();

	}
	
	public void setConnection(Connection connection){
		this.connection=connection;
	}

	public Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		frmEntreeJeu.setVisible(true);

	}

	// Gestion des evenements du modele

	public void evenementVue(JFrame uneFrame, Object info) {
		if (uneFrame instanceof EntreeJeu) {
			evenementEntreeJeu(info);
		}
		if (uneFrame instanceof ChoixJoueur) {
			evenementChoixJoueur();
		}
	}

	private void evenementChoixJoueur() {
		// A FAIRE leJeu.(JeuClient.envoi(info));
	}

	private void evenementEntreeJeu(Object info) {
		if ((String) info == "serveur") {
			new ServeurSocket(this, PORT);
			leJeu = new JeuServeur(this);
			frmEntreeJeu.dispose();
			(frmArene = new Arene()).setVisible(true);
		
		} else {
			(new ClientSocket((String) info, PORT,this)).isConnexionOk();
			frmArene = new Arene();
			leJeu.setConnection(connection);
			frmEntreeJeu.dispose();
			(frmChoixJoueur = new ChoixJoueur(this)).setVisible(true);
		}

	}

}
