package dispatcher;

import java.net.ServerSocket;

public interface Dispatcher {
    void dispatch(ServerSocket serverSocket, HandleMap handlers);
}
