package planetlotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class LottoNumberTest {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 30;

    @ParameterizedTest
    @ValueSource(ints = {
            -1,     // 음수
            0, 31   // 경계값
    })
    void 범위를_초과하는_로또_번호를_생성하려고_하면_예외를_발생한다(int outOfRangedNumber) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LottoNumber.from(outOfRangedNumber));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 30, // 경계값
            2, 12, 22 // 범위 내의 임의값
    })
    void 로또_번호_정상_생성(int expectedNumber) {
        LottoNumber lottoNumber = LottoNumber.from(expectedNumber);

        assertThat(lottoNumber.getNumber()).isEqualTo(expectedNumber);
    }

    @ParameterizedTest
    @MethodSource("provideLotOfLottoNumbers")
    void 로또_번호는_오름차순으로_정렬된다(List<LottoNumber> lottoNumbers) {
        Stream<LottoNumber> naturalOrderSorted = lottoNumbers.stream()
                .sorted();

        assertThat(naturalOrderSorted).isSorted();
    }

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3, 11, 22, 30
    })
    void 로또_번호의_동일성은_번호로_판단한다(int number) {
        LottoNumber lottoNumber = LottoNumber.from(number);
        LottoNumber otherLottoNumber = LottoNumber.from(number);

        assertThat(lottoNumber).isSameAs(otherLottoNumber);
    }

    private static Stream<Arguments> provideLotOfLottoNumbers() {
        return Stream.of(
                Arguments.of(getLottoNumbers(List.of(1, 2, 3, 4, 5))), // 오름차순
                Arguments.of(getLottoNumbers(List.of(6, 5, 4, 3, 2))), // 내림차순
                Arguments.of(getLottoNumbers(List.of(1, 10, 7, 22, 15))) // 뒤죽박죽
        );
    }

    private static List<LottoNumber> getLottoNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(LottoNumber::from)
                .toList();
    }
}
