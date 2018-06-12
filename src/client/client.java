package client;
import java.io.Serializable;
import java.util.function.Consumer;


public class client extends Connections{
	private String ip;
	private int port;
	public client(String ip, int port, Consumer<Serializable> CallBack,Consumer<Serializable> CallBack1,Consumer<Serializable> CallBack2) {
		super(CallBack, CallBack1, CallBack2);
		this.ip = ip;
		this.port = port;
	}

	@Override
	protected boolean isServer() {
		return false;
	}

	@Override
	protected String getIP() {
		return ip;
	}

	@Override
	protected int getPort() {
		return port;
	}

}
