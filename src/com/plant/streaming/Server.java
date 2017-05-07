package com.plant.streaming;
import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.net.Socket;
import java.net.ServerSocket;

import com.github.sarxos.webcam.Webcam;

public class Server {
	Webcam webcam;
	List<ClientThread> clients;
	
	// The server socket.
	  ServerSocket serverSocket = null;
	  // The client socket.
	  Socket clientSocket = null;
	// The default port number.
  int portNumber = 2222;
  
  final int maxClientsCount = 10;
  final ClientThread[] threads;
	
	public Server (Webcam webcam){
		webcam = Webcam.getDefault();
		clients = new LinkedList<ClientThread>();
		threads = new ClientThread[maxClientsCount];
	}
	
	public List<ClientThread> getClients(){
		return clients;
	}

	public void start(){
		
	    /*
	     * Open a server socket on the portNumber (default 2222). Note that we can
	     * not choose a port less than 1023 if we are not privileged users (root).
	     */
	    try {
	      serverSocket = new ServerSocket(portNumber);
	    } catch (IOException e) {
	      System.out.println(e);
	    }

	    /*
	     * Create a client socket for each connection and pass it to a new client
	     * thread.
	     */
	    while (true) {
	      try {
	        clientSocket = serverSocket.accept();
	        System.out.println("ONE JOINED!");
	        System.out.println(clientSocket.getInetAddress());
	        int i = 0;
	        for (i = 0; i < maxClientsCount; i++) {
	          if (threads[i] == null) {
	            (threads[i] = new ClientThread(clientSocket, threads)).start();
	            break;
	          }
	        }
	        if (i == maxClientsCount) {
	          PrintStream os = new PrintStream(clientSocket.getOutputStream());
	          os.println("Server too busy. Try later.");
	          os.close();
	          clientSocket.close();
	        }
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	}
	
	public void stop(){
		webcam.close();
	}
	
	public boolean addConnection(ClientThread client){
		return clients.add(client);
	}
	
	public boolean removeConnection(ClientThread client){
		return clients.remove(client);
	}
	
	
}
