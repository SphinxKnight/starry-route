package ed.inf.ds.s1260575;

import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;


public class ThreadTransfert extends Thread {
    Table table;
    InputNode node;
    ArrayList<InputLink> map;
   
    private String id;
    private ArrayList<Queue<MessageTable>> messagesList;
	private ArrayList<String> idList;
	
    public ThreadTransfert(ArrayList<String> idList,
			ArrayList<Queue<MessageTable>> messagesList, String id, Table table,ArrayList<InputLink> map) {
		this.idList = idList;
		this.messagesList = messagesList;
		this.id = id;
		this.table = table;
		this.map= map;
		
	}
	public void run(){
		boolean running = true;
		Date d1 = new Date();
		while( running) {
			this.receiveTable();
//        	boolean currBoolean = false;
//        	for(int i=0;i<messagesList.size();i++){
//        		if(!messagesList.get(i).isEmpty()){
//        			currBoolean=true;
//        			break;
//        		}
//        		
//        	}
//        	running = currBoolean;
        	Date d2 = new Date();
        	if(d2.getTime()-d1.getTime()>5000){
        		running = false;
        		this.printTable2();
        	}
        	
        }
    
        return ;
       
    }
    
	public void receiveTable(){
    	int index = idList.indexOf(this.id);
    	MessageTable ms = messagesList.get(index).poll();
    	
    	if(ms==null){
    		return ;
    	}
//    	System.out.println("Message for : "+this.id+" !");
    	String lines="";
    	for(LineTable lt : ms.getTable().getRouteTable()){
    		lines+=" ("+Integer.toString(lt.getAdress())+"|"+lt.getLink().right_name+"|"+Integer.toString(lt.getCost())+")";
    	}
		System.out.println("receive "+ms.getName()+" "+this.id+" "+lines);
    	boolean hasChanged = refreshTable(ms.getName(),ms.getTable());
    	if(hasChanged){
//    		this.printTable();
    		this.sendTableToAll();
    	}
    	return;
    }
    public void sendTable(String nameSender, String nameReceiver) {
    	//Add message in queue for Receiver
   		int index = idList.indexOf(nameReceiver);
   		String lines="";
    	for(LineTable lt : this.table.getRouteTable()){
    		lines+=" ("+Integer.toString(lt.getAdress())+"|"+lt.getLink().right_name+"|"+Integer.toString(lt.getCost())+")";
    	}
		System.out.println("send "+nameSender+" "+nameReceiver+" "+lines);
   		messagesList.get(index).add(new MessageTable(nameSender,this.table));
   		   		
    }
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
    
    public boolean refreshTable(String nameSender, Table tableSent){
    	int nbRow = tableSent.getRouteTable().size();
    	boolean hasChanged=false;
    	for(int i=0;i<nbRow;i++){
    		InputLink currLink = tableSent.getRouteTable().get(i).getLink();
    		int currCost = tableSent.getRouteTable().get(i).getCost();
    		int currAdress = tableSent.getRouteTable().get(i).getAdress();
    		InputLink il = new InputLink(this.id, nameSender);
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