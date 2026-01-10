package planetlotto.domain;

import java.util.List;
import java.util.Set;

public class Lotto {
    private static final int LOTTO_SIZE = 5;

    private final List<LottoNumber> numbers;

    private Lotto(List<Integer> numbers) {
        this.numbers = numbers.stream()
                .map(LottoNumber::from)
                .sorted()
                .toList();
    }

    public static Lotto of(List<Integer> numbers) {
        List<Integer> copyOfNumbers = List.copyOf(numbers);
        validate(copyOfNumbers);

        return new Lotto(copyOfNumbers);
    }

    private static void validate(List<Integer> numbers) {
        requireFitToLottoSize(numbers);
        requireNoDuplicates(numbers);
    }

    private static void requireFitToLottoSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("로또는 " + LOTTO_SIZE + "개의 숫자를 가져야 합니다.");
        }
    }

    private static void requireNoDuplicates(List<Integer> numbers) {
        Set<Integer> distinctNumbers = Set.copyOf(numbers);

        if (distinctNumbers.size() < numbers.size()) {
            throw new IllegalArgumentException("로또가 가지는 로또 번호에는 중복이 없어야 합니다.");
        }
    }

    public List<LottoNumber> getNumbers() {
        return List.copyOf(numbers);
    }
}
