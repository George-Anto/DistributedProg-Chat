package serverPi;

import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;

public class MultiThreadedServerTCP {
	
	private static final int PORT = 1235;
	//���������� ������������ ���������� ��� ��� ���������� ��� � ��� ���� ����������
	static double Pi = 0;
	static Lock lock = new ReentrantLock(); 
	
	public static void main(String args[]) throws IOException {
		
		
		//������� ��� ��������� ������� ��� �� �������������
		int numThreads = 3;
		long numSteps = 1000000000;
		long threadPortion = numSteps / numThreads;
		double step = 1.0 / (double)numSteps;

		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		
		int i = 0;
		//������� ��� �� ��������� �� ������ ��� �� ������������ �� ���� �������
		ServerThread sthreads[] = new ServerThread[numThreads];
		
		while (i < numThreads) {	

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			//������� ��� ��������� ��� �� ���������� �� ������ ��� �� ������ ��� ������� ����
			sthreads[i] = new ServerThread(dataSocket, i, step, threadPortion);
			sthreads[i].start();
			i ++;
		}
		
		//�� ������ ��������� ��������� �� ������ ��� ���� ������� �� ������������ ��� �������
		//���� ��� ���� �������� ��������� �� � ��� ������������
		for (i = 0; i < numThreads; i++) {
			try {
				sthreads[i].join();
           		} catch (InterruptedException e) {}
		}
		
		System.out.println("Computed Pi = " + Pi + ".");
		
	}
}


