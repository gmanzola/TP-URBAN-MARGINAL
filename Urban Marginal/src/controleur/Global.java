package controleur;

public interface Global {

	public static final Integer PORT = 6666;
	public static final String SEPARATOR = "//";
	public static final String CHEMIN = "media" + SEPARATOR;
	public static final String CHEMINFONDS = CHEMIN + "fonds" + SEPARATOR;
	public static final String FONDCHOIX = CHEMINFONDS + "fondchoix.jpg";

	public static final Integer GAUCHE = 0;
	public static final Integer DROITE = 1;
	public static final String CHEMINPERSOS = CHEMIN + "personnages" + SEPARATOR;
	public static final String PERSO = CHEMINPERSOS + "perso";
	public static final String EXTIMAGE = ".gif";
	public static final String MARCHE = "marche";
	public static final String BLESSE = "touche";
	public static final String MORT = "mort";
	public static final Integer NBPERSOS = 3;
	public static final Integer H_PERSO = 44;
	public static final Integer L_PERSO = 39;
	public static final String SEPARE = "~";
	public static final Integer PSEUDO = 0;
	public static final Integer H_ARENE = 600;
	public static final Integer L_ARENE = 800;
	public static final Integer H_CHAT = 200;
	public static final Integer H_SAISIE = 25;
	public static final Integer MARGE = 5;
	public static final String FONDARENE = CHEMINFONDS + "fondarene.jpg";
	public static final Integer NBMURS = 20;
	public static final String CHEMINMURS = CHEMIN + "murs" + SEPARATOR;
	public static final String MUR = CHEMINMURS + "mur.gif";
	public static final Integer H_MUR = 35;
	public static final Integer L_MUR = 34;
	
	
	
	

}
