import java.io.*;
import java.net.*;

class ServerThread extends Thread
{
	private Socket myDataSocket;
	private InputStream is;
   	private BufferedReader in;
   	//� ��� ������ ��� � �������� ��� ��������� ������� ����� ������� �� ���������
   	//������ ��� ��� �� ������ ���� ��� �� sockes ��� �� ��������� �� ������ ��� 
   	//�������� �� ���� ���� ��� ��� ������ ��� ���������� �� ����� ���� ����������
   	//������� ��� ����������� ��� chat
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
   				//���� �� i ����� ��� �� �� id ���  ��������� ������� ������������
   				//��� ������� ��� ���������� socket
   				if(i == myId) {
   	   				is = MultithreadedChatServerTCP.sockets[i].getInputStream();
   	   				in = new BufferedReader(new InputStreamReader(is));
   	   				//�� ���� ��� ��������� ����������� ������������ ��� ������
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
				//�� ������ ����������� �� ���� ��� ���� ������ ��� �������������
				//��� ������ ��� ���� ����� ���
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
			
		
