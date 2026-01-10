package planetlotto.domain.lotto;

import java.util.List;

public class Lottos {
    private final List<Lotto> lottos;

    private Lottos(List<Lotto> lottos) {
        this.lottos = lottos;
    }

    public static Lottos of(List<Lotto> lottos) {
        return new Lottos(lottos);
    }

    public List<Lotto> toList() {
        return List.copyOf(lottos);
    }

    public int size() {
        return lottos.size();
    }

    public List<List<Integer>> toResponse() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .toList();
    }
}
