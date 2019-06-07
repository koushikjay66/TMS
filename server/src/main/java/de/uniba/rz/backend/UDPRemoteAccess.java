package de.uniba.rz.backend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * This class is the UDP Remote Access Class. All the connections will be managed from here . 
 * 
 */
public class UDPRemoteAccess implements RemoteAccess{

	private static boolean active =true;
	private DatagramSocket datagram_Socket ;
	
	public UDPRemoteAccess() {
	
		
	}
	
	@Override
	public void run() {
	
		// TODO Auto-generated method stub
		while(active) {
			byte data[] = new byte[64507];
			DatagramPacket packet = new DatagramPacket(data, data.length);
				try {
					
					this.datagram_Socket.receive(packet);
					//System.out.println("Connected: "+ datagram_Socket.getRemoteSocketAddress());
					String raw_json_data=new String(packet.getData());
					
					/*
					 * JSON Format is written here 
					 * {
					 * 	requestType = int
					 * 
					 * }
					 */
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("\u001B[31m"+" Socket Has been closed . Cannot read data error "+"\u001B[0m");
					//e.printStackTrace();
				}
			}
	}

	@Override
	public void prepareStartup(TicketStore ticketStore) {
		// TODO Auto-generated method stub
		
		try {
			this.datagram_Socket=new DatagramSocket(new InetSocketAddress(InetAddress.getLocalHost(), 1140));
			// Setting the timeout to be 5000 milisecons
			//this.datagram_Socket.setSoTimeout(5000);
			System.out.println("UDP Socket is running on " + datagram_Socket.getLocalSocketAddress().toString());
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Error on creating binding Address.");
			e.printStackTrace();
		}
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		this.active=false;
		
		this.datagram_Socket.close();
	}

}
