package dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * [공통 경로 역추적 유틸리티]
 * DP나 그래프 탐색 종료 후, parent 배열을 따라가며 최적 경로를 리스트로 복원
 */
public final class Backtracker {

    private Backtracker() {}

    /**
     * 1차원 parent 배열 기반 역추적
     * @param parent 각 상태의 이전 상태가 기록된 배열
     * @param endState 도착 상태 (역추적 시작점)
     * @param stopState 추적을 종료할 기저 상태 (보통 -1)
     * @return 출발지부터 도착지까지의 순차적 경로
     */
    public static List<Integer> reconstruct(int[] parent, int endState, int stopState) {
        List<Integer> path = new ArrayList<>();

        for (int curr = endState; curr != stopState; curr = parent[curr]) {
            if (curr < 0 || curr >= parent.length) {
                break;
            }
            path.add(curr);
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * stopState = -1인 경우의 메서드
     */
    public static List<Integer> reconstruct(int[] parent, int endState) {
        return reconstruct(parent, endState, -1);
    }
}