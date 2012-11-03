package ed.inf.ds.s1260575;

import java.util.ArrayList;
import java.util.Queue;


public class ThreadTransfert extends Thread {
    Table table;
    InputNode node;
   
    private String id;
    private ArrayList<Queue<MessageTable>> messagesList;
	private ArrayList<String> idList;
	
    public ThreadTransfert(ArrayList<String> idList,
			ArrayList<Queue<MessageTable>> messagesList, String id, Table table) {
		this.idList = idList;
		this.messagesList = messagesList;
		this.id = id;
		this.table = table;
	}
	public void run() {
        while( !isInterrupted()) {
            this.printTable();

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
    		int currAdress = tableSent.getRouteTable().get(i).getAdress();
    		InputLink il = new InputLink(nameSender, this.id);
    		if(!table.contains(currAdress)){
    			
    			table.getRouteTable().add(new LineTable(currAdress,il,currCost+1));
    			hasChanged=true;
    		}
    		else if(table.getCost(currAdress)>tableSent.getCost(currAdress)+1){
	//        	if 1 + cost for the address is better than the current known one:
	//        	place this row in p1's table with the link "p2" and a
	//        	cost of one more than the received cost
    			int k = table.indexOf(currAdress);
    			table.getRouteTable().set(k,new LineTable(currAdress,il,currCost+1));
    			
    			hasChanged=true;
    		}
    		else if(table.isLinked(currAdress,nameSender)){
    			if(table.getCost(currAdress)!=currCost+1){
//    				oldLine
    				//TODO refresh table
    				int k = table.indexOf(currAdress);
        			table.getRouteTable().set(k,new LineTable(currAdress,il,currCost+1));
//		        	table.getRouteTable().set(table.getRouteTable().indexOf(), element)
    				hasChanged=true;
    			}

    		}
    	}
    	
//    		
//    		if process p1 has updated its table in any way:
//    		send updated table to all links
    	
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
}