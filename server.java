import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        int port = 12345; // Choose any available port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for a client...");
            
            Socket socket = serverSocket.accept(); // Accept a client connection
            System.out.println("Client connected!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while (true) {
                message = in.readLine();
                if (message == null || message.equalsIgnoreCase("bye")) {
                    System.out.println("Client disconnected.");
                    break;
                }
                System.out.println("Client: " + message);

                System.out.print("You: ");
                String reply = userInput.readLine();
                out.println(reply);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
