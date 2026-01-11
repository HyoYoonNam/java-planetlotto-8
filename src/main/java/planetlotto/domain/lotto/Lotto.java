package planetlotto.domain.lotto;

import java.util.List;
import java.util.Set;

public class Lotto {
    public static final int LOTTO_SIZE = 5;

    private final List<LottoNumber> numbers;

    // 생성자는 최대한 할당만 하는게 좋다. Integer로 받고, 여기서 LottoNumber를 생성하면 너무 무거움.
    private Lotto(List<LottoNumber> numbers) {
        this.numbers = numbers.stream()
                // 정렬은 무조건 보장되어야 하므로 (혹시 다른 정적 팩토리 메서드가 추가될 수도 있으니) 생성자에서 일괄 처리하는 것이 좋다.
                .sorted()
                .toList();
    }

    public static Lotto of(List<Integer> numbers) {
        // 바로 스트림 때려서 사용하는 경우에는 어차피 side effect가 없고, 새로운 리스트를 생성하기 때문에 방어적 복사가 불필요하다.
        List<LottoNumber> lottoNumbers = numbers.stream()
                .map(LottoNumber::from)
                .toList();

        validate(lottoNumbers);

        return new Lotto(lottoNumbers);
    }

    private static void validate(List<LottoNumber> numbers) {
        requireFitToLottoSize(numbers);
        requireNoDuplicates(numbers);
    }

    private static void requireFitToLottoSize(List<LottoNumber> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("로또는 " + LOTTO_SIZE + "개의 숫자를 가져야 합니다.");
        }
    }

    private static void requireNoDuplicates(List<LottoNumber> numbers) {
        Set<LottoNumber> distinctNumbers = Set.copyOf(numbers);

        if (distinctNumbers.size() < numbers.size()) {
            throw new IllegalArgumentException("로또가 가지는 로또 번호에는 중복이 없어야 합니다.");
        }
    }

    public boolean contains(LottoNumber lottoNumber) {
        return numbers.contains(lottoNumber);
    }

    public List<LottoNumber> getLottoNumbers() {
        return List.copyOf(numbers);
    }

    public List<Integer> getNumbers() {
        return numbers.stream()
                .map(LottoNumber::getNumber)
                .toList();
    }
}
