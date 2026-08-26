package graph;

import edge.Edge;

import java.util.*;

/**
 * [위상정렬 알고리즘]
 * 방향 비순환 그래프(DAG)의 정점을 선후 관계에 맞게 정렬한다.
 * 진입 차수가 0인 정점부터 차례대로 제거하는 칸 알고리즘을 사용한다.
 */
public final class TopologicalSort {

    private final List<Integer> order;
    private final boolean hasCycle;
    private final int n; // 그래프의 최대 정점 번호

    private TopologicalSort(Graph graph) {
        Objects.requireNonNull(graph, "graph는 null일 수 없습니다.");

        if (!graph.directed()) {
            throw new IllegalArgumentException("위상정렬은 방향 그래프에서만 사용할 수 있습니다.");
        }

        this.n = graph.size();
        List<Integer> sorted = calculate(graph);
        this.order = Collections.unmodifiableList(sorted);
        this.hasCycle = sorted.size() != n + 1;
    }

    /**
     * 라이브러리 진입점.
     *
     * @param graph 위상정렬할 방향 그래프
     * @return 계산이 완료된 TopologicalSort 결과 객체
     */
    public static TopologicalSort from(Graph graph) {
        return new TopologicalSort(graph);
    }

    /**
     * 위상정렬 결과를 반환.
     *
     * @throws IllegalStateException 그래프에 사이클이 존재하는 경우
     */
    public List<Integer> order() {
        if (hasCycle) {
            throw new IllegalStateException("그래프에 사이클이 존재하여 위상정렬할 수 없습니다.");
        }
        return order;
    }

    /** 그래프에 사이클이 존재하는지 반환. */
    public boolean hasCycle() {
        return hasCycle;
    }

    // O(V + E) 위상정렬
    private List<Integer> calculate(Graph graph) {
        int[] indegree = new int[n + 1];

        // 각 정점의 진입 차수 계산
        for (Edge edge : graph.edges()) {
            indegree[edge.v()]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();

        // 진입 차수가 0인 모든 정점을 큐에 추가
        for (int vertex = 0; vertex <= n; vertex++) {
            if (indegree[vertex] == 0) {
                queue.offer(vertex);
            }
        }

        List<Integer> result = new ArrayList<>(n + 1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            for (Edge edge : graph.neighbors(current)) {
                int next = edge.v();
                if (--indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return result;
    }
}
