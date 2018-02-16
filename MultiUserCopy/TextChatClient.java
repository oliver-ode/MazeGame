import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextChatClient {
    //Socket/Server variables
    static final int DEFAULT_PORT = 1728;
    static final String HANDSHAKE = "ICS4UCHATBASE";
    
    //Graphics variables
    private JFrame frame = new JFrame();
    private JTextField textInput;
    private JTextField usernameInput;
    private JTextArea textArea;
    private JPanel panel = new JPanel() {
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    };

    private ActionListener enterPress = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            String text = textInput.getText();
            textInput.setText("");
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();

            BufferedReader incoming;
            PrintWriter outgoing;
            String messageIn;
            String computer = "192.168.0.240";
            Socket connection;

            try {
                connection = new Socket(computer, DEFAULT_PORT);
                incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()) );
                outgoing = new PrintWriter(connection.getOutputStream());

                while (true) {
                    outgoing.println("--> " + usernameInput.getText() + " : "+ df.format(dateobj) + " - " + text + "\n");
                    System.out.println("Got to the line 52");
                    textArea.append("--> " + usernameInput.getText() + " : "+ df.format(dateobj) + " - " + text + "\n");
                    outgoing.flush();
                    if (outgoing.checkError()) {
                        throw new IOException("Error occurred while transmitting message.");
                    }
                    messageIn = incoming.readLine();
                    messageIn = messageIn.substring(1);
                    textArea.append(messageIn);
                }
            }
            catch (Exception lol){
                System.exit(1);
            }
        }
    };

    //Constructor
    public TextChatClient() {
        /**Socket/Server**/
        //Variables
        String computer;
        int port = DEFAULT_PORT;
        Socket connection;
        BufferedReader incoming;
        PrintWriter outgoing;
        String messageIn;
        //Computer identification
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        computer = "192.168.0.240";
        //Connecting devices
        try {
            System.out.println("Connecting to " + computer + " on port " + port);
            connection = new Socket(computer,port);
            incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()) );
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

        /**Graphics**/
        //General setup/layout
        frame.add(panel);
        frame.setLayout(new GridBagLayout());
        //Text input
        textInput = new JTextField();
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.gridx = 1;
        constraints1.gridy = 1;
        textInput.setColumns(75);
        textInput.addActionListener(enterPress);
        frame.add(textInput, constraints1);
        //Text area
        textArea = new JTextArea();
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.gridwidth = 2;
        textArea.setEditable(false);
        textArea.setColumns(75);
        textArea.setRows(30);
        frame.add(textArea, constraints2);
        //Username selection
        usernameInput = new JTextField();
        usernameInput.setText("NewUser");
        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.gridx = 2;
        constraints3.gridy = 1;
        usernameInput.setColumns(20);
        frame.add(usernameInput, constraints3);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        TextChatClient tcb = new TextChatClient();
    }
}
