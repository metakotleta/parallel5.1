import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {

    private final ServerSocketChannel serverSocketChannel;

    public Server(String adress, int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(adress, port));
    }

    public void readCalculateAndResponse() {
        try (SocketChannel socketChannel = serverSocketChannel.accept()) {
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(2 << 10);
                int count = 0;
                while (socketChannel.isConnected()) {
                    count = socketChannel.read(buffer);
                    if (count == -1) break;
                    String msg = new String(buffer.array(), 0, count, StandardCharsets.UTF_8);
                    buffer.clear();
                    //response
                    socketChannel.write(ByteBuffer.wrap(calculate(msg).getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculate(String msg) {
        Integer elements = Integer.valueOf(msg);
        int[] fib = (elements > 2) ? new int[elements] : new int[2];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i < elements; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return Arrays.deepToString(Arrays.stream(fib).boxed().toArray());
    }
}
