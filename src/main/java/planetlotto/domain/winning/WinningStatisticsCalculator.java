package planetlotto.domain.winning;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import planetlotto.domain.lotto.Lotto;
import planetlotto.domain.lotto.Lottos;

/**
 * 이 정적 유틸 클래스는 당첨 로또와, 구매한 로또 목록을 이용하여 여러 통계랑을 계산한다.
 */

public class WinningStatisticsCalculator {
    private WinningStatisticsCalculator() {
    }

    public static Map<Integer, Integer> calculateWinningInformation(WinningLotto winningLotto,
                                                                    Lottos lottos) {
        Map<WinningInformation, Integer> winningMap = new EnumMap<>(WinningInformation.class);
        for (WinningInformation winningInfo : WinningInformation.values()) {
            winningMap.put(winningInfo, 0);
        }

        for (Lotto lotto : lottos.toList()) {
            int matchCount = winningLotto.calculateMatchCount(lotto);
            boolean bonusMatched = winningLotto.isMatchedBonusNumber(lotto);
            WinningInformation winningInfo =
                    WinningInformation.findByMatchCountAndBonusMatched(matchCount, bonusMatched);
            winningMap.put(winningInfo, winningMap.get(winningInfo) + 1);
        }

        // TODO: 이거 toMap() 순서 유지 되는지 확인 필요
        Map<Integer, Integer> result = winningMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toRank(),
                        Entry::getValue
                ));

        return result;
    }
}
