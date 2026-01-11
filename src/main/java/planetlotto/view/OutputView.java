package planetlotto.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class OutputView {
    // 3주 차때 내 코드는 List<Lotto>를 받았다. 하지만 이는 View가 도메인(Lotto)에 의존하기 때문에 좋지 않다.
    public static void printPurchasedLottos(final List<List<Integer>> lottos) {
        final String header = String.format("%d개를 구매했습니다.", lottos.size());
        final String output = lottos.stream()
                .map(Object::toString)
                .collect(Collectors.joining(
                        System.lineSeparator(),
                        header,
                        ""
                ));

        System.out.println(output);
    }

    /**
     * index 0번은 미당첨
     * index 1~5번은 1~5등
     */
    public static void printResult(final Map<Integer, Integer> countsByRank) {
        final List<String> lines = List.of(
                "당첨 통계",
                "---",
                "5개 일치 (100,000,000원) - " + countsByRank.getOrDefault(1, 0) + "개",
                "4개 일치, 보너스 번호 일치 (10,000,000원) - " + countsByRank.getOrDefault(2, 0) + "개",
                "4개 일치 (1,500,000원) - " + countsByRank.getOrDefault(3, 0) + "개",
                "3개 일치, 보너스 번호 일치 (500,000원) - " + countsByRank.getOrDefault(4, 0) + "개",
                "2개 일치, 보너스 번호 일치 (5,000원) - " + countsByRank.getOrDefault(5, 0) + "개",
                "0개 일치 (0원) - " + countsByRank.getOrDefault(0, 0) + "개"
        );
        final String output = lines.stream()
                .collect(Collectors.joining(lineSeparator()));

        System.out.println(output);
    }

    public static void printErrorMessage(final String message) {
        System.out.printf("[ERROR] %s%n", message);
    }
}
