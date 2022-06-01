import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Server {

    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void readCalculateAndResponse() {
        try (Socket socket = serverSocket.accept()) {
            while (true) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg;
                while ((msg = in.readLine()) != null ) {
                    out.println(calculate(msg));
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
