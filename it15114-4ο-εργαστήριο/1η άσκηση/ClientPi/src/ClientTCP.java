import java.net.*;
import java.io.*;

public class ClientTCP {
	
	private static final String HOST = "localhost";
	private static final int PORT = 1235;

	public static void main(String args[]) throws IOException {

		Socket dataSocket = new Socket(HOST, PORT);
		
		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os,true);
		       	
		System.out.println("Connection to " + HOST + " established");
		
		String stringId, stringStep, stringThreadPortion;
		
		//Ο πελάτης διαβάζει τα στοχεία που χρειάζεται για τον υπολογισμό που του αναλογεί
		stringId = in.readLine();
		stringStep = in.readLine();
		stringThreadPortion = in.readLine();
		
		//Τα μετατρέπει σε αριθμητική μορφή από αλφαριθμητική για να μπορέσει να τα χρησιμοποιήσει
		int myId = Integer.parseInt(stringId);
		double step = Double.parseDouble(stringStep);
		long threadPortion = Integer.parseInt(stringThreadPortion);
		
		int myStart = (int) (myId * threadPortion);
		int myEnd = (int) ((myId + 1) * threadPortion);
		//Για να μην περισσέψει κομμάτι του εμβαδού από τον τελευταίο πελάτη-εργάτη
		//μπορούσαμε είτε να στείλουμε την πληροφορία των συνολικών πελατών μέσω της
		//σύνδεσης σε κάθε πελάτη για να ξέρει αν είναι ο τελευταίος και να εκτελέσει
		//την παρακάτω εντολή που είναι στα σχόλια είτε να θεώρήσουμε πως αυτή η 
		//πληροφορία είναι γνωστή όπως λέει η εκφώνηση
		//Για να μπορεί να αλλάζει το numThreads στον κώδικα του server και να μην
		//επηρεάζεται το αποτέλεσμα εδώ, την έχω βάλει στα σχόλια 
		//if(myId == (3 - 1))
			//myEnd = numSteps;
		double partialPi = 0;
		
		
		//Δεν έχω χρησιμοποιήσει κλάση πρωτόκολλο για τον υπολογισμό του μερικού
		//π στους πελάτες αλλά ο υπολογισμός γίνεται απευθείας εδώ από κάτω και 
		//το αποτέλεσμα στέλνεται στο νήμα που επικοινωνεί με τον κάθε πελάτη για
		//περαιτέρω χρήση του
		for (long i = myStart; i < myEnd; i++) {
			double x = ((double)i+0.5)*step;
			partialPi += 4.0/(1.0+x*x);
		}
		
		out.println(partialPi);

		//Στην συνέχεια τερματίζεται η επικοινωνία
		dataSocket.close();
		System.out.println("Data Socket closed");
	}
}			
