package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DeMultiplexer implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;
    private NioHandleMap handleMap;
    public DeMultiplexer(AsynchronousSocketChannel channel, NioHandleMap handleMap) {
        this.channel = channel;
        this.handleMap = handleMap;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        if (result == -1) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (result > 0) {
            buffer.flip();

            String header = new String(buffer.array());

            NioEventHandler handler = handleMap.get(header);

            ByteBuffer newBuffer = ByteBuffer.allocate(handler.getDataSize());

            handler.initialize(channel, newBuffer);
            channel.read(newBuffer, newBuffer, handler);
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

    }
}
