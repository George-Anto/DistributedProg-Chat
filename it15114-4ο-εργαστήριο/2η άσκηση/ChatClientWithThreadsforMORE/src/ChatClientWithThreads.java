import java.net.*;
import java.io.*;

// Spawn a different thread for receiving messages
// Due to multiple threads used, simultaneous communication is now possibl

public class ChatClientWithThreads {
	
	private static final int PORT = 1235;
	
	public static void main(String args[]) throws IOException
	{
		InetAddress host = InetAddress.getLocalHost();
		Socket dataSocket = new Socket(host,PORT);
		SendThread send = new SendThread(dataSocket);
		Thread thread = new Thread(send);
		thread.start();
		ReceiveThread receive = new ReceiveThread(dataSocket);
		Thread thread2 = new Thread(receive);
		thread2.start();
	}
}	

class SendThread implements Runnable{

	private Socket dataSocket;
	
	public SendThread(Socket soc){
		dataSocket = soc;
	}
	
	public void run() {
		try{
			System.out.println("Connection established");
			BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
			OutputStream os = dataSocket.getOutputStream();
			PrintWriter out = new PrintWriter(os,true);
			System.out.print("Send message, CLOSE for exit:");
			String msg = user.readLine();
			while(!msg.equals("CLOSE")) {
				out.println(msg);
				System.out.print("Send message:");
				msg = user.readLine();
			}	
			out.println(msg);
			dataSocket.close();
			System.out.println("Data Socket closed");
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
}

class ReceiveThread implements Runnable{

	private Socket dataSocket;
	
	public ReceiveThread(Socket soc){
		dataSocket = soc;
	}
	
	public void run() {
		try{
			// Get the ingoing stream
			InputStream is = dataSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String msg = in.readLine();
			while(msg != null) {
				System.out.println("Received message: " + msg);
				System.out.print("Send message:");
				msg = in.readLine();
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
}

