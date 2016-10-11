package modele;

import java.io.Serializable;

import javax.swing.JLabel;

public class Label implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int nbLabel;
	private int numLabel;
	private JLabel jLabel;
	
	
	public Label (int numLabel, JLabel jLabel){
		this.numLabel = numLabel;
		this.jLabel = jLabel;
		
	}
	
	/**
	 * @return the numLabel
	 */
	public int getNumLabel() {
		return this.numLabel;
	}

	/**
	 * @return the jLabel
	 */
	public JLabel getjLabel() {
		return this.jLabel;
	}

	/**
	 * @return the nbLabel
	 */
	public static int getNbLabel() {
		return nbLabel;
	}

	/**
	 * @param nbLabel the nbLabel to set
	 */
	public static void setNbLabel(int nbLabel) {
		Label.nbLabel = nbLabel;
	}

}
