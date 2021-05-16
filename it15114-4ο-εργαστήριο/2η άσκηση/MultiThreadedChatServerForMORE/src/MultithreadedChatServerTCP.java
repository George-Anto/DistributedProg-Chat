import java.net.*;
import java.io.*;

public class MultithreadedChatServerTCP {
	
	private static final int PORT = 1235;
	//Ορισμός των συνολικών πελατών που πρέπει να δημιουργηθούν για να τρέξει το chat
	static int totalClients = 3;
	//Πίνακας με όλα τα sockets που θα δημιουργηθούν, ένα για κάθε πελάτη
	static Socket[] sockets = new Socket[totalClients];
	
	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		
		int i = 0;
		
		ServerThread[] serverThreads = new ServerThread[totalClients];
		
		System.out.println("Server is waiting for " + totalClients + " clients to connect.");
		
		//Όσο δεν έχουν συνδεθεί όλοι οι πελάτες ξαναμπαίνουμε στην επανάληψη
		while (i < totalClients) {	

			System.out.println("Server is waiting for client " + i + " in port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());
			sockets[i] = dataSocket;
            
			//Δημιουργία των νημάτων που χειρίζονται την επικοινωνία με κάθε πελάτη
			serverThreads[i] = new ServerThread(i);
			i++;
		}
		
		//Τα νήματα ξεκινούν το run αφού έχουν δημιουργηθεί όλες οι συνδέσεις
		for(i = 0; i < totalClients; i++) {
			serverThreads[i].start();
		}
		
		//Το κυρίως πρόγραμμα περιμένει να τερματιστούν όλα τα νήματα, δηλαδή
		//να λήξει η επικοινωνία πριν τερματιστεί
		for(i = 0; i < totalClients; i++) {
			try {
				serverThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Connection terminated.");
	}
}
