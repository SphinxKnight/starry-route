package ed.inf.ds.s1260575;

/**
 * A line in a routing table described with adress, link and cost  
 * @see Table
 * @author Julien-G
 *
 */
public class LineTable{
	/**
	 * The adress described as an integer
	 */
	private int adress;
	
	/**
	 * The link between two nodes
	 * @see InputLink
	 */
	private InputLink link;
	
	/**
	 * The cost for a message to reach the adress
	 */
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