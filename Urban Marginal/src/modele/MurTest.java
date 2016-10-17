/**
 * 
 */
package modele;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Dell
 *
 */
public class MurTest {

	public Mur mur1;
	public Mur mur2;
	/**
	 * Test method for {@link modele.Objet#toucheObjet(modele.Objet)}.
	 */
	@Test
	public void testToucheObjet() {
		
		mur1.posX = 20 ;
		mur1.posY = 10 ;
		mur2.posX = 20 ;
		mur2.posY = 10 ;
		assertTrue(mur1.toucheObjet(mur2));
	}

}
