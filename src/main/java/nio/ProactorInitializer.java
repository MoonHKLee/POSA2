package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProactorInitializer {

    private static final int PORT = 5000;
    private static final int THREAD_POOL_SIZE = 8;
    private static final int INITIAL_SIZE = 4;
    private static final int BACK_LOG = 50;

    public void startServer() {
        System.out.println("SERVER START!");

        NioHandleMap handleMap = new NioHandleMap();

        NioEventHandler sayHelloHandler = new NioSayHelloEventHandler();
        NioEventHandler updateProfileHandler = new NioUpdateProfileEventHandler();

        handleMap.put(sayHelloHandler.getHandle(), sayHelloHandler);
        handleMap.put(updateProfileHandler.getHandle(), updateProfileHandler);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor,INITIAL_SIZE);

            AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);
            listener.bind(new InetSocketAddress(PORT), BACK_LOG);

            listener.accept(listener, new Dispatcher(handleMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
