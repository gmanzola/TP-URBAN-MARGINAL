package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

public class JeuClient extends Jeu {
	
	private Connection connection;
	
/**
 * @param controle 
 */
	public JeuClient(Controle controle) {
		super.controle = controle;

	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;

	}

	@Override
	public void reception(Connection connection, Object info) {
		if(info instanceof JPanel){
			controle.evemenementModele(this,"ajout panel murs", info);
		}
		if(info instanceof Label){
			controle.evemenementModele(this, "ajout joueur", info);
		}
		if(info instanceof String){
			controle.evemenementModele(this, "remplace chat", info);
		}
		if(info instanceof Integer){
			controle.evemenementModele(this, "son", info);
		}

	}

	@Override
	public void deconnection(Connection connection) {
		System.exit(0);
	}
	
	public void envoi(Object info) {
		super.envoi(this.connection, info);

	}
	
	

}
