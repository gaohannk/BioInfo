package module3.common;

public class Node implements Comparable<Node> {
	public int x, y;
	public char direct;

	public Node(int x, int y, char direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	@Override
	public int compareTo(Node o) {
		if (this.x < o.x) {
			if (this.y <= o.y) {
				return -1;
			} else {
				return 1;
			}
		} else if (this.x == o.x) {
			if (this.y <= o.y) {
				return -1;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}
}
