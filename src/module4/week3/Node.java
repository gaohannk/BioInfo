package module4.week3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static common.Constant.nucle;

public class Node {

    public char[] character;
    public int id;
    public Node left;
    public Node right;
    public Map<Character, Integer> symbolMap;
    public int score;

    public Node(int id, int len, Node left, Node right) {
        this.id = id;
        this.character = new char[len];
        this.symbolMap = new HashMap<>();
        this.left = left;
        this.right = right;
    }

    public Node(int id, char[] character) {
        this.id = id;
        this.character = character;
        this.symbolMap = new HashMap<>();
        for (char c : nucle) {
            this.symbolMap.put(c, Integer.MAX_VALUE);
        }
    }

    public Node(int id, int len) {
        this.id = id;
        this.character = new char[len];
        this.symbolMap = new HashMap<>();

    }

    public Node(Node other) {
        this.character  = other.character;
        this.id = other.id;
        this.left = other.left;
        this.right = other.right;
        this.symbolMap = other.symbolMap;
        this.score = other.score;
    }

    public Node(int id) {
        this.id = id;
    }

    public static int getHammingDistance(char[] v, char[] w) {
        int count = 0;
        for (int i = 0; i < v.length; i++) {
            if (v[i] != w[i])
                count++;
        }
        return count;
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
