package de.uniba.rz.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Logger;

import de.uniba.rz.entities.Ticket;

public class UDPConnManager implements Runnable{

	private boolean connectionStatus;
	private  DatagramSocket connection_Socket;
	private final String ip;
	private final int port;

	public UDPConnManager(String ip , int port) {
		this.ip=ip;
		this.port=port;
	}

	public  void  makeConnection() {

		DatagramSocket socket_=null;
		try {
			socket_ = new DatagramSocket(null);

			socket_.setSoTimeout(10000);

			socket_.connect(Inet4Address.getByName(this.ip), this.port);
			this.connectionStatus=true;

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket is busy/Connection Timeout occured");
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Host not Found Please check your connection");
		}


		this.connection_Socket=socket_;

	}

	/*
	 * Function to end the current available connection
	 */
	public void endConnection() {

		this.connection_Socket.close();

	}

	/*
	 * Check if the connection exists
	 */

	public boolean isConnected() {
		return this.connectionStatus;

	}

	/* 
	 * Call this method if we need to send any data to the server. 
	 * This method will accept ticket t and creates a JSON Object which later needs to be send to server. 
	 *  Also this checks if any split is necessary. If so then calls split and continue text
	 *  Communication Type True requesting to sendData , False Requesting to Receive Data.  
	 */
	public String sendData(String Data_, boolean CommunicationType ) {

		byte Data_Array [];
		String SplittedArray[]=splitAndReturn(Data_);
		for(int i=0;i< SplittedArray.length ;i++) {
			
			// Implement this with Thread
			Data_Array = SplittedArray[i].getBytes();
			DatagramPacket dp = new DatagramPacket(Data_Array, Data_Array.length);
			try {
				this.connection_Socket.send(dp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
				
			}


		}
		//		
		//		if(!CommunicationType) {
		//			
		//			return receiveData();
		//		}
		//		
		return "Sent!";
	}

	private String [] splitAndReturn(String json_Data) {
		String random_ID =  (new Random()).nextInt((1000 - 100) + 1) + 100+"_"+System.currentTimeMillis();
		int totalchunk =(json_Data.getBytes().length/64507)+1; 
		String		final_Data_Array [] = new String[totalchunk];

		// Max UDP size is 64507 but we setting a threshold of 1000 bytes because We will add something with this one. 

		int max=64507;
		int min=0;
		for(int i =0; i<totalchunk; i++) {

			max=max>=json_Data.length() ?json_Data.length(): max;

			final_Data_Array[i]="{\nclientID: "+random_ID+",\nTotalChunk:"+totalchunk +"\nChunkID: "+(i+1)+"\nData:"+ " {"+json_Data.substring(min, max)+"/n}";
			min=max;
			max=max+64507;
		}

		return final_Data_Array;

	}

	public String receiveData() {

		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
