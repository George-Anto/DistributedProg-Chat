package serverPi;

import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;

public class MultiThreadedServerTCP {
	
	private static final int PORT = 1235;
	//Δημιουργία μοιραζόμενων μεταβλητών για την αποθήκευση του π και μιας κλειδαριάς
	static double Pi = 0;
	static Lock lock = new ReentrantLock(); 
	
	public static void main(String args[]) throws IOException {
		
		
		//Ορισμός των συνολικών νημάτων που θα δημιουργηθούν
		int numThreads = 3;
		long numSteps = 1000000000;
		long threadPortion = numSteps / numThreads;
		double step = 1.0 / (double)numSteps;

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		
		int i = 0;
		//Πίνακας που θα φιλοξενεί τα νήματα που θα επικοινωνούν με τους πελάτες
		ServerThread sthreads[] = new ServerThread[numThreads];
		
		while (i < numThreads) {	

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			//Πέρασμα των ορισμάτων που θα χρειαστούν τα νήματα για να κάνουν την δουλεία τους
			sthreads[i] = new ServerThread(dataSocket, i, step, threadPortion);
			sthreads[i].start();
			i ++;
		}
		
		//Το κυρίως πρόγραμμα περιμένει τα νήματα και τους πελάτες να ολοκληρώσουν την δουλεία
		//τους και στην συνέχεια εκτυπώνει το π που υπολογίστηκε
		for (i = 0; i < numThreads; i++) {
			try {
				sthreads[i].join();
           		} catch (InterruptedException e) {}
		}
		
		System.out.println("Computed Pi = " + Pi + ".");
		
	}
}


