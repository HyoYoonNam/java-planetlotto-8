package planetlotto.domain.lotto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    // "데이터를 가진 쪽이 프로세스를 처리한다."
    // Lottos는 구매한 Lotto 목록을 가진다. => 순회 프로세스는 Lottos의 책임
    // WinningLotto는 당첨 계산에 필요한 당첨 번호 6개와 보너스 번호를 가진다. => 당첨 계산은 WinningLotto의 책임 (Lottos가 WinningLotto에게 위임)
    public Map<WinningInformation, Integer> matchAll(WinningLotto winningLotto) {
        return lottos.stream()
                .map(winningLotto::calculateWinning)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        // groupingBy는 Long으로 집계하기 때문에 Integer로 하려면 다음과 같이 처리
                        Collectors.summingInt(e -> 1)
                ));
                /* 아래 방법으로도 가능
                .collect(Collectors.toMap(
                        Function.identity(),
                        winningInfo -> 1,
                        Integer::sum
                ));
                */
    }

    public List<List<Integer>> toResponse() {
        return lottos.stream()
                .map(Lotto::getNumbers)
                .toList();
    }
}
