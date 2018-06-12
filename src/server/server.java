package server;
import java.io.Serializable;
import java.util.function.Consumer;

public class server extends Connections{

		private int port;
	public server(int port, Consumer<Serializable> CallBack,Consumer<Serializable> CallBack1,Consumer<Serializable> CallBack2) {
		super(CallBack, CallBack1, CallBack2);
		this.port = port;
	}

	@Override
	protected boolean isServer() {
		return true;
	}
 
	@Override
	protected String getIP() {
		return null;
	}

	@Override
	protected int getPort() {
		return port;
	}

}
