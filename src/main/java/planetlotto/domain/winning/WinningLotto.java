package planetlotto.domain.winning;

import java.util.List;
import planetlotto.domain.lotto.Lotto;
import planetlotto.domain.lotto.LottoNumber;

/**
 * 이 클래스는 당첨 번호(6개)와 보너스 번호(1개)를 가지는 당첨 로또이다.
 *
 * <p>이 클래스는 정적 팩토리 메서드인 {@code of(List<Integer>, int)}를 통해서만 인스턴스를 생성할 수 있다.
 */

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
}
