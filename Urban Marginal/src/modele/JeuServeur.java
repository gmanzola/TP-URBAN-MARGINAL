package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

public class JeuServeur extends Jeu implements Global {

	private ArrayList<Mur> lesMurs = new ArrayList<>();
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<>();
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<>();
	private int nbJoueurTue = 0;

	public JeuServeur(Controle controle) {
		super.controle = controle;

		Label.setNbLabel(0);
	}

	public void constructionMurs() {
		for (int k = 0; k < NBMURS; k++) {
			lesMurs.add(new Mur());
			controle.evemenementModele(this, "ajout mur", lesMurs.get(k).getLabel().getjLabel());
		}

	}

	@Override
	public void setConnection(Connection connection) {
		lesJoueurs.put(connection, new Joueur(this));

	}

	@Override
	public void reception(Connection connection, Object info) {
		// System.out.println(info);
		String[] infos;
		infos = ((String) info).split(SEPARE);

		switch (Integer.parseInt(infos[0])) {

		case PSEUDO:

			controle.evemenementModele(this, "envoi panel murs", connection);
			for (Joueur unJoueur : lesJoueursDansLordre) {
				super.envoi(connection, unJoueur.getLabel());
				super.envoi(connection, unJoueur.getMessage());
				super.envoi(connection, unJoueur.getBoule().getLabel());
			}
			lesJoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[2]), lesJoueurs, lesMurs);
			this.lesJoueursDansLordre.add(this.lesJoueurs.get(connection));
			String laPhrase = "***  " + lesJoueurs.get(connection).getPseudo() + "  vient de se connecter ***";
			controle.evemenementModele(this, "ajout phrase", laPhrase);
			break;

		case CHAT:
			laPhrase = lesJoueurs.get(connection).getPseudo() + " > " + infos[1];
			controle.evemenementModele(this, "ajout phrase", laPhrase);
			break;

		case ACTION:

			if (!lesJoueurs.get(connection).estMort()) {
				lesJoueurs.get(connection).action(Integer.parseInt(infos[1]), lesJoueurs, lesMurs);
			}

			break;
		}
	}

	@Override
	public void deconnection(Connection connection) {
		lesJoueurs.get(connection).departJoueur();
		lesJoueurs.remove(connection);

	}

	public void nouveauLabelJeu(Label label) {
		controle.evemenementModele(this, "ajout joueur", label.getjLabel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.Jeu#envoi(outils.connexion.Connection, java.lang.Object)
	 */

	public void envoi(Object info) {
		for (Connection unJoueur : lesJoueurs.keySet()) {
			super.envoi(unJoueur, info);
		}
	}

	/**
	 * @return the nbJoueurTue
	 */
	public int getNbJoueurTue() {
		return nbJoueurTue;
	}

	/**
	 * @param nbJoueurTue
	 *            the nbJoueurTue to set
	 */
	public void setNbJoueurTue(int nbJoueurTue) {
		this.nbJoueurTue = nbJoueurTue;
	}
}
