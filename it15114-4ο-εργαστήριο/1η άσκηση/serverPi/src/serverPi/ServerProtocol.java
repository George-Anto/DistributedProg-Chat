package serverPi;

import java.net.*;
import java.io.*;

public class ServerProtocol {
	
	
	//Στην μέθοδο του πρωτοκόλλου γίνεται η πρόσθεση του μερικού π στο ολικό με κλείδωμα της 
	//κρίσιμης περιοχής όταν δηλαδή γίνεται τροποποίηση της μοιραζόμενης μεταβλητής
	public void addPi(String aPartialPi) {
		double partialPi = Double.parseDouble(aPartialPi); 
		MultiThreadedServerTCP.lock.lock();
		MultiThreadedServerTCP.Pi += partialPi;
		MultiThreadedServerTCP.lock.unlock();
	}
}