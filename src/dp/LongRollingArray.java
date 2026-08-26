package dp;

import java.util.Arrays;

/**
 * [슬라이딩 윈도우 기반 공간 최적화 유틸리티]
 * 2차원 DP 테이블을 1차원 배열 2개(prev, curr)로 압축하여 메모리를 절약
 */
public final class LongRollingArray {

    private long[] prev;
    private long[] curr;

    public LongRollingArray(int size) {
        this.prev = new long[size];
        this.curr = new long[size];
    }

    public long[] prev() { return prev; }
    public long[] curr() { return curr; }

    /**
     * 루프가 한 번 끝날 때마다 현재 배열을 이전 배열로 스왑
     */
    public void swap() {
        long[] temp = prev;
        prev = curr;
        curr = temp;
    }

    /**
     * 스왑 직후, 새로운 curr 배열을 특정 값으로 초기화
     */
    public void fillCurr(long value) {
        Arrays.fill(curr, value);
    }
}