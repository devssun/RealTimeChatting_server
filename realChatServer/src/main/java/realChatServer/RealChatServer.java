package realChatServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class RealChatServer  extends WebSocketServer{

	public RealChatServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port)); //������ ���鶧 ��Ʈ ����(���ο� ��Ʈ �־ ������ �������)
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onClose(WebSocket conn, int arg1, String arg2, boolean arg3) { 
		// TODO Auto-generated method stub
//		this.sendToAll(conn+"ä�ù��� �����̽��ϴ�."); //���� �ܿ� ���� �ִ°�
		System.out.println(conn+"ä�ù��� �����̽��ϴ�."); //�ܼ�â�� �����°�
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		// TODO Auto-generated method stub
		this.sendToAll(message);
		System.out.println(conn + ":" + message);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) { //������ �����Ͽ� ���� ������ ����Ǵ� �޼ҵ� ä�ù濬��������
		// TODO Auto-generated method stub
//		this.sendToAll("new connection :" + handshake.getResourceDescriptor()); //handshake->Ŭ���̾��� ��ü ���� ����(ip�ּ�)
		System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress()+"ä�ù濡 �����ϼ̽��ϴ�");
	}
	

	public void sendToAll(String text){
		Collection <WebSocket> con = connections(); //Collection->���� <WebSocket>->���׸� : �������̶�� Ÿ�Ը� �� �÷��� �ȿ� ���� �ִ�. 
		synchronized (con){
			for(WebSocket c:con){ //��ü �ϳ� ���� �ͼ� 
				c.send(text); //send�� ������ 
			}
		}
	}
	public static void main(String[] args) throws Exception { //--> throws �� ���ܰ� �߻��ϸ� ȣ���Ѱ����� ������ �װ����� ���ܰ� ��....
		// TODO Auto-generated method stub
		int port_chat = 8889; // ��Ʈ�� ��ȣȭ������ �ɾ���(��ȭ��) ��ȭ�� �ȿ����ָ� ���� ����
		int port_gps = 8890;	// GPS ���� ��Ʈ
		
		RealChatServer server_chat= new RealChatServer(port_chat);
		server_chat.start();
		System.out.println("ä�ü�������  ��Ʈ:"+ server_chat.getPort());
		
		RealChatServer server_gps= new RealChatServer(port_gps);
		server_gps.start();
		System.out.println("GPS��������  ��Ʈ:"+ server_gps.getPort());
	}
}
