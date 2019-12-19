package module4.week2;

import java.util.List;

public class Cluster {
	public int id;
	public List<Node> nodes;
	public int size;
	public Node headNode;


	public Cluster(int id, List<Node> nodes, Node headNode){
		this.id = id;
		this.nodes = nodes;
		this.size = nodes.size();
		this.headNode = headNode;
	}
}
