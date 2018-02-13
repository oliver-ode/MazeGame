import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSideClient {
    public static final int LISTENING_PORT = 32007;
    public ServerSideClient() throws IOException {
        ServerSocket listener;  // Listens for incoming connections.
        Socket connection;      // For communication with the connecting program.


        try {
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
            System.out.println(listener.getLocalSocketAddress().toString());
            System.out.println(listener.getInetAddress().toString());
            while (true) {
                // Accept next connection request and handle it.
                connection = listener.accept();
                sendDate(connection);
            }
        }
        catch (Exception e) {
            System.out.println("Sorry, the server has shut down.");
            System.out.println("Error:  " + e);
            return;
        }


    }
    private static void sendDate(Socket client) {
        try {
            System.out.println("Connection from " + client.getInetAddress().toString() );
            PrintWriter outgoing;   // Stream for sending data.
            outgoing = new PrintWriter( client.getOutputStream() );
            outgoing.println("Does this thing work?");
            outgoing.flush();  // Make sure the data is actually sent!
            client.close();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSideClient serverClient = new ServerSideClient();
    }
}