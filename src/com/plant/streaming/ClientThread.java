package com.plant.streaming;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread{
	
	private DataInputStream is = null;
	  private PrintStream os = null;
	  private Socket clientSocket = null;
	  private final ClientThread[] threads;
	  private int maxClientsCount;

	  public ClientThread(Socket clientSocket, ClientThread[] threads) {
	    this.clientSocket = clientSocket;
	    this.threads = threads;
	    maxClientsCount = threads.length;
	  }
}
