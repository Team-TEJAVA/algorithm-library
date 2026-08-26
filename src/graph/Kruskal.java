package graph;

import edge.Edge;

import java.util.*;

/**
 * [크루스칼 알고리즘]
 * 무방향 가중치 그래프의 최소 신장 트리(MST)를 구한다.
 * 간선을 가중치 오름차순으로 확인하며, 사이클을 만들지 않는 간선만 선택한다.
 * 그래프가 연결되어 있지 않으면 각 연결 요소의 최소 신장 트리를 합친 최소 신장 숲(MSF)을 반환한다.
 */
public final class Kruskal {

    private final long totalWeight;
    private final List<Edge> selectedEdges;
    private final boolean connected;

    private Kruskal(Graph graph) {
        Objects.requireNonNull(graph, "graph는 null일 수 없습니다.");
        if (graph.directed()) {
            throw new IllegalArgumentException("크루스칼 알고리즘은 무방향 그래프에서만 사용할 수 있습니다.");
        }

        int vertexCount = graph.size();
        List<Edge> sortedEdges = new ArrayList<>(graph.edges());
        sortedEdges.sort(Comparator.comparingLong(Edge::w));

        UnionFind unionFind = new UnionFind(vertexCount);
        List<Edge> picked = new ArrayList<>();
        long weight = 0L;

        for (Edge edge : sortedEdges) {
            if (unionFind.union(edge.u(), edge.v())) {
                picked.add(edge);
                weight += edge.w();

                // 정점이 V개인 트리의 간선 수는 V - 1개이다.
                if (picked.size() == vertexCount - 1) {
                    break;
                }
            }
        }

        this.totalWeight = weight;
        this.selectedEdges = Collections.unmodifiableList(picked);
        this.connected = vertexCount <= 1 || picked.size() == vertexCount - 1;
    }

    /**
     * 라이브러리 진입점.
     *
     * @param graph 최소 신장 트리를 구할 무방향 가중치 그래프
     * @return 계산이 완료된 Kruskal 결과 객체
     */
    public static Kruskal from(Graph graph) {
        return new Kruskal(graph);
    }

    /** 선택된 간선의 가중치 총합을 반환. */
    public long weight() {
        return totalWeight;
    }

    /** 최소 신장 트리(또는 최소 신장 숲)를 구성하는 수정 불가능한 간선 목록을 반환. */
    public List<Edge> edges() {
        return selectedEdges;
    }

    /** 모든 정점이 하나의 최소 신장 트리로 연결되었는지 반환. */
    public boolean isConnected() {
        return connected;
    }
}
