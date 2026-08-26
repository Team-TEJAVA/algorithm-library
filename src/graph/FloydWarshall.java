package graph;

import edge.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * [플로이드-워셜 알고리즘]
 * 그래프 내의 모든 정점 쌍 간의 최단 경로를 계산하며, 음수 사이클을 감지.
 */
public final class FloydWarshall {

    private static final long INF = Long.MAX_VALUE;

    private final long[][] dist;
    private final int[][] next; // 경로 역추적을 위한 배열
    private final boolean hasNegativeCycle;
    private final int n; // 그래프의 최대 정점 번호

    private FloydWarshall(Graph graph) {
        this.n = graph.size();
        int vertexCount = n + 1;

        this.dist = new long[vertexCount][vertexCount];
        this.next = new int[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(next[i], -1);
            dist[i][i] = 0;
        }

        // 초기 간선 가중치 반영
        for (int u = 0; u < vertexCount; u++) {
            for (Edge edge : graph.neighbors(u)) {
                int v = edge.v();
                long w = edge.w();
                if (w < dist[u][v]) {
                    dist[u][v] = w;
                    next[u][v] = v;
                }
            }
        }

        this.hasNegativeCycle = calculate(vertexCount);
    }

    /**
     * 라이브러리 진입점
     * @param graph 탐색할 그래프 객체
     * @return 계산이 완료된 FloydWarshall 결과 객체
     */
    public static FloydWarshall from(Graph graph) {
        return new FloydWarshall(graph);
    }

    // O(V^3) 최단 경로 계산 및 음수 사이클 확인
    private boolean calculate(int vertexCount) {
        // 경유지
        for (int k = 0; k < vertexCount; k++) {
            // 출발 노드
            for (int i = 0; i < vertexCount; i++) {
                // 도착 노드
                for (int j = 0; j < vertexCount; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }

        // 음수 사이클 존재 여부 확인
        for (int i = 0; i < vertexCount; i++) {
            if (dist[i][i] < 0) {
                return true;
            }
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
     * 정점 u에서 정점 v까지의 최단 거리 반환
     */
    public long dist(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        if (hasNegativeCycle) {
            throw new IllegalStateException("음수 사이클이 존재하여 최단 거리를 특정할 수 없습니다.");
        }
        return dist[u][v];
    }

    /**
     * 정점 u에서 정점 v까지의 최단 경로를 리스트로 반환
     */
    public List<Integer> pathTo(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        if (hasNegativeCycle) {
            throw new IllegalStateException("음수 사이클이 존재하여 경로를 추적할 수 없습니다.");
        }
        // 도달 불가능한 경우
        if (dist[u][v] == INF) {
            return Collections.emptyList();
        }

        List<Integer> path = new ArrayList<>();
        for (int curr = u; curr != v; curr = next[curr][v]) {
            if (curr == -1) {
                return Collections.emptyList();
            }
            path.add(curr);
        }
        path.add(v);
        return path;
    }

    /**
     * 정점 u에서 정점 v로 도달 가능한지 여부 반환
     */
    public boolean isReachable(int u, int v) {
        checkVertex(u);
        checkVertex(v);
        return dist[u][v] != INF;
    }

    // 올바른 정점 번호인지 여부 확인
    private void checkVertex(int x) {
        if (x < 0 || x > n) {
            throw new IndexOutOfBoundsException("정점 번호 범위를 벗어났습니다: " + x);
        }
    }
}