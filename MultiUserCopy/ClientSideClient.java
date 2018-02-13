import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ClientSideClient{
    public static final int LISTENING_PORT = 32007;
    public ClientSideClient() throws IOException{
        String hostName;         // Name of the server computer to connect to.
        Socket connection;       // A socket for communicating with server.
        BufferedReader incoming; // For reading data from the connection.

        /* Get computer name from command line. */

        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter computer name or IP address: ");
        hostName = stdin.nextLine();

        /* Make the connection, then read and display a line of text. */

        try {
            connection = new Socket( hostName, LISTENING_PORT );
            incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()) );
            String lineFromServer = incoming.readLine();
            if (lineFromServer == null) {
                // A null from incoming.readLine() indicates that
                // end-of-stream was encountered.
                throw new IOException("Connection was opened, " +
                        "but server did not send any data.");
            }
            System.out.println();
            System.out.println(lineFromServer);
            System.out.println();
            incoming.close();
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }

    }

    public static void main(String[] args) throws IOException{
        ClientSideClient clientClient = new ClientSideClient();
    }
}
