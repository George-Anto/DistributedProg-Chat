import java.net.*;
import java.io.*;

public class ServerProtocol {

	public String processRequest(String theInput, int anId) {
		//����� ����������� ��� �� �������� ���� server ���� ���� - ������� 
		//������� �� ������
		System.out.println("Received message from client " + anId + ": " + theInput);
		String theOutput = theInput;
		System.out.println("Send message to all other clients: " + theOutput);
		return theOutput;
	}
}