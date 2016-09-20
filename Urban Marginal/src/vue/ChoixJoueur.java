package vue;

import java.awt.Cursor;

import controleur.Controle;
import controleur.Global;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class ChoixJoueur extends JFrame implements Global {

	private JPanel contentPane;
	private JTextField txtPseudo;
	private Integer numPerso;
	private JLabel lblPersonnage;
	private Controle controle;

	private void lblPrecedent_clic() {
		numPerso--;
		if(numPerso == 0){
			numPerso = NBPERSOS;
		}
			affichePerso();
	}

	private void lblSuivant_clic() {
		if(numPerso == 3){
			numPerso = 0;
		}
			numPerso++;
			affichePerso();
	}

	private void lblGo_clic() {
		if((txtPseudo.getText()).equals("")){
			JOptionPane.showMessageDialog(null,"Le Pseudo est obligatoire");
			txtPseudo.requestFocus();
		}
		else{
			controle.evenementVue(this, "");
		}
		
	}

	private void souris_normale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private void souris_doigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void affichePerso() {
		lblPersonnage.setIcon(new ImageIcon(PERSO + numPerso + MARCHE + 1 + "d" + 1 + EXTIMAGE));
	}

	/**
	 * Create the frame.
	 * @param controle  
	 */

	public ChoixJoueur(Controle controle) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 416, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.controle = controle;

		/**
		 * 
		 */
		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblPrecedent_clic();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				souris_doigt();
			}

			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});

		lblPrecedent.setBounds(62, 144, 41, 43);
		contentPane.add(lblPrecedent);

		/**
		 * 
		 */
		JLabel lblSuivant = new JLabel("");
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				souris_doigt();
			}

			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});

		lblSuivant.setBounds(296, 144, 41, 43);
		contentPane.add(lblSuivant);

		/**
		 * 
		 */

		JLabel lblGo = new JLabel("");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				souris_doigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});

		lblGo.setBounds(318, 204, 49, 59);
		contentPane.add(lblGo);

		/**
		 * 
		 */

		txtPseudo = new JTextField();
		txtPseudo.setBounds(143, 248, 119, 15);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);

		lblPersonnage = new JLabel("");
		lblPersonnage.setBounds(155, 119, 96, 103);
		contentPane.add(lblPersonnage);

		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		lblFond.setIcon(new ImageIcon(FONDCHOIX));
		contentPane.add(lblFond);

		txtPseudo.requestFocus();
		// Initialise numPerso a 1 ce qui affichera le perso numero 1
		numPerso = 1;
		affichePerso();
	}

}
