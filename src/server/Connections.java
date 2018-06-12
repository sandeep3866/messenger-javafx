package server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class Connections {
	private ConnectionThread connThread = new ConnectionThread();
	private Consumer<Serializable> CallBack;
	private Consumer<Serializable> CallBack1;
	private Consumer<Serializable> CallBack2;
	
	public Connections(Consumer<Serializable> CallBack,Consumer<Serializable> CallBack1,Consumer<Serializable> CallBack2){
		this.CallBack = CallBack;
		this.CallBack1 = CallBack1;
		this.CallBack2 = CallBack2;
	}
	
	public void startConnection() throws Exception{
		connThread.start();
	}
	public void send(Serializable data) throws Exception{
		connThread.out.writeObject(data);
	}
	public void SendMessageColor(Serializable message) throws Exception{
		connThread.out.writeObject(message);
	}
	public void SendNameColor(Serializable name) throws Exception{
		connThread.out.writeObject(name);
	}
	public void closeConnection() throws Exception{
		connThread.socket.close();
	}

protected abstract boolean isServer();
protected abstract String getIP();
protected abstract int getPort();

private class ConnectionThread extends Thread{

	private Socket socket;
	private ObjectOutputStream out;
	
	public void run(){
		try(ServerSocket server = isServer() ? new ServerSocket(getPort()): null;
				Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
			this.socket = socket;
			this.out = out;
			socket.setTcpNoDelay(true);
			while(true){
				Serializable data = (Serializable) in.readObject();
				Serializable message = (Serializable) in.readObject();
				Serializable name = (Serializable) in.readObject();
				CallBack.accept(data);
				CallBack1.accept(message);
				CallBack2.accept(name);
			}
		}
		catch(Exception e){
			CallBack.accept("Connection Closed");
			CallBack1.accept("Connection Closed");
			CallBack2.accept("Connection Closed");
			}
		}
	}
}