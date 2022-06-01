import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private int port;
    private String adress;
    private SocketChannel socket;


    public Client(String adress, int port) throws IOException {
        this.port = port;
        this.adress = adress;
    }

    public void requestCalculation() throws IOException {
        String systemIn;
        try (Scanner scanner = new Scanner(System.in)) {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(adress, port));
            while (true) {
                System.out.print("Введите число:\n");
                systemIn = scanner.nextLine();

                if (systemIn.equals("end")) {
                    socket.close();
                    break;
                } else {
                    socket.write(ByteBuffer.wrap(systemIn.getBytes(StandardCharsets.UTF_8)));
                }
                readMsg();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void readMsg() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesCount = socket.read(buffer);
        System.out.println(new String(buffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
        buffer.clear();
    }
}
