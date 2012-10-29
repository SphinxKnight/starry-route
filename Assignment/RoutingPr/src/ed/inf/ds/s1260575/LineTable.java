package ed.inf.ds.s1260575;


public class LineTable{
	private InputNode dest;
	private InputLink link;
	private int cost;
	public LineTable(InputNode dest,InputLink link, int cost){
		this.dest = dest;
		this.link = link;
		this.cost = cost;
	}
	public InputLink getLink() {
	
		return link;
	}
	public int getCost() {
		
		return cost;
	}
	public InputNode getDest() {
		
		return dest;
	}
}