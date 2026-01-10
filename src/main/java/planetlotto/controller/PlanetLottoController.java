package planetlotto.controller;

import planetlotto.domain.lotto.LottoMachine;
import planetlotto.domain.lotto.Lottos;
import planetlotto.domain.numbergenerator.NumberGenerator;
import planetlotto.util.RetryUtil;
import planetlotto.view.InputView;
import planetlotto.view.OutputView;

public class PlanetLottoController {
    private final LottoMachine lottoMachine;

    public PlanetLottoController(NumberGenerator numberGenerator) {
        this.lottoMachine = new LottoMachine(numberGenerator);
    }

    public void run() {
        Lottos lottos = RetryUtil.retryUntilSuccess(this::setUpPurchasedLottos, OutputView::printErrorMessage);
        OutputView.printPurchasedLottos(lottos.toResponse());
    }

    private Lottos setUpPurchasedLottos() {
        int amount = InputView.askAmount();
        return lottoMachine.purchase(amount);
    }
}
