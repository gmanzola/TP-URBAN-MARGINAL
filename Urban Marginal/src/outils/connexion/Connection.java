package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import controleur.Controle;

public class Connection extends Thread {

	private Object leRecepteur;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Connection(Socket socket, Object leRecepteur) {
		this.leRecepteur = leRecepteur;
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("erreur lors de l'appel du socket : " + e);
			System.exit(0);
		}
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("erreur lors de l'appel du socket : " + e);
			System.exit(0);
		}
		super.start();
		((controleur.Controle)this.leRecepteur).setConnection(this);
	}

	public void run() {
		boolean inOk = true;
		Object reception;
		while (inOk) {
			try {
				reception = in.readObject();
				((controleur.Controle)this.leRecepteur).receptionInfo(this,reception);
			} catch (ClassNotFoundException e) {
				System.out.println("erreur format d'objet : " + e);
				System.exit(0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Un client s'est d�connect�");
				inOk = false;
				((Controle)this.leRecepteur).deconnection(this);
				try {
					in.close();
				} catch (IOException e1) {
					System.out.println("La fermeture du canal s'est mal pass� : " + e);
				}

			}
		}

	}

	public synchronized void envoi(Object unObjet) {
		try {
			this.out.reset();
			out.writeObject(unObjet);
			this.out.reset();
			out.flush();
		} catch (IOException e) {
			System.out.println("erreur sur l'objet OUT" + e);
		}
	}

}
