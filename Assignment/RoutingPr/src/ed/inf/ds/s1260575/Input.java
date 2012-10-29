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
}
