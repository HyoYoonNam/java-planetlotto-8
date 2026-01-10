package planetlotto;

import planetlotto.controller.PlanetLottoController;
import planetlotto.domain.lotto.Lotto;
import planetlotto.domain.lotto.LottoNumber;
import planetlotto.domain.numbergenerator.RandomNumberGenerator;

public class Application {
    public static void main(String[] args) {
        PlanetLottoController planetLottoController = new PlanetLottoController(
                new RandomNumberGenerator(LottoNumber.MIN_NUMBER, LottoNumber.MAX_NUMBER, Lotto.LOTTO_SIZE));

        planetLottoController.run();
    }
}
