package planetlotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import planetlotto.domain.numbergenerator.FixedNumberGenerator;

class LottoMachineTest {
    private static final int PRICE_PER_LOTTO = 500;
    private static final String NEGATIVE_PURCHASE_AMOUNT_ERROR_MESSAGE = "음수";

    @ParameterizedTest
    @MethodSource("provideUnDivisibleByLottoPrice")
    void 로또_구입_금액이_로또_1장의_가격으로_나누어떨어지지_않으면_예외를_발생한다(int unDivisibleByLottoPrice) {
        LottoMachine lottoMachine = new LottoMachine(new FixedNumberGenerator(List.of()));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> lottoMachine.purchase(unDivisibleByLottoPrice))
                .withMessageContaining(String.valueOf(PRICE_PER_LOTTO));
    }

    @Test
    void 로또_구입_금액이_음수이면_예외를_발생한다() {
        LottoMachine lottoMachine = new LottoMachine(new FixedNumberGenerator(List.of()));
        int negativePurchaseAmount = -500;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> lottoMachine.purchase(negativePurchaseAmount))
                .withMessageContaining(NEGATIVE_PURCHASE_AMOUNT_ERROR_MESSAGE);
    }

    @Test
    void 로또_구매_정상_1장() {
        // TODO: 개선 필요
        FixedNumberGenerator fixedNumberGenerator = new FixedNumberGenerator(List.of(1, 2, 3, 4, 5));

        LottoMachine lottoMachine = new LottoMachine(fixedNumberGenerator);
        Lottos purchased = lottoMachine.purchase(PRICE_PER_LOTTO);

        assertThat(purchased.toResponse()).hasSize(1);
    }

    private static Stream<Arguments> provideUnDivisibleByLottoPrice() {
        return Stream.of(
                Arguments.of(450),
                Arguments.of(550),
                Arguments.of(1200)
        );
    }
}
