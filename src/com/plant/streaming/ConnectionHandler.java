package com.plant.streaming;

public class ConnectionHandler {
	Server server;
	
	public ConnectionHandler (Server server){
		this.server = server;
	}
	
	public void sendToClients(Byte[] image){
		for(ClientThread client : server.getClients()){
			//TODO: Send message
		}
	}
	
}
