import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextChatMultiBase {
    //Attributes
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
            String text;
            text = textInput.getText();
            textInput.setText("");
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            textArea.append("--> " + usernameInput.getText() + " : "+ df.format(dateobj) + " - " + text + "\n");
        }
    };

    //Constructor
    public TextChatMultiBase() {
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
}
