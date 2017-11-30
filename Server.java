import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ArrayList<Integer> client_int;
		ArrayList<Integer> prime_int = new ArrayList<Integer>();
		
		ServerSocket ini_socket = new ServerSocket(6789);
		
		while(true) {
			Socket connectionSocket = ini_socket.accept();
			System.out.println("Socket is been accepted");
			//Scanner s = new Scanner(connectionSocket.getInputStream()).useDelimiter("\\A");
			//String result = s.hasNext() ? s.next() : ""; 
			//System.out.println(result);
			// Q: Do we have to use the BufferedReader at here? Can I use the DatainputStream?
			// Q: Do we have to use DataOut/InputStream? Can I use ObjectOut/InputStream?

			ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
			ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
			outToClient.flush();
			
			System.out.println("Finish creating the input and output stream");
			
			System.out.println("This is inFromClient: " + inFromClient);
			
			client_int = (ArrayList<Integer>) inFromClient.readObject();

			for (int n : client_int) {
				if (isPrime(n) == true) {
					prime_int.add(n);
				}
			}
			outToClient.writeObject(prime_int);	
			outToClient.flush();
			
			//inFromClient.close();
			//outToClient.close();
			//ini_socket.close();
		}
		
	}
	
	public static boolean isPrime(int n){
		for (int i=2; i<n; i++)
		{
			if (n%i == 0)
				return false;
		}
		return true;
	}

	// Q: Should we shutdown the server? Why/why not?
	/*
	 * Usually it is better to close all the things, both socket and the stream
	 */
	
}
