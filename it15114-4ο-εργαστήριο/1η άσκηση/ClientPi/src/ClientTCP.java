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
		
		//� ������� �������� �� ������� ��� ���������� ��� ��� ���������� ��� ��� ��������
		stringId = in.readLine();
		stringStep = in.readLine();
		stringThreadPortion = in.readLine();
		
		//�� ���������� �� ���������� ����� ��� ������������� ��� �� �������� �� �� ��������������
		int myId = Integer.parseInt(stringId);
		double step = Double.parseDouble(stringStep);
		long threadPortion = Integer.parseInt(stringThreadPortion);
		
		int myStart = (int) (myId * threadPortion);
		int myEnd = (int) ((myId + 1) * threadPortion);
		//��� �� ��� ���������� ������� ��� ������� ��� ��� ��������� ������-������
		//���������� ���� �� ��������� ��� ���������� ��� ��������� ������� ���� ���
		//�������� �� ���� ������ ��� �� ����� �� ����� � ���������� ��� �� ���������
		//��� �������� ������ ��� ����� ��� ������ ���� �� ���������� ��� ���� � 
		//���������� ����� ������ ���� ���� � ��������
		//��� �� ������ �� ������� �� numThreads ���� ������ ��� server ��� �� ���
		//����������� �� ���������� ���, ��� ��� ����� ��� ������ 
		//if(myId == (3 - 1))
			//myEnd = numSteps;
		double partialPi = 0;
		
		
		//��� ��� �������������� ����� ���������� ��� ��� ���������� ��� �������
		//� ����� ������� ���� � ����������� ������� ��������� ��� ��� ���� ��� 
		//�� ���������� ��������� ��� ���� ��� ����������� �� ��� ���� ������ ���
		//��������� ����� ���
		for (long i = myStart; i < myEnd; i++) {
			double x = ((double)i+0.5)*step;
			partialPi += 4.0/(1.0+x*x);
		}
		
		out.println(partialPi);

		//���� �������� ������������ � �����������
		dataSocket.close();
		System.out.println("Data Socket closed");
	}
}			
