package planetlotto.domain.lotto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import planetlotto.domain.winning.WinningInformation;
import planetlotto.domain.winning.WinningLotto;

public class Lottos {
    private final List<Lotto> lottos;

    private Lottos(List<Lotto> lottos) {
        this.lottos = List.copyOf(lottos);
    }

    public static Lottos of(List<Lotto> lottos) {
        return new Lottos(lottos);
    }

    public int size() {
        return lottos.size();
    }

    public Map<WinningInformation, Integer> matchAll(WinningLotto winningLotto) {
        Map<WinningInformation, Integer> result = new EnumMap<>(WinningInformation.class);
        for (WinningInformation winningInfo : WinningInformation.values()) {
            result.put(winningInfo, 0);
        }

        for (Lotto lotto : lottos) {
            WinningInformation winningInformation = findWinningInformation(winningLotto, lotto);
            result.merge(winningInformation, 1, Integer::sum);
        }

        return result;
    }

    public Map<Integer, Integer> matchAllToResponse(WinningLotto winningLotto) {
        Map<Integer, Integer> result = new LinkedHashMap<>();
        Arrays.stream(WinningInformation.values())
                .forEach(winningInfo -> result.put(winningInfo.toRank(), 0));

        for (Lotto lotto : lottos) {
            WinningInformation winningInformation = findWinningInformation(winningLotto, lotto);
            result.merge(winningInformation.toRank(), 1, Integer::sum);
        }

        return result;
    }

    private WinningInformation findWinningInformation(WinningLotto winningLotto, Lotto lotto) {
        return WinningInformation.findByMatchCountAndBonusMatched(
                winningLotto.calculateMatchCount(lotto),
                winningLotto.isMatchedBonusNumber(lotto));
    }

    public List<List<Integer>> toResponse() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .toList();
    }
}
