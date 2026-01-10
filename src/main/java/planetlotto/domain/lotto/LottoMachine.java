package planetlotto.domain.lotto;

import java.util.ArrayList;
import java.util.List;
import planetlotto.domain.numbergenerator.NumberGenerator;

public class LottoMachine {
    private static final int PRICE_PER_LOTTO = 500;

    private final NumberGenerator numberGenerator;

    public LottoMachine(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public List<Lotto> purchase(final int purchaseAmount) {
        validate(purchaseAmount);

        int quantity = purchaseAmount / PRICE_PER_LOTTO;

        return issue(quantity);
    }

    private static void validate(int purchaseAmount) {
        requireNonNegative(purchaseAmount);
        requireDivisibleByLottoPrice(purchaseAmount);
    }

    private static void requireNonNegative(final int purchaseAmount) {
        if (purchaseAmount < 0) {
            throw new IllegalArgumentException("구입 금액은 음수일 수 없습니다.");
        }
    }

    private static void requireDivisibleByLottoPrice(final int purchaseAmount) {
        if (purchaseAmount % PRICE_PER_LOTTO != 0) {
            throw new IllegalArgumentException("구입 금액은 " + PRICE_PER_LOTTO + "로 나누어떨어져야 합니다.");
        }
    }

    private List<Lotto> issue(int quantity) {
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            List<Integer> generated = numberGenerator.generate();
            Lotto lotto = Lotto.of(generated);
            lottos.add(lotto);
        }

        return List.copyOf(lottos);
    }
}
