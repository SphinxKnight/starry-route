package ed.inf.ds.s1260575;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;



public class RouteThread {
	
	private ArrayList<Queue<MessageTable>> messagesList;
	
   
	public RouteThread() throws FileNotFoundException {
   		messagesList = new ArrayList<Queue<MessageTable>>();
   		ArrayList<InputCommand> alic = new ArrayList<InputCommand>();
   		ArrayList<InputNode> alin = new ArrayList<InputNode>();
   		ArrayList<InputLink> alil = new ArrayList<InputLink>();
   		ArrayList<String> idList = new ArrayList<String>(); 
//   		Interprete file
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
   		Input input1 = new Input(alin,alil,alic);
   		
   		
   		ArrayList<InputNode> alin1 = new ArrayList<InputNode>();
   		alin1 =(ArrayList<InputNode>) input1.getInput_nodes();
   		ArrayList<InputCommand> alic1 = new ArrayList<InputCommand>();
   		alic1 = (ArrayList<InputCommand>) input1.getInput_commands();
   		ArrayList<InputLink> alil1 = new ArrayList<InputLink>();
   		alil1 = (ArrayList<InputLink>) input1.getInput_links();
   		ArrayList<ThreadTransfert> altt = new ArrayList<ThreadTransfert>();
   		//Initiate table and Threads
   		ArrayList<String> listNameNodes= new ArrayList<String>();
   		for(InputNode inputnode : alin1){
   			listNameNodes.add(inputnode.name);
   			ConcurrentLinkedQueue<MessageTable> qmt = new ConcurrentLinkedQueue<MessageTable>();
   			messagesList.add(qmt);
   		}
   		
   		//Create Array List Table
   		
   		for (InputNode inputnode : alin1) {
			String currName = inputnode.name;
			Table currTable = new Table();
			ArrayList<LineTable> alli = new ArrayList<LineTable>();
			//Add local row(s)
			for (int adress : inputnode.local_addresses) {
				LineTable localLine = new LineTable(adress,new InputLink("local", "local"), 0);
				alli.add(localLine);
			}
			
//			//Catch link and register in table
//			for(InputLink inputLink : alil1){
//				if(inputLink.left_name.equals(currName)){
//					InputNode in = input1.getNodeFromName(inputLink.right_name);
//					for (int adress : in.local_addresses) {
//						LineTable lt=new LineTable(adress, inputLink, 1);
//						alli.add(lt);
//					}
//					
//				}
//				else if(inputLink.right_name.equals(currName)){
//					//Add the reverse link 
//					InputLink newLink = new InputLink(currName,inputLink.left_name);
//					InputNode in =input1.getNodeFromName(inputLink.left_name);
//					for (int adress : in.local_addresses) {
//						LineTable lt=new LineTable(adress, newLink, 1);
//						alli.add(lt);
//					}
//				}
//			}
			
			currTable.setRouteTable(alli);
			ThreadTransfert tt = new ThreadTransfert(listNameNodes, messagesList, inputnode.name, currTable, alil1);
			altt.add(tt);
   		}
   		
   		ArrayList<String> namesToSend = new ArrayList<String>();
   		for(InputCommand ic : alic1){
   			namesToSend.add(ic.process_name);
   		}
   		
//   		for (ThreadTransfert threadTransfert : altt) {
//        	threadTransfert.printTable();
//   		}
//   		System.out.println("_*_*_*_*_*_*_*_*_*_*_*_*");
   		//Launch everything 
        for (ThreadTransfert threadTransfert : altt) {
         	if(namesToSend.contains(threadTransfert.getIdd())){
//           		System.out.println("Primary send from : "+threadTransfert.getIdd());
        		threadTransfert.sendTableToAll();
        	}
        }
       
        for (ThreadTransfert threadTransfert : altt) {
        	threadTransfert.start();
        	
		}
        //Don't add anything here (mess with resources)
        
        return;
      
    }
   	
	  public static void main(String[] args) throws FileNotFoundException {
		RouteThread rc = new RouteThread();
		return;
	}
	
}
