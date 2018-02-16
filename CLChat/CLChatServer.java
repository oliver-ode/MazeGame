import java.net.*;
import java.util.Scanner;
import java.io.*;

public class CLChatServer {
    //Port
    static final int DEFAULT_PORT = 1728;
    //Handshake string
    static final String HANDSHAKE = "CLChat";
    //This character is prepended to every message that is sent.
    static final char MESSAGE = '0';
    //This character is sent to the connected program when the user quits.
    static final char CLOSE = '1';

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        ServerSocket listener;
        Socket connection;
        BufferedReader incoming;
        PrintWriter outgoing;
        String messageOut;
        String messageIn;
        Scanner userInput;

        try {
            listener = new ServerSocket(port);
            System.out.println(listener.getInetAddress().getHostAddress());
            System.out.println("Listening on port " + listener.getLocalPort());
            connection = listener.accept();
            listener.close();
            incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            outgoing = new PrintWriter(connection.getOutputStream());
            outgoing.println(HANDSHAKE);  // Send handshake to client.
            outgoing.flush();
            messageIn = incoming.readLine();  // Receive handshake from client.
            if (!HANDSHAKE.equals(messageIn)) {
                throw new Exception("Connected program is not a CLChat!");
            }
            System.out.println("Connected.  Waiting for the first message.");
        } catch (Exception e) {
            System.out.println("An error occurred while opening connection.");
            System.out.println(e.toString());
            return;
        }

        /* Exchange messages with the other end of the connection until one side
           or the other closes the connection.  This server program waits for 
           the first message from the client.  After that, messages alternate 
           strictly back and forth. */
        try {
            userInput = new Scanner(System.in);
            System.out.println("NOTE: Enter 'quit' to end the program.\n");
            while (true) {
                System.out.println("WAITING...");
                messageIn = incoming.readLine();
                if (messageIn.length() > 0) {
                    // The first character of the message is a command. If
                    // the command is CLOSE, then the connection is closed.
                    // Otherwise, remove the command character from the
                    // message and proceed.
                    if (messageIn.charAt(0) == CLOSE) {
                        System.out.println("Connection closed at other end.");
                        connection.close();
                        break;
                    }
                    messageIn = messageIn.substring(1);
                }
                System.out.println("RECEIVED: " + messageIn);
                System.out.print("SEND: ");
                messageOut = userInput.nextLine();
                if (messageOut.equalsIgnoreCase("quit")) {
                    // User wants to quit.  Inform the other side
                    // of the connection, then close the connection.
                    outgoing.println(CLOSE);
                    outgoing.flush();  // Make sure the data is sent!
                    connection.close();
                    System.out.println("Connection closed.");
                    break;
                }
                outgoing.println(MESSAGE + messageOut);
                outgoing.flush(); // Make sure the data is sent!
                if (outgoing.checkError()) {
                    throw new IOException("Error occurred while transmitting message.");
                }
            }
        } catch (Exception e) {
            System.out.println("Sorry, an error has occurred.  Connection lost.");
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }
}
