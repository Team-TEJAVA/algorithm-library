package graph;

import edge.Edge;

import java.util.ArrayList;
import java.util.List;

public final class Graph {

    private final int n; // 정점 개수
    private final boolean directed; // 방향/무방향 구분
    private final List<Edge>[] adj; // 인접 리스트
    private final List<Edge> edges; // 전체 간선 목록

    @SuppressWarnings("unchecked")
    private Graph(int n, boolean directed) {
        if (n < 0) {
            throw new IllegalArgumentException("정점 수는 음수일 수 없습니다: " + n);
        }
        this.n = n;
        this.directed = directed;
        this.adj = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            this.adj[i] = new ArrayList<>();
        }
        this.edges = new ArrayList<>();
    }

    // 방향 그래프
    public static Graph directed(int n) {
        return new Graph(n, true);
    }

    // 무방향 그래프
    public static Graph undirected(int n) {
        return new Graph(n, false);
    }

    // 가중치 간선 추가
    public Graph addEdge(int u, int v, long w) {
        checkVertex(u);
        checkVertex(v);
        adj[u].add(new Edge(u, v, w));
        if (!directed) {
            adj[v].add(new Edge(v, u, w));
        }
        edges.add(new Edge(u, v, w));
        return this;
    }

    // 가중치 없는 간선 추가
    public Graph addEdge(int u, int v) {
        return addEdge(u, v, 1L);
    }

    // 간선 배열을 한 번에 등록
    public Graph addEdges(int[][] edgeArray) {
        for (int[] e : edgeArray) {
            if (e.length == 2) {
                addEdge(e[0], e[1]); // {u,v}
            } else if (e.length == 3) {
                addEdge(e[0], e[1], e[2]); // {u,v,w}
            } else {
                throw new IllegalArgumentException(
                        "간선은 {u,v} 또는 {u,v,w} 형태여야 합니다. 실제 길이=" + e.length
                );
            }
        }
        return this;
    }

    // 정점 개수
    public int size() {
        return n;
    }

    // 방향/무방향 여부
    public boolean directed() {
        return directed;
    }

    // u의 이웃 목록
    public List<Edge> neighbors(int u) {
        checkVertex(u);
        return adj[u];
    }

    // 전체 간선
    public List<Edge> edges() {
        return edges;
    }

    private void checkVertex(int x) {
        if (x < 0 || x > n) {
            throw new IndexOutOfBoundsException(
                    "정점 번호 범위를 벗어났습니다: " + x + " (허용 범위: 0.." + n + ")"
            );
        }
    }
}
