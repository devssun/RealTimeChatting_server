package realChatServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class RealChatServer  extends WebSocketServer{

	public RealChatServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port)); //웹소켓 만들때 포트 보냄(메인에 포트 넣어서 웹소켓 만들것임)
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onClose(WebSocket conn, int arg1, String arg2, boolean arg3) { 
		// TODO Auto-generated method stub
//		this.sendToAll(conn+"채팅방을 나가셨습니다."); //서버 단에 보내 주는것
		System.out.println(conn+"채팅방을 나가셨습니다."); //콘솔창에 찍히는것
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
	public void onOpen(WebSocket conn, ClientHandshake handshake) { //누군가 웹소켓에 접근 했을떄 실행되는 메소드 채팅방연결했을떄
		// TODO Auto-generated method stub
//		this.sendToAll("new connection :" + handshake.getResourceDescriptor()); //handshake->클라이언테 객체 정보 가짐(ip주소)
		System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress()+"채팅방에 입장하셨습니다");
	}
	

	public void sendToAll(String text){
		Collection <WebSocket> con = connections(); //Collection->집합 <WebSocket>->제네릭 : 윕소켓이라는 타입만 이 컬렛션 안에 들어갈수 있다. 
		synchronized (con){
			for(WebSocket c:con){ //객체 하나 꺼내 와서 
				c.send(text); //send로 보내고 
			}
		}
	}
	public static void main(String[] args) throws Exception { //--> throws 는 예외가 발생하면 호출한것으로 날려서 그곳에서 예외가 뜸....
		// TODO Auto-generated method stub
		int port_chat = 8889; // 포트에 암호화같은거 걸어줌(방화벽) 방화벽 안열어주면 접근 못함
		int port_gps = 8890;	// GPS 서버 포트
		
		RealChatServer server_chat= new RealChatServer(port_chat);
		server_chat.start();
		System.out.println("채팅서버시작  포트:"+ server_chat.getPort());
		
		RealChatServer server_gps= new RealChatServer(port_gps);
		server_gps.start();
		System.out.println("GPS서버시작  포트:"+ server_gps.getPort());
	}
}
