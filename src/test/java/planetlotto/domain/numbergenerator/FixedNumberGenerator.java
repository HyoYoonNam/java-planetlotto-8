package planetlotto.domain.numbergenerator;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class FixedNumberGenerator implements NumberGenerator {
    private final Queue<List<Integer>> numbersQueue = new ArrayDeque<>();

    @SafeVarargs
    public FixedNumberGenerator(List<Integer>... numbersLists) {
        for (List<Integer> numbers : numbersLists) {
            numbersQueue.offer(numbers);
        }
    }

    @Override
    public List<Integer> generate() {
        if (numbersQueue.isEmpty()) {
            throw new IllegalArgumentException("테스트용 번호 리스트를 모두 소모함");
        }

        return numbersQueue.poll();
    }
}
