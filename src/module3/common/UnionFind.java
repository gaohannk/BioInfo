package module3.common;

import java.util.Arrays;

public class UnionFind {
    public int[] id;

    public UnionFind(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    private int find(int i) {
        while (i != id[i])
            i = id[i];
        return i;
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public void unite(int p, int q) {
        int i = find(p);
        int j = find(q);
        id[i] = j;
    }

    public int getCount() {
        int count = 0;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == i) {
                count++;
            }
        }
        return count;
    }
}