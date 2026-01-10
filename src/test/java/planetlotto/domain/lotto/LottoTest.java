package planetlotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LottoTest {
    private static final int LOTTO_SIZE = 5;
    private static final String DUPLICATED_ERROR_MESSAGE = "중복";

    @ParameterizedTest
    @MethodSource("provideNotFitToLottoSizeNumbers")
    void 로또가_가지는_로또_번호가_5개가_아니면_예외를_발생한다(List<Integer> notFitToLottoSizeNumbers) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Lotto.of(notFitToLottoSizeNumbers))
                .withMessageContaining(String.valueOf(LOTTO_SIZE));
    }

    @ParameterizedTest
    @MethodSource("provideOutOfRangedNumbers")
    void 로또가_가지는_로또_번호가_범위를_벗어나면_예외를_발생한다(List<Integer> OutOfRangedNumbers) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Lotto.of(OutOfRangedNumbers))
                .withMessageContaining(
                        String.valueOf(LottoNumberTest.MIN_NUMBER),
                        String.valueOf(LottoNumberTest.MAX_NUMBER));
    }

    @Test
    void 로또가_중복된_번호를_가지면_예외를_발생한다() {
        List<Integer> duplicatedNumbers = List.of(1, 1, 2, 3, 4);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Lotto.of(duplicatedNumbers))
                .withMessageContaining(DUPLICATED_ERROR_MESSAGE);
    }

    @Test
    void 로또_정상_생성() {
        List<Integer> validNumbers = List.of(1, 2, 3, 4, 5);
        // TODO: LottoTest에서 LottoNumber 로직 불러오는게 맘에 들지는 않지만 일단 이대로 진행
        List<LottoNumber> expectedLottoNumbers = validNumbers.stream()
                .map(LottoNumber::from)
                .toList();

        Lotto lotto = Lotto.of(validNumbers);

        assertThat(lotto.getNumbers()).isEqualTo(expectedLottoNumbers);
    }

    @Test
    void 로또가_가지는_로또_번호는_오름차순으로_정렬된다() {
        List<Integer> unSortedNumbers = List.of(5, 1, 3, 2, 4);

        Lotto lotto = Lotto.of(unSortedNumbers);

        assertThat(lotto.getNumbers()).isSorted();
    }

    private static Stream<Arguments> provideNotFitToLottoSizeNumbers() {
        return Stream.of(
                Arguments.of((List.of(1, 2, 3, 4, 5, 6))),  // 6개
                Arguments.of((List.of(1, 2, 3, 4))),        // 4개
                Arguments.of(List.of())                     // 0개
        );
    }

    private static Stream<Arguments> provideOutOfRangedNumbers() {
        return Stream.of(
                Arguments.of((List.of(0, 1, 2, 3, 4))),     // 하한 범위 초과
                Arguments.of((List.of(1, 2, 3, 4, 31))),    // 상한 범위 초과
                Arguments.of(List.of(-1, 1, 2, 3, 4))       // 음수 포함
        );
    }
}
