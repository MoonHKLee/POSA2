package nio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public interface NioEventHandler extends CompletionHandler<Integer, ByteBuffer> {
    String getHandle();
    int getDataSize();
    void initialize(AsynchronousSocketChannel asynchronousSocketChannel, ByteBuffer buffer);
}
