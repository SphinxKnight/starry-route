package ed.inf.ds.s1260575;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * The "monitor" class : scans the init file,
 * creates the threads for each node with the corresponding information
 * takes care of sending "send" init events
 * @author Julien-G
 *
 */
public class RouteThread {
	
	/**
	 * The attribute to store the messages 
	 */
	private ArrayList<Queue<MessageTable>> messagesList;
	
   //Constructor and only method
	public RouteThread() throws FileNotFoundException {
   		messagesList = new ArrayList<Queue<MessageTable>>();
   		
   		//Init from "init.txt"
   		//Skip these lines if you want to use Input object initial constructor for testing
   		ArrayList<InputCommand> alic = new ArrayList<InputCommand>();
   		ArrayList<InputNode> alin = new ArrayList<InputNode>();
   		ArrayList<InputLink> alil = new ArrayList<InputLink>();
   		//Interprete file
   		Scanner sc = new Scanner(new File("init.txt"));
   		while(sc.hasNextLine()){
   			String line= sc.nextLine();
   			Scanner lineScanner = new Scanner(line);
   			if(line.startsWith("node")){
   				lineScanner.next();
   				String name=lineScanner.next();
   				ArrayList<Integer> ln = new ArrayList<Integer>();
   				while(lineScanner.hasNext()){
   					ln.add(Integer.parseInt(lineScanner.next()));
   				}
   				int intList[]=new int[ln.toArray().length];
   				for (int i=0;i<ln.size();i++) intList[i]=ln.get(i);
   				InputNode in = new InputNode(name,intList);
   				alin.add(in);
   			}
   			else if(line.startsWith("link")){
   				lineScanner.next();
   				InputLink iL = new InputLink(lineScanner.next(),lineScanner.next());
   				alil.add(iL);
   			}
   			else if(line.startsWith("send")){
   				lineScanner.next();
   				InputCommand ic = new InputCommand("send", lineScanner.next());
   				alic.add(ic);
   			}
   		}
   		//End of scanning init.txt 
   		
   		//REPLACE HERE FOR USE OF DEFAULT CONSTRUCTOR//
   		
   		Input input1 = new Input(alin,alil,alic);
   
   		//REPLACE HERE FOR CORRECTION PURPOSE       //
   		
   		//Use new objects so that there is no risk between reading the file or using the default constructor 
   		ArrayList<InputNode> alin1 = new ArrayList<InputNode>();
   		alin1 =(ArrayList<InputNode>) input1.getInput_nodes();
   		ArrayList<InputCommand> alic1 = new ArrayList<InputCommand>();
   		alic1 = (ArrayList<InputCommand>) input1.getInput_commands();
   		ArrayList<InputLink> alil1 = new ArrayList<InputLink>();
   		alil1 = (ArrayList<InputLink>) input1.getInput_links();
   		ArrayList<ThreadTransfert> altt = new ArrayList<ThreadTransfert>();
   		
   		//Initiate table and Threads
   		ArrayList<String> listNameNodes= new ArrayList<String>();
   		
   		//Fill messagesList with empty queues
   		for(InputNode inputnode : alin1){
   			listNameNodes.add(inputnode.name);
   			ConcurrentLinkedQueue<MessageTable> qmt = new ConcurrentLinkedQueue<MessageTable>();
   			messagesList.add(qmt);
   		}
   		
   		//Create Table for each node  
   		for (InputNode inputnode : alin1) {
			Table currTable = new Table();
			ArrayList<LineTable> alli = new ArrayList<LineTable>();
			//Add local row(s) for init
			for (int adress : inputnode.local_addresses) {
				LineTable localLine = new LineTable(adress,new InputLink("local", "local"), 0);
				alli.add(localLine);
			}
			currTable.setRouteTable(alli);
			ThreadTransfert tt = new ThreadTransfert(listNameNodes, messagesList, inputnode.name, currTable, alil1);
			altt.add(tt);
   		}
   		
   		
   		//transform a little bit the ArrayList of InputCommand ...
   		ArrayList<String> namesToSend = new ArrayList<String>();
   		for(InputCommand ic : alic1){
   			namesToSend.add(ic.process_name);
   		}
   		

   		//Launch everything 
   		
   		//1-Initiate sending commands
        for (ThreadTransfert threadTransfert : altt) {
         	if(namesToSend.contains(threadTransfert.getIdd())){
        		threadTransfert.sendTableToAll();
        	}
        }
       
        //2-Here we are
        for (ThreadTransfert threadTransfert : altt) {
        	threadTransfert.start();
        	
		}
        //The job here is being done by the other threads
        
        return;
      
    }
   	
	  @SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		RouteThread rc = new RouteThread();
		
		return;
	}
	
}
