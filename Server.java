import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server is running...");

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("Client connected!");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                String message;

                while ((message = reader.readLine()) != null) {

                    System.out.println("Received: " + message);
                }

                socket.close();

                System.out.println("Client disconnected.");
            }

        } catch (Exception e) {

            System.out.println("Server error.");
        }
    }
}