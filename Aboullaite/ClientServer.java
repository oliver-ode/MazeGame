import javax.swing.*;

public class ClientServer {
	public static void main(String [] args){
		Object[] selectionValues = { "Server","Client"};
		String initialSection = "Server";
		
		Object selection = JOptionPane.showInputDialog(null, "Login as : ", "MyChatApp", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSection);
		if(selection.equals("Server")){
                   String[] arguments = new String[] {};
			new MultiThreadChatServerSync().main(arguments);
		}else if(selection.equals("Client")){
			String IPServer = JOptionPane.showInputDialog("Enter the Server IP address");
                        String[] arguments = new String[] {IPServer};
			new ChatClient().main(arguments);
		}
		
	}

}
