package dispatcher;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerDispatcher implements Dispatcher {

    public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable deMultiplexer = new DeMultiplexer(socket, handleMap);
                Thread thread = new Thread(deMultiplexer);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
