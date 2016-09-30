package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

public class JeuServeur extends Jeu implements Global {
	
	private ArrayList<Mur> lesMurs = new ArrayList<>();
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<>() ;

	public JeuServeur(Controle controle) {
		super.controle = controle;
		
		Label.setNbLabel(0);
	}
	
	public void constructionMurs(){
		for(int k = 0; k < NBMURS; k++){
			lesMurs.add(new Mur());
			controle.evemenementModele(this, "ajout mur", lesMurs.get(k).getLabel().getjLabel());
		}
		
	}

	@Override
	public void setConnection(Connection connection) {
		lesJoueurs.put(connection, new Joueur());

	}

	@Override
	public void reception(Connection connection, Object info) {
		
		System.out.println(info);

	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub

	}

}
