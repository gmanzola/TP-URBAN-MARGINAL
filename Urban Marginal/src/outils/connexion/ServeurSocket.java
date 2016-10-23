package outils.connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurSocket extends Thread {

	private Object leRecepteur;
	private ServerSocket serverSocket;

	public ServeurSocket(Object leRecepteur, int port) {

		this.leRecepteur = leRecepteur;
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("erreur grave lors de la cr�ation du socket serveur : " + e);
			System.exit(0);
		}
		super.start();
	}

	public void run() {
		Socket socket;
		while (true) {
			try {
				//System.out.println("le serveur attend..");
				socket = serverSocket.accept();
				//System.out.println("Un client s'est connect�");
				new Connection(socket, leRecepteur);

			} catch (IOException e) {
				System.out.println("erreur lors de la cr�ation du socket : " + e);
				System.exit(0);
			}

		}

	}

}