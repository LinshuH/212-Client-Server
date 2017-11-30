import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		ArrayList<Integer> int_array = new ArrayList<Integer>();
		ArrayList<Integer> prime_array = new ArrayList<Integer>();
		
		Socket clientSocket = new Socket(InetAddress.getLocalHost(),6789);
		
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
		outToServer.flush();
		ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
		
		//Scanner inFromUser = new Scanner(System.in);
		String temp = "";

		
		do {
			System.out.println("Enter an integer (\"!\" to send): ");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
			temp = inFromUser.readLine();
			//temp = inFromUser.nextLine();
			int_array.add(Integer.valueOf(temp));
		} while (!temp.equals("!"));

		System.out.println("Reach after the while in Client");
		
		outToServer.writeObject(int_array); //end send the int_array to server
		System.out.println("This is outToServer: " + outToServer);
		
		//get the prime array from server
		prime_array = (ArrayList<Integer>) inFromServer.readObject();
		
		//take the integers of the send arraylist into the string format 
		String send = "[";
		for (int n : int_array) {
			send += n + ",";
		}
		send = send.substring(0,send.length() - 1);
		send += "]";
		
		//take the integers of the send arraylist into the string format 
		String receive = "[";
		for (int n : prime_array) {
			receive += n + ",";
		}
		receive = receive.substring(0,receive.length() - 1);
		receive += "]";
		
		//print out the result
		System.out.println("Send: " + send);
		System.out.println("Receive: " + receive);
	
		
		outToServer.close();
		inFromServer.close();
		clientSocket.close();
	}

}
