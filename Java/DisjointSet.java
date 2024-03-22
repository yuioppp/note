public class DisjointSet {

    int[] father;

    // 判断两点间是否存在有效路径
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        father = new int[n];
        init();
        for (int[] edge : edges) {
            join(edge[0], edge[1]);
        }
        return isSame(source, destination);
    }

    // 并查集初始化
    public void init() {
        for (int i = 0; i < father.length; i++) {
            father[i] = i;
        }
    }

    // 寻根过程，递归
    public int find(int u) {
        if (u == father[u]) {
            return u;
        } else {
            father[u] = find(father[u]);
            return father[u];
        }
    }

    // 判断 u 和 v 是否为同一个根
    public boolean isSame(int u, int v) {
        u = find(u);
        v = find(v);
        return u == v;
    }

    // 将 v -> u 这条边加入并查集
    public void join(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v) return;      // 如果根相同，则在一个集合
        father[v] = u;
    }
}