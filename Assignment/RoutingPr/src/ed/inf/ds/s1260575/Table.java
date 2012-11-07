package ed.inf.ds.s1260575;

import java.util.ArrayList;
/**
 * This class represents a routing 
 * table as a list of rows 
 * @see LineTable
 * @author Julien-G
 *
 */
public class Table{
  	private ArrayList<LineTable> routeTable;
  	
  	public ArrayList<LineTable> getRouteTable(){
  		return routeTable;
  	}
	/**
	 * Getter to obtain the cost corresponding to a particular adress
	 * @param currAdress the particular adress
	 * @return the cost of this adress
	 */
  	public int getCost(int currAdress) {
		for(int i=0;i<routeTable.size();i++){
			if(routeTable.get(i).getAdress()==currAdress){
				return routeTable.get(i).getCost();
			}
		}
		return 0;
	}
  	
  	/**
  	 * Verify if an adress is linked in the table or not 
  	 * @param currAdress the adress we want to verify
  	 * @param nameSender the name of the link we want to check
  	 * @return true if linked, false in any other case
  	 */
	public boolean isLinked(int currAdress, String nameSender) {
		for(int i=0;i<routeTable.size();i++){
			if(routeTable.get(i).getAdress()==currAdress&&(routeTable.get(i).getLink().left_name==nameSender||routeTable.get(i).getLink().right_name==nameSender)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if an adress is in the table
	 * @param currAdress
	 * @return true if the adress is in the table
	 */
	public boolean contains(int currAdress) {
		for(int i=0;i<routeTable.size();i++){
			if(routeTable.get(i).getAdress()==currAdress){
				return true;
			}
		}
		return false;
	}
	
	
	
	public int indexOf(int currAdress) {
		for(int i=0;i<routeTable.size();i++){
			if(routeTable.get(i).getAdress()==currAdress){
				return i;
			}
		}
		return -1;
	}
	public void setRouteTable(ArrayList<LineTable> route){
		this.routeTable = route;
	}
}