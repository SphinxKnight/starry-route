package ed.inf.ds.s1260575;

import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

/**
 * This class represents a single node in the system.
 * It uses a common ressource to get the messages (it will only access its messages)
 * @author Julien-G
 *
 */
public class ThreadTransfert extends Thread {
	/**
	 * The table of the corresponding node
	 * @see Table
	 */
    Table table;
    /**
     * The InputNode object corresponding
     */
    InputNode node;
    
    /**
     * Map of the existing links in the system (created with init in RouteThread)
     * so that we can only use the links connected to this node
     */
    ArrayList<InputLink> map;
    
    /**
     * The name of the node
     */
    private String id;
    
    /**
     * The shared resource, each element is a queue with messages. Each node has a single index in this list.
     * Message when received are popped from the queue.
     * When sending, we add an element to the corresponding queue (no access to non-linked nodes !)
     */
    private ArrayList<Queue<MessageTable>> messagesList;
    
    /**
     * The list of nodes with same index as messagesList so that we can know what queue use. 
     */
	private ArrayList<String> idList;
	
	/**
	 * Simple constructor with attributes 
	 * @param idList
	 * @param messagesList
	 * @param id
	 * @param table
	 * @param map
	 */
    public ThreadTransfert(ArrayList<String> idList,
			ArrayList<Queue<MessageTable>> messagesList, String id, Table table,ArrayList<InputLink> map) {
		this.idList = idList;
		this.messagesList = messagesList;
		this.id = id;
		this.table = table;
		this.map= map;
		
	}
    
    /**
     * The run method implemented for a Thread
     */
	public void run(){
		boolean running = true;
		Date d1 = new Date();
		
		//This will loop if not stopped 
		//It simulates the "living" of the node
		while( running) {
			//Check if it has any message waiting 
			//(This will also refresh the table if necessary and send it)
			this.receiveTable();
        	Date d2 = new Date();
        	if(d2.getTime()-d1.getTime()>30000){
        		//Job done, print the state of the table
        		running = false;
        		this.printTable2();
        	}
        	
        }
    
        return ;
       
    }
    /**
     * See if a message is awaiting, refresh the table if necessary
     * Send it to everyone if changed
     * @see MessageTable
     */
	public void receiveTable(){
    	int index = idList.indexOf(this.id);
    	MessageTable ms = messagesList.get(index).poll();
    	
    	if(ms==null){
    		return ;
    	}
//    	System.out.println("Message for : "+this.id+" !");
    	//Printing the "receive" event
    	String lines="";
    	ArrayList<LineTable> tabl = new ArrayList<LineTable>(ms.getTable().getRouteTable());
    	for(LineTable lt : tabl){
    		lines+=" ("+Integer.toString(lt.getAdress())+"|"+lt.getLink().right_name+"|"+Integer.toString(lt.getCost())+")";
    	}
		System.out.println("receive "+ms.getName()+" "+this.id+" "+lines);
		//End of printing "receive" event
		
		boolean hasChanged = refreshTable(ms.getName(),ms.getTable());
    	if(hasChanged){
//    		this.printTable();
    		
    		
    		
    		/*When a process p1 updates its table in response to a table received
    		from process p2, is it necessary that process p1 sends its updated table
    		back to process p2? Explain the reasoning behind your answer.*/
    		
    		//Answer to the question 1//
    		
    		/*It is necessary for such a process p1 to send back the table to the 
    		 * sender process. I will take a simple example to demonstrate this assumption.
    		 * If you use a graph with (only) the following links :
    		 *  p1<->p2
    		 *  p2<->p3
    		 *  p3<->p4
    		 *  ... (and so on) 
    		 * i.e. the structure of the graph is linear (can be described like this also :
    		 * p1-p2-p3-p4-...)
    		 *  and you do not send back the tables, the last node will be the only one with all 
    		 *  the information. Here p1 will not know anything about p4 (even p3). By sending 
    		 *  back every refreshed table, we can have a correct final solution.
    		 */
    		//End of the answer of the question 1//
    		
    		/*
    		 * Whether you answered yes or no to the first part, does requiring p1
				to return its updated table to p2 increase or decrease the number of
				events required for the algorithm to converge, or does it depend on the
				network and/or ordering of events? Try to justify your answer with
				logic and/or statistics from your simulator.
    		 */
    		  		
    		//Answer of the question 2//
    		
    		/*The previous answer is I think relevant to help answer this question.
    		 * The number of events (receive/send) will increase, because at least
    		 * one node will send an additional message. 
    		 * If we compare the simple "linear" solution (explained above), 
    		 * (first message send by p1 in the initiation file) we have 
    		 * 		-6 events without sending back 
    		 * 		-28 events with sending back (in one case)
    		 *  	
    		 * In both case, the algorithm converge, but in the first case, the solution
    		 * is incomplete. 
    		 *  However we can also take the example of the circular graph
    		 *  p1<->p2<->p3<->p4<->p1
    		 *  Where the initiation is done by p1 :
    		 *  	-20 events without sending back
    		 *  	-44 events with sending back (in one case)
    		 *  Here, given the structure of the graph, both solution are equals.
    		 *  
    		 *  The order of events, at least with this threads-oriented simulation impacts slightly
 			 *  the number of events (variations observed approximately +- 4 events). About the order,
 			 *  using threads and running several times a simulation based on the same input was useful
 			 *  to observe this phenomenon.
    		 *  To conclude, I would say that the number of events increases (at least by 1 message) 
    		 *  with sending back the new table. However this increase and the impact on the final
    		 *  solution are really coupled with the structure of the graph used. 
 			 * 
 			 *  
    		 *  
    		 */
    		
    		//End of the answer of the question 2//
    		//If table changed, send it to everyone else 
//    		this.sendTableToAll(ms.getName());
    		this.sendTableToAll();
    	}
    	return;
    }
	/**
	 * Send a message from nameSender to nameReceiver
	 * @see MessageTable
	 * @param nameSender
	 * @param nameReceiver
	 */
    public void sendTable(String nameSender, String nameReceiver) {
    	//Add message in queue for Receiver
   		int index = idList.indexOf(nameReceiver);
   		//Print sending event 
   		String lines="";
    	for(LineTable lt : this.table.getRouteTable()){
    		lines+=" ("+Integer.toString(lt.getAdress())+"|"+lt.getLink().right_name+"|"+Integer.toString(lt.getCost())+")";
    	}
		System.out.println("send "+nameSender+" "+nameReceiver+" "+lines);
		//End of logging
			
		//Actual message
   		messagesList.get(index).add(new MessageTable(nameSender,this.table));
   		   		
    }
    
    /**
     * Send a Table to all the neighbours
     */
    public void sendTableToAll(){
    	//Get neighbours
    	ArrayList<String> neighbours = new ArrayList<String>();
    	for(InputLink il : map){
    		if(il.left_name.equals(this.id)){
    			neighbours.add(il.right_name);
    		}
    		else if(il.right_name.equals(this.id)){
    			neighbours.add(il.left_name);
    		} 
    	}

    	for(String neigh : neighbours){
    		sendTable(this.id,neigh);
    	}
    }
    
    /**
     * Send a table to every neighbours except one 
     * @param except
     */
    public void sendTableToAll(String except){
    	//Get neighbours
    	ArrayList<String> neighbours = new ArrayList<String>();
    	for(InputLink il : map){
    		if(il.left_name.equals(this.id)){
    			neighbours.add(il.right_name);
    		}
    		else if(il.right_name.equals(this.id)){
    			neighbours.add(il.left_name);
    		} 
    	}

    	for(String neigh : neighbours){
    		if(!neigh.equals(except)){
    			sendTable(this.id,neigh);
    		}
    	}
    }
    
    /**
     * Main algorithm to see if the table has to be refreshed
     * @param nameSender the name of the node which sent the message
     * @param tableSent the sent Table
     * @return true if the table has changed 
     */
    public boolean refreshTable(String nameSender, Table tableSent){
    	int nbRow = tableSent.getRouteTable().size();
    	boolean hasChanged=false;
    	for(int i=0;i<nbRow;i++){
    		int currCost = tableSent.getRouteTable().get(i).getCost();
    		int currAdress = tableSent.getRouteTable().get(i).getAdress();
    		InputLink il = new InputLink(this.id, nameSender);
    		//If the adress is unknown until now, we add it with a cost of the previous cost +1
    		if(!table.contains(currAdress)){
    			table.getRouteTable().add(new LineTable(currAdress,il,currCost+1));
    			hasChanged=true;
    		}
    		//If the cost was too high, and the new cost is better, we change the link and the cost for this adress
    		else if(table.getCost(currAdress)>tableSent.getCost(currAdress)+1){
    			int k = table.indexOf(currAdress);
    			table.getRouteTable().set(k,new LineTable(currAdress,il,currCost+1));
    			
    			hasChanged=true;
    		}
    		//If the cost is not coherent with the link, we refresh the cost for this adress
    		else if(table.isLinked(currAdress,nameSender)){
    			if(table.getCost(currAdress)!=currCost+1){
    				int k = table.indexOf(currAdress);
        			table.getRouteTable().set(k,new LineTable(currAdress,il,currCost+1));
    				hasChanged=true;
    			}

    		}
    	}
    	return hasChanged;
    }
    
    public void printTable(){
    	System.out.println("Process : "+this.id);
    	ArrayList<LineTable> routetable =this.table.getRouteTable();
    	for(int i=0; i<routetable.size();i++){
    		LineTable currLine = routetable.get(i);
    		System.out.println("Line "+Integer.toString(i)+": adr "+Integer.toString(currLine.getAdress())
    													  +" - link "+currLine.getLink().left_name+"<->"+currLine.getLink().right_name+
    													  " - cost "+currLine.getCost());
    	}
    	System.out.println("_____________________");
    }
    private void printTable2() {
    	String lines="";
    	for(LineTable lt : this.table.getRouteTable()){
    		lines+=" ("+Integer.toString(lt.getAdress())+"|"+lt.getLink().right_name+"|"+Integer.toString(lt.getCost())+")";
    	}
		System.out.println("table "+this.id+" "+lines);		
	}
    
	public String getIdd() {
		return this.id;
	}
}