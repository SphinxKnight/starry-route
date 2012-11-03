package ed.inf.ds.s1260575;


public class LineTable{
	private int adress;
	private InputLink link;
	private int cost;
	public LineTable(int adress,InputLink link, int cost){
		this.adress = adress;
		this.link = link;
		this.cost = cost;
	}
	public InputLink getLink() {
	
		return link;
	}
	public int getCost() {
		
		return cost;
	}
	public int getAdress() {
		
		return adress;
	}
}