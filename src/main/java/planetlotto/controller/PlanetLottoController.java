package planetlotto.controller;

import java.util.List;
import java.util.Map;
import planetlotto.domain.lotto.Lotto;
import planetlotto.domain.lotto.LottoMachine;
import planetlotto.domain.lotto.LottoNumber;
import planetlotto.domain.lotto.Lottos;
import planetlotto.domain.numbergenerator.NumberGenerator;
import planetlotto.domain.winning.WinningLotto;
import planetlotto.domain.winning.WinningStatisticsCalculator;
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
        Map<Integer, Integer> resultMap = WinningStatisticsCalculator.calculateWinningInformation(winningLotto,
                lottos);
        OutputView.printResult(resultMap);
    }

    private WinningLotto setUpWinningLotto() {
        List<Integer> rawWinningLotto = InputView.askWinningLotto();
        Lotto winningLotto = Lotto.of(rawWinningLotto);

        int rawBonusNumber = InputView.askBonusNumber();
        LottoNumber bonusNumber = LottoNumber.from(rawBonusNumber);

        return WinningLotto.of(winningLotto, bonusNumber);
    }

    private Lottos setUpPurchasedLottos() {
        int amount = InputView.askAmount();
        return lottoMachine.purchase(amount);
    }
}
