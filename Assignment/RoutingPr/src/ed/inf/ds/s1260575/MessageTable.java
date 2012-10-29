package ed.inf.ds.s1260575;


public class MessageTable{
	private String name;
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
