package graph;

import edge.Edge;

import java.util.*;

/**
 * [다익스트라 알고리즘]
 * 음수가 아닌 가중치 그래프에서 단일 출발지 최단 경로를 계산한다.
 * 매번 가장 가까운 미확정 정점을 우선순위 큐로 꺼내 이웃을 완화(relaxation)한다.
 * 전제:간선 가중치가 음수이면 안 된다. 음수 간선이 있으면 BellmanFord를 사용할 것.
 */
public final class Dijkstra {

    private static final long INF = Long.MAX_VALUE;
    private final long[] dist;
    private final int[] parent;
    private final int n; // 그래프의 최대 정점 번호.

    // 우선순위 큐 항목: (정점, 그 정점까지의 잠정 거리)
    private record Node(int vertex, long dist) {}

    private Dijkstra(Graph graph, int src) {
        this.n = graph.size();
        int vertexCount = n + 1;
        this.dist = new long[vertexCount];
        this.parent = new int[vertexCount];
        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);
        checkVertex(src);
        dist[src] = 0;
        calculate(graph, src);
    }

    /**
     * 라이브러리 진입점.
     * @param graph 대상 그래프(음수 가중치 없음)
     * @param src 시작 정점 번호
     * @return 계산이 완료된 Dijkstra 결과 객체
     */
    public static Dijkstra from(Graph graph, int src) {
        return new Dijkstra(graph, src);
    }

    /** 시작 정점에서 정점 v까지의 최단 거리(도달 불가면 INF). */
    public long distTo(int v) {
        checkVertex(v);
        return dist[v];
    }

    /** 시작 정점에서 정점 v까지 도달 가능하면 true. */
    public boolean isReachable(int v) {
        checkVertex(v);
        return dist[v] != INF;
    }

    /** 시작 정점에서 정점 v까지의 최단 경로(도달 불가면 빈 리스트). */
    public List<Integer> pathTo(int v) {
        checkVertex(v);
        if (dist[v] == INF) {
            return Collections.emptyList();
        }
        List<Integer> path = new ArrayList<>();
        for (int curr = v; curr != -1; curr = parent[curr]) {
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }

    // O(E log V) 최단 경로 계산
    private void calculate(Graph graph, int src) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(Node::dist));
        pq.offer(new Node(src, 0));
        while (!pq.isEmpty()) {
            Node top = pq.poll();
            int u = top.vertex();
            // 큐에 남아 있던 이미 더 짧게 확정된 항목이면 무시.
            if (top.dist() > dist[u]) {
                continue;
            }
            for (Edge edge : graph.neighbors(u)) {
                int v = edge.v();
                long w = edge.w();
                // 완화: u를 거쳐 가는 게 더 짧으면 갱신하고 큐에 넣는다.
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }
    }

    // 올바른 정점 번호인지 확인.
    private void checkVertex(int x) {
        if (x < 0 || x > n) {
            throw new IndexOutOfBoundsException("정점 번호 범위를 벗어났습니다: " + x);
        }
    }
}
