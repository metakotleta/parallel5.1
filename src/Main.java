import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            Server server = new Server("127.0.0.1", 20666);
            new Thread(null, server::readCalculateAndResponse, "server").start();
            Client client = new Client("127.0.0.1", 20666);
            new Thread(null, () -> {
                try {
                    client.requestCalculation();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            },"client").start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
