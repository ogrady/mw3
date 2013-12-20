package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MetalWarriorsServer {

	public static final int MW3_PORT = 11666;

	public static void main(String[] args) {
		try{
			boolean done = false;
			String clientSentence = new String();
			ServerSocket welcomeSocket = new ServerSocket(MW3_PORT);

			while(!done)
			{
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient =
						new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				int c;
				while((c = inFromClient.read()) != -1) {
					clientSentence = clientSentence.concat(Character.toString((char) c));
				}
				//clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);
			}

			welcomeSocket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
