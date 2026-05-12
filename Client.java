import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void sendData(List<Transaction> transactions) {

        try {

            Socket socket = new Socket("localhost", 1234);

            PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

            for (Transaction t : transactions) {

                writer.println(
                    t.getType() + " | " +
                    t.description + " | " +
                    t.amount
                );
            }

            writer.close();
            socket.close();

            System.out.println("Transactions sent to server!");

        } catch (Exception e) {

            System.out.println("Unable to connect to server.");
        }
    }
}