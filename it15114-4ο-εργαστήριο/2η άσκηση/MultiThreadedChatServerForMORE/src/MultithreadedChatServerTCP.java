import java.net.*;
import java.io.*;

public class MultithreadedChatServerTCP {
	
	private static final int PORT = 1235;
	//������� ��� ��������� ������� ��� ������ �� ������������� ��� �� ������ �� chat
	static int totalClients = 3;
	//������� �� ��� �� sockets ��� �� �������������, ��� ��� ���� ������
	static Socket[] sockets = new Socket[totalClients];
	
	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		
		int i = 0;
		
		ServerThread[] serverThreads = new ServerThread[totalClients];
		
		System.out.println("Server is waiting for " + totalClients + " clients to connect.");
		
		//��� ��� ����� �������� ���� �� ������� ������������� ���� ���������
		while (i < totalClients) {	

			System.out.println("Server is waiting for client " + i + " in port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());
			sockets[i] = dataSocket;
            
			//���������� ��� ������� ��� ����������� ��� ����������� �� ���� ������
			serverThreads[i] = new ServerThread(i);
			i++;
		}
		
		//�� ������ �������� �� run ���� ����� ������������ ���� �� ���������
		for(i = 0; i < totalClients; i++) {
			serverThreads[i].start();
		}
		
		//�� ������ ��������� ��������� �� ������������ ��� �� ������, ������
		//�� ����� � ����������� ���� �����������
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
