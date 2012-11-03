package ed.inf.ds.s1260575;

import java.util.ArrayList;

public class Table{
  	private ArrayList<LineTable> routeTable;
  	
  	public ArrayList<LineTable> getRouteTable(){
  		return routeTable;
  	}
	public int getCost(int currAdress) {
		for(int i=0;i<routeTable.size();i++){
			if(routeTable.get(i).getAdress()==currAdress){
				return routeTable.get(i).getCost();
			}
		}
		return 0;
	}
	public boolean isLinked(int currAdress, String nameSender) {
		for(int i=0;i<routeTable.size();i++){
			if(routeTable.get(i).getAdress()==currAdress&&(routeTable.get(i).getLink().left_name==nameSender||routeTable.get(i).getLink().right_name==nameSender)){
				return true;
			}
		}
		return false;
	}
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