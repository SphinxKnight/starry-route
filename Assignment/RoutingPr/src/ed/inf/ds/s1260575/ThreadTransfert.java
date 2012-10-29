package ed.inf.ds.s1260575;

import java.util.ArrayList;
import java.util.Queue;


public class ThreadTransfert extends Thread {
    Table table;
    InputNode node;
    private int id;
    private ArrayList<Queue<MessageTable>> messagesList;
	private ArrayList<String> idList;
	
    public ThreadTransfert(ArrayList<String> idList,
			ArrayList<Queue<MessageTable>> messagesList, int id) {
		this.idList = idList;
		this.messagesList = messagesList;
		this.id = id;
	}
	public void run() {
        while( !isInterrupted()) {
            InputNode otherNode = new InputNode("ee",new int[] {1});
            sendTable(node.name,otherNode.name,table);

        }
    }
    public void receiveTable(){
    	int index = idList.indexOf(this.node.name);
    	MessageTable ms = messagesList.get(index).poll();
    	boolean hasChanged = refreshTable(ms.getName(),ms.getTable());
    	if(hasChanged){
    		//TODO 
    		//SEND ALL THE MESSAGES
    	}
    }
    public void sendTable(String nameSender, String nameReceiver, Table table) {
    	//Add message in queue for Receiver
   		int index = idList.indexOf(nameReceiver);
   		messagesList.get(index).add(new MessageTable(nameSender,table));
   		   		
    }
    public boolean refreshTable(String nameSender, Table tableSent){
    	int nbRow = tableSent.getRouteTable().size();
    	boolean hasChanged=false;
    	for(int i=0;i<nbRow;i++){
    		InputLink currLink = tableSent.getRouteTable().get(i).getLink();
    		int currCost = tableSent.getRouteTable().get(i).getCost();
    		InputNode currNode = tableSent.getRouteTable().get(i).getDest();
    		if(table.getRouteTable().contains(currNode)){
//    			if address is not known by p1:
//    	        	add the address to p1's table with the link "p2" and a
//    	        	cost of one more than the received cost
    			table.getRouteTable().add(new LineTable(currNode,currLink,currCost+1));
    			hasChanged=true;
    		}
//        	if 1 + cost for the address is better than the current known one:
//        	place this row in p1's table with the link "p2" and a
//        	cost of one more than the received cost
//        	if address is known by p1 with a link of p2 then:
//        			if the cost for p2 is not exactly one less than p1's cost:
//        			act as if this address was unknown to p1
    	}
    	
//    		
//    		if process p1 has updated its table in any way:
//    		send updated table to all links
    	
    	return false;
    }
}