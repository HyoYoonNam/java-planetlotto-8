package planetlotto.domain.winning;

import planetlotto.domain.lotto.Lotto;
import planetlotto.domain.lotto.LottoNumber;

public class WinningLotto {
    private final Lotto winningNumbers;
    private final LottoNumber bonusNumber;

    private WinningLotto(Lotto winningNumbers, LottoNumber bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("당첨 번호와 보너스 번호 사이에 중복이 존재합니다.");
        }

        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public static WinningLotto of(Lotto winningLotto, LottoNumber bonusNumber) {
        return new WinningLotto(winningLotto, bonusNumber);
    }

    /** {@code lotto}가 당첨 번호(보너스 번호 제외) 중 몇 개를 적중했는지 그 수를 리턴한다. */
    public int calculateMatchCount(Lotto lotto) {
        return winningNumbers.match(lotto);
    }

    public boolean isMatchedBonusNumber(Lotto lotto) {
        return lotto.contains(bonusNumber);
    }
}
