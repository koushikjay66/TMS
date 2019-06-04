package de.uniba.rz.app;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import de.uniba.rz.entities.Priority;
import de.uniba.rz.entities.Ticket;
import de.uniba.rz.entities.TicketException;
import de.uniba.rz.entities.Type;

public class UDPTicketManagementBackend implements TicketManagementBackend{

	private final UDPConnManager connection;
	public  UDPTicketManagementBackend() {
		// TODO Auto-generated constructor stub
		this.connection=new UDPConnManager("serverip" , 1140);
		this.connection.makeConnection();
		
	}
	@Override
	public void triggerShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ticket createNewTicket(String reporter, String topic, String description, Type type, Priority priority)
			throws TicketException {
		// TODO Auto-generated method stub
		System.out.println("Reporter");
		return null;
	}

	@Override
	public List<Ticket> getAllTickets() throws TicketException {
		// TODO Auto-generated method stub
		String j="{ requesttype=\"getAllTicket\"}";
		String allTickets=connection.sendData(j, false);
		return Arrays.asList((new Gson()).fromJson(allTickets, Ticket.class));
	}

	@Override
	public Ticket getTicketById(int id) throws TicketException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket acceptTicket(int id) throws TicketException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket rejectTicket(int id) throws TicketException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket closeTicket(int id) throws TicketException {
		// TODO Auto-generated method stub
		return null;
	}

}

