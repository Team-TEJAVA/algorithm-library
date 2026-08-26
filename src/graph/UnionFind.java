package graph;

public class UnionFind {

    private final int[] parent; // parent[x] = x의 부모. 루트면 자기 자신
    private final int[] size; // size[root] = 그 그룹의 원소 수. 루트에서만 유효한 값.
    private final int n; // 최대 정점 번호. 유효 인덱스는 0..n.
    private int components; // 현재 그룹의 개수.

    public UnionFind(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("정점 번호는 음수일 수 없습니다: " + n);
        }
        this.n = n;
        int capacity = n + 1;
        this.parent = new int[capacity];
        this.size = new int[capacity];
        this.components = capacity;
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * x가 속한 그룹의 루트를 반환한다.
     * union by size로 트리 높이가 O(log n)이라 재귀 깊이도 그만큼 얕아 스택 안전.
     */
    public int find(int x) {
        checkVertex(x);
        return findRoot(x);
    }

    /**
     * a와 b가 속한 두 그룹을 합친다.
     * @return 실제로 합쳐졌으면 true, 이미 같은 그룹이었으면 false.
     */
    public boolean union(int a, int b) {
        checkVertex(a);
        checkVertex(b);
        int ra = findRoot(a);
        int rb = findRoot(b);
        if (ra == rb) {
            return false; // 이미 같은 그룹 -> 합칠 것 없음.
        }
        if (size[ra] < size[rb]) {
            int tmp = ra;
            ra = rb;
            rb = tmp;
        }
        parent[rb] = ra;
        size[ra] += size[rb];
        components--;
        return true;
    }

    /** a와 b가 같은 그룹이면 true. */
    public boolean connected(int a, int b) {
        checkVertex(a);
        checkVertex(b);
        return findRoot(a) == findRoot(b);
    }

    /** x가 속한 그룹의 원소 수. */
    public int sizeOf(int x) {
        checkVertex(x);
        return size[findRoot(x)];
    }

    /** 현재 그룹의 개수. */
    public int countComponents() {
        return components;
    }

    // 이미 검증된 인덱스에 대해서만 호출(중복 검사 회피).
    private int findRoot(int x) {
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = findRoot(parent[x]);
    }

    // 올바른 정점 번호(0..n)인지 확인.
    private void checkVertex(int x) {
        if (x < 0 || x > n) {
            throw new IndexOutOfBoundsException("정점 번호 범위를 벗어났습니다: "
                    + x + " (허용: 0.." + n + ")");
        }
    }
}
