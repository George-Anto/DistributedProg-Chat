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
   	//�� ���� ������� �������� ��� ����������� ��� ��� ���������� ��� ������� �
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
			//�� ���� ���� ������� �� �������� ��� ���������� � ������� ��� ����������� 
			//��� �� ����� ��� ������� ��� ��� ���� ��������
			out.println(myId);
			out.println(step);
			out.println(threadPortion);
			stringPartialPi = in.readLine();
			//����������� ��� ����� ���� ������������ ����������� ��� ��� ������� ��� ����� �
			ServerProtocol app = new ServerProtocol();
			app.addPi(stringPartialPi);

			//� ����������� ������������ ���� ��� ���� ��� �������� 
			dataSocket.close();
			System.out.println("Data socket " + myId + " closed");

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
