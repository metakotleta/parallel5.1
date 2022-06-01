import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private int port;
    private String adress;
    private Socket socket;


    public Client(String adress, int port) throws IOException {
        this.port = port;
        this.adress = adress;
        socket = new Socket(adress, port);
    }

    public void requestCalculation() throws IOException {
        String systemIn;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Введите число:\n");
                systemIn = scanner.nextLine();

                if (systemIn.equals("end")) {
                    socket.close();
                    break;
                } else {
                    out.println(systemIn);
                }
                System.out.println(in.readLine());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
