import java.io.*;
import java.net.*;

public class user {
    public static void main(String[] args) {
        String serverIP = "127.0.0.1"; // Server runs on the same machine
        int port = 12345; // Port number

        try (Socket socket = new Socket(serverIP, port)) {
            System.out.println("Connected to server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while (true) {
                System.out.print("You: ");
                message = userInput.readLine();
                out.println(message);

                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Disconnected from server.");
                    break;
                }

                String response = in.readLine();
                System.out.println("Server: " + response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
