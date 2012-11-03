package ed.inf.ds.s1260575;

import java.util.ArrayList;
import java.util.List;

public class Input{
	List<InputNode> input_nodes = new ArrayList<InputNode>();
	List<InputLink> input_links = new ArrayList<InputLink>();
	List<InputCommand> input_commands = new ArrayList<InputCommand>();
	public Input(){
		input_nodes.add(new InputNode("p1", new int[] {1}));
		input_nodes.add(new InputNode("p1",new int[] {2}));
		input_nodes.add(new InputNode("p1",new int[] {3}));
		input_nodes.add(new InputNode("p1",new int[] {4,5}));
		input_links.add(new InputLink("p1", "p2"));
		input_links.add(new InputLink("p1", "p2"));
		input_links.add(new InputLink("p1", "p2"));
		input_links.add(new InputLink("p1", "p2"));
		input_commands.add(new InputCommand("send", "p1"));
		
	}
	public Input(List<InputNode> input_nodes, List<InputLink> input_links,
			List<InputCommand> input_commands) {
		super();
		this.input_nodes = input_nodes;
		this.input_links = input_links;
		this.input_commands = input_commands;
	}
	public List<InputNode> getInput_nodes() {
		return input_nodes;
	}
	public List<InputLink> getInput_links() {
		return input_links;
	}
	public List<InputCommand> getInput_commands() {
		return input_commands;
	}
	public InputNode getNodeFromName(String name){
		for(int i=0;i<input_nodes.size();i++){
			if(input_nodes.get(i).name.equals(name)){
				return input_nodes.get(i);
			}
		}
		return null;
	}
}
