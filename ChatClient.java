import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {
        // Try-with-resources: closes socket + streams automatically
        try (
            Socket socket = new Socket("localhost", 5000);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("Connected to Chat Server!");
            System.out.println("Type your messages:");

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = serverInput.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException ignored) {
                }
            });

            readThread.start();

            // Send messages to server
            String text;
            while ((text = keyboard.readLine()) != null) {
                out.println(text);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
