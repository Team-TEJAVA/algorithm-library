package dp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * [자동 메모이제이션]
 * 재귀 형태의 DP 점화식에 캐싱 기능을 동적으로 부여.
 * @param <T> 상태를 나타내는 입력 타입 (Integer, String 등)
 * @param <R> 계산 결과 반환 타입 (Long, Integer 등)
 */
public final class Memoizer<T, R> {

    private final Map<T, R> cache = new HashMap<>();
    private final Function<T, R> function;

    private Memoizer(Function<Memoizer<T, R>, Function<T, R>> functionBuilder) {
        this.function = functionBuilder.apply(this);
    }

    /**
     * 캐싱 기능이 적용된 함수를 생성하여 반환
     */
    public static <T, R> Function<T, R> create(Function<Memoizer<T, R>, Function<T, R>> functionBuilder) {
        Memoizer<T, R> memoizer = new Memoizer<>(functionBuilder);
        return memoizer::call;
    }

    /**
     * 상태가 캐시에 존재하면 즉시 반환하고, 없으면 계산 후 캐시에 저장
     */
    public R call(T input) {
        return cache.computeIfAbsent(input, function);
    }
}