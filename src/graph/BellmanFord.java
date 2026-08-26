package graph;

import edge.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * [벨만-포드 알고리즘]
 * 음수 가중치가 있는 그래프에서 단일 출발지 최단 경로를 계산하며, 음수 사이클을 감지.
 * 시작 지점은 미리 제공됨
 */
public final class BellmanFord {

    private static final long INF = Long.MAX_VALUE;

    private final long[] dist;
    private final int[] parent;
    private final boolean hasNegativeCycle;
    private final int n; // 그래프의 최대 정점 번호

    private BellmanFord(Graph graph, int src) {
        this.n = graph.size();

        int vertexCount = n + 1;
        this.dist = new long[vertexCount];
        this.parent = new int[vertexCount];

        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        checkVertex(src);
        dist[src] = 0;

        this.hasNegativeCycle = calculate(graph, vertexCount);
    }

    /**
     * 라이브러리 진입점
     * @param graph 탐색할 그래프 객체
     * @param src 시작 정점 번호
     * @return 계산이 완료된 BellmanFord 결과 객체
     */
    public static BellmanFord from(Graph graph, int src) {
        return new BellmanFord(graph, src);
    }

    // O(V * E) 최단 경로 계산 및 음수 사이클 확인
    private boolean calculate(Graph graph, int vertexCount) {
        List<Edge> edges = graph.edges();

        // V - 1번
        for (int i = 0; i < vertexCount - 1; i++) {
            boolean isUpdated = false;
            for (Edge edge : edges) {
                if (relax(edge)) {
                    isUpdated = true;
                }
            }
            // 한 번도 갱신이 일어나지 않았다면 이미 최단거리 완성으로 종료
            if (!isUpdated) {
                break;
            }
        }

        // 음수사이클 존재여부 확인
        for (Edge edge : edges) {
            if (relax(edge)) {
                return true;
            }
        }
        return false;
    }

    // Relaxation 수행으로 거리 갱신 및 갱신 발생 여부 반환
    private boolean relax(Edge edge) {
        int u = edge.u();
        int v = edge.v();
        long w = edge.w();

        if (dist[u] != INF && dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            parent[v] = u;
            return true;
        }
        return false;
    }

    /**
     * 그래프에 음수 사이클이 존재하는지 여부를 반환
     */
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    /**
     * 시작 정점으로부터 정점 v까지의 최단 거리 반환
     */
    public long distTo(int v) {
        checkVertex(v);
        if (hasNegativeCycle) {
            throw new IllegalStateException("음수 사이클이 존재하여 최단 거리를 특정할 수 없습니다.");
        }
        return dist[v];
    }

    /**
     * 시작 정점에서 정점 v까지의 최단 경로 리스트로 반환
     */
    public List<Integer> pathTo(int v) {
        checkVertex(v);
        if (hasNegativeCycle) {
            throw new IllegalStateException("음수 사이클이 존재하여 경로를 추적할 수 없습니다.");
        }
        // 도달 불가능한 경우
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

    /**
     * 도달 불가능 여부 확인 (무한대)
     */
    public boolean isReachable(int v) {
        checkVertex(v);
        return dist[v] != INF;
    }

    // 올바른 정점 번호인지 여부 확인
    private void checkVertex(int x) {
        if (x < 0 || x > n) {
            throw new IndexOutOfBoundsException("정점 번호 범위를 벗어났습니다: " + x);
        }
    }
}