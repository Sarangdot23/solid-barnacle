import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

public class ChatServer {
    private static JTextArea chatArea;
    private static JTextField inputField;
    private static PrintWriter out;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Server");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);

        try (ServerSocket serverSocket = new ServerSocket(12345);
             Socket socket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            chatArea.append("Client connected!\n");
            out = new PrintWriter(socket.getOutputStream(), true);

            sendButton.addActionListener((ActionEvent e) -> sendMessage());
            inputField.addActionListener((ActionEvent e) -> sendMessage());

            String message;
            while ((message = in.readLine()) != null) {
                chatArea.append("Client: " + message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n");
            out.println(message);
            inputField.setText("");
        }
    }
}
