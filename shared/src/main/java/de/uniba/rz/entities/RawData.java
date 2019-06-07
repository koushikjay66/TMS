package de.uniba.rz.entities;


/**
 * 
 * @author Kowshik Dipta Das Joy
 * @de
 * This class is the helper class for parsing and creating Data. Will be used for both 
 * Client and Server to Store ticket Information. 
 * Also note that this will be used for both UDP and JMS Communication
 */
public class RawData {

	
	public String RequestType;
	public String ClientID;
	public int TotalSize;
	public int ThisPacketSize;
	public int TotalChunk;
	public int ChunkNo;
	public String Data;
	
}
