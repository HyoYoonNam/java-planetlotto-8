package planetlotto.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import planetlotto.domain.lotto.Lotto;
import planetlotto.domain.lotto.LottoMachine;
import planetlotto.domain.lotto.LottoNumber;
import planetlotto.domain.lotto.Lottos;
import planetlotto.domain.numbergenerator.NumberGenerator;
import planetlotto.domain.winning.WinningInformation;
import planetlotto.domain.winning.WinningLotto;
import planetlotto.util.RetryUtil;
import planetlotto.view.InputView;
import planetlotto.view.OutputView;

public class PlanetLottoController {
    private final LottoMachine lottoMachine;

    public PlanetLottoController(NumberGenerator numberGenerator) {
        this.lottoMachine = new LottoMachine(numberGenerator);
    }

    public void run() {
        // 입력한 금액에 해당하는 로또 구매
        Lottos lottos = RetryUtil.retryUntilSuccess(
                this::setUpPurchasedLottos, OutputView::printErrorMessage);
        OutputView.printPurchasedLottos(lottos.toResponse());

        // 당첨 번호와 보너스 번호를 입력받아 당첨 로또를 생성
        WinningLotto winningLotto = RetryUtil.retryUntilSuccess(
                this::setUpWinningLotto, OutputView::printErrorMessage);

        // 당첨 결과 계산 후 출력
        Map<WinningInformation, Integer> winningMap = lottos.matchAll(winningLotto);
        OutputView.printResult(countsByRank(winningMap));
    }

    private Lottos setUpPurchasedLottos() {
        int amount = InputView.askAmount();
        return lottoMachine.purchase(amount);
    }

    private WinningLotto setUpWinningLotto() {
        List<Integer> rawWinningLotto = InputView.askWinningLotto();
        Lotto winningLotto = Lotto.of(rawWinningLotto);

        int rawBonusNumber = InputView.askBonusNumber();
        LottoNumber bonusNumber = LottoNumber.from(rawBonusNumber);

        return WinningLotto.of(winningLotto, bonusNumber);
    }

    private Map<Integer, Integer> countsByRank(Map<WinningInformation, Integer> winningMap) {
        return winningMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toRank(),
                        Entry::getValue));
    }
}
