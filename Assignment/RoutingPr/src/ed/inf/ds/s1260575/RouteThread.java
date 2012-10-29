package ed.inf.ds.s1260575;

import java.util.ArrayList;
import java.util.Queue;


public class RouteThread {
	
	private ArrayList<Queue<MessageTable>> messagesList;
	private ArrayList<String> idList;
   
	public RouteThread() {
   		messagesList = new ArrayList<Queue<MessageTable>>();
   		idList = new ArrayList<String>(); 
        for( int i = 0; i <2; i++){
        	int id=i;
            new ThreadTransfert(idList, messagesList, id).start();
        }
    }
   	
	  
	
}
