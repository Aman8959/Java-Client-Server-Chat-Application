import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    // thread-safe access via synchronizing on this set
    private static final Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("💬 Chat Server started...");

        // ServerSocket in try-with-resources -> will be closed automatically when main exits
        try (ServerSocket server = new ServerSocket(5000)) {
            while (true) {
                Socket client = server.accept();
                System.out.println("New user connected: " + client);

                ClientHandler handler = new ClientHandler(client);
                handler.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Thread for each client
    static class ClientHandler extends Thread {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            PrintWriter pwRef = null; // keep reference so we can remove from set in finally

            // Put socket and streams into try-with-resources so they're closed automatically
            try (Socket s = this.socket;
                 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

                // Save reference for removal later
                pwRef = out;

                // Register this client's writer
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);

                    // Broadcast to all clients
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(message);
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("Client disconnected or IO error: " + e.getMessage());
            } finally {
                // Ensure writer is removed from set even if an exception occurred
                if (pwRef != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(pwRef);
                    }
                }
                // socket and streams are closed by try-with-resources above
            }
        }
    }
}
