package serverPi;

import java.net.*;
import java.io.*;

public class ServerProtocol {
	
	
	//���� ������ ��� ����������� ������� � �������� ��� ������� � ��� ����� �� �������� ��� 
	//�������� �������� ���� ������ ������� ����������� ��� ������������ ����������
	public void addPi(String aPartialPi) {
		double partialPi = Double.parseDouble(aPartialPi); 
		MultiThreadedServerTCP.lock.lock();
		MultiThreadedServerTCP.Pi += partialPi;
		MultiThreadedServerTCP.lock.unlock();
	}
}