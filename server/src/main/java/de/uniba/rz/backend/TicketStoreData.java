package de.uniba.rz.backend;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.RawData;
import de.uniba.rz.entities.Status;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.Type;

public class TicketStoreData implements TicketStore{

	private static HashMap <Integer , Ticket>  TicketData=new HashMap<Integer,Ticket>();
	private static HashMap<String , RawData[]> ChunkTicketPartialData = new HashMap<String , RawData[]>();

	private String RawTicketData;

	public TicketStoreData(String RawTicketData) {
		// TODO Auto-generated constructor stub
		this.RawTicketData=RawTicketData;
	}

	public void handleRequest() {
		Gson g = new Gson();
		RawData raw_Data_Object =g.fromJson(this.RawTicketData, RawData.class);

		if(isFullTicket(raw_Data_Object) && !isUpdateRequest(raw_Data_Object)) {
			Ticket t = this.createFullTicket(raw_Data_Object);
			this.storeNewTicket(t.getReporter(), t.getTopic(), t.getDescription(), t.getType(), t.getPriority());
		}
	}

	/**
	 * This method will be used for creating full ticket. 
	 * Inputs user String data and returns ticket
	 */
	public Ticket createFullTicket(RawData raw_Data_Object) {
		return  new Gson().fromJson(raw_Data_Object.Data, Ticket.class);

	}

	public Ticket createTicketFromChunk(RawData raw_Data_Object) {



		return null;
	}

	public static synchronized boolean ifFull(String key)
	{
		//will implement check for exist method
	if	(ChunkTicketPartialData.containsKey(key)==false) {
		return false;
	}

		int x= ChunkTicketPartialData.get(key).length;
		for	(int i=0;i<x;i++)
		{
			if(ChunkTicketPartialData.get(key)[i]==null) {
				return false;
			}
		}
		return true;
	}
	

	public static synchronized  boolean putPartialTicketData(String key , RawData[] x) {

		return false;
	}


	public static synchronized boolean deletePartialData(String Key) {


		return true;
	}

	public static synchronized RawData[] getPartialData() {

		return null;
	}
	/**
	 * Check if this is the full ticket or not . If so returns true else false.
	 * This Function will return true  
	 */
	public boolean isFullTicket(RawData RawDataObject) {
		return (RawDataObject.ChunkNo==1 && RawDataObject.TotalChunk==1)? true : false;
		//		if(RawDataObject.ChunkNo==1 && RawDataObject.TotalChunk==1) {
		//			return true;
		//		}
		//		return false;
	}


	/**
	 * Checks if this is only a updateRequest or not. 
	 */
	public boolean isUpdateRequest(RawData RawDataObject) {
		return RawDataObject.RequestType=="update" ? true : false;

	}

	@Override
	/* 
	 * I actually do not need this method because I have got the Object and I am Storing into Them.
	 * But As this has been proposed!!!!
	 */
	public Ticket storeNewTicket(String reporter, String topic, String description, Type type, Priority priority) {
		// TODO Auto-generated method stub	

		Integer Id =new AtomicInteger().getAndIncrement();
		TicketStoreData.putData(Id, new Ticket(Id , reporter , topic , description , type, priority));

		return TicketStoreData.TicketData.get(Id);
	}

	@Override
	public void updateTicketStatus(int ticketId, Status newStatus)
			throws UnknownTicketException, IllegalStateException {
		// TODO Auto-generated method stub

	}

	/**
	 * This method is only for becoming Thread Safe. Understanding may be wrong . But that is what I found from Google!
	 */
	public static synchronized void putData(Integer id , Ticket t  ) {
		TicketStoreData.TicketData.put(id, t);

	} 



	@Override
	public List<Ticket> getAllTickets() {
		// TODO Auto-generated method stub
		return null;
	}


}
