package serverPi;

import java.io.*;
import java.net.*;

class ServerThread extends Thread
{
	private Socket dataSocket;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
   	private PrintWriter out;
   	//Το νήμα κρατάει στοιχεία που χρειάζονται για τον υπολογισμό του μερικού π
   	private int myId;
   	private double step;
   	private long threadPortion;

   	public ServerThread(Socket socket, int anId, double step, long threadPortion)
   	{
      		dataSocket = socket;
      		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
			myId = anId;
			this.step = step;
			this.threadPortion = threadPortion;
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
      		}
    	}

	public void run()
	{
   		String stringPartialPi;
		
		try {
			//Το κάθε νήμα στέλνει τα στοιχεία που χρειάζεται ο πέλάτης που επικοινωνεί 
			//για να κάνει την εργασία που του έχει ανατεθεί
			out.println(myId);
			out.println(step);
			out.println(threadPortion);
			stringPartialPi = in.readLine();
			//Δμηιουτγεία και κλήση ενός αντικειμένου πρωτοκόλλου για την άθροιση στο ολικό π
			ServerProtocol app = new ServerProtocol();
			app.addPi(stringPartialPi);

			//Η επικοινωνία τερματίζεται μετά την λήξη της εργασίας 
			dataSocket.close();
			System.out.println("Data socket " + myId + " closed");

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
