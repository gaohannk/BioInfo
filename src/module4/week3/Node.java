package module4.week3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static common.Constant.nucle;

public class Node {

    public char[] character;
    public int id;
    public Node left;
    public Node right;
    public Map<Character, Integer> symbolMap;

    public Node(int id, int len) {
        this.id = id;
        this.character = new char[len];
        this.symbolMap = new HashMap<>();
        for (char c : nucle) {
            this.symbolMap.put(c, Integer.MAX_VALUE);
        }
    }

    public Node(int id, char[] character) {
        this.id = id;
        this.character = character;
        this.symbolMap = new HashMap<>();
        for (char c : nucle) {
            this.symbolMap.put(c, Integer.MAX_VALUE);
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "character=" + Arrays.toString(character) +
                ", id=" + id +
                ", left=" + left +
                ", right=" + right +
                ", symbolMap=" + symbolMap +
                '}';
    }
}
