package planetlotto;

import planetlotto.controller.PlanetLottoController;
import planetlotto.domain.numbergenerator.RandomNumberGenerator;

public class Application {
    public static void main(String[] args) {
        PlanetLottoController planetLottoController = new PlanetLottoController(new RandomNumberGenerator(1, 30, 5));

        planetLottoController.run();
    }
}
