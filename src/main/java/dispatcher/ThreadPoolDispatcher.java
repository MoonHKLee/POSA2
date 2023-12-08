package dispatcher;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolDispatcher implements Dispatcher {

    static final String NUM_THREADS = "8";
    static final String THREAD_PROP = "Threads";
    private int numThreads;
    public ThreadPoolDispatcher() {
        numThreads = Integer.parseInt(System.getProperty(THREAD_PROP, NUM_THREADS));
    }
    public void dispatch(final ServerSocket serverSocket, final HandleMap handleMap) {
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread() {
                public void run() {
                    dispatchLoop(serverSocket, handleMap);
                }
            };
            thread.start();
            System.out.println("Created and started Thread = " + thread.getName());
        }
        System.out.println("Iterative server starting in main thread " + Thread.currentThread().getName());

        dispatchLoop(serverSocket, handleMap);
    }

    private void dispatchLoop(ServerSocket serverSocket, HandleMap handleMap) {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Runnable deMultiplexer = new DeMultiplexer(socket, handleMap);
                deMultiplexer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
