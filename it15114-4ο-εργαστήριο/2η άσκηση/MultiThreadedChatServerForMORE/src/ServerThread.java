import java.io.*;
import java.net.*;

class ServerThread extends Thread
{
	private Socket myDataSocket;
	private InputStream is;
   	private BufferedReader in;
   	//Η ροή εξόδου και η αποστολή του μηνύματος γίνεται πλέον πίνακας με συνολικές
   	//θέσεις όσα και τα νήματα αλλά και τα sockes για να στέλνεται το μήνυμα που 
   	//λαμβάνει το κάθε νήμα από τον πελάτη που εξυπηρετεί σε όλους τους υπόλοιπους
   	//πελάτες που συμματέχουν στο chat
	private OutputStream[] os;
   	private PrintWriter[] out;
   	private int myId;
	private static final String EXIT = "CLOSE";

   	public ServerThread(int anId) {	
   		myId = anId;
   		os = new OutputStream[MultithreadedChatServerTCP.totalClients];
   		out = new PrintWriter[MultithreadedChatServerTCP.totalClients];
   	}

	public void run() {
		
		for(int i = 0; i < MultithreadedChatServerTCP.totalClients; i++) {
   			try {
   				//Όταν το i είναι ίσο με το id του  τρέχοντος νήματος δημιουργόυμε
   				//ροή εισόδου στο αντίστοιχο socket
   				if(i == myId) {
   	   				is = MultithreadedChatServerTCP.sockets[i].getInputStream();
   	   				in = new BufferedReader(new InputStreamReader(is));
   	   				//Σε όλες τις υπόλοιπες περιπτώσεις δημιουργούμε ροή εξόδου
   	   			}else
   	   			{
   	   				os[i] = MultithreadedChatServerTCP.sockets[i].getOutputStream();
   	   				out[i] = new PrintWriter(os[i], true);
   	   			}
   			}catch (IOException e)	{		
   				System.out.println("I/O Error " + e);
      		}
   		}
		
   		String inmsg, outmsg;
		
		try {
			inmsg = in.readLine();
			ServerProtocol app = new ServerProtocol();
			outmsg = app.processRequest(inmsg, myId);
			while(!outmsg.equals(EXIT)) {
				//Το μήνυμα αποστέλεται σε όλες τις ροές εξόδου που δημιουργήσαμε
				//Όχι δηλαδή και στον εαυτό του
				for(int i = 0; i < MultithreadedChatServerTCP.totalClients; i++) {
					if(i != myId)
						out[i].println(outmsg);
				}
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg, myId);
			}		

			myDataSocket.close();
			System.out.println("Data socket closed");

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
