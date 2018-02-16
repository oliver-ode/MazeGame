import java.net.*;
import java.util.Scanner;
import java.io.*;

class CLChatClient {
    //Port
    static final int DEFAULT_PORT = 1728;
    //Handshake string
    static final String HANDSHAKE = "CLChat";
    //This character is prepended to every message that is sent.
    static final char MESSAGE = '0';
    //This character is sent to the connected program when the user quits.
    static final char CLOSE = '1';

    public static void main(String[] args) {
        String computer;
        int port = DEFAULT_PORT;
        Socket connection;
        BufferedReader incoming;
        PrintWriter outgoing;
        String messageOut;
        String messageIn;
        Scanner userInput;

        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter computer name or IP address: ");
        computer = stdin.nextLine();

        //Connecting devices
        try {
            System.out.println("Connecting to " + computer + " on port " + port);
            connection = new Socket(computer,port);
            incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()) );
            outgoing = new PrintWriter(connection.getOutputStream());
            outgoing.println(HANDSHAKE);
            outgoing.flush();
            messageIn = incoming.readLine();
            if (! messageIn.equals(HANDSHAKE) ) {
                throw new IOException("Connected program is not CLChat!");
            }
            System.out.println("Connected.");
        }
        catch (Exception e) {
            System.out.println("An error occurred while opening connection.");
            System.out.println(e.toString());
            return;
        }

        /* Exchange messages with the other end of the connection until one side or 
           the other closes the connection.  This client program sends the first message.
           After that,  messages alternate strictly back and forth. */
        try {
            userInput = new Scanner(System.in);
            System.out.println("NOTE: Enter 'quit' to end the program.\n");
            while (true) {
                System.out.print("SEND: ");
                messageOut = userInput.nextLine();
                if (messageOut.equalsIgnoreCase("quit"))  {
                        // User wants to quit.  Inform the other side
                        // of the connection, then close the connection.
                    outgoing.println(CLOSE);
                    outgoing.flush();
                    connection.close();
                    System.out.println("Connection closed.");
                    break;
                }
                outgoing.println(MESSAGE + messageOut);
                outgoing.flush();
                if (outgoing.checkError()) {
                    throw new IOException("Error occurred while transmitting message.");
                }
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
            }
        }
        catch (Exception e) {
            System.out.println("Sorry, an error has occurred.  Connection lost.");
            System.out.println(e.toString());
            System.exit(1);
        }
    }
}