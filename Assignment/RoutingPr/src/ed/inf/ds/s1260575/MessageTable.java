package ed.inf.ds.s1260575;

/**
 * An object to represent a message 
 * @see Table
 * @author Julien-G
 *
 */
public class MessageTable{
	/**
	 * The name of the sender 
	 */
	private String name;
	
	/**
	 * The sent table
	 */
	private Table tableSend;
	
	public MessageTable(String name,Table table){
		this.name=name;
		this.tableSend=table;
	}
	public String getName(){
		return name;
	}
	public Table getTable(){
		return tableSend;
	}
}
