package controleur;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
		if(leJeu instanceof JeuServeur){
			leJeu.setConnection(connection);
		}
	}
	

	public Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		frmEntreeJeu.setVisible(true);

	}

	// Gestion des evenements du modele
	
	public void receptionInfo(Connection connection, Object info){
		this.leJeu.reception(connection, info);
	}

	public void evenementVue(JFrame uneFrame, Object info) {
		if (uneFrame instanceof EntreeJeu) {
			evenementEntreeJeu(info);
		}
		if (uneFrame instanceof ChoixJoueur) {
			evenementChoixJoueur(info);
		}
	}
	
	private void evenementChoixJoueur(Object info) {
		((JeuClient)this.leJeu).envoi(info);
		frmChoixJoueur.dispose();
		frmArene.setVisible(true);
		
	}

	private void evenementEntreeJeu(Object info) {
		if ((String) info == "serveur") {
			new ServeurSocket(this, PORT);
			leJeu = new JeuServeur(this);
			frmEntreeJeu.dispose();
			frmArene = new Arene();
			((JeuServeur)this.leJeu).constructionMurs();
			frmArene.setVisible(true);
		} 
		else {
			if((new ClientSocket((String) info, PORT,this)).isConnexionOk()){
				leJeu = new JeuClient(this);
				frmArene = new Arene();
				leJeu.setConnection(connection);
				frmEntreeJeu.dispose();
				frmChoixJoueur = new ChoixJoueur(this);
				frmChoixJoueur.setVisible(true);
			}
		}

	}
	
	public void evemenementModele(Object unJeu,String ordre, Object info){
		if(unJeu instanceof JeuServeur){
			evenementJeuServeur(ordre,info);
			
		}
		
	}

	private void evenementJeuServeur(String ordre, Object info) {
		if(ordre == "ajout mur"){
			frmArene.ajoutMur((JLabel)info);
		}
		
	}

}
